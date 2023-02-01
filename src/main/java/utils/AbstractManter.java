package utils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class AbstractManter extends LeCoffeeSession implements Message {
	@PersistenceContext(unitName = "lecoffee")
	public EntityManager em;
	
	public AbstractManter() {
		if(em == null) {
			em = Jpa.getEntityManager();
		}
	}
}