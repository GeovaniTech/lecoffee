package managedBean.appconfigs;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.primefaces.PrimeFaces;

import abstracts.AbstractMBean;
import enums.EnumLogCategory;
import enums.EnumLogType;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import keep.client.IKeepClientSBean;
import to.appconfigs.TOAppConfig;
import to.client.TOClient;
import to.logs.TOLog;
import utils.CookieUtil;
import utils.ImageUtil;
import utils.RedirectURL;

@Named("MBAppConfigs")
@SessionScoped
public class MBAppConfigs extends AbstractMBean {

	private static final long serialVersionUID = 8432905268667991640L;
	public static final String MANAGED_BEAN_NAME = "MBAppConfigs";
	
	private TOAppConfig appConfigs;
	private List<Locale> localeList;
	
	// For actions that require password
	private String password;
	
	@EJB
	private IKeepClientSBean clientSBean;

	public MBAppConfigs() {
		//Attributes
		this.setAppConfigs(new TOAppConfig());
		this.setLocaleList(new ArrayList<Locale>());
		this.getLocaleList().add(new Locale("pt_BR"));
		this.getLocaleList().add(new Locale("en_US"));
		
		//Initial Configurations
		this.getAppConfigs().setLanguage(Locale.getDefault().getLanguage());
		this.getAppConfigs().setDarkMode(false);
		this.getAppConfigs().setShowValues(false);
		
		//Getting User preferences
		this.getConfigsFromCookies();	
	}
	
 	public boolean getConfigsFromCookies() {
 		this.getAppConfigs().setDarkMode(CookieUtil.getDarkModeCookie());
 		
 		if(CookieUtil.getLanguageCookie() != null) {
 			this.getAppConfigs().setLanguage(CookieUtil.getLanguageCookie());
 			
 			return true;
 		}
 		return false;
 	}
 	
 	public void configAppByUserPreferences() {
 		if(this.getClientLogged() != null && this.getClientLogged().getAppConfig() != null) {
 			this.setAppConfigs(this.getClientLogged().getAppConfig());
 			
 			if(!this.getAppConfigs().isShowValuesStartUp()) {
 				this.getAppConfigs().setShowValues(false);
 			} else {
 				this.getAppConfigs().setShowValues(true);
 			} 
 			
 			this.createCookiePreferences();
 		} else {
 			updateUserConfigs();
 		}
 	}
 	
 	public void updateUserConfigs() {
 		this.getClientLogged().setAppConfig(this.getAppConfigs());
 		this.getClientSBean().change(this.getClientLogged());
 		
 		this.createCookiePreferences();
 		this.updateCards();
 	}
	
	public void createCookiePreferences() {	
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		Cookie darkMode = new Cookie("darkMode", "" + this.getAppConfigs().isDarkMode());
		darkMode.setMaxAge(60*60*24*30);
		darkMode.setPath("/lecoffee");
		
		Cookie language = new Cookie("language", this.getAppConfigs().getLanguage());
		language.setMaxAge(60*60*24*30);
		language.setPath("/lecoffee");
		
		response.addCookie(darkMode);
		response.addCookie(language);
	}
	
	public void removeUserFromCookie() {
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		Cookie userSession = new Cookie("userSession", null);
		userSession.setMaxAge(60*60*24*30);
		userSession.setPath("/lecoffee");
		
		response.addCookie(userSession);
	}
	
	public void updateCards() {
		PrimeFaces.current().executeScript("updateTableAndCards();");
	}
	
	public void logout() {
		removeUserFromCookie();
		finishSession();
		
		RedirectURL.redirectTo("/lecoffee/login");
	}
	
	public void deleteAccount() { 		
		try {
			this.getClientSBean().deleteAccount();
			
			TOLog log = new TOLog();
			log.setCategory(EnumLogCategory.DELETE_ACCOUNT);
			log.setType(EnumLogType.INFO);
			log.setStack("USER ACCOUNT DELETED SUCCESSFULLY. USER DELETE: " +  this.getClientSession().getEmail());
			log.setCreationUser(this.getClientSession().getEmail());
			log.setCreationDate(new Date());
			log.setIp(this.getUserIpAddress());
			
			saveLog(log);
			
			finishSession();

			RedirectURL.redirectTo("/lecoffee/login/accountdeleted");
		} catch (Exception e) {
			TOLog log = new TOLog();
			log.setCategory(EnumLogCategory.DELETE_ACCOUNT);
			log.setType(EnumLogType.EXCEPTION);
			log.setStack(ExceptionUtils.getStackTrace(e));
			log.setCreationUser(this.getClientSession().getEmail());
			log.setCreationDate(new Date());
			log.setIp(this.getUserIpAddress());
			
			saveLog(log);
			
			e.printStackTrace();
		}
	}
	
	public boolean isUserLogged() {
		if(this.getClientSession() != null) {
			return true;
		}
		
		return false;
	}
	
	public boolean isUserAdminLogged() {
		if(this.getClientSession() != null && (this.getClientLogged().getSecurityLevel().equals("admin") || this.getClientLogged().getSecurityLevel().equals("superadmin"))) {
			return true;
		}
		
		return false;
	}
	
	public void redirectTo(String url) {
		RedirectURL.redirectTo(url);
	}
	
	public String getBrazilianCurrency(Double value) {
		Locale localeBR = new Locale("pt", "BR");
		NumberFormat brazilianFormat = NumberFormat.getCurrencyInstance(localeBR);
		String formattedValue = brazilianFormat.format(value == null ? 0.0 : value);

		return formattedValue;
	}

	public String getRenderedImage(byte[] imageBytes) {
		return ImageUtil.geRenderedImage(imageBytes);
	}

	public void refreshPage() throws IOException {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	}
	
	public String getSystemVersion() {
		 FacesContext facesContext = FacesContext.getCurrentInstance(); 
		 Locale locale = facesContext.getViewRoot().getLocale();
		 ResourceBundle bundle = ResourceBundle.getBundle("app-config", locale);

		 return bundle.getString("system_version");
	}
	
	// Getters and Setters
	public TOAppConfig getAppConfigs() {
		return appConfigs;
	}
	public void setAppConfigs(TOAppConfig appConfigs) {
		this.appConfigs = appConfigs;
	}
	public List<Locale> getLocaleList() {
		return localeList;
	}
	public void setLocaleList(List<Locale> localeList) {
		this.localeList = localeList;
	}
	public TOClient getClientLogged() {
		return this.getClientSession();
	}
	public IKeepClientSBean getClientSBean() {
		return clientSBean;
	}
	public void setClientSBean(IKeepClientSBean clientSBean) {
		this.clientSBean = clientSBean;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}

