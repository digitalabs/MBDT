package org.icrisat.mbdt.gef.editPart.genotype;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.graphics.Color;

import org.icrisat.mbdt.gef.Activator;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.GenotypeModel.Accessions;
import org.icrisat.mbdt.model.GenotypeModel.SelectedAccessions;
import org.icrisat.mbdt.model.GenotypeModel.SortedMarkers;
import org.icrisat.mbdt.model.TargetGenotype.TargetGeno;
import org.icrisat.mbdt.model.sessions.Session;
import org.icrisat.mbdt.model.sessions.SessionTargetGenotype;


public class SMarkerEditPart extends AbstractGraphicalEditPart implements  IPropertyChangeListener{

	List<String> lMarkers= new ArrayList<String>();
	static List<String> lMarkers1= new ArrayList<String>();
	List<String> markerPositions= new ArrayList<String>();
	List<String> genotypeMarkers= new ArrayList<String>();
	static List<String> maxAllele= new ArrayList<String>();
	static List<Integer> maxAlleleValue= new ArrayList<Integer>();
	static int yValue= 60, count= 0, flag= 0, start=0, gm=0, k=0, b=0;
	static String prevSelAcc= "";
	String selAcc= null;
	String marker_of_intrest;
	static int tp=0, cc= 0;
	static int r=0;
	HashMap<String, List<Figure>> figureMap; 
	static int midCumDist1=0,width1=0,ir=0;
	//for ritu target geno
	static List<String> accAllele= new ArrayList<String>();
	static List<String> markerAllele= new ArrayList<String>();
	static List<String> valueAllele= new ArrayList<String>();
	static List<String> labelAllele= new ArrayList<String>();
	static List<String> chromosomeName= new ArrayList<String>();
	static List<Integer> markerPos= new ArrayList<Integer>();
	static List<String> chrName= new ArrayList<String>();
	static HashMap type= new HashMap();
	Rectangle position=null;
	String atype=null;
	Accessions accessions=new Accessions();
	//end......... 

