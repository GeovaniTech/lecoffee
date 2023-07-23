package keep.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.Query;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;

import model.Client;
import to.TOClient;
import to.TOFilterLovClient;
import utils.AbstractManter;
import utils.EmailUtil;
import utils.Encryption;
import utils.JwtTokenUtil;
import utils.MessageUtil;
import utils.RedirectUrl;
import utils.SimpleWhere;
import utils.StringUtil;
import utils.UserContext;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepClientSBean extends AbstractManter<Client, TOClient> implements IKeepClientSBean, IKeepClientSBeanRemote {
	private ModelMapper converter;
	
	public KeepClientSBean() {
		this.setClassTypes(Client.class, TOClient.class);
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
				
				UserContext.setCurrentUser(toClient.getEmail());
				
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

	@Override
	public TOClient findById(int id) {
		Client client = em.find(Client.class, id);
		
		TOClient to = null;
		
		if(client != null) {
			to = this.getConverter().map(client, TOClient.class);
		}
		
		return to;
	}
	
	@Override
	public List<TOClient> listClientsLov(TOFilterLovClient filter) {
		StringBuilder sql = new StringBuilder();
		
		HashMap<String, Object> parameters = new HashMap<>();
		
		sql.append(" SELECT DISTINCT C FROM ");
		sql.append(Client.class.getName()).append(" C ");
		sql.append(" LEFT JOIN C.addresses AS A ");
		sql.append(" WHERE 1 = 1 ");
		sql.append(SimpleWhere.queryFilter("C.nome", filter.getName()));
		sql.append(SimpleWhere.queryFilter("C.email", filter.getEmail()));
		sql.append(SimpleWhere.queryFilter("A.street", filter.getStreet()));
		sql.append(SimpleWhere.queryFilter("A.neighborhood", filter.getNeighborhood()));
		sql.append(SimpleWhere.queryFilter("A.complement", filter.getComplement()));
		sql.append(SimpleWhere.queryFilter("A.cep", filter.getCep()));
		
		if(filter.getNumberHouse() != null) {
			sql.append(" AND A.house_number = :house_number ");
			parameters.put("house_number", filter.getNumberHouse());
		}
		
		if(filter.getPhoneNumber() != null && filter.getPhoneNumber().length() == 16) {
			sql.append(" AND C.phone_number = :phone_number ");
			parameters.put("phone_number", filter.getPhoneNumber());
		}
		
		Query query = em.createQuery(sql.toString(), Client.class);
		
		for(String obj : parameters.keySet()) {
			query.setParameter(obj, parameters.get(obj));
		}
		
		return this.convertModelResults(query.getResultList());
	}

	//Getters and Setters
	public ModelMapper getConverter() {
		return converter;
	}
	public void setConverter(ModelMapper converter) {
		this.converter = converter;
	}
}
