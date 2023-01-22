package utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public abstract interface Message {
	public static void createMessage(FacesMessage.Severity severity, String titulo, String descricao) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, titulo, descricao));
	}
	
	public static void emptyValues() {
		createMessage(FacesMessage.SEVERITY_ERROR, "Empty Values", "There are empty values");
	}
	
	public static void saveSuccessfully() {
		createMessage(FacesMessage.SEVERITY_INFO, "Saved Successfully", "Successful registration");
	}

	public static void changedSuccessfully() {
		createMessage(FacesMessage.SEVERITY_INFO, "Change Successfully", "Changed registration");
	}
	
	public static void removeSuccessfully() {
		createMessage(FacesMessage.SEVERITY_INFO, "Removed Successfully", "Record removed successfully");
	}
	
	public static void errorRemoving() {
		createMessage(FacesMessage.SEVERITY_ERROR, "Error Removing", "Could not remove record");
	}
	
	public static void erroNameUsed() {
		createMessage(FacesMessage.SEVERITY_ERROR, "Error Register", "The user name is being used!");
	}
	
	public static void passwordsDontMatch() {
		createMessage(FacesMessage.SEVERITY_ERROR, "Error Register", "The passwords do not match");
	}
	
	public static void errorGenerateReport() {
		createMessage(FacesMessage.SEVERITY_ERROR, "The report was not generated", "We couldn't generate the report, try again later");
	}
}
