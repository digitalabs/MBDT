package org.icrisat.mbdt.model.LinkageMapModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Intervals implements Serializable {
	String chromosome;
	String map_marker;
	String markerposition;
	String distance;
	String numbering;
	int count;
	String pri_dist;
	String pri_chrome;
	List Marker;
	
	String forestatus;
	HashMap mstatus;
	//new 
	List<MapMarkers> Markers;
	List<MarkerPosition> Markpositions;

	//UI Properties
	int x;
	int y;
	int height;
	int width;
		
	//from swapna
	
	List unScreenedmarkers;
	String strunscreenedMrks;
	
// Start propertylistner change
	
	PropertyChangeSupport listeners = new PropertyChangeSupport(this);

	public void addListener(PropertyChangeListener listener) {
		listeners.addPropertyChangeListener(listener);
	}
	
	public void removeListener(PropertyChangeListener listener) {
		listeners.removePropertyChangeListener(listener);
	}
	
	public void firePropertyChange(String propName, Object oldValue, Object newValue) {
		listeners.firePropertyChange(propName, oldValue, newValue);
	}
	
	
//	End propertylistner change
	
	public List<MapMarkers> getMarkers() {
		if(Markers==null){
			Markers=new ArrayList<MapMarkers>();
		}
		return Markers;
	}
	public List<MarkerPosition> getMarkpositions() {
		if(Markpositions==null){
			Markpositions=new ArrayList<MarkerPosition>();
		}
		return Markpositions;
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
	public String getstrUnscreenedMrks() {
		return strunscreenedMrks;
	}
	public void setstrUnscreenedMrks(String unscreenedMrks) {
		this.strunscreenedMrks = unscreenedMrks;
	}		
	//end	
	

	public  int getX() {
		return x;
	}
	public  void setX(int x) {
		this.x = x;
	}
	public  int getY() {
		return y;
	}
	public  void setY(int y) {
		this.y = y;
	}
	public  int getHeight() {
		return height;
	}
	public  void setHeight(int height) {
		this.height = height;
	}
	public  int getWidth() {
		return width;
	}
	public  void setWidth(int width) {
		this.width = width;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getNumbering() {
		return numbering;
	}
	public void setNumbering(String numbering) {
		this.numbering = numbering;
	}
	public String getChromosome() {
		return chromosome;
	}
	public void setChromosome(String chromosome) {
		this.chromosome = chromosome;
	}
	public String getMap_marker() {
		return map_marker;
	}
	public void setMap_marker(String map_marker) {
		this.map_marker = map_marker;
	}
	public String getMarkerposition() {
		return markerposition;
	}
	public void setMarkerposition(String markerposition) {
		this.markerposition = markerposition;
	}
	public  String getPri_dist() {
		return pri_dist;
	}
	public  void setPri_dist(String pri_dist) {
		this.pri_dist = pri_dist;
	}
//	public List<MapMarkers> getMarkers() {
//	if(Markers==null){
//	Markers=new ArrayList<MapMarkers>();
//	}
//	return Markers;
//	}
//	public List<MarkerPosition> getMarkposition() {
//	if(Markposition==null){
//	Markposition=new ArrayList<MarkerPosition>();
//	}
//	return Markposition;
//	}
	public String getPri_chrome() {
		return pri_chrome;
	}
	public void setPri_chrome(String pri_chrome) {
		this.pri_chrome = pri_chrome;
	}
	public  List getMarker() {
		return Marker;
	}
	public  void setMarker(List marker) {
		Marker = marker;
	}
	public String getForestatus() {
		return forestatus;
	}
	public void setForestatus(String forestatus) {
		this.forestatus = forestatus;
		firePropertyChange("PROPERTY_CHANGE_Forestatus"+this.map_marker, null, null);
	}
	public HashMap getMstatus() {
		return mstatus;
	}
	public void setMstatus(HashMap mstatus) {
		this.mstatus = mstatus;
	}


}
