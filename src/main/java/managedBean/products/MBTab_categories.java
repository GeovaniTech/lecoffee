package managedBean.products;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import keep.category.KeepCategorySBean;
import to.TOCategory;
import utils.AbstractBean;
import utils.MessageUtil;

@Named("MBTab_category")
@ViewScoped
public class MBTab_categories extends AbstractBean {
	private static final long serialVersionUID = 2137478986375484379L;
	
	private List<TOCategory> categories;
	private List<TOCategory> activedCategories;
	private KeepCategorySBean sbean;
	private TOCategory category;	
	private boolean onFilter;
	private boolean tableView;
	
	public MBTab_categories() {
		this.setCategories(new ArrayList<TOCategory>());
		this.setActivedCategories(new ArrayList<TOCategory>());
		this.setSbean(new KeepCategorySBean());
		this.setCategory(new TOCategory());
		this.setOnFilter(false);
		this.setTableView(false);
		
		list();
		listActives();
	}

	public void onReorderList() {
		int id = 1;
		
		for(TOCategory category : this.getActivedCategories()) {
			category.setPriority(id);
			
			this.getSbean().change(category);
		
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
		
		this.setCategory(new TOCategory());
		list();
		listActives();
		
		MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("record_successfully_saved"), null, FacesMessage.SEVERITY_INFO);
	}
	
	public void change() {
		this.getSbean().change(this.getCategory());
		
		this.setCategory(new TOCategory());
		list();
		listActives();
		
		MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("record_changed_successfully"), null, FacesMessage.SEVERITY_INFO);
	}
	
	public void active() {
		this.getSbean().active(this.getCategory());
		this.setNewCategory();
		
		list();
		listActives();
		
		MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("registration_activated_successfully"), null, FacesMessage.SEVERITY_INFO);
	}
	
	public void disable() {
		this.getSbean().disable(this.getCategory());
		
		this.setCategory(new TOCategory());
		list();
		listActives();
		
		MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("successfully_deactivated_registration"), null, FacesMessage.SEVERITY_INFO);
	}
	
	public void list() {
		this.setCategories(this.getSbean().list());
	}
	
	public void remove() {
		this.getSbean().remove(this.getCategory());
		
		this.setNewCategory();
		list();
		listActives();
		
		MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("successfully_deleted_record"), null, FacesMessage.SEVERITY_INFO);
	}
	
	public void setNewCategory() {
		this.setCategory(new TOCategory());
	}
	
	//Getters and Setters
	public boolean isOnFilter() {
		return onFilter;
	}
	public void setOnFilter(boolean onFilter) {
		this.onFilter = onFilter;
	}
	public KeepCategorySBean getSbean() {
		return sbean;
	}
	public void setSbean(KeepCategorySBean sbean) {
		this.sbean = sbean;
	}
	public TOCategory getCategory() {
		return category;
	}
	public void setCategory(TOCategory category) {
		this.category = category;
	}
	public boolean isTableView() {
		return tableView;
	}
	public void setTableView(boolean tableView) {
		this.tableView = tableView;
	}
	public List<TOCategory> getCategories() {
		return categories;
	}
	public void setCategories(List<TOCategory> categories) {
		this.categories = categories;
	}
	public List<TOCategory> getActivedCategories() {
		return activedCategories;
	}
	public void setActivedCategories(List<TOCategory> activedCategories) {
		this.activedCategories = activedCategories;
	}
}
