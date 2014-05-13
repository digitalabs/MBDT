package org.icrisat.mbdt.model.LinkageMapModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LinkageMap implements Serializable {
	
	List<Intervals> chromosomes;
	List<LimsMarkers> limsMarkers;
	List<LinkageScaleModel> linkageScale;
	//for Loading...
	List<Object> selectedUnscreenMarkers;
	
	int unScreenedcount;
	public List<LimsMarkers> getLimsMarkers() {
		if(limsMarkers == null){
			limsMarkers = new ArrayList<LimsMarkers>();
			
		}
		return limsMarkers;
	}

	public List<Intervals> getChromosomes() {
		if(chromosomes== null) {
			chromosomes= new ArrayList<Intervals>();
		}
		return chromosomes;
	}

	public int getUnScreenedcount() {
		return unScreenedcount;
	}

	public void setUnScreenedcount(int unScreenedcount) {
		this.unScreenedcount = unScreenedcount;
	}

	public List<LinkageScaleModel> getLinkageScale() {
		if(linkageScale== null) {
			linkageScale= new ArrayList<LinkageScaleModel>();
		}
		return linkageScale;
	}
	public List<Object> getSelectedUnscreenMarkers() {
		if(selectedUnscreenMarkers == null){
			selectedUnscreenMarkers = new ArrayList<Object>();			
		}
		return selectedUnscreenMarkers;
	}

	public void setSelectedUnscreenMarkers(List<Object> selectedUnscreenMarkers) {
		this.selectedUnscreenMarkers = selectedUnscreenMarkers;
	}	
	
}

