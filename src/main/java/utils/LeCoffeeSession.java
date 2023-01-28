package utils;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class LeCoffeeSession {
	protected HttpSession getSession() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		
		if(session == null) {
			session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		}
		
		return session;
	}
}
