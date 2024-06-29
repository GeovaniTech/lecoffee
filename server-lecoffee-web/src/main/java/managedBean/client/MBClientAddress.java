package managedBean.client;

import java.util.List;

import org.primefaces.event.SelectEvent;

import abstracts.AbstractFilterMBean;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import to.client.TOClient;
import to.client.address.TOAddress;
import to.client.address.TOFilterAddress;

@Named("MBClientAddress")
@ViewScoped
public class MBClientAddress extends AbstractFilterMBean<TOAddress, TOFilterAddress> {
	private static final long serialVersionUID = -1358854487037536544L;
	
	public static final String MANAGED_BEAN_NAME = "MBClientAddress";

	private TOClient client;
	
	@PostConstruct
	@Override
	public void init() {
		
	}

	@Override
	public List<TOAddress> getData(TOFilterAddress filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getCount(TOFilterAddress filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearFilters() {
		this.setFilter(new TOFilterAddress());		
	}

	@Override
	public void onRowSelect(SelectEvent<TOAddress> event) {
		// TODO Auto-generated method stub
	}

	
	// Getters and Setters
	public TOClient getClient() {
		return client;
	}

	public void setClient(TOClient client) {
		this.client = client;
	}
}
