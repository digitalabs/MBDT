package org.icrisat.mbdt.gef.rootEditPart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.sessions.Session;

public class RootEditPart extends AbstractGraphicalEditPart implements PropertyChangeListener {
	//, ILoadNotifier

	@Override
	protected IFigure createFigure() {
		FreeformLayer lyr = new FreeformLayer();
		lyr.setLayoutManager(new XYLayout());
		//System.out.println(" im in root editpart ");
		return lyr;

		/*Figure fig = new FreeformLayer();
		fig.setBackgroundColor(ColorConstants.green);
		fig.setOpaque(true);
		return fig;*/
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub

	}

	@Override
	protected List getModelChildren() {
				
		List result =new LinkedList();
		LinkedHashSet result1=new LinkedHashSet();
		if((!Session.getInstance().getRootmodel().getGenotype().isEmpty())){
			result1.addAll(((RootModel)getModel()).getGenotype());
		}
		if((!Session.getInstance().getRootmodel().getLinkagemap().isEmpty())){	
			result1.addAll(((RootModel)getModel()).getLinkagemap());
		} 

		if((!Session.getInstance().getRootmodel().getQtl().isEmpty())){
			result1.addAll(((RootModel)getModel()).getQtl());
		}
		if((!Session.getInstance().getRootmodel().getLoadNextGen().isEmpty())){
			result1.addAll(((RootModel)getModel()).getLoadNextGen());
		}
		
		for(Iterator iterator = result1.iterator(); iterator.hasNext();){
			result.add(iterator.next());
		}
		return result;
	}

	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub

	}

}

