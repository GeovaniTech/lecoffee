package to;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.mail.Address;

import converter.BaseEntity;

public class TOClient implements Serializable, BaseEntity {
	private static final long serialVersionUID = 7799424873449345678L;
	
	private int id;
	private String nome;
	private String email;
	private String nivel;
	private Integer totalOrders;
	private Date lastLogin;
	private Date accountCreationDate;
	private Date accountChangeDate;
	private Date inactivationDate;
	private List<TOCart> carts;
	private List<TOAddress> addresses;
	private boolean blocked;
	private boolean completedRegistration;
	private String phone_number;
	private boolean changePassword;
	
	public TOClient() {
		this.email = "";
		this.setAddresses(new ArrayList<TOAddress>());
	}
	
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
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	public Date getAccountCreationDate() {
		return accountCreationDate;
	}
	public void setAccountCreationDate(Date accountCreationDate) {
		this.accountCreationDate = accountCreationDate;
	}
	public Date getAccountChangeDate() {
		return accountChangeDate;
	}
	public void setAccountChangeDate(Date accountChangeDate) {
		this.accountChangeDate = accountChangeDate;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public boolean isChangePassword() {
		return changePassword;
	}
	public void setChangePassword(boolean changePassword) {
		this.changePassword = changePassword;
	}
	public Date getInactivationDate() {
		return inactivationDate;
	}
	public void setInactivationDate(Date inactivationDate) {
		this.inactivationDate = inactivationDate;
	}
	public List<TOCart> getCarts() {
		return carts;
	}
	public void setCarts(List<TOCart> carts) {
		this.carts = carts;
	}
	public List<TOAddress> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<TOAddress> addresses) {
		this.addresses = addresses;
	}

	@Override
	public Long getIdBase() {
		return (long) this.getId();
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountChangeDate, accountCreationDate, addresses, blocked, carts, changePassword,
				completedRegistration, email, id, inactivationDate, lastLogin, nivel, nome, phone_number, totalOrders);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TOClient other = (TOClient) obj;
		return Objects.equals(accountChangeDate, other.accountChangeDate)
				&& Objects.equals(accountCreationDate, other.accountCreationDate)
				&& Objects.equals(addresses, other.addresses) && blocked == other.blocked
				&& Objects.equals(carts, other.carts) && changePassword == other.changePassword
				&& completedRegistration == other.completedRegistration && Objects.equals(email, other.email)
				&& id == other.id && Objects.equals(inactivationDate, other.inactivationDate)
				&& Objects.equals(lastLogin, other.lastLogin) && Objects.equals(nivel, other.nivel)
				&& Objects.equals(nome, other.nome) && Objects.equals(phone_number, other.phone_number)
				&& Objects.equals(totalOrders, other.totalOrders);
	}

}

