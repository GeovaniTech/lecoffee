package keep.cart;

import javax.ejb.Local;

import model.Cart;
import to.TOCart;
import utils.IMainMethodsKeep;

@Local
public interface IkeepCartSBean extends IMainMethodsKeep<Cart, TOCart> {
	public void save(Cart cart);
	public void change(Cart cart);
	public void remove(Cart cart);
	public Cart findById(int id);
	public Double getTotalOrder(int cart_id);
}
