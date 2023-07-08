package managedBean;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import keep.order.KeepOrderSBean;
import to.TOClientOrder;
import utils.AbstractBean;

@Named("MBListOrders")
@RequestScoped
public class MBListOrders extends AbstractBean {
	private static final long serialVersionUID = -7432808333308843693L;
	
	private KeepOrderSBean orderSbean;
	private List<TOClientOrder> clientOrders;
	private List<TOClientOrder> allOrdersAdmin;
	
	public MBListOrders() {
		this.setOrderSbean(new KeepOrderSBean());
		this.setClientOrders(this.getClientOrders());
	}
	
	public List<TOClientOrder> getAllOrders() {
		return  this.getOrderSbean().list();
	}
	
	public List<TOClientOrder> getOrdersByClient() {
		return this.getOrderSbean().listByClientId(this.getClient().getId());
	}

	// Getters and Setters
	public KeepOrderSBean getOrderSbean() {
		return orderSbean;
	}

	public void setOrderSbean(KeepOrderSBean orderSbean) {
		this.orderSbean = orderSbean;
	}

	public List<TOClientOrder> getClientOrders() {
		return this.getOrdersByClient();
	}

	public void setClientOrders(List<TOClientOrder> clientOrders) {
		this.clientOrders = clientOrders;
	}

	public List<TOClientOrder> getAllOrdersAdmin() {
		return this.getAllOrders();
	}

	public void setAllOrdersAdmin(List<TOClientOrder> allOrdersAdmin) {
		this.allOrdersAdmin = allOrdersAdmin;
	}
}
