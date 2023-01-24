package manter.client;

import javax.ejb.Local;

@Local
public interface IManterClientSBean {
	public void save(String email, String password, String repetedPasswrod);
	
	public boolean verifyClient();
}
