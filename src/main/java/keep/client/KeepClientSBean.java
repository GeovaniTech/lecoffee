package keep.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import model.Client;
import to.TOClient;
import utils.AbstractManter;
import utils.EmailUtil;
import utils.Encryption;
import utils.MessageUtil;
import utils.RedirectUrl;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepClientSBean extends AbstractManter implements IKeepClientSBean, IKeepClientSBeanRemote {
	
	@Override
	public boolean save(String email, String password, String repetedPasswrod) {		
		if(verifyClient(email)) {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("existing_email"), null, FacesMessage.SEVERITY_ERROR);
			return false;
		}
		
		Client client = new Client();
		
		client.setEmail(email);
		client.setSenha(Encryption.encryptTextSHA(password));
		client.setNivel("client");
		client.setAccountCreationDate(new Date());
		
		em.getTransaction().begin();
		em.persist(client);
		em.getTransaction().commit();
		
		RedirectUrl.redirecionarPara("/lecoffee/login/finished");
		
		return true;
	}
	
	@Override
	public void change(TOClient client) {
		Client model = em.find(Client.class, client.getId());
		
		model.setId(client.getId());
		model.setAccountChangeDate(new Date());
		model.setAccountCreationDate(client.getAccountCreationDate());
		model.setBlocked(client.isBlocked());
		model.setCep(client.getCep());
		model.setComplement(client.getComplement());
		model.setCompletedRegistration(client.isCompletedRegistration());
		//model.setEmail(client.getEmail());
		model.setHouse_number(client.getHouse_number());
		model.setLastLogin(client.getLastLogin());
		model.setNivel(client.getNivel());
		model.setNome(client.getNome());
		model.setStreet(client.getStreet());
		model.setTotalOrders(client.getTotalOrders());
		model.setCarts(client.getCarts());
		
		em.getTransaction().begin();
		em.merge(model);
		em.getTransaction().commit();
	}
	
	public void change(Client client) {
		client.setAccountChangeDate(new Date());
		
		em.getTransaction().begin();
		em.merge(client);
		em.getTransaction().commit();
	}

	@Override
	public boolean verifyClient(String email) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT C ");
		sql.append(" FROM ").append(Client.class.getName()).append(" C ");
		sql.append(" WHERE C.email = :email");
		
		try {
			Client client = em.createQuery(sql.toString(), Client.class)
					.setParameter("email", email)
					.getSingleResult();
			
			return client != null;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean logar(String email, String password) {
		StringBuilder sql = new StringBuilder();
		
		if(!EmailUtil.validateEmail(email)) {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("invalid_email"), null, FacesMessage.SEVERITY_ERROR);
			return false;
		}
		
		sql.append(" SELECT C ");
		sql.append(" FROM ").append(Client.class.getName()).append(" C ");
		sql.append(" WHERE C.email = :email AND C.senha = :password");
		
		try {
			Client client = em.createQuery(sql.toString(), Client.class)
					.setParameter("email", email)
					.setParameter("password", Encryption.encryptTextSHA(password))
					.getSingleResult();
			
			if(client != null) {
				TOClient toClient = new TOClient();
				
				toClient.setId(client.getId());
				toClient.setAccountChangeDate(client.getAccountChangeDate());
				toClient.setAccountCreationDate(client.getAccountCreationDate());
				toClient.setBlocked(client.isBlocked());
				toClient.setCep(client.getCep());
				toClient.setComplement(client.getComplement());
				toClient.setCompletedRegistration(client.isCompletedRegistration());
				toClient.setEmail(client.getEmail());
				toClient.setHouse_number(client.getHouse_number());
				toClient.setLastLogin(client.getLastLogin());
				toClient.setNivel(client.getNivel());
				toClient.setNome(client.getNome());
				toClient.setNivel(client.getNivel());
				toClient.setTotalOrders(client.getTotalOrders());
				toClient.setCarts(client.getCarts());
				
				client.setLastLogin(new Date());
				change(client);
				
				getSession().setAttribute("client", toClient);
				Cookie userCookie = new Cookie("userSession", Encryption.encryptNormalText(client.getEmail()));
				
				userCookie.setMaxAge(60*60*24*30);
				userCookie.setPath("/");
				HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
				
				response.addCookie(userCookie);
				
				if(client.getNivel().equals("admin")) {
					RedirectUrl.redirecionarPara("/lecoffee/admin/pedidos");
				} else {
					RedirectUrl.redirecionarPara("/lecoffee/home");
				}
				
				return true;
			}
			
		} catch (Exception e) {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("invalid_information"), null, FacesMessage.SEVERITY_ERROR);
		}
		
		return false;
	}
	
	@Override
	public TOClient findByEmail(String email) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT C FROM ").append(Client.class.getName()).append(" C ");
		sql.append(" WHERE C.email = :email");
		
		Client client = em.createQuery(sql.toString(), Client.class)
				.setParameter("email", email)
				.getSingleResult();
		
		if(client != null) {
			TOClient toClient = new TOClient();
			
			toClient.setId(client.getId());
			toClient.setAccountChangeDate(client.getAccountChangeDate());
			toClient.setAccountCreationDate(client.getAccountCreationDate());
			toClient.setBlocked(client.isBlocked());
			toClient.setCep(client.getCep());
			toClient.setComplement(client.getComplement());
			toClient.setCompletedRegistration(client.isCompletedRegistration());
			toClient.setEmail(client.getEmail());
			toClient.setHouse_number(client.getHouse_number());
			toClient.setLastLogin(client.getLastLogin());
			toClient.setNivel(client.getNivel());
			toClient.setNome(client.getNome());
			toClient.setNivel(client.getNivel());
			toClient.setTotalOrders(client.getTotalOrders());
			toClient.setCarts(client.getCarts());
			
			return toClient;
		}
		return null;
	}

	@Override
	public List<TOClient> list() {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT C FROM ").append(Client.class.getName()).append(" C ");
		
		List<Client> results = em.createQuery(sql.toString(), Client.class).getResultList();
		
		return convertModelToTransferObject(results);
	}
	
	public List<TOClient> convertModelToTransferObject(List<Client> results) {
		List<TOClient> clients = new ArrayList<TOClient>();
		
		for(Client client : results) {
			TOClient toClient = new TOClient();
			
			toClient.setId(client.getId());
			toClient.setAccountChangeDate(client.getAccountChangeDate());
			toClient.setAccountCreationDate(client.getAccountCreationDate());
			toClient.setBlocked(client.isBlocked());
			toClient.setCep(client.getCep());
			toClient.setComplement(client.getComplement());
			toClient.setCompletedRegistration(client.isCompletedRegistration());
			toClient.setEmail(client.getEmail());
			toClient.setHouse_number(client.getHouse_number());
			toClient.setLastLogin(client.getLastLogin());
			toClient.setNivel(client.getNivel());
			toClient.setNome(client.getNome());
			toClient.setNivel(client.getNivel());
			toClient.setTotalOrders(client.getTotalOrders());
			toClient.setCarts(client.getCarts());
			
			clients.add(toClient);
		}
		
		return clients;
	}

	@Override
	public void setNewPassword(String email, String password) {
		TOClient toClient = findByEmail(email);
		
		Client client = em.find(Client.class, toClient.getId());
		
		client.setSenha(Encryption.encryptTextSHA(password));
		
		change(client);
	}
}
