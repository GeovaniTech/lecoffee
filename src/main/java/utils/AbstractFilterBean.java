package utils;

import java.io.Serializable;

public class AbstractFilterBean extends LeCoffeeSession implements Serializable, Message {
	private static final long serialVersionUID = 8971275979039568812L;
	
	private boolean toggleFilter = false;

	public void changeToggleFilter() {
		this.setToggleFilter(!toggleFilter);
	}
	
	public boolean isToggleFilter() {
		return toggleFilter;
	}

	public void setToggleFilter(boolean toggleFilter) {
		this.toggleFilter = toggleFilter;
	}
}
