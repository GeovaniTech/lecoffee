package keep.cart;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import keep.cart.item.KeepItemSBean;
import model.Cart;
import model.Item;
import to.TOCart;
import utils.AbstractManter;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepCartSBean extends AbstractManter<Cart, TOCart> implements IkeepCartSBean, IKeepCartSBeanRemote {
	private KeepItemSBean itemSbean;
	
	public KeepCartSBean() {
		this.setItemSbean(new KeepItemSBean());
		this.setClassTypes(Cart.class, TOCart.class);
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
	public Double getTotalOrder(int cart_id) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT SUM(item.product.price * item.amount) ");
		sql.append(" FROM ").append(Cart.class.getName()).append(" C ");
		sql.append(" JOIN C.items AS item ");
		sql.append(" WHERE C.id = :cart_id ");
		
		Number number = em.createQuery(sql.toString(), Number.class)
						  .setParameter("cart_id", cart_id)
						  .getSingleResult();
		
		return number != null ? number.doubleValue() : 0.0;
	}
	
	@Override
	public void save(TOCart object) {
		Cart cart = this.convertToModel(object);
		
		for(Item item : cart.getItems()) {
			this.getItemSbean().save(item);
		}
		
		cart.setCreationDate(new Date());
		
		em.getTransaction().begin();
		em.persist(cart);
		em.getTransaction().commit();
		
		object.setId(cart.getId());
	}

	@Override
	public void change(TOCart object) {
		Cart cart = this.convertToModel(object);
		
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
	public void remove(TOCart object) {
		Cart cart = this.convertToModel(object);
		
		em.getTransaction().begin();
		em.remove(em.contains(cart) ? cart : em.merge(cart));
		em.getTransaction().commit();
	}

	@Override
	public TOCart findByIdTO(int id) {
		return this.convertToDTO(this.findById(id));
	}

	@Override
	public List<TOCart> list() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT C FROM ").append(Cart.class.getName()).append(" C ");
		
		return this.convertModelResults(em.createQuery(sql.toString(), Cart.class)
										  .getResultList());
	}
	
	//Getters and Setters 
	public KeepItemSBean getItemSbean() {
		return itemSbean;
	}
	public void setItemSbean(KeepItemSBean itemSbean) {
		this.itemSbean = itemSbean;
	}
}
