package managedBean;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.PrimeFaces;

import keep.cart.KeepCartSBean;
import keep.cart.item.KeepItemSBean;
import keep.product.KeepProductSBean;
import model.Cart;
import model.Item;
import model.Product;
import to.TOClient;
import utils.AbstractBean;
import utils.Cookies;
import utils.RedirectUrl;

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
	
	public Double getTotalOrder() {
		Double value = this.getCartSBean().getTotalOrder(this.getCart().getId());
		
		if(value == null) {
			value = 0.0;
		}
		
		return value;
	}
	
	public void addProduct(int id) {
		Product product = this.getProductSBean().findById(id);
		
		for(int i = 0;  i < this.getCart().getItems().size(); i++) {
			if(this.getCart().getItems().get(i).getProduct().getId() == product.getId()) {
				Item item = this.getCart().getItems().get(i);
				
				item.setAmount(item.getAmount() + 1);
				
				this.getCart().getItems().set(i, item);
				this.change();
				
				PrimeFaces.current().executeScript("counterTotalProducts(); updateBottomCard(); counterTotalProductsDesktop(); updateProductsCart(); updateProductsCategories();");
				
				return;
			}
		}
		
		Item item = new Item();
		item.setProduct(product);
		item.setAmount(item.getAmount() + 1);
		
		this.getCart().getItems().add(item);
		this.change();
		
		PrimeFaces.current().executeScript("counterTotalProducts(); updateBottomCard(); counterTotalProductsDesktop(); updateProductsCart(); updateProductsCategories();");
	}
	
	public void removeProduct(int id) {
		Product product = this.getProductSBean().findById(id);
		
		for(int i = 0; i < this.getCart().getItems().size(); i++) {
			if(this.getCart().getItems().get(i).getProduct().getId() == product.getId()) {
				Item item = this.getCart().getItems().get(i);
				
				if(item.getAmount() > 0) {
					item.setAmount(item.getAmount() - 1);
					
					if(item.getAmount() == 0) {
						this.getCart().getItems().remove(i);
					}
				} 
				
				this.change();
			}
		}
		
		PrimeFaces.current().executeScript("counterTotalProducts(); updateBottomCard(); counterTotalProductsDesktop(); updateProductsCart(); updateProductsCategories();");
	}
	
	public void change() {
		this.getCartSBean().change(this.getCart());
	}
	
	public void createCart() {
		Integer id = Cookies.getCartIdFromCookie();

		Cart newCart = new Cart();
		
		if(id == null) {
			newCart = newCart();
			id = newCart.getId();
		}
		
		newCart = this.getCartSBean().findById(id);
		
		if(newCart == null) {
			newCart = newCart();
		}
		
		this.setCart(newCart);
		
		Cookie cookie = new Cookie("cart", this.getCart().getId() + "");
		cookie.setPath("/lecoffee");
		cookie.setMaxAge(Integer.MAX_VALUE);
		
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		response.addCookie(cookie);
	}
	
	public Cart newCart() {
		Cart cart = new Cart();
		this.getCartSBean().save(cart);
		
		return cart;
	}
	
	public void continueToAddress() {
		TOClient client = getClient();
		
		if(client == null) {
			PrimeFaces.current().executeScript("openCart(); openDoLogin();");
		} else {
			PrimeFaces.current().executeScript("openCart(); openAddress();");
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
