package managedBean;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import io.jsonwebtoken.JwtException;
import keep.client.KeepClientSBean;
import utils.AbstractBean;
import utils.EmailUtil;
import utils.JwtTokenUtil;
import utils.MessageUtil;

@Named("MBRegister")
@SessionScoped
public class MBRegister extends AbstractBean {
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String senha;
	private String confirmaSenha;
	private KeepClientSBean sbean;
	private String token;
	
	public MBRegister() {
		sbean = new KeepClientSBean();
	}
	
	public boolean fazerValidacoes() {
		if(!EmailUtil.validateEmail(email)) {
			
			System.out.println(MessageUtil.getMessageFromProperties("invalid_email"));
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("invalid_email"), null, FacesMessage.SEVERITY_ERROR);
			return false;
		}
		
		if(!senha.equals(confirmaSenha)) {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("password_are_not_the_same"), null, FacesMessage.SEVERITY_WARN);
			return false;
		}
		
		if(sbean.verifyClient(email)) {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("existing_email"), null, FacesMessage.SEVERITY_ERROR);
			return false;
		}
		
		sendConfirmationToken();
		return true;
	}
	
	public void sendConfirmationToken() {
		String title = "LeCoffee - Confirmação de email";
		
		StringBuilder description = new StringBuilder();
		
		description.append("<h2>Confirme o seu cadastro na LeCoffee</h2>");
		description.append("<p>Olá,	</p>");
		description.append("<p>Agradecemos por se cadastrar na LeCoffee! ");
		description.append("Estamos felizes em tê-lo(a) como nosso(a) cliente.</p>");
		description.append("Para finalizar o seu cadastro, por favor, confirme a sua conta clicando no link abaixo:</p>");
		description.append("<p><a href=http://localhost:8081/lecoffee/pages/register.xhtml?token=");
		description.append(JwtTokenUtil.generateEmailToken(email) + ">");
		description.append("Finalizar Cadastro</a></p>");
		description.append("<p>Caso você não tenha criado uma conta na LeCoffee ");
		description.append("ou acredite que este email tenha sido enviado por engano, por favor, desconsidere esta mensagem.</p>");
		description.append("Atenciosamente, <br>");
		description.append("A equipe LeCoffee <br>");
		
		EmailUtil.sendMail(email, title, description.toString(), MessageUtil.getMessageFromProperties("confirmation_email_sent"));	
	}
	
	public void cadastrar() {
		try {
			if(this.getToken() != null) {
				String emailFromToken = JwtTokenUtil.getEmailFromToken(this.getToken());
				
				if(emailFromToken != null && !emailFromToken.equals("")) {
					sbean.save(emailFromToken, senha, confirmaSenha);
				}
			}
			
			reset();
		} catch (Exception e) {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("expired_validation_link"), null, FacesMessage.SEVERITY_ERROR);
			
			System.out.println(" ################### TOKEN EXPIRADO ###################");
		}
	}
	
	public void reset() {
		this.setEmail(null);
		this.setSenha(null);
		this.setConfirmaSenha(null);
		this.setToken(null);
	}
	
	//Getters and Setters
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getConfirmaSenha() {
		return confirmaSenha;
	}
	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}
	public KeepClientSBean getSbean() {
		return sbean;
	}
	public void setSbean(KeepClientSBean sbean) {
		this.sbean = sbean;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
