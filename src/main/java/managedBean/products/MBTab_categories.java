package managedBean.products;

import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import keep.category.KeepCategorySBean;
import model.Category;
import utils.AbstractBean;

@Named("MBTab_category")
@ViewScoped
public class MBTab_categories extends AbstractBean {
	private static final long serialVersionUID = 2137478986375484379L;
	
	private List<Category> cagories;
	private KeepCategorySBean sbean;
	private Category category;
	
	public MBTab_categories() {
		cagories = new ArrayList<Category>();
		category = new Category();
	}
	
	public List<Category> getCagories() {
		return cagories;
	}
	public void setCagories(List<Category> cagories) {
		this.cagories = cagories;
	}
	public KeepCategorySBean getSbean() {
		return sbean;
	}
	public void setSbean(KeepCategorySBean sbean) {
		this.sbean = sbean;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
}
