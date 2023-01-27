package managedBean;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("MBDarkMode")
@SessionScoped
public class MBDarkMode implements Serializable {
	private static final long serialVersionUID = -2862315075236619884L;
	
	private String darkMode = "true";

	public String getDarkMode() {
		return darkMode;
	}

	public void setDarkMode(String darkMode) {
		this.darkMode = darkMode;
	}
}
