package to;

public class TOFilterLovClient {
	private TOInputFilter name;
	private TOInputFilter email;
	private TOInputFilter phoneNumber;
	private TOInputFilter street;
	private TOInputFilter neighborhood;
	private TOInputFilter complement;
	private TOInputFilter cep;
	private Integer numberHouse;
	
	public TOFilterLovClient() {
		this.setName(new TOInputFilter());
		this.setEmail(new TOInputFilter());
		this.setPhoneNumber(new TOInputFilter());
		this.setStreet(new TOInputFilter());
		this.setNeighborhood(new TOInputFilter());
		this.setCep(new TOInputFilter());
		this.setComplement(new TOInputFilter());
	}
	
	// Getters and Setters
	public TOInputFilter getName() {
		return name;
	}
	public void setName(TOInputFilter name) {
		this.name = name;
	}
	public TOInputFilter getEmail() {
		return email;
	}
	public void setEmail(TOInputFilter email) {
		this.email = email;
	}
	public TOInputFilter getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(TOInputFilter phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public TOInputFilter getStreet() {
		return street;
	}
	public void setStreet(TOInputFilter street) {
		this.street = street;
	}
	public TOInputFilter getNeighborhood() {
		return neighborhood;
	}
	public void setNeighborhood(TOInputFilter neighborhood) {
		this.neighborhood = neighborhood;
	}
	public Integer getNumberHouse() {
		return numberHouse;
	}
	public void setNumberHouse(Integer numberHouse) {
		this.numberHouse = numberHouse;
	}
	public TOInputFilter getComplement() {
		return complement;
	}
	public void setComplement(TOInputFilter complement) {
		this.complement = complement;
	}
	public TOInputFilter getCep() {
		return cep;
	}
	public void setCep(TOInputFilter cep) {
		this.cep = cep;
	}
}
