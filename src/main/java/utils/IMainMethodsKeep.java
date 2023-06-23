package utils;

public interface IMainMethodsKeep<object, to> {
	public void save(object object);
	public void change(object object);
	public void remove(object object);
	public object findById(int id);
	public to findByIdTO(int id);
}
