package org.icrisat.mbdt.gef.editPart.linkageMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.icrisat.mbdt.gef.editPart.targetGenotype.ExampleXYLayoutPolicy;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.LinkageMapModel.Intervals;
import org.icrisat.mbdt.model.LinkageMapModel.MapMarkers;

public class MapIntervalsEditPart extends AbstractGraphicalEditPart {

	int gk;
	static int YValue= 25;
	static int XValue= 0;
	int pre_width= 0;
	int width= 0;
	int width1= 0;
	int Dist_Of_2Markers= 0;
	int yy= 0;
	int  location=0;
	int Previous_Dist= 0;
	static int i= 0, n= 0;
	private String df;
	static int j=0;
	int qtlYValue = 0;
	String Distance= null;
	List scale_distance = new ArrayList();
	float scale = 0.0f;
	static int distace_cm =0;
	@Override
	protected IFigure createFigure() {
		RootModel rootModel = RootModel.getRootModel();
		LinkageData linkage;
		Qtl_MapData qtl_MapData;
	
		
		FreeformLayer lyr = new FreeformLayer();
		lyr.setLayoutManager(new XYLayout());
		
		if(rootModel.getLoadFlag() == null){
			 qtl_MapData = Qtl_MapData.getQtl_MapData();
			 linkage = LinkageData.getLinkageData();
		}else{
			 qtl_MapData = rootModel.getQtlMapData();
				linkage = rootModel.getLinkData();
		}
		scale = linkage.getScale();
			if((i ==(linkage.getDistances()).size())){
				i=0;
				distace_cm=0;
			}
			try{
		Font classFont = new Font(null, "verdana",6, SWT.BOLD);
		Label lbl1 = new Label();
		lbl1.setFont(classFont);
		lbl1.setText("LinkageMap");
		((IFigure) lbl1).setForegroundColor(ColorConstants.black);
		((IFigure) lbl1).setOpaque(true);
		lyr.add((IFigure) lbl1);
		if(!(qtl_MapData==null)){
		qtlYValue = qtl_MapData.getQtlYValueForOthers();
		}
		Rectangle posLabel= new Rectangle(10, 25+6+30+qtlYValue+20, 55, 10);
		lbl1.setBounds(posLabel);
		int temp =Math.round(Float.parseFloat(qtl_MapData.getMarkerPositions().get(i)));
		scale_distance=linkage.getScaleChom_start();
		
		if((temp  == 0)&&(i==0))
		{
			Label lbl2 = new Label();
			lbl2.setFont(classFont);
			int chom_no =distace_cm+1;
			int temp1 =chom_no-1;
			lbl2.setText( linkage.getChromlist().get(temp1));
			((IFigure) lbl2).setForegroundColor(ColorConstants.black);
			((IFigure) lbl2).setOpaque(true);
			lyr.add((IFigure) lbl2);
			
			Rectangle posLabel1 = null;
			if(linkage.isCview()==true){
				try {
					if((linkage.getSelectedChromosome().equals(linkage.getChromlist().get(temp1))&&(linkage.isCview()==true))){
						linkage.setCview(false);
					
					posLabel1= new Rectangle(70, 25+13+30+qtlYValue+20,Math.round(65*2.5f),9);
					lbl2.setBounds(posLabel1);
				}
				}catch(Exception e){
					
				}
			}else{	
			
			location = Math.round(Float.parseFloat(scale_distance.get(distace_cm).toString())); 
				posLabel1= new Rectangle(location+70, 25+13+30+qtlYValue+20,65,9);
				lbl2.setBounds(posLabel1);
			}
			distace_cm++;
		}else if((temp  == 0)&&(!qtl_MapData.getChromosome().get(i).equals(qtl_MapData.getChromosome().get(i-1))))
		{
			Label lbl2 = new Label();
			lbl2.setFont(classFont);
			int chom_no =distace_cm+1;
			int temp1 =chom_no-1;
			lbl2.setText( linkage.getChromlist().get(temp1));
			((IFigure) lbl2).setForegroundColor(ColorConstants.black);
			((IFigure) lbl2).setOpaque(true);
			lyr.add((IFigure) lbl2);
			
			Rectangle posLabel1 = null;
			if(linkage.isCview()==true){
				try {
					if((linkage.getSelectedChromosome().equals(linkage.getChromlist().get(temp1))&&(linkage.isCview()==true))){
						linkage.setCview(false);
					
					posLabel1= new Rectangle(70, 25+13+30+qtlYValue+20,Math.round(65*2.5f),9);
					lbl2.setBounds(posLabel1);
				}
				}catch(Exception e){
					
				}
			}else{	
			
			location = Math.round(Float.parseFloat(scale_distance.get(distace_cm).toString())); 
				posLabel1= new Rectangle(location+70, 25+13+30+qtlYValue+20,65,9);
				lbl2.setBounds(posLabel1);
			}
			distace_cm++;
		}
		i++;
			}catch(Exception e){
			}
		return lyr;
	}
	
	protected List getModelChildren() {
		
			List result =new LinkedList();
			LinkedHashSet result1=new LinkedHashSet();
		
			try {
				if((((Intervals)getModel()).getMarkpositions().size())!=0){
					result1.addAll(((Intervals)getModel()).getMarkpositions());
				} 
				if((((Intervals)getModel()).getMarkers().size())!=0){
					result1.addAll(((Intervals)getModel()).getMarkers());
				}
				for(Iterator iterator = result1.iterator(); iterator.hasNext();){
					result.add(iterator.next());
				}
			}catch(Exception e){
		}
			
		return result;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new ExampleXYLayoutPolicy());
	}
}