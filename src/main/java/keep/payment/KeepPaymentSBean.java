package keep.payment;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.modelmapper.ModelMapper;

import model.Payment;
import to.TOPayment;
import utils.AbstractManter;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepPaymentSBean extends AbstractManter implements IKeepPaymentSBean, IKeepPaymentSBeanRemote {

	private ModelMapper converter;
	
	public KeepPaymentSBean() {
		this.setConverter(new ModelMapper());
	}
	
	@Override
	public void save(TOPayment payment) {
		Payment model = this.getConverter().map(payment, Payment.class);
		
		em.getTransaction().begin();
		em.persist(model);
		em.getTransaction().commit();
	}

	@Override
	public void change(TOPayment payment) {
		Payment model = this.getConverter().map(payment, Payment.class);
		
		em.getTransaction().begin();
		em.merge(model);
		em.getTransaction().commit();
	}

	@Override
	public void remove(TOPayment payment) {
		Payment model = this.findModelById(payment.getId());
		
		em.getTransaction().begin();
		em.remove(em.contains(model) ? model : em.merge(model));
		em.getTransaction().commit();
	}

	@Override
	public TOPayment findById(int id) {
		Payment payment = em.find(Payment.class, id);

		return this.getConverter().map(payment, TOPayment.class);
	}
	
	@Override
	public Payment findModelById(int id) {
		return em.find(Payment.class, id);
	}
	
	@Override
	public List<TOPayment> getPayments() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT P FROM ");
		sql.append(Payment.class.getName()).append(" P ");
		
		List<Payment> results = em.createQuery(sql.toString(), Payment.class)
										.getResultList();
		
		List<TOPayment> convertedResults = new ArrayList<TOPayment>();
		
		for(Payment payment : results) {
			TOPayment to = this.getConverter().map(payment, TOPayment.class);
			
			convertedResults.add(to);
		}
		
		return convertedResults;
	}

	//Getters and Setters
	public ModelMapper getConverter() {
		return converter;
	}
	public void setConverter(ModelMapper converter) {
		this.converter = converter;
	}
}
