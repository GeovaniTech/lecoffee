package keep.address;

import java.util.List;

import javax.ejb.Local;

import model.Address;
import to.TOAddress;
import utils.IMainMethodsKeep;

@Local
public interface IkeepAddressSBean extends IMainMethodsKeep<Address, TOAddress> {
	public void save(TOAddress address, int client_id);
	public void change(TOAddress address, int client_id);
	public void remove(Address address, int client_id);
	public Address findById(int id);
	public List<TOAddress> getClientAddresses(int client_id);
}
