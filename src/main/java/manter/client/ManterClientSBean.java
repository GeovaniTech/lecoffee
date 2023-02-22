package manter.client;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

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
		Client model = new Client();
		
		model.setId(client.getId());
		model.setNome(client.getNome());
		model.setEmail(client.getEmail());
		model.setCart(client.getCart());
		model.setPreferences(client.getPreferences());
		
		em.getTransaction().begin();
		em.merge(model);
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
	public void logar(String email, String password) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT C ");
		sql.append(" FROM ").append(Client.class.getName()).append(" C ");
		sql.append(" WHERE C.email = :email AND C.senha = :password");
		
		try {
			Client client = em.createQuery(sql.toString(), Client.class)
					.setParameter("email", email)
					.setParameter("password", PasswordEncryption.encrypt(password))
					.getSingleResult();
			
			TOClient toClient = new TOClient();
			
			toClient.setEmail(client.getEmail());
			toClient.setCart(client.getCart());
			toClient.setPreferences(client.getPreferences());
			toClient.setNome(client.getNome());
			toClient.setNivel(client.getNivel());
			toClient.setId(client.getId());
			
			getSession().setAttribute("client", toClient);
			
			if(client.getNivel().equals("admin")) {
				RedirectUrl.redirecionarPara("/lecoffee/pages/admin/pedidos.xhtml");
			} else {
				RedirectUrl.redirecionarPara("/lecoffee/pages/client/home.xhtml");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			msg.informacoesInvalidas();
		}
	}

	@Override
	public void saveNewPreferences(AppConfigs preferences) {
		TOClient client = getClient();
		client.setPreferences(preferences);
		
		change(client);
		
	}
}
