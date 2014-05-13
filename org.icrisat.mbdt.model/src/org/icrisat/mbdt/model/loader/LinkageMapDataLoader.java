package org.icrisat.mbdt.model.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.StringTokenizer;

import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.Chromosome.Chromosome;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.LinkageMapModel.Intervals;
import org.icrisat.mbdt.model.LinkageMapModel.LimsMarkers;
import org.icrisat.mbdt.model.LinkageMapModel.LinkageMap;
import org.icrisat.mbdt.model.LinkageMapModel.LinkageScaleModel;
import org.icrisat.mbdt.model.LinkageMapModel.MapMarkers;
import org.icrisat.mbdt.model.LinkageMapModel.MapMarkersScale;
import org.icrisat.mbdt.model.LinkageMapModel.MarkerPosition;
import org.icrisat.mbdt.model.LinkageMapModel.MarkerPositionScale;
import org.icrisat.mbdt.model.sessions.SessionChromosome;

public class LinkageMapDataLoader {
	
	List df=new ArrayList();
	List df1=new ArrayList();
	List df2=new ArrayList();
	List df3=new ArrayList();
	List cum_dist = new ArrayList();
	List scale_distance = new ArrayList();
	List scale_cm = new ArrayList();
	List scaleChom_start = new ArrayList();
	
	LinkedHashSet countchrome=new LinkedHashSet();
	List<String> duppChromeList = new ArrayList<String>();
 
	static String tempGeno="";
	boolean isfinal =false;
    static String tempCum=""; 
    static float cumDist = 0.0f;
    int dist=0;
    int previous_dis=0;
    float idealLength = 200.0f;
    float scale = 0.0f;
    
