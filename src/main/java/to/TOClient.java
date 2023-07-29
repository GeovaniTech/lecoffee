package to;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
	private String cep;
	private String street;
	private String complement;
	private Integer house_number;
	private List<TOCart> carts;
	private List<TOAddress> addresses;
	private boolean blocked;
	private boolean completedRegistration;
	private String phone_number;
	private String neighborhood;
	private boolean changePassword;
	
	public TOClient() {
		this.email = "";
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
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getComplement() {
		return complement;
	}
	public void setComplement(String complement) {
		this.complement = complement;
	}
	public Integer getHouse_number() {
		return house_number;
	}
	public void setHouse_number(Integer house_number) {
		this.house_number = house_number;
	}
	public boolean isBlocked() {
		return blocked;
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	public String getNeighborhood() {
		return neighborhood;
	}
	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
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

	@Override
	public int hashCode() {
		return Objects.hash(accountChangeDate, accountCreationDate, addresses, blocked, carts, cep, changePassword,
				complement, completedRegistration, email, house_number, id, inactivationDate, lastLogin, neighborhood,
				nivel, nome, phone_number, street, totalOrders);
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
				&& Objects.equals(carts, other.carts) && Objects.equals(cep, other.cep)
				&& changePassword == other.changePassword && Objects.equals(complement, other.complement)
				&& completedRegistration == other.completedRegistration && Objects.equals(email, other.email)
				&& Objects.equals(house_number, other.house_number) && id == other.id
				&& Objects.equals(inactivationDate, other.inactivationDate)
				&& Objects.equals(lastLogin, other.lastLogin) && Objects.equals(neighborhood, other.neighborhood)
				&& Objects.equals(nivel, other.nivel) && Objects.equals(nome, other.nome)
				&& Objects.equals(phone_number, other.phone_number) && Objects.equals(street, other.street)
				&& Objects.equals(totalOrders, other.totalOrders);
	}
}

