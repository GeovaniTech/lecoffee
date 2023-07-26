package to;

import java.io.Serializable;
import java.util.Objects;

import converter.BaseEntity;
import utils.AbstractObject;

public class TOPayment extends AbstractObject implements Serializable, BaseEntity {
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
	
	@Override
	public Long getIdBase() {
		return (long) this.getId();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(icon, id, name);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TOPayment other = (TOPayment) obj;
		return Objects.equals(icon, other.icon) && id == other.id && Objects.equals(name, other.name);
	}
}
