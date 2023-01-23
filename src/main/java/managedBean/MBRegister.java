package managedBean;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import utils.AbstractBean;

@Named("MBRegister")
@ViewScoped
public class MBRegister extends AbstractBean {
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String senha;
	private String confirmaSenha;
	
	public void cadastrar() {
		msg.teste();
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getConfirmaSenha() {
		return confirmaSenha;
	}
	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}
}
