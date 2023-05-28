package keep.cart.item;

import javax.ejb.Local;

import model.Item;

@Local
public interface IKeepItemSBean {
	public void save(Item item);
	public void change(Item item);
	public void remove(Item item);
	public Item findById(int id);
}
