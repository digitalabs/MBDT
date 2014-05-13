package org.icrisat.mbdt.gef.editPart.genotype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.icrisat.mbdt.gef.editPart.targetGenotype.ExampleXYLayoutPolicy;
import org.icrisat.mbdt.gef.editPartFactory.ChromosomeEditPartFactory;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.GenotypeModel.SelectedAccessions;
import org.icrisat.mbdt.model.sessions.Session;

public class AccessionsEditPart extends AbstractGraphicalEditPart {

	List<String> markerPositions= new ArrayList<String>();
	List<String> markercumPositions= new ArrayList<String>();
	List<String> chromosomes= new ArrayList<String>();
	List<Integer> list_of_widths= new ArrayList<Integer>();
	int width1= 0,width2= 0;	
	static int yvalue= 80, Xvalue= 0, k= 0;
	String labelText= "_";
	//LinkageData linkageData= LinkageData.getLinkageData();
	static int prevSize= 0;
	int qtlYValue = 0;
	int nextGenlbl1Width = 0;
	float scale = 0.0f;
	RootModel rootModel = Session.getInstance().getRootmodel();
	@Override
	protected IFigure createFigure() {
		SelectedAccessions selectedAcc = (SelectedAccessions)getModel();
		
		String acc1 = selectedAcc.getSelAccession();
		//Code added by SWAPNA on 19th NOV 2009.....
		Qtl_MapData qtl_MapData;
		LinkageData linkage;
		
		if(rootModel.getLoadFlag() == null){
			qtl_MapData = Qtl_MapData.getQtl_MapData();
			linkage = LinkageData.getLinkageData();
		}else{
			qtl_MapData = rootModel.getQtlMapData();
			linkage = rootModel.getLinkData();
		}
		scale = linkage.getScale();
		Figure fig = new Figure();
		fig.setLayoutManager(new XYLayout());
		Label lbl = new Label();
		Label lbl1 = new Label();
		
		String labelofacc = null;
		if((linkage.getAccWithLabels()).containsKey(acc1)) {
			labelofacc = ((linkage.getAccWithLabels()).get(acc1)).toString();
		}
		fig.setToolTip(new Label(labelofacc+": "+acc1));
		fig.getToolTip().isShowing();
		if(labelofacc.equals("Donor")) {
			labelText= "D";
		}
		else if(labelofacc.equals("Recurrent")){
			labelText= "R";
		}
		if(Session.getInstance().getRootmodel().getLoadNextGen().isEmpty()){
			lbl.setText("("+labelText+")");
			Font classFont= new Font(null, "Arial", 8, SWT.BOLD);
			lbl.setFont(classFont);
			((IFigure) lbl).setForegroundColor(ColorConstants.darkGray);
			((IFigure) lbl).setOpaque(true);
			fig.add((IFigure) lbl);
		}else{
			nextGenlbl1Width = 20;
		}
		lbl1.setText(acc1);
		Font classFont1 = null;
		if(acc1.length()<=6){
			 classFont1= new Font(null, "Arial", 8, SWT.BOLD);
		}else if((acc1.length()<=9)&&(acc1.length()>6)){
			 classFont1= new Font(null, "Arial", 6, SWT.BOLD);	
		}else if((acc1.length()>9)){
			 classFont1= new Font(null, "Arial", 4, SWT.BOLD);	
		}
		
		lbl1.setFont(classFont1);
		((IFigure) lbl1).setForegroundColor(ColorConstants.blue);
		((IFigure) lbl1).setOpaque(true);
		fig.add((IFigure) lbl1);
		
		try {
			String countTemp = linkage.getCount();
			List<String> chrName = linkage.getChromName();
			int count = Integer.parseInt(countTemp);
			markercumPositions = linkage.getMarkerPositions();
			chromosomes = linkage.getChromName();
			markerPositions = linkage.getDistances();
			
			for(int i = 0; i < (markercumPositions.size()); i++){
				String dist= (markerPositions.get(i)).toString();
				Float Distance1= Float.parseFloat(dist);
				int width= Math.round(Distance1);
				width1= width1+width;
				width2= width2+width;

				if(i!= 0 && (((markercumPositions.get(i)).equals("0") || ((markercumPositions.get(i)).equals("0.0"))) &&(!chrName.get(i).equals(chrName.get(i-1))))||(i == markercumPositions.size()-1 && ((!((markercumPositions.get(i)).equals("0")||(markercumPositions.get(i)).equals("0.0")))))){
					int hj1= width2;
//					list_of_widths.add(hj1);
					if(i==markercumPositions.size()-1){
						list_of_widths.add(Math.round(Float.parseFloat(markercumPositions.get(i))*scale));
					}else{
					list_of_widths.add(Math.round(Float.parseFloat(markercumPositions.get(i-1))*scale));
					}
//					System.out.println(hj1+"...."+markercumPositions.get(i-1));
					width2= 0;
				}
			}
			if((k+1)> selectedAcc.getSelacc().size() || (prevSize != selectedAcc.getSelacc().size())){
				k=0;
				yvalue=80;
			}
//			fig.setBackgroundColor(ColorConstants.white);
			qtlYValue = qtl_MapData.getQtlYValueForOthers();
			Rectangle position = null;
			if((linkage.isCview()==true)&&(!(linkage.getSelectedChromosome().toString().length()==0))){
				try {
					linkage.setCview(false);
					int d = Math.round(Float.parseFloat(linkage.getChrmno_cumdis().get(linkage.getSelectedChromosome()).toString()));
					 position= new Rectangle(0, yvalue+70+qtlYValue, Math.round(d*scale*2.5f)+70, 10);
//					fig.setBackgroundColor(ColorConstants.white);
					fig.setBounds(position);
				}catch(Exception e){
				}
			}else{
				float dis=0;
				for(int d=0;d<linkage.getCumlativeDistance().size();d++){
					dis = dis + Float.parseFloat(linkage.getCumlativeDistance().get(d).toString());
				}
			position= new Rectangle(0, yvalue+70+qtlYValue, (int)(dis*scale)+(count*30)+50, 10);
			fig.setBackgroundColor(ColorConstants.white);
			fig.setBounds(position);
			}
			
			
			fig.setToolTip(new Label(acc1));
			fig.setOpaque(true);
			
			Rectangle posLabel= new Rectangle(0+48, yvalue+70+qtlYValue, 22, 10);
			lbl.setBounds(posLabel);
			Rectangle posLabel1= new Rectangle(0, yvalue+70+qtlYValue, 45+nextGenlbl1Width, 10);
			lbl1.setBounds(posLabel1);
			
			yvalue=yvalue+20;
			k++;
			prevSize= selectedAcc.getSelacc().size();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return fig;
	}

	@Override
	protected void createEditPolicies() {
//		installEditPolicy(EditPolicy.LAYOUT_ROLE, new ExampleXYLayoutPolicy());
	}

	protected List getModelChildren() {
		LinkageData linkageData;
		
		if(rootModel.getLoadFlag() == null){
			//RootModel rootModel = Session.getInstance().getRootmodel();
			linkageData = LinkageData.getLinkageData();
		}else{			
			linkageData = rootModel.getLinkData();
		}
		
		String countTemp= linkageData.getCount();
		int count= Integer.parseInt(countTemp);
		List<Object> result= new ArrayList<Object>();
		for(int i=0; i<((SelectedAccessions)getModel()).getSelacc1().size(); i++) {
			if(((SelectedAccessions)getModel()).getSelacc1().size()!=0) {
				List gg = ((SelectedAccessions)getModel()).getSelacc();
				HashMap<String, String> keyvalue=((SelectedAccessions)getModel()).getKeyvalue();
				for(int j=0; j<count; j++) {
					((SelectedAccessions)getModel()).getSelacc1().iterator().next().setSelacc(gg);
					((SelectedAccessions)getModel()).getSelacc1().iterator().next().setType(((SelectedAccessions)getModel()).getType());
					((SelectedAccessions)getModel()).getSelacc1().iterator().next().setKeyvalue(keyvalue);
					((SelectedAccessions)getModel()).getSelacc1().iterator().next().setList_of_widths(list_of_widths);
					result.add(((SelectedAccessions)getModel()).getSelacc1().get(i));
					
				}
			}
		}
		return result;
	}
}
