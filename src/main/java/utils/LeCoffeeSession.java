package utils;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import to.TOClient;

public class LeCoffeeSession {
	protected HttpSession getSession() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		
		if(session == null) {
			session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		}
		
		return session;
	}
	
	protected TOClient getClient() {
		return (TOClient) getSession().getAttribute("client");
	}
}
