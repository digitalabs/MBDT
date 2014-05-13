package org.icrisat.mbdt.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;



public class SessionManager extends Notifier{
	
	static SessionManager sManager;
	List<RootModel> ismabProjects;
	
	private SessionManager() {
		// TODO Auto-generated constructor stub
	}
	
	public static SessionManager getInstance() {
		if (sManager == null) {
			sManager = new SessionManager();
		}
		return sManager;
	}
	
	public List<RootModel> getIsmabProjects() {
		if (ismabProjects == null) {
			ismabProjects = new ArrayList<RootModel>();
		}
		return ismabProjects;
	}
	
	public void addProject(RootModel project) {
		getIsmabProjects().add(project);
		firePropertyChange("ADD_PROJECT", null, null);
	}
	
	
	

}
