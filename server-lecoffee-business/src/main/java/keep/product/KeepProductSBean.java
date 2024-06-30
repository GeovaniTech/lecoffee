package keep.product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import abstracts.AbstractKeep;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.persistence.Query;
import model.product.Product;
import query.SimpleWhere;
import to.TOParameter;
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
		
		List<TOParameter> params = new ArrayList<TOParameter>();
		
		sql.append(" SELECT COUNT(P) ")
			.append(this.getFromProducts())
			.append(this.getWhereProducts(params, filter));
		
		Query query = this.getEntityManager().createQuery(sql.toString());
		setParameters(query, params);
		
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
		
		List<TOParameter> params = new ArrayList<TOParameter>();
		
		sql.append(" SELECT P ")
			.append(this.getFromProducts())
			.append(this.getWhereProducts(params, filter));

		Query query = this.getEntityManager().createQuery(sql.toString());
		query.setFirstResult(filter.getFirstResult());
		query.setMaxResults(filter.getMaxResults());
		setParameters(query, params);
		
		return this.convertModelResults(query.getResultList());
	}
	
	@Override
	public void disable(TOProduct product) {
		Date date = new Date();
		String user = this.getClientSession().getEmail();
		
		TOProduct model = this.findById(product.getId());
		model.setInactivationDate(date);
		model.setInactivationUser(user);
		product.setInactivationDate(date);
		product.setInactivationUser(user);
		
		this.save(model);
	}

	@Override
	public void active(TOProduct product) {
		TOProduct model = this.findById(product.getId());
		model.setInactivationDate(null);
		model.setInactivationUser(null);
		product.setInactivationDate(null);
		product.setInactivationUser(null);
		
		this.save(model);
	}
	
	private String getFromProducts() { 
		return " FROM " + Product.class.getSimpleName() + " P ";
	}
	
	private String getWhereProducts(List<TOParameter> params, TOFilterProduct filter) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" WHERE 1 = 1 ");
		sql.append(SimpleWhere.queryFilter("P.name", filter.getName()));
		sql.append(SimpleWhere.queryFilter("P.description", filter.getDescription()));
		sql.append(SimpleWhere.queryFilterNumberRange("P.price", filter.getPrice()));
		
		if (StringUtil.isNotNull(filter.getIdCategory())) {
			sql.append(" AND P.category.id = :idCategory ");
			params.add(new TOParameter("idCategory", filter.getIdCategory()));
		}
		
		return sql.toString();
	}

}
