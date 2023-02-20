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
	
	private List<Category> categories;
	private KeepCategorySBean sbean;
	private Category category;
	
	public MBTab_categories() {
		categories = new ArrayList<Category>();
		sbean = new KeepCategorySBean();
		category = new Category();
		
		list();
	}
	
	public void save() {
		sbean.save(this.getCategory());
		
		this.setCategory(new Category());
		list();
	}
	
	public void change() {
		sbean.change(this.getCategory());
		
		this.setCategory(new Category());
		list();
	}
	
	public void disable() {
		sbean.disable(this.getCategory());
		
		this.setCategory(new Category());
		list();
	}
	
	public void list() {
		this.setCategories(sbean.list());
	}
	
	public void removeAll() {
		sbean.removeAll();
		list();
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
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
