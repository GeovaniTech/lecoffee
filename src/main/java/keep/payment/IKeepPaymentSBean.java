package keep.payment;

import java.util.List;

import javax.ejb.Local;

import model.Payment;
import to.TOPayment;

@Local
public interface IKeepPaymentSBean {
	public void save(TOPayment payment);
	public void change(TOPayment payment);
	public void remove(TOPayment payment);
	public TOPayment findById(int id);
	public Payment findModelById(int id);
	public List<TOPayment> getPayments();
}
