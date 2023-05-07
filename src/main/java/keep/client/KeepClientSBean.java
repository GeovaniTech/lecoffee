package keep.client;

import java.util.ArrayList;
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
		
		em.getTransaction().begin();
		em.persist(client);
		em.getTransaction().commit();
		
		RedirectUrl.redirecionarPara("/lecoffee/login/finished");
		
		return true;
	}
	
	@Override
	public void change(TOClient client) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" UPDATE ").append(Client.class.getName()).append(" C ");
		sql.append(" SET C.nome = :nome, ");
		sql.append(" C.email = :email, ");
		sql.append(" C.carts = :carts ");
		sql.append(" WHERE C.id = :id_client");
		
		em.getTransaction().begin();
		em.createQuery(sql.toString())
			.setParameter("nome", client.getNome())
			.setParameter("email", client.getEmail())
			.setParameter("carts", client.getCarts())
			.setParameter("id_client", client.getId())
			.executeUpdate();
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
				
				toClient.setEmail(client.getEmail());
				toClient.setCarts(client.getCarts());
				toClient.setNome(client.getNome());
				toClient.setNivel(client.getNivel());
				toClient.setId(client.getId());
				
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
			
			toClient.setEmail(client.getEmail());
			toClient.setCarts(client.getCarts());
			toClient.setNome(client.getNome());
			toClient.setNivel(client.getNivel());
			toClient.setId(client.getId());
			
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
			TOClient to = new TOClient();
			
			to.setId(client.getId());
			to.setNome(client.getNome());
			to.setEmail(client.getEmail());
			to.setCompletedRegistration(client.isCompletedRegistration());
			to.setCarts(client.getCarts());
			to.setNivel(client.getNivel());
			to.setTotalOrders(client.getTotalOrders());
			
			clients.add(to);
		}
		
		return clients;
	}
}
