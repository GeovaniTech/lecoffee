package keep.cart;

import java.util.Date;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import keep.cart.item.KeepItemSBean;
import model.Cart;
import model.Item;
import utils.AbstractManter;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepCartSBean extends AbstractManter implements IkeepCartSBean, IKeepCartSBeanRemote {
	private KeepItemSBean itemSbean;
	
	public KeepCartSBean() {
		this.setItemSbean(new KeepItemSBean());
	}
	
	@Override
	public void save(Cart cart) {
		for(Item item : cart.getItems()) {
			this.getItemSbean().save(item);
		}
		
		cart.setCreationDate(new Date());
		
		em.getTransaction().begin();
		em.persist(cart);
		em.getTransaction().commit();
	}

	@Override
	public void change(Cart cart) {
		for(Item item :  cart.getItems()) {
			if(item.getId() != null) {
				this.getItemSbean().change(item);
			} else {
				this.getItemSbean().save(item);
			}
		}
		
		em.getTransaction().begin();
		em.merge(cart);
		em.getTransaction().commit();
	}

	@Override
	public void remove(Cart cart) {
		em.getTransaction().begin();
		em.remove(em.contains(cart) ? cart : em.merge(cart));
		em.getTransaction().commit();
	}

	@Override
	public Cart findById(int id) {
		return em.find(Cart.class, id);
	}
	
	@Override
	public void getTotalOrder(int cart_id) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT SUM()");
	}
	
	//Getters and Setters 
	public KeepItemSBean getItemSbean() {
		return itemSbean;
	}
	public void setItemSbean(KeepItemSBean itemSbean) {
		this.itemSbean = itemSbean;
	}
}
