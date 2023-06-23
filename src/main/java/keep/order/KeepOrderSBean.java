package keep.order;

import model.ClientOrder;
import to.TOClientOrder;
import utils.AbstractManter;

public class KeepOrderSBean extends AbstractManter implements IKeepOrderSBean, IKeepOrderSBeanRemote {

	@Override
	public void save(ClientOrder object) {
		em.getTransaction().begin();
		em.persist(object);
		em.getTransaction().commit();
	}

	@Override
	public void change(ClientOrder object) {
		em.getTransaction().begin();
		em.merge(object);
		em.getTransaction().commit();
		
	}

	@Override
	public void remove(ClientOrder object) {
		em.getTransaction().begin();
		em.remove(em.contains(object) ? object : em.merge(object));
		em.getTransaction().commit();
	}

	@Override
	public ClientOrder findById(int id) {
		return em.find(ClientOrder.class, id);
	}

	@Override
	public TOClientOrder findByIdTO(int id) {
		TOClientOrder to = this.getConverter().map(this.findById(id), TOClientOrder.class);
		return to;
	}

}
