package org.icrisat.mbdt.gef.editPart.targetGenotype;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.icrisat.mbdt.model.TargetGenotype.TargetGeno;

public class TargetGenotypeEditPart extends AbstractGraphicalEditPart {

	@Override
	protected IFigure createFigure() {
//		System.out.println("result :");
		FreeformLayer lyr = new FreeformLayer();
//		lyr.setLayoutManager(new XYLayout());
		return lyr;
	}

	@Override
	protected void createEditPolicies() {
//		installEditPolicy(EditPolicy.LAYOUT_ROLE, new ExampleXYLayoutPolicy());
	}	
	
	public List getModelChildren() {
		List result=new ArrayList();
		for(int i=0; i<((TargetGeno)getModel()).getParents().size(); i++){
			if(((TargetGeno)getModel()).getParents().size()!=0){
				result.add((((TargetGeno)getModel()).getParents().get(i)));
			}
		}
//		System.out.println("result :"+result);
		return result;
	}
}
