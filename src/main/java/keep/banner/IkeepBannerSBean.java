package keep.banner;

import java.util.List;

import javax.ejb.Local;

import model.Banner;

@Local
public interface IkeepBannerSBean {
	public void save(Banner banner);
	
	public void change(Banner banner);
	
	public void remove(Banner banner);
	
	public Banner findById(int id);
	
	public List<Banner> list();
}
