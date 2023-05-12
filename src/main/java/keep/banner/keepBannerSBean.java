package keep.banner;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import model.Banner;
import utils.AbstractManter;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class keepBannerSBean extends AbstractManter implements IkeepBannerSBean, IkeepBannerSBeanRemote {

	@Override
	public void save(Banner banner) {
		em.getTransaction().begin();
		em.persist(banner);
		em.getTransaction().commit();
	}

	@Override
	public void change(Banner banner) {
		em.getTransaction().begin();
		em.merge(banner);
		em.getTransaction().commit();
		
	}

	@Override
	public void remove(Banner banner) {
		em.getTransaction().begin();
		em.remove(em.contains(banner) ? banner : em.merge(banner));
		em.getTransaction().commit();
	}

	@Override
	public Banner findById(int id) {
		Banner banner = em.find(Banner.class, id);
		
		return banner;
	}

	@Override
	public List<Banner> list() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT B FROM ");
		sql.append(Banner.class.getName()).append(" B ");
		
		return em.createQuery(sql.toString(), Banner.class)
				.getResultList();
	}


}
