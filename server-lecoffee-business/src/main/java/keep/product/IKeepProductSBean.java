package keep.product;

import java.util.List;

import jakarta.ejb.Local;
import to.product.TOFilterProduct;
import to.product.TOProduct;

@Local
public interface IKeepProductSBean {
	public void save(TOProduct product);
	public void remove(TOProduct product);
	public void disable(TOProduct product);
	public void active(TOProduct product);
	public TOProduct findById(String id);
	public Integer getCount(TOFilterProduct filter);
	public List<TOProduct> searchProducts(TOFilterProduct filter);
}
