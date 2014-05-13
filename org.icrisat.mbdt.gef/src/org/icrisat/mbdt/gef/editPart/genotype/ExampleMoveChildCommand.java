package org.icrisat.mbdt.gef.editPart.genotype;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.icrisat.mbdt.gef.editPart.targetGenotype.MarkerParentsEditPart;
import org.icrisat.mbdt.model.TargetGenotype.MarkersSelectedParents;

public class ExampleMoveChildCommand extends Command {
	
	private EditPart child;
	private EditPart after;

	public ExampleMoveChildCommand(EditPart child, EditPart after) {
		this.child = child;
		this.after = after;
	}
	
	@Override
	public void execute() {
		super.execute();
		MarkersSelectedParents mp= (MarkersSelectedParents) ((MarkerParentsEditPart)child.getParent()).getModel();		
		
		/*AccessionPoint ap = (AccessionPoint) ((AccessionPointEditPart)child.getParent()).getModel();
		
		int position = ap.getMarkers().indexOf(after.getModel());
		ap.getMarkers().remove(child.getModel());
		ap.getMarkers().add(position, (Marker) child.getModel());
		
		child.getParent().refresh();*/
		
	}
}
