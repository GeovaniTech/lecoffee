package model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Client {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nome;
	private String email;
	private String senha;
	private String nivel;
	private boolean completedRegistration;
	
	@OneToOne
	private AppConfigs preferences;
	
	@OneToMany
	private List<Cart> cart;
	
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
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
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
	public boolean isCompletedRegistration() {
		return completedRegistration;
	}
	public void setCompletedRegistration(boolean completedRegistration) {
		this.completedRegistration = completedRegistration;
	}
	public List<Cart> getCart() {
		return cart;
	}
	public void setCart(List<Cart> cart) {
		this.cart = cart;
	}
}
