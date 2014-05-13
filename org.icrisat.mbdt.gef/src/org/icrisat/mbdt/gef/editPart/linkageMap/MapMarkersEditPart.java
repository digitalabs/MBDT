package org.icrisat.mbdt.gef.editPart.linkageMap;

import java.util.HashMap;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.icrisat.mbdt.gef.editPart.genotype.ExampleFlowLayoutEditPolicy;
import org.icrisat.mbdt.gef.editPartFactory.ChromosomeEditPartFactory;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.LinkageMapModel.MapMarkers;

public class MapMarkersEditPart extends AbstractGraphicalEditPart {

	static int XValue=0;
	float width=0;
	static int i=0;
	static int j=0;
	static HashMap<String, Integer> hashLinkDet = new HashMap<String, Integer>();
	int qtlYValue = 0;
	float scale = 0.0f;
	static int count = 0;
	static int XVal = 0;
	@Override
	protected IFigure createFigure() {
		i++;
		MapMarkers gM= (MapMarkers)getModel();
		
		//Code added by SWAPNA on 17th NOV 2009.....
		//Qtl_MapData qtl_MapData =Qtl_MapData.getQtl_MapData();
		
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
//		scale = scale+0.5f;
		Figure fig=new Figure();
		try{
			String Distance1 = gM.getDistance();
			Float Distance2 = Float.parseFloat(Distance1);
			width=(Distance2);
			//LinkageData linkage = LinkageData.getLinkageData();
			if((i ==((linkage.getMarkerPositions()).size()+1)) ){
				XValue=0;
				i=1;
				j=0;
				width=0;
				count = 0;
				XVal = 0;
			}

			XValue=XValue+(int)(Math.round(width*scale));
			if(j!=0){
				if(!(linkage.getChromName().get(j-1).equals(null))){
					
					if(!((linkage.getChromName().get(j).equals(linkage.getChromName().get(j-1))))){
						XValue=XValue+30;
						//Code by Swapna on NOV 11th 2009.....
						hashLinkDet.put(linkage.getChromName().get(j), XValue);
						qtl_MapData.setHashLinkDetails(hashLinkDet);
						count++;
					}
				}
			}
			//LinkageData lk = LinkageData.getLinkageData();
			fig.setBackgroundColor(ColorConstants.black);
			try {
				if(lk.getListSelMarkers().contains(gM.getName())) {
					fig.setBackgroundColor(ColorConstants.red);
				}
			}catch(Exception e){}
			fig.setOpaque(true);
			
			//Code added by SWAPNA on 17th NOV 2009.....
			//if(qtl_MapData.getQtlYValue() != 0){
				qtlYValue = qtl_MapData.getQtlYValueForOthers();
				if(linkage.isCview()==true){
					try {
						
						if((linkage.getSelectedChromosome().equals(linkage.getChromlist().get(count))&&(linkage.isCview()==true))){
							linkage.setCview(false);
						XVal=XVal+(int)(Math.round(width*scale*2.5f));
						Rectangle position1= new Rectangle(XVal+70, 25+30+qtlYValue+20, 1, 12);
						fig.setBounds(position1);
						fig.setToolTip(new Label(gM.getName()));
						}
					}catch(Exception e){
						
					}
				}else{
					Rectangle position1= new Rectangle(XValue+70, 25+30+qtlYValue+20, 1, 12);
					fig.setBounds(position1);
					fig.setToolTip(new Label(gM.getName()));
				}
				
				
			
		} catch(Exception e){
		}
		j++;
		return fig;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new ExampleFlowLayoutEditPolicy());
	}
}