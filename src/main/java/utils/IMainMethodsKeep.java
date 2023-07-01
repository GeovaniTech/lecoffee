package utils;

import java.util.List;

public interface IMainMethodsKeep<object, to> {
	public void save(to object);
	public void change(to object);
	public void remove(to object);
	public object findById(int id);
	public to findByIdTO(int id);
	public List<to> list();	
}
