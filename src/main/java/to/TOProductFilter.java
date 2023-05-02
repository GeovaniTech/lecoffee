package to;

import java.io.Serializable;

public class TOProductFilter implements Serializable {
	private static final long serialVersionUID = 1004274014603699692L;
	
	private TOInputFilter name;
	private TOInputFilter description;
	private TOInputNumberFilter price;
	private TOInputNumberFilter rating;
	private TODateRangeFilter dateCreation;
	private int category_id;

	//Getters and Setters
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

	public TODateRangeFilter getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(TODateRangeFilter dateCreation) {
		this.dateCreation = dateCreation;
	}
	
	public TOInputNumberFilter getRating() {
		return rating;
	}

	public void setRating(TOInputNumberFilter rating) {
		this.rating = rating;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}
}
