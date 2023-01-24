package manter.client;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import model.Client;
import utils.AbstractManter;
import utils.EmailValidator;

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
		client.setSenha(password);
		
		em.getTransaction().begin();
		em.persist(client);
		em.getTransaction().commit();
		
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
			
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return false;
	}
	
	
}
