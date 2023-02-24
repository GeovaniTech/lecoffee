package managedBean;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import manter.client.ManterClientSBean;
import to.TOClient;
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
		createCookiePreferences();
		
		getSession().invalidate();
		RedirectUrl.redirecionarPara("/lecoffee/pages/login.xhtml");
	}
	
	public void createCookiePreferences() {
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		TOClient client = getClient();
		
		Cookie darkMode = new Cookie("darkMode", "" + client.getPreferences().isDarkMode());
		Cookie language = new Cookie("language", "" + client.getPreferences().getLanguage());
		
		response.addCookie(darkMode);
		response.addCookie(language);
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
