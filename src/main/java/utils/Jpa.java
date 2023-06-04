package utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

@PersistenceUnit(unitName = "lecoffee")
public class Jpa {
	private static EntityManagerFactory emf = null;

	static {
		if(emf == null) {
			emf = Persistence.createEntityManagerFactory("lecoffee");
		}
	}
	
	public static EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
}