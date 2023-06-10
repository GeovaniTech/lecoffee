package managedBean;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.json.JSONObject;
import org.primefaces.PrimeFaces;

import keep.address.KeepAddressSBean;
import model.Address;
import utils.AbstractBean;
import utils.CepUtil;
import utils.MessageUtil;

@Named("MBAddress")
@SessionScoped
public class MBAddress extends AbstractBean {
	private static final long serialVersionUID = 434357587597049751L;
	
	private Address address;
	private Address selectedAddress;
	private List<Address> clientAddress;
	private KeepAddressSBean addressSBean;
	
	private boolean newAddressVisible;
	
	public MBAddress() {
		this.setAddress(new Address());
		this.setAddressSBean(new KeepAddressSBean());
		this.setClientAddress(new ArrayList<Address>());
		this.setNewAddressVisible(false);
		
		this.updateClientAddresses();
	}
	
	public void save() {
		this.getAddressSBean().save(this.getAddress(), getClient().getId());
		this.setAddress(new Address());
		
		this.updateClientAddresses();
	}
	
	public void change() {
		this.getAddressSBean().change(this.getAddress(), getClient().getId());
		
		this.updateClientAddresses();
	}
	
	public void remove() {
		this.getAddressSBean().remove(this.getAddress(), getClient().getId());
		
		this.updateClientAddresses();
	}
	
	public void updateClientAddresses() {
		this.setClientAddress(this.getAddressSBean().getClientAddresses(getClient().getId()));
	}
	
	public void changeNewAddressView() {
		if(this.isNewAddressVisible()) {
			this.setNewAddressVisible(false);
		} else {
			this.setNewAddressVisible(true);
		}
		
		PrimeFaces.current().ajax().update(":formAddress");
	}
	
	public void getCEPInformations() {
		if(this.getAddress().getCep() != null && !this.getAddress().getCep().toString().equals("")) {
			JSONObject informations = CepUtil.getCEPInformations(this.getAddress().getCep().toString());
			
			if(!informations.getString("localidade").equals("Blumenau")) {
				MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("cep_from_other_city"), null, FacesMessage.SEVERITY_ERROR);
				return;
			}
			
			this.getAddress().setNeighborhood(informations.getString("bairro"));
			this.getAddress().setStreet(informations.getString("logradouro"));
		}
	}
	
	public void selectAddress(ActionEvent event) {
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		
		Address address = this.getAddressSBean().findById(Integer.parseInt(id));
		
		System.out.println("TOMA ESSE ADD SEU MERDA: " +  address);
	}

	//Getters and Setters
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public List<Address> getClientAddress() {
		return clientAddress;
	}
	public void setClientAddress(List<Address> clientAddress) {
		this.clientAddress = clientAddress;
	}
	public KeepAddressSBean getAddressSBean() {
		return addressSBean;
	}
	public void setAddressSBean(KeepAddressSBean addressSBean) {
		this.addressSBean = addressSBean;
	}
	public boolean isNewAddressVisible() {
		return newAddressVisible;
	}
	public void setNewAddressVisible(boolean newAddressVisible) {
		this.newAddressVisible = newAddressVisible;
	}
	public Address getSelectedAddress() {
		return selectedAddress;
	}
	public void setSelectedAddress(Address selectedAddress) {
		this.selectedAddress = selectedAddress;
	}
}
