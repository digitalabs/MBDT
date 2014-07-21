package org.icrisat.mbdt.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.Datavalidation.DataValidation;
import org.icrisat.mbdt.model.GenotypeModel.Genotype;
import org.icrisat.mbdt.model.LinkageMapModel.LinkageMap;
import org.icrisat.mbdt.model.NextGeneration.LoadNextGenData;
import org.icrisat.mbdt.model.PhenotypeModel.Phenotype;
import org.icrisat.mbdt.model.QTLModel.QTL;

public class RootModel implements Serializable {
	
	
	//making RootModel singleton....
	private RootModel()
	{
	    // no code req'd
	}
	
    public static RootModel getRootModel()
    {
      if (ref == null)
          // it's ok, we can call this constructor
          ref = new RootModel();
      return ref;
    }

	private static RootModel ref;    
  
    String acc;
    String firstAcc;
	int t=0;
	List<String> accession;
	List<String> chrNo;
	List<String> markerName;
	List<Integer> markerPosition;
	List<String> label;
	List<Integer> markerPrevPos;
	List<Integer> markerNextPos;
	List<String> AccAllele;
	List<LinkageMap> linkagemap;
	List<QTL> qtl;
	List<Genotype> genotype;
	List<Phenotype> phenotype;
	String name;
	List<DataValidation> dataValidation;
	
	HashMap type;
	
	String generation;
	LinkageData linkData;
	Qtl_MapData qtlMapData;
	
	HashSet<String> GenotypeName = new HashSet<String>();
	String haploidlevel;
	HashSet<String> MarkerName_validation = new HashSet<String>();
	Boolean LinageMap_Error;
	String LinkageMap_Chromfirst;
	Boolean loadFlag;
	String loadProject;
	String loadGeneration;
	HashMap<String, Object> genoObject = new HashMap<String, Object>();
	HashMap<String, String> typeValue = new HashMap<String, String>();
	String saveType = "";
	
	List<LoadNextGenData> loadNextGen;
	
	String linkagemapPath;
	String PhenotypePath;
	String qtlPath;
	String GenotypePath;
	
	//	 database parameters
	int genotypeDataset;
	int MapDataset;
	int QtlDataset;
	int PhenoDataset;
	
	 
	

	
	/*String projectName;
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}*/

	public int getGenotypeDataset() {
		return genotypeDataset;
	}

	public void setGenotypeDataset(int genotypeDataset) {
		this.genotypeDataset = genotypeDataset;
	}

	public int getMapDataset() {
		return MapDataset;
	}

	public void setMapDataset(int mapDataset) {
		MapDataset = mapDataset;
	}

	public int getQtlDataset() {
		return QtlDataset;
	}

	public void setQtlDataset(int qtlDataset) {
		QtlDataset = qtlDataset;
	}

	public int getPhenoDataset() {
		return PhenoDataset;
	}

	public void setPhenoDataset(int phenoDataset) {
		PhenoDataset = phenoDataset;
	}

	public String getLinkageMap_Chromfirst() {
		return LinkageMap_Chromfirst;
	}

	public void setLinkageMap_Chromfirst(String linkageMapChromfirst) {
		LinkageMap_Chromfirst = linkageMapChromfirst;
	}

	public Boolean getLinageMap_Error() {
	return LinageMap_Error;
	}

	public void setLinageMap_Error(Boolean linageMapError) {
	LinageMap_Error = linageMapError;
	}

	public HashSet<String> getMarkerName_validation() {
	return MarkerName_validation;
	}

	public void setMarkerName_validation(HashSet<String> markerNameValidation) {
	MarkerName_validation = markerNameValidation;
	}

	public String getHaploidlevel() {
		return haploidlevel;
	}

	public void setHaploidlevel(String haploidlevel) {
		this.haploidlevel = haploidlevel;
	}

	public HashSet<String> getGenotypeName() {
		return GenotypeName;
	}

	public void setGenotypeName(HashSet<String> genotypeName) {
		GenotypeName = genotypeName;
	}
	
	public String getGenotypePath() {
		return GenotypePath;
	}

	public void setGenotypePath(String genotypePath) {
		GenotypePath = genotypePath;
	}
	public String getQtlPath() {
		return qtlPath;
	}

	public void setQtlPath(String qtlPath) {
		this.qtlPath = qtlPath;
	}

	
	public String getPhenotypePath() {
		return PhenotypePath;
	}

	public void setPhenotypePath(String phenotypePath) {
		PhenotypePath = phenotypePath;
	}
	
	public String getLinkagemapPath() {
		return linkagemapPath;
	}

