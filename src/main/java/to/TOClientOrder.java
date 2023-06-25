package to;

import java.io.Serializable;
import java.util.Date;

import model.Address;
import model.Cart;
import model.Payment;
import utils.AbstractObject;

public class TOClientOrder extends AbstractObject implements Serializable {
	private static final long serialVersionUID = -2652741365794788971L;
	
	private int id;
	private TOClient client;
	private String status;
	private Date finishedDate;
	private Date timeSent;
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
	public TOClient getClient() {
		return client;
	}
	public void setClient(TOClient client) {
		this.client = client;
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
	public Date getTimeSent() {
		return timeSent;
	}
	public void setTimeSent(Date timeSent) {
		this.timeSent = timeSent;
	}
}
