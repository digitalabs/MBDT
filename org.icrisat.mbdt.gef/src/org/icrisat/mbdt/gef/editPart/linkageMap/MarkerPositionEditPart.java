package org.icrisat.mbdt.gef.editPart.linkageMap;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.icrisat.mbdt.gef.editPartFactory.ChromosomeEditPartFactory;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.LinkageMapModel.MarkerPosition;

public class MarkerPositionEditPart extends AbstractGraphicalEditPart {

	static int XValue=0;
	int width=0;
	int width1=0;
	static int i=0;
	static int j=0;
	String pre_dist=null;
	static int count = 0;
	static int XVal = 0;
	int qtlYValue = 0;
	float scale = 0.0f;
	static int sval = 0;
	@Override
	protected IFigure createFigure() {
		i++;
		Figure fig=new Figure();
		MarkerPosition gM= (MarkerPosition)getModel();
		
		//Code added by SWAPNA on 17th NOV 2009.....
		RootModel rootModel = RootModel.getRootModel();
		LinkageData linkage;
		Qtl_MapData qtl_MapData;
		
		if(rootModel.getLoadFlag() == null){
			qtl_MapData = Qtl_MapData.getQtl_MapData();
			 linkage = LinkageData.getLinkageData();
		}else{
			 
			 qtl_MapData = rootModel.getQtlMapData();
				linkage = rootModel.getLinkData();
		}
		scale = linkage.getScale();
		try {
			String Distance1=gM.getDistance();
			Float Distance2=Float.parseFloat(Distance1)*scale;
			width=Math.round(Distance2);
			if((i==(linkage.getMarkerPositions()).size()+1)){
				XValue=0;
				i=1;
				j=0;
				width=0;
				count = 0;
				XVal = 0;
				sval = 0;
			}

			if(j!=0){
				pre_dist=String.valueOf(linkage.getDistances().get(j-1));
			}else{
				pre_dist="0";
			}
			Float Distance3=Float.parseFloat(pre_dist)*scale;
			width1=Math.round(Distance3);

			XValue=XValue+width1;

			if(j!=0){
				if(!(linkage.getChromName().get(j-1).equals(null))){
					if(!((linkage.getChromName().get(j).equals(linkage.getChromName().get(j-1))))){
						XValue=XValue+30;
						count++;
					}					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		fig.setBackgroundColor(ColorConstants.red);
		fig.setOpaque(true);
		qtlYValue = qtl_MapData.getQtlYValueForOthers();
		
		if(linkage.isCview()==true){
			try {
				
				if((linkage.getSelectedChromosome().equals(linkage.getChromlist().get(count))&&(linkage.isCview()==true))){
					linkage.setCview(false);
				if(sval == 0){
					width1 = 0;
					sval =1;
				}
				XVal=XVal+Math.round(width1*2.5f);
				
				Rectangle position1= new Rectangle(XVal+70, 25+6+30+qtlYValue+20, Math.round(width*2.5f),1);
				fig.setBounds(position1);
				}
			}catch(Exception e){
				
			}
		}else{
			Rectangle position1= new Rectangle(XValue+70,25+6+30+qtlYValue+20, width,1);
			fig.setBounds(position1);
		}
			
		j++;
		return fig;
	}

	@Override
	protected void createEditPolicies() {
//		installEditPolicy(EditPolicy.LAYOUT_ROLE, new ExampleFlowLayoutEditPolicy());
	}
}
