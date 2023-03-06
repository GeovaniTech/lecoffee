package managedBean;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import keep.client.KeepClientSBean;

import to.TOClient;
import utils.AbstractBean;
import utils.RedirectUrl;

@Named("MBLogin")
@ViewScoped
public class MBLogin extends AbstractBean {
	private static final long serialVersionUID = -6696747613785703525L;
	
	private String email;
	private String password;
	private KeepClientSBean sBean;
	
	public MBLogin() {		
		sBean = new KeepClientSBean();
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
		
		if(client != null) {
			Cookie darkMode = new Cookie("darkMode", "" + client.getPreferences().isDarkMode());
			darkMode.setMaxAge(Integer.MAX_VALUE);
			darkMode.setPath("/");
			
			Cookie language = new Cookie("language", "" + client.getPreferences().getLanguage());
			language.setMaxAge(Integer.MAX_VALUE);
			language.setPath("/");
			
			response.addCookie(darkMode);
			response.addCookie(language);
		}
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

	public KeepClientSBean getsBean() {
		return sBean;
	}

	public void setsBean(KeepClientSBean sBean) {
		this.sBean = sBean;
	}
}
