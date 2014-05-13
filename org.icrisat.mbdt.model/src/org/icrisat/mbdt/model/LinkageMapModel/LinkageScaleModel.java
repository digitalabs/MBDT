package org.icrisat.mbdt.model.LinkageMapModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LinkageScaleModel implements Serializable {
	
//	String chromosome;
//	String map_marker;
//	String markerposition;
//	String distance;
//	String numbering;
//	int count;
//	String pri_dist;
//	String pri_chrome;
//	List Marker;
	
	//new 
	List<MapMarkersScale> MarkersScale;
	List<MarkerPositionScale> MarkpositionsScale;

	public List<MapMarkersScale> getMarkersScale() {
		if(MarkersScale==null){
			MarkersScale=new ArrayList<MapMarkersScale>();
		}
		return MarkersScale;
	}
	public List<MarkerPositionScale> getMarkpositionsScale() {
		if(MarkpositionsScale==null){
			MarkpositionsScale=new ArrayList<MarkerPositionScale>();
		}
		return MarkpositionsScale;
	}
	
	/*public String getChromosome() {
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
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getPri_dist() {
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
	public List getMarker() {
		return Marker;
	}
	public void setMarker(List marker) {
		Marker = marker;
	}*/

		
		

	}