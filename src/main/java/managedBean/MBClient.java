package managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.json.JSONObject;

import keep.client.KeepClientSBean;
import model.Client;
import to.TOAddress;
import to.TOClient;
import utils.AbstractFilterBean;
import utils.CepUtil;
import utils.EmailUtil;
import utils.MessageUtil;
import utils.StringUtil;

@Named("MBClient")
@ViewScoped
public class MBClient extends AbstractFilterBean<TOClient, Client> implements Serializable {
	private static final long serialVersionUID = 5677286107782030604L;
	
	private List<TOClient> customers;
	private KeepClientSBean sBean;
	private TOClient client;
	private TOAddress address;
	private String email;
	
	public MBClient() {
		this.setClient(new TOClient());
		this.setAddress(new TOAddress());
		this.setCustomers(new ArrayList<TOClient>());
		this.setsBean(new KeepClientSBean());
		
		updateList();
	}
	
	public void initNewClient() {
		this.setClient(new TOClient());
	}
	
	public void updateList() {
		this.setCustomers(this.getsBean().list());
	}
	
	public void updateEmail() {
		this.setEmail(this.getClient().getEmail());
	}
	
	public void save() {
		if(!EmailUtil.validateEmail(this.getClient().getEmail())) {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("invalid_email"), null, FacesMessage.SEVERITY_ERROR);
			return;
		}
		
		if(this.getsBean().verifyClient(this.getClient().getEmail()) ) {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("existing_email"), null, FacesMessage.SEVERITY_ERROR);
			return;
		}
		
		this.getClient().setChangePassword(true);
		this.getsBean().save(this.getClient());
		
		this.updateList();
		
		MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("record_successfully_saved"), null, FacesMessage.SEVERITY_INFO);
	}
	
	public void change() {
		if(StringUtil.isNotNull(this.getEmail()) && !this.getEmail().equals(this.getClient().getEmail())) {
			if(!EmailUtil.validateEmail(this.getEmail())) {
				MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("invalid_email"), null, FacesMessage.SEVERITY_ERROR);
				return;
			}
			
			if(this.getsBean().verifyClient(this.getEmail()) ) {
				MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("existing_email"), null, FacesMessage.SEVERITY_ERROR);
				return;
			}		
		}
				
		this.getClient().setAccountChangeDate(new Date());
		this.getClient().setEmail(this.getEmail());
		this.getsBean().change(this.getClient());
		
		MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("record_changed_successfully"), null, FacesMessage.SEVERITY_INFO);
	}
	
	public void active() {
		this.getClient().setInactivationDate(null);
		this.getClient().setBlocked(false);
		
		change();
	}
	
	public void disable() {
		this.getClient().setInactivationDate(new Date());
		this.getClient().setBlocked(true);
		
		change();
	}
	
	public void remove() {
		this.getsBean().remove(this.getClient());
		this.setClient(new TOClient());
		
		this.updateList();
		
		MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("successfully_deleted_record"), null, FacesMessage.SEVERITY_INFO);
	}
	
	public void getCEPInformations() {
		if(this.getAddress().getCep() != null && !this.getAddress().getCep().toString().equals("")) {
			try {
				JSONObject informations = CepUtil.getCEPInformations(this.getAddress().getCep().toString());
				
				if(!informations.getString("localidade").equals("Blumenau")) {
					MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("cep_from_other_city"), null, FacesMessage.SEVERITY_ERROR);
					return;
				}
				
				this.getAddress().setNeighborhood(informations.getString("bairro"));
				this.getAddress().setStreet(informations.getString("logradouro"));
			} catch (Exception e) {
				MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("cep_not_found"), null, FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}
		}
	}
	
	public void onSaveAddress() {
		this.getClient().getAddresses().add(this.getAddress());
		this.setAddress(new TOAddress());
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
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public TOAddress getAddress() {
		return address;
	}

	public void setAddress(TOAddress address) {
		this.address = address;
	}
	
}