	ArrayList<String> labelList1=new ArrayList<String>();
	ArrayList<String> labelList3=new ArrayList<String>();
	static int prevCount= 0, tCount=0;
	HashMap idLabelMap=new HashMap();
	Vector<String> array= new Vector<String>();
	int qtlYValue = 0;
	int linkageCount = 0;
	HashMap tooltip_sort= new HashMap();
	private String marker;
	float scale = 0.0f;
	boolean heterozygous = false;
	static boolean start1 = false;
	HashMap flankingcount;
	HashMap<String, List<String>> flankingindividuals;
	@Override
	protected IFigure createFigure() {
		Activator.getDefault().getPreferenceStore().addPropertyChangeListener(this);
		String b1=null;
		SortedMarkers gM = (SortedMarkers)getModel();
		selAcc= gM.getAcc();
		
		List allellic = accessions.getAlleValues();
		List<String> allelicValues = gM.getAlleles();
		genotypeMarkers= gM.getMarkerName();
		
//		LinkageData linkageData=LinkageData.getLinkageData();
		
		tp++;
		idLabelMap=null;
		
		//Code added by SWAPNA on 19th NOV 2009.....
		//Qtl_MapData qtl_MapData =Qtl_MapData.getQtl_MapData();
		RootModel rootModel1 = Session.getInstance().getRootmodel();
		LinkageData linkageData;
		Qtl_MapData qtl_MapData;
		
		if(rootModel1.getLoadFlag() == null){
			 qtl_MapData = Qtl_MapData.getQtl_MapData();
			 linkageData = LinkageData.getLinkageData();
		}else{
			 qtl_MapData = rootModel1.getQtlMapData();
			linkageData = rootModel1.getLinkData();
		}
		scale = linkageData.getScale();
		qtlYValue = qtl_MapData.getQtlYValueForOthers();
		String genoDataCheck = rootModel1.getGenotype().get(0).getDataCheck();
		
		try{
			for(int h=0; h < linkageData.getMarkers_of_intrest().size(); h++){
				marker_of_intrest= linkageData.getMarkers_of_intrest().iterator().next();
			}
			idLabelMap=gM.getKeyvalue();
			if(tp==1){
			}
		}catch(Exception e){
		}
		if(cc==0) {
			prevSelAcc= gM.getAcc();	
		}
		cc++;
		lMarkers= linkageData.getMarker();
		linkageCount = Integer.parseInt(linkageData.getCount());
		
		int l= ((k/linkageCount)+1);
		if(l>gM.getCount()|| ((prevCount!= gM.getCount())&& tCount!=0)) {
			yValue= 40;
			k=0;
			gm=0;
			maxAlleleValue.clear();
			lMarkers1.clear();
			/*accAllele.clear();
			markerAllele.clear();
			labelAllele.clear();*/
		}
		tCount= 1;
		if (!prevSelAcc.equals(null)) {
			if((!selAcc.matches(prevSelAcc))||(flag>=Integer.parseInt(linkageData.getCount()))) {
				count=0;
				yValue= yValue+20;
				flag= 0;
				start= 0;
			}
		}
		if(gm==0) {
			int countVal = 0;
			if(rootModel1.getGeneration().equals("Second")){
//			if((rootModel1.getSaveType().equals("TargetDataAvailable"))&&(genoDataCheck.equals("Char"))){
//			if(genoDataCheck.equals("Char")){
				countVal = 1;
			}else{
				countVal = 2;
			}
			for(int i=0;i<genotypeMarkers.size(); i=i+countVal) {
				maxAllele.add((String) genotypeMarkers.get(i));
				maxAlleleValue.add(1);
			}
			gm++;
		}
		flag++;

		chrName = linkageData.getChromName();
		tooltip_sort = linkageData.getTooltip();
		
		markerPositions= linkageData.getDistances();
		Object endString= gM.getList_of_widths().get(flag-1);
		Float endFloat= Float.parseFloat(endString.toString());
		int end= Math.round(endFloat);
//		int end= Math.round(endFloat*scale);
		int cumDist= 0;
		int prevCumDist= 0;
		boolean first = false;
		Figure fig=new Figure();
		fig.setBackgroundColor(ColorConstants.cyan);
		Rectangle position1= null;
		if(linkageData.isCview()){
			 position1 = new Rectangle(start+70, yValue+70+qtlYValue, Math.round(end*2.5f), 10);
		}else{
		 position1 = new Rectangle(start+70, yValue+70+qtlYValue, end, 10);
		}
		fig.setBounds(position1);
		
		
		Figure fig2;
		for(int i= count; i<markerPositions.size(); i++) {
			Float mPos= Float.parseFloat((String) markerPositions.get(i));
			int markPos= Math.round(mPos);
			cumDist= cumDist+markPos;
			int markpos1= 0;
			if(i<markerPositions.size()-1) {
				Float mPos1= Float.parseFloat((String) markerPositions.get(i+1));
				markpos1= Math.round(mPos1);
			}
			if(((markerPositions.get(i).equals("0")||markerPositions.get(i).equals("0.0"))&& i!=0)&&(genotypeMarkers.contains(lMarkers.get(i)))){
				first = true;
			}
			if((!((markerPositions.get(i).equals("0")||(markerPositions.get(i).equals("0.0")) )&& i!=0))||((markerPositions.get(i).equals("0")||markerPositions.get(i).equals("0.0"))&&(chrName.get(i).equals(chrName.get(i-1)))&&(i!=0))) {	
//			if(!(markerPositions.get(i).equals("0") && i!=0)) {

				for(int k=0; k<genotypeMarkers.size(); k++) {

					if(genotypeMarkers.get(k).equals(lMarkers.get(i))) {
						String label= null;
						markerPos.add(cumDist);
						chromosomeName.add(chrName.get(i));
						String combiAllele = "";
						if(rootModel1.getGeneration().equals("Second")){
//						if((rootModel1.getSaveType().equals("TargetDataAvailable"))&&(genoDataCheck.equals("Char"))){
//						if(genoDataCheck.equals("Char")){
							combiAllele = allelicValues.get(k);
						}else{
							if(linkageData.getDataType().equals("Diploid")){
								combiAllele = allelicValues.get(k)+" "+allelicValues.get(k+1);
								if(!allelicValues.get(k).equals(allelicValues.get(k+1))){
									setHeterozygous(true);
								}
								}else if(linkageData.getDataType().equals("Haploid")){
									combiAllele = allelicValues.get(k);
								}
						}
//						String combiAllele = allelicValues.get(k)+" "+allelicValues.get(k+1);						
//						System.out.println("k::::"+k+"::::Combi :"+combiAllele);
						if(idLabelMap != null){
							
							labelList1=(ArrayList) idLabelMap.get(selAcc);
							//System.out.println("selacc......."+selAcc);
							//System.out.println("labelList1......."+labelList1);
							ArrayList labelList2=new ArrayList();
							labelList2.addAll(labelList1);

							labelList3 = labelList2;
//							System.out.println("labelList3......."+labelList3.size());

							for(int p=0; p<labelList3.size();p++){
								if(r==labelList3.size()){
									r=0;
									p=0;
								}
								p=r;
								b1=(String)labelList3.get(p);
								r=p+1;
								array.add(b1);
								break;
							}
						}

						label = array.elementAt(0);
						fig2= new Figure();
						if(idLabelMap.size()!= 0){
							labelList3.remove(0);
							array.clear();
						}
						//For NextGenerations....
						String testLbl = "";
						if(rootModel1.getGeneration().equals("Second")){
//						if((rootModel1.getSaveType().equals("TargetDataAvailable"))&&(genoDataCheck.equals("Char"))){
//						if(genoDataCheck.equals("Char")){
							if(label.equals("A")){
								testLbl = "a1";
							}else if(label.equals("B")){
								testLbl = "a2";
							}else if(label.equals("H")) {
								testLbl = "a3";
							}else if(label.equals("a50")) {
								testLbl = "a50";
							}else if(label.equals("amono")){
								testLbl = "amono";
							}
							label = testLbl;
							
							//-----start for monomorphic markers in 2nd generation-----
							
							try{
								HashMap monomarkers=new HashMap();
								monomarkers=rootModel1.getLinkagemap().get(0).getLimsMarkers().get(0).getMonomorphicMarkers();
								if(!(monomarkers==null)){
									if(monomarkers.get(lMarkers.get(i)).equals("Monomorphic")){
										label = "amono";
									}
								}
																				
							}catch(Exception e){
							}
							
							
							//----------end-----
						}
						/*if (getFigureMap().containsKey(label)) {
							((ArrayList<Figure>)getFigureMap().get(label)).add(fig2);
						} else {
							ArrayList<Figure> figureList = new ArrayList<Figure>();
							figureList.add(fig2);
							getFigureMap().put(label, figureList);
						}*/
						
						float midCumDist= (cumDist+prevCumDist)/2;
						if(cumDist==0) {
							midCumDist= cumDist;
						}
						float width= ((cumDist+(cumDist+markpos1))/2)-midCumDist;
						if(linkageData.isCview()){
							try{
								midCumDist = midCumDist*2.5f;
								width = width*2.5f;
							}catch(Exception e){
							}
						}
						position = null;
						
						//----start--foreground back ground markers---
							try{
								HashMap mstatus=new HashMap();								
								mstatus=rootModel1.getLinkagemap().get(0).getChromosomes().get(0).getMstatus();
							if(!(mstatus==null)){
								if(!(mstatus.get(lMarkers.get(i))==null)){
								if(mstatus.get(lMarkers.get(i)).equals("Foreground")){
									position= new Rectangle(Math.round((midCumDist*scale))+start+70, yValue+70+qtlYValue, (int)(width*scale), 12);
								}else{
									 position= new Rectangle(Math.round((midCumDist*scale))+start+70, yValue+72+qtlYValue, (int)(width*scale), 6);
								}
								}else{
									 position= new Rectangle(Math.round((midCumDist*scale))+start+70, yValue+72+qtlYValue, (int)(width*scale), 6);
								}
							}else{
								position= new Rectangle(Math.round((midCumDist*scale))+start+70, yValue+72+qtlYValue, (int)(width*scale), 6);
							}
							midCumDist1 = Math.round((midCumDist*scale));
							width1 = (int)(width*scale);
							}catch(Exception e){
							}
													
							
							//-------end---fore ground back ground markers----
								
								
//						Rectangle position= new Rectangle(midCumDist+start+70, yValue+70+qtlYValue, width, 10);
						/*fig2.setBounds(position);
						
						
						if(genoDataCheck.equals("Char")){
							if(label.equals("amono")){
								fig2.setToolTip(new Label(lMarkers.get(i)+": Monomorphic"));
							}else{
								fig2.setToolTip(new Label(lMarkers.get(i)+": "+tooltip_sort.get(key1)));
							}
						}else{
						fig2.setToolTip(new Label(lMarkers.get(i)+": "+tooltip_sort.get(key1)));
						}*/
						String key1 =lMarkers.get(i)+"$"+selAcc;
						try{
						if(label.contains("@@")){
							if(label.contains("a50")){
								label="a50";
								assignColorsToAllels(label, fig2);
								fig.add(fig2);
								setHeterozygous(false);
							}else{
								String tool = (String) tooltip_sort.get(key1);
							Rectangle hetero1 = null;
							if((Integer.parseInt(tool.substring(0,tool.indexOf(" "))))<((Integer.parseInt(tool.substring(tool.indexOf(" ")+1,tool.length()))))){
								if(position.height == 6){
									hetero1= new Rectangle(Math.round((midCumDist*scale))+start+70, yValue+72+qtlYValue, (int)(width*scale), 4);
								}else{
									hetero1= new Rectangle(Math.round((midCumDist*scale))+start+70, yValue+72+qtlYValue-3, (int)(width*scale), 7);	
								}
								fig2.setBounds(hetero1);
								fig2.setToolTip(new Label(lMarkers.get(i)+" : "+tool.substring(0,tool.indexOf(" ") )));
								assignColorsToAllels(label.substring(0,label.indexOf("@@")), fig2);
								
								fig2.setOpaque(true);
								fig.add(fig2);
								if (getFigureMap().containsKey(label.substring(0,label.indexOf("@@")))) {
									((ArrayList<Figure>)getFigureMap().get(label.substring(0,label.indexOf("@@")))).add(fig2);
								} else {
									ArrayList<Figure> figureList = new ArrayList<Figure>();
									figureList.add(fig2);
									getFigureMap().put(label.substring(0,label.indexOf("@@")), figureList);
								}
								fig2 = new Figure();
								if(position.height == 6){
									hetero1= new Rectangle(Math.round((midCumDist*scale))+start+70, yValue+72+qtlYValue+3, (int)(width*scale), 3);
								}else{
									hetero1= new Rectangle(Math.round((midCumDist*scale))+start+70, yValue+72+qtlYValue+3, (int)(width*scale), 6);	
								}
								fig2.setBounds(hetero1);
								fig2.setToolTip(new Label(lMarkers.get(i)+" : "+tool.substring(tool.indexOf(" ")+1,tool.length())));
//								fig2.setBackgroundColor(ColorConstants.darkGreen);
								assignColorsToAllels(label.substring(label.indexOf("@@")+2,label.length()), fig2);
								fig2.setVisible(true);
								fig2.setOpaque(true);
								fig.add(fig2);
								if (getFigureMap().containsKey(label.substring(label.indexOf("@@")+2,label.length()))) {
									((ArrayList<Figure>)getFigureMap().get(label.substring(label.indexOf("@@")+2,label.length()))).add(fig2);
								} else {
									ArrayList<Figure> figureList = new ArrayList<Figure>();
									figureList.add(fig2);
									getFigureMap().put(label.substring(label.indexOf("@@")+2,label.length()), figureList);
								}
							}else{
								if(position.height == 6){
									hetero1= new Rectangle(Math.round((midCumDist*scale))+start+70, yValue+72+qtlYValue+3, (int)(width*scale), 3);
								}else{
									hetero1= new Rectangle(Math.round((midCumDist*scale))+start+70, yValue+72+qtlYValue+3, (int)(width*scale), 6);	
								}
								
								fig2.setBounds(hetero1);
								fig2.setToolTip(new Label(lMarkers.get(i)+" : "+tool.substring(0,tool.indexOf(" ") )));
								assignColorsToAllels(label.substring(0,label.indexOf("@@")), fig2);
								
								fig2.setOpaque(true);
								fig.add(fig2);
								if (getFigureMap().containsKey(label.substring(0,label.indexOf("@@")))) {
									((ArrayList<Figure>)getFigureMap().get(label.substring(0,label.indexOf("@@")))).add(fig2);
								} else {
									ArrayList<Figure> figureList = new ArrayList<Figure>();
									figureList.add(fig2);
									getFigureMap().put(label.substring(0,label.indexOf("@@")), figureList);
								}
								fig2 = new Figure();
								if(position.height == 6){
									hetero1= new Rectangle(Math.round((midCumDist*scale))+start+70, yValue+72+qtlYValue, (int)(width*scale), 4);
								}else{
									hetero1= new Rectangle(Math.round((midCumDist*scale))+start+70, yValue+72+qtlYValue-3, (int)(width*scale), 7);	
								}
								fig2.setBounds(hetero1);
								fig2.setToolTip(new Label(lMarkers.get(i)+" : "+tool.substring(tool.indexOf(" ")+1,tool.length())));
//								fig2.setBackgroundColor(ColorConstants.darkGreen);
								assignColorsToAllels(label.substring(label.indexOf("@@")+2,label.length()), fig2);
								fig2.setVisible(true);
								fig2.setOpaque(true);
								fig.add(fig2);
								if (getFigureMap().containsKey(label.substring(label.indexOf("@@")+2,label.length()))) {
									((ArrayList<Figure>)getFigureMap().get(label.substring(label.indexOf("@@")+2,label.length()))).add(fig2);
								} else {
									ArrayList<Figure> figureList = new ArrayList<Figure>();
									figureList.add(fig2);
									getFigureMap().put(label.substring(label.indexOf("@@")+2,label.length()), figureList);
								}
							}
							
							setHeterozygous(false);
							}
						}else{

							
							if (getFigureMap().containsKey(label)) {
								((ArrayList<Figure>)getFigureMap().get(label)).add(fig2);
							} else {
								ArrayList<Figure> figureList = new ArrayList<Figure>();
								figureList.add(fig2);
								getFigureMap().put(label, figureList);
							}
							
									
//							Rectangle position= new Rectangle(midCumDist+start+70, yValue+70+qtlYValue, width, 10);
							fig2.setBounds(position);
							
							key1 =lMarkers.get(i)+"$"+selAcc;
							if(genoDataCheck.equals("Char")){
								if(label.equals("amono")){
									fig2.setToolTip(new Label(lMarkers.get(i)+": Monomorphic"));
								}else{
									fig2.setToolTip(new Label(lMarkers.get(i)+": "+tooltip_sort.get(key1)));
								}
							}else{
							fig2.setToolTip(new Label(lMarkers.get(i)+": "+tooltip_sort.get(key1)));
							}
							assignColorsToAllels(label, fig2);
							fig.add(fig2);}
						}catch(Exception e){
						}
						
//						fig.add(fig2);
						/*if((genoDataCheck.equals("Char"))||()){
							
						}else{
							k=k+1;
						}*/
						break;
					}
				}
				if(start1 == true &&(markerPositions.get(i).equals("0.2") )){
					markerPositions.set(i,"0");
					first = false;
				}
			}
			else if(((markerPositions.get(i).equals("0"))||(markerPositions.get(i).equals("0.0")))&&(!chrName.get(i).equals(chrName.get(i-1)))) {
				count= i+1;
				cumDist=0;
				break;
			}
			prevCumDist= cumDist;
		}
		if(first == true){				
			count--;
			markerPositions.set(count, "0.2");
			start1 = true;
		}
		start= start+end+30;
		prevSelAcc= selAcc;
		k++;
//		System.out.println(selAcc);
		accAllele=linkageData.getAccallellic();
		type=linkageData.getType();
		markerAllele=linkageData.getMarkallelic();
		labelAllele=linkageData.getLabelallelic();
		prevCount= gM.getCount();
//		linkageData.setAccallellic(accAllele);
		RootModel rootModel = Session.getInstance().getRootmodel();
		rootModel.setAccession(accAllele);
		rootModel.setChrNo(chromosomeName);
		rootModel.setMarkerName(markerAllele);
		rootModel.setMarkerPosition(markerPos);
		rootModel.setLabel(labelAllele);
		rootModel.setType(type);
		return fig;
	}


