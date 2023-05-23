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
	private List<Category> activedCategories;
	private KeepCategorySBean sbean;
	private Category category;	
	private boolean onFilter;
	private boolean tableView;
	
	public MBTab_categories() {
		this.setCategories(new ArrayList<Category>());
		this.setActivedCategories(new ArrayList<Category>());
		this.setSbean(new KeepCategorySBean());
		this.setCategory(new Category());
		this.setOnFilter(false);
		this.setTableView(false);
		
		list();
		listActives();
	}

	public void onReorderList() {
		int id = 1;
		
		for(Category category : this.getActivedCategories()) {
			Category model = this.getSbean().findById(category.getId());
			model.setPriority(id);
			
			this.getSbean().change(model);
			
			id++;
		}
	}
	
	public void listActives() {
		this.setActivedCategories(this.getSbean().listActives());
	}
	
	public void toggleFilter() {
		this.setOnFilter(!this.isOnFilter());
	}
	
	public void changeTableView() {
		this.setTableView(!this.isTableView());
	}
	
	public void save() {
		this.getSbean().save(this.getCategory());
		
		this.setCategory(new Category());
		list();
		listActives();
	}
	
	public void change() {
		this.getSbean().change(this.getCategory());
		
		this.setCategory(new Category());
		list();
		listActives();
	}
	
	public void active() {
		this.getSbean().active(this.getCategory());
		this.setNewCategory();
		
		list();
		listActives();
	}
	
	public void disable() {
		this.getSbean().disable(this.getCategory());
		
		this.setCategory(new Category());
		list();
		listActives();
	}
	
	public void list() {
		this.setCategories(this.getSbean().list());
	}
	
	public void remove() {
		this.getSbean().remove(this.getCategory());
		
		this.setNewCategory();
		list();
		listActives();
	}
	
	public void setNewCategory() {
		this.setCategory(new Category());
	}
	
	//Getters and Setters
	public boolean isOnFilter() {
		return onFilter;
	}
	public void setOnFilter(boolean onFilter) {
		this.onFilter = onFilter;
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
	public boolean isTableView() {
		return tableView;
	}
	public void setTableView(boolean tableView) {
		this.tableView = tableView;
	}
	public List<Category> getActivedCategories() {
		return activedCategories;
	}
	public void setActivedCategories(List<Category> activedCategories) {
		this.activedCategories = activedCategories;
	}
}
