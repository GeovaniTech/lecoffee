package to.client;

import java.util.Date;

import abstracts.AbstractTOObject;
import to.appconfigs.TOAppConfig;

public class TOClient extends AbstractTOObject {

	private static final long serialVersionUID = -7590357042811071338L;
	
	private String email;
	private String phoneNumber;
	private String securityLevel;
	private Date lastLogin;
	private boolean blocked;
	private boolean changePassword;
	private TOAppConfig appConfig;
	
	public TOClient() {
		this.setSecurityLevel("client");
	}
	
	// Getters and Setters
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSecurityLevel() {
		return securityLevel;
	}
	public void setSecurityLevel(String securityLevel) {
		this.securityLevel = securityLevel;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	public boolean isBlocked() {
		return blocked;
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public boolean isChangePassword() {
		return changePassword;
	}
	public void setChangePassword(boolean changePassword) {
		this.changePassword = changePassword;
	}
	public TOAppConfig getAppConfig() {
		return appConfig;
	}
	public void setAppConfig(TOAppConfig appConfig) {
		this.appConfig = appConfig;
	}
	
}
