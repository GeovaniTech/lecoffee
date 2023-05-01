package keep.appConfigs;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import model.AppConfigs;
import utils.AbstractManter;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepAppConfigs extends AbstractManter implements IKeepAppConfigs, IKeepAppConfigsRemote{

	@Override
	public void save(AppConfigs appConfigs) {
		em.getTransaction().begin();
		em.persist(appConfigs);
		em.getTransaction().commit();
	}

	@Override
	public void change(AppConfigs appConfigs) {
		em.getTransaction().begin();
		em.merge(appConfigs);
		em.getTransaction().commit();
	}

	@Override
	public AppConfigs findByClientId(int id) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT A FROM ").append(AppConfigs.class.getName()).append(" A ");
		sql.append(" WHERE A.client_id = :client_id");
		
		
		return (AppConfigs) em.createQuery(sql.toString())
				.setParameter("client_id", id)
				.getSingleResult();
	}
}
