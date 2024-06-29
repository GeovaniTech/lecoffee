package to.payment;

import abstracts.AbstractTOObject;

public class TOPayment extends AbstractTOObject {

	private static final long serialVersionUID = 8157737976710047562L;

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
