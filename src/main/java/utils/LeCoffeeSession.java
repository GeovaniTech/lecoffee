package utils;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import to.TOClient;

public class LeCoffeeSession {
	protected HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	protected HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	
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
	
	protected void finishSession() {
		getSession().setAttribute("client", null);
		getSession().invalidate();
	}
	
	//Setters and Getters
	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}
}
