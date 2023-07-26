package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private boolean finished;
	private Date creationDate;
	private Date completionDate;
	private Double total; 
	
	@OneToMany(cascade = CascadeType.REMOVE)
	@Column(unique = false)
	private List<Item> items;
	
	public Cart() {
		this.setItems(new ArrayList<Item>());
	}
	
	public int getTotalQuantityOfProdctsFromCart() {
		int total = 0;
		
		for(Item item : this.getItems()) {
			total += item.getAmount();
		}
		
		return total;
	}
	
	public Integer getQuantityOfProduct(int id) {
		for(Item item : this.getItems()) {
			if(item.getProduct().getId() == id) {
				return item.getAmount();
			}
		}
		
		return 0;
	}
	
	// Getters and Setters
	public int getId() {	
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	public boolean isFinished() {
		return finished;
	}
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getCompletionDate() {
		return completionDate;
	}
	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
}
