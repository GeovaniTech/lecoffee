package keep.product;

import java.util.List;

import abstracts.AbstractKeep;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.persistence.Query;
import model.product.Product;
import to.product.TOFilterProduct;
import to.product.TOProduct;
import utils.StringUtil;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepProductSBean extends AbstractKeep<Product, TOProduct> implements IKeepProductSBean, IKeepProductRemoteSBean {

	public KeepProductSBean() {
		super(Product.class, TOProduct.class);
	}
	
	@Override
	public void save(TOProduct product) {
		Product model = this.convertToModel(product);
		
		if(StringUtil.isNull(model.getId())) {
			this.getEntityManager().persist(model);
			return;
		}
		
		this.getEntityManager().merge(model);
	}

	@Override
	public void remove(TOProduct product) {
		Product model = this.convertToModel(product);
		this.getEntityManager().remove(this.getEntityManager().contains(model) ? model : this.getEntityManager().merge(model));
	}

	@Override
	public TOProduct findById(String id) {
		return this.convertToDTO(this.getEntityManager().find(Product.class, id));
	}

	@Override
	public Integer getCount(TOFilterProduct filter) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT COUNT(P) ")
		.append(this.getFromProducts())
		.append(this.getWhereProducts());
		
		Query query = this.getEntityManager().createQuery(sql.toString());
		
		Number value;
		
		try {
			value = (Number) query.getSingleResult();
		} catch (Exception e) {
			value = 0;
		}
		
		return value.intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TOProduct> searchProducts(TOFilterProduct filter) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT P ")
			.append(this.getFromProducts())
			.append(this.getWhereProducts());

		Query query = this.getEntityManager().createQuery(sql.toString());
		query.setFirstResult(filter.getFirstResult());
		query.setMaxResults(filter.getMaxResults());
		
		return this.convertModelResults(query.getResultList());
	}
	
	private String getFromProducts() { 
		return " FROM " + Product.class.getSimpleName() + " P ";
	}
	
	private String getWhereProducts() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" WHERE 1 = 1 ");
		
		return sql.toString();
	}

}
