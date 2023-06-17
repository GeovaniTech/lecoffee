package to;

import java.io.Serializable;

import utils.AbstractObject;

public class TOPayment extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 5845051535888151461L;
	
	private int id;
	private String name;
	private String icon;
	
	//Getters and Setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
}
