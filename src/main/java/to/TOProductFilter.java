package to;

public class TOProductFilter {
	private TOInputFilter name;
	private TOInputFilter description;
	private TOInputNumberFilter price;
	
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
}
