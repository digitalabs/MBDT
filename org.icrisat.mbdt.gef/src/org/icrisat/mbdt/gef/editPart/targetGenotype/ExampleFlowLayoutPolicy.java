package org.icrisat.mbdt.gef.editPart.targetGenotype;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.FlowLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.icrisat.mbdt.gef.editPart.genotype.ExampleMoveChildCommand;

public class ExampleFlowLayoutPolicy extends FlowLayoutEditPolicy implements
		EditPolicy {

	@Override
	protected Command createAddCommand(EditPart child, EditPart after) {
//		System.out.println("Moving child");
		return null;
	}

	@Override
	protected Command createMoveChildCommand(EditPart child, EditPart after) {
//		System.out.println("Moving child");
		return new ExampleMoveChildCommand(child,after);
	}

	@Override
	protected Command getCreateCommand(CreateRequest request) {
//		System.out.println("Moving child");
		return null;
	}
}
