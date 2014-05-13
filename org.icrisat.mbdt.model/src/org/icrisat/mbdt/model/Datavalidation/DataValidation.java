package org.icrisat.mbdt.model.Datavalidation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataValidation implements Serializable {

	private DataValidation()
    {
        // no code req'd
    }

    public static DataValidation getValidationData()
    {
      if (ref == null)
          ref = new DataValidation();
      return ref;
    }

    private static DataValidation ref;
    
    
    
	
	List<String> GenoValidationrules = new ArrayList<String>();
	List<String> MapValidationrules = new ArrayList<String>();
	List<String> QtlValidationrules = new ArrayList<String>();
	List<String> phenoValidationrules = new ArrayList<String>();
	
	
	
	public List<String> getGenoValidationrules() {
		return GenoValidationrules;
	}
	public void setGenoValidationrules(List<String> genoValidationrules) {
		GenoValidationrules = genoValidationrules;
	}
	public List<String> getMapValidationrules() {
		return MapValidationrules;
	}
	public void setMapValidationrules(List<String> mapValidationrules) {
		MapValidationrules = mapValidationrules;
	}
	public List<String> getQtlValidationrules() {
		return QtlValidationrules;
	}
	public void setQtlValidationrules(List<String> qtlValidationrules) {
		QtlValidationrules = qtlValidationrules;
	}
	public List<String> getPhenoValidationrules() {
		return phenoValidationrules;
	}
	public void setPhenoValidationrules(List<String> phenoValidationrules) {
		this.phenoValidationrules = phenoValidationrules;
	}

}
