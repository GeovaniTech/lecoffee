package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Client;
import utils.RedirectUrl;

/**
 * Servlet Filter implementation class FilterNivelAdmin
 */
public class FilterNivelAdmin implements Filter {

    /**
     * Default constructor. 
     */
    public FilterNivelAdmin() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		HttpSession session = request.getSession(true);
		
		
		Client client = (Client) session.getAttribute("client");
		
		if(client == null || client.getNivel().equals("client")) {
			response.setStatus(401);
			response.sendRedirect("/lecoffee/pages/login.xhtml");
		} else if(client.getNivel().equals("admin")) {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
