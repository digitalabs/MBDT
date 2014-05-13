package org.icrisat.mbdt.gef.editPart.nextGenerations;

import java.util.ArrayList;
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
import org.icrisat.mbdt.gef.editPart.targetGenotype.ExampleXYLayoutPolicy;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.Chromosome.Chromosome;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.NextGeneration.LoadNextGenDataFirstChild;
import org.icrisat.mbdt.model.sessions.Session;
import org.icrisat.mbdt.model.sessions.SessionChromosome;

public class LoadNextGen1EditPart extends AbstractGraphicalEditPart {
	
	List lMapMarkerDistances = new ArrayList();
	List<Integer> linkageGrpWidth= new ArrayList<Integer>();
	int qtlYValue = 0;
	int finalWidth = 0;
	int test = 0;
	RootModel rootModel = RootModel.getRootModel();
	LinkageData linkage;
	float scale = 0.0f;
	@Override
	protected IFigure createFigure() {
		// TODO Auto-generated method stub
				
		Qtl_MapData qtl_MapData;
		RootModel rModel = null;
		Chromosome chrom = SessionChromosome.getInstance().getChromosome();
		if(rootModel.getLoadFlag() == null){
			 qtl_MapData = Qtl_MapData.getQtl_MapData();
			 linkage = LinkageData.getLinkageData();
		}else{
			rModel = Session.getInstance().getRootmodel();
			qtl_MapData = rootModel.getQtlMapData();
			linkage = rootModel.getLinkData();
		}
		scale = linkage.getScale();
		Figure fig = new Figure();
		Label lblAcc = new Label();
		Label lblType = new Label();
				
		fig.setBackgroundColor(ColorConstants.white);
//		fig.setOpaque(true);
		
//		lblAcc.setText(rModel.getLoadNextGen().get(0).getTargetAcc());
		lblAcc.setText("Target");
		Font classFont= new Font(null, "Arial", 8, SWT.BOLD);
		lblAcc.setFont(classFont);
		((IFigure) lblAcc).setForegroundColor(ColorConstants.red);
		((IFigure) lblAcc).setOpaque(true);
		fig.add((IFigure) lblAcc);
		
		
		
		qtlYValue = qtl_MapData.getQtlYValueForOthers();
		lMapMarkerDistances = linkage.getDistances();
		String strChromeCount = linkage.getCount();
		int chromeCount = Integer.parseInt(strChromeCount);
		
		for(int i = 0; i < lMapMarkerDistances.size(); i++){
			String strDistance = (String)lMapMarkerDistances.get(i);
			Float fltDistance = Float.parseFloat(strDistance);
			int width = Math.round(fltDistance);
			finalWidth = finalWidth + width;
			test = test + width;
//			if(((i!= 0 && ((((lMapMarkerDistances.get(i)).equals("0"))) || ((lMapMarkerDistances.get(i)).equals("0.0")))) || (i == lMapMarkerDistances.size()-1 && ((!(lMapMarkerDistances.get(i)).equals("0"))))) ) {
//				if((i!= 0 &&((lMapMarkerDistances.get(i)).equals("0"))&&(!linkage.getChromName().get(i).equals(linkage.getChromName().get(i-1))))||(i == lMapMarkerDistances.size()-1)){
					if(i!= 0 && (((lMapMarkerDistances.get(i)).equals("0") || ((lMapMarkerDistances.get(i)).equals("0.0"))) &&(!linkage.getChromName().get(i).equals(linkage.getChromName().get(i-1))))||(i == lMapMarkerDistances.size()-1 && ((!((lMapMarkerDistances.get(i)).equals("0")||(lMapMarkerDistances.get(i)).equals("0.0")))))){
					int hj1 = test;
//				linkageGrpWidth.add(hj1);
				if(i==lMapMarkerDistances.size()-1){
				linkageGrpWidth.add(Math.round(Float.parseFloat((String)linkage.getMarkerPositions().get(i))*scale));
				}else{
					linkageGrpWidth.add(Math.round(Float.parseFloat((String)linkage.getMarkerPositions().get(i-1))*scale));
				}
				
				test = 0;
			}
//			}
		}
		float dis=0;
		for(int d=0;d<linkage.getCumlativeDistance().size();d++){
			dis = dis + Float.parseFloat(linkage.getCumlativeDistance().get(d).toString());
		}
		((LoadNextGenDataFirstChild)getModel()).setLinkageGrpWidth(linkageGrpWidth);
		Rectangle position = null;
		try {
			if(linkage.isCview()){
				linkage.setCview(false);
				 position = new Rectangle(0, 110+qtlYValue+20, Math.round(600 * 2.5f), 10);
			}else{
				 position = new Rectangle(0, 110+qtlYValue+20, Math.round(dis*scale) + ((chromeCount - 1) * 30)+70+14, 10);
			}
		} catch (Exception e) {
		}
		fig.setBounds(position);
		Rectangle posLabel= new Rectangle(5, 110+qtlYValue+20, 40, 10);
		lblAcc.setBounds(posLabel);
		
		return fig;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new ExampleXYLayoutPolicy());
	}
	
	@Override
	protected List getModelChildren() {
		// TODO Auto-generated method stub
		List result = new ArrayList();
		RootModel rModel = null;
		
		if(rootModel.getLoadFlag() == null){
			//RootModel rootModel = Session.getInstance().getRootmodel();
			linkage = LinkageData.getLinkageData();
		}else{
			rModel = Session.getInstance().getRootmodel();
			linkage = rootModel.getLinkData();
		}
		String linkageCountTemp = linkage.getCount();
		int linkageCount = Integer.parseInt(linkageCountTemp);
		
		for(int j = 0; j < linkageCount; j++) {
			result.add(((LoadNextGenDataFirstChild)getModel()).getTargetAcc());
		}
		
		return result;
	}

}
