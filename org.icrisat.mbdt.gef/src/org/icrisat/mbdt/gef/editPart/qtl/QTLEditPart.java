package org.icrisat.mbdt.gef.editPart.qtl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.QTLModel.QTL;
import org.icrisat.mbdt.model.sessions.Session;

public class QTLEditPart extends AbstractGraphicalEditPart {
	
	static List<String> EnvironmentCount = new ArrayList<String>();
	static int yValue = 10;
	static int traitYValue = 20;
	String prevTraitName = "";	
	HashMap<String, Integer> hashTraitDet = new HashMap<String, Integer>();
	int prevYValue = 0;
	int prevEnvValue = 0;
	int defaultTraitCount = 3;
	int defaultEnviCount = 1;
	int noOftraits = 0;
	int noOftraits1 = 0;
	int noOfEnvironments = 0;
	int noOftraitsForLbl = 0;	
	int lblHeight = 0;
	
	/***** 17th NOV 2009 
	 * At first the qtlYvalue is set to 30.
	 * but later it is changed to 40 in order to calculate the
	 * yValue for different Traits and Environments...
	 * 
	 ******/
	//static int qtlYvalue = 30;
	int qtlYvalue = 40;
	
	@Override
	protected IFigure createFigure() {		
		//This layer supports ZOOM.... 
		FreeformLayer lyr = new FreeformLayer();
		String qtlinfo = "";
		
		//for Editing In GEF...XYLayout is used if we want to do drag and drop.....
		lyr.setLayoutManager(new XYLayout());
		
		//Qtl_MapData qtl_MapData =Qtl_MapData.getQtl_MapData();
		RootModel rootModel = Session.getInstance().getRootmodel();
		Qtl_MapData qtl_MapData;
		
		if(rootModel.getLoadFlag() == null){
			qtl_MapData = Qtl_MapData.getQtl_MapData();
		}else{			
			qtl_MapData = rootModel.getQtlMapData();
		}
		
		List qtlEnviCount = ((QTL)getModel()).getQtlEnviListFromLoader();
		List qtlTraitCount = ((QTL)getModel()).getQtlTraitListFromLoader();
		Font classFont = new Font(null, "verdana",8, SWT.BOLD);
		Font classFont1 = new Font(null, "verdana",5, SWT.BOLD);
		
		if(qtlTraitCount.size() > 3){
			noOftraitsForLbl = qtlTraitCount.size() - defaultTraitCount;
//			lblHeight = 40 + (10 * noOftraitsForLbl);
			lblHeight = 10;
			if(qtlEnviCount.size() > 1){
				yValue = qtl_MapData.getQtlYValue();
			}
		}else{
			lblHeight = 50;
		}
		
		//dynamically creating labels for environments.....
		for(int i = 0; i < qtlEnviCount.size(); i++){
			/*lbli--->'lbl' is the name of the label and 'i' is for dynamically 
			 *	creating the labels depending on Environments...
			*/
			Label lbli = new Label();
			lbli.setFont(classFont);
			String setLblText = "E"+qtlEnviCount.get(i);
			lbli.setText(setLblText);
			((IFigure) lbli).setForegroundColor(ColorConstants.black);
			((IFigure) lbli).setOpaque(true);
			lyr.add((IFigure) lbli);
			Rectangle posLabel= new Rectangle(5, yValue, 30, 50);
			
			lbli.setBounds(posLabel);
						
			if((qtlTraitCount.size() > 3) && (qtlEnviCount.size() > 1)){
				/*** In order to increase the gap for the last environment
				 * i have changed the value to 60.
				***/
				yValue = yValue - (60 + 10 * noOftraitsForLbl);
			}else{
				yValue -=60;
			}
		}
		yValue = 0;
		return lyr;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub

	}

