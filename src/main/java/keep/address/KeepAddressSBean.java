package keep.address;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import model.Address;
import utils.AbstractManter;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepAddressSBean extends AbstractManter implements IkeepAddressSBean, IkeepAddressSBeanRemote {
	@Override
	public void save(Address address) {
		em.getTransaction().begin();
		em.persist(address);
		em.getTransaction().commit();
	}

	@Override
	public void change(Address address) {
		em.getTransaction().begin();
		em.merge(address);
		em.getTransaction().commit();
	}

	@Override
	public void remove(Address address) {
		em.getTransaction().begin();
		em.remove(em.contains(address) ? address : em.merge(address));
		em.getTransaction().commit();
	}

	@Override
	public Address findById(int id) {
		return em.find(Address.class, id);
	}

}
