package to;

import model.AppConfigs;
import model.Cart;

public class TOClient {
	private int id;
	private String nome;
	private String email;
	private String nivel;
	private AppConfigs preferences;
	private Cart cart;
	
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
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public AppConfigs getPreferences() {
		return preferences;
	}
	public void setPreferences(AppConfigs preferences) {
		this.preferences = preferences;
	}
}

