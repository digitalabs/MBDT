package org.icrisat.mbdt.model.CommonModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Qtl_MapData implements Serializable{
	private Qtl_MapData()
    {
        // no code req'd
    }

    public static Qtl_MapData getQtl_MapData()
    {
      if (ref == null)
          // it's ok, we can call this constructor
          ref = new Qtl_MapData();
      return ref;
    }
    private static Qtl_MapData ref;
    
	List<String> MarkerPositions;
	List<String> Distances;
	List<String> chromosome;
	List<String> qtlLinkChrom = new ArrayList<String>();
	
	String qtlas;
	
	HashMap<String, Integer> hashLinkDetails = new HashMap<String, Integer>();
	HashMap<String, Integer> hashTraitDetails = new HashMap<String, Integer>();
	HashMap<String, Integer> hashEnvTraitDetails = new HashMap<String, Integer>();
	
	int qtlYValue = 0;
	int qtlYValueForOthers = 0;
	
	public List<String> getMarkerPositions() {
		return MarkerPositions;
	}
	public void setMarkerPositions(List<String> markerPositions) {
		MarkerPositions = markerPositions;
	}
	public List<String> getDistances() {
		return Distances;
	}
	public void setDistances(List<String> distances) {
		Distances = distances;
	}
	public List<String> getChromosome() {
		return chromosome;
	}
	public void setChromosome(List<String> chromosome) {
		this.chromosome = chromosome;
	}

	public List<String> getQtlLinkChrom() {
		if(qtlLinkChrom == null){
			qtlLinkChrom = new ArrayList<String>();
		}
		return qtlLinkChrom;
	}

	public void setQtlLinkChrom(List<String> qtlLinkChrom) {
		this.qtlLinkChrom = qtlLinkChrom;
	}

	public HashMap<String, Integer> getHashLinkDetails() {
		return hashLinkDetails;
	}

	public void setHashLinkDetails(HashMap<String, Integer> hashLinkDetails) {
		this.hashLinkDetails = hashLinkDetails;
	}

	public HashMap<String, Integer> getHashTraitDetails() {
		return hashTraitDetails;
	}

	public void setHashTraitDetails(HashMap<String, Integer> hashTraitDetails) {
		this.hashTraitDetails = hashTraitDetails;
	}

	public HashMap<String, Integer> getHashEnvTraitDetails() {
		return hashEnvTraitDetails;
	}

	public void setHashEnvTraitDetails(HashMap<String, Integer> hashEnvTraitDetails) {
		this.hashEnvTraitDetails = hashEnvTraitDetails;
	}

	public int getQtlYValue() {
		return qtlYValue;
	}

	public void setQtlYValue(int qtlYValue) {
		this.qtlYValue = qtlYValue;
	}

	public int getQtlYValueForOthers() {
		return qtlYValueForOthers;
	}

	public void setQtlYValueForOthers(int qtlYValueForOthers) {
		this.qtlYValueForOthers = qtlYValueForOthers;
	}

	public String getQtlas() {
		return qtlas;
	}

	public void setQtlas(String qtlas) {
		this.qtlas = qtlas;
	}

	
}
