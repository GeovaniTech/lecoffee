package managedBean;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import keep.client.KeepClientSBean;

import to.TOClient;
import utils.AbstractBean;
import utils.Cookies;
import utils.Encryption;
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
		redirectUserFromCookie();
	}

	public void logar() {
		sBean.logar(email, password);
	}
	
	public void redirectUserFromCookie() {
		String user = Cookies.getUserCookie();
		
		if(user != null) {
			TOClient toClient = sBean.findByEmail(Encryption.decryptNormalText(user));
			
			getSession().setAttribute("client", toClient);
			
			if(toClient.getNivel().equals("admin")) {
				RedirectUrl.redirecionarPara("/lecoffee/pages/admin/pedidos.xhtml");
			} else {
				RedirectUrl.redirecionarPara("/lecoffee/pages/client/home.xhtml");
			}
		}
	}
	
	public void logout() {
		createCookiePreferences();
		
		Cookie userSession = new Cookie("userSession", "");
		userSession.setMaxAge(60*60*24*30);
		userSession.setPath("/");
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		response.addCookie(userSession);
		
		//RedirectUrl.redirecionarPara("/lecoffee/pages/login.xhtml");
		
		getSession().invalidate();
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
