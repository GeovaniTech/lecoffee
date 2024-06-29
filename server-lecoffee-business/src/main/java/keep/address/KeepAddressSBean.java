package keep.address;

import java.util.List;

import abstracts.AbstractKeep;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.persistence.Query;
import model.client.Address;
import to.client.address.TOAddress;
import to.client.address.TOFilterAddress;
import utils.StringUtil;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepAddressSBean extends AbstractKeep<Address, TOAddress> implements IKeepAddressSbean, IKeepAddressRemoteSbean{

	public KeepAddressSBean() {
		super(Address.class, TOAddress.class);
	}

	@Override
	public void save(TOAddress address) {
		Address model = this.convertToModel(address);
		
		if(StringUtil.isNull(model.getId())) {
			this.getEntityManager().persist(model);
		} else {
			this.getEntityManager().merge(model);
		}
	}

	@Override
	public void remove(TOAddress address) {
		Address model = this.convertToModel(address);
		this.getEntityManager().remove(this.getEntityManager().contains(model) ? model : this.getEntityManager().merge(model));
	}
	
	@Override
	public TOAddress findById(int id) {
		return this.convertToDTO(this.getEntityManager().find(Address.class, id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TOAddress> searchAdresses(TOFilterAddress filter) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT C ");
		sql.append(this.fromAddress());
		sql.append(this.whereAddress());
				
		Query query = this.getEntityManager().createQuery(sql.toString());
		query.setFirstResult(filter.getFirstResult());
		query.setMaxResults(filter.getMaxResults());
		
		return this.convertModelResults(query.getResultList());
	}
	
	
	@Override
	public Integer getCount(TOFilterAddress filter) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private String fromAddress() {
		return " FROM " + Address.class.getSimpleName() + " C ";
	}
	
	private String whereAddress() {
		return "";
	}
}
