package org.icrisat.mbdt.gef.editPart.nextGenerations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.icrisat.mbdt.gef.Activator;
import org.icrisat.mbdt.gef.editPart.targetGenotype.ExampleXYLayoutPolicy;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.NextGeneration.LoadNextGenDataFirstChild;
import org.icrisat.mbdt.model.NextGeneration.LoadNextGenDataSecondChild;
import org.icrisat.mbdt.model.sessions.Session;

public class LoadNextGen2EditPart extends AbstractGraphicalEditPart {

	int qtlYValue = 0;
	static int Xvalue = 0;	
	int width = 0;
	static int editPartcount = 0;
	LinkedHashSet<String> colorLabels = new LinkedHashSet<String>();
	LinkedHashMap<String, String> colorLabelHashMap = new LinkedHashMap<String, String>();
	RootModel rootModel = Session.getInstance().getRootmodel();
	LinkageData linkage;
	float scale = 0.0f;
	@Override
	protected IFigure createFigure() {
		// TODO Auto-generated method stub
		
		
		Qtl_MapData qtl_MapData;
				
		if(rootModel.getLoadFlag() == null){
			 qtl_MapData = Qtl_MapData.getQtl_MapData();
			 linkage = LinkageData.getLinkageData();
		}else{
			
			qtl_MapData = rootModel.getQtlMapData();
			linkage = rootModel.getLinkData();
		}
		scale = linkage.getScale();
		qtlYValue = qtl_MapData.getQtlYValueForOthers();
		
		Figure fig  = new Figure();
		fig.setOpaque(true);
		fig.setBackgroundColor(ColorConstants.lightGray);
		
		String linkageCountTemp = linkage.getCount();
		int linkageCount = Integer.parseInt(linkageCountTemp);
		
		if(editPartcount == linkageCount){
			editPartcount = 0;
			Xvalue = 0;
		}
		rootModel.getLoadNextGen().get(0).getListtargetAcc().get(0).getLoadNGSecondChild().get(0).setNGTLinkageGrpcount(editPartcount);
//		System.out.println(rootModel.getLoadNextGen().get(0).getListtargetAcc().get(0).getLinkageGrpWidth());
//		System.out.println("editatrt count :"+editPartcount);
		
		String strlinkagewidth = rootModel.getLoadNextGen().get(0).getListtargetAcc().get(0).getLinkageGrpWidth().get(editPartcount).toString();
		int linkagewidth = Math.round( Float.parseFloat(strlinkagewidth));
		Rectangle position = null;
		if(linkage.isCview()==true){
			try {
				if((linkage.getSelectedChromosome().equals(linkage.getChromlist().get(editPartcount)))&&(linkage.isCview())){
				position = new Rectangle(70, 50+qtlYValue+60+20,Math.round( linkagewidth*2.5f), 15);
				fig.setBounds(position);
				
				}
			} catch (Exception e) {
			}
		}else{
		 position = new Rectangle(Xvalue+70,50+qtlYValue+60+20, linkagewidth, 15);
		fig.setBounds(position);
		}
		Xvalue = Xvalue + linkagewidth;
		Xvalue = Xvalue + 30;
		editPartcount++;
		
		return fig;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub
	}
	
	@Override
	protected List getModelChildren() {
		List result = new ArrayList();
		List<String> colorLabelList = new ArrayList<String>();
		RootModel rModel = null;
		
		if(rootModel.getLoadFlag() == null){
			//RootModel rootModel = Session.getInstance().getRootmodel();
			linkage = LinkageData.getLinkageData();
		}else{
			rModel = Session.getInstance().getRootmodel();
			linkage = rootModel.getLinkData();
		}
		
		for(int c = 0; c < rModel.getLoadNextGen().get(0).getListtargetAcc().get(0).getLoadNGSecondChild().get(0).getNGTcolorValue().size(); c++){
			colorLabels.add(rModel.getLoadNextGen().get(0).getListtargetAcc().get(0).getLoadNGSecondChild().get(0).getNGTcolorValue().get(c));
		}
		
		for(Iterator iterator = colorLabels.iterator(); iterator.hasNext();){
			colorLabelList.add(iterator.next()+"");
		}
		for(int cl = 0; cl < colorLabelList.size(); cl++){
//			System.out.println("ite.."+colorLabelList.get(cl));
			if(colorLabelList.get(cl).equals("Monomorphic"))
			{
				String strcolorValue = "192,192,192";
				colorLabelHashMap.put(colorLabelList.get(cl), strcolorValue);
				
			}else{
				colorLabelHashMap.put(colorLabelList.get(cl), Activator.getDefault().getPreferenceStore().getString(colorLabelList.get(cl)));
			}
		}
		rModel.getLoadNextGen().get(0).getListtargetAcc().get(0).getLoadNGSecondChild().get(0).setNGTcolorValuesHashMap(colorLabelHashMap);
		result.addAll(rModel.getLoadNextGen().get(0).getListtargetAcc().get(0).getLoadNGSecondChild());
		return result;
	}

}
