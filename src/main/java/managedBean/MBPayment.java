package managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import keep.payment.KeepPaymentSBean;
import to.TOPayment;
import utils.AbstractFilterBean;

@Named("MBPayment")
@ViewScoped
public class MBPayment extends AbstractFilterBean {
	private static final long serialVersionUID = 1580040667992795847L;
	
	private KeepPaymentSBean paymentSBean;
	private TOPayment payment;
	private List<TOPayment> payments;
	
	public MBPayment() {
		this.setPayment(new TOPayment());
		this.setPaymentSBean(new KeepPaymentSBean());
		this.setPayments(new ArrayList<TOPayment>());
		this.list();
	}
	
	public void createNewPayment() {
		this.setPayment(new TOPayment());
		this.setEditObject(false);
	}
	
	public void save() {
		this.getPaymentSBean().save(this.getPayment());
		this.list();
	}
	
	public void change() {
		this.getPaymentSBean().change(this.getPayment());
		this.list();
	}
	
	public void remove() {
		this.getPaymentSBean().remove(this.getPayment());
		this.list();
	}
	
	public void list() {
		this.setPayments(this.getPaymentSBean().getPayments());
	}

	//Getters and Setters
	public KeepPaymentSBean getPaymentSBean() {
		return paymentSBean;
	}
	public void setPaymentSBean(KeepPaymentSBean paymentSBean) {
		this.paymentSBean = paymentSBean;
	}
	public TOPayment getPayment() {
		return payment;
	}
	public void setPayment(TOPayment payment) {
		this.payment = payment;
	}
	public List<TOPayment> getPayments() {
		return payments;
	}
	public void setPayments(List<TOPayment> payments) {
		this.payments = payments;
	}
}
