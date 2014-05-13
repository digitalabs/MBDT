package org.icrisat.mbdt.model.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.StringTokenizer;

import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.LinkageMapModel.Intervals;
import org.icrisat.mbdt.model.LinkageMapModel.LimsMarkers;
import org.icrisat.mbdt.model.LinkageMapModel.LinkageMap;
import org.icrisat.mbdt.model.LinkageMapModel.MapMarkers;
import org.icrisat.mbdt.model.LinkageMapModel.MarkerPosition;

public class LinkageMapDataLoader1 {

	LinkedHashSet countchrome= new LinkedHashSet();
	List df= new ArrayList();
	List df1= new ArrayList();
	List df2= new ArrayList();
	List df3= new ArrayList();
	int flag= 0;
	
	public RootModel load(String filePath) {
		
		// Get a singleton
		RootModel rootModel= RootModel.getRootModel();
		try {
			FileReader fr= new FileReader(new File(filePath));
			BufferedReader br= new BufferedReader(fr);
			String str;
			
//			System.out.println("linkData= "+br);
			
			while((str= br.readLine())!= null) {
				
				int count= 0;
				if(str.contains("group") && str.contains("=")) {
					flag= 1;
					StringTokenizer st2= new StringTokenizer(str, "p");
//					System.out.println("Tokens= "+st2.countTokens());
//					count= st2.countTokens();
//					System.out.println("Count= "+str);
//					chrome.setChromosome(countStri);
//					markpo.setChromoname(countStri);
//					df3.add(countStri);
				}
				if(str.contains("QTLs ;")) {
					flag=0;
				}
				StringTokenizer st1= new StringTokenizer(str, "\t");
				int i=0;
				if(flag== 1) {
					LinkageMap lMap= new LinkageMap();
					Qtl_MapData qtl_MapData =Qtl_MapData.getQtl_MapData();
					LinkageData linkData=LinkageData.getLinkageData();
					Intervals chrome=new Intervals();
					MapMarkers mark=new MapMarkers();
					MarkerPosition markpo=new MarkerPosition();
					LimsMarkers limsmarker = new LimsMarkers();
					while(st1.hasMoreTokens()) {
						String geno= st1.nextToken();
						switch(i) {
						case 0:
							chrome.setChromosome(geno);
							countchrome.add(geno);
							markpo.setChromoname(geno);
							df3.add(geno);
							break;
						case 1:					
							chrome.setMap_marker(geno);
							linkData.setMarkerName(geno);
							limsmarker.setMarkers(geno);
							df.add(geno);
							mark.setName(geno);
							break;
						case 2:
							chrome.setNumbering(geno);
							break;
						case 3:
							df2.add(geno);
							chrome.setDistance(geno);
							markpo.setDistance(geno);
							mark.setDistance(geno);
							break;
						case 4:
							df1.add(geno);
							chrome.setMarkerposition(geno);
							markpo.setMarkerPosition(geno);
							mark.setMarkerPosition(geno);
							//markerPosition.setMarkerPosition(geno);
							break;
						case 5:
							chrome.setForestatus("Background");
							break;
						default :
							break;
						} i++;
					}
					chrome.setCount(count);
					chrome.setMarker(df);
					chrome.setForestatus("Background");
					
					linkData.setMarker(df);
					linkData.setMarkerPositions(df1);
					linkData.setDistances(df2);
					linkData.setChromName(df3);
					qtl_MapData.setDistances(df2);
					qtl_MapData.setMarkerPositions(df1);
					qtl_MapData.setChromosome(df3);
					lMap.getChromosomes().add(chrome);
					chrome.getMarkpositions().add(markpo);
					chrome.getMarkers().add(mark);
					lMap.getLimsMarkers().add(limsmarker);
					rootModel.getLinkagemap().add(lMap);
				}
			}
		}catch(Exception e) {
		}
		return rootModel;	
	}
}