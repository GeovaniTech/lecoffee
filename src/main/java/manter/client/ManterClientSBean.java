package manter.client;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import model.Client;
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
			
			getSession().setAttribute("client", client);
			
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
	
	
}
