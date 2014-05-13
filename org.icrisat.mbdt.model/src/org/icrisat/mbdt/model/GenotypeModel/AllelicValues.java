package org.icrisat.mbdt.model.GenotypeModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllelicValues implements Serializable {

	List allelicValues= new ArrayList();

	public List getAllelicValues() {
		
		return allelicValues;
	}

	public void setAllelicValues(List allelicValues) {
		this.allelicValues = allelicValues;
	}
}
