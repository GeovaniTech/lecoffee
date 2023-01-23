package manter.client;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ManterClientSBean implements IManterClientSBean, IManterClientSBeanRemote {

	@Override
	public void save() {
		
		
	}
	
}
