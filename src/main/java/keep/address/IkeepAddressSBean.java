package keep.address;

import java.util.List;

import javax.ejb.Local;

import model.Address;

@Local
public interface IkeepAddressSBean {
	public void save(Address address, int client_id);
	public void change(Address address, int client_id);
	public void remove(Address address, int client_id);
	public Address findById(int id);
	public List<Address> getClientAddresses(int client_id);
}
