package org.icrisat.mbdt.gef.editPart.genotype;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;


import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.Polygon;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.Triangle;


import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.TableItem;
import org.icrisat.mbdt.gef.Activator;
import org.icrisat.mbdt.gef.wizards.BestIndividualPage;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.GenotypeModel.GenotypeMarkers;
import org.icrisat.mbdt.model.LinkageMapModel.Intervals;
import org.icrisat.mbdt.model.TargetGenotype.TargetGeno;
import org.icrisat.mbdt.model.sessions.Session;
import org.icrisat.mbdt.model.sessions.SessionTargetGenotype;

public class GMarkerEditPart extends AbstractGraphicalEditPart implements PropertyChangeListener, IPropertyChangeListener{

	List<String> lMarkers= new ArrayList<String>();
	LinkedHashSet<String> list= new LinkedHashSet<String>();
	static List<String> lMarkers1= new ArrayList<String>();
	List<String> markerPositions= new ArrayList<String>();
	List<String> genotypeMarkers= new ArrayList<String>();
	static List<String> accAllele= new ArrayList<String>();
	static HashMap type= new HashMap();
	HashMap<String, List<Figure>> figureMap; 
	static HashMap<String, Integer> alleleLabel = new HashMap<String, Integer>(); 
	static List<String> markerAllele= new ArrayList<String>();
	static List<String> valueAllele= new ArrayList<String>();
	static List<String> labelAllele= new ArrayList<String>();
	static List<String> maxAllele= new ArrayList<String>();
	static List<Integer> maxAlleleValue= new ArrayList<Integer>();
	static List<String> chromosomeName= new ArrayList<String>();
	static List<Integer> markerPos= new ArrayList<Integer>();
	static List<Integer> markerPrevPos= new ArrayList<Integer>();
	static List<Integer> markerNextPos= new ArrayList<Integer>();
	static List<String> chrName= new ArrayList<String>();
	static List<String> combination= new ArrayList<String>();
	static int yValue= 80, count= 0, flag= 0, start=0, gm=0, k=0, b=0;
	static String prevSelAcc= "";
	static String firstAcc= null;
	String selAcc= null;
	String atype=null;
	String marker_of_intrest;
	static List<String> listSelMarker= new ArrayList<String>();
	Vector<String> accessions= new Vector<String>();
	Vector<String> label1= new Vector<String>();
	static int tp=0, cc= 0, max = 0;
	static int prevCount= 0, tCount=0;
	int qtlYValue = 0;

	int selAccSize = 0;
	static int colorCount = 1;
	static int colorCount1 = 1;	
	List<String> lMapMarkerOrderList = new ArrayList<String>();
	static HashMap markerAlleleColor = new HashMap();
	HashMap bestcount;
	HashMap<String, List<String>> bestindividuals;
	HashMap missingcount;
	HashMap flankingcount;
	HashMap<String, List<String>> missingindividuals;
	HashMap<String, List<String>> flankingindividuals;
	List markers = new ArrayList();
	static int prevFlagVal = 0;
	int linkageCount = 0;
	static HashMap tooltip  = new HashMap(); 
	private Label  lblStatusType;
	Rectangle position=null;
	static int ic = 0;
	static int midCumDist1=0,width1=0,ir=0;
	Figure fig2;
	static int init = 0;
	static String initstr = "";
	private String marker;
	boolean heterozygous = false;
	float scale = 0.0f;
	Color color;
	static boolean start1 = false;
	static int mwidth =0, pwidth = 0;
	
