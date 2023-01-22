package utils;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class LeCoffeeSession {
	protected HttpSession getSession() {
		return(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}
}
