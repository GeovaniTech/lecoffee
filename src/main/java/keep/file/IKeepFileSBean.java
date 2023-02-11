package keep.file;

import javax.ejb.Local;

import model.File;

@Local
public interface IKeepFileSBean {
	public void save(File file);
	public void remove(File file);
}
