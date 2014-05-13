package org.icrisat.mbdt.model.LinkageMapModel;

import java.io.Serializable;

public class MarkerPosition implements Serializable {
	
	String MarkerPosition;
	String chromoname;
	String distance;
	//String pri_dist;
//	String pri_chrome;

	public String getChromoname() {
		return chromoname;
	}

	public void setChromoname(String chromoname) {
		this.chromoname = chromoname;
	}

	public  String getMarkerPosition() {
	
		return MarkerPosition;
	}

	public  void setMarkerPosition(String markerPosition) {
		this.MarkerPosition = markerPosition;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	/*public String getPri_dist() {
		return pri_dist;
	}

	public void setPri_dist(String pri_dist) {
		this.pri_dist = pri_dist;
	}

	public String getPri_chrome() {
		return pri_chrome;
	}

	public void setPri_chrome(String pri_chrome) {
		this.pri_chrome = pri_chrome;
	}
*/
}
