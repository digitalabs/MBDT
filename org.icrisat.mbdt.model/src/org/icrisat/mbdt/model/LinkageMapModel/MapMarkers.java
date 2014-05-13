package org.icrisat.mbdt.model.LinkageMapModel;

import java.io.Serializable;
import java.util.List;

public class MapMarkers implements Serializable {
	
	String Name;
	String markerPosition;
	String distance;

	public  String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getMarkerPosition() {
		return markerPosition;
	}

	public void setMarkerPosition(String markerPosition) {
		this.markerPosition = markerPosition;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		this.Name = name;
	}
	
	
}
