package keep.product;

import java.util.Date;
import java.util.List;

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
		if(product.getImageBytes() != null) {
			product.setCreationDate(new Date());
			product.setRating(0);
			
			em.getTransaction().begin();
			em.persist(product);
			em.getTransaction().commit();
		} else {
			msg.informacoesInvalidas();
		}
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
	public Product findById(int id) {
		Product product = em.find(Product.class, id);
		
		return product;
	}

	@Override
	public List<Product> list() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT P FROM ");
		sql.append(Product.class.getName()).append(" P ");
		sql.append(" ORDER BY P.status ASC ");
		return em.createQuery(sql.toString(), Product.class)
				.getResultList();
	}

	@Override
	public void removeAll() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" DELETE FROM ");
		sql.append(Product.class.getName()).append(" P ");
		
		em.getTransaction().begin();
		em.createQuery(sql.toString()).executeUpdate();
		em.getTransaction().commit();
	}
	
}
