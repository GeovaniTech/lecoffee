package keep.client;

import javax.ejb.Local;
import to.TOClient;

@Local
public interface IKeepClientSBean {
	public boolean save(String email, String password, String repetedPasswrod);
	public void change(TOClient client);
	public boolean verifyClient(String email);
	public boolean logar(String email, String password);
	public TOClient findByEmail(String email);
}
