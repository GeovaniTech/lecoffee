package managedBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import manter.client.ManterClientSBean;
import utils.AbstractBean;
import utils.RedirectUrl;

@Named("MBLogin")
@ViewScoped
public class MBLogin extends AbstractBean {
	private static final long serialVersionUID = -6696747613785703525L;
	
	private String email;
	private String password;
	private ManterClientSBean sBean;
	
	
	public MBLogin() {		
		sBean = new ManterClientSBean();
	}

	public void logar() {
		sBean.logar(email, password);
	}
	
	public void logout() {
		getSession().setAttribute("client", null);
		RedirectUrl.redirecionarPara("/lecoffee/pages/login.xhtml");
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ManterClientSBean getsBean() {
		return sBean;
	}

	public void setsBean(ManterClientSBean sBean) {
		this.sBean = sBean;
	}
}