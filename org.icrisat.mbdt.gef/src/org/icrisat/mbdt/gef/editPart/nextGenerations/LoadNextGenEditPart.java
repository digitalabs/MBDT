package org.icrisat.mbdt.gef.editPart.nextGenerations;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.NextGeneration.LoadNextGenData;

public class LoadNextGenEditPart extends AbstractGraphicalEditPart{

	@Override
	protected IFigure createFigure() {
		// TODO Auto-generated method stub
		FreeformLayer lyr = new FreeformLayer();
		return lyr;
	} 

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List getModelChildren() {
		List result=new ArrayList();
		
		result = (((LoadNextGenData)getModel()).getListtargetAcc());
//		System.out.println("result in 1st editpart :"+result); 
		return result;
	}

}
 