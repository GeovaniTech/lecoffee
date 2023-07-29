package keep.address;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import keep.client.KeepClientSBean;
import model.Address;
import model.Client;
import to.TOAddress;
import utils.AbstractManter;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepAddressSBean extends AbstractManter<Address, TOAddress> implements IkeepAddressSBean, IkeepAddressSBeanRemote {
	private KeepClientSBean clientSBean;
	
	public KeepAddressSBean() {
		this.setClientSBean(new KeepClientSBean());
		this.setClassTypes(Address.class, TOAddress.class);
	}
	
	@Override
	public void save(TOAddress address, int client_id) {
		Address model = this.convertToModel(address);
		
		
		em.getTransaction().begin();
		em.persist(model);
		em.getTransaction().commit();
		
		Client client = em.find(Client.class, client_id);
		
		client.getAddresses().add(model);
		
		em.getTransaction().begin();
		em.merge(client);
		em.getTransaction().commit();
	}

	@Override
	public void change(TOAddress address, int client_id) {
		Address model = this.convertToModel(address);
		
		em.getTransaction().begin();
		em.merge(model);
		em.getTransaction().commit();
	}

	@Override
	public void remove(Address address, int client_id) {
		Client client = em.find(Client.class, client_id);
		
		client.getAddresses().remove(address);
		
		em.getTransaction().begin();
		em.merge(client);
		em.remove(em.contains(address) ? address : em.merge(address));
		em.getTransaction().commit();
	}

	@Override
	public Address findById(int id) {
		return em.find(Address.class, id);
	}

	@Override
	public List<TOAddress> getClientAddresses(int client_id) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT A FROM ");
		sql.append(Client.class.getName()).append(" C ");
		sql.append(" JOIN C.addresses as A ");
		sql.append(" WHERE C.id = :client_id");
		
		List<Address> results = em.createQuery(sql.toString(), Address.class)
				.setParameter("client_id", client_id)
				.getResultList();
		
		return this.convertModelResults(results);
	}
	
	@Override
	public void save(TOAddress object) {
		Address address = this.convertToModel(object);
		
		em.getTransaction().begin();
		em.persist(address);
		em.getTransaction().commit();
	}

	@Override
	public void change(TOAddress object) {
		Address address = this.convertToModel(object);
		
		em.getTransaction().begin();
		em.merge(address);
		em.getTransaction().commit();
	}

	@Override
	public void remove(TOAddress object) {
		Address address = this.convertToModel(object);
		
		em.getTransaction().begin();
		em.remove(em.contains(address) ? address : em.merge(address));
		em.getTransaction().commit();
	}

	@Override
	public TOAddress findByIdTO(int id) {
		return this.convertToDTO(this.findById(id));
	}

	@Override
	public List<TOAddress> list() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT A FROM ").append(Address.class.getName()).append(" A ");
		
		List<Address> results = em.createQuery(sql.toString(), Address.class)
								  .getResultList();
		
		return this.convertModelResults(results);
	}

	//Getters and Setters
	public KeepClientSBean getClientSBean() {
		return clientSBean;
	}
	public void setClientSBean(KeepClientSBean clientSBean) {
		this.clientSBean = clientSBean;
	}
}
