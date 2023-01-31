package managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("MBAppConfigs")
@SessionScoped
public class MBAppConfigs implements Serializable {
	private static final long serialVersionUID = -2862315075236619884L;
	
	private boolean darkMode = false;
	private List<Locale> localeList;
	private String language;

	public MBAppConfigs() {
		this.localeList = new ArrayList<Locale>();
		localeList.add(new Locale("pt"));
		localeList.add(new Locale("en"));
		
		language = Locale.getDefault().getLanguage();
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
}
