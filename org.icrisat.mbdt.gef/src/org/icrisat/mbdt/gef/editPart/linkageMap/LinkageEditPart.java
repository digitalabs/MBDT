package org.icrisat.mbdt.gef.editPart.linkageMap;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.icrisat.mbdt.gef.editPart.targetGenotype.ExampleXYLayoutPolicy;
import org.icrisat.mbdt.model.LinkageMapModel.LinkageMap;

public class LinkageEditPart extends  AbstractGraphicalEditPart {
	List result = new LinkedList();


	@Override
	protected IFigure createFigure() {

		FreeformLayer lyr = new FreeformLayer();
		//for Editing In GEF...XYLayout is used if we want to do drag and drop.....
		lyr.setLayoutManager(new XYLayout());
		return lyr;		
	}

	@Override
	protected void createEditPolicies() {
//		installEditPolicy(EditPolicy.LAYOUT_ROLE, new ExampleXYLayoutPolicy());	
	}
	
	@Override
	protected List getModelChildren() {
		List result12 =new LinkedList();
		try {
			int test =((LinkageMap)getModel()).getChromosomes().size();
			int test1 =((LinkageMap)getModel()).getLinkageScale().size();
			
			if(test!=0) {
				result.addAll(((LinkageMap)getModel()).getChromosomes());
			}
			
			if(test1!=0)
			{
				result.addAll(((LinkageMap)getModel()).getLinkageScale());
			}
			for(Iterator iterator = result.iterator(); iterator.hasNext();){
				
				result12.add(iterator.next());
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result12;
	}
	
}
