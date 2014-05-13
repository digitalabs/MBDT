package org.icrisat.mbdt.ui.views;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.GenotypeModel.Accessions;
import org.icrisat.mbdt.model.PhenotypeModel.Phenotype;
import org.icrisat.mbdt.model.sessions.Session;

public class PhenotypeDataLabelProvider extends LabelProvider implements ITableLabelProvider {

	static int i=0;	
//	static HashMap<string>
	public Image getColumnImage(Object element, int columnIndex) {
		
		return null;
	}

	
	public String getColumnText(Object element, int columnIndex) {
		
		String result= "";
		((Accessions)element).getCount();
		if(columnIndex==0){
			result = ((Accessions)element).getName();
		}else{
			
			try {
				if(!(((Accessions)element).getTraitValues().get(columnIndex-i).getTraitValues().get(i).toString()==null))
					result = ((Accessions)element).getTraitValues().get(columnIndex-i).getTraitValues().get(i).toString();
				else
					result = "\t";
				i++;
			} catch (Exception e) {
				result = "\t";
				i=0;
			}
		}
		
		if(i==((Accessions)element).getTraits().size()){
			i=0;
		}
		
			 
		return result;
	}

	

	


	

	
}
