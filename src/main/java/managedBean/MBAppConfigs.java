package managedBean;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("MBAppConfigs")
@SessionScoped
public class MBAppConfigs implements Serializable {
	private static final long serialVersionUID = -2862315075236619884L;
	
	private boolean darkMode = false;

	public boolean isDarkMode() {
		return darkMode;
	}

	public void setDarkMode(boolean darkMode) {
		this.darkMode = darkMode;
	}
}
