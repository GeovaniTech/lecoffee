package managedBean.products;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;

import manter.product.KeepProductSBean;
import model.File;
import model.Product;
import utils.AbstractBean;
import utils.FileUtil;

@Named("MBTab_products")
@ViewScoped
public class MBTab_products extends AbstractBean {
	private static final long serialVersionUID = 9160760899720982450L;
	
	private Product product;
	private KeepProductSBean sBean;
	private List<Product> products;
	private boolean status;
	
	public MBTab_products() {
		this.status = true;
		product = new Product();
		sBean = new KeepProductSBean();
		products = new ArrayList<Product>();
		list();
	}
	
	public void save() {
		product.setStatus(this.isStatus() ? "active" : "disable");
		sBean.save(product);
		product = new Product();
		
		list();
	}

	public void change() {
		sBean.change(product);
		product = new  Product();
		
		list();
	}
	
	public void removeAll() {
		sBean.removeAll();
		
		list();
	}
	
	public void disable(Product product) {
		sBean.disable(product);
		
		list();
	}
	
	public void list() {
		this.setProducts(sBean.list());
	}
	
	public void addImage(FileUploadEvent event) throws IOException {
		File file = FileUtil.convertPrimefacesFile(event.getFile());
		
		product.setImageBytes(file.getBytes());
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
