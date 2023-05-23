package keep.category;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import model.Category;
import model.Product;
import utils.AbstractManter;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepCategorySBean extends AbstractManter implements IkeepCategorySBean, IkeepCategorySBeanRemote {

	@Override
	public void save(Category category) {
		category.setStatus("active");
		
		em.getTransaction().begin();
		em.persist(category);
		em.getTransaction().commit();
	}

	@Override
	public void change(Category category) {
		em.getTransaction().begin();
		em.merge(category);
		em.getTransaction().commit();
	}
	
	@Override
	public void remove(Category category) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" DELETE FROM ").append(Product.class.getName()).append(" P ");
		sql.append(" WHERE P.category.id = :categoryId ");
		
		em.getTransaction().begin();
		em.createQuery(sql.toString())
			.setParameter("categoryId", category.getId())
			.executeUpdate();
		em.getTransaction().commit();
		
		sql = new StringBuilder();
		
		sql.append("DELETE FROM ").append(Category.class.getName()).append(" C ");
		sql.append(" WHERE C.id = :categoryId ");
		
		em.getTransaction().begin();
		em.createQuery(sql.toString())
			.setParameter("categoryId", category.getId())
			.executeUpdate();
		em.getTransaction().commit();
	}

	@Override
	public void disable(Category category) {
		category.setStatus("disabled");
		change(category);
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" UPDATE ").append(Product.class.getName()).append(" P ");
		sql.append(" SET P.status = 'disabled' " );
		sql.append(" WHERE P.category.id = :categoryId ");
		
		em.getTransaction().begin();
		em.createQuery(sql.toString())
			.setParameter("categoryId", category.getId())
			.executeUpdate();
		em.getTransaction().commit();
	}
	
	@Override
	public void active(Category category) {
		category.setStatus("active");
		change(category);
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" UPDATE ").append(Product.class.getName()).append(" P ");
		sql.append(" SET P.status = 'active' " );
		sql.append(" WHERE P.category.id = :categoryId ");
		
		em.getTransaction().begin();
		em.createQuery(sql.toString())
			.setParameter("categoryId", category.getId())
			.executeUpdate();
		em.getTransaction().commit();
	}

	@Override
	public Category findById(int id) {
		Category category = em.find(Category.class, id);
		
		return category;
	}

	@Override
	public List<Category> list() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT C FROM ");
		sql.append(Category.class.getName()).append(" C ");
		
		return em.createQuery(sql.toString(), Category.class)
					.getResultList();
	}

	@Override
	public List<Category> listActives() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT C FROM ");
		sql.append(Category.class.getName()).append(" C ");
		sql.append(" WHERE C.status = 'active' ");
		sql.append(" ORDER BY C.priority ASC ");
		
		return em.createQuery(sql.toString(), Category.class)
				.getResultList();
	}
}
