package org.icrisat.mbdt.gef.editPart.nextGenerations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.graphics.Color;
import org.icrisat.mbdt.gef.Activator;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.Chromosome.Chromosome;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.NextGeneration.LoadNextGenDataFirstChild;
import org.icrisat.mbdt.model.NextGeneration.LoadNextGenDataSecondChild;
import org.icrisat.mbdt.model.sessions.Session;
import org.icrisat.mbdt.model.sessions.SessionChromosome;

public class LoadNextGen3EditPart extends AbstractGraphicalEditPart implements IPropertyChangeListener{

	int qtlYValue = 0;
	static int Xvalue = 0;	
	int width = 0;
	static int editPartcount = 0;
	RootModel rootModel = Session.getInstance().getRootmodel();
	LinkageData linkage;
	List<String> lMapMarkerDistances = new ArrayList<String>();
	List<String> lMapMarkers = new ArrayList<String>();
	static List<String> chrName= new ArrayList<String>();
	List TargetMarkers = new ArrayList();
	static int count = 0, prevCount = 0, loopCount = 0;
	static int start = 0;
	float scale = 0.0f;
	HashMap<String, List<Figure>> figureMap; 
	static boolean start1 = false;
	@Override
	protected IFigure createFigure() {
		// TODO Auto-generated method stub
		
		LoadNextGenDataSecondChild loadNSecondChild = (LoadNextGenDataSecondChild)getModel();
		Qtl_MapData qtl_MapData;
		Chromosome chrom = SessionChromosome.getInstance().getChromosome();
		if(rootModel.getLoadFlag() == null){
			 qtl_MapData = Qtl_MapData.getQtl_MapData();
			 linkage = LinkageData.getLinkageData();
		}else{
			
			qtl_MapData = rootModel.getQtlMapData();
			linkage = rootModel.getLinkData();
		}
		scale = linkage.getScale();
		qtlYValue = qtl_MapData.getQtlYValueForOthers()+20;
		lMapMarkerDistances = linkage.getDistances();
		TargetMarkers = loadNSecondChild.getNGTargetMarkerName();
		lMapMarkers = linkage.getMarker();
		
		if(loopCount >= Integer.parseInt(linkage.getCount())){
			count = 0;
			loopCount = 0;
			start = 0;
		}
		
		
		Figure fig = new Figure();
		fig.setBackgroundColor(ColorConstants.cyan);
//		fig.setOpaque(true);
		
		Activator.getDefault().getPreferenceStore().addPropertyChangeListener(this);
		
		
		Object endString = rootModel.getLoadNextGen().get(0).getListtargetAcc().get(0).getLinkageGrpWidth().get(loadNSecondChild.getNGTLinkageGrpcount());
		Float endFloat = Float.parseFloat(endString.toString());		
		int end = Math.round(endFloat);
		Rectangle position1 = null;
		if(linkage.isCview()==true){
			try {
				if((linkage.getSelectedChromosome().equals(linkage.getChromlist().get(loadNSecondChild.getNGTLinkageGrpcount())))&&(linkage.isCview())){
				position1 = new Rectangle(70, 50+qtlYValue+60,Math.round( end*2.5f), 10);
				fig.setBounds(position1);
				}
			} catch (Exception e) {
			}
		}else{		
		 position1 = new Rectangle(start+70, 50+qtlYValue+60, end, 10);
		 fig.setBounds(position1);
			}
		
		
		Figure fig2;
		boolean first = false;
		int cumDist = 0;
		int prevCumDist = 0;
		chrName = linkage.getChromName();
		try{		
//		System.out.println("Count :"+count+":::"+lMapMarkerDistances.size()+":::"+prevCount+"::::::"+loopCount+":::::"+start);
		for(int i = count; i < lMapMarkerDistances.size(); i++) {
			
			Float mPos = Float.parseFloat((String) lMapMarkerDistances.get(i));
			int markPos = Math.round(mPos);
			cumDist = cumDist + markPos;
			
			if((lMapMarkerDistances.get(i).equals("0")&& i!=0)&&(TargetMarkers.contains(lMapMarkers.get(i)))){
				first = true;
			}
			if((!(lMapMarkerDistances.get(i).equals("0") && i!=0))||((lMapMarkerDistances.get(i).equals("0"))&&(chrName.get(i).equals(chrName.get(i-1)))&&(i!=0))) {
				
				for(int k = 0; k < TargetMarkers.size(); k++) {
					if(TargetMarkers.get(k).equals(lMapMarkers.get(i))) {
						String label = loadNSecondChild.getNGTcolorValue().get(k);
						String testLbl = "";
						fig2  = new Figure();
						if(label.equals("A")){
							testLbl = "a1";
						}else if(label.equals("B")){
							testLbl = "a2";
						}
						if(label.equals("-")){
							
							testLbl = "a50";
						}
						if(label.equals("Monomorphic")){
							
							testLbl = "amono";
						}
						
						if (getFigureMap().containsKey(testLbl)) {
							((ArrayList<Figure>)getFigureMap().get(testLbl)).add(fig2);
						} else {
							ArrayList<Figure> figureList = new ArrayList<Figure>();
							figureList.add(fig2);
							getFigureMap().put(testLbl, figureList);
						}
						assignColorsToAllels(testLbl, fig2);
						
						int midCumDist = (cumDist+prevCumDist)/2;
						if(cumDist == 0) {
							midCumDist= cumDist;
						}
						width = loadNSecondChild.getNGTWidth().get(k);
						
						
						Rectangle position = null;
						
						
						if(linkage.isCview()){
							if((linkage.getSelectedChromosome().equals(linkage.getChromlist().get(loadNSecondChild.getNGTLinkageGrpcount())))){
								
							try{
								HashMap mstatus=new HashMap();								
								mstatus=rootModel.getLinkagemap().get(0).getChromosomes().get(0).getMstatus();
							if(!(mstatus==null)){
								if(!(mstatus.get(lMapMarkers.get(i))==null)){					
								if(mstatus.get(lMapMarkers.get(i)).equals("Foreground")){
									position= new Rectangle(Math.round((midCumDist*scale*2.5f))+70, 50+qtlYValue+60,Math.round(loadNSecondChild.getNGTWidth().get(k) * 2.5f) , 12);
									
								}else{
									 position= new Rectangle(Math.round((midCumDist*scale*2.5f))+70, 52+qtlYValue+60, Math.round(loadNSecondChild.getNGTWidth().get(k) * 2.5f), 6);
								}
								}else{
									 position= new Rectangle(Math.round((midCumDist*scale*2.5f))+70, 52+qtlYValue+60, Math.round(loadNSecondChild.getNGTWidth().get(k) * 2.5f), 6);
								}
							}else{
								 position= new Rectangle(Math.round((midCumDist*scale*2.5f))+70, 52+qtlYValue+60, Math.round(loadNSecondChild.getNGTWidth().get(k) * 2.5f), 6);
							}
							fig2.setBounds(position);
							fig2.setToolTip(new Label(lMapMarkers.get(i)+": "+label));
							fig.add(fig2);
							}catch(Exception e){
							}
							}
						}else{
						//----start--foreground back ground markers---
						try{
							HashMap mstatus=new HashMap();								
							mstatus=rootModel.getLinkagemap().get(0).getChromosomes().get(0).getMstatus();
						if(!(mstatus==null)){
							if(!(mstatus.get(lMapMarkers.get(i))==null)){					
							if(mstatus.get(lMapMarkers.get(i)).equals("Foreground")){
								position= new Rectangle(Math.round((midCumDist*scale))+start+70, 50+qtlYValue+60,width , 12);
								
							}else{
								 position= new Rectangle(Math.round((midCumDist*scale))+start+70, 52+qtlYValue+60, width, 6);
							}
							}else{
								 position= new Rectangle(Math.round((midCumDist*scale))+start+70, 52+qtlYValue+60, width, 6);
							}
						}else{
							 position= new Rectangle(Math.round((midCumDist*scale))+start+70, 52+qtlYValue+60, width, 6);
						}	
						}catch(Exception e){
						}
						fig2.setBounds(position);
						fig2.setToolTip(new Label(lMapMarkers.get(i)+": "+label));
						fig.add(fig2);
						//-------end---fore ground back ground markers----
						
						}
						
						
					}
				}
				if(start1 == true &&(lMapMarkerDistances.get(i).equals("0.2") )){
					lMapMarkerDistances.set(i,"0");
					first = false;
				}
			}else if((lMapMarkerDistances.get(i).equals("0"))&&(!linkage.getChromName().get(i).equals(linkage.getChromName().get(i-1)))) {
				count = i+1;
				cumDist=0;
				break;
			}
			prevCumDist= cumDist;
		}
		if(first == true){				
			count--;
			lMapMarkerDistances.set(count, "0.2");
			start1 = true;
		}
		prevCount = count;
		start= start+end+30;
		loopCount++;
	}catch(Exception e){
	}
		return fig;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub

	}
	public void assignColorsToAllels(String label, Figure figure) {
		int color1 = 0, color2 = 0, color3 = 0;
		String colorValue = Activator.getDefault().getPreferenceStore().getString(label);
				
		if(label.equals("-")){
			if(colorValue==""){
				colorValue="192,192,192";
			}
			label = "a50";
		}
		if(label.equals("Monomorphic")){
			if(colorValue==""){
				colorValue="192,192,192";
			}
			label = "amono";
		}
		if(colorValue == ""){
			//for assigning default color to markers alleles in heat maps
			Random r = new Random();
			color1 = r.nextInt(256);
			color2 = r.nextInt(256);
			color3 = r.nextInt(256);								
			Activator.getDefault().getPreferenceStore().setValue(label, color1+","+color2+","+color3);
		}else{
			StringTokenizer st = new StringTokenizer(colorValue, ",");
			color1 = Integer.parseInt(st.nextToken());
			color2 = Integer.parseInt(st.nextToken());
			color3 = Integer.parseInt(st.nextToken());
			Activator.getDefault().getPreferenceStore().setValue(label, color1+","+color2+","+color3);
		}
		figure.setBackgroundColor(new Color(null, (new Double(color1)).intValue() , (new Double(color2)).intValue() ,(new Double(color3).intValue())));
		figure.setOpaque(true);
		
		
	}

	public void propertyChange(PropertyChangeEvent event) {
		List<Figure> figureList = getFigureMap().get(event.getProperty());
		if (figureList != null) {
			for (Figure figure : figureList) {
				assignColorsToAllels(event.getProperty(), figure);
			}
		}
	}
	
	public HashMap<String, List<Figure>> getFigureMap() {
		if(figureMap == null) {
			figureMap = new HashMap<String, List<Figure>>();
		}
		return figureMap;
	}

}
