package manter.client;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.AppConfigs;
import model.Client;
import to.TOClient;
import utils.AbstractManter;
import utils.EmailValidator;
import utils.PasswordEncryption;
import utils.RedirectUrl;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ManterClientSBean extends AbstractManter implements IManterClientSBean, IManterClientSBeanRemote {
	
	private ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	private HttpServletRequest httpServletRequest = (HttpServletRequest) externalContext.getRequest();
	private HttpServletResponse httpServletResponse = (HttpServletResponse) externalContext.getResponse();
	
	@Override
	public boolean save(String email, String password, String repetedPasswrod) {
		if(!EmailValidator.validateEmail(email)) {
			msg.emailInvalido();
			return false;
		}
		
		if(!password.equals(repetedPasswrod)) {
			msg.senhasNaoSaoIguais();
			return false;
		}
		
		if(verifyClient(email)) {
			msg.emailJaExistente();
			return false;
		}
		
		Client client = new Client();
		
		client.setEmail(email);
		client.setSenha(PasswordEncryption.encrypt(password));
		client.setNivel("client");
		
		em.getTransaction().begin();
		em.persist(client);
		em.getTransaction().commit();
		
		RedirectUrl.redirecionarPara("/lecoffee/pages/login.xhtml");
		
		return true;
	}
	
	@Override
	public void change(TOClient client) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" UPDATE ").append(Client.class.getName()).append(" C ");
		sql.append(" SET C.nome = :nome, ");
		sql.append(" C.email = :email, ");
		sql.append(" C.cart = :cart, ");
		sql.append(" C.preferences = :preferences ");
		sql.append(" WHERE C.id = :id_client");
		
		em.getTransaction().begin();
		em.createQuery(sql.toString())
			.setParameter("nome", client.getNome())
			.setParameter("email", client.getEmail())
			.setParameter("cart", client.getCart())
			.setParameter("preferences", client.getPreferences())
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
		
		if(!EmailValidator.validateEmail(email)) {
			msg.emailInvalido();
			return false;
		}
		
		sql.append(" SELECT C ");
		sql.append(" FROM ").append(Client.class.getName()).append(" C ");
		sql.append(" WHERE C.email = :email AND C.senha = :password");
		
		try {
			Client client = em.createQuery(sql.toString(), Client.class)
					.setParameter("email", email)
					.setParameter("password", PasswordEncryption.encrypt(password))
					.getSingleResult();
			
			if(client != null) {
				TOClient toClient = new TOClient();
				
				toClient.setEmail(client.getEmail());
				toClient.setCart(client.getCart());
				toClient.setPreferences(client.getPreferences());
				toClient.setNome(client.getNome());
				toClient.setNivel(client.getNivel());
				toClient.setId(client.getId());
				
				getSession().setAttribute("client", toClient);
				HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
				Cookie userCookie = new Cookie("userSession", client.getEmail());
				
				userCookie.setMaxAge(60*60*24*30);
				userCookie.setPath("/");
				
				response.addCookie(userCookie);
				
				if(client.getNivel().equals("admin")) {
					RedirectUrl.redirecionarPara("/lecoffee/pages/admin/pedidos.xhtml");
				} else {
					RedirectUrl.redirecionarPara("/lecoffee/pages/client/home.xhtml");
				}
				
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			msg.informacoesInvalidas();
		}
		
		return false;
	}

	@Override
	public void saveNewPreferences(AppConfigs preferences) {
		TOClient client = getClient();
		client.setPreferences(preferences);
		
		change(client);
		
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
			toClient.setCart(client.getCart());
			toClient.setPreferences(client.getPreferences());
			toClient.setNome(client.getNome());
			toClient.setNivel(client.getNivel());
			toClient.setId(client.getId());
			
			return toClient;
		}
		return null;
	}

	public ExternalContext getExternalContext() {
		return externalContext;
	}

	public void setExternalContext(ExternalContext externalContext) {
		this.externalContext = externalContext;
	}

	public HttpServletRequest getHttpServletRequest() {
		return httpServletRequest;
	}

	public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}

	public HttpServletResponse getHttpServletResponse() {
		return httpServletResponse;
	}

	public void setHttpServletResponse(HttpServletResponse httpServletResponse) {
		this.httpServletResponse = httpServletResponse;
	}
}