	@Override
	protected List getModelChildren() {
		/** From here it again goes to the GraphicalEditPartFactory and check if it
		 * is ENIV then it goes to QTLGrayLinesEditPart where it returns the
		 *  Rectangular boxes.... 
		**/ 
		//RootModel rootModel = Session.getInstance().getRootmodel();
		List<String> result=new ArrayList<String>();
		List<String> environmentList = new ArrayList<String>();
		List<String> traitList = new ArrayList<String>();
		RootModel rootModel = Session.getInstance().getRootmodel();
		Qtl_MapData qtl_MapData;
		
		if(rootModel.getLoadFlag() == null){
			qtl_MapData = Qtl_MapData.getQtl_MapData();
		}else{			
			qtl_MapData = rootModel.getQtlMapData();
		}
		
		for(int i = 0; i < rootModel.getQtl().size(); i++){
			for(int j=0;j<rootModel.getQtl().get(i).getQtlData().size();j++){
				if(rootModel.getQtl().get(i).getQtlData().get(j).getQtlEnviName()!=null){
					if(!EnvironmentCount.contains(rootModel.getQtl().get(i).getQtlData().get(j).getQtlEnviName())){
						EnvironmentCount.add(rootModel.getQtl().get(i).getQtlData().get(j).getQtlEnviName());
					}
				}
			}
			rootModel.getQtl().get(i).setQtlEnvironmentCount(EnvironmentCount.size());
			
			traitList = rootModel.getQtl().get(i).getQtlTraitListFromLoader();
		}
		
		/***** 17th NOV 2009 
		 * Here we are calculating the yValues for each trait and placing
		 * the same in hashTraitDet HashMap and setting the same to the Model.
		 * 
		 * i.e, {FT_1 = 40, BDW_1 = 50} etc...
		 ******/
		for(int i = 1; i <= EnvironmentCount.size(); i++){
			
			/***** 17th NOV 2009 
			 * If No.of traits is > 3 then we are calculating the qtlYvalue.
			 * where noOftraits = traitList.size() - defaultTraitCount.  
			 ******/
			if(traitList.size() > 3){
				noOftraits = traitList.size() - defaultTraitCount;
				noOfEnvironments = EnvironmentCount.size() - defaultEnviCount; 
				
				if((traitList.size() > 3) && (EnvironmentCount.size() > 1)){
					qtlYvalue = (qtlYvalue + (20 * noOftraits * noOfEnvironments)) - ((10 * noOftraits) * (noOfEnvironments - 1 ));
					
				}else{
					qtlYvalue = qtlYvalue + (10 * noOftraits);
				}				
			}else{
				if(EnvironmentCount.size() > 1){
					qtlYvalue = qtlYvalue + 10;
				}
			}
			
			for(int k = 0; k < traitList.size(); k++){
				if(k != 0){
					if(!(traitList.get(k).equals(prevTraitName))){
						
						qtlYvalue = qtlYvalue - 10;						
					}			
				}else{
					//System.out.println(i+"::::PPPPP:::"+prevEnvValue+":::::PPPPP:::::"+qtlYvalue+"::::PPPPP::::"+prevYValue);
					if((i!= 1)&&(i != prevEnvValue)){
						
						/***** 17th NOV 2009 
						 * If No.of traits is > 3 and EnvironmentCount.size() > 1
						 * then we are calculating the qtlYvalue.
						 * where qtlYvalue = prevYValue - 30.  
						 ******/
						
						if((traitList.size() > 3) && (EnvironmentCount.size() > 1)){
							
							qtlYvalue = prevYValue - 30;
						}else{
							//System.out.println("2 COMING HERE :************************"+prevYValue);
							if((EnvironmentCount.size() > 1) && (traitList.size() < 3)){
								if(traitList.size() == 2){
									qtlYvalue = prevYValue - 50;
								}else{
									qtlYvalue = prevYValue - 60;
								}
								
							}else{
								qtlYvalue = prevYValue - 40;
							}
						}
						
					}else{
						qtlYvalue = qtlYvalue;						
						
					}					
				}
								
				hashTraitDet.put(traitList.get(k)+"_"+i, qtlYvalue);
				prevYValue = qtlYvalue;				
				qtl_MapData.setHashEnvTraitDetails(hashTraitDet);
				prevTraitName = traitList.get(k);
			}			
			
			environmentList.add("E"+i);
			result.add("ENVI"+i);
			prevEnvValue = i;
		}
		
		for(int e = 0; e < rootModel.getQtl().size(); e++){
			rootModel.getQtl().get(e).setQtlEnvironmentList(environmentList);
		}
		
		return result;
	}	
}
