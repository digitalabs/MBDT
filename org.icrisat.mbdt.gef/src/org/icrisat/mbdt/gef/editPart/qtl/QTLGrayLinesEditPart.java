package org.icrisat.mbdt.gef.editPart.qtl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.sessions.Session;

public class QTLGrayLinesEditPart extends AbstractGraphicalEditPart {

	static int xValue = 0;	
	static int lblyValue = 0;
	static int countval = 1;
	int finalWidth = 0;
	List lMapMarkerDistances = new ArrayList();
	static List yValuesList = new ArrayList();	
	static List<String> TraitCount = new ArrayList<String>();
	int EnvironmentCount = 0;	
	static int traitYValue = 20;
	List qtlTraitList = new ArrayList<String>();
	String currentModelele = "";
	int traitLblYValue = 0;
	int TraitNameCount = 0;	
	float scale = 0.0f;
	/***** 17th NOV 2009 
	 * At first the yValue is set to zero.
	 * but it is changed to 10 in order for increasing the
	 * space between different environments.
	 ******/	
	//static int yValue = 0;
	static int yValue = 10;
	int rectHeight = 40;
	int defaultTraitCount = 3;
	int noOftraits = 0;
	int noOftraits1 = 0;
	int countTraitYvalue = 0;
	
	@Override
	protected IFigure createFigure() {
		
		currentModelele = (String) getModel();
		RootModel rootModel = Session.getInstance().getRootmodel();
		//Qtl_MapData qtl_MapData = Qtl_MapData.getQtl_MapData();
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
			EnvironmentCount = rootModel.getQtl().get(t).getQtlEnvironmentCount();
			qtlTraitList = rootModel.getQtl().get(t).getQtlTraitListFromLoader();
			TraitNameCount = rootModel.getQtl().get(t).getQtlTraitCount();
		}
		
		//LinkageData linkageData = LinkageData.getLinkageData();
		
		lMapMarkerDistances = linkageData.getDistances();
		String strChromeCount = linkageData.getCount();
		int chromeCount = Integer.parseInt(strChromeCount);
		
		
		for(int i = 0; i < lMapMarkerDistances.size(); i++){
			String strDistance = (String)lMapMarkerDistances.get(i);
			Float fltDistance = Float.parseFloat(strDistance)*scale;
			int width = Math.round(fltDistance);
			if(linkageData.isCview()==true){
				try {
					if((linkageData.getSelectedChromosome().equals(qtl_MapData.getChromosome().get(i))&&(linkageData.isCview()==true))){
						finalWidth = finalWidth + width;	
					}	
				}catch(Exception e){
					
				}
			}else{
				finalWidth = finalWidth + width;
			}
		}
		if(countval > EnvironmentCount){
			countval = 1;
			yValue = 10;
			countTraitYvalue = 0;
		}
		
		
		Font classFont = new Font(null, "verdana",5, SWT.BOLD);	
		
		
		Figure fig = new Figure();
//		fig.setBackgroundColor(ColorConstants.red);
		fig.setBorder(new LineBorder());
		
		fig.setOpaque(true);
		
		/***** 17th NOV 2009 
		 * If No.of traits is > 3 then we are calculating the YValue 
		 * for the trait label.
		 *  
		 ******/		
		if(qtlTraitList.size() > 3){
			noOftraits1 = qtlTraitList.size() - defaultTraitCount;
			traitYValue = traitYValue + (10 * noOftraits1);
		}
		
		//dynamically creating labels for Traits.....
		for(int tt = 0; tt < qtlTraitList.size(); tt++){
			/*lbltt--->'lbl' is the name of the label and 'tt' is for dynamically 
			 *	creating the labels depending on Environments...
			 */
			
			Label lbltt = new Label();
			lbltt.setFont(classFont);
			
			lbltt.setText((String)qtlTraitList.get(tt));
//			((IFigure) lbltt).setForegroundColor(ColorConstants.black);
//			((IFigure) lbltt).setOpaque(true);
			fig.add((IFigure) lbltt);
			
			//traitLblYValue = qtl_MapData.getHashEnvTraitDetails().get(qtlTraitList.get(tt)+"_"+currentModelele.substring(4));
//			if((countTraitYvalue == 0) && (traitYValue == 0)){
			if((countTraitYvalue == 0) && (qtlTraitList.size() < 3)){
				traitYValue = 20;
			}
			
			Rectangle posLabel= new Rectangle(-5, traitYValue,40, 20);
			
//			System.out.println("TRAIT LABEL :"+posLabel);
			
			lbltt.setBounds(posLabel);
			if(traitYValue == 0){
				traitYValue = 20;
			}else{
				traitYValue -=10;
			}
			countTraitYvalue ++;
		}
		
