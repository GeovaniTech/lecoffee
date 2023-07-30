package managedBean;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import keep.address.KeepAddressSBean;
import keep.order.KeepOrderSBean;
import to.TOAddress;
import to.TOClientOrder;
import utils.AbstractBean;

@Named("MBListOrders")
@RequestScoped
public class MBListOrders extends AbstractBean {
	private static final long serialVersionUID = -7432808333308843693L;
	
	private KeepOrderSBean orderSbean;
	private KeepAddressSBean addressSBean;
	private List<TOClientOrder> clientOrders;
	private List<TOClientOrder> allOrdersAdmin;
	private List<TOAddress> clientAddress;
	
	public MBListOrders() {
		this.setOrderSbean(new KeepOrderSBean());
		this.setAddressSBean(new KeepAddressSBean());
		this.setClientOrders(this.getClientOrders());
	}
	
	public List<TOClientOrder> getAllOrders() {
		return  this.getOrderSbean().list();
	}
	
	public List<TOClientOrder> getOrdersByClient() {
		if(this.getClient() != null) {
			return this.getOrderSbean().listByClientId(this.getClient().getId());
		}
		
		return null;
	}
	
	public List<TOAddress> allClientAddresses() {
		if(this.getClient() != null) {
			return this.getAddressSBean().getClientAddresses(this.getClient().getId());
		}
		
		return null;
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

	public KeepAddressSBean getAddressSBean() {
		return addressSBean;
	}

	public void setAddressSBean(KeepAddressSBean addressSBean) {
		this.addressSBean = addressSBean;
	}

	public List<TOAddress> getClientAddress() {
		return this.allClientAddresses();
	}

	public void setClientAddress(List<TOAddress> clientAddress) {
		this.clientAddress = clientAddress;
	}
}
