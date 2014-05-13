package org.icrisat.mbdt.model.GenotypeModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.icrisat.mbdt.model.PhenotypeModel.TraitValues;

public class Accessions implements Serializable {

	
	// Non_UI properties
	//from swapna
	List dbType;
	List<String> selectedAccessions1;
	
	//phenotype accession 
	boolean aflag;
	
	
	//-----Start Property change listeners-----
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
	
	//-----End Property change listeners-----
	
	
	public List getDbType() {
		return dbType;
	}

	public void setDbType(List dbType) {
		this.dbType = dbType;
	}
	public List<String> getSelectedAccessions1() {
		if(selectedAccessions1 == null){
			selectedAccessions1 = new ArrayList();
			
		}
		return selectedAccessions1;
	}

	public void setSelectedAccessions1(List<String> selectedAccessions1) {
		this.selectedAccessions1 = selectedAccessions1;
	}	
	
	// end of swapna
	
	
	// Non_UI properties
	
	String id;
	String name;
	String type= "";
	String trials;
	int i=0;
	String marker;
	List<AllelicValues> allelicValues;
	
	List<String> alleValues;
	List<GenotypeMarkers> gMarkers;
	List<SelectedAccessions> selectedAccessions;
	List gh;

	List<String> gmark;
	int count=0;
	HashMap<String, Integer> hashMarkersCount = new HashMap<String, Integer>();
	List<String> missingMarkers;
	
	//Loading Generations... 
	List<Object> loadAcc;
	List<Object> selAccforTargetCreation;
	
	//Phenotype data...
	List<String> traits;
	List<TraitValues> traitValues;
	HashMap<String, Integer> hashtraitCount = new HashMap<String, Integer>();
	
	
	
	public HashMap<String, Integer> getHashtraitCount() {
		return hashtraitCount;
	}

	public void setHashtraitCount(HashMap<String, Integer> hashtraitCount) {
		this.hashtraitCount = hashtraitCount;
	}

	public List<GenotypeMarkers> getGenotypeMarkers() {
		if(gMarkers== null) {
			gMarkers= new ArrayList();
		}
		return gMarkers;
	}

	public List<AllelicValues> getAllelicValues() {
		if(allelicValues== null) {
			allelicValues= new ArrayList();
		}
		return allelicValues;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {	
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		firePropertyChange("PROPERTY_CHANGE", null, null);
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<SelectedAccessions> getSelectedAccessions() {
		if(selectedAccessions == null) {
			selectedAccessions = new ArrayList<SelectedAccessions>();
		}
					
		return selectedAccessions;
	}

	public String getMarker() {
		return marker;
	}

	public void setMarker(String marker) {
		
		this.marker = marker;
	}

	public List getGh() {
		return gh;
	}

	public void setGh(List gh) {
		this.gh = gh;
	}

	public List<String> getGmark() {
		return gmark;
	}

	public void setGmark(List<String> gmark) {
		this.gmark = gmark;
	}

	public List<String> getAlleValues() {
		if(alleValues == null){
			alleValues = new ArrayList<String>();			
		}
		return alleValues;
	}

	public void setAlleValues(List<String> alleValues) {
		this.alleValues = alleValues;
	}

	public HashMap<String, Integer> getMarkersCount() {
		return hashMarkersCount;
	}

	public void setMarkersCount(HashMap<String, Integer> markersCount) {
		this.hashMarkersCount = markersCount;
	}

	public List<String> getMissingMarkers() {
		if(missingMarkers == null){
			missingMarkers = new ArrayList<String>();			
		}
		return missingMarkers;
	}

	public void setMissingMarkers(List<String> missingMarkers) {
		this.missingMarkers = missingMarkers;
	}
	public List<Object> getLoadAcc() {
		if(loadAcc == null){
			loadAcc = new ArrayList<Object>();
		}
		return loadAcc;
	}

	public void setLoadAcc(List<Object> loadAcc) {
		this.loadAcc = loadAcc;
	}

	public List<Object> getSelAccforTargetCreation() {
		if(selAccforTargetCreation == null){
			selAccforTargetCreation = new ArrayList<Object>();
		}
		return selAccforTargetCreation;
	}

	public void setSelAccforTargetCreation(List<Object> selAccforTargetCreation) {
		this.selAccforTargetCreation = selAccforTargetCreation;
	}

	public List<TraitValues> getTraitValues() {
		if(traitValues== null) {
			traitValues= new ArrayList();
		}
		return traitValues;
	}

	public List<String> getTraits() {
		return traits;
	}

	public void setTraits(List<String> traits) {
		this.traits = traits;
	}

	public boolean isAflag() {
		return aflag;
	}

	public void setAflag(boolean aflag) {
		this.aflag = aflag;
	}
}
