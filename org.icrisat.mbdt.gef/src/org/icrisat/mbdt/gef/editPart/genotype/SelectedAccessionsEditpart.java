package org.icrisat.mbdt.gef.editPart.genotype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.icrisat.mbdt.gef.Activator;
import org.icrisat.mbdt.gef.editPartFactory.ChromosomeEditPartFactory;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.GenotypeModel.SelectedAccessions1;
import org.icrisat.mbdt.model.sessions.Session;

public class SelectedAccessionsEditpart extends AbstractGraphicalEditPart {

	List markerPositions= new ArrayList();
	List markercumPositions= new ArrayList();
	List<String> chromosomes=new ArrayList();
	List<Integer> list_of_widths=new ArrayList();
	int width1=0,width2=0;	
	static int yvalue=80, Xvalue=0, k=0, n=0,j=0;
	int count;
	String acc1=null;
	static int prevSize= 0;
	int qtlYValue = 0;
	static HashMap<String, Object> test = new HashMap<String, Object>();
	static HashMap<String, String> genotypemarkerType = new HashMap<String, String>();
	HashMap<String, String> keyvalue=new HashMap<String, String>();
	List<String> markerName= new ArrayList();
	int linkageCount = 0;
	float scale = 0.0f;
	int width3= 0;
	LinkageData linkage;
	@Override
	protected IFigure createFigure() {
		keyvalue=null;
		Rectangle position3;
		SelectedAccessions1 selectedAcc=(SelectedAccessions1)getModel();
		acc1 = selectedAcc.getSelAccession();
		markerName=selectedAcc.getGenotypeMarkers().iterator().next().getMarkerName();
//		System.out.println("acc in SelAccessionEditPart=   "+acc1);
		list_of_widths = selectedAcc.getList_of_widths();
		
		RootModel rootModel = Session.getInstance().getRootmodel();
		
		//Code added by SWAPNA on 19th NOV 2009.....
		//Qtl_MapData qtl_MapData =Qtl_MapData.getQtl_MapData();
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
		
		try{
			keyvalue=selectedAcc.getKeyvalue();
		}catch(Exception e){
		}
		

		count= selectedAcc.getSelacc().size();
		linkageCount = Integer.parseInt(linkage.getCount());
		
		if(((k/linkageCount)+1)>selectedAcc.getSelacc().size() || (prevSize != selectedAcc.getSelacc().size())) {
			yvalue= 80;
			k=0;
		}

		Figure fig1  = new Figure();
		fig1.setLayoutManager(new FlowLayout());
		fig1.setOpaque(true);
		String colorValue=  Activator.getDefault().getPreferenceStore().getString("UnScreened");
		fig1.setBackgroundColor(ColorConstants.lightGray);
		if((linkage.isCview()==true)&&(!(linkage.getSelectedChromosome().toString().length()==0))){
		try {			
			if((linkage.getSelectedChromosome().equals(linkage.getChromlist().get(n))&&(linkage.isCview()==true))){
			
			if((selectedAcc.getSelacc().indexOf(acc1)) < (selectedAcc.getSelacc().size())){
				width3 = Integer.parseInt(selectedAcc.getList_of_widths().get(n).toString());
				n++;
			}
			position3= new Rectangle(70, yvalue+70+qtlYValue, Math.round(width3*2.5f), 15);
			fig1.setBounds(position3);
			String labelofacc=null;

			if((linkage.getAccWithLabels()).containsKey(acc1)) {
				labelofacc=((linkage.getAccWithLabels()).get(acc1)).toString();
			}
			fig1.setToolTip(new Label(labelofacc+": "+acc1.toString()));

			}else{
				n++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		}else{
			if((selectedAcc.getSelacc().indexOf(acc1)) < (selectedAcc.getSelacc().size())){
				width3 = Integer.parseInt(selectedAcc.getList_of_widths().get(n).toString());
				n++;
			}
			position3= new Rectangle(Xvalue+70, yvalue+70+qtlYValue, width3, 15);
			fig1.setBounds(position3);
			String labelofacc=null;

			//LinkageData linkage =LinkageData.getLinkageData();
			if((linkage.getAccWithLabels()).containsKey(acc1)) {
				labelofacc=((linkage.getAccWithLabels()).get(acc1)).toString();
			}
			fig1.setToolTip(new Label(labelofacc+": "+acc1.toString()));

			Xvalue=Xvalue+width3;
			Xvalue=Xvalue+30;
		}
		
		
		if(n == linkageCount) {
			n = 0;
			yvalue = yvalue + 20;
			Xvalue = 0;
		}
		k++;
		prevSize= selectedAcc.getSelacc().size();
		return fig1;
	}

	@Override
	protected void createEditPolicies() {
		
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new ExampleFlowLayoutEditPolicy());
		
	}

	protected List getModelChildren() {
		j++;
		List result=new ArrayList();
		RootModel rootModel = Session.getInstance().getRootmodel();
		
		if(keyvalue == null) {
			((SelectedAccessions1)getModel()).getGenotypeMarkers().iterator().next().setCount(count);
			((SelectedAccessions1)getModel()).getGenotypeMarkers().iterator().next().setList_of_widths(list_of_widths);
			((SelectedAccessions1)getModel()).getGenotypeMarkers().iterator().next().setType(((SelectedAccessions1)getModel()).getType());
			test.put(((SelectedAccessions1)getModel()).getSelAccession(), ((SelectedAccessions1)getModel()).getGenotypeMarkers());
			genotypemarkerType.put(((SelectedAccessions1)getModel()).getSelAccession(), ((SelectedAccessions1)getModel()).getGenotypeMarkers().iterator().next().getType());
			rootModel.setGenoObject(test);
			rootModel.setTypeValue(genotypemarkerType);
			result.addAll(((SelectedAccessions1)getModel()).getGenotypeMarkers());
		}
		try {
			if(keyvalue != null) {
				//System.out.println(" in not equal to $$$$$");
				((SelectedAccessions1)getModel()).getSortedMarkers().iterator().next().setMarkerName(markerName);
				((SelectedAccessions1)getModel()).getSortedMarkers().iterator().next().setType(((SelectedAccessions1)getModel()).getType());
				((SelectedAccessions1)getModel()).getSortedMarkers().iterator().next().setKeyvalue(keyvalue);
				((SelectedAccessions1)getModel()).getSortedMarkers().iterator().next().setAcc(acc1);	
				((SelectedAccessions1)getModel()).getSortedMarkers().iterator().next().setCount(count);
				((SelectedAccessions1)getModel()).getSortedMarkers().iterator().next().setList_of_widths(list_of_widths);
				test.put(((SelectedAccessions1)getModel()).getSelAccession(), ((SelectedAccessions1)getModel()).getSortedMarkers());
				genotypemarkerType.put(((SelectedAccessions1)getModel()).getSelAccession(), ((SelectedAccessions1)getModel()).getSortedMarkers().iterator().next().getType());				
				rootModel.setGenoObject(test);
				rootModel.setTypeValue(genotypemarkerType);
				result.addAll(((SelectedAccessions1)getModel()).getSortedMarkers());

			}
		}catch(Exception e) {
		}
//		System.out.println("SelectedAccessionsEditpart.......result......"+result);
		return result;
	}
}