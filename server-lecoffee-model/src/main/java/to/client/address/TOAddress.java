package to.client.address;

import abstracts.AbstractTOObject;

public class TOAddress extends AbstractTOObject {

	private static final long serialVersionUID = 7849039148348552046L;
	
	private String cep;
	private String street;
	private String complement;
	private Integer house_number;
	private String neighborhood;
	
	// Getters and Setters
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
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
	public String getNeighborhood() {
		return neighborhood;
	}
	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}
}
