package keep.category;

import java.util.List;

import javax.ejb.Local;

import model.Category;

@Local
public interface IkeepCategorySBean {
	public void save(Category category);
	public void change(Category category);
	public void disable(Category category);
	public void remove(Category category);
	public List<Category> list();
	public Category findById(int id);
}
