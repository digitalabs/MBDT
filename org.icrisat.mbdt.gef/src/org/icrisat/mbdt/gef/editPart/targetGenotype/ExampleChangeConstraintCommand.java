package org.icrisat.mbdt.gef.editPart.targetGenotype;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;

public class ExampleChangeConstraintCommand extends Command {

	private Rectangle position;
	private EditPart child;

	public ExampleChangeConstraintCommand(EditPart child, Object constraint) {
		this.child = child;
		this.position = (Rectangle) constraint;
	}
	
	public void execute(){
		// Asking the controller to refresh UI so now it goes to AccessionPointEditPart() and executes refreshVisuals() which refreshes the UI
		child.refresh();
	}
}
