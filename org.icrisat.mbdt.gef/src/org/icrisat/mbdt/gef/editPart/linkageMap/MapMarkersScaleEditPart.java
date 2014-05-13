package org.icrisat.mbdt.gef.editPart.linkageMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;

import org.icrisat.mbdt.gef.editPart.genotype.ExampleFlowLayoutEditPolicy;
import org.icrisat.mbdt.gef.editPartFactory.ChromosomeEditPartFactory;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.LinkageMapModel.MapMarkersScale;

public class MapMarkersScaleEditPart extends AbstractGraphicalEditPart {

	
	static int j=0;
	int qtlYValue = 0;
	int  location=0;
	List scale_distance = new ArrayList();
	List scale_cm  		= new ArrayList();
	static int count = 0;
	static int XVal = 0;
	static String chrom = "";
	float scale = 0.0f;
	@Override
	protected IFigure createFigure() {
		MapMarkersScale gM= (MapMarkersScale)getModel();
		
		RootModel rootModel = RootModel.getRootModel();
		LinkageData linkage;
		Qtl_MapData qtl_MapData;
		LinkageData lk;
		
		if(rootModel.getLoadFlag() == null){
			 qtl_MapData = Qtl_MapData.getQtl_MapData();
			 linkage = LinkageData.getLinkageData();
			 lk = LinkageData.getLinkageData();
		}else{			 
			qtl_MapData = rootModel.getQtlMapData();
			linkage = rootModel.getLinkData();
			lk = rootModel.getLinkData();
		}
		scale = lk.getScale();
		Figure fig=new Figure();
	
		try{
//			LinkageData linkage = LinkageData.getLinkageData();
			scale_distance= linkage.getScalePositions();
			scale_cm= 		linkage.getScaleCm();
			fig.setBackgroundColor(ColorConstants.black);
			fig.setOpaque(true);
		if(j==scale_distance.size()){
			j = 0;
			count = 0;
			/*if(linkage.isCview()==true){
			count ++;
			}*/
			
		}
		if(linkage.isCview()==true){
			try {
				if(scale_cm.get(j).equals(0)){
					
					if(linkage.getChromlist().get(count).equals(linkage.getSelectedChromosome())){
						chrom = linkage.getChromlist().get(count).toString();
					}else{
						chrom = "";
					}
				count++;
				
			}
				if((linkage.getSelectedChromosome().equals(chrom)&&(linkage.isCview()==true))){
					linkage.setCview(false);
		
				if(scale_distance.size() > j){
					
					location = Math.round(Integer.parseInt(scale_cm.get(j).toString())*scale*2.5f); 
					
							qtlYValue = qtl_MapData.getQtlYValueForOthers();
							Rectangle position1= new Rectangle(location+70, 55+35+qtlYValue+20, 1, 6);
							fig.setBounds(position1);
						
					
				    String val2 = Integer.toString (Integer.parseInt(scale_cm.get(j).toString()));
				    fig.setToolTip(new Label(val2));
					}
				}
				}catch(Exception e){
					
				}
			}else{
			if(scale_distance.size() > j){
				
			location = Integer.parseInt(scale_distance.get(j).toString()); 
			
					qtlYValue = qtl_MapData.getQtlYValueForOthers();
					Rectangle position1= new Rectangle(location+70, 55+35+qtlYValue+20, 1, 6);
					fig.setBounds(position1);
			
		    String val2 = Integer.toString (Integer.parseInt(scale_cm.get(j).toString()));
		    fig.setToolTip(new Label(val2));
			}
			}
//		count++;
		}catch(Exception e){
		}
		j++;
		return fig;
	}

	@Override
	protected void createEditPolicies() {
//		installEditPolicy(EditPolicy.LAYOUT_ROLE, new ExampleFlowLayoutEditPolicy());
	}
}