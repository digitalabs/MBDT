package org.icrisat.mbdt.gef.editPart.targetGenotype;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.TargetGenotype.Parents;

public class ParentsEditPart extends AbstractGraphicalEditPart  {

	static int n=0, p=0;
	String label= null;
	static String label1= "Recurrent";
	String labelText= "_";
	//LinkageData lk= LinkageData.getLinkageData();
	List<String> markercumPositions= new ArrayList<String>();
	List<String> markerPositions= new ArrayList<String>();
	int width1= 0;
	RootModel rootModel = RootModel.getRootModel();
	LinkageData lk;
	float scale = 0.0f;
	@Override
	protected IFigure createFigure() {	
		Parents parent = (Parents)getModel();
		Figure fig= new Figure();
		fig.setBackgroundColor(ColorConstants.white);
//		System.out.println("parent.getParent= "+parent.getParent());
		if(rootModel.getLoadFlag() == null){
			lk = LinkageData.getLinkageData();
		}else{			
			lk = rootModel.getLinkData();
		}
		scale = lk.getScale();
		if(p >= 3) {
			p = 0;
			n = 0;
		}
		String type = parent.getType();
		Label lbl= new Label();
		Font classFont = new Font(null, "Arial", 10, SWT.BOLD);
		lbl.setFont(classFont);
		if(type.equals("Donor")) {
			labelText= "D";
		}
		else if(type.equals("Recurrent")){
			labelText= "R";
		}
		else if(type.equals("Target")) {
			labelText= "T";
		}
		lbl.setText(labelText);
		((IFigure) lbl).setForegroundColor(ColorConstants.darkGray);
		((IFigure) lbl).setOpaque(true);
		
		markercumPositions= lk.getDistances();
		markerPositions= lk.getDistances();
		for(int i=0; i<(markercumPositions.size()); i++){
			String dist= (markerPositions.get(i)).toString();
			Float Distance1= Float.parseFloat(dist)*scale;
			int width= Math.round(Distance1);
			width1= width1+width;
		}
		String count= lk.getCount();
		int chrCount= Integer.parseInt(count);
		fig.add((IFigure) lbl);
		Rectangle position= new Rectangle(0, 20+n, width1+(chrCount*30), 10);
		fig.setBounds(position);
		Rectangle posLabel= new Rectangle(0, 20+n, 20, 10);
		lbl.setBounds(posLabel);
		label= parent.getType();
		p++;
//		fig.setBackgroundColor(ColorConstants.red);
//		fig.setOpaque(true);
		fig.setToolTip(new Label(label+": "+parent.getParent()));
		n= n+20;
		return fig;
	}

	@Override
	protected void createEditPolicies() {
//		installEditPolicy(EditPolicy.LAYOUT_ROLE, new ExampleXYLayoutPolicy());
	}

	protected List getModelChildren(){
		if(rootModel.getLoadFlag() == null){
			lk = LinkageData.getLinkageData();
		}else{			
			lk = rootModel.getLinkData();
		}
		List child= new ArrayList();
		try{
		String countTemp= lk.getCount();
		// List temp= new ArrayList();
		int count= Integer.parseInt(countTemp);
		if(!(((Parents)getModel()).getSelParents().toString()==null)) {
			for(int i=0; i<count; i++) {
				((Parents)getModel()).getSelParents().iterator().next().setSelectedParents(((Parents)getModel()).getSelParents().iterator().next().getSelectedParents());	
				child.add(((Parents)getModel()).getSelParents().get(0));
			}
		}
		}catch(Exception e){
//			e.printStackTrace();
		}
		return child;
	}
}
