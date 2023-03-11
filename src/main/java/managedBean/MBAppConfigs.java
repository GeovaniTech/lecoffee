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
	private KeepAppConfigs appConfigsSBean;
	
	public MBAppConfigs() {
		this.appConfigs = new AppConfigs();
		this.localeList = new ArrayList<Locale>();
		this.clientSBean = new KeepClientSBean();
		this.appConfigsSBean = new KeepAppConfigs();
		
		localeList.add(new Locale("pt"));
		localeList.add(new Locale("en"));
		
		updateConfigs();
		redirectUserFromCookie();
	}

	public void updateConfigs() {
		
	}
	
	public void logout() {
		createCookiePreferences();
		
		Cookie userSession = new Cookie("userSession", "");
		userSession.setMaxAge(60*60*24*30);
		userSession.setPath("/");
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		response.addCookie(userSession);
		
		RedirectUrl.redirecionarPara("/lecoffee/pages/login.xhtml");
		
		getSession().invalidate();
	}
	
	public void redirectUserFromCookie() {
		String user = Cookies.getUserCookie();
		
		if(user != null) {
			TOClient toClient = clientSBean.findByEmail(Encryption.decryptNormalText(user));
			
			getSession().setAttribute("client", toClient);
			
			if(toClient.getNivel().equals("admin")) {
				RedirectUrl.redirecionarPara("/lecoffee/pages/admin/pedidos.xhtml");
			} else {
				RedirectUrl.redirecionarPara("/lecoffee/pages/client/home.xhtml");
			}
		}
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

	public KeepAppConfigs getAppConfigsSBean() {
		return appConfigsSBean;
	}

	public void setAppConfigsSBean(KeepAppConfigs appConfigsSBean) {
		this.appConfigsSBean = appConfigsSBean;
	}
}
