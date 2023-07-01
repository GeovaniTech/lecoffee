package keep.product;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.faces.application.FacesMessage;

import model.Product;
import to.TOProduct;
import to.TOProductFilter;
import utils.AbstractManter;
import utils.MessageUtil;
import utils.SimpleWhere;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepProductSBean extends AbstractManter<Product, TOProduct> implements IKeepProductSBean, IKeepProductSBeanRemote {

	public KeepProductSBean() {
		this.setClassTypes(Product.class, TOProduct.class);
	}
	
	@Override
	public void save(TOProduct product) {
		if(product.getImageBytes() != null) {
			product.setCreationDate(new Date());
			product.setRating(0);
			
			Product model = this.convertToModel(product);
			
			em.getTransaction().begin();
			em.persist(model);
			em.getTransaction().commit();
		} else {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("invalid_information"), null, FacesMessage.SEVERITY_ERROR);
		}
	}

	@Override
	public void change(TOProduct product) {
		Product model  = this.convertToModel(product);
		
		em.getTransaction().begin();
		em.merge(model);
		em.getTransaction().commit();
	}

	@Override
	public void disable(TOProduct product) {
		product.setStatus("disabled");
		
		this.change(product);
	}

	@Override
	public Product findById(int id) {
		Product product = em.find(Product.class, id);
		
		return product;
	}

	@Override
	public List<TOProduct> list() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT P FROM ");
		sql.append(Product.class.getName()).append(" P ");
		sql.append(" ORDER BY P.status ASC ");
		
		List<Product> results = em.createQuery(sql.toString(), Product.class)
				.getResultList();
		
		return this.convertModelResults(results);
	}

	@Override
	public List<TOProduct> filterProducts(TOProductFilter filter) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT P FROM ").append(Product.class.getName()).append(" P ");
		sql.append(" WHERE 1 = 1 ");
		
		sql.append(SimpleWhere.queryFilter("P.name", filter.getName()));
		sql.append(SimpleWhere.queryFilter("P.description", filter.getDescription()));
		sql.append(SimpleWhere.queryFilter("P.category.name", filter.getCategory_id()));
		sql.append(SimpleWhere.queryFilterNumberRange("P.price", filter.getPrice()));
		sql.append(SimpleWhere.queryFilterNumberRange("P.rating", filter.getRating()));
		sql.append(SimpleWhere.queryFilterDateRange("P.creationDate", filter.getDateCreation()));
		 
		List<Product> results = em.createQuery(sql.toString(), Product.class)
				.getResultList();
		
		return this.convertModelResults(results);
	}

	@Override
	public void remove(TOProduct product) {
		Product model = convertToModel(product);
		
		em.getTransaction().begin();
		em.remove(em.contains(model) ? model : em.merge(model));	
		em.getTransaction().commit();
	}

	@Override
	public List<TOProduct> getProductsByCategoryId(int id) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT P FROM ");
		sql.append(Product.class.getName()).append(" P ");
		sql.append(" WHERE P.status = 'active' ");
		sql.append(" AND P.category.id = :id ");
		
		List<Product> results = em.createQuery(sql.toString(), Product.class)
				.setParameter("id", id)
				.getResultList();
		
		return this.convertModelResults(results);
	}

	@Override
	public TOProduct findByIdTO(int id) {
		Product model = this.findById(id);
		
		return this.convertToDTO(model);
	}
}
