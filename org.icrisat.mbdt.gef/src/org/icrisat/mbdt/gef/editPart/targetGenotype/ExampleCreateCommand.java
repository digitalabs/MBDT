package org.icrisat.mbdt.gef.editPart.targetGenotype;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;


public class ExampleCreateCommand extends Command {

	Object child;
	Rectangle position;
	EditPart parent;
	
	public ExampleCreateCommand(Object obj, Rectangle position, EditPart host) {
		this.child = obj;
		this.position = position;
		this.parent = host;		
	}
	
	@Override
	public void execute() {
//		((MarkersSelectedParents)child).setRectanglePositionX(position.x);
//		((MarkersSelectedParents)child).setRectanglePositionY(position.y);
//		((MarkersSelectedParents)child).setWidth(5);
//		((MarkersSelectedParents)child).setHeight(10);
		parent.refresh();
	}
}
