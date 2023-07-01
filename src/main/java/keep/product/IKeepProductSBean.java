package keep.product;

import java.util.List;

import javax.ejb.Local;

import model.Product;
import to.TOProduct;
import to.TOProductFilter;
import utils.IMainMethodsKeep;

@Local
public interface IKeepProductSBean extends IMainMethodsKeep<Product, TOProduct> {
	public void disable(TOProduct to);
	public List<TOProduct> filterProducts(TOProductFilter filter);
	public List<TOProduct> getProductsByCategoryId(int id);
}
