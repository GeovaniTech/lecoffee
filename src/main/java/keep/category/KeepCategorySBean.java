package keep.category;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepCategorySBean implements IkeepCategorySBean, IkeepCategorySBeanRemote {
	
}
