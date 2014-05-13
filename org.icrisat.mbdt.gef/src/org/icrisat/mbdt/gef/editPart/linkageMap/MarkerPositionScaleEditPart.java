package org.icrisat.mbdt.gef.editPart.linkageMap;

import java.util.ArrayList;
import java.util.List;

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

public class MarkerPositionScaleEditPart extends AbstractGraphicalEditPart {
	
	static int j=0;
	static int cumcout=0;
	int qtlYValue = 0;
	int  location=0;
	List scale_distance = new ArrayList();
	List scale_cm  		= new ArrayList();
	List Cumdistance    = new ArrayList();
	float scale = 0.0f;
	static int sval = 0;
	static int count = 0;
	static int XVal = 0;
	@Override
	protected IFigure createFigure() {
		
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
		
		Figure fig=new Figure();
		scale = linkage.getScale();
		try{

			scale_distance= linkage.getScalePositions();
			scale_cm= 		linkage.getScaleCm();
			Cumdistance=    linkage.getCumlativeDistance();
			if(j==scale_distance.size()){
				j = 0;
				cumcout=0;
				count = 0;
				XVal = 0;
			}
			fig.setBackgroundColor(ColorConstants.black);
			fig.setOpaque(true);
		
			if(scale_distance.size() > j){
				
			        location = Integer.parseInt(scale_distance.get(j).toString()); 
//			        location=(int) (location*scale);
					qtlYValue = qtl_MapData.getQtlYValueForOthers();
					
					int temp=Integer.parseInt(scale_cm.get(j).toString());
					if(temp == 0)
					{
						Float cumudistance =Float.parseFloat((String) Cumdistance.get(cumcout));
//						Float cumudistance = (Float)Cumdistance.get(cumcout);
						int cum_dis =Math.round(cumudistance*scale);
						if(linkage.isCview()==true){
							try {
								if((linkage.getSelectedChromosome().equals(linkage.getChromlist().get(count))&&(linkage.isCview()==true))){
									linkage.setCview(false);
								/*if(sval == 0){
									location = 0;
									sval =1;
								}*/location = 0;
								Rectangle position1= new Rectangle(location+70, 55+35+qtlYValue+20+6,Math.round(cum_dis*2.5f) ,1);
								fig.setBounds(position1);
								}
							}catch(Exception e){
								
							}
						}else{				
						Rectangle position1= new Rectangle(location+70, 55+35+qtlYValue+20+6,cum_dis ,1);
						fig.setBounds(position1);
						}
						cumcout++;
						count++;
					}
				
			
		    String val2 = Integer.toString (Integer.parseInt(scale_cm.get(j).toString()));
		    fig.setToolTip(new Label(val2));
			}
		}catch(Exception e){
		}
		j++;
		return fig;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new ExampleFlowLayoutEditPolicy());
	}
}
