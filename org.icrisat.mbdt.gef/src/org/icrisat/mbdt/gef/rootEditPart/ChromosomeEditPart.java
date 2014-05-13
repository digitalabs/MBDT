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
import org.icrisat.mbdt.model.Chromosome.Chromosome;
import org.icrisat.mbdt.model.sessions.Session;
import org.icrisat.mbdt.model.sessions.SessionChromosome;

public class ChromosomeEditPart extends AbstractGraphicalEditPart implements PropertyChangeListener {

	@Override
	protected IFigure createFigure() {
		FreeformLayer lyr = new FreeformLayer();
		lyr.setLayoutManager(new XYLayout());
		return lyr;

	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub

	}

	@Override
	protected List getModelChildren() {
				
		List result =new LinkedList();
		LinkedHashSet result1=new LinkedHashSet();
		SessionChromosome.getInstance().getChromosome().setLinkagemap(Session.getInstance().getRootmodel().getLinkagemap());
		SessionChromosome.getInstance().getChromosome().setQtl(Session.getInstance().getRootmodel().getQtl());
		SessionChromosome.getInstance().getChromosome().setGenotype(Session.getInstance().getRootmodel().getGenotype());
		SessionChromosome.getInstance().getChromosome().setLoadNextGen(Session.getInstance().getRootmodel().getLoadNextGen());
		if((!SessionChromosome.getInstance().getChromosome().getGenotype().isEmpty())){
			result1.addAll(((Chromosome)getModel()).getGenotype());
		}
		
		if((!SessionChromosome.getInstance().getChromosome().getLinkagemap().isEmpty())){	
			result1.addAll(((Chromosome)getModel()).getLinkagemap());

		} 

		if((!SessionChromosome.getInstance().getChromosome().getQtl().isEmpty())){
			result1.addAll(((Chromosome)getModel()).getQtl());
		}
		if((!SessionChromosome.getInstance().getChromosome().getLoadNextGen().isEmpty())){
			result1.addAll(((Chromosome)getModel()).getLoadNextGen());
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

