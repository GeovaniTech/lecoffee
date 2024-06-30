package to.product;

import abstracts.AbstractTOFilter;
import to.TOInputFilter;
import to.TOInputNumberFilter;

public class TOFilterProduct extends AbstractTOFilter {

	private static final long serialVersionUID = 4182325692279252803L;
	
	private TOInputFilter name;
	private TOInputFilter description;
	private TOInputNumberFilter price;
	private String idCategory;

	public TOFilterProduct() { 
		this.setName(new TOInputFilter());
		this.setDescription(new TOInputFilter());
		this.setPrice(new TOInputNumberFilter());
	}
	
	public TOInputFilter getName() {
		return name;
	}

	public void setName(TOInputFilter name) {
		this.name = name;
	}

	public TOInputFilter getDescription() {
		return description;
	}

	public void setDescription(TOInputFilter description) {
		this.description = description;
	}

	public TOInputNumberFilter getPrice() {
		return price;
	}

	public void setPrice(TOInputNumberFilter price) {
		this.price = price;
	}

	public String getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(String idCategory) {
		this.idCategory = idCategory;
	}
	
}
