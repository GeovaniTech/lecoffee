package keep.category;

import java.util.List;

import javax.ejb.Local;

import model.Category;
import to.TOCategory;

@Local
public interface IkeepCategorySBean {
	public void save(TOCategory category);
	public void change(TOCategory category);
	public void disable(TOCategory category);
	public void active(TOCategory category);
	public void remove(TOCategory category);
	public List<TOCategory> list();
	public List<TOCategory> listActives();
	public Category findById(int id);
	public TOCategory findToById(int id);
}
