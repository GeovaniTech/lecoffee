package keep.category;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.modelmapper.ModelMapper;

import model.Category;
import model.Product;
import to.TOCategory;
import utils.AbstractManter;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepCategorySBean extends AbstractManter implements IkeepCategorySBean, IkeepCategorySBeanRemote {
	private ModelMapper converter;
	
	public KeepCategorySBean() {
		this.setConverter(new ModelMapper());
	}
	
	@Override
	public void save(TOCategory category) {
		category.setStatus("active");
		
		Category model = this.getConverter().map(category, Category.class);
		
		em.getTransaction().begin();
		em.persist(model);
		em.getTransaction().commit();
	}

	@Override
	public void change(TOCategory category) {
		Category model = this.getConverter().map(category, Category.class);
		
		em.getTransaction().begin();
		em.merge(model);
		em.getTransaction().commit();
	}
	
	@Override
	public void remove(TOCategory category) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" DELETE FROM ").append(Product.class.getName()).append(" P ");
		sql.append(" WHERE P.category.id = :categoryId ");
		
		em.getTransaction().begin();
		em.createQuery(sql.toString())
			.setParameter("categoryId", category.getId())
			.executeUpdate();
		em.getTransaction().commit();
		
		sql = new StringBuilder();
		
		sql.append(" DELETE FROM ").append(Category.class.getName()).append(" C ");
		sql.append(" WHERE C.id = :categoryId ");
		
		em.getTransaction().begin();
		em.createQuery(sql.toString())
			.setParameter("categoryId", category.getId())
			.executeUpdate();
		em.getTransaction().commit();
	}

	@Override
	public void disable(TOCategory category) {
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
	public void active(TOCategory category) {
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
	public List<TOCategory> list() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT C FROM ");
		sql.append(Category.class.getName()).append(" C ");
		
		List<Category> categories = em.createQuery(sql.toString(), Category.class)
				.getResultList();
		
		List<TOCategory> convertedCategories = new ArrayList<TOCategory>();
		
		for(Category category : categories) {
			convertedCategories.add(this.getConverter().map(category, TOCategory.class));
		}
		
		return convertedCategories;
	}

	@Override
	public List<TOCategory> listActives() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT C FROM ");
		sql.append(Category.class.getName()).append(" C ");
		sql.append(" WHERE C.status = 'active' ");
		sql.append(" ORDER BY C.priority ASC ");
		
		List<Category> categories = em.createQuery(sql.toString(), Category.class)
				.getResultList();
		
		List<TOCategory> convertedCategories = new ArrayList<TOCategory>();
		
		for(Category category : categories) {
			convertedCategories.add(this.getConverter().map(category, TOCategory.class));
		}
		
		return convertedCategories;
	}
	
	@Override
	public TOCategory findToById(int id) {
		Category model = this.findById(id);
		
		TOCategory to = this.getConverter().map(model, TOCategory.class);
		
		return to;
	}

	//Getters and Setters
	public ModelMapper getConverter() {
		return converter;
	}
	public void setConverter(ModelMapper converter) {
		this.converter = converter;
	}
}
