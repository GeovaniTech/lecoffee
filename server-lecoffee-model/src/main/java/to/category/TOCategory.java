package to.category;

import abstracts.AbstractTOObject;

public class TOCategory extends AbstractTOObject {

	private static final long serialVersionUID = 4388002215977916293L;
	
	private String id;
	private String name;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
