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
import to.TOClient;
import utils.AbstractFilterBean;
import utils.CepUtil;
import utils.EmailUtil;
import utils.MessageUtil;
import utils.StringUtil;

@Named("MBClient")
@ViewScoped
public class MBClient extends AbstractFilterBean implements Serializable {
	private static final long serialVersionUID = 5677286107782030604L;
	
	private List<TOClient> customers;
	private KeepClientSBean sBean;
	private TOClient client;
	private String email;
	
	public MBClient() {
		this.setClient(new TOClient());
		this.setCustomers(new ArrayList<TOClient>());
		this.setsBean(new KeepClientSBean());
		
		updateList();
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
		
		this.getsBean().list();
	}
	
	public void getCEPInformations() {
		if(this.getClient().getCep() != null && !this.getClient().getCep().toString().equals("")) {
			JSONObject informations = CepUtil.getCEPInformations(this.getClient().getCep().toString());
			
			if(!informations.getString("localidade").equals("Blumenau")) {
				MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("cep_from_other_city"), null, FacesMessage.SEVERITY_ERROR);
				return;
			}
			
			this.getClient().setNeighborhood(informations.getString("bairro"));
			this.getClient().setStreet(informations.getString("logradouro"));
		}
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
}
