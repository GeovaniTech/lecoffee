package keep.earnings;

import java.util.Calendar;
import java.util.Date;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.Query;

import model.ClientOrder;
import utils.AbstractManter;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepEarningsSBean extends AbstractManter implements IKeepEarningsSBean, IKeepEarningsSBeanRemote {

	@Override
	public Double getTotalADay() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT SUM(item.product.price * item.amount) ")
		   .append(" FROM ").append(ClientOrder.class.getName()).append(" O ")
		   .append(" JOIN O.cart.items AS item ")
		   .append(" WHERE O.finishedDate >= :beginningWeek AND O.finishedDate <= :weekend ");
		
		Calendar cal = Calendar.getInstance();
		
		cal.add(Calendar.DAY_OF_MONTH, -1);
		
		Query query = em.createQuery(sql.toString(), Number.class);
		query.setParameter("beginningWeek", cal.getTime());
		cal.add(Calendar.DAY_OF_MONTH, 1);
		query.setParameter("weekend", cal.getTime());
		
		Number result = (Number) query.getSingleResult();
		
		return result != null ? result.doubleValue() : 0.0;
	}

	@Override
	public Double getTotalAWeek() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT SUM(item.product.price * item.amount) ")
		   .append(" FROM ").append(ClientOrder.class.getName()).append(" O ")
		   .append(" JOIN O.cart.items AS item ")
		   .append(" WHERE O.finishedDate >= :beginningWeek AND O.finishedDate <= :weekend ");
		
		Calendar cal = Calendar.getInstance();
		
		cal.add(Calendar.DAY_OF_WEEK, -7);
		
		Number result = em.createQuery(sql.toString(), Number.class)
				.setParameter("beginningWeek", cal.getTime())
				.setParameter("weekend", new Date())
				.getSingleResult(); 
		
		return result != null ? result.doubleValue() : 0.0;
	}

	@Override
	public Double getTotalAMonth() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT SUM(item.product.price * item.amount) ")
		   .append(" FROM ").append(ClientOrder.class.getName()).append(" O ")
		   .append(" JOIN O.cart.items AS item ")
		   .append(" WHERE O.finishedDate >= :beginningWeek AND O.finishedDate <= :weekend ");
		
		Calendar cal = Calendar.getInstance();
		
		cal.add(Calendar.MONTH, -1);
		
		Number result = em.createQuery(sql.toString(), Number.class)
				.setParameter("beginningWeek", cal.getTime())
				.setParameter("weekend", new Date())
				.getSingleResult(); 
		
		return result != null ? result.doubleValue() : 0.0;
	}

	@Override
	public Double getTotalAYear() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT SUM(item.product.price * item.amount) ")
		   .append(" FROM ").append(ClientOrder.class.getName()).append(" O ")
		   .append(" JOIN O.cart.items AS item ")
		   .append(" WHERE O.finishedDate >= :beginningWeek AND O.finishedDate <= :weekend ");
		
		Calendar cal = Calendar.getInstance();
		
		cal.add(Calendar.YEAR, -1);
		
		Query query = em.createQuery(sql.toString(), Number.class);
		
		query.setParameter("beginningWeek", cal.getTime());
		
		cal.add(Calendar.YEAR, 1);
		
		query.setParameter("weekend", cal.getTime());
		
		Number result = (Number) query.getSingleResult();
		
		return result != null ? result.doubleValue() : 0.0;
	}

}
