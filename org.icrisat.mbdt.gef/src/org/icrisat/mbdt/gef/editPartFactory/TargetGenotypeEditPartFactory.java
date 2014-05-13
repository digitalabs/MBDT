package org.icrisat.mbdt.gef.editPartFactory;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.icrisat.mbdt.gef.editPart.targetGenotype.ColorAlleleEditPart;
import org.icrisat.mbdt.gef.editPart.targetGenotype.MarkerParentsEditPart;
import org.icrisat.mbdt.gef.editPart.targetGenotype.ParentsEditPart;
import org.icrisat.mbdt.gef.editPart.targetGenotype.SelectedParentsEditPart;
import org.icrisat.mbdt.gef.editPart.targetGenotype.TargetGenotypeEditPart;
import org.icrisat.mbdt.model.TargetGenotype.ColorAllele;
import org.icrisat.mbdt.model.TargetGenotype.MarkersSelectedParents;
import org.icrisat.mbdt.model.TargetGenotype.Parents;
import org.icrisat.mbdt.model.TargetGenotype.SelectedParents;
import org.icrisat.mbdt.model.TargetGenotype.TargetGeno;

public class TargetGenotypeEditPartFactory implements EditPartFactory {

	
	public EditPart createEditPart(EditPart context, Object model) {
		
		EditPart ep= null;
		//System.out.println("Model :"+model);
		if(model instanceof TargetGeno) {
			ep= new TargetGenotypeEditPart();
		}
		if(model instanceof Parents) {
			ep= new ParentsEditPart();
		}
		if(model instanceof SelectedParents) {
			ep= new SelectedParentsEditPart();
		}
		if(model instanceof MarkersSelectedParents) {
			ep= new MarkerParentsEditPart();
		}
		if(model instanceof ColorAllele) {
			ep= new ColorAlleleEditPart();
		}
		ep.setModel(model);
		return ep;
	}
}
