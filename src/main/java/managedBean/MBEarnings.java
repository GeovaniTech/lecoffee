package managedBean;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import keep.earnings.KeepEarningsSBean;
import utils.AbstractBean;

@Named("MBEarnings")
@ViewScoped
public class MBEarnings extends AbstractBean {
	private static final long serialVersionUID = 6323781877935490405L;

	private KeepEarningsSBean earningSBean;
	
	public MBEarnings() {
		this.setEarningSBean(new KeepEarningsSBean());
	}
	
	public Double getTotalADay() {
		return this.getEarningSBean().getTotalADay();
	}
	
	public Double getTotalAWeek() {
		return this.getEarningSBean().getTotalAWeek();
	}
	
	public Double getTotalAMonth() {
		return this.getEarningSBean().getTotalAMonth();
	}
	
	public Double getTotalAYear() {
		return this.getEarningSBean().getTotalAYear();
	}
	
	// Getters and Setters
	public KeepEarningsSBean getEarningSBean() {
		return earningSBean;
	}
	public void setEarningSBean(KeepEarningsSBean earningSBean) {
		this.earningSBean = earningSBean;
	}
}
