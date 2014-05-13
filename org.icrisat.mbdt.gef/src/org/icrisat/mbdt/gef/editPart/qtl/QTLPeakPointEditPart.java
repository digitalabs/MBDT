package org.icrisat.mbdt.gef.editPart.qtl;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.QTLModel.QTL;
import org.icrisat.mbdt.model.QTLModel.QTLPeakPoints;
import org.icrisat.mbdt.model.sessions.Session;

public class QTLPeakPointEditPart extends AbstractGraphicalEditPart {

	String strpeakPoint = "";
	String StartPt = "";
	int PeakPoint = 0;
	int xValueOfStartPt = 0;
	float xValue = 0;
	int yValueOfStartPt = 0;
	static int prevxValueOfStartPt = 0;
	static int prevxValue = 0;
	float scale = 0.0f;
	@Override
	protected IFigure createFigure() {
		
		QTLPeakPoints qtlPeakPoint = ((QTLPeakPoints)getModel());				
		RootModel rootModel = Session.getInstance().getRootmodel();
		LinkageData linkageData;
		if(rootModel.getLoadFlag() == null){
			linkageData = LinkageData.getLinkageData();
		}else{	
			linkageData = rootModel.getLinkData();
		}
		scale = linkageData.getScale();	
		Figure fig = new Figure();
		
		try {
			xValue = Float.parseFloat(qtlPeakPoint.getQtlPeakPoints()) - qtlPeakPoint.getQtlStartPtFrmEditPart();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
		}
		
		fig.setForegroundColor(ColorConstants.yellow);
		fig.setBackgroundColor(ColorConstants.green);
		fig.setOpaque(true);
		Rectangle position = null;
		
		/*if(linkageData.isCview()==true){
			try{
				for(int i1 = 0; i1<rootModel.getQtl().get(0).getQtlData().size(); i1++ ){
					if(linkageData.getSelectedChromosome().equals(rootModel.getQtl().get(0).getQtlData().get(i1).getQtlChromNames())){
						position = new Rectangle(Math.round(Integer.parseInt(qtlPeakPoint.getQtlPeakPoints())*scale),qtlPeakPoint.getQtlYValueForPeak(),5,5);	
						
					}
				}
				
			}catch(Exception e){
				
			}
			}else{
				position = new Rectangle(qtlPeakPoint.getQtlStartPtForPeak()+Math.round(xValue*scale),qtlPeakPoint.getQtlYValueForPeak(),2,5);
			}*/
		 
		position = new Rectangle(qtlPeakPoint.getQtlStartPtForPeak()+Math.round(xValue*scale),qtlPeakPoint.getQtlYValueForPeak(),2,5);
		
		fig.setToolTip(new Label("Peak Point :"+qtlPeakPoint.getQtlPeakPoints()));
		fig.setBounds(position);	
		return fig;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub
	}
}
