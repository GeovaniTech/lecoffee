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
		product.setStatus("active");
		
		em.getTransaction().begin();
		em.persist(product);
		em.getTransaction().commit();
	}

	@Override
	public void change(Product product) {
		em.getTransaction().begin();
		em.persist(product);
		em.getTransaction().commit();
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
	public void list() {
		// TODO Auto-generated method stub
		
	}
	
}
