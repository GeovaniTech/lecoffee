package to;

import java.io.Serializable;
import java.util.List;
import model.Cart;

public class TOClient implements Serializable {
	private static final long serialVersionUID = 7799424873449345678L;
	
	private int id;
	private String nome;
	private String email;
	private String nivel;
	private Integer totalOrders;
	private List<Cart> carts;
	private boolean completedRegistration;
	
	//Getters and Setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	public List<Cart> getCarts() {
		return carts;
	}
	public void setCarts(List<Cart> carts) {
		this.carts = carts;
	}
	public boolean isCompletedRegistration() {
		return completedRegistration;
	}
	public void setCompletedRegistration(boolean completedRegistration) {
		this.completedRegistration = completedRegistration;
	}
	public Integer getTotalOrders() {
		return totalOrders;
	}
	public void setTotalOrders(Integer totalOrders) {
		this.totalOrders = totalOrders;
	}
}

