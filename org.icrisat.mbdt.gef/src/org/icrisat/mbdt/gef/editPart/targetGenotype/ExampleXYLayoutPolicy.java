package org.icrisat.mbdt.gef.editPart.targetGenotype;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

public class ExampleXYLayoutPolicy extends XYLayoutEditPolicy {

	@Override
	//this fucntion gets fired when we move the figure and executes whenever we dropit to a new Place
	protected Command createChangeConstraintCommand(EditPart child, Object constraint) {
		/*
		 * child is the Accesspoint and constraint is the new position
		 */
//		System.out.println("CreateChangeCommand1");
//		System.out.println("child*******************"+child);
		return new ExampleChangeConstraintCommand(child,constraint);	
	}

	@Override
	protected Command getCreateCommand(CreateRequest request) {
//		System.out.println("CreateChangeCommand2");
		Object obj = request.getNewObject();
		Rectangle position = (Rectangle) getConstraintFor(request);
//		System.out.println("Rectangle position= "+position);
		return new ExampleCreateCommand(obj, position, getHost());
	}
}
