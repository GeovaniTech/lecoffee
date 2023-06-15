package managedBean.products;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;

import keep.category.KeepCategorySBean;
import keep.product.KeepProductSBean;
import model.Category;
import model.File;
import model.Product;
import to.TOCategory;
import to.TOProductFilter;
import utils.AbstractFilterBean;
import utils.FileUtil;
import utils.MessageUtil;

@Named("MBTab_products")
@ViewScoped
public class MBTab_products extends AbstractFilterBean {
	private static final long serialVersionUID = 9160760899720982450L;
	
	private Product product;
	private TOProductFilter productFilter;
	private KeepProductSBean sBean;
	private List<Product> products;
	private boolean status;
	
	private List<TOCategory> categoriesFilter;
	private KeepCategorySBean sBeanCategory;
	
	// Used when the user change product's category
	private Integer categoryId;
		
	private boolean tableView;
	
	public MBTab_products() {
		this.setStatus(true);
		this.setToggleFilter(false);
		this.setNewProduct();
		
		this.setsBean(new KeepProductSBean()); 
		this.setProducts(new ArrayList<Product>());
		this.setCategoriesFilter(new ArrayList<TOCategory>());
		this.setsBeanCategory(new KeepCategorySBean());
		this.setProductFilter(new TOProductFilter());
		
		list();
		initFilters();
	}
	
	public List<Product> getProductsByCategoryId(int id) {
		return this.getsBean().getProductsByCategoryId(id);
	}
	
	public void initFilters() {
		this.setCategoriesFilter(this.getsBeanCategory().listActives());
		
		if(this.getProduct().getCategory()!= null) {
			this.setCategoryId(this.getProduct().getCategory().getId());
		}
	}  
	
	public void save() {
		if(this.getProduct().getImageBytes() != null) {
			this.getProduct().setStatus(this.isStatus() ? "active" : "disable");
			this.getProduct().setCategory(this.getsBeanCategory().findById(this.getCategoryId()));
			this.getsBean().save(this.getProduct());
			
			this.setNewProduct();
			this.setCategoryId(null);
			
			list();			
			
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("record_successfully_saved"), null, FacesMessage.SEVERITY_INFO);
		} else {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("select_image_required"), null, FacesMessage.SEVERITY_WARN);
		}
	}

	public void change() {
		if(this.getProduct().getImageBytes() != null) {
			this.getProduct().setStatus(this.isStatus() ? "active" : "disable");
			
			if(this.getCategoryId() != null) {
				Category category = this.getsBeanCategory().findById(this.getCategoryId());
				
				this.getProduct().setCategory(category);
			}
			
			this.getsBean().change(this.getProduct());
			
			this.setNewProduct();
			
			list();		
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("record_changed_successfully"), null, FacesMessage.SEVERITY_INFO);
		} else {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("select_image_required"), null, FacesMessage.SEVERITY_WARN);
		}
	}
	
	public void active() {
		this.getProduct().setStatus("active");
		change();
	}	
	
	public void openChangeDialog(Product product) {
		this.setProduct(product);
		
		PrimeFaces.current().executeScript("PF('dialog-change-product').show()");
	}
	
	public void disable() {
		this.getsBean().disable(this.getProduct());
		
		list();
	}
	
	public void disable(Product product) {
		this.getsBean().disable(product);
		
		list();
	}
	
	public void removeProduct() {
		this.getsBean().remove(this.getProduct());
		list();
		
		MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("successfully_deleted_record"), null, FacesMessage.SEVERITY_INFO);
	}	
	
	public void list() {
		this.setProducts(this.getsBean().list());
	}
	
	public void changeTableView() {
		this.setTableView(!this.isTableView());
	}
	
	public void filterProducts() {
		this.setProducts(this.getsBean().filterProducts(this.getProductFilter()));
	}
	
	public void cleanFilters() {
		this.setProductFilter(new TOProductFilter());
		this.list();
	}
	
	public void addImage(FileUploadEvent event) throws IOException {
		File file = FileUtil.convertPrimefacesFile(event.getFile());
		
		this.getProduct().setImageBytes(file.getBytes());
	}
	
	public void setNewProduct() {
		this.setProduct(new Product());
	}
	
	//Getters and Setters
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
	public List<TOCategory> getCategoriesFilter() {
		return categoriesFilter;
	}
	public void setCategoriesFilter(List<TOCategory> categoriesFilter) {
		this.categoriesFilter = categoriesFilter;
	}
	public KeepCategorySBean getsBeanCategory() {
		return sBeanCategory;
	}
	public void setsBeanCategory(KeepCategorySBean sBeanCategory) {
		this.sBeanCategory = sBeanCategory;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}	
}
