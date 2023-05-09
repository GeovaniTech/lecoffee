package managedBean;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import keep.client.KeepClientSBean;
import utils.AbstractBean;
import utils.JwtTokenUtil;
import utils.MessageUtil;
import utils.RedirectUrl;
import utils.StringUtil;

@Named("MBNewPassword")
@ViewScoped
public class MBNewPassword extends AbstractBean {
	private static final long serialVersionUID = 2965673570827470675L;
	
	private String password;
	private String repeatPassword;
	private String token;
	private KeepClientSBean sBeanClient;
	
	public MBNewPassword() {
		this.setsBeanClient(new KeepClientSBean());
	}
	
	public boolean verifyToken() {
		try {
			if(!StringUtil.isNotNull(this.getToken())) {
				MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("invalid_token"), null, FacesMessage.SEVERITY_ERROR);
				return false;
			}
			
			if(this.getsBeanClient().findByEmail(JwtTokenUtil.getEmailFromToken(this.getToken())) == null) {
				MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("user_not_found"), null, FacesMessage.SEVERITY_ERROR);
				return false;
			}
			
			return true;
		} catch (Exception e) {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("invalid_token"), null, FacesMessage.SEVERITY_ERROR);
			return false;
		}
	}
	
	public void changePassword() {
		if(!this.getPassword().equals(this.getRepeatPassword())) {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("password_are_not_the_same"), null, FacesMessage.SEVERITY_WARN);
		}
		
		if(verifyToken()) {
			String emailFromToken = JwtTokenUtil.getEmailFromToken(this.getToken());
			this.getsBeanClient().setNewPassword(emailFromToken, this.getPassword());
		}
		
		RedirectUrl.redirecionarPara("/lecoffee/login/finishedNewPassword");
	}
	
	// Getters and Setters
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRepeatPassword() {
		return repeatPassword;
	}
	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public KeepClientSBean getsBeanClient() {
		return sBeanClient;
	}
	public void setsBeanClient(KeepClientSBean sBeanClient) {
		this.sBeanClient = sBeanClient;
	}
}

