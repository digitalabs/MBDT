package org.icrisat.mbdt.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Notifier {
	
	PropertyChangeSupport listeners = new PropertyChangeSupport(this);	
	public void addListener(PropertyChangeListener listener) {
		listeners.addPropertyChangeListener(listener);
	}
	
	public void removeListener(PropertyChangeListener listener) {
		listeners.removePropertyChangeListener(listener);
	}
	
	public void firePropertyChange(String propertyName, Object oldValue, Object newValue){
		listeners.firePropertyChange(propertyName, oldValue, newValue);
	}

}
