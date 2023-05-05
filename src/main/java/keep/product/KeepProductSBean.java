package keep.product;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.faces.application.FacesMessage;

import model.Product;
import to.TOProductFilter;
import utils.AbstractManter;
import utils.MessageUtil;
import utils.SimpleWhere;
import utils.StringUtil;

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
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("invalid_information"), null, FacesMessage.SEVERITY_ERROR);
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
		product.setStatus("disabled");
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
	public List<Product> filterProducts(TOProductFilter filter) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT P FROM ").append(Product.class.getName()).append(" P ");
		sql.append(" WHERE 1 = 1 ");

		if(StringUtil.isNotNull(filter.getName().getValue())) {
			sql.append(SimpleWhere.queryFilter("P.name", filter.getName()));
		}
		
		if(StringUtil.isNotNull(filter.getDescription().getValue())) {
			sql.append(SimpleWhere.queryFilter("P.description", filter.getDescription()));
		}
		
		if(filter.getPrice().getFrom() != null || filter.getPrice().getTo() != null) {
			sql.append(SimpleWhere.queryFilterNumberRange("P.price", filter.getPrice()));
		}
		
		if(filter.getRating().getFrom() != null || filter.getRating().getTo() != null) {
			sql.append(SimpleWhere.queryFilterNumberRange("P.rating", filter.getRating()));
		}
		 
		return null;
	}
}
