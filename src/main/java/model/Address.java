package model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import converter.BaseEntity;

@Entity
public class Address implements BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
	@Override
	public int hashCode() {
		return Objects.hash(cep, complement, house_number, id, neighborhood, street);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		return Objects.equals(cep, other.cep) && Objects.equals(complement, other.complement)
				&& Objects.equals(house_number, other.house_number) && id == other.id
				&& Objects.equals(neighborhood, other.neighborhood) && Objects.equals(street, other.street);
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
