package managedBean.products;

import java.io.IOException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;

import keep.banner.keepBannerSBean;
import model.Banner;
import model.File;
import to.TOBanner;
import utils.AbstractFilterBean;
import utils.FileUtil;
import utils.MessageUtil;

@Named("MBBanners")
@ViewScoped
public class MBBanners extends AbstractFilterBean {
	private static final long serialVersionUID = -3844521267018502665L;
	
	private Banner banner;
	private keepBannerSBean bannerSBean;
	private List<TOBanner> banners;
	private List<TOBanner> activedBanners;
	private boolean tableView;
	
	public MBBanners() {
		this.setBanner(new Banner());
		this.setBannerSBean(new keepBannerSBean());
		
		list();
		listActived();
	}
	
	 public void onSelect(SelectEvent<TOBanner> event) {
		 this.setBanner(this.getBannerSBean().findById(event.getObject().getId()));
		 
		 PrimeFaces.current().executeScript("PF('dialog-change-banner').show();");
	 }
	
	public void save() {
		if(this.getBanner().getBytes() != null) {
			this.getBannerSBean().save(this.getBanner());
			this.setBanner(new Banner());
			
			list();
			listActived();
		} else {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("select_image_required"), null, FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void change() {
		if(this.getBanner().getBytes() != null) {
			this.getBannerSBean().change(this.getBanner());
		
			list();
			listActived();
		}
	}
	
	public void active() {
		this.getBanner().setStatus("active");
		this.change();
		
		list();
		listActived();
	}
	
	public void disable() {
		this.getBanner().setStatus("disabled");
		this.change();
		
		list();
		listActived();
	}
	
	public void remove() {
		this.getBannerSBean().remove(this.getBanner());
	
		list();
		listActived();
	}
	
	public void list() {
		this.setBanners(this.getBannerSBean().list());
	}
	
	public void listActived() {
		this.setActivedBanners(this.getBannerSBean().listActivedBanners());
	}
	
	public void addImage(FileUploadEvent event) throws IOException {
		File file = FileUtil.convertPrimefacesFile(event.getFile());
		
		this.getBanner().setBytes(file.getBytes());
	}
	
	public void onReorderList() {
		int id = 1;

		for(TOBanner banner : this.getActivedBanners()) {
			Banner model = this.getBannerSBean().findById(banner.getId());
			
			model.setPriority(id);
			this.getBannerSBean().save(model);
			
			id++;
		}
	}
	
	public void changeTableView() {
		this.setTableView(!this.isTableView());
	}
	
	//Getters and Setters
	public Banner getBanner() {
		return banner;
	}

	public void setBanner(Banner banner) {
		this.banner = banner;
	}

	public keepBannerSBean getBannerSBean() {
		return bannerSBean;
	}

	public void setBannerSBean(keepBannerSBean bannerSBean) {
		this.bannerSBean = bannerSBean;
	}

	public List<TOBanner> getBanners() {
		return banners;
	}

	public void setBanners(List<TOBanner> banners) {
		this.banners = banners;
	}

	public boolean isTableView() {
		return tableView;
	}

	public void setTableView(boolean tableView) {
		this.tableView = tableView;
	}

	public List<TOBanner> getActivedBanners() {
		return activedBanners;
	}

	public void setActivedBanners(List<TOBanner> activedBanners) {
		this.activedBanners = activedBanners;
	}
}
