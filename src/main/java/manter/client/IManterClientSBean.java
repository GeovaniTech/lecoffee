package manter.client;

import javax.ejb.Local;

import model.AppConfigs;
import to.TOClient;

@Local
public interface IManterClientSBean {
	public boolean save(String email, String password, String repetedPasswrod);
	public void change(TOClient client);
	public boolean verifyClient(String email);
	public void logar(String email, String password);
	public void saveNewPreferences(AppConfigs preferences);
}
