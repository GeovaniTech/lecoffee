package keep.product;

import java.util.List;

import javax.ejb.Local;

import model.Product;
import to.TOProductFilter;

@Local
public interface IKeepProductSBean  {
	public void save(Product product);
	public void change(Product product);
	public void disable(Product product);
	public void remove(Product product);
	public Product findById(int id);
	public List<Product> list();
	public List<Product> filterProducts(TOProductFilter filter);
}
