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

import org.modelmapper.ModelMapper;

import model.Client;
import to.TOClient;
import utils.AbstractManter;
import utils.EmailUtil;
import utils.Encryption;
import utils.JwtTokenUtil;
import utils.MessageUtil;
import utils.RedirectUrl;
import utils.StringUtil;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepClientSBean extends AbstractManter implements IKeepClientSBean, IKeepClientSBeanRemote {
	private ModelMapper converter;
	
	public KeepClientSBean() {
		this.setConverter(new ModelMapper());
	}
	
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
		client.setCreationDate(new Date());
		
		if(getClient() != null) {
			client.setCreationUser(getClient().getEmail());
		}
		
		em.getTransaction().begin();
		em.persist(client);
		em.getTransaction().commit();
		
		RedirectUrl.redirecionarPara("/lecoffee/login/finished");
		
		return true;
	}
	
	@Override
	public void save(TOClient client) {
		Client model = this.getConverter().map(client, Client.class);
		
		em.getTransaction().begin();
		em.persist(model);
		em.getTransaction().commit();
	}
	
	@Override
	public void change(TOClient client) {
		Client model = this.getConverter().map(client, Client.class);
		
		Client pattern = em.find(Client.class, model.getId());
		
		if(!StringUtil.isNotNull(model.getEmail())) {
			model.setEmail(pattern.getEmail());
		}
		
		if(!StringUtil.isNotNull(model.getSenha())) {
			model.setSenha(pattern.getSenha());
		}
		
		em.getTransaction().begin();
		em.merge(model);
		em.getTransaction().commit();
	}
	
	public void change(Client client) {
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
		sql.append(" WHERE C.email = :email AND C.senha = :password ");
		sql.append(" OR C.email = :email AND C.changePassword = true ");
		
		try {
			Client client = em.createQuery(sql.toString(), Client.class)
					.setParameter("email", email)
					.setParameter("password", Encryption.encryptTextSHA(password))
					.getSingleResult();
			
			if(client != null) {
				if(client.isBlocked()) {
					MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("user_blocked"), null, FacesMessage.SEVERITY_ERROR);
					
					return false;
				}
				
				if(client.isChangePassword()) {
					String token = JwtTokenUtil.generateEmailToken(client.getEmail(), null);
					
					RedirectUrl.redirecionarPara("/lecoffee/newpassword/" + token);
					
					return false;
				}
				
				TOClient toClient = this.getConverter().map(client, TOClient.class);
				
				client.setLastLogin(new Date());
				change(client);
				
				getSession().setAttribute("client", toClient);
				Cookie userCookie = new Cookie("userSession", Encryption.encryptNormalText(client.getEmail()));
				
				userCookie.setMaxAge(60*60*24*30);
				userCookie.setPath("/lecoffee");
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
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("user_or_password_incorrect"), null, FacesMessage.SEVERITY_ERROR);
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
			TOClient toClient = this.getConverter().map(client, TOClient.class);
			
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
			TOClient toClient = this.getConverter().map(client, TOClient.class);
			
			clients.add(toClient);
		}
		
		return clients;
	}
	
	public TOClient convertModelToDTO(Client client) {
		return null;
	}

	@Override
	public void setNewPassword(String email, String password) {
		TOClient toClient = findByEmail(email);
		
		Client client = em.find(Client.class, toClient.getId());
		
		client.setSenha(Encryption.encryptTextSHA(password));
		client.setChangeDate(new Date());
		
		if(getClient() != null) {
			client.setChangeUser(getClient().getEmail());
		} else {
			client.setChangeUser(getClient().getEmail());
		}
		
		client.setChangePassword(false);
		
		change(client);
	}

	@Override
	public void remove(TOClient client) {
		Client model = em.find(Client.class, client.getId());
		
		em.getTransaction().begin();
		em.remove(em.contains(model) ? model : em.merge(model));
		em.getTransaction().commit();
	}

	//Getters and Setters
	public ModelMapper getConverter() {
		return converter;
	}
	public void setConverter(ModelMapper converter) {
		this.converter = converter;
	}
}
