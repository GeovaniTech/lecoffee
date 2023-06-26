package managedBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import keep.cart.KeepCartSBean;
import keep.order.KeepOrderSBean;
import to.TOClientOrder;
import utils.AbstractFilterBean;
import utils.IMainMethodsBean;

@Named("MBClientOrder")
@ViewScoped
public class MBClientOrder extends AbstractFilterBean implements IMainMethodsBean {
	private static final long serialVersionUID = -7662896969043865803L;

	private KeepOrderSBean orderSBean;
	private KeepCartSBean cartSBean;
	private List<TOClientOrder> orders;
	private TOClientOrder orderSelected;
	
	public MBClientOrder() {
		this.setOrderSBean(new KeepOrderSBean());
		this.setCartSBean(new KeepCartSBean());
		this.setOrders(new ArrayList<TOClientOrder>());
		this.setOrderSelected(new TOClientOrder());
		
		this.list();
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
		
		System.out.println("Atualizando Pedidos...");
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
}
