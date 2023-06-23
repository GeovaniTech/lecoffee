package utils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;

public class AbstractManter extends LeCoffeeSession  {
	@PersistenceContext(unitName = "lecoffee")
	public EntityManager em;
	public ModelMapper converter;
	
	
	public AbstractManter() {
		if(em == null) {
			em = Jpa.getEntityManager();
		}
		
		this.setConverter(new ModelMapper());
	}

	// Getters and Setters
	public ModelMapper getConverter() {
		return converter;
	}
	public void setConverter(ModelMapper converter) {
		this.converter = converter;
	}
}
