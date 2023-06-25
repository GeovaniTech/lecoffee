package model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import to.TOClient;
import utils.AbstractObject;

@Entity
public class ClientOrder extends AbstractObject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int client_id;
	private String status;
	private Date finishedDate;
	private Date timeSent;
	
	@OneToOne
	private Cart cart;
	
	@OneToOne
	private Address address;
	
	@OneToOne
	private Payment payment;
	
	//Getters and Setters
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
