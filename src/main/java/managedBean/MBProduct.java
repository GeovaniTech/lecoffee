package managedBean;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import keep.product.KeepProductSBean;
import to.TOProduct;
import utils.AbstractBean;

@Named("MBProduct")
@ViewScoped
public class MBProduct extends AbstractBean {
	private static final long serialVersionUID = -7828714455689754401L;
	
	private KeepProductSBean productSBean;
	private TOProduct product;

	public MBProduct() {
		this.setProduct(new TOProduct());
		this.setProductSBean(new KeepProductSBean());
	}
	
	public void selectProductById(int id) {
		this.setProduct(this.getProductSBean().findByIdTO(id));
	}
	
	// Getters and Setters
	public TOProduct getProduct() {
		return product;
	}

	public void setProduct(TOProduct product) {
		this.product = product;
	}

	public KeepProductSBean getProductSBean() {
		return productSBean;
	}

	public void setProductSBean(KeepProductSBean productSBean) {
		this.productSBean = productSBean;
	}
}
