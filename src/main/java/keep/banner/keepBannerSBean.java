package keep.banner;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import model.Banner;
import to.TOBanner;
import utils.AbstractManter;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class keepBannerSBean extends AbstractManter implements IkeepBannerSBean, IkeepBannerSBeanRemote {

	@Override
	public void save(Banner banner) {
		banner.setStatus("active");
		
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
	public List<TOBanner> list() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT B FROM ");
		sql.append(Banner.class.getName()).append(" B ");
		sql.append(" ORDER BY B.priority ASC ");
		
		List<Banner> banners = em.createQuery(sql.toString(), Banner.class)
				.getResultList();
		
		List<TOBanner> toBanners = new ArrayList<TOBanner>();
		
		for(Banner banner: banners) {
			TOBanner to = new TOBanner();
			to.setId(banner.getId());
			to.setName(banner.getName());
			to.setPriority(banner.getPriority());
			to.setStatus(banner.getStatus());
			to.setBytes(banner.getBytes()); 
			
			toBanners.add(to);
		}
		
		return toBanners;
	}


}
