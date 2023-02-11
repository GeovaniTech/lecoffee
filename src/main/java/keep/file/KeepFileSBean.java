package keep.file;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import model.File;
import utils.AbstractManter;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepFileSBean extends AbstractManter implements IKeepFileSBean, IKeepFileSBeanRemote {

	@Override
	public void save(File file) {
		em.getTransaction().begin();
		em.persist(file);
		em.getTransaction().commit();
	}

	@Override
	public void remove(File file) {
		em.getTransaction().begin();
		em.remove(em.contains(file) ? file : em.merge(file));
		em.getTransaction().commit();
	}
}