    static int lcount = 1;
    HashMap linkage = new HashMap();
	public RootModel load(String filePath) {
		
		LinkageMap lMap= new LinkageMap();
		
		// Get a singleton
		RootModel rootModel = RootModel.getRootModel();
		Chromosome chrom = Chromosome.getChromosome();
		try {
			FileReader fr= new FileReader(new File(filePath));
			BufferedReader br= new BufferedReader(fr);
			tempGeno=rootModel.getLinkageMap_Chromfirst();
			String str;
			int countj=0;
			LinkageData linkData= LinkageData.getLinkageData();
			Qtl_MapData qtl_MapData= Qtl_MapData.getQtl_MapData();
			LinkageScaleModel linkScale = new LinkageScaleModel();
			String prevChromName = "";
			MapMarkersScale mark_scale = new MapMarkersScale();
			MarkerPositionScale markpo_scale = new MarkerPositionScale();
			while((str= br.readLine())!= null) {
				
				Intervals chrome= new Intervals();
				MapMarkers mark= new MapMarkers();				
				MarkerPosition markpo= new MarkerPosition();
				
                LimsMarkers limsmarker= new LimsMarkers();
                
				StringTokenizer st1= new StringTokenizer(str, "\t");
				int count= st1.countTokens();
				int i= 0;
				
				while(st1.hasMoreTokens()) {
				
					String geno= st1.nextToken();
					switch(i) {
					case 0:
						if(!geno.equals(null)) {

							if(!(geno.equals(tempGeno)))
							{
								isfinal=true;
							}
							tempGeno=geno;
							
						chrome.setChromosome(geno);
						countchrome.add(geno);
						markpo.setChromoname(geno);
						df3.add(geno);
						}
						break;
					case 1:
						if(!geno.equals(null)) {
						chrome.setMap_marker(geno);
						linkData.setMarkerName(geno);
						limsmarker.setMarkers(geno);
						df.add(geno);
						mark.setName(geno);
						}
						break;
					case 2:
						if(!geno.equals(null)) {
						chrome.setNumbering(geno);
						}
						break;
					case 3:
						if(!geno.equals(null)) {
						df2.add(geno);
						chrome.setDistance(geno);

						markpo.setDistance(geno);
//						markpo_scale.setDistance(geno);
						mark.setDistance(geno);
						}
						break;
					case 4:
						if(!geno.equals(null)) {
							
							if(isfinal ==true)
							{
								markpo_scale.setDistance(tempCum);
								cum_dist.add(tempCum);
								isfinal=false;
								if(cumDist < Float.parseFloat(tempCum)){
									cumDist = Float.parseFloat(tempCum);
									linkData.setScale(idealLength/cumDist);
//									linkData.setScale(10);
								}
								
							}
						df1.add(geno);
						chrome.setMarkerposition(geno);
						markpo.setMarkerPosition(geno);
						
						mark.setMarkerPosition(geno);
						tempCum=geno;
						}
						break;
					case 5:
						chrome.setForestatus("Background");
						break;
					default :
						break;
					} i++;
				}
				
				if(!(chrome.getChromosome().equals(prevChromName))){
					duppChromeList.add(chrome.getChromosome());
					linkage.put(chrome.getChromosome(), (lcount+"@@"+(lcount-1)));
					lcount++;
				}
				prevChromName = chrome.getChromosome();
				
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
				qtl_MapData.setQtlLinkChrom(duppChromeList);
				lMap.getChromosomes().add(chrome);

				chrome.getMarkpositions().add(markpo);
				chrome.getMarkers().add(mark);
				
				
				lMap.getLimsMarkers().add(limsmarker);

			}
			cum_dist.add(tempCum);
			
			scaleChom_start.add(0);
			scale = linkData.getScale();
			HashMap chromPos = new HashMap();
			for(int i =0;i<cum_dist.size();i++)
			{
				Float cumudistance =Float.parseFloat((String) cum_dist.get(i));
				
//				int cumdis =Math.round(cumudistance);
				
				
				float temp =cumudistance*scale;
                int cumdis= (int)(Math.round(temp));
				 
				int Postion_cm=0;
				cumdis=cumdis+previous_dis; 
				for(;dist<cumdis;)
				{
					mark_scale.setDistance(Integer.toString(dist));
					scale_distance.add(dist);
					scale_cm.add(Postion_cm);
					
					float  temp1=25*scale;
					int sample	=(int)(Math.round(temp1));
					Postion_cm=Postion_cm+25;
					dist=dist+sample;
					markpo_scale.setDistance(Integer.toString(dist));
					linkScale.getMarkpositionsScale().add(markpo_scale);
					linkScale.getMarkersScale().add(mark_scale);
					lMap.getLinkageScale().add(linkScale);
				}
				mark_scale.setDistance(Integer.toString(cumdis));
				scale_cm.add(Math.round(cumudistance));
			
				scale_distance.add(cumdis);
				dist=cumdis+30;
				scaleChom_start.add(dist);
				markpo_scale.setDistance(Integer.toString(dist));
				linkScale.getMarkpositionsScale().add(markpo_scale);
				linkScale.getMarkersScale().add(mark_scale);
				lMap.getLinkageScale().add(linkScale);
				previous_dis=cumdis+30;
			}
			for(int i = 0; i<scaleChom_start.size()-1;i++){
				
				chromPos.put(duppChromeList.get(i), scaleChom_start.get(i));
				
			}
			//----start...... unlink markers......
			
			for(int i=0;i<rootModel.getGenotype().get(0).getHeaderList().size();i++){
				if(!lMap.getLimsMarkers().contains(rootModel.getGenotype().get(0).getHeaderList().get(i))){
					
				}
					
			}
			
			
			//-----end.....unlink markers.......
			
			linkData.setCumlativeDistance(cum_dist);
			linkData.setCount(""+countchrome.size());
			linkData.setScalePositions(scale_distance);
			linkData.setScaleCm(scale_cm);
			linkData.setScaleChom_start(scaleChom_start);
			linkData.setChromPos(chromPos);
			linkData.setChromlist(duppChromeList);
			linkData.setLinkage(linkage);
			rootModel.getLinkagemap().add(lMap);
			chrom.getLinkagemap().add(lMap);
			SessionChromosome.getInstance().setChromosome(chrom);
		}catch(Exception e) {
		}
		return rootModel;	
	}
}
