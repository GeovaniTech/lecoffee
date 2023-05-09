package keep.client;

import java.util.List;

import javax.ejb.Local;
import to.TOClient;

@Local
public interface IKeepClientSBean {
	public boolean save(String email, String password, String repetedPasswrod);
	public void change(TOClient client);
	public boolean verifyClient(String email);
	public boolean logar(String email, String password);
	public TOClient findByEmail(String email);
	public List<TOClient> list();
	public void setNewPassword(String email, String password);
}
