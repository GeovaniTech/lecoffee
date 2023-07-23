package lovs;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

import keep.client.KeepClientSBean;
import to.TOClient;
import to.TOFilterLovClient;
import utils.AbstractLov;

@Named("lovClient")
@ViewScoped
public class LovClient extends AbstractLov<TOClient, TOFilterLovClient> implements Serializable {
	private static final long serialVersionUID = -7273408973088160700L;
	
	private TOFilterLovClient filter;
	private KeepClientSBean keepClientSBean;
	private TOClient clientSelected;
	
	public LovClient() {
		this.setFilter(new TOFilterLovClient());
		this.setKeepClientSBean(new KeepClientSBean());
		this.setClientSelected(new TOClient());
	}
	
	@Override
	public List<TOClient> getDataPage() {
		return this.getKeepClientSBean().listClientsLov(this.getFilter());
	}

	@Override
	public String getPathLovXhtml() {
		return "/resources/lovs/lovClient";
	}

	@Override
	public void clearFilters() {
		this.setFilter(new TOFilterLovClient());
	}
	
	@Override
	public void onRowSelect(SelectEvent<TOClient> event) {
		PrimeFaces.current().dialog().closeDynamic(this.getClientSelected());
	}

	// Getters and Setters
	public TOFilterLovClient getFilter() {
		return filter;
	}
	public void setFilter(TOFilterLovClient filter) {
		this.filter = filter;
	}
	public KeepClientSBean getKeepClientSBean() {
		return keepClientSBean;
	}
	public void setKeepClientSBean(KeepClientSBean keepClientSBean) {
		this.keepClientSBean = keepClientSBean;
	}
	public TOClient getClientSelected() {
		return clientSelected;
	}
	public void setClientSelected(TOClient clientSelected) {
		this.clientSelected = clientSelected;
	}
}
