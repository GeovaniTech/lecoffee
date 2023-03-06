package utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class Cookies extends LeCoffeeSession {	
	public String getUserCookie() {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("userSession")) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
	
	//Getters and setters
	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
}
