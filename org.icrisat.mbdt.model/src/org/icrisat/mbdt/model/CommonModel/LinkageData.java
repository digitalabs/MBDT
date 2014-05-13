package org.icrisat.mbdt.model.CommonModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

public class LinkageData implements Serializable {
	
	private LinkageData()
    {
        // no code req'd
    }

    public static LinkageData getLinkageData()
    {
      if (ref == null)
          ref = new LinkageData();
      return ref;
    }

    private static LinkageData ref;
    HashMap accWithLabels;
    List<String> accFrmGV;
    
    // for setting the selected accessions in load  action
    List<Object> loadAcc;
	
    
    
    String markerName;
	String markerPosition;
	List<String> marker;
	public HashMap getFlankmarkers() {
		return flankmarkers;
	}

	public void setFlankmarkers(HashMap flankmarkers) {
		this.flankmarkers = flankmarkers;
	}

	List<String> foregroundMarker;
	List<String> flankingMarker;
	HashMap flankmarkers;
	
	List<String> bestMarkers;
	HashMap bestcount;
	boolean Dart;
	boolean missing;
	boolean flanking;
	HashMap markerData;
	
	public List<String> getFlankingMarker() {
		return flankingMarker;
	}

	public void setFlankingMarker(List<String> flankingMarker) {
		this.flankingMarker = flankingMarker;
	}

	public HashMap getMarkerData() {
		return markerData;
	}

	public void setMarkerData(HashMap markerData) {
		this.markerData = markerData;
	}

	public boolean isMissing() {
		return missing;
	}

	public void setMissing(boolean missing) {
		this.missing = missing;
	}

	public boolean isFlanking() {
		return flanking;
	}

	public void setFlanking(boolean flanking) {
		this.flanking = flanking;
	}

	public boolean isDart() {
		return Dart;
	}

	public void setDart(boolean dart) {
		Dart = dart;
	}

	HashMap missingCount;
	HashMap flankingCount;
	public HashMap getFlankingCount() {
		return flankingCount;
	}

	public void setFlankingCount(HashMap flankingCount) {
		this.flankingCount = flankingCount;
	}

	int bcount;
	int mcount;
	int fcount;
	

	HashMap<String,List<String>> bestindividual;
	HashMap<String,List<String>> missingindividual;
	HashMap<String,List<String>> flankingindividual;
	List<String> markerPositions;
	String count;
	
	List<String> distances;
	List<String> chromName;
	List<String> mark_all;
	LinkedHashSet<String> sortedAccessions;
	LinkedHashSet<String> markers_of_intrest;
	LinkedHashSet<String> listSelMarkers;
	
	List<String> accallellic;
	List<String> markallelic;
	List<String> labelallelic;
	int maximumAllele;
	List<String> scalePositions;
	List<String> scaleCm;
	List<String> scaleChom_start;
	HashMap type;
	String sortval;
	String tgeno;
	Boolean isSortingDone = false;
    HashMap tooltip;
    LinkedHashSet<String> accession_of_interest;
    Boolean hideActionStatus = false;
    List<Object> NGloadSelectedAcc;
	HashMap chrmno_cumdis;
    String dataType;
	float scale;
	List<String>CumlativeDistance;
	String selectedChromosome;
	List<String> chromlist; 
	HashMap linkage;
	HashMap chromPos;
	
	
	boolean cview;
	
	public HashMap<String, List<String>> getFlankingindividual() {
		return flankingindividual;
	}

	public void setFlankingindividual(
			HashMap<String, List<String>> flankingindividual) {
		this.flankingindividual = flankingindividual;
	}
	public int getFcount() {
		return fcount;
	}

	public void setFcount(int fcount) {
		this.fcount = fcount;
	}
	
	public boolean isCview() {
		return cview;
	}

	public void setCview(boolean cview) {
		this.cview = cview;
	}

	public String getSelectedChromosome() {
		return selectedChromosome;
	}

