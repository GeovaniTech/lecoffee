package utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MessageFun {
	public void createMessage(FacesMessage.Severity severity, String titulo, String descricao) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, titulo, descricao));
	}
	
	public void emailJaExistente() {
		createMessage(FacesMessage.SEVERITY_ERROR, "E-mail já está em uso por outro usuário.", null);
	}
	
	public void emailInvalido() {
		createMessage(FacesMessage.SEVERITY_ERROR, "E-mail informado é inválido.", null);
	}
	
	public void senhasNaoSaoIguais() {
		createMessage(FacesMessage.SEVERITY_WARN, "Senhas não estão iguais.", null);
	}
	
	public void informacoesInvalidas() {
		createMessage(FacesMessage.SEVERITY_ERROR, "Informações Inválidas", null);
	}
}
