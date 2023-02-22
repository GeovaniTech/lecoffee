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

	private ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

	public MBAppConfigs() {
		this.appConfigs = new AppConfigs();
		this.localeList = new ArrayList<Locale>();
		localeList.add(new Locale("pt"));
		localeList.add(new Locale("en"));
		
		updateConfigs();
	}

	public void updateConfigs() {
		TOClient client = getClient();

		if (client == null) {
			appConfigs.setDarkMode(false);
			appConfigs.setLanguage(Locale.getDefault().getLanguage());
		} else if (client.getPreferences().getLanguage() != null) {
			appConfigs.setLanguage(client.getPreferences().getLanguage());
		} else {
			appConfigs.setDarkMode(client.getPreferences().isDarkMode());
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
}
