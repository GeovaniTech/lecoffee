package managedBean.products;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;

import keep.product.KeepProductSBean;
import model.File;
import model.Product;
import to.TOProductFilter;
import utils.AbstractFilterBean;
import utils.FileUtil;

@Named("MBTab_products")
@ViewScoped
public class MBTab_products extends AbstractFilterBean {
	private static final long serialVersionUID = 9160760899720982450L;
	
	private Product product;
	private TOProductFilter productFilter;
	private KeepProductSBean sBean;
	private List<Product> products;
	private boolean status;
	
	private boolean tableView;
	
	public MBTab_products() {
		this.status = true;
		this.setToggleFilter(false);
		this.setNewProduct();
		sBean = new KeepProductSBean();
		products = new ArrayList<Product>();
		list();
	}
	
	public void save() {
		this.getProduct().setStatus(this.isStatus() ? "active" : "disable");
		sBean.save(this.getProduct());
		
		this.setNewProduct();
		
		list();
	}

	public void change() {
		this.getProduct().setStatus(this.isStatus() ? "active" : "disable");
		sBean.change(this.getProduct());
		
		this.setNewProduct();
		
		list();
	}
	
	public void openChangeDialog(Product product) {
		this.setProduct(product);
		
		PrimeFaces.current().executeScript("PF('dialog-change-product').show()");
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
	
	public void changeTableView() {
		this.tableView = !this.tableView;
	}
	
	public void addImage(FileUploadEvent event) throws IOException {
		File file = FileUtil.convertPrimefacesFile(event.getFile());
		
		this.getProduct().setImageBytes(file.getBytes());
	}
	
	public void setNewProduct() {
		this.setProduct(new Product());
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

	public TOProductFilter getProductFilter() {
		return productFilter;
	}

	public void setProductFilter(TOProductFilter productFilter) {
		this.productFilter = productFilter;
	}

	public boolean isTableView() {
		return tableView;
	}

	public void setTableView(boolean tableView) {
		this.tableView = tableView;
	}	
}