	public void setSelectedChromosome(String selectedChromosome) {
		this.selectedChromosome = selectedChromosome;
	}
	
	public HashMap getLinkage() {
		return linkage;
	}

	public void setLinkage(HashMap linkage) {
		this.linkage = linkage;
	}

	
	
	public HashMap getMissingCount() {
		return missingCount;
	}

	public void setMissingCount(HashMap missingCount) {
		this.missingCount = missingCount;
	}

	public int getMcount() {
		return mcount;
	}

	public void setMcount(int mcount) {
		this.mcount = mcount;
	}

	public HashMap<String, List<String>> getMissingindividual() {
		return missingindividual;
	}

	public void setMissingindividual(HashMap<String, List<String>> missingindividual) {
		this.missingindividual = missingindividual;
	}
	
	public List<String> getChromlist() {
		return chromlist;
	}

	public void setChromlist(List<String> chromlist) {
		this.chromlist = chromlist;
	}

	public HashMap getChromPos() {
		return chromPos;
	}

	public void setChromPos(HashMap chromPos) {
		this.chromPos = chromPos;
	}

	public List<String> getCumlativeDistance() {
		return CumlativeDistance;
	}

	public void setCumlativeDistance(List<String> cumlativeDistance) {
		CumlativeDistance = cumlativeDistance;
	}

	boolean modified;
    public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}
    public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public HashMap getChrmno_cumdis() {
		return chrmno_cumdis;
	}

	public void setChrmno_cumdis(HashMap chrmno_cumdis) {
		this.chrmno_cumdis = chrmno_cumdis;
	}
	public HashMap getTooltip() {
		return tooltip;
	}

	public void setTooltip(HashMap tooltip) {
		this.tooltip = tooltip;
	}

	public HashMap getType() {
		return type;
	}

	public void setType(HashMap type) {
		this.type = type;
	}

	public int getMaximumAllele() {
		return maximumAllele;
	}

	public void setMaximumAllele(int maximumAllele) {
		this.maximumAllele = maximumAllele;
	}

	public List<String> getMarkallelic() {
		return markallelic;
	}

	public void setMarkallelic(List<String> markallelic) {
		this.markallelic = markallelic;
	}

	public List<String> getLabelallelic() {
		return labelallelic;
	}

	public void setLabelallelic(List<String> labelallelic) {
		this.labelallelic = labelallelic;
	}

	public List<String> getAccallellic() {
		return accallellic;
	}

	public void setAccallellic(List<String> accallellic) {
		this.accallellic = accallellic;
	}

	public LinkedHashSet<String> getMarkers_of_intrest() {
		return markers_of_intrest;
	}

	public void setMarkers_of_intrest(LinkedHashSet<String> markers_of_intrest) {
		this.markers_of_intrest = markers_of_intrest;
	}

	public List<String> getMark_all() {
		return mark_all;
	}

	public void setMark_all(List<String> mark_all) {
		this.mark_all = mark_all;
	}
	public LinkedHashSet<String> getSortedAccessions() {
		return sortedAccessions;
	}

	public void setSortedAccessions(
			LinkedHashSet<String> sortedAccessions) {
		this.sortedAccessions = sortedAccessions;
	}
	
	public List<String> getChromName() {
		return chromName;
	}
	public void setChromName(List<String> chromName) {
		this.chromName = chromName;
	}
	public List<String> getDistances() {
		return distances;
	}
	public void setDistances(List<String> distances) {
		this.distances = distances;
	}
	
	public String getCount() {	
		return count;
	}
	public void setCount(String count) {	
		this.count = count;
	}
	public List<String> getMarker() {
		return marker;
	}
	public void setMarker(List<String> marker) {
		this.marker = marker;
	}
	public String getMarkerName() {
		return markerName;
	}
	public void setMarkerName(String markerName) {
		this.markerName = markerName;
	}
	public String getMarkerPosition() {
		return markerPosition;
	}
	public void setMarkerPosition(String markerPosition) {
		this.markerPosition = markerPosition;
	}
	public List<String> getMarkerPositions() {
		
		return markerPositions;
	}
	public void setMarkerPositions(List<String> markerPositions) {
		this.markerPositions = markerPositions;
	}

	public HashMap getAccWithLabels() {
		return accWithLabels;
	}

	public void setAccWithLabels(HashMap accWithLabels) {
		this.accWithLabels = accWithLabels;
	}

	public List<String> getAccFrmGV() {
		if(accFrmGV == null){
			accFrmGV = new ArrayList<String>();
		}
		return accFrmGV;
	}

	public void setAccFrmGV(List<String> accFrmGV) {
		this.accFrmGV = accFrmGV;
	}

	public LinkedHashSet<String> getListSelMarkers() {
		return listSelMarkers;
	}

	public void setListSelMarkers(LinkedHashSet<String> listSelMarkers) {
		this.listSelMarkers = listSelMarkers;
	}
		public List<String> getScalePositions() {
		
		return scalePositions;
	}
	public void setScalePositions(List<String> scalePositions) {
		this.scalePositions = scalePositions;
	}

	public List<String> getScaleCm() {
		return scaleCm;
	}

	public void setScaleCm(List<String> scaleCm) {
		this.scaleCm = scaleCm;
	}

	public String getSortval() {
		return sortval;
	}

	public void setSortval(String sortval) {
		this.sortval = sortval;
	}

	public Boolean getIsSortingDone() {
		return isSortingDone;
	}

	public void setIsSortingDone(Boolean isSortingDone) {
		this.isSortingDone = isSortingDone;
	}

	public String getTgeno() {
		return tgeno;
	}

	public void setTgeno(String tgeno) {
		this.tgeno = tgeno;
	}
	public List<String> getScaleChom_start() {
		return scaleChom_start;
	}

	public void setScaleChom_start(List<String> scaleChom_start) {
		this.scaleChom_start = scaleChom_start;
	}
