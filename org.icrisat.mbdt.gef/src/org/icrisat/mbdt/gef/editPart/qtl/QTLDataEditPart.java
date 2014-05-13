package org.icrisat.mbdt.gef.editPart.qtl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ToolTipManager;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.swt.graphics.Color;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.QTLModel.QTLData;
import org.icrisat.mbdt.model.sessions.Session;

public class QTLDataEditPart extends AbstractGraphicalEditPart {
	
	static int countval=1;
	//static int xValue = 10;
	static int yValue = 0;
	int xx = 0;
	int finalValue =0;
	int missingValue = 0;
	static int prevEndpoint = 0;
	static int prevStartpoint = 0;
	static int prevWidth = 0;
	static int test = 0;
	static int testCount = 0;
	static int prevXvalue = 0;
	static int countgroup = 0;
	static int prevLinkData = 0;
	static int missingPrevlinkData;
	static int missingPrevlinkData1;
	static int missingPrevlinkDataTot;
	String linkDataValue = "";
	String prevMissinglinkData = "";
	String prevMissinglinkData1 = "";	
	int linkInt;
	List<Integer> qtlGrayLinePos = new ArrayList<Integer>();
	
	int environmentCount= 0;
	int traitCount = 0;
	List<String> environmentList = new ArrayList<String>();
	List<String> traitList = new ArrayList<String>();
	float modWidth = 0;float xValue;float width = 0;
	private Rectangle position;
	static int suppCount = 0;
	static String prevEnvironment = "";
	static Color testColor;
	float scale = 0.0f;
	static HashMap<String, HashMap<String, Integer>> hashEnviDet = new HashMap<String, HashMap<String, Integer>> ();
	
	
	@Override
	protected IFigure createFigure() {
		
		QTLData qtlData = ((QTLData)getModel());
		
		RootModel rootModel = Session.getInstance().getRootmodel();
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
		Figure fig = new Figure();
		try{
		if(qtlData.getQtlStartPt() != null){

			xValue = Float.parseFloat(qtlData.getQtlStartPt())*scale;
			width =  Float.parseFloat(qtlData.getQtlEndPt())*scale;
			int prevGrpCount = qtlData.getPrevGroupCount();
			int currGrpCount = qtlData.getGroupCount();
			modWidth = width - xValue;
			qtlData.setQtlPeakPointFrmEditPart(qtlData.getQtlPeakPoint());
					
			
			/***** 17th NOV 2009 
			 * Here we are calculating the xx and yValue for the Qtl Lines.
			 * For xx and yValue we are getting the values from the Model. 
			 * 
			 ******/
			if(prevGrpCount != 0){				
				
//				System.out.println("linkInt****** :"+qtl_MapData.getHashLinkDetails()+"  qtlData :"+qtlData.getQtlChromNames());
				if(prevGrpCount != currGrpCount){
					xx = qtl_MapData.getHashLinkDetails().get(qtlData.getQtlChromNames());
					
					yValue = qtl_MapData.getHashEnvTraitDetails().get(qtlData.getQtltraitName()+"_"+qtlData.getQtlEnviName());
					
				}else{
					yValue = qtl_MapData.getHashEnvTraitDetails().get(qtlData.getQtltraitName()+"_"+qtlData.getQtlEnviName());
				}

				if(prevGrpCount != 1){
					xx = qtl_MapData.getHashLinkDetails().get(qtlData.getQtlChromNames());
					
					yValue = qtl_MapData.getHashEnvTraitDetails().get(qtlData.getQtltraitName()+"_"+qtlData.getQtlEnviName());
					
					
				}				
			}else{
				yValue = qtl_MapData.getHashEnvTraitDetails().get(qtlData.getQtltraitName()+"_"+qtlData.getQtlEnviName());
				
				
			}
		
			if(countval != qtlData.getGroupCount()){
				countval = 1;
				prevLinkData = 0;
				prevXvalue = 0;
				countgroup = 0;
				//missingPrevlinkData = 0;
				//missingPrevlinkDataTot = 0;
				//missingPrevlinkData1 = 0;
				//prevGetQtlStartPt.clear();
				suppCount = 0;
				prevEnvironment = "";
			}
					
		
			fig.setForegroundColor(new Color(null,255,86,62));
			fig.setBackgroundColor(new Color(null,255,86,62));
			try{
				if(((qtlData.getSelectedqtl().toString().equals("foreqtl")))||(qtl_MapData.getQtlas().equals("fore"))){
					fig.setForegroundColor(new Color(null,198,24,0));
					fig.setBackgroundColor(new Color(null,198,24,0));
					
				}
				if((qtlData.getSelectedqtl().toString().equals("backqtl"))||(qtlData.getSelectedqtl().toString()==null)){
					if((qtl_MapData.getQtlas().equals("back"))){
						fig.setForegroundColor(new Color(null,255,86,62));
						fig.setBackgroundColor(new Color(null,255,86,62));
					}
				}
				
			}catch(Exception e){
			}
			fig.setOpaque(true);
			if(linkageData.isCview()==true){
			if((linkageData.isCview()==true)&&(linkageData.getSelectedChromosome().equals(qtlData.getQtlChromNames()))){
				try {
					position = new Rectangle(Math.round((70+Math.round(xValue)*2.5f)),yValue,Math.round(modWidth*2.5f),5);
					qtlData.getQtlPeakPoints().get(0).setQtlStartPtForPeak(Math.round((70+Math.round(xValue)*2.5f)));
					qtlData.setQtlStartPtForPeak(Math.round((70+Math.round(xValue)*2.5f)));
				}catch(Exception e){
					
				}
			}
			}else{
				position = new Rectangle(70+Math.round(xValue+xx),yValue,Math.round(modWidth),5);
				qtlData.getQtlPeakPoints().get(0).setQtlStartPtForPeak(70+Math.round(xValue+xx));
				qtlData.setQtlStartPtForPeak(70+Math.round(xValue+xx));
			}
			fig.setBounds(position);
			fig.setToolTip(new Label(" SP :"+qtlData.getQtlStartPt()+"; EP :"+qtlData.getQtlEndPt()+"; TN :"+qtlData.getQtltraitName()+"; LOD :"+qtlData.getQtlLodsqr()+"; R^2 :"+qtlData.getQtlRsqr()+"; AE :"+qtlData.getQtlAddEffects()));
			
			prevXvalue = xx;
			prevEnvironment = qtlData.getQtlEnviName();
			qtlData.getQtlPeakPoints().get(0).setQtlYValueForPeak(yValue);
			qtlData.setQtlYValueForPeak(yValue);		
//			qtlData.getQtlPeakPoints().get(0).setQtlStartPtForPeak(70+Math.round(xValue+xx));
//			qtlData.setQtlStartPtForPeak(70+Math.round(xValue+xx));
			qtlData.getQtlPeakPoints().get(0).setQtlStartPtFrmEditPart(Integer.parseInt(qtlData.getQtlStartPt()));
			yValue = yValue - 10;
		}
	}catch(Exception e){
		
	}
		int dismissDelay = ToolTipManager.sharedInstance().getDismissDelay();

	    // Keep the tool tip showing
	    dismissDelay = 3000;
	    ToolTipManager.sharedInstance().setDismissDelay(dismissDelay);
		return fig;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected List getModelChildren(){
		
		List<Object> result = new ArrayList<Object>();
		Object temp = "";
				
		// For Peak Points............
		
		if(((QTLData)getModel()).getQtlPeakPoints().size()!= 0){
			
			for(int qt = 0; qt < ((QTLData)getModel()).getQtlPeakPoints().size(); qt++){
				
				if(!temp.equals(((QTLData)getModel()).getQtlPeakPoints().get(qt))){
					result.add(((QTLData)getModel()).getQtlPeakPoints().get(qt));
				}
				temp = ((QTLData)getModel()).getQtlPeakPoints().get(qt);
			}
		}
		
		return result;
	}	
}
