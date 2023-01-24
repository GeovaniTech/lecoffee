package manter.client;

import javax.ejb.Local;

@Local
public interface IManterClientSBean {
	public boolean save(String email, String password, String repetedPasswrod);
	public boolean verifyClient(String email);
	public void logar(String email, String password);
}
