package to;

import java.io.Serializable;
import java.util.Date;

import model.Address;
import model.Cart;
import model.Payment;

public class TOClientOrder implements Serializable {
	private static final long serialVersionUID = -2652741365794788971L;
	
	private int id;
	private int client_id;
	private String status;
	private Date finishedDate;
	private Cart cart;
	private Address address;
	private Payment payment;
	
	// Getters and Setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getClient_id() {
		return client_id;
	}
	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getFinishedDate() {
		return finishedDate;
	}
	public void setFinishedDate(Date finishedDate) {
		this.finishedDate = finishedDate;
	}
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
}
