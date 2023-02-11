package manter.product;

import javax.ejb.Local;

import model.Product;

@Local
public interface IKeepProductSBean  {
	public void save(Product product);
	public void change(Product product);
	public void disable(Product product);
	public boolean validFields(Product product);
	public void findById(int id);
	public void list();
	
}
