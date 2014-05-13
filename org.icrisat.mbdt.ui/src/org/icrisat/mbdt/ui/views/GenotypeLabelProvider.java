package org.icrisat.mbdt.ui.views;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.icrisat.mbdt.model.GenotypeModel.Accessions;
import org.icrisat.mbdt.model.GenotypeModel.Genotype;
import org.icrisat.mbdt.model.GenotypeModel.GenotypeMarkers;

public class GenotypeLabelProvider extends LabelProvider implements ITableLabelProvider {

	List<GenotypeMarkers> gm;
	int j=0;
	String preName= "";
	String name= "";
	
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}
	int i=0;
	
	public String getColumnText(Object element, int columnIndex) {
		String result= "";
		switch(columnIndex) {
		case 0:
			result= ((Accessions)element).getId();
			break;
		case 1:
			result= ((Accessions)element).getName();
			name= result;
			break;
		default:
			if(!(name.equals(preName))) {
				j=0;
			}
			result= ((Accessions)element).getAllelicValues().iterator().next().getAllelicValues().get(j).toString();
			j++;
			preName= name;
			break;
		}
		return result;
	}
}