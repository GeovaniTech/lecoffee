package managedBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import keep.cart.KeepCartSBean;
import keep.order.KeepOrderSBean;
import keep.product.KeepProductSBean;
import lovs.LovClient;
import model.Cart;
import model.ClientOrder;
import model.Item;
import model.Product;
import to.TOClient;
import to.TOClientOrder;
import utils.AbstractFilterBean;
import utils.IMainMethodsBean;

@Named("MBClientOrder")
@SessionScoped
public class MBClientOrder extends AbstractFilterBean<TOClientOrder, ClientOrder> implements IMainMethodsBean {
	private static final long serialVersionUID = -7662896969043865803L;

	private KeepOrderSBean orderSBean;
	private KeepCartSBean cartSBean;
	private KeepProductSBean productSBean;
	private List<TOClientOrder> orders;
	private TOClientOrder orderSelected;
	private LovClient lovClient;
	private TOClientOrder manualOrder;
	private Cart manualCart;
	private Boolean startNewOrder;

	public MBClientOrder() {
		this.setOrderSBean(new KeepOrderSBean());
		this.setCartSBean(new KeepCartSBean());
		this.setProductSBean(new KeepProductSBean());
		this.setOrders(new ArrayList<TOClientOrder>());
		this.setOrderSelected(new TOClientOrder());
		this.setLovClient(new LovClient());

		this.list();
		this.initOrder();
	}
	
	public void addProduct(int id) {
		Product product = this.getProductSBean().findById(id);
		
		for(int i = 0;  i < this.getManualCart().getItems().size(); i++) {
			if(this.getManualCart().getItems().get(i).getProduct().getId() == product.getId()) {
				Item item = this.getManualCart().getItems().get(i);
				
				item.setAmount(item.getAmount() + 1);
				
				this.getManualCart().getItems().set(i, item);
				this.getCartSBean().change(this.getManualCart());
				
				//PrimeFaces.current().executeScript("counterTotalProducts(); updateBottomCard(); counterTotalProductsDesktop(); updateProductsCart(); updateProductsCategories(); updateProductsOrder(); updateBottomCardOrder();");
				
				return;
			}
		}
		
		Item item = new Item();
		item.setProduct(product);
		item.setAmount(item.getAmount() + 1);
		
		this.getManualCart().getItems().add(item);
		this.getCartSBean().change(this.getManualCart());
		
		//PrimeFaces.current().executeScript("counterTotalProducts(); updateBottomCard(); counterTotalProductsDesktop(); updateProductsCart(); updateProductsCategories(); updateProductsOrder(); updateBottomCardOrder();");
	}
	
	public void removeProduct(int id) {
		Product product = this.getProductSBean().findById(id);
		
		for(int i = 0; i < this.getManualCart().getItems().size(); i++) {
			if(this.getManualCart().getItems().get(i).getProduct().getId() == product.getId()) {
				Item item = this.getManualCart().getItems().get(i);
				
				if(item.getAmount() > 0) {
					item.setAmount(item.getAmount() - 1);
					
					if(item.getAmount() == 0) {
						this.getManualCart().getItems().remove(i);
					}
				} 
				
				this.getCartSBean().change(this.getManualCart());
			}
		}
		
		//PrimeFaces.current().executeScript("counterTotalProducts(); updateBottomCard(); counterTotalProductsDesktop(); updateProductsCart(); updateProductsCategories(); updateProductsOrder(); updateBottomCardOrder();");
	}
	
	public Integer getQuantityOfProduct(int id) {
		for(Item item : this.getManualCart().getItems()) {
			if(item.getProduct().getId() == id) {
				return item.getAmount();
			}
		}
		
		return 0;
	}
	
	public Double getTotalOrder() {
		Double value = this.getCartSBean().getTotalOrder(this.getManualCart().getId());
		
		if(value == null) {
			value = 0.0;
		}
		
		return value;
	}
	
	public int getTotalQuantityOfProdctsFromCart() {
		int total = 0;
		
		for(Item item : this.getManualCart().getItems()) {
			total += item.getAmount();
		}
		
		return total;
	}
	
	public void initOrder() {
		this.setStartNewOrder(true);
		this.setManualCart(new Cart());
		this.setManualOrder(new TOClientOrder());
		
		this.getCartSBean().save(this.getManualCart());
	}
	
