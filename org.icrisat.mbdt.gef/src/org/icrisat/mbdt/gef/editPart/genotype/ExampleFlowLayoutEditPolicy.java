package org.icrisat.mbdt.gef.editPart.genotype;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.FlowLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

public class ExampleFlowLayoutEditPolicy extends FlowLayoutEditPolicy {

	@Override
	protected Command createAddCommand(EditPart child, EditPart after) {
//		System.out.println("Add Child**********");
		return new ExampleAddChildCommand(child,after);
		
	}

	@Override
	protected Command createMoveChildCommand(EditPart child, EditPart after) {
//		System.out.println("Move Child**********");
		return new ExampleMoveChildCommand(child,after);
	}

	@Override
	protected Command getCreateCommand(CreateRequest arg0) {
//		System.out.println("Create Command**********");
		return null;
	}
}