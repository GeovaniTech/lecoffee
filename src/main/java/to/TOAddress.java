package to;

import converter.BaseEntity;

public class TOAddress implements BaseEntity {
	private int id;
	private String cep;
	private String street;
	private String complement;
	private Integer house_number;
	private String neighborhood;
	
	@Override
	public Long getIdBase() {
		return (long) this.getId();
	}

	// Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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