	private void assignColorsToAllels(String label, Figure figure) {
		String colorValue =  Activator.getDefault().getPreferenceStore().getString(label);
		int color1 = 0,color2 = 0,color3 = 0;
		//----for monomorphic markers......
		if(label.equals("amono")){								
			if(colorValue==""){
				colorValue="192,192,192";
				}
			StringTokenizer st = new StringTokenizer(colorValue, ",");
			color1 = Integer.parseInt(st.nextToken());
			color2 = Integer.parseInt(st.nextToken());
			color3 = Integer.parseInt(st.nextToken());
			Activator.getDefault().getPreferenceStore().setValue(label, color1+","+color2+","+color3);
		}
		if(label.equals("a50")){
			if(colorValue==""){
			colorValue="192,192,192";
			}
			StringTokenizer st = new StringTokenizer(colorValue, ",");
			color1 = Integer.parseInt(st.nextToken());
			color2 = Integer.parseInt(st.nextToken());
			color3 = Integer.parseInt(st.nextToken());
			Activator.getDefault().getPreferenceStore().setValue(label, color1+","+color2+","+color3);
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
		}
		
		
		
		figure.setBackgroundColor(new Color(null, color1 , color2 ,color3));
		figure.setOpaque(true);
	}
	
	@Override
	protected void createEditPolicies() {
		//installEditPolicy(EditPolicy.LAYOUT_ROLE, new ExampleXYLayoutPolicy());
	}
public void propertyChange(PropertyChangeEvent evt) {
		
		marker = evt.getPropertyName().substring(26, evt.getPropertyName().length());
		if (evt.getPropertyName().substring(0,26).equals("PROPERTY_CHANGE_Forestatus")) {
			refreshVisuals();
//			createFigure();
		}
	}

protected void refreshVisuals() {
	
	try{
		RootModel rootModel1 = Session.getInstance().getRootmodel();

		HashMap mstatus=new HashMap();
		mstatus=rootModel1.getLinkagemap().get(0).getChromosomes().get(0).getMstatus();
		
		if(mstatus==null){
			mstatus=new HashMap();
		}
		
		int marketPosition = lMarkers.indexOf(marker);
		if(marketPosition != -1 && (rootModel1.getLinkagemap().get(0).getChromosomes().get(marketPosition).getForestatus().equals("Foreground")||rootModel1.getLinkagemap().get(0).getChromosomes().get(marketPosition).getForestatus().equals("Foreground FL"))){
			position= new Rectangle(midCumDist1+start+70, yValue+72+qtlYValue, width1, 12);
			mstatus.put(marker, "Foreground");
			
		
		}else{
			position= new Rectangle(midCumDist1+start+70, yValue+72+qtlYValue, width1, 6);
			mstatus.put(marker, "Background");
		}
		
		rootModel1.getLinkagemap().get(0).getChromosomes().get(0).setMstatus(mstatus);
		/*if(rootModel1 instanceof RootModel) {
			Session.getInstance().setRootModel(rootModel1);
		}*/			
	}catch(Exception e){
	}
	
}


public void propertyChange(org.eclipse.jface.util.PropertyChangeEvent arg0) {
	List<Figure> figureList = getFigureMap().get(arg0.getProperty());
	if (figureList != null) {
		for (Figure figure : figureList) {
			assignColorsToAllels(arg0.getProperty(), figure);
		}
		
	}
}

public HashMap<String, List<Figure>> getFigureMap() {
	if(figureMap == null) {
		figureMap = new HashMap<String, List<Figure>>();
	}
	return figureMap;
}
public boolean isHeterozygous() {
	return heterozygous;
}

public void setHeterozygous(boolean heterozygous) {
	this.heterozygous = heterozygous;
}

}
