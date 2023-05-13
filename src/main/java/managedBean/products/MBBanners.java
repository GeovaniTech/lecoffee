package managedBean.products;

import java.io.IOException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;

import keep.banner.keepBannerSBean;
import model.Banner;
import model.File;
import utils.AbstractBean;
import utils.FileUtil;
import utils.MessageUtil;

@Named("MBBanners")
@ViewScoped
public class MBBanners extends AbstractBean {
	private static final long serialVersionUID = -3844521267018502665L;
	
	private Banner banner;
	private keepBannerSBean bannerSBean;
	private List<Banner> banners;
	
	public MBBanners() {
		this.setBanner(new Banner());
		this.setBannerSBean(new keepBannerSBean());
		
		list();
	}
	
	public void save() {
		if(this.getBanner().getBytes() != null) {
			this.getBannerSBean().save(this.getBanner());
			this.setBanner(new Banner());
			
			list();
		} else {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("select_image_required"), null, FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void change() {
		if(this.getBanner().getBytes() != null) {
			this.getBannerSBean().change(this.getBanner());
		
			list();
		}
	}
	
	public void disable() {
		this.getBanner().setStatus("disabled");
		this.getBannerSBean().change(this.getBanner());
		
		list();
	}
	
	public void remove() {
		this.getBannerSBean().remove(this.getBanner());
	
		list();
	}
	
	public void list() {
		this.setBanners(this.getBannerSBean().list());
	}
	
	public void addImage(FileUploadEvent event) throws IOException {
		File file = FileUtil.convertPrimefacesFile(event.getFile());
		
		this.getBanner().setBytes(file.getBytes());
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

	public List<Banner> getBanners() {
		return banners;
	}

	public void setBanners(List<Banner> banners) {
		this.banners = banners;
	}
}
