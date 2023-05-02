package to;

import java.io.Serializable;

public class TOCategory implements Serializable {
	private static final long serialVersionUID = 7557207866744043040L;
	
	private int id;
	private String name;
	private String status;
	
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
}
