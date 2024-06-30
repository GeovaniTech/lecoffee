package managedBean.menu;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import abstracts.AbstractMBean;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named(MBMenuLecoffee.MANAGED_BEAN_NAME)
@ViewScoped
public class MBMenuLecoffee extends AbstractMBean {

	private static final long serialVersionUID = 4863287155259223199L;
	public static final String MANAGED_BEAN_NAME = "MBMenuLecoffee";

	private MenuModel menu;

	@PostConstruct
	public void init() {
		this.setMenu(new DefaultMenuModel());
        
        String securityLevel = this.getClientSession().getSecurityLevel();
        
        if(securityLevel.equals("admin") || securityLevel.equals("superadmin")) {            
            DefaultMenuItem orders = DefaultMenuItem.builder()
            		.styleClass("menu-orders")
                    .value(this.getLabel("orders"))
                    .icon("pi pi-shopping-cart")
                    .url(this.createUrlMenu("admin/orders"))
                    .build();
            
            DefaultMenuItem products = DefaultMenuItem.builder()
            		.styleClass("menu-products")
                    .value(this.getLabel("products"))
                    .icon("pi pi-ticket")
                    .url(this.createUrlMenu("admin/products"))
                    .build();
            
            DefaultMenuItem payments = DefaultMenuItem.builder()
            		.styleClass("menu-payments")
                    .value(this.getLabel("payments"))
                    .icon("pi pi-credit-card")
                    .url(this.createUrlMenu("admin/payments"))
                    .build();
            
            DefaultMenuItem users = DefaultMenuItem.builder()
            		.styleClass("menu-users")
                    .value(this.getLabel("users"))
                    .icon("pi pi-user")
                    .url(this.createUrlMenu("admin/users"))
                    .build();
            
            this.getMenu().getElements().add(orders);
            this.getMenu().getElements().add(products);
            this.getMenu().getElements().add(payments);
            this.getMenu().getElements().add(users);
        } 
        
        if(securityLevel.equals("superadmin")) {
            DefaultSubMenu superAdmin = DefaultSubMenu.builder()
                    .label("Super Admin")
                    .styleClass("submenu")
                    .expanded(true)
                    .build();
            
            DefaultMenuItem logs = DefaultMenuItem.builder()
            		.styleClass("menu-logs")
                    .value(this.getLabel("logs"))
                    .icon("pi pi-exclamation-circle")
                    .url(this.createUrlMenu("admin/logs"))
                    .build();
            
            superAdmin.getElements().add(logs);
            
            this.getMenu().getElements().add(superAdmin);
        }
	}
	
	private String createUrlMenu(String pageUrl) {
		return this.getRequest().getContextPath() + "/" + pageUrl;
	}
	
	// Getters and Setters
	public MenuModel getMenu() {
		return menu;
	}

	public void setMenu(MenuModel menu) {
		this.menu = menu;
	}
		
}
