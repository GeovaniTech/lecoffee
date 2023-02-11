package managedBean.products;

import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import manter.product.KeepProductSBean;
import model.Product;
import utils.AbstractBean;

@Named("MBTab_products")
@ViewScoped
public class MBTab_products extends AbstractBean {
	private static final long serialVersionUID = 9160760899720982450L;
	
	private Product product;
	private KeepProductSBean sBean;
	private List<Product> products;
	
	public MBTab_products() {
		product = new Product();
		sBean = new KeepProductSBean();
		products = new ArrayList<Product>();
	}
	
	public void save() {
		sBean.save(product);
	}

	public void change() {
		sBean.change(product);
	}
	
	public void disable() {
		sBean.disable(product);
	}
	
	public void list() {
		
	}
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public KeepProductSBean getsBean() {
		return sBean;
	}

	public void setsBean(KeepProductSBean sBean) {
		this.sBean = sBean;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
}
