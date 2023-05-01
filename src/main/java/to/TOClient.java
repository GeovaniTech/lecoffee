package to;

import java.util.List;

import model.AppConfigs;
import model.Cart;

public class TOClient {
	private int id;
	private String nome;
	private String email;
	private String nivel;
	private AppConfigs preferences;
	private List<Cart> carts;
	private boolean completedRegistration;
	
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
	public AppConfigs getPreferences() {
		return preferences;
	}
	public void setPreferences(AppConfigs preferences) {
		this.preferences = preferences;
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
}

