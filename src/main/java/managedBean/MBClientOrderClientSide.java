package managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import keep.client.KeepClientSBean;
import keep.order.KeepOrderSBean;
import keep.payment.KeepPaymentSBean;
import to.TOAddress;
import to.TOCart;
import to.TOClientOrder;
import to.TOPayment;
import utils.AbstractBean;

@Named("MBClientOrderClientSide")
@SessionScoped
public class MBClientOrderClientSide extends AbstractBean  {
	private static final long serialVersionUID = -4737960995649792058L;

	private KeepOrderSBean orderSBean;
	private KeepPaymentSBean paymentSBean;
	private KeepClientSBean clientSBean;
	private TOClientOrder clientOrder;
	private List<TOClientOrder> clientOrders;
	private List<TOClientOrder> finishedOrders;
	
	public MBClientOrderClientSide() {
		this.setOrderSBean(new KeepOrderSBean());
		this.setPaymentSBean(new KeepPaymentSBean());
		this.setClientOrder(new TOClientOrder());
		this.setClientOrders(new ArrayList<TOClientOrder>());
		this.setFinishedOrders(new ArrayList<TOClientOrder>());
		
		this.list();
	}
	
	public void list() {
		this.setClientOrders(this.getOrderSBean().listByClientId(this.getClient().getId()));
		this.setFinishedOrders(this.getOrderSBean().listFinishedsByClientId(this.getClient().getId()));
	}
	
	public void finishOrder(TOAddress address, TOPayment payment, TOCart cart) {
		this.getClientOrder().setAddress(address);
		this.getClientOrder().setCart(cart);
		this.getClientOrder().setPayment(this.getPaymentSBean().findById(payment.getId()));
		this.getClientOrder().setClient(this.getClient());
		this.getClientOrder().setClient_id(this.getClient().getId());
		this.getClientOrder().setStatus("A confirmar");
		
		this.getOrderSBean().save(this.getClientOrder());
		
		this.list();
	}
	
	// Getters and Setters
	public KeepOrderSBean getOrderSBean() {
		return orderSBean;
	}
	public void setOrderSBean(KeepOrderSBean orderSBean) {
		this.orderSBean = orderSBean;
	}
	public TOClientOrder getClientOrder() {
		return clientOrder;
	}
	public void setClientOrder(TOClientOrder clientOrder) {
		this.clientOrder = clientOrder;
	}
	public List<TOClientOrder> getClientOrders() {
		return clientOrders;
	}
	public void setClientOrders(List<TOClientOrder> clientOrders) {
		this.clientOrders = clientOrders;
	}
	public KeepPaymentSBean getPaymentSBean() {
		return paymentSBean;
	}
	public void setPaymentSBean(KeepPaymentSBean paymentSBean) {
		this.paymentSBean = paymentSBean;
	}
	public KeepClientSBean getClientSBean() {
		return clientSBean;
	}
	public void setClientSBean(KeepClientSBean clientSBean) {
		this.clientSBean = clientSBean;
	}

	public List<TOClientOrder> getFinishedOrders() {
		return finishedOrders;
	}

	public void setFinishedOrders(List<TOClientOrder> finishedOrders) {
		this.finishedOrders = finishedOrders;
	}
}
