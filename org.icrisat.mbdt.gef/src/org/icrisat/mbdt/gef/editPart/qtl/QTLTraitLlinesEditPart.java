package org.icrisat.mbdt.gef.editPart.qtl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.icrisat.mbdt.gef.editPart.targetGenotype.ExampleFlowLayoutPolicy;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.sessions.Session;

public class QTLTraitLlinesEditPart extends AbstractGraphicalEditPart {
	/**********/
//	static int yValue = 30;
	static int yValue = 40;
	List lMapMarkerDistances = new ArrayList();
	static List yValuesList = new ArrayList();
	int finalWidth = 0;
	static int countval = 1;
	static int prevyValue = 0;
	int traitCount = 0;
	int EnvironmentCount = 0;
	int checkingCount = 0;
	static int chkPositions = 1;
	static HashMap<String, Integer> hashTraitDet = new HashMap<String, Integer>();
	static HashMap<Integer, HashMap<String, Integer>> hashEnvironDet = new HashMap<Integer, HashMap<String, Integer>>();
	static HashMap<Integer, HashMap<String, Integer>> hashEnvironDet1 = new HashMap<Integer, HashMap<String, Integer>>();
	
	List<String> qtlTraitList = new ArrayList<String>();
	List<String> qtlEnviList = new ArrayList<String>();
	String prevTraitName = "";
	static int countTraitLoop = 1;
	static int qtlYvalue = 30;
	static int chkEnvi = 1;
	String currentModelele = "";
	int traitYValue = 0;
	int defaultTraitCount = 3;
	int noOftraits = 0;
	float scale = 0.0f;
	@Override
	protected IFigure createFigure() {
		// TODO Auto-generated method stub
		
		/***** 17th NOV 2009 
		 * Here in currentModelele we are getting the
		 * current Model element...
		 ******/
		currentModelele = (String) getModel();
		RootModel rootModel = Session.getInstance().getRootmodel();
		//Qtl_MapData qtl_MapData =Qtl_MapData.getQtl_MapData();
		Qtl_MapData qtl_MapData;
		LinkageData linkageData;
		
		if(rootModel.getLoadFlag() == null){
			qtl_MapData = Qtl_MapData.getQtl_MapData();
			linkageData = LinkageData.getLinkageData();
		}else{			
			qtl_MapData = rootModel.getQtlMapData();
			linkageData = rootModel.getLinkData();
		}
		scale = linkageData.getScale();		
		for(int t = 0; t < rootModel.getQtl().size(); t++){
			traitCount = rootModel.getQtl().get(t).getQtlTraitCount();
			EnvironmentCount =  rootModel.getQtl().get(t).getQtlEnvironmentCount();
			checkingCount = EnvironmentCount * traitCount;			
			qtlTraitList = rootModel.getQtl().get(t).getQtlTraitList();
			qtlEnviList = rootModel.getQtl().get(t).getQtlEnviListFromLoader();
		}		
		
		//LinkageData linkageData = LinkageData.getLinkageData();
		
		lMapMarkerDistances = linkageData.getDistances();
		String strChromeCount = linkageData.getCount();
		int chromeCount = Integer.parseInt(strChromeCount);
		
		
		for(int i = 0; i < lMapMarkerDistances.size(); i++){
			String strDistance = (String)lMapMarkerDistances.get(i);
			Float fltDistance = Float.parseFloat(strDistance)*scale;
			int width = Math.round(fltDistance);
			finalWidth = finalWidth + width;			
		}
		
		
		for(int qt = 0; qt < qtlTraitList.size(); qt++){
			if(chkPositions > checkingCount){
				yValue = 30;
				countval = 1;	
			}
			
			if((countTraitLoop == 1) || (countval > traitCount)){
				
				if(qt != 0){
					if(!(qtlTraitList.get(qt).equals(prevTraitName))){
						qtlYvalue = qtlYvalue - 10;
					}			
					prevTraitName = qtlTraitList.get(qt);
				}else{
					
					if(countval > traitCount){
						qtlYvalue = prevyValue - 40;
						
					}else{
						qtlYvalue = yValue;						
						
					}					
				}
				
				hashTraitDet.put(qtlTraitList.get(qt), qtlYvalue);				
				qtl_MapData.setHashTraitDetails(hashTraitDet);				
			}			
		}
		/**********/
		/*if(traitCount > 3){
			noOftraits = traitCount - defaultTraitCount;
			yValue = yValue + (10 * noOftraits);
		}*/
		
		
		if(countval > traitCount){
			//modified on NOV 5th 2009 in order to increase space btw different environments...
			//yValue = prevyValue - 30;
			yValue = prevyValue - 40;
			countval = 1;				
		}
		
		if(chkPositions > checkingCount){
//			yValue = 30;
			yValue = 40;
			chkPositions =1;
		}
		
		/***** 17th NOV 2009 
		 * Here we are getting the traitYValue directly from
		 * the Model.
		 ******/ 
		
		traitYValue = qtl_MapData.getHashEnvTraitDetails().get(currentModelele);
		
		Figure fig = new Figure();
		fig.setBackgroundColor(ColorConstants.gray);
		fig.setForegroundColor(ColorConstants.gray);
		fig.setBorder(new LineBorder());
		
		fig.setOpaque(true);
		/**********/
		
		
//		Rectangle position = new Rectangle(70, yValue, finalWidth + ((chromeCount - 1) * 30), 5 );
		Rectangle position = new Rectangle(70, traitYValue, finalWidth + ((chromeCount - 1) * 30), 5 );
		//System.out.println("position TraitLINES EDITPART  :"+position);
		fig.setBounds(position);
		
		countval +=1;
		chkPositions +=1;
		countTraitLoop +=1;
		prevyValue = yValue;
		yValue = yValue - 10;
		
		return fig;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new ExampleFlowLayoutPolicy());
	}
	
	@Override
	protected List getModelChildren() {
		RootModel rootModel = Session.getInstance().getRootmodel();
		List result = new ArrayList();
		for(int i = 0; i < rootModel.getQtl().size(); i++){
			for(int j=0;j<rootModel.getQtl().get(i).getQtlData().size();j++){
				if(rootModel.getQtl().get(i).getQtlData().get(j).getQtltraitName()!=null){
					result = rootModel.getQtl().get(i).getQtlData();
					
				}
			}
			/*for(int j=0;j<rootModel.getQtl().get(i).getQtlData().size();j++){
				if(rootModel.getQtl().get(i).getQtlData().get(j).getQtltraitName()!=null){

					
				}
			}*/
			
		}
		return result;
	}

}
