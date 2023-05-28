package managedBean;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import keep.cart.KeepCartSBean;
import keep.cart.item.KeepItemSBean;
import keep.product.KeepProductSBean;
import model.Cart;
import model.Item;
import model.Product;
import utils.AbstractBean;
import utils.Cookies;

@Named("MBCart")
@SessionScoped
public class MBCart extends AbstractBean {
	private static final long serialVersionUID = -5497296575605508749L;
	
	private Cart cart;
	private KeepCartSBean cartSBean;
	private KeepItemSBean itemSBean;
	private KeepProductSBean productSBean;
	
	public MBCart() {
		// SBeans
		this.setCartSBean(new KeepCartSBean());
		this.setItemSBean(new KeepItemSBean());
		this.setProductSBean(new KeepProductSBean());
		
		this.setCart(new Cart());
		this.createCart();
	}
	
	public int getTotalQuantityOfProdctsFromCart() {
		int total = 0;
		
		for(Item item : this.getCart().getItems()) {
			total += item.getAmount();
		}
		
		return total;
	}
	
	public Integer getQuantityOfProduct(int id) {
		for(Item item : this.getCart().getItems()) {
			if(item.getProduct().getId() == id) {
				return item.getAmount();
			}
		}
		
		return 0;
	}
	
	public void addProduct(int id) {
		Product product = this.getProductSBean().findById(id);
		
		for(int i = 0;  i < this.getCart().getItems().size(); i++) {
			if(this.getCart().getItems().get(i).getProduct().getId() == product.getId()) {
				Item item = this.getCart().getItems().get(i);
				
				item.setAmount(item.getAmount() + 1);
				
				this.getCart().getItems().set(i, item);
				this.change();
				
				PrimeFaces.current().executeScript("counterTotalProducts();");
				
				return;
			}
		}
		
		Item item = new Item();
		item.setProduct(product);
		item.setAmount(item.getAmount() + 1);
		
		this.getCart().getItems().add(item);
		this.change();
		
		PrimeFaces.current().executeScript("counterTotalProducts();");
	}
	
	public void removeProduct(int id) {
		Product product = this.getProductSBean().findById(id);
		
		for(int i = 0; i < this.getCart().getItems().size(); i++) {
			if(this.getCart().getItems().get(i).getProduct().getId() == product.getId()) {
				Item item = this.getCart().getItems().get(i);
				
				if(item.getAmount() > 0) {
					item.setAmount(item.getAmount() - 1);
				} else {
					this.getCart().getItems().remove(i);
				}
				
				this.change();
			}
		}
		
		PrimeFaces.current().executeScript("counterTotalProducts();");
	}
	
	public void change() {
		this.getCartSBean().change(this.getCart());
	}
	
	public void createCart() {
		Integer id = Cookies.getCartIdFromCookie();
		
		if(id != null) {
			this.setCart(this.getCartSBean().findById(id));
		} else {
			this.getCartSBean().save(this.getCart());
			
			System.out.println(this.getCart().getId());
		}
	}

	//Getters and Setters
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public KeepCartSBean getCartSBean() {
		return cartSBean;
	}
	public void setCartSBean(KeepCartSBean cartSBean) {
		this.cartSBean = cartSBean;
	}
	public KeepItemSBean getItemSBean() {
		return itemSBean;
	}
	public void setItemSBean(KeepItemSBean itemSBean) {
		this.itemSBean = itemSBean;
	}
	public KeepProductSBean getProductSBean() {
		return productSBean;
	}
	public void setProductSBean(KeepProductSBean productSBean) {
		this.productSBean = productSBean;
	}
}
