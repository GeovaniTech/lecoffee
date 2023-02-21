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

import utils.ImageUtil;

@Named("MBAppConfigs")
@SessionScoped
public class MBAppConfigs implements Serializable {
	private static final long serialVersionUID = -2862315075236619884L;
	
	private boolean darkMode = false;
	private List<Locale> localeList;
	private String language;
	
	private ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	private HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
	private HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

	public MBAppConfigs() {
		this.localeList = new ArrayList<Locale>();
		localeList.add(new Locale("pt"));
		localeList.add(new Locale("en"));
		
		language = Locale.getDefault().getLanguage();
		
		String cookieDark = getCookieDarkMode();
		
		if(cookieDark != null && cookieDark.equals("false")) {
			darkMode = false;
		} else if (cookieDark != null) { 
			darkMode = true;
		} else {
			Cookie cookieDarkMode = new Cookie("darkMode", "false");
			cookieDarkMode.setMaxAge(Integer.MAX_VALUE);
			response.addCookie(cookieDarkMode);
		}
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
	
	public String getCookieDarkMode() {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
		    for (Cookie cookie : cookies) {
		        if (cookie.getName().equals("darkMode")) {
		            return cookie.getValue();
		        } 
		    }
		}
		
		return null;
	}
	
	public boolean changeCookieDarkMode() {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
		    for (Cookie cookie : cookies) {
		        if (cookie.getName().equals("darkMode")) {
		            cookie.setValue("" + darkMode); // atualiza o valor do cookie
		            cookie.setMaxAge(Integer.MAX_VALUE);
		            response.addCookie(cookie); // envia o cookie de volta para o navegador
		            
		            return true;
		        }
		    }
		}
		
		return false;
	}
	
	public boolean isDarkMode() {
		return darkMode;
	}

	public void setDarkMode(boolean darkMode) {
		this.darkMode = darkMode;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

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

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
}