	public void setLinkagemapPath(String linkagemapPath) {
		this.linkagemapPath = linkagemapPath;
	}

	public List<LoadNextGenData> getLoadNextGen() {
		if(loadNextGen == null){
			loadNextGen = new ArrayList<LoadNextGenData>();			
		}
		return loadNextGen;
	}

	public void setLoadNextGen(List<LoadNextGenData> loadNextGen) {
		this.loadNextGen = loadNextGen;
	}

	public Qtl_MapData getQtlMapData() {
		return qtlMapData;
	}

	public void setQtlMapData(Qtl_MapData qtlMapData) {
		this.qtlMapData = qtlMapData;
	}

	public HashMap getType() {
		return type;
	}

	public void setType(HashMap type) {
		this.type = type;
	}

	public List<Integer> getMarkerPrevPos() {
		return markerPrevPos;
	}

	public void setMarkerPrevPos(List<Integer> markerPrevPos) {
		this.markerPrevPos = markerPrevPos;
	}

	public List<Integer> getMarkerNextPos() {
		return markerNextPos;
	}

	public void setMarkerNextPos(List<Integer> markerNextPos) {
		this.markerNextPos = markerNextPos;
	}

	public List<String> getAccession() {
		return accession;
	}

	public void setAccession(List<String> accession) {
		this.accession = accession;
	}

	public List<String> getChrNo() {
		return chrNo;
	}

	public void setChrNo(List<String> chrNo) {
		this.chrNo = chrNo;
	}

	public List<String> getMarkerName() {
		return markerName;
	}

	public void setMarkerName(List<String> markerName) {
		this.markerName = markerName;
	}

	public List<Integer> getMarkerPosition() {
		return markerPosition;
	}

	public void setMarkerPosition(List<Integer> markerPosition) {
		this.markerPosition = markerPosition;
	}

	public List<String> getLabel() {
		return label;
	}

	public void setLabel(List<String> label) {
		this.label = label;
	}	
		
	public List<Genotype> getGenotype() {
		if(genotype==null){
			genotype=new ArrayList<Genotype>();
		}
		return genotype;
	}

	public List<DataValidation> getDataValidation() {
		return dataValidation;
	}

	public void setDataValidation(List<DataValidation> dataValidation) {
		this.dataValidation = dataValidation;
	}

	public void setGenotype(List<Genotype> genotype) {
		this.genotype = genotype;
	}
	
	public List<LinkageMap> getLinkagemap() {
		if(linkagemap==null){
			linkagemap=new ArrayList<LinkageMap>();
		}
		return linkagemap;
	}

	public void setLinkagemap(List<LinkageMap> linkagemap) {
		this.linkagemap = linkagemap;
	}

	public List<QTL> getQtl() {
		if(qtl==null){
			qtl=new ArrayList<QTL>();
		}	
		return qtl;
	}
	
	public void setQtl(List<QTL> qtl) {
		this.qtl = qtl;
	}

	public String getFirstAcc() {
		return firstAcc;
	}

	public void setFirstAcc(String firstAcc) {
		this.firstAcc = firstAcc;
	}

	public List<String> getAccAllele() {
		return AccAllele;
	}

	public void setAccAllele(List<String> accAllele) {
		AccAllele = accAllele;
	}
	
	public LinkageData getLinkData() {
		return linkData;
	}

	public void setLinkData(LinkageData linkData) {
		this.linkData = linkData;
	}

	public Boolean getLoadFlag() {
		return loadFlag;
	}

	public void setLoadFlag(Boolean loadFlag) {
		this.loadFlag = loadFlag;
	}

	public String getLoadProject() {
		return loadProject;
	}

	public void setLoadProject(String loadProject) {
		this.loadProject = loadProject;
	}

	public String getLoadGeneration() {
		return loadGeneration;
	}

	public void setLoadGeneration(String loadGeneration) {
		this.loadGeneration = loadGeneration;
	}

	public HashMap<String, Object> getGenoObject() {
		return genoObject;
	}

	public void setGenoObject(HashMap<String, Object> genoObject) {
		this.genoObject = genoObject;
	}

	public HashMap<String, String> getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(HashMap<String, String> typeValue) {
		this.typeValue = typeValue;
	}

	public String getSaveType() {
		return saveType;
	}

	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}

	public List<Phenotype> getPhenotype() {
		if(phenotype==null){
			phenotype=new ArrayList<Phenotype>();
		}
		return phenotype;
	}

	public void setPhenotype(List<Phenotype> phenotype) {
		this.phenotype = phenotype;
	}

	public String getGeneration() {
		return generation;
	}

	public void setGeneration(String generation) {
		this.generation = generation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	


	
}
