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
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.LinkageMapModel.LinkageScaleModel;

public class ScaleEditPart extends AbstractGraphicalEditPart {

	static int j=0;
	int qtlYValue = 0;
	int  location=0;
	int Postion_CM=0;
	List scale_distance = new ArrayList();
	List scale_cm = new ArrayList();
	float scale = 0.0f;
	static int count = 0;
	@Override
	protected IFigure createFigure() {  
				
		RootModel rootModel = RootModel.getRootModel();
		LinkageData linkage;
		Qtl_MapData qtl_MapData;
		
		FreeformLayer lyr = new FreeformLayer();
		lyr.setLayoutManager(new XYLayout());
		
		
		try{
			if(rootModel.getLoadFlag() == null){
				 qtl_MapData = Qtl_MapData.getQtl_MapData();
				 linkage = LinkageData.getLinkageData();
			}else{
				 qtl_MapData = rootModel.getQtlMapData();
					linkage = rootModel.getLinkData();
			}
			scale = linkage.getScale();
			scale_distance= linkage.getScalePositions();
			scale_cm = linkage.getScaleCm();
			if(j==scale_distance.size()){
				j = 0;
				
			}
			if(linkage.isCview()==true){
				
				try {
					if((linkage.getSelectedChromosome().equals(linkage.getChromlist().get(count))&&(linkage.isCview()==true))){
						
						//---start for scale label
						Font classFont = new Font(null, "verdana",4, SWT.BOLD);
						Font classFont1 = new Font(null, "verdana",6, SWT.BOLD);
						Label lbl1 = new Label();
						lbl1.setFont(classFont1);
						lbl1.setText("cM");
						((IFigure) lbl1).setForegroundColor(ColorConstants.black);
						((IFigure) lbl1).setOpaque(true);
						lyr.add((IFigure) lbl1);
						qtlYValue = qtl_MapData.getQtlYValueForOthers();
						Rectangle posLabel= new Rectangle(40,55+6+25+qtlYValue+20, 20, 15);
						lbl1.setBounds(posLabel);
						
						Label lbl2 = new Label();
						lbl2.setFont(classFont);
						
							Float cumudistance =Float.parseFloat(linkage.getChrmno_cumdis().get(linkage.getSelectedChromosome()).toString());
							
//																
							float temp =cumudistance*scale*2.5f;
			                int cumdis= (int)(Math.round(temp));
							 
							int Postion_cm=Integer.parseInt(scale_cm.get(j).toString());
							 int dist = Math.round(Float.parseFloat(scale_cm.get(j).toString())*scale*2.5f);
							if(dist<cumdis)
							{
								
								float  temp1=25*scale;
								int sample	=(int)(Math.round(temp1));
								
								lbl2.setText(""+Postion_cm);
								((IFigure) lbl2).setForegroundColor(ColorConstants.black);
								((IFigure) lbl2).setOpaque(true);
								lyr.add((IFigure) lbl2);
								Rectangle posLabel1= new Rectangle(63+dist, 55+25+qtlYValue+20,12,5);
								lbl2.setBounds(posLabel1);
//								Postion_cm=Postion_cm+25;
//								dist=dist+sample;
							}
							
						//-----end for scale label
						
						
						
				}
					if((linkage.isCview()==true)&&(!linkage.getSelectedChromosome().equals(linkage.getChromlist().get(count)))){
						count ++;
						}
				}catch(Exception e){
					
				}
			}else{
			
			if(scale_distance.size() > j){
				
			location = Math.round(Float.parseFloat(scale_distance.get(j).toString())); 
			Postion_CM = Integer.parseInt(scale_cm.get(j).toString()); 
			
					
					Font classFont = new Font(null, "verdana",4, SWT.BOLD);
					Font classFont1 = new Font(null, "verdana",6, SWT.BOLD);
					Label lbl1 = new Label();
					lbl1.setFont(classFont1);
					lbl1.setText("cM");
					((IFigure) lbl1).setForegroundColor(ColorConstants.black);
					((IFigure) lbl1).setOpaque(true);
					lyr.add((IFigure) lbl1);
					qtlYValue = qtl_MapData.getQtlYValueForOthers();
					Rectangle posLabel= new Rectangle(40,55+6+25+qtlYValue+20, 20, 15);
					lbl1.setBounds(posLabel);
					
					Label lbl2 = new Label();
					lbl2.setFont(classFont);
					lbl2.setText(""+Postion_CM);
					((IFigure) lbl2).setForegroundColor(ColorConstants.black);
					((IFigure) lbl2).setOpaque(true);
					lyr.add((IFigure) lbl2);
					
					Rectangle posLabel1= new Rectangle(location+63, 55+25+qtlYValue+20,12,5);
					lbl2.setBounds(posLabel1);
					}
				}
		}catch(Exception e){
		}
		j++;
		return lyr;
	}
	
	protected List getModelChildren() {
		List result =new LinkedList();
		
		LinkedHashSet result1=new LinkedHashSet();

		try {
			
			if((((LinkageScaleModel)getModel()).getMarkpositionsScale().size())!=0){
				result1.addAll(((LinkageScaleModel)getModel()).getMarkpositionsScale());
			} 
			
			if((((LinkageScaleModel)getModel()).getMarkersScale().size())!=0){
				result1.addAll(((LinkageScaleModel)getModel()).getMarkersScale());
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
	}
}
