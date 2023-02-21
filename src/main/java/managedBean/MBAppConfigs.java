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
import javax.servlet.http.HttpServletRequest;

import utils.ImageUtil;

@Named("MBAppConfigs")
@SessionScoped
public class MBAppConfigs implements Serializable {
	private static final long serialVersionUID = -2862315075236619884L;
	
	private boolean darkMode = false;
	private List<Locale> localeList;
	private String language;
	
	private ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

	public MBAppConfigs() {
		this.localeList = new ArrayList<Locale>();
		localeList.add(new Locale("pt"));
		localeList.add(new Locale("en"));
		
		language = Locale.getDefault().getLanguage();
		
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
}