	@Override
	protected IFigure createFigure() {
		Activator.getDefault().getPreferenceStore().addPropertyChangeListener(this);
		GenotypeMarkers gM = (GenotypeMarkers)getModel();
		selAcc= gM.getAcc();
		atype=gM.getType();
		List<String> allelicValues = gM.getAlleles();
		genotypeMarkers = gM.getMarkerName();

		Figure fig = new Figure();
		lblStatusType = new Label();

		RootModel rootModel1 = Session.getInstance().getRootmodel();
		LinkageData linkageData;
		Qtl_MapData qtl_MapData;

		if(rootModel1.getLoadFlag() == null){
			qtl_MapData = Qtl_MapData.getQtl_MapData();
			linkageData = LinkageData.getLinkageData();
		}else{
			qtl_MapData = rootModel1.getQtlMapData();
			linkageData = rootModel1.getLinkData();
			rootModel1 = Session.getInstance().getRootmodel();
		}

		scale = linkageData.getScale();
		
		tp++;
		RootModel root = Session.getInstance().getRootmodel();
		firstAcc = root.getFirstAcc();

		for(int acc = 0; acc < root.getGenotype().get(0).getAccessions().size(); acc++){
			for(int selacc = 0; selacc < root.getGenotype().get(0).getAccessions().get(acc).getSelectedAccessions().size(); selacc++){
				selAccSize = root.getGenotype().get(0).getAccessions().get(acc).getSelectedAccessions().get(selacc).getSelacc().size();
			}
		}

		//Code added by SWAPNA on 19th NOV 2009.....
		qtlYValue = qtl_MapData.getQtlYValueForOthers();
		String genoDataCheck = root.getGenotype().get(0).getDataCheck();

		try{
			for(int h=0; h < linkageData.getMarkers_of_intrest().size(); h++){
				marker_of_intrest= linkageData.getMarkers_of_intrest().iterator().next();
			}
		}catch(Exception e){
		}
		if(cc==0) {
			prevSelAcc= gM.getAcc();
			initstr = gM.getAcc();
		}
		cc++;
		lMarkers = linkageData.getMarker();
		markerPositions= linkageData.getDistances();
		linkageCount = Integer.parseInt(linkageData.getCount());

		//Here we are creating a List of markers according to LinkageMap file order...
		for(int i = 0; i < markerPositions.size(); i++) {
			for(int k=0; k<genotypeMarkers.size(); k++) {
				if(genotypeMarkers.get(k).equals(lMarkers.get(i))) {
					if(!lMapMarkerOrderList.contains(lMarkers.get(i))){
						lMapMarkerOrderList.add(lMarkers.get(i));
					}
				}
			}			
		}

		int l= ((k/linkageCount)+1);
		if(l > gM.getCount() || ((prevCount != gM.getCount())&& tCount!=0)) {
			yValue= 60;
			k=0;
			gm=0;
			maxAlleleValue.clear();
			lMarkers1.clear();
			accessions.clear();
			label1.clear();
			list.clear();
			accAllele.clear();
			markerAllele.clear();
			valueAllele.clear();
			labelAllele.clear();
			combination.clear();
			colorCount = 1;
			colorCount1 = 1;
			markerAlleleColor.clear();
			getFigureMap().clear();
		}
		tCount= 1;
		if (!prevSelAcc.equals(null)) {
			if((!(selAcc.matches(prevSelAcc))) && ((flag == prevFlagVal) || (prevFlagVal == 0))||(flag>=Integer.parseInt(linkageData.getCount()))) {
				count =0;
				yValue = yValue + 20;
				prevFlagVal = flag;
				flag = 0;
				start = 0;
				try {
					if(ic==0 && !(rootModel1.getLinkData().getBestcount()==null)){
						rootModel1.getLinkData().setBestcount(null);
					}
				} catch (Exception e) {
				}
				if((ic<root.getGenotype().get(0).getAccessions().size())&&(!initstr.equals(selAcc))){
					ic++;
				}

			}if((selAcc.matches(prevSelAcc)) && ((flag == prevFlagVal) && (prevFlagVal != 0))||(flag>=Integer.parseInt(linkageData.getCount()))){
				count =0;
				yValue = yValue + 20;
				prevFlagVal = flag;
				flag = 0;
				start = 0;
			}		
		}
		if(gm == 0) {
			int countVal = 0;
			if(root.getGeneration().equals("Second")){
				//				if((rootModel1.getSaveType().equals("TargetDataAvailable"))&&(genoDataCheck.equals("Char"))){
				//				if(genoDataCheck.equals("Char")){
				countVal = 1;
			}else{
				if(linkageData.getDataType().equals("Diploid")){
					countVal = 2;
				}else if(linkageData.getDataType().equals("Haploid")){
					countVal = 1;
				}
			}
			for(int i = 0; i < genotypeMarkers.size(); i=i+countVal) {				
				maxAllele = lMapMarkerOrderList;
				maxAlleleValue.add(colorCount1);
				if(root.getGeneration().equals("Second")){
					//					if(rootModel1.getSaveType().equals("TargetDataAvailable")){
					//					if(genoDataCheck.equals("Char")){
					if(colorCount1 >= 3){
						colorCount1 = 1;
					}else{
						colorCount1 ++;
					}
				}else{
					if(colorCount1 >= selAccSize){
						colorCount1 = 1;
					}else{
						colorCount1 ++;
					}
				}
			}
			gm++;
		}
		flag++;
		chrName = linkageData.getChromName();
		try{
			Object endString = gM.getList_of_widths().get(flag - 1);
			Float endFloat = Float.parseFloat(endString.toString());
			
			int end = Math.round(endFloat);
//			int end = Math.round(endFloat*scale);
			int cumDist = 0;
			int prevCumDist = 0;
			boolean first = false;
			Rectangle position1 = null;
			fig.setBackgroundColor(ColorConstants.cyan);
			if(linkageData.isCview()){
				 position1 = new Rectangle(start+70, yValue+70+qtlYValue, Math.round(end*2.5f), 10);
			}else{
			 position1 = new Rectangle(start+70, yValue+70+qtlYValue, end, 10);
			}
			fig.setBounds(position1);
			for(int i = count; i < markerPositions.size(); i++) {
				try{
					rootModel1.getLinkagemap().get(0).getChromosomes().get(i).addListener(this);
				}catch(Exception e){
				}

				Float mPos = Float.parseFloat((String) markerPositions.get(i));
				int markPos = Math.round(mPos);
				cumDist = cumDist + markPos;
				int markpos1 = 0;
				if(i < markerPositions.size()-1) {
					Float mPos1 = Float.parseFloat((String) markerPositions.get(i+1));
					
					markpos1 = Math.round(mPos1);
					//----start drawing to next screened marker----
					/*mwidth = mwidth + Math.round(Float.parseFloat((String) markerPositions.get(i)));
					if(rootModel1.getGenotype().get(0).getHeaderList().contains(lMarkers.get(i))){
						pwidth = mwidth - pwidth;
						
					}*/
						//----end-----
				}
				if(((markerPositions.get(i).equals("0")||markerPositions.get(i).equals("0.0"))&& i!=0)&&(genotypeMarkers.contains(lMarkers.get(i)))){
					first = true;
				}
				
				if((!((markerPositions.get(i).equals("0")||(markerPositions.get(i).equals("0.0")) )&& i!=0))||((markerPositions.get(i).equals("0")||markerPositions.get(i).equals("0.0"))&&(chrName.get(i).equals(chrName.get(i-1)))&&(i!=0))) {
//				if(!(markerPositions.get(i).equals("0") && i!=0)) {
					for(int k = 0; k < genotypeMarkers.size(); k++) {
						
						if(genotypeMarkers.get(k).equals(lMarkers.get(i))) {
							String label = "";
							String genemarker = genotypeMarkers.get(k).toString();
							String combiAllele = "";

							if(root.getGeneration().equals("Second")){
								//								if((rootModel1.getSaveType().equals("TargetDataAvailable"))&&(genoDataCheck.equals("Char"))){
								//								if(genoDataCheck.equals("Char")){
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

							String tempMarker = null;

							markerPos.add(cumDist);
							chromosomeName.add(chrName.get(i));
							tempMarker = lMarkers.get(i);
							if(selAcc.equals(firstAcc)) {							
								/**
								 * Here we are coloring the first selected accession....
								 **/
								if(genoDataCheck.equals("Char")){
									if(rootModel1.getGeneration().equals("Second")){
										if(combiAllele.contains("-")){
											label="a50";
										}else if(combiAllele.contains("Monomorphic")){
											label = "amono";
										}else{
											label = combiAllele;							
										}
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

									}else if(rootModel1.getGeneration().equals("First")){
										if(!(alleleLabel.containsKey(combiAllele))){
											colorCount = alleleLabel.size()+1;
											alleleLabel.put(combiAllele, colorCount);

										}


										if(combiAllele.contains("-")){
											label="a50";
										}else{
											label = combiAllele;							
										}
									}

								}else if(linkageData.isDart()){
									if(!(alleleLabel.containsKey(combiAllele))){
										colorCount = alleleLabel.size()+1;
										alleleLabel.put(combiAllele, colorCount);

									}
									if(combiAllele.contains("-")){
										label="a50";
									}else{
										label = combiAllele;							
									}
								}else{
									if((Integer.parseInt(combiAllele.substring(0,1))==0)|| (Integer.parseInt(combiAllele.substring(combiAllele.indexOf(" ")+1,combiAllele.indexOf(" ")+2))==0)){
										label="a50";
									}else{
										if(isHeterozygous()){
											label = "a"+colorCount;
											maxAlleleValue.remove(maxAllele.indexOf(tempMarker));
											markerAlleleColor.put(lMarkers.get(i)+"@"+allelicValues.get(k),"a"+colorCount );
											
//											maxAlleleValue.add(maxAllele.indexOf(tempMarker),colorCount);
											colorCount++;
											label = label+"@@"+"a"+colorCount;	

											markerAlleleColor.put(lMarkers.get(i)+"@"+allelicValues.get(k+1), "a"+colorCount);
											maxAlleleValue.add(maxAllele.indexOf(tempMarker),colorCount);
										}else{
											label = "a"+colorCount;
											maxAlleleValue.remove(maxAllele.indexOf(tempMarker));
											markerAlleleColor.put(lMarkers.get(i)+"@"+allelicValues.get(k), label);
											maxAlleleValue.add(maxAllele.indexOf(tempMarker),colorCount);
										}
									}
								}
								accAllele.add(selAcc);
								type.put(selAcc,atype);
								markerAllele.add(genemarker);
								labelAllele.add(label);
								valueAllele.add(combiAllele);
								if(rootModel1.getGeneration().equals("Second")){
									//									if((rootModel1.getSaveType().equals("TargetDataAvailable"))&&(genoDataCheck.equals("Char"))){
									//									if(genoDataCheck.equals("Char")){
									if(colorCount >= 3){
										colorCount = 1;
									}else{
										colorCount ++;
									}
								}else{
									if(genoDataCheck.equals("Char")){
										/*if(!(alleleLabel.containsKey(combiAllele))){
									alleleLabel.put(combiAllele, colorCount);
									colorCount++;
									}else{
										colorCount = alleleLabel.get(combiAllele);
									}
										 */

									}else{
										if(colorCount >= selAccSize){
											colorCount = 1;
										}else{
											colorCount ++;
										}
									}
								}
							}
							else {
								/** Here we are coloring the selected accession having 
								 ** same allele value for a particular marker.....
								 **/
								if (combination.contains(lMarkers.get(i)+": "+combiAllele)) {
									for(int j = 0; j < accAllele.size(); j++) {
										if((markerAllele.get(j).equals(lMarkers.get(i)))) {
											if (valueAllele.get(j).equals(combiAllele)) {

												if(genoDataCheck.equals("Char")){
													if(rootModel1.getGeneration().equals("Second")){
														if(combiAllele.contains("-")){
															label="a50";
														}else if(combiAllele.contains("Monomorphic")){
															label = "amono";
														}else{
															label = combiAllele;							
														}
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

													}else if(rootModel1.getGeneration().equals("First")){

														if(combiAllele.contains("-")){
															label="a50";
														}else{
															label = combiAllele;							
														}
													}

												}else{
													try {
														if(linkageData.isDart()){
															if(combiAllele.contains("-")){
																label="a50";
															}else{
																label = labelAllele.get(j);;							
															}
														}else if((Integer.parseInt(combiAllele.substring(0,1))==0)|| (Integer.parseInt(combiAllele.substring(combiAllele.indexOf(" ")+1,combiAllele.indexOf(" ")+2))==0)){
															label="a50";
														}else{
															label= labelAllele.get(j);
														}
													} catch (Exception e) {
														// TODO Auto-generated catch block
													}
												}

												accAllele.add(selAcc);
												type.put(selAcc,atype);
												markerAllele.add(genemarker);
												labelAllele.add(label);
												valueAllele.add(combiAllele);
												break;
											}
										}
									}
								}else if((isHeterozygous())&&((markerAlleleColor.containsKey(lMarkers.get(i)+"@"+allelicValues.get(k)))||(markerAlleleColor.containsKey(lMarkers.get(i)+"@"+allelicValues.get(k+1))))){
									int maximum= 0;
									for(int j = 0; j < accAllele.size(); j++) {
										if((markerAllele.get(j).equals(lMarkers.get(i)))) {
											
											
											if(genoDataCheck.equals("Char")){
												if(rootModel1.getGeneration().equals("First")){

														if(combiAllele.contains("-")){
															label="a50";
														}else{
															label = combiAllele;							
														}
													}

												}else{
													
													if((Integer.parseInt(combiAllele.substring(0,1))==0)|| (Integer.parseInt(combiAllele.substring(combiAllele.indexOf(" ")+1,combiAllele.indexOf(" ")+2))==0)){
														label="a50";
													}else{
														if(isHeterozygous()){
															if((markerAlleleColor.containsKey(lMarkers.get(i)+"@"+allelicValues.get(k)))){
																label = (String) markerAlleleColor.get(lMarkers.get(i)+"@"+allelicValues.get(k));
															}else{

																maximum = maxAlleleValue.get(maxAllele.indexOf(tempMarker));
																maxAlleleValue.remove(maxAllele.indexOf(tempMarker));
																if(maximum >= selAccSize*2){								
																	maximum = 1;								
																}else{
																	maximum++;
																}
//																maximum++;
																label= "a"+maximum;
																maxAlleleValue.add(maxAllele.indexOf(tempMarker),maximum);
																markerAlleleColor.put(lMarkers.get(i)+"@"+allelicValues.get(k), "a"+maximum);
															}
															if((markerAlleleColor.containsKey(lMarkers.get(i)+"@"+allelicValues.get(k+1)))){
																label = label+"@@"+(String) markerAlleleColor.get(lMarkers.get(i)+"@"+allelicValues.get(k+1));;	
															}else{
																maximum = maxAlleleValue.get(maxAllele.indexOf(tempMarker));
																maxAlleleValue.remove(maxAllele.indexOf(tempMarker));
																if(maximum >= selAccSize*2){								
																	maximum = 1;								
																}else{
																	maximum++;
																}
//																maximum++;
																label= label+"@@a"+maximum;
																maxAlleleValue.add(maxAllele.indexOf(tempMarker),maximum);
																markerAlleleColor.put(lMarkers.get(i)+"@"+allelicValues.get(k+1), "a"+maximum);
																
															}
														}else{
															label = (String) markerAlleleColor.get(lMarkers.get(i)+"@"+allelicValues.get(k));
														}
													}
												}
											accAllele.add(selAcc);
											type.put(selAcc,atype);
											markerAllele.add(genemarker);
											labelAllele.add(label);
											valueAllele.add(combiAllele);
											break;
										}
										
//									}
										
									}
								}else {
									/** Here we are coloring the selected accession having 
									 ** different allele value for a particular marker.....
									 **/
									if((markerAlleleColor.containsKey(lMarkers.get(i)+"@"+allelicValues.get(k)))){
										label = (String) markerAlleleColor.get(lMarkers.get(i)+"@"+allelicValues.get(k));
									}else{
									
									int maximum= 0, prevmaximum = 0;
									int index= maxAllele.indexOf(tempMarker);
									try{
									maximum = maxAlleleValue.get(index);
									}catch(Exception e){
									}
									prevmaximum = maximum;
									if(rootModel1.getGeneration().equals("Second")){
										//										if(genoDataCheck.equals("Char")){
										if(maximum >= 3){								
											maximum = 1;								
										}else{
											maximum++;
										}
									}else{
										if(genoDataCheck.equals("Char")){
											if(!(alleleLabel.containsKey(combiAllele))){
												maximum = alleleLabel.size()+1;
												alleleLabel.put(combiAllele, maximum);

											}

											maximum = alleleLabel.get(combiAllele);


										}else if(linkageData.isDart()){
											if(!(alleleLabel.containsKey(combiAllele))){
												colorCount = alleleLabel.size()+1;
												alleleLabel.put(combiAllele, colorCount);

											}
											if(combiAllele.contains("-")){
												label="a50";
											}else{
												label = combiAllele;							
											}
										}else if(isHeterozygous()){
											if(maximum >= selAccSize*2){								
												maximum = 1;								
											}else{
												maximum++;
											}
										}else{
											if(maximum >= selAccSize){								
												maximum = 1;								
											}else{
												maximum++;
											}
										}
									}
									try{
									maxAlleleValue.remove(index);
									}catch(Exception e){
										
									}
									if(genoDataCheck.equals("Char")){

										if(rootModel1.getGeneration().equals("Second")){
											if(combiAllele.contains("-")){
												label="a50";
											}else if(combiAllele.contains("Monomorphic")){
												label = "amono";
											}else{
												label = combiAllele;							
											}
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

										}else if(rootModel1.getGeneration().equals("First")){
											if(genoDataCheck.equals("Char")){

												if(!(alleleLabel.containsKey(combiAllele))){
													maximum = alleleLabel.size()+1;
													alleleLabel.put(combiAllele, maximum);
												}
												label = combiAllele;
												if(combiAllele=="-"){
													label = "a50";
												}
											}else{
												if((Integer.parseInt(combiAllele.substring(0,1))==0)|| (Integer.parseInt(combiAllele.substring(combiAllele.indexOf(" ")+1,combiAllele.indexOf(" ")+2))==0)){
													label="a50";
												}else{
													if(isHeterozygous()){
														label = "a"+maximum;
														markerAlleleColor.put(lMarkers.get(i)+"@"+allelicValues.get(k),"a"+maximum );
														maximum++;
														label = label+"@@"+"a"+maximum;	
														maxAlleleValue.add(index, maximum);
														markerAlleleColor.put(lMarkers.get(i)+"@"+allelicValues.get(k+1), "a"+maximum);
													}else{
														label = "a"+maximum;
														maxAlleleValue.add(index, maximum);
														markerAlleleColor.put(lMarkers.get(i)+"@"+allelicValues.get(k), label);
													}

												}
											}
										}

									}else{
										try {
											if(linkageData.isDart()){
												if(combiAllele.contains("-")){
													label="a50";
												}else{
													label = "a"+maximum;
													markerAlleleColor.put(lMarkers.get(i)+"@"+allelicValues.get(k), label);
													maxAlleleValue.add(index, maximum);							
												}
											}else  if((Integer.parseInt(combiAllele.substring(0,1))==0)|| (Integer.parseInt(combiAllele.substring(combiAllele.indexOf(" ")+1,combiAllele.indexOf(" ")+2))==0)){
												label="a50"; 
												maxAlleleValue.add(index, maximum);
											}else{
												if(isHeterozygous()){
													label = "a"+maximum;
													markerAlleleColor.put(lMarkers.get(i)+"@"+allelicValues.get(k),"a"+maximum );
													maximum++;
													label = label+"@@"+"a"+maximum;	
													maxAlleleValue.add(index, maximum);
													markerAlleleColor.put(lMarkers.get(i)+"@"+allelicValues.get(k+1), "a"+maximum);

												}else{
													label = "a"+maximum;
													markerAlleleColor.put(lMarkers.get(i)+"@"+allelicValues.get(k), label);
													maxAlleleValue.add(index, maximum);
												}
											}
										} catch (Exception e) {
											// TODO Auto-generated catch block
										}
									}
									
								}
									accAllele.add(selAcc);
									type.put(selAcc,atype);
									markerAllele.add(genemarker);
									labelAllele.add(label);
									valueAllele.add(combiAllele);
								}	
							}
							combination.add(lMarkers.get(i)+": "+combiAllele);
							fig2 = new Figure();
							try {
								String testLbl = "";
								if(rootModel1.getGeneration().equals("Second")){
									//									if((rootModel1.getSaveType().equals("TargetDataAvailable"))&&(genoDataCheck.equals("Char"))){
									//									if(genoDataCheck.equals("Char")){
									//-----flank---
									HashMap flankmarkers = new HashMap();
									HashMap monomarkers = new HashMap();
									try {
										monomarkers = rootModel1.getLinkagemap().get(0).getLimsMarkers().get(0).getMonomorphicMarkers();
									} catch (Exception e1) {
										// TODO Auto-generated catch block
									}
									List<String> flankingMarkers = linkageData.getFlankingMarker();
									if(flankingMarkers==null){
										flankingMarkers=new ArrayList<String>();
									}
									if(monomarkers == null){
										monomarkers = new HashMap();
									}
									if(flankmarkers==null){
										flankmarkers = new HashMap();
									}
									flankmarkers = linkageData.getFlankmarkers();
									/*try {
										monomarkers = rootModel1.getLinkagemap().get(0).getLimsMarkers().get(0).getMonomorphicMarkers();
										
										if(!(flankmarkers==null)){
											List fmarkers = new ArrayList();
											
											fmarkers.addAll(flankmarkers.keySet());
											for(int m =0; m<fmarkers.size(); m++){
												String lmarker =  flankmarkers.get(fmarkers.get(m)).toString().substring(0, flankmarkers.get(fmarkers.get(m)).toString().indexOf("!@!"));
												String rmarker =  flankmarkers.get(fmarkers.get(m)).toString().substring(flankmarkers.get(fmarkers.get(m)).toString().indexOf("!@!")+3,flankmarkers.get(fmarkers.get(m)).toString().length());
												if(lMarkers.get(i).equals(lmarker)){
												if(!markers.contains(flankmarkers.get(fmarkers.get(m)).toString().substring(0, flankmarkers.get(fmarkers.get(m)).toString().indexOf("!@!")))){
												  
												 if(monomarkers.get(lmarker).equals("Monomorphic")){
												 for(int f=i-1;;f--){
													 if(rootModel1.getLinkagemap().get(0).getChromosomes().get(f).getChromosome().equals(rootModel1.getLinkagemap().get(0).getChromosomes().get(f-1).getChromosome())){
														if((rootModel1.getGenotype().get(0).getHeaderList().contains(rootModel1.getLinkagemap().get(0).getChromosomes().get(f).getMap_marker()))&&(!(monomarkers.get(rootModel1.getLinkagemap().get(0).getChromosomes().get(f).getMap_marker()).equals("Monomorphic")))){
															markers.add(rootModel1.getLinkagemap().get(0).getChromosomes().get(f).getMap_marker());
															lmarker = rootModel1.getLinkagemap().get(0).getChromosomes().get(f).getMap_marker();
															break;											
														}
														}
												 else{
													 break; 
												 }
												 }
												 }else{
													markers.add(flankmarkers.get(fmarkers.get(m)).toString().substring(0, flankmarkers.get(fmarkers.get(m)).toString().indexOf("!@!")));
													lmarker = flankmarkers.get(fmarkers.get(m)).toString().substring(0, flankmarkers.get(fmarkers.get(m)).toString().indexOf("!@!"));
												 }
												}
												
												if(!markers.contains(flankmarkers.get(fmarkers.get(m)).toString().substring(flankmarkers.get(fmarkers.get(m)).toString().indexOf("!@!")+3,flankmarkers.get(fmarkers.get(m)).toString().length()))){
													 rmarker = flankmarkers.get(fmarkers.get(m)).toString().substring(flankmarkers.get(fmarkers.get(m)).toString().indexOf("!@!")+3,flankmarkers.get(fmarkers.get(m)).toString().length());
													 if(monomarkers.get(rmarker).equals("Monomorphic")){
													 for(int f=i+1;;f++){
														 if(rootModel1.getLinkagemap().get(0).getChromosomes().get(f).getChromosome().equals(rootModel1.getLinkagemap().get(0).getChromosomes().get(f+1).getChromosome())){
															if((rootModel1.getGenotype().get(0).getHeaderList().contains(rootModel1.getLinkagemap().get(0).getChromosomes().get(f).getMap_marker()))&&(!(monomarkers.get(rootModel1.getLinkagemap().get(0).getChromosomes().get(f).getMap_marker()).equals("Monomorphic")))){
																markers.add(rootModel1.getLinkagemap().get(0).getChromosomes().get(f).getMap_marker());
																rmarker = rootModel1.getLinkagemap().get(0).getChromosomes().get(f).getMap_marker();
																break;											
															}
															}else{
																break;
															}
													 }
													 }else{
													markers.add(flankmarkers.get(fmarkers.get(m)).toString().substring(flankmarkers.get(fmarkers.get(m)).toString().indexOf("!@!")+3,flankmarkers.get(fmarkers.get(m)).toString().length()));
													rmarker = flankmarkers.get(fmarkers.get(m)).toString().substring(flankmarkers.get(fmarkers.get(m)).toString().indexOf("!@!")+3,flankmarkers.get(fmarkers.get(m)).toString().length());
													 }
												}
												flankmarkers.put(fmarkers.get(m), lmarker+"!@!"+rmarker);
												linkageData.setFlankmarkers(flankmarkers);
											}
											}
										}
								try {
											String lflank = "", rflank = "";
											List<String> foregroundMarkers = linkageData.getForegroundMarker();
											if(!(foregroundMarkers==null)){
												if(foregroundMarkers.contains(lMarkers.get(i))){
													String mark="";
											try {
												for(int f=i-1;f!=0;f--){
													mark = rootModel1.getLinkagemap().get(0).getChromosomes().get(f).getMap_marker();
												if(rootModel1.getGenotype().get(0).getHeaderList().contains(mark)&&(!foregroundMarkers.contains(mark))){
												if(!(flankingMarkers.contains(mark))){
													flankingMarkers.add(mark);													
													lflank = mark;
													break;											
												}
												if(!markers.contains(mark))
													markers.add(mark);
												}
												}
											} catch (Exception e) {
												// TODO Auto-generated catch block
											}

											 try {
												for(int f=i+1;f!=rootModel1.getLinkagemap().get(0).getChromosomes().size();f++){
													mark = rootModel1.getLinkagemap().get(0).getChromosomes().get(f).getMap_marker();
													if(rootModel1.getGenotype().get(0).getHeaderList().contains(mark)&&(!foregroundMarkers.contains(mark))){
													if(!(flankingMarkers.contains(mark))){
														flankingMarkers.add(mark);														
														rflank = mark;
														break;											
													}
													if(!markers.contains(mark))
														markers.add(mark);
														}
														}
											} catch (Exception e) {
												// TODO Auto-generated catch block
											}
											 flankmarkers.put(lMarkers.get(i), lflank+"!@!"+rflank);
												}
												}
											linkageData.setFlankmarkers(flankmarkers);
										} catch (Exception e) {
											// TODO Auto-generated catch block
										}
									} catch (Exception e1) {
										// TODO Auto-generated catch block
									}*/
									///----flank----
									
									if(label.equals("A")){
										testLbl = "a1";
										
										//-----start flanking markers.....
										
										try {
											flankingMarkers = linkageData.getFlankingMarker();
											List<String> individuals;
											if(!(flankmarkers==null)){
												if((flankingMarkers.contains(lMarkers.get(i)))&&(!monomarkers.get(lMarkers.get(i)).equals("Monomorphic"))){
													flankingcount=rootModel1.getLinkData().getFlankingCount();
													flankingindividuals = rootModel1.getLinkData().getFlankingindividual();
													if(flankingindividuals==null){
														flankingindividuals=new HashMap<String, List<String>>();
													}
//													if(ic<root.getGenotype().get(0).getAccessions().size()){
														if(!(flankingcount==null)){

															if(flankingcount.containsKey(lMarkers.get(i))){
																int bc=(Integer) flankingcount.get(lMarkers.get(i));
//																if((ic<root.getGenotype().get(0).getAccessions().size())&&((!initstr.equals(selAcc))))
																	
																if(flankingindividuals.containsKey(lMarkers.get(i))){
																	individuals = flankingindividuals.get(lMarkers.get(i));
																	if(!individuals.contains(selAcc)){
																	individuals.add(selAcc);
																	flankingcount.put(lMarkers.get(i),bc+1);
																	}
																	flankingindividuals.put(lMarkers.get(i), individuals);
																}else{
																	individuals = new ArrayList<String>();
																	individuals.add(selAcc);
																	flankingindividuals.put(lMarkers.get(i), individuals);
																	flankingcount.put(lMarkers.get(i),bc+1);
																}
															}else{
																flankingcount.put(lMarkers.get(i),1);
																individuals = new ArrayList<String>();
																individuals.add(selAcc);
																flankingindividuals.put(lMarkers.get(i), individuals);

															}
														}else{
															flankingcount = new HashMap();
															flankingcount.put(lMarkers.get(i),1);

															individuals = new ArrayList<String>();
															individuals.add(selAcc);
															flankingindividuals.put(lMarkers.get(i), individuals);
														}
														rootModel1.getLinkData().setFlankingCount(flankingcount);
														rootModel1.getLinkData().setFlankingindividual(flankingindividuals);
//													}
												}
											}
										} catch (RuntimeException e) {
										}
										//----end flanking markers....
									}else if(label.equals("B")){
										testLbl = "a2";
										try {

											List<String> foregroundMarkers = linkageData.getForegroundMarker();
											List<String> individuals;
											if(!(foregroundMarkers==null)){
												if(foregroundMarkers.contains(lMarkers.get(i))){													
													bestcount=rootModel1.getLinkData().getBestcount();
													bestindividuals = rootModel1.getLinkData().getBestindividual();
													if(bestindividuals==null){
														bestindividuals=new HashMap<String, List<String>>();
													}
//													if(ic<root.getGenotype().get(0).getAccessions().size()){
														if(!(bestcount==null)){

															if(bestcount.containsKey(lMarkers.get(i))){
																int bc=(Integer) bestcount.get(lMarkers.get(i));
																if((ic<root.getGenotype().get(0).getAccessions().size())&&((!initstr.equals(selAcc))))
																	bestcount.put(lMarkers.get(i),bc+1);
																if(bestindividuals.containsKey(lMarkers.get(i))){
																	individuals = bestindividuals.get(lMarkers.get(i));
																	if(!individuals.contains(selAcc))
																	individuals.add(selAcc);
																	bestindividuals.put(lMarkers.get(i), individuals);
																}else{
																	individuals = new ArrayList<String>();
																	individuals.add(selAcc);
																	bestindividuals.put(lMarkers.get(i), individuals);
																}
															}else{
																bestcount.put(lMarkers.get(i),1);
																individuals = new ArrayList<String>();
																individuals.add(selAcc);
																bestindividuals.put(lMarkers.get(i), individuals);

															}
														}else{
															bestcount = new HashMap();
															bestcount.put(lMarkers.get(i),1);

															individuals = new ArrayList<String>();
															individuals.add(selAcc);
															bestindividuals.put(lMarkers.get(i), individuals);
														}
														rootModel1.getLinkData().setBestcount(bestcount);
														rootModel1.getLinkData().setBestindividual(bestindividuals);
//													}
												}
											}										
										} catch (RuntimeException e) {
										}

									}else if(label.equals("H")) {
										testLbl = "a3";
										try {

											List<String> foregroundMarkers = linkageData.getForegroundMarker();
											List<String> individuals;
											if(!(foregroundMarkers==null)){
												if(foregroundMarkers.contains(lMarkers.get(i))){
													bestcount=rootModel1.getLinkData().getBestcount();
													bestindividuals = rootModel1.getLinkData().getBestindividual();
													if(bestindividuals==null){
														bestindividuals=new HashMap<String, List<String>>();
													}
//													if(ic<root.getGenotype().get(0).getAccessions().size()){
														if(!(bestcount==null)){

															if(bestcount.containsKey(lMarkers.get(i))){
																int bc=(Integer) bestcount.get(lMarkers.get(i));
																if((ic<root.getGenotype().get(0).getAccessions().size())&&((!initstr.equals(selAcc))))
																	bestcount.put(lMarkers.get(i),bc+1);
																if(bestindividuals.containsKey(lMarkers.get(i))){
																	individuals = bestindividuals.get(lMarkers.get(i));
																	if(!individuals.contains(selAcc))
																	individuals.add(selAcc);
																	bestindividuals.put(lMarkers.get(i), individuals);
																}else{
																	individuals = new ArrayList<String>();
																	individuals.add(selAcc);
																	bestindividuals.put(lMarkers.get(i), individuals);
																}
															}else{
																bestcount.put(lMarkers.get(i),1);
																individuals = new ArrayList<String>();
																individuals.add(selAcc);
																bestindividuals.put(lMarkers.get(i), individuals);

															}
														}else{
															bestcount = new HashMap();
															bestcount.put(lMarkers.get(i),1);

															individuals = new ArrayList<String>();
															individuals.add(selAcc);
															bestindividuals.put(lMarkers.get(i), individuals);
														}
														rootModel1.getLinkData().setBestcount(bestcount);
														rootModel1.getLinkData().setBestindividual(bestindividuals);
//													}
												}
											}
										} catch (RuntimeException e) {
										}
									}else if(label.equals("C")) {
										testLbl = "a4";
									}else if(label.equals("D")) {
										testLbl = "a5";
									}else if(label.equals("a50")) {
										testLbl = "a50";
										try {

											List<String> foregroundMarkers = linkageData.getForegroundMarker();
											List<String> individuals;
											if(!(foregroundMarkers==null)){
												if(foregroundMarkers.contains(lMarkers.get(i))){
													missingcount=rootModel1.getLinkData().getMissingCount();
													missingindividuals = rootModel1.getLinkData().getMissingindividual();
													if(missingindividuals==null){
														missingindividuals=new HashMap<String, List<String>>();
													}
//													if(ic<root.getGenotype().get(0).getAccessions().size()){
														if(!(missingcount==null)){

															if(missingcount.containsKey(lMarkers.get(i))){
																int bc=(Integer) missingcount.get(lMarkers.get(i));
//																if((ic<root.getGenotype().get(0).getAccessions().size())&&((!initstr.equals(selAcc))))
																	
																if(missingindividuals.containsKey(lMarkers.get(i))){
																	individuals = missingindividuals.get(lMarkers.get(i));
																	if(!individuals.contains(selAcc)){
																	individuals.add(selAcc);
																	missingcount.put(lMarkers.get(i),bc+1);
																	}
																	missingindividuals.put(lMarkers.get(i), individuals);
																}else{
																	individuals = new ArrayList<String>();
																	individuals.add(selAcc);
																	missingindividuals.put(lMarkers.get(i), individuals);
																	missingcount.put(lMarkers.get(i),bc+1);
																}
															}else{
																missingcount.put(lMarkers.get(i),1);
																individuals = new ArrayList<String>();
																individuals.add(selAcc);
																missingindividuals.put(lMarkers.get(i), individuals);

															}
														}else{
															missingcount = new HashMap();
															missingcount.put(lMarkers.get(i),1);

															individuals = new ArrayList<String>();
															individuals.add(selAcc);
															missingindividuals.put(lMarkers.get(i), individuals);
														}
														rootModel1.getLinkData().setMissingCount(missingcount);
														rootModel1.getLinkData().setMissingindividual(missingindividuals);
//													}
												}
											}
										} catch (RuntimeException e) {
										}

									}
									else if(label.equals("amono")) {
										testLbl = "amono";
									}
									label = testLbl;
									//-----start for monomorphic markers in 2nd generation-----

									try{
										monomarkers=new HashMap();
										monomarkers=rootModel1.getLinkagemap().get(0).getLimsMarkers().get(0).getMonomorphicMarkers();
										if(!(monomarkers==null)){
											if(monomarkers.get(lMarkers.get(i)).equals("Monomorphic")){
												label = "amono";
											}
										}

									}catch(Exception e){
									}


									//----------end-----
								}else{
									if((genoDataCheck.equals("Char"))||(linkageData.isDart())){
										if(combiAllele.contains("-")){
											label = "a50";
										}else{
										label = "a"+alleleLabel.get(combiAllele);
										}

										labelAllele.remove(labelAllele.size()-1);
										labelAllele.add(label);

									}
								}

								float midCumDist = (cumDist+prevCumDist)/2;
								if(cumDist == 0) {
									midCumDist= cumDist;
								}
								float width= ((cumDist+(cumDist+markpos1))/2)-midCumDist;
//								float width= ((cumDist+(cumDist+pwidth))/2)-midCumDist;
								if(linkageData.isCview()){
									try{
										midCumDist = midCumDist*2.5f;
										width = width*2.5f;
									}catch(Exception e){
									}
								}
								markerPrevPos.add(Math.round(midCumDist));
								
								markerNextPos.add((int)width);
								
								
								Rectangle position = new Rectangle(Math.round((midCumDist*scale))+start+70, yValue+72+qtlYValue, (int)(width*scale), 6);;

								//----start--foreground back ground markers---
								try{
									HashMap mstatus=new HashMap();								
									mstatus=rootModel1.getLinkagemap().get(0).getChromosomes().get(0).getMstatus();

									if(!(mstatus==null)){


										if(!(mstatus.get(lMarkers.get(i))==null)){
											if(mstatus.get(lMarkers.get(i)).equals("Foreground")){
												//												System.out.println(lMarkers.get(i)+".....fore...");
												position= new Rectangle(Math.round((midCumDist*scale))+start+70, yValue+70+qtlYValue,(int)(width*scale), 12);
											}else{
												position= new Rectangle(Math.round((midCumDist*scale))+start+70, yValue+72+qtlYValue, (int)(width*scale), 6);
											}
										}else{
											position= new Rectangle(Math.round((midCumDist*scale))+start+70, yValue+72+qtlYValue, (int)(width*scale), 6);
										}
									}else{
										position= new Rectangle(Math.round((midCumDist*scale))+start+70, yValue+72+qtlYValue, (int)(width*scale), 6);
									}

									ir = i;
									midCumDist1 = Math.round(midCumDist);
									width1 = (int)(width*scale);

								}catch(Exception e){
								}

								//-------end---fore ground back ground markers----

								String key =lMarkers.get(i)+"$"+selAcc;
								tooltip.put(key, combiAllele);

								/*fig2.setBounds(position);
								if(label.equals("amono")){
									fig2.setToolTip(new Label(lMarkers.get(i)+": Monomorphic"));
								}else{
									fig2.setToolTip(new Label(lMarkers.get(i)+": "+combiAllele));
								}
*/
								if(isHeterozygous()){
									if(label.contains("a50")){
										label="a50";
										assignColorsToAllels(label, fig2);
										fig.add(fig2);
										setHeterozygous(false);
									}else{
										Rectangle hetero1 = null;
										if(Integer.parseInt(allelicValues.get(k))<Integer.parseInt(allelicValues.get(k+1))){
										if(position.height == 6){
											hetero1= new Rectangle(Math.round((midCumDist*scale))+start+70, yValue+72+qtlYValue, (int)(width*scale), 4);
										}else{
											hetero1= new Rectangle(Math.round((midCumDist*scale))+start+70, yValue+72+qtlYValue-3, (int)(width*scale), 7);	
										}
										fig2.setBounds(hetero1);
										fig2.setToolTip(new Label(lMarkers.get(i)+" : "+allelicValues.get(k)));
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
										fig2.setToolTip(new Label(lMarkers.get(i)+" : "+allelicValues.get(k+1)));
										//									fig2.setBackgroundColor(ColorConstants.darkGreen);
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
											fig2.setToolTip(new Label(lMarkers.get(i)+" : "+allelicValues.get(k)));
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
											fig2.setToolTip(new Label(lMarkers.get(i)+" : "+allelicValues.get(k+1)));
											//									fig2.setBackgroundColor(ColorConstants.darkGreen);
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
									
									fig2.setBounds(position);
									if(label.equals("amono")){
										fig2.setToolTip(new Label(lMarkers.get(i)+": Monomorphic"));
									}else{
										fig2.setToolTip(new Label(lMarkers.get(i)+": "+combiAllele));
									}

									
									
									assignColorsToAllels(label, fig2);
									fig.add(fig2);}


							}catch(Exception e) {
							}
							if(rootModel1.getGeneration().equals("Second")){
								//								if(genoDataCheck.equals("Char")){
							}else{
								k=k+1;
							}
							
							break;
						}
					}//------for loop end---
					
					if(start1 == true &&(markerPositions.get(i).equals("0.2") )){
						markerPositions.set(i,"0");
						first = false;
					}
				}//----if loop end---
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
			linkageData.setAccallellic(accAllele);
			linkageData.setMarkallelic(markerAllele);
			linkageData.setLabelallelic(labelAllele);
			linkageData.setTooltip(tooltip);
			try {
				if(!(marker_of_intrest.equals(null))) {
					for(int h=0; h<accAllele.size(); h++) {
						if((accAllele.get(h)).equals(accAllele.get(h+1))) {
							if((markerAllele.get(h)).equals(marker_of_intrest)) {
								accessions.add(accAllele.get(h));
								label1.add(labelAllele.get(h));
							}
						}
					}
				}
			} catch(Exception p) {
			}
			if(((k/linkageCount)+1)==gM.getCount()) {
				final Integer ONE = new Integer(1);    
				Map<String, Integer> m = new LinkedHashMap<String, Integer>(); 
				Map<String, String> b = new LinkedHashMap<String, String>();
				Map c = new LinkedHashMap(); 
				// for counting similar label count like m(a1=4, a2=5, a4=2);
				// getting which acc id having label like b(icvf=a1,.....);
				for(int k=0;k<accessions.size();k++) {
					Integer freq = (Integer) m.get(label1.elementAt(k)); 
					m.put(label1.get(k),(freq==null ? ONE :new Integer(freq.intValue() + 1)));
					b.put(accessions.get(k),label1.elementAt(k));
				}
				LinkedList acclist = new LinkedList();
				LinkedList acclist1 = new LinkedList();
				LinkedList acclist2 = new LinkedList();

				// dividing accessions and labels into separate vectors
				Iterator accession_key = (b.keySet()).iterator();
				Iterator accession_val = (b.values()).iterator();
				while(accession_key.hasNext()&& accession_val.hasNext()){
					Object acckey=accession_key.next();
					Object accval=accession_val.next();
					acclist1.add(acckey);
					acclist2.add(accval);
				}

				// sorting the label-count map
				List mapKeys = new ArrayList(m.keySet());
				List mapValues = new ArrayList(m.values());
				Collections.sort(mapValues); 
				Collections.sort(mapKeys); 

				LinkedHashMap someMap = new LinkedHashMap();
				Iterator valueIt = mapValues.iterator();
				while (valueIt.hasNext()) {
					Object val = valueIt.next();
					Iterator keyIt = mapKeys.iterator();
					while(keyIt.hasNext()) {
						Object key = keyIt.next();
						if (m.get(key).toString().equals(val.toString())) {
							m.remove(key);
							mapKeys.remove(key);
							someMap.put(key, val);
							acclist.add(key);
							break;
						}
					}
				}

				// for preparing a pair contains accession and label in a sorting order of 
				// color labels at particular marker of interest
				int size=(someMap.keySet()).size();
				for(int p1=0; p1<size; p1++){
					for(int o=0; o<acclist1.size(); o++){
						if((acclist.get(p1)).equals(acclist2.get(o))){
							c.put(acclist1.get(o),acclist2.get(o));
							list.add((String) acclist1.get(o));
						}
					}
				}
			}
			linkageData.setSortedAccessions(list);
			start= start+end+30;
			prevSelAcc= selAcc;
			k++;
			RootModel rootModel = Session.getInstance().getRootmodel();
			rootModel.setAccession(accAllele);
			rootModel.setType(linkageData.getType());
			//			System.out.println("root"+type);
			rootModel.setChrNo(chromosomeName);
			rootModel.setMarkerName(markerAllele);

			rootModel.setMarkerPosition(markerPos);
			rootModel.setMarkerPrevPos(markerPrevPos);
			rootModel.setMarkerNextPos(markerNextPos);

			rootModel.setAccAllele(valueAllele);
			rootModel.setLabel(labelAllele);

//			System.out.println("markerAlleleColor....."+markerAlleleColor);



			try {
				for(int j=0; j<maxAlleleValue.size(); j++) {
					if(maxAlleleValue.get(j)>max) {
						max= maxAlleleValue.get(j);
					}
				}
				//				System.out.println("Maximum Allele= "+max);
				LinkageData lk= LinkageData.getLinkageData();
				lk.setMaximumAllele(max); 
			}catch(Exception e){
			}
			prevCount= gM.getCount();
			//gM.setEP(getParent().getChildren());
		}catch(Exception e){
		}
		/*if(linkageData.isCview()==true){
			try {
				fig.setBounds(new Rectangle(Math.round(fig.getBounds().x*2.5f), fig.getBounds().y, Math.round(fig.getBounds().width*2.5f), fig.getBounds().height));
				
			}catch(Exception e){
			}
		}*/
		
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
		color = new Color(null, color1 , color2 ,(color3));
		figure.setBackgroundColor(color);
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