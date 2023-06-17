package utils;

import java.io.Serializable;

public class AbstractFilterBean extends LeCoffeeSession implements Serializable {
	private static final long serialVersionUID = 8971275979039568812L;
	
	private boolean toggleFilter = false;
	private boolean editObject =  false;

	public void changeToggleFilter() {
		this.setToggleFilter(!this.isToggleFilter());
	}

	public void changeEditObject(boolean value) {
		this.setEditObject(value);
	}
	
	//Getters and Setters
	public boolean isToggleFilter() {
		return toggleFilter;
	}
	public void setToggleFilter(boolean toggleFilter) {
		this.toggleFilter = toggleFilter;
	}
	public boolean isEditObject() {
		return editObject;
	}
	public void setEditObject(boolean editObject) {
		this.editObject = editObject;
	}
}
