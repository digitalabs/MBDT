	package org.icrisat.mbdt.model.loader;

	import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.Platform;
import org.generationcp.middleware.manager.Database;
import org.generationcp.middleware.manager.DatabaseConnectionParameters;
import org.generationcp.middleware.manager.ManagerFactory;
import org.generationcp.middleware.manager.api.GenotypicDataManager;
import org.generationcp.middleware.pojos.gdms.MapInfo;
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

	public class LinkageMapDatasetLoader {
		
		List df=new ArrayList();
		List df1=new ArrayList();
		List<String> df2=new ArrayList<String>();
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
	    DatabaseConnectionParameters local, central;  
		ManagerFactory factory;
		GenotypicDataManager manager;
		List<MapInfo> mapinfo = new ArrayList<MapInfo>();
		int number = 0;
		float pos =0.0f; 
		public RootModel load(String filePath) {
			
			LinkageMap lMap= new LinkageMap();
			
			// Get a singleton
			RootModel rootModel = RootModel.getRootModel();
			Chromosome chrom = Chromosome.getChromosome();
			try {
//				local = new DatabaseConnectionParameters("localhost", "13306", "ibdbv2_groundnut_central", "root", "");
//				central = new DatabaseConnectionParameters("localhost", "13306", "ibdbv2_groundnut_central", "root", "");

//				local = new DatabaseConnectionParameters("localhost", "13306", "ibdbv2_cowpea_2_local", "root", "");
//				central = new DatabaseConnectionParameters("localhost", "13306", "ibdbv2_cowpea_central", "root", "");
				String url = Platform.getLocation().toString().substring(0, Platform.getLocation().toString().lastIndexOf("/")+1);
				 local = new DatabaseConnectionParameters(url+"DatabaseConfig.properties","local");
				 central = new DatabaseConnectionParameters(url+"DatabaseConfig.properties","central");
				
				factory = new ManagerFactory(local, central);
				manager=factory.getGenotypicDataManager();
				filePath = filePath.substring(0, filePath.lastIndexOf(" ("));
				mapinfo = manager.getMapInfoByMapName(filePath,  Database.CENTRAL);
				String str;
				int countj=0;
				LinkageData linkData= LinkageData.getLinkageData();
				Qtl_MapData qtl_MapData= Qtl_MapData.getQtl_MapData();
				LinkageScaleModel linkScale = new LinkageScaleModel();
				String prevChromName = "";
				MapMarkersScale mark_scale = new MapMarkersScale();
				MarkerPositionScale markpo_scale = new MarkerPositionScale();
				
//				while((str= br.readLine())!= null) {
				String lg,marker;
				int position=0;
				 float id = 0.0f,cd = 0.0f, tempdis = 0.0f;
					for( int m=0; m< mapinfo.size(); m++){
					Intervals chrome= new Intervals();
					MapMarkers mark= new MapMarkers();				
					MarkerPosition markpo= new MarkerPosition();
					
	                LimsMarkers limsmarker= new LimsMarkers();
	               lg = mapinfo.get(m).getLinkageGroup();
	               marker = mapinfo.get(m).getMarkerName();
	                cd = mapinfo.get(m).getStartPosition();
	                
	                //-----chromosome name---
	                if((!(lg.equals(tempGeno)))&&(m!=0))
					{
						isfinal=true;
						position =0;
					}
					tempGeno=lg;
					
				chrome.setChromosome(lg);
				countchrome.add(lg);
				markpo.setChromoname(lg);
				df3.add(lg);
					
				//-----------Marker name---
				if(!marker.equals(null)) {
					chrome.setMap_marker(marker);
					linkData.setMarkerName(marker);
					limsmarker.setMarkers(marker);
					df.add(marker);
					mark.setName(marker);
					}
				//---------postion-----
				chrome.setNumbering(String.valueOf(position));
				if(position == 0){
					id = 0.0f;
				}else{
					id =cd - tempdis;
					DecimalFormat form = new DecimalFormat("0.00"); 
					id = Float.parseFloat(form.format(id));
				}
				position++;
				//--------intermarker dist and cumulative dist
				
					
				
				String itemp = String.valueOf(id);
				df2.add(itemp);
				chrome.setDistance(String.valueOf(id));

				markpo.setDistance(String.valueOf(id));
				mark.setDistance(String.valueOf(id));
				
					
					if(isfinal ==true)
					{
						markpo_scale.setDistance(tempCum);
						cum_dist.add(tempCum);
						isfinal=false;
						if(cumDist < Float.parseFloat(tempCum)){
							cumDist = Float.parseFloat(tempCum);
							linkData.setScale(idealLength/cumDist);
//							linkData.setScale(10);
						}
						
					}
					tempdis = cd;
					String tcd = String.valueOf(cd);
				df1.add(tcd);
				chrome.setMarkerposition(String.valueOf(cd));
				markpo.setMarkerPosition(String.valueOf(cd));
				
				mark.setMarkerPosition(String.valueOf(cd));
				tempCum=String.valueOf(cd);
				
				
				chrome.setForestatus("Background");
				
				if(!(chrome.getChromosome().equals(prevChromName))){
						duppChromeList.add(chrome.getChromosome());
						linkage.put(chrome.getChromosome(), (lcount+"@@"+(lcount-1)));
						lcount++;
					}
					prevChromName = chrome.getChromosome();
					
					chrome.setCount(5);
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
					
//					int cumdis =Math.round(cumudistance);
					
					
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
				e.printStackTrace();
			}
			return rootModel;	
		}
	}
