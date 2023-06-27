package keep.earnings;

import javax.ejb.Local;

@Local
public interface IKeepEarningsSBean {
	public Double getTotalADay();
	public Double getTotalAWeek();
	public Double getTotalAMonth();
	public Double getTotalAYear();
}
