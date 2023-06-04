package utils;

import java.util.Date;

public class AbstractObject {
	private String creationUser;
	private Date creationDate;
	private String changeUser;
	private Date changeDate;
	private String inactivationUser;
	private Date inactivationDate;
	
	// Getters and Setters
	public String getCreationUser() {
		return creationUser;
	}
	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getChangeUser() {
		return changeUser;
	}
	public void setChangeUser(String changeUser) {
		this.changeUser = changeUser;
	}
	public Date getChangeDate() {
		return changeDate;
	}
	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}
	public String getInactivationUser() {
		return inactivationUser;
	}
	public void setInactivationUser(String inactivationUser) {
		this.inactivationUser = inactivationUser;
	}
	public Date getInactivationDate() {
		return inactivationDate;
	}
	public void setInactivationDate(Date inactivationDate) {
		this.inactivationDate = inactivationDate;
	}
}