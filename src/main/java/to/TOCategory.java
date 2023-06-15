package to;

import java.io.Serializable;
import java.util.Objects;

import converter.BaseEntity;

public class TOCategory implements Serializable, BaseEntity {
	private static final long serialVersionUID = 7557207866744043040L;
	
	private int id;
	private String name;
	private String status;
	private String icon;
	private Integer priority;
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	@Override
	public Long getIdBase() {
		return (long) this.getId();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(icon, id, name, priority, status);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TOCategory other = (TOCategory) obj;
		return Objects.equals(icon, other.icon) && id == other.id && Objects.equals(name, other.name)
				&& Objects.equals(priority, other.priority) && Objects.equals(status, other.status);
	}
}
