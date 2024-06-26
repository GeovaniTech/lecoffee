package to.product;

import abstracts.AbstractTOObject;
import to.category.TOCategory;

public class TOProduct extends AbstractTOObject {

	private static final long serialVersionUID = -8052801068640424625L;

	private String name;
	private String description;
	private Double price;
	private Integer rating;
	private TOCategory category;
	private byte[] imageBytes;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	public TOCategory getCategory() {
		return category;
	}
	public void setCategory(TOCategory category) {
		this.category = category;
	}
	public byte[] getImageBytes() {
		return imageBytes;
	}
	public void setImageBytes(byte[] imageBytes) {
		this.imageBytes = imageBytes;
	}
}
