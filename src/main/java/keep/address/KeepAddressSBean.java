package keep.address;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import keep.client.KeepClientSBean;
import model.Address;
import model.Client;
import utils.AbstractManter;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepAddressSBean extends AbstractManter implements IkeepAddressSBean, IkeepAddressSBeanRemote {
	private KeepClientSBean clientSBean;
	
	public KeepAddressSBean() {
		this.setClientSBean(new KeepClientSBean());
	}
	
	@Override
	public void save(Address address, int client_id) {
		em.getTransaction().begin();
		em.persist(address);
		em.getTransaction().commit();
		
		Client client = em.find(Client.class, client_id);
		
		client.getAddresses().add(address);
		
		em.getTransaction().begin();
		em.merge(client);
		em.getTransaction().commit();
	}

	@Override
	public void change(Address address, int client_id) {
		em.getTransaction().begin();
		em.merge(address);
		em.getTransaction().commit();
	}

	@Override
	public void remove(Address address, int client_id) {
		em.getTransaction().begin();
		em.remove(em.contains(address) ? address : em.merge(address));
		em.getTransaction().commit();
	}

	@Override
	public Address findById(int id) {
		return em.find(Address.class, id);
	}

	@Override
	public List<Address> getClientAddresses(int client_id) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT A FROM ");
		sql.append(Client.class.getName()).append(" C ");
		sql.append(" JOIN C.addresses as A ");
		sql.append(" WHERE C.id = :client_id");
		
		return em.createQuery(sql.toString(), Address.class)
				.setParameter("client_id", client_id)
				.getResultList();
	}

	//Getters and Setters
	public KeepClientSBean getClientSBean() {
		return clientSBean;
	}
	public void setClientSBean(KeepClientSBean clientSBean) {
		this.clientSBean = clientSBean;
	}
}
