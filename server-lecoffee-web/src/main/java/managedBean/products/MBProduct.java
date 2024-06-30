package managedBean.products;

import java.util.List;

import org.primefaces.event.SelectEvent;

import abstracts.AbstractFilterMBean;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import keep.category.IKeepCategorySBean;
import keep.product.IKeepProductSBean;
import to.category.TOCategory;
import to.product.TOFilterProduct;
import to.product.TOProduct;

@Named(MBProduct.MANAGED_BEAN_NAME)
@ViewScoped
public class MBProduct extends AbstractFilterMBean<TOProduct, TOFilterProduct> {

	private static final long serialVersionUID = 8232940223702944364L;
	public static final String MANAGED_BEAN_NAME = "MBProduct";

	private TOProduct product;
	private List<TOCategory> categories;
	
	@EJB
	private IKeepProductSBean productSBean;
	
	@EJB
	private IKeepCategorySBean categorySBean;

	@PostConstruct
	@Override
	public void init() {
		this.setCategories(this.getCategorySBean().searchAllCategories());
		this.clearFilters();
		this.searchResults();
	}
	
	public void initProduct() { 
		this.getMBProductInfo().initProduct();
	}

	@Override
	public List<TOProduct> getData(TOFilterProduct filter) {
		return this.getProductSBean().searchProducts(filter);
	}

	@Override
	public Integer getCount(TOFilterProduct filter) {
		return this.getProductSBean().getCount(filter);
	}

	@Override
	public void clearFilters() {
		this.setFilter(new TOFilterProduct());
	}

	@Override
	public void onRowSelect(SelectEvent<TOProduct> event) {
		this.setProduct(event.getObject());
		this.getMBProductInfo().editProduct(this.getProduct());
	}
	
	public MBProductInfo getMBProductInfo() { 
		return this.getMBean(MBProductInfo.MANAGED_BEAN_NAME);
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

	public List<TOCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<TOCategory> categories) {
		this.categories = categories;
	}

	public IKeepCategorySBean getCategorySBean() {
		return categorySBean;
	}

	public void setCategorySBean(IKeepCategorySBean categorySBean) {
		this.categorySBean = categorySBean;
	}
}
