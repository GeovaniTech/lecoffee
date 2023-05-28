package keep.cart;

import javax.ejb.Local;

import model.Cart;

@Local
public interface IkeepCartSBean {
	public void save(Cart cart);
	public void change(Cart cart);
	public void remove(Cart cart);
	public Cart findById(int id);
}
