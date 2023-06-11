package managedBean;

import java.awt.Toolkit;
import java.io.IOException;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import keep.client.KeepClientSBean;
import model.AppConfigs;
import to.TOClient;
import utils.Cookies;
import utils.Encryption;
import utils.ImageUtil;
import utils.LeCoffeeSession;
import utils.RedirectUrl;

@Named("MBAppConfigs")
@SessionScoped
public class MBAppConfigs extends LeCoffeeSession implements Serializable {
	private static final long serialVersionUID = -2862315075236619884L;

	private AppConfigs appConfigs;
	private List<Locale> localeList;
	private KeepClientSBean clientSBean;
	
	public MBAppConfigs() {
		//Attributes
		this.setAppConfigs(new AppConfigs());
		this.setLocaleList(new ArrayList<Locale>());
		this.getLocaleList().add(new Locale("pt"));
		this.getLocaleList().add(new Locale("en"));
		
		//SBeans
		this.setClientSBean(new KeepClientSBean());
		
		//Initial Configurations
		this.getAppConfigs().setLanguage(Locale.getDefault().getLanguage());
		this.getAppConfigs().setDarkMode(false);
		
		//Getting User preferences
		this.getConfigsFromCookies();
		
		//Redirecting if user is logged
		this.redirectUserFromCookie();
	}
 	
 	public boolean getConfigsFromCookies() {
 		this.getAppConfigs().setDarkMode(Cookies.getDarkModeCookie());
 		
 		if(Cookies.getLanguageCookie() != null) {
 			this.getAppConfigs().setLanguage(Cookies.getLanguageCookie());
 			
 			return true;
 		}
 		return false;
 	}
	
	public void redirectUserFromCookie() {
		String userEmail = Cookies.getUserCookie();
		
		if(userEmail != null && !userEmail.equals("")) {
			try {
				TOClient client =  this.getClientSBean().findByEmail(Encryption.decryptNormalText(userEmail));
				
				if(client.isBlocked()) {
					RedirectUrl.redirecionarPara("/lecoffee/login");
					
					return;
				}
				
				getSession().setAttribute("client", client);
				
				client.setLastLogin(new Date());
				this.getClientSBean().change(client);
			
				if(this.getClientLogged().getNivel().equals("admin")) {
					RedirectUrl.redirecionarPara("/lecoffee/admin/pedidos");
				} else {
					RedirectUrl.redirecionarPara("/lecoffee/home");
				}
			} catch (Exception e) {
				// User not found
				removeUserFromCookie();
				
				RedirectUrl.redirecionarPara("/lecoffee/home");
			}
		}
	}
	
	public void createCookiePreferences() {
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	
		Cookie darkMode = new Cookie("darkMode", "" + this.getAppConfigs().isDarkMode());
		darkMode.setMaxAge(Integer.MAX_VALUE);
		darkMode.setPath("/");
		
		Cookie language = new Cookie("language", this.getAppConfigs().getLanguage());
		language.setMaxAge(Integer.MAX_VALUE);
		language.setPath("/");
		
		response.addCookie(darkMode);
		response.addCookie(language);
	}
	
	public void logout() {
		removeUserFromCookie();
		
		finishSession();
		
		RedirectUrl.redirecionarPara("/lecoffee/login");
	}
	
	public void removeUserFromCookie() {
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		Cookie userSession = new Cookie("userSession", null);
		userSession.setMaxAge(60*60*24*30);
		userSession.setPath("/lecoffee");
		
		response.addCookie(userSession);
	}

	public String getBrazilianCurrency(Double value) {
		Locale localeBR = new Locale("pt", "BR");
		NumberFormat brazilianFormat = NumberFormat.getCurrencyInstance(localeBR);
		String formattedValue = brazilianFormat.format(value);

		return formattedValue;
	}

	public String getRenderedImage(byte[] imageBytes) {
		return ImageUtil.geRenderedImage(imageBytes);
	}

	public void refreshPage() throws IOException {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	}
		
	public boolean isUserLogged() {
		try {
			TOClient client = getClient();
			
			if(client != null) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		
		return false;
	}
	
	public void redirectTo(String url) {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

		try {
			ec.redirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isMobileScreen() {
		System.setProperty("java.awt.headless", "false");
		
		System.out.println(Toolkit.getDefaultToolkit().getScreenSize().getWidth());
		
		return Toolkit.getDefaultToolkit().getScreenSize().getWidth() < 900;
	}

	// Getters and Setters
	public List<Locale> getLocaleList() {
		return localeList;
	}

	public void setLocaleList(List<Locale> localeList) {
		this.localeList = localeList;
	}

	public AppConfigs getAppConfigs() {
		return appConfigs;
	}

	public void setAppConfigs(AppConfigs appConfigs) {
		this.appConfigs = appConfigs;
	}

	public KeepClientSBean getClientSBean() {
		return clientSBean;
	}

	public void setClientSBean(KeepClientSBean clientSBean) {
		this.clientSBean = clientSBean;
	}

	public TOClient getClientLogged() {
		return getClient();
	}
}
