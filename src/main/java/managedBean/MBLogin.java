package managedBean;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import keep.client.KeepClientSBean;

import utils.AbstractBean;
import utils.Cookies;
import utils.EmailUtil;
import utils.Encryption;
import utils.JwtTokenUtil;
import utils.MessageUtil;
import utils.RedirectUrl;

@Named("MBLogin")
@ViewScoped
public class MBLogin extends AbstractBean {
	private static final long serialVersionUID = -6696747613785703525L;
	
	private String email;
	private String password;
	private KeepClientSBean sBean;
	private String registerFinished;
	
	public MBLogin() {		
		this.setsBean(new KeepClientSBean());
		redirectUserFromCookie();
	}

	public void logar() {
		this.getsBean().logar(this.getEmail(), this.getPassword());
	}
	
	public void redirectUserFromCookie() {
		String userEmail = Cookies.getUserCookie();
		
		if(userEmail != null && !userEmail.equals("")) {
			try {
				getSession().setAttribute("client", this.getsBean().findByEmail(Encryption.decryptNormalText(userEmail)));
			} catch (Exception e) {
				// User not found
				removeUserFromCookie();
				
				RedirectUrl.redirecionarPara("/lecoffee/home");
			}
		}
	}
	
	public void removeUserFromCookie() {
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		Cookie userSession = new Cookie("userSession", null);
		userSession.setMaxAge(1);
		userSession.setPath("/");
		
		response.addCookie(userSession);
	}
	
	public void sendRegisterFinishedMessage() {
		if(this.getRegisterFinished() != null && this.getRegisterFinished().equals("finished")) {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("registration_completed_successfully"), null, FacesMessage.SEVERITY_INFO);
		}
	}
	
	public void setNewPasswordValidations() {
		if(!EmailUtil.validateEmail(this.getEmail())) {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("invalid_email"), null, FacesMessage.SEVERITY_WARN);
			return;
		}
		
		if(!this.getsBean().verifyClient(this.getEmail())) {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("user_not_found"), null, FacesMessage.SEVERITY_ERROR);
			return;
		}
		
		this.setNewPassword();
	}
	
	public void setNewPassword() {
		
	}
	
	public String createTokenNewPassword() {
		return JwtTokenUtil.generateEmailToken(this.getEmail());
	}
	
	//Getters and Setters
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public KeepClientSBean getsBean() {
		return sBean;
	}

	public void setsBean(KeepClientSBean sBean) {
		this.sBean = sBean;
	}

	public String getRegisterFinished() {
		return registerFinished;
	}

	public void setRegisterFinished(String registerFinished) {
		this.registerFinished = registerFinished;
	}
}
