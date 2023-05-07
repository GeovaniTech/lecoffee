package managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import keep.client.KeepClientSBean;
import to.TOClient;
import utils.AbstractBean;
import utils.AbstractFilterBean;

@Named("MBClient")
@ViewScoped
public class MBClient extends AbstractFilterBean implements Serializable {
	private static final long serialVersionUID = 5677286107782030604L;
	
	private List<TOClient> customers;
	private KeepClientSBean sBean;
	private TOClient client;
	
	public MBClient() {
		this.setCustomers(new ArrayList<TOClient>());
		this.setsBean(new KeepClientSBean());
		updateList();
	}
	
	public void updateList() {
		this.setCustomers(this.getsBean().list());
	}
	
	//Getters and Setters
	public List<TOClient> getCustomers() {
		return customers;
	}

	public void setCustomers(List<TOClient> customers) {
		this.customers = customers;
	}

	public KeepClientSBean getsBean() {
		return sBean;
	}

	public void setsBean(KeepClientSBean sBean) {
		this.sBean = sBean;
	}

	public TOClient getClient() {
		return client;
	}

	public void setClient(TOClient client) {
		this.client = client;
	}
}
