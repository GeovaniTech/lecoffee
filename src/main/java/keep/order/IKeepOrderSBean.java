package keep.order;

import java.util.List;

import javax.ejb.Local;

import model.ClientOrder;
import to.TOClientOrder;
import utils.IMainMethodsKeep;

@Local
public interface IKeepOrderSBean extends IMainMethodsKeep<ClientOrder, TOClientOrder>{
	public List<TOClientOrder> listByClientId(int client_id);
}