	public void disableOrder() {
		this.initOrder();
		this.setStartNewOrder(false);
	}
	
	public void sendNewOrder() {
		this.getManualOrder().setStatus("Em preparo");
	}
	
	public void acceptOrder(int orderId) {
		TOClientOrder order = this.getOrderSBean().findByIdTO(orderId);
		order.setStatus("Em preparo");
		
		this.getOrderSBean().change(order);
		
		this.list();
	}
	
	public void cancelOrder(int orderId) { 
		TOClientOrder order = this.getOrderSBean().findByIdTO(orderId);
		order.setStatus("Cancelado");
		
		this.getOrderSBean().change(order);
		
		this.list();
	}
	
	public void sendOrder(int orderId) { 
		TOClientOrder order = this.getOrderSBean().findByIdTO(orderId);
		order.setStatus("Enviado");
		order.setTimeSent(new Date());
		
		this.getOrderSBean().change(order);
		
		this.list();
	}
	
	public void finishOrder(int orderId) { 
		TOClientOrder order = this.getOrderSBean().findByIdTO(orderId);
		order.setStatus("Finalizado");
		order.setFinishedDate(new Date());
		
		this.getOrderSBean().change(order);
		
		this.list();
	}
	
    public void onRowSelect(SelectEvent<TOClient> event) {
    	this.getLovClient().setClientSelected(event.getObject());
    }
	
	public int getTotalAConfirmar() {
		return this.getOrderSBean().getQuantityAConfirmar().intValue();
	}
	
	public int getTotalEmPreparo() {
		return this.getOrderSBean().getQuantityEmPreparo().intValue();
	}
	
	public int getTotalEnviado() {
		return this.getOrderSBean().getQuantityEnviado().intValue();
	}
	
	public int getTotalFinalizado() {
		return this.getOrderSBean().getQuantityFinalizado().intValue();
	}
	
	public int getTotalCancelado() {
		return this.getOrderSBean().getQuantityCancelado().intValue();
	}
	
	@Override
	public void save() {
		
	}

	@Override
	public void change() {
		this.getOrderSBean().change(this.getOrderSelected());
		
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}
	
	public Double getTotalOrder(int cart_id) {
		Double value = this.getCartSBean().getTotalOrder(cart_id);
		
		if(value == null) {
			value = 0.0;
		}
		
		return value;
	}

	@Override
	public void list() {
		this.setOrders(new ArrayList<>());
		this.setOrders(this.getOrderSBean().list());
	}

	// Getters and Setters
	public KeepOrderSBean getOrderSBean() {
		return orderSBean;
	}
	public void setOrderSBean(KeepOrderSBean orderSBean) {
		this.orderSBean = orderSBean;
	}
	public List<TOClientOrder> getOrders() {
		return orders;
	}
	public void setOrders(List<TOClientOrder> orders) {
		this.orders = orders;
	}
	public TOClientOrder getOrderSelected() {
		return orderSelected;
	}
	public void setOrderSelected(TOClientOrder orderSelected) {
		this.orderSelected = orderSelected;
	}
	public KeepCartSBean getCartSBean() {
		return cartSBean;
	}
	public void setCartSBean(KeepCartSBean cartSBean) {
		this.cartSBean = cartSBean;
	}
	public LovClient getLovClient() {
		return lovClient;
	}
	public void setLovClient(LovClient lovClient) {
		this.lovClient = lovClient;
	}
	public TOClientOrder getManualOrder() {
		return manualOrder;
	}
	public void setManualOrder(TOClientOrder manualOrder) {
		this.manualOrder = manualOrder;
	}
	public Cart getManualCart() {
		return manualCart;
	}
	public void setManualCart(Cart manualCart) {
		this.manualCart = manualCart;
	}
	public Boolean getStartNewOrder() {
		return startNewOrder;
	}
	public void setStartNewOrder(Boolean startNewOrder) {
		this.startNewOrder = startNewOrder;
	}
	public KeepProductSBean getProductSBean() {
		return productSBean;
	}
	public void setProductSBean(KeepProductSBean productSBean) {
		this.productSBean = productSBean;
	}
}
