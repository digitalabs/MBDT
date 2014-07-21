package org.icrisat.mbdt.model.CommonModel;

import javax.servlet.ServletContextEvent;

import org.generationcp.middleware.support.servlet.MiddlewareServletContextListener;

public class MBDTServletContextListener extends MiddlewareServletContextListener {

	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
		
		System.out.println("MiddlewareServletContextListener : Context Destroyed");
	}

	public void contextInitialized(ServletContextEvent arg0) {
		
		super.contextInitialized(arg0);
				
		System.out.println("MiddlewareServletContextListener : Context Initialized");
	}
	

}
