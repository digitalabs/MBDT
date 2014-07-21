package org.icrisat.mbdt.model.CommonModel;

import javax.servlet.ServletRequestEvent;

import org.generationcp.middleware.manager.ManagerFactory;
import org.generationcp.middleware.manager.api.WorkbenchDataManager;
import org.generationcp.middleware.support.servlet.MiddlewareServletRequestListener;

public class MBDTServletRequestListener extends MiddlewareServletRequestListener {

	@Override
	public void requestDestroyed(ServletRequestEvent event) {
		super.requestDestroyed(event);
		
		//System.out.println("MiddlewareServletRequestListener : Request Destroyed");
		
		//GDMSModel.getGDMSModel().setManagerFactory(null);
		
	}

	@Override
	public void requestInitialized(ServletRequestEvent event) {
		super.requestInitialized(event);
		
		//System.out.println("MiddlewareServletRequestListener : Request Initialized");
		
		ManagerFactory managerFactoryForRequest = getManagerFactoryForRequest(event.getServletRequest());
		
	
		//GDMSModel.getGDMSModel().setServletRequest(event);
		
		if (null != managerFactoryForRequest) {
			//System.out.println("ManagerFactory object obtained.");
			MBDTModel.getMBDTModel().setManagerFactory(managerFactoryForRequest);
			//GDMSModel.getGDMSModel().setWorkbenchDataManager(super.getWorkbenchManagerForRequest(event.getServletRequest()));
		} else {
			//System.out.println("ManagerFactory object is null.");
			
		}
		
		WorkbenchDataManager dataManager = getWorkbenchManagerForRequest(event.getServletRequest());
        if (null != dataManager) {
            MBDTModel.getMBDTModel().setWorkbenchDataManager(dataManager);
        } else {
            System.out.println("WorkbenchDataManager object is null.");

        }
	}
	
	
}
