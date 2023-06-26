package keep.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import keep.cart.KeepCartSBean;
import keep.client.KeepClientSBean;
import model.ClientOrder;
import to.TOClientOrder;
import utils.AbstractManter;

public class KeepOrderSBean extends AbstractManter implements IKeepOrderSBean, IKeepOrderSBeanRemote {
	
	private KeepClientSBean clientSBean;
	private KeepCartSBean cartSBean;
	
	public KeepOrderSBean() {
		this.setClientSBean(new KeepClientSBean());
		this.setCartSBean(new KeepCartSBean());
	}
	
	@Override
	public void save(TOClientOrder object) {
		object.setCreationDate(new Date());
		object.setCreationUser(this.getClient().getEmail());
		
		ClientOrder model = this.getConverter().map(object, ClientOrder.class);
		
		this.getCartSBean().change(object.getCart());
		
		em.getTransaction().begin();
		em.persist(model); 
		em.getTransaction().commit();
	}

	@Override
	public void change(TOClientOrder object) {
		object.setChangeDate(new Date());
		object.setChangeUser(this.getClient().getEmail());
		
		ClientOrder model = this.getConverter().map(object, ClientOrder.class);
		
		em.getTransaction().begin();
		em.merge(model);
		em.getTransaction().commit();
		
	}

	@Override
	public void remove(TOClientOrder object) {
		ClientOrder model = this.getConverter().map(object, ClientOrder.class);
		
		em.getTransaction().begin();
		em.remove(em.contains(model) ? model : em.merge(model));
		em.getTransaction().commit();
	}
	
	@Override
	public List<TOClientOrder> listByClientId(int client_id) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT O FROM ");
		sql.append(ClientOrder.class.getName()).append(" O ");
		sql.append(" WHERE O.client_id = :client_id ");
		
		List<ClientOrder> results = em.createQuery(sql.toString(), ClientOrder.class)
										.setParameter("client_id", client_id)	
										.getResultList();
		
		List<TOClientOrder> convertedResults = new ArrayList<TOClientOrder>();
		
		for(ClientOrder model : results) {
			if(model != null) {
				TOClientOrder to = this.getConverter().map(model, TOClientOrder.class);
				to.setClient(this.getClientSBean().findById(client_id));
				convertedResults.add(to);		
			}
		}
		
		return convertedResults;	
	}

	@Override
	public List<TOClientOrder> list() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT O FROM ");
		sql.append(ClientOrder.class.getName()).append(" O ");
		sql.append(" ORDER BY O.creationDate DESC");
		
		List<ClientOrder> results = em.createQuery(sql.toString(), ClientOrder.class)
										.getResultList();
		
		List<TOClientOrder> convertedResults = new ArrayList<TOClientOrder>();
		
		for(ClientOrder model : results) {
			if(model != null) {
				TOClientOrder to = this.getConverter().map(model, TOClientOrder.class);
				to.setClient(this.getClientSBean().findById(model.getClient_id()));
				convertedResults.add(to);				
			}
		}
		
		return convertedResults;
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

	@Override
	public Number getQuantityAConfirmar() {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT COUNT(O.id) FROM ")
		   .append(ClientOrder.class.getName()).append(" O ")
		   .append(" WHERE status = 'A confirmar' ");
		
		return em.createQuery(sql.toString(), Number.class)
				.getSingleResult();
	}

	@Override
	public Number getQuantityEmPreparo() {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT COUNT(O.id) FROM ")
		   .append(ClientOrder.class.getName()).append(" O ")
		   .append(" WHERE status = 'Em preparo' ");
		
		return em.createQuery(sql.toString(), Number.class)
				.getSingleResult();
	}

	@Override
	public Number getQuantityEnviado() {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT COUNT(O.id) FROM ")
		   .append(ClientOrder.class.getName()).append(" O ")
		   .append(" WHERE status = 'Enviado' ");
		
		return em.createQuery(sql.toString(), Number.class)
				.getSingleResult();
	}

	@Override
	public Number getQuantityFinalizado() {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT COUNT(O.id) FROM ")
		   .append(ClientOrder.class.getName()).append(" O ")
		   .append(" WHERE status = 'Finalizado' ");
		
		return em.createQuery(sql.toString(), Number.class)
				.getSingleResult();
	}

	@Override
	public Number getQuantityCancelado() {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT COUNT(O.id) FROM ")
		   .append(ClientOrder.class.getName()).append(" O ")
		   .append(" WHERE status = 'Cancelado' ");
		
		return em.createQuery(sql.toString(), Number.class)
				.getSingleResult();
	}
	
	// Getters and Setters
	public KeepClientSBean getClientSBean() {
		return clientSBean;
	}
	public void setClientSBean(KeepClientSBean clientSBean) {
		this.clientSBean = clientSBean;
	}
	public KeepCartSBean getCartSBean() {
		return cartSBean;
	}
	public void setCartSBean(KeepCartSBean cartSBean) {
		this.cartSBean = cartSBean;
	}
}
