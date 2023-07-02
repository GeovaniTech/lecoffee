package keep.order;

import java.util.List;

import javax.ejb.Local;

import model.ClientOrder;
import to.TOClientOrder;
import utils.IMainMethodsKeep;

@Local
public interface IKeepOrderSBean extends IMainMethodsKeep<ClientOrder, TOClientOrder>{
	public List<TOClientOrder> listByClientId(int client_id);
	public List<TOClientOrder> listFinishedsByClientId(int client_id);
	public Number getQuantityAConfirmar();
	public Number getQuantityEmPreparo();
	public Number getQuantityEnviado();
	public Number getQuantityFinalizado();
	public Number getQuantityCancelado();
}
