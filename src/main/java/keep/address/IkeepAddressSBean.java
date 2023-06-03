package keep.address;

import javax.ejb.Local;

import model.Address;

@Local
public interface IkeepAddressSBean {
	public void save(Address address);
	public void change(Address address);
	public void remove(Address address);
	public Address findById(int id);
}
