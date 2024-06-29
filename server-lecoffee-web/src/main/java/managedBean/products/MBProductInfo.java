package managedBean.products;

import java.util.Date;

import org.primefaces.PrimeFaces;

import abstracts.AbstractMBean;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import keep.product.IKeepProductSBean;
import to.product.TOProduct;

@Named(MBProductInfo.MANAGED_BEAN_NAME)
@ViewScoped
public class MBProductInfo extends AbstractMBean {

	private static final long serialVersionUID = -209387974542831500L;
	public static final String MANAGED_BEAN_NAME = "MBProductInfo";
	
	private TOProduct product;
	private boolean editing;
	
	@EJB
	private IKeepProductSBean productSBean;
	
	public MBProductInfo() {
		this.initProduct();
	}
	
	public void save() {
		try {
			this.getProduct().setCreationDate(new Date());
			this.getProduct().setCreationUser(this.getClientSession().getEmail());
			
			this.getProductSBean().save(this.getProduct());
			this.setEditing(true);
			showMessageItemSaved(this.getProduct().getName());
		} catch (Exception e) {
			showMessageError(e);
		}
	}
	
	public void change() {
		try {
			this.getProduct().setChangeDate(new Date());
			this.getProduct().setChangeUser(this.getClientSession().getEmail());

			this.getProductSBean().save(this.getProduct());
			showMessageItemChanged(this.getProduct().getName());
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
		
		this.updateFormProduct();
	}
	
	public void editProduct(TOProduct product) {
		this.setEditing(true);
		this.setProduct(product);
		
		this.updateFormProduct();
	}
	
	public void updateFormProduct() {
		PrimeFaces.current().ajax().update("tabview_products:dialogProductInfo:formProductInfo");
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
}
