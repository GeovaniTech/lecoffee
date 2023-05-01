package keep.appConfigs;

import javax.ejb.Local;

import model.AppConfigs;

@Local
public interface IKeepAppConfigs {
	public void save(AppConfigs appConfigs);
	public void change(AppConfigs appConfigs);
	public AppConfigs findByClientId(int id);
}
