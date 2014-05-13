package org.icrisat.mbdt.model.LinkageMapModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LimsMarkers implements Serializable {
	
	String Markers;
	String strunscreenedMrks;
	String strMarkersFrmTviewer;
	List unScreenedmarkers;
	List markersFrmTviewer;
	String MarkerStatus;
	HashMap mstatus;
	HashMap monomorphicMarkers;
	

	public HashMap getMonomorphicMarkers() {
		return monomorphicMarkers;
	}

	public void setMonomorphicMarkers(HashMap monomorphicMarkers) {
		this.monomorphicMarkers = monomorphicMarkers;
	}

	public String getMarkers() {
		return Markers;
	}

	public List getMarkersFrmTviewer() {
		if(markersFrmTviewer == null){
			markersFrmTviewer = new ArrayList();			
		}
		return markersFrmTviewer;
	}

	public void setMarkersFrmTviewer(List markersFrmTviewer) {
		this.markersFrmTviewer = markersFrmTviewer;
	}

	public void setMarkers(String markers) {
		Markers = markers;
	}
	
	public String getstrUnscreenedMrks() {
		return strunscreenedMrks;
	}
	
	public void setstrUnscreenedMrks(String unscreenedMrks) {
		this.strunscreenedMrks = unscreenedMrks;
	}
	public List getUnScreenedmarkers() {
		if(unScreenedmarkers == null){
			unScreenedmarkers = new ArrayList();			
		}
		return unScreenedmarkers;
	}
	public void setUnScreenedmarkers(List unScreenedmarkers) {
		this.unScreenedmarkers = unScreenedmarkers;
	}

	public String getStrMarkersFrmTviewer() {
		return strMarkersFrmTviewer;
	}

	public void setStrMarkersFrmTviewer(String strMarkersFrmTviewer) {
		this.strMarkersFrmTviewer = strMarkersFrmTviewer;
	}

	public String getMarkerStatus() {
		return MarkerStatus;
	}

	public void setMarkerStatus(String markerStatus) {
		this.MarkerStatus = markerStatus;
	}

	public HashMap getMstatus() {
		return mstatus;
	}

	public void setMstatus(HashMap mstatus) {
		this.mstatus = mstatus;
	}

	
	
}
