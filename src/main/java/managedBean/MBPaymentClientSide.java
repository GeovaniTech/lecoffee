package managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import keep.payment.KeepPaymentSBean;
import to.TOPayment;
import utils.AbstractBean;

@Named("MBPaymentClientSide")
@SessionScoped
public class MBPaymentClientSide extends AbstractBean {
	private static final long serialVersionUID = -6720461491956556481L;
	
	private KeepPaymentSBean paymentSBean;
	private TOPayment paymentSelected;
	private List<TOPayment> payments;
	
	public MBPaymentClientSide() {
		this.setPaymentSelected(new TOPayment());
		this.setPaymentSBean(new KeepPaymentSBean());
		this.setPayments(new ArrayList<TOPayment>());
		
		list();
	}
	
	public void list() {
		this.setPayments(this.getPaymentSBean().getPayments());
	}
	
	public void selectPaymentMethod(int id) {
		this.setPaymentSelected(this.getPaymentSBean().findById(id));
		
		PrimeFaces.current().ajax().update("updateBottomCardOrder(); ");
	}

	//Getters and Setters
	public KeepPaymentSBean getPaymentSBean() {
		return paymentSBean;
	}
	public void setPaymentSBean(KeepPaymentSBean paymentSBean) {
		this.paymentSBean = paymentSBean;
	}
	public TOPayment getPaymentSelected() {
		return paymentSelected;
	}
	public void setPaymentSelected(TOPayment paymentSelected) {
		this.paymentSelected = paymentSelected;
	}
	public List<TOPayment> getPayments() {
		return payments;
	}
	public void setPayments(List<TOPayment> payments) {
		this.payments = payments;
	}
}
