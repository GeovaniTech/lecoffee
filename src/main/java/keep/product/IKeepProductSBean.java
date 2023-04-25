package keep.product;

import java.util.List;

import javax.ejb.Local;

import model.Product;

@Local
public interface IKeepProductSBean  {
	public void save(Product product);
	public void change(Product product);
	public void disable(Product product);
	public Product findById(int id);
	public List<Product> list();
}
