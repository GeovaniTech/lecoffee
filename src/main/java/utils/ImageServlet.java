package utils;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manter.product.KeepProductSBean;
import model.Product;

@WebServlet("/imageServlet")
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public ImageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		KeepProductSBean sBeanProduct = new KeepProductSBean();

	    String paramName = "objId_" + request.getParameter("objId");
	    int productId = Integer.parseInt(request.getParameter(paramName));
		
	    Product product = sBeanProduct.findById(productId);
	    
		byte[] bytes = product.getImageBytes(); // seus bytes da imagem aqui
        
		System.out.println("HERE");
		
		response.setContentType("image/png"); // defina o tipo MIME da imagem aqui
        response.getOutputStream().write(bytes);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
