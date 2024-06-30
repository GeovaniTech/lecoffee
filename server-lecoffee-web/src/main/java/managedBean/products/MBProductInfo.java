package managedBean.products;

import java.io.IOException;
import java.util.List;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;

import abstracts.AbstractMBean;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import keep.category.IKeepCategorySBean;
import keep.product.IKeepProductSBean;
import to.category.TOCategory;
import to.product.TOProduct;
import utils.ImageUtil;
import utils.StringUtil;

@Named(MBProductInfo.MANAGED_BEAN_NAME)
@ViewScoped
public class MBProductInfo extends AbstractMBean {

	private static final long serialVersionUID = -209387974542831500L;
	public static final String MANAGED_BEAN_NAME = "MBProductInfo";
	
	private TOProduct product;
	private String idCategory;
	private boolean editing;
	private List<TOCategory> categories;
	
	@EJB
	private IKeepProductSBean productSBean;
	
	@EJB
	private IKeepCategorySBean categorySBean;
	
	@PostConstruct
	public void init() { 
		this.initProduct();
		this.setCategories(this.getCategorySBean().searchAllCategories());
	}
	
	public void save() {
		try {
			if (this.getProduct().getImageBytes() == null) {
				this.addMessage("select_image_required", FacesMessage.SEVERITY_ERROR);
				return;
			}
			
			if(StringUtil.isNotNull(this.getIdCategory())) {
				this.getProduct().setCategory(this.getCategorySBean().findById(this.getIdCategory()));
			}
			
			this.getProductSBean().save(this.getProduct());
			this.setEditing(true);
			showMessageItemSaved(this.getProduct().getName());
		} catch (Exception e) {
			showMessageError(e);
		}
	}
	
	public void updateStatus() {
		try {
			if (this.getProduct().getInactivationDate() == null) {
				this.getProductSBean().disable(this.getProduct());
				showMessageItemDisabled(this.getProduct().getName());
				return;
			} 
			
			this.getProductSBean().active(this.getProduct());
			showMessageItemActived(this.getProduct().getName());
		} catch (Exception e) {
			showMessageError(e);
		}
	}
	
	
	public void remove() {
		try {
			this.getProductSBean().remove(this.getProduct());
			showMessageItemRemoved(this.getProduct().getName());
			
			this.initProduct();
		} catch (Exception e) {
			showMessageError(e);
		}
	}
	
	public void initProduct() {
		this.setEditing(false);
		this.setProduct(new TOProduct());
		this.setIdCategory(null);
		
		this.updateFormProduct();
	}
	
	public void editProduct(TOProduct product) {
		this.setEditing(true);
		this.setProduct(product);
		this.setIdCategory(product.getCategory().getId());
		
		this.updateFormProduct();
	}
	
	public void updateFormProduct() {
		PrimeFaces.current().ajax().update("tabview_products:dialogProductInfo:form-product-info");
	}
	
    public void handleFileUpload(FileUploadEvent event) {
    	try {
			this.getProduct().setImageBytes(ImageUtil.getBytesFromInputStream(event.getFile().getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	public boolean isEditing() {
		return editing;
	}

	public void setEditing(boolean editing) {
		this.editing = editing;
	}

	public IKeepProductSBean getProductSBean() {
		return productSBean;
	}

	public void setProductSBean(IKeepProductSBean productSBean) {
		this.productSBean = productSBean;
	}

	public TOProduct getProduct() {
		return product;
	}

	public void setProduct(TOProduct product) {
		this.product = product;
	}

	public IKeepCategorySBean getCategorySBean() {
		return categorySBean;
	}

	public void setCategorySBean(IKeepCategorySBean categorySBean) {
		this.categorySBean = categorySBean;
	}

	public List<TOCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<TOCategory> categories) {
		this.categories = categories;
	}

	public String getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(String idCategory) {
		this.idCategory = idCategory;
	}
}
