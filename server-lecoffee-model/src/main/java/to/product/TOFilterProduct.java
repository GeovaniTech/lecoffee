package to.product;

import abstracts.AbstractTOFilter;
import to.TOInputFilter;

public class TOFilterProduct extends AbstractTOFilter {

	private static final long serialVersionUID = 4182325692279252803L;
	
	private TOInputFilter name;

	public TOFilterProduct() { 
		this.setName(new TOInputFilter());
	}
	
	public TOInputFilter getName() {
		return name;
	}

	public void setName(TOInputFilter name) {
		this.name = name;
	}
}
