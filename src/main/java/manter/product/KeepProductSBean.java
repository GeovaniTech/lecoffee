package manter.product;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import model.Product;
import utils.AbstractManter;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepProductSBean extends AbstractManter implements IKeepProductSBean, IKeepProductSBeanRemote {

	@Override
	public void save(Product product) {
		if(validFields(product)) {
			em.getTransaction().begin();
			em.persist(product);
			em.getTransaction().commit();
			
			//Message
		} else {
			//Message
		}
	}

	@Override
	public void change(Product product) {
		if(validFields(product)) {
			em.getTransaction().begin();
			em.merge(product);
			em.getTransaction().commit();
			
			//Message
		} else {
			//Message
		}
		
	}

	@Override
	public void disable(Product product) {
		product.setStatus("disable");
		change(product);
	}

	@Override
	public void findById(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean validFields(Product product) {
		if(product.getName() != null
			&& product.getDescription() != null
			&& product.getImages() != null
			&& product.getPrice() != null) {
			
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void list() {
		// TODO Auto-generated method stub
		
	}
	
}
