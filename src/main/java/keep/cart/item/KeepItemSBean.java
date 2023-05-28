package keep.cart.item;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import model.Item;
import utils.AbstractManter;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepItemSBean extends AbstractManter implements IKeepItemSBean, IKeepItemSBeanRemote {

	@Override
	public void save(Item item) {
		em.getTransaction().begin();
		em.persist(item);
		em.getTransaction().commit();
	}

	@Override
	public void change(Item item) {
		em.getTransaction().begin();
		em.merge(item);
		em.getTransaction().commit();
	}

	@Override
	public void remove(Item item) {
		em.getTransaction().begin();
		em.remove(em.contains(item) ? item : em.merge(item));
		em.getTransaction().commit();
	}

	@Override
	public Item findById(int id) {
		return em.find(Item.class, id);
	}

}