public LinkedHashSet<String> getAccession_of_interest() {
		return accession_of_interest;
	}

	public void setAccession_of_interest(LinkedHashSet<String> accession_of_interest) {
		this.accession_of_interest = accession_of_interest;
	}

	public Boolean getHideActionStatus() {
		return hideActionStatus;
	}

	public void setHideActionStatus(Boolean hideActionStatus) {
		this.hideActionStatus = hideActionStatus;
	}

	public List<Object> getNGloadSelectedAcc() {
		if(NGloadSelectedAcc == null){
			NGloadSelectedAcc = new ArrayList<Object>();
		}
		return NGloadSelectedAcc;
	}

	public void setNGloadSelectedAcc(List<Object> gloadSelectedAcc) {
		NGloadSelectedAcc = gloadSelectedAcc;
	}

	public List<Object> getLoadAcc() {
		return loadAcc;
	}

	public void setLoadAcc(List<Object> loadAcc) {
		this.loadAcc = loadAcc;
	}

	public List<String> getForegroundMarker() {
		return foregroundMarker;
	}

	public void setForegroundMarker(List<String> foregroundMarker) {
		this.foregroundMarker = foregroundMarker;
	}

	public List<String> getBestMarkers() {
		return bestMarkers;
	}

	public void setBestMarkers(List<String> bestMarkers) {
		this.bestMarkers = bestMarkers;
	}

	public HashMap getBestcount() {
		return bestcount;
	}

	public void setBestcount(HashMap bestcount) {
		this.bestcount = bestcount;
	}

	public HashMap<String, List<String>> getBestindividual() {
		return bestindividual;
	}

	public void setBestindividual(HashMap<String, List<String>> bestindividual) {
		this.bestindividual = bestindividual;
	}

	public int getBcount() {
		return bcount;
	}

	public void setBcount(int bcount) {
		this.bcount = bcount;
	}

	
}
