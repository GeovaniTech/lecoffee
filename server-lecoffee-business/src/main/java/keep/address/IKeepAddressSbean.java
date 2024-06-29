package keep.address;

import java.util.List;

import jakarta.ejb.Local;
import to.client.address.TOAddress;
import to.client.address.TOFilterAddress;

@Local
public interface IKeepAddressSbean {
	public void save(TOAddress address);
	public void remove(TOAddress address);
	public TOAddress findById(int id);
	public Integer getCount(TOFilterAddress filter);
	public List<TOAddress> searchAdresses(TOFilterAddress filter);
}
