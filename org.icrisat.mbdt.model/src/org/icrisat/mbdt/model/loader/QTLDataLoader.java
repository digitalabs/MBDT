package org.icrisat.mbdt.model.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.Chromosome.Chromosome;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.QTLModel.QTL;
import org.icrisat.mbdt.model.QTLModel.QTLData;
import org.icrisat.mbdt.model.QTLModel.QTLPeakPoints;
import org.icrisat.mbdt.model.QTLModel.QTLPreviousData;
import org.icrisat.mbdt.model.sessions.SessionChromosome;

public class QTLDataLoader {
	
	public RootModel load(String filePath){
		QTL qtl = new QTL();
		QTLPreviousData qtlPrev = new QTLPreviousData();
		Qtl_MapData qtl_MapData = Qtl_MapData.getQtl_MapData();
		LinkageData linkData= LinkageData.getLinkageData();
		
//		QTLGrayLines qtlGray = new QTLGrayLines();
		
		// Get a singleton
		RootModel rootModel = RootModel.getRootModel();
		Chromosome chrom = Chromosome.getChromosome();
		String qtlinfo = "";
		String qtlInfosplited = "";
		int groupCount = 0;
		ArrayList<String> EnviName = new ArrayList<String>();
		ArrayList<String> TraitName = new ArrayList<String>();
		
		String prevGroupName = "";
		ArrayList<String> qtlChromList = new ArrayList<String>();
		ArrayList<String> qtlChromList1 = new ArrayList<String>();
		int defaultTraitCount = 3;
		int defaultEnviCount = 1;
		int noOfTraits = 0;
		int QtlYValue = 0;
		int QtlYValueForOthers = 0;
		int noOfEnvironments = 0;
		
		try {
			FileReader fr = new FileReader(new File(filePath));
			BufferedReader br = new BufferedReader(fr);
			
			//qtlinfo = br.readLine();
			qtlinfo = qtlinfo + br.readLine();
			//String str = br.readLine();
			String str = "";
			String fileContent= "";
			int count = 0;
			
			if(qtlinfo.contains("; ")){
				qtlInfosplited = qtlinfo.substring(30);
			}
			
			qtl.setQtlInfo(qtlInfosplited);
			while((str = br.readLine()) != null){
				QTLData qtlData = new QTLData();
				QTLPeakPoints qtlPeakPoint = new QTLPeakPoints();
				
				qtl.getQtlData().add(qtlData);
				rootModel.getQtl().add(qtl);
				chrom.getQtl().add(qtl);
				SessionChromosome.getInstance().setChromosome(chrom);
				qtlData.setQtlPrevStartPt(qtlPrev.getQtlPrevStart());
				qtlData.setQtlPrevEndPt(qtlPrev.getQtlPrevEnd());
				qtlData.setPrevGroupCount(qtlPrev.getQtlPrevgroupCount());
								
						
						StringTokenizer st = new StringTokenizer(str , "	");
						int i=0;
						
						while(st.hasMoreTokens()){
							
							switch (i) {
							case 0:								
								qtlData.setQtlNames(st.nextToken());
								break;
								
							case 1:								
								qtlData.setQtlChromNames(st.nextToken());
								break;
							case 2:
								qtlPeakPoint.setQtlPeakPoints(st.nextToken());
								break;
								
							case 3:
								qtlData.setQtlStartPt(st.nextToken());
								break;
								
							case 4:
								qtlData.setQtlEndPt(st.nextToken());
								break;
								
							case 5:
								qtlData.setQtltraitName(st.nextToken());
								break;
								
							case 6:
								qtlData.setQtlEnviName(st.nextToken());
								break;
								
							case 7:
								String pp = st.nextToken();
								break;
								
							case 8:
								String pp1 = st.nextToken();
								break;
								
							case 9:
								String pp2 = st.nextToken();
								break;
								
							case 10:
								String pp3 = st.nextToken();
								break;
								
							case 11:
								String pp4 = st.nextToken();
								break;
								
							case 12:
								qtlData.setQtlAddEffects(st.nextToken());
								break;
								
							case 13:
								
								qtlData.setQtlLodsqr(st.nextToken());
								break;
								
							case 14:
								qtlData.setQtlRsqr(st.nextToken());
								break;
							
							default:
								break;
							
							}i++;												
						}
						String gcount = (String)linkData.getLinkage().get(qtlData.getQtlChromNames());
						
						if(qtlData.getQtlChromNames() != null){
							
							if(!(qtlData.getQtlChromNames().equals(prevGroupName))){
								qtlChromList.add(qtlData.getQtlChromNames());
								qtlChromList1.add(qtlData.getQtlChromNames());
//								groupCount++;
								
								
								groupCount = Integer.parseInt(gcount.substring(0,gcount.indexOf("@@")));
								
							}
							prevGroupName = qtlData.getQtlChromNames();							
								
						}
						//Setting GroupCount...
						qtlData.setGroupCount(groupCount);
						qtlData.setPrevGroupCount(Integer.parseInt(gcount.substring(gcount.indexOf("@@")+2, gcount.length())));
						qtlData.getQtlPeakPoints().add(qtlPeakPoint);
						qtlData.setQtlChromList(qtlChromList);
						qtlData.setQtlModChromList(qtlChromList1);
						
						if(EnviName != null){
							if(!EnviName.contains(qtlData.getQtlEnviName())){
								EnviName.add(qtlData.getQtlEnviName());
							}
						}
						
						if(TraitName != null){
							if(!TraitName.contains(qtlData.getQtltraitName())){
								TraitName.add(qtlData.getQtltraitName());
							}
						}
						qtl.setQtlEnviListFromLoader(EnviName);
						qtl.setQtlTraitListFromLoader(TraitName);
						qtlData.setGroupCount(groupCount);
						qtlPrev.setQtlPrevgroupCount(groupCount);
					}
			
			/*** 17th NOV 2009 
			 * In this Loader we are setting the QTL Yvalue's and
			 * YValue for Linkage and HeatMap beco'z depending on
			 * the position of QTL we are setting the positions of
			 * Heat Map and Linkage Map..... 
			 ****/
			
			if(qtl.getQtlTraitListFromLoader().size() > 3){
				noOfTraits = qtl.getQtlTraitListFromLoader().size() - defaultTraitCount;
				noOfEnvironments = qtl.getQtlEnviListFromLoader().size() - defaultEnviCount; 
				
//				QtlYValue =  10 + (10 * noOfTraits);
				QtlYValue =  10 + (10 * noOfTraits * noOfEnvironments);
				
				if(qtl.getQtlEnviListFromLoader().size() > 1){
//					QtlYValueForOthers = 10 + (20 * noOfTraits);
					QtlYValueForOthers = 10 + (20 * noOfTraits) + ((10 * noOfTraits) * noOfEnvironments);
					
				}else{
					QtlYValueForOthers = 10 + (10 * noOfTraits);
				}
			}else{
				if(qtl.getQtlEnviListFromLoader().size() > 1){
					QtlYValue =  10 + 10;
				}else{
					QtlYValue =  10;
				}
				
				QtlYValueForOthers = 10;
			}
			
			qtl_MapData.setQtlYValue(QtlYValue);
			qtl_MapData.setQtlYValueForOthers(QtlYValueForOthers);
			
		} catch (FileNotFoundException e) {		
		} catch (IOException e) {
		}		
		return rootModel;
	}

}
