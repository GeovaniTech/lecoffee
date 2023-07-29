package to;

import java.io.Serializable;
import java.util.Date;

import utils.AbstractObject;

public class TOClientOrder extends AbstractObject implements Serializable {
	private static final long serialVersionUID = -2652741365794788971L;
	
	private int id;
	private int client_id;
	private TOClient client;
	private String status;
	private Date finishedDate;
	private Date timeSent;
	private TOCart cart;
	private TOAddress address;
	private TOPayment payment;
	
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
	public Date getTimeSent() {
		return timeSent;
	}
	public void setTimeSent(Date timeSent) {
		this.timeSent = timeSent;
	}
	public int getClient_id() {
		return client_id;
	}
	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}
	public TOAddress getAddress() {
		return address;
	}
	public void setAddress(TOAddress address) {
		this.address = address;
	}
	public TOPayment getPayment() {
		return payment;
	}
	public void setPayment(TOPayment payment) {
		this.payment = payment;
	}
	public TOCart getCart() {
		return cart;
	}
	public void setCart(TOCart cart) {
		this.cart = cart;
	}
}
