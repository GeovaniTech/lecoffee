package utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MessageFun {
	public void createMessage(FacesMessage.Severity severity, String titulo, String descricao) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, titulo, descricao));
	}
	
	public void teste() {
		createMessage(FacesMessage.SEVERITY_ERROR, "Teste", "Teste2");
	}
}
