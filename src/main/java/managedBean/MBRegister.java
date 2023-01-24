package managedBean;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import manter.client.ManterClientSBean;
import utils.AbstractBean;

@Named("MBRegister")
@ViewScoped
public class MBRegister extends AbstractBean {
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String senha;
	private String confirmaSenha;
	private ManterClientSBean sbean;
	
	public MBRegister() {
		sbean = new ManterClientSBean();
	}
	
	public void cadastrar() {
		sbean.save(email, senha, confirmaSenha);
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
	public ManterClientSBean getSbean() {
		return sbean;
	}
	public void setSbean(ManterClientSBean sbean) {
		this.sbean = sbean;
	}
}