		/*fig.add((IFigure) lbl);
		fig.add((IFigure) lbl1);
		fig.add((IFigure) lbl2);*/
//		qtl.setQtlGrayLineYvalues(yValue);
		yValuesList.add(yValue);
		
		rootModel.getQtl().get(0).setQtlGrayLineYvalues(yValuesList);
//		Rectangle position = new Rectangle(70, yValue, finalWidth + ((chromeCount - 1) * 28), 2 );60,45,25
//		System.out.println("yValue   before:"+yValue);
		
//		Rectangle position = new Rectangle(40, yValue, finalWidth + ((chromeCount - 1) * 30)+50, 45 );
		
		
		
		
		/***** 17th NOV 2009 
		 * If No.of traits is > 3 then we are dynamically increasing the
		 * height of the rectangle.
		 *  
		 ******/
		
		if(qtlTraitList.size() > 3){
			noOftraits = qtlTraitList.size() - defaultTraitCount;
			rectHeight = rectHeight + (10 * noOftraits);			
		}
		/***** 17th NOV 2009 
		 * If No.of traits is > 3 then we are dynamically increasing the
		 * height of the rectangle.
		 *  
		 ******/
		/*if((qtlTraitList.size() > 3) && (EnvironmentCount > 1)){
			yValue = yValue + 10;
		}*/
		
		if(countval == 1){
			if((qtlTraitList.size() > 3) && (EnvironmentCount > 1)){
				yValue = qtl_MapData.getQtlYValue();
				
			}else{
				/***** 18th NOV 2009 
				 * If No.of traits is < 3 and EnvironmentCount >1 then we 
				 * are getting the yValue from Model.
				 *  
				 ******/
				if(EnvironmentCount > 1){
					yValue = qtl_MapData.getQtlYValue();
				}else{
					yValue = yValue;
				}
				
			}
		}
		Rectangle position = null;
		if(linkageData.isCview()==true){
			try {
				if(linkageData.isCview()==true){
					linkageData.setCview(false);
					position = new Rectangle(40, yValue, Math.round((finalWidth+10)* 2.5f), rectHeight );
				}
			}catch(Exception e){
				
			}
		}else{
		 position = new Rectangle(40, yValue, finalWidth + ((chromeCount - 1) * 30)+50, rectHeight );
		}
		//System.out.println("position GRAYLINES EDIT PART :"+position);
		fig.setBounds(position);
		countval +=1;
		
//		yValue -= 12;
//		yValue -= 6;
		
		//modified on NOV 5th 2009 in order to increase space btw different environments...
		//yValue -= 50;
		
		/***** 17th NOV 2009 
		 * If No.of traits is > 3 and EnvironmentCount > 1
		 * then we are calculating the YValue.
		 * where noOftraits = qtlTraitList.size() - defaultTraitCount.  
		 ******/   
		if((qtlTraitList.size() > 3) && (EnvironmentCount > 1)){
			yValue = yValue - (50 + 10 * noOftraits);
		}else{
			yValue -= 60;
		}
		
		
		lblyValue -=70;
		return fig;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub

	}
	@Override
	protected List getModelChildren() {		
		RootModel rootModel = Session.getInstance().getRootmodel();
		List<Object> result = new ArrayList<Object>();
		List<String> traitList = new ArrayList<String>();
		
		for(int i = 0; i < rootModel.getQtl().size(); i++){
			for(int j=0;j<rootModel.getQtl().get(i).getQtlData().size();j++){
				if(rootModel.getQtl().get(i).getQtlData().get(j).getQtltraitName()!=null){
					
//					System.out.println(j);
					if(!TraitCount.contains(rootModel.getQtl().get(i).getQtlData().get(j).getQtltraitName())){
						TraitCount.add(rootModel.getQtl().get(i).getQtlData().get(j).getQtltraitName());
//						System.out.println(TraitCount);
					}
				}
			}
			rootModel.getQtl().get(i).setQtlTraitCount(TraitCount.size());
			
		}
		
		//System.out.println("currentModelele  ::"+currentModelele);
		for(int i = 0; i < TraitCount.size(); i++){
			traitList.add(TraitCount.get(i));
			result.add(TraitCount.get(i)+"_"+currentModelele.substring(4));
			
			/***** Commented on 17th NOV 2009 in order to send model as traitname_environmentNo. i.e,FT_1,BDW_1 etc...  *****/
//			result.add(TraitCount.get(i));
			
		}
		for(int t = 0; t < rootModel.getQtl().size(); t++){
			rootModel.getQtl().get(t).setQtlTraitList(traitList);			
		}
		
		return result;
	}

}
