package managedBean;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import io.jsonwebtoken.JwtException;
import keep.client.KeepClientSBean;
import utils.AbstractBean;
import utils.EmailUtil;
import utils.JwtTokenUtil;

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
			msg.emailInvalido();
			return false;
		}
		
		if(!EmailUtil.validateEmail(email)) {
			msg.emailInvalido();
			return false;
		}
		
		if(!senha.equals(confirmaSenha)) {
			msg.senhasNaoSaoIguais();
			return false;
		}
		
		if(sbean.verifyClient(email)) {
			msg.emailJaExistente();
			return false;
		}
		
		sendConfirmationToken();
		return true;
	}
	
	public void sendConfirmationToken() {
		String title = "LeCoffee - Confirmação de email";
		
		StringBuilder description = new StringBuilder();
		
		description.append("Você acabou de se cadastrar na LeCoffee, ");
		description.append("para prosseguir confirme o seu cadastro acessando o link abaixo: \n");
		description.append("<a href=http://localhost:8081/lecoffee/pages/register.xhtml?token=");
		description.append(JwtTokenUtil.generateEmailToken(email));
		description.append(">Finalizar Cadastro</a>");
		
		EmailUtil.sendMail(email, title, description.toString());	
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
		} catch (JwtException e) {
			e.printStackTrace();
			System.out.println(" ################### TOKE EXPIRADO ###################");
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
