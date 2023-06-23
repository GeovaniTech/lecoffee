package keep.order;

import javax.ejb.Local;

import model.ClientOrder;
import to.TOClientOrder;
import utils.IMainMethodsKeep;

@Local
public interface IKeepOrderSBean extends IMainMethodsKeep<ClientOrder, TOClientOrder>{

}
