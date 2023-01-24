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
	public void save(String email, String password, String repetedPasswrod) {
		if(!EmailValidator.validateEmail(email)) {
			msg.emailInvalido();
		}
		
		if(!password.equals(repetedPasswrod)) {
			msg.senhasNaoSaoIguais();
		}
	}

	@Override
	public boolean verifyClient() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT C ");
		sql.append(" FROM ").append(Client.class.getName()).append(" C ");
		sql.append(" WHERE C.email = :email");
		return false;
	}
	
	
}
