package to;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

import converter.BaseEntity;

public class TOBanner implements Serializable, BaseEntity {
	private static final long serialVersionUID = -6242749673748442713L;
	
	private int id;
	private String name;
	private String status;
	private Integer priority;
	private byte[] bytes;
	
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
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	@Override
	public Long getIdBase() {
		return (long) this.getId();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(bytes);
		result = prime * result + Objects.hash(id, name, priority, status);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TOBanner other = (TOBanner) obj;
		return Arrays.equals(bytes, other.bytes) && id == other.id && Objects.equals(name, other.name)
				&& Objects.equals(priority, other.priority) && Objects.equals(status, other.status);
	}
}
