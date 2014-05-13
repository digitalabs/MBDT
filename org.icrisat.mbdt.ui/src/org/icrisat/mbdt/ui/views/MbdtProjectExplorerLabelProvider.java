package org.icrisat.mbdt.ui.views;

import org.eclipse.jface.viewers.LabelProvider;

import org.icrisat.mbdt.model.RootModel;

public class MbdtProjectExplorerLabelProvider extends LabelProvider {
	
	@Override
	public String getText(Object element) {
		if (element instanceof RootModel) {
			return ((RootModel)element).getName();
		} else if (element instanceof org.icrisat.mbdt.model.GenotypeModel.Genotype) {
			return "Genotype File";
		} else if (element instanceof String) {
			return ((String)element);
		}
		return "";
	}

}
