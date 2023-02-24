package managedBean;

import java.io.IOException;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import keep.appConfigs.KeepAppConfigs;
import manter.client.ManterClientSBean;
import model.AppConfigs;
import to.TOClient;
import utils.ImageUtil;
import utils.LeCoffeeSession;

@Named("MBAppConfigs")
@SessionScoped
public class MBAppConfigs extends LeCoffeeSession implements Serializable {
	private static final long serialVersionUID = -2862315075236619884L;

	private AppConfigs appConfigs;
	private List<Locale> localeList;
	private ManterClientSBean clientSBean;
	private KeepAppConfigs appConfigsSBean;

	private ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	private HttpServletRequest httpServletRequest = (HttpServletRequest) externalContext.getRequest();
	private HttpServletResponse httpServletResponse = (HttpServletResponse) externalContext.getResponse();
	
	public MBAppConfigs() {
		this.appConfigs = new AppConfigs();
		this.localeList = new ArrayList<Locale>();
		this.clientSBean = new ManterClientSBean();
		this.appConfigsSBean = new KeepAppConfigs();
		
		localeList.add(new Locale("pt"));
		localeList.add(new Locale("en"));
		
		updateConfigs();
	}

	public void updateConfigs() {
		TOClient client = getClient();

		if(client == null) {
			if(getLanguageCookie() != null) {
				appConfigs.setLanguage(getLanguageCookie());
			} else {
				appConfigs.setLanguage(Locale.getDefault().getLanguage());
			}
			
			if(getDarkModeCookie()) {
				appConfigs.setDarkMode(getDarkModeCookie());
			} else {
				appConfigs.setDarkMode(false);
			}
		} else {
			appConfigs.setDarkMode(client.getPreferences().isDarkMode());
			appConfigs.setLanguage(client.getPreferences().getLanguage());
		}
		
		createCookiePreferences();
	}
	
	public String getLanguageCookie() {
	  Cookie[] cookies = httpServletRequest.getCookies();
	  if(cookies!=null) {
	    for (Cookie cookie : cookies) {
	      if(cookie.getName().equals("language")) {
	        return cookie.getValue();
	      }
	    }
	  }
	  
	  return null;
	}
	
	public boolean getDarkModeCookie() {
		  Cookie[] cookies = httpServletRequest.getCookies();
		  if(cookies!=null) {
		    for (Cookie cookie : cookies) {
		      if(cookie.getName().equals("darkMode")) {
		        return Boolean.parseBoolean(cookie.getValue());
		      }
		    }
		  }
		  
		  return false;
	}
	
	public void setNewPreferences() {
		TOClient client = getClient();
		
		if(client.getPreferences() == null) {
			appConfigsSBean.save(appConfigs);
		} else {
			appConfigs.setId(client.getPreferences().getId());
			appConfigsSBean.change(appConfigs);
		}
		
		client.setPreferences(appConfigs);
		
		clientSBean.change(client);
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

	// Getters and Setters
	public List<Locale> getLocaleList() {
		return localeList;
	}

	public void setLocaleList(List<Locale> localeList) {
		this.localeList = localeList;
	}

	public ExternalContext getExternalContext() {
		return externalContext;
	}

	public void setExternalContext(ExternalContext externalContext) {
		this.externalContext = externalContext;
	}

	public AppConfigs getAppConfigs() {
		return appConfigs;
	}

	public void setAppConfigs(AppConfigs appConfigs) {
		this.appConfigs = appConfigs;
	}

	public ManterClientSBean getClientSBean() {
		return clientSBean;
	}

	public void setClientSBean(ManterClientSBean clientSBean) {
		this.clientSBean = clientSBean;
	}

	public KeepAppConfigs getAppConfigsSBean() {
		return appConfigsSBean;
	}

	public void setAppConfigsSBean(KeepAppConfigs appConfigsSBean) {
		this.appConfigsSBean = appConfigsSBean;
	}

	public HttpServletRequest getHttpServletRequest() {
		return httpServletRequest;
	}

	public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}

	public HttpServletResponse getHttpServletResponse() {
		return httpServletResponse;
	}

	public void setHttpServletResponse(HttpServletResponse httpServletResponse) {
		this.httpServletResponse = httpServletResponse;
	}
}
