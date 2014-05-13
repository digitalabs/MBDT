package org.icrisat.mbdt.model.Datavalidation;

import java.io.Serializable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;

public class LinkageMapValidation implements Serializable {

	boolean isfinal =false;
	boolean notvalid =false;
	boolean  valideLinkage =false;
	static String tempGeno="";
	 static String tempCum=""; 
	 int chromnum=0;
	int chrom_num=0; 
		RootModel rootModel = RootModel.getRootModel();
		List<String> validationrules = new ArrayList<String>();
		HashMap  DublicateMarkers =  new HashMap();
		HashMap  ValidateDublicate = new HashMap();

		List<String> validate_chromsize = new ArrayList<String>();
		
		
		LinkageData	linkage = LinkageData.getLinkageData();
		DataValidation validation =  DataValidation.getValidationData();
		
		public List<String>  LinkageValidation()
		{
			try {
				
				HashMap  cumno_cumdist =  new HashMap();
				LinkageData	linkage = LinkageData.getLinkageData();
				
				FileReader  fr = new FileReader(new File(rootModel.getLinkagemapPath()));
				BufferedReader br =new BufferedReader(fr);
				String strHeader;
				List<String> markerName = new ArrayList<String>();
				HashSet set = new HashSet();
				int postion_int=0;
				int chromlenght_int=0;
				int count=0;
				int chrom_singlemarker=0;
				  int counting =0; 
				  
				HashSet<String> Genotype_Markers = new HashSet<String>();
				Genotype_Markers =rootModel.getMarkerName_validation();
				
//				validationrules.add(" ***LinkageMap Validation *** ");
				while((strHeader= br.readLine())!= null) {
				StringTokenizer stHeader = new StringTokenizer(strHeader,"\t");
				String chromNum =stHeader.nextToken();
				if(counting==0)
				{
					tempGeno=chromNum;
					rootModel.setLinkageMap_Chromfirst(chromNum);
					
				}
				counting++;
				chrom_singlemarker++;
				
				if(!(count==0))
				{
				if(!(chromNum.equals(tempGeno)))
				{
					isfinal=true;
					chrom_num++;
					if(chrom_singlemarker==1)
					{
						validate_chromsize.add("Chromosome with one marker "+ chrom_num);
					}
					
					chrom_singlemarker=0;
				}
				}
				cumno_cumdist.put(tempGeno, tempCum);
				tempGeno=chromNum;
				String MarkerName =stHeader.nextToken();
				count++;
				markerName.add(MarkerName);
				
				
				if(Genotype_Markers.contains(MarkerName)){
					valideLinkage=true;
				}
				
				if ( ! DublicateMarkers.containsKey( MarkerName ) ) {
		             
					DublicateMarkers.put( MarkerName, count);
		        }else{
		        	
		        	String temp = DublicateMarkers.get(MarkerName)+"  "+count;
		        	DublicateMarkers.put( MarkerName,temp );
		        	
		        	ValidateDublicate.put( MarkerName,temp );
		        }

				
				String postion = stHeader.nextToken();
				String chromlenght = stHeader.nextToken();
				String cumdist=stHeader.nextToken();
				
				
			
				try {
					float f = Float.valueOf(postion.trim()).floatValue();
					postion_int = (int)f;
					float f1 = Float.valueOf(chromlenght.trim()).floatValue();
					chromlenght_int = (int)f;
					
				if(isfinal ==true && postion_int==0 && chromlenght_int==0 )
				{
					chromnum++;
//					cumno_cumdist.put(tempGeno, tempCum);
					isfinal=false;
				}
				
				
				} catch (NumberFormatException e) {
					notvalid=true;
				}
				
				tempCum=cumdist;
				}
				
				
				chromnum++;
				cumno_cumdist.put(tempGeno, tempCum);
				
				linkage.setChrmno_cumdis(cumno_cumdist);
			 
				     
				Set set1 =  ValidateDublicate.entrySet();
				// Get an iterator
				Iterator i = set1.iterator();
				// Display elements
				
				validationrules.add(" ***Linkage Data Validation *** ");
				validationrules.add("chrom #  "+chromnum );
				validationrules.add("Markers   #  "+DublicateMarkers.size());
				validationrules.add("                 ");
				
				while(i.hasNext()) {
				Map.Entry me = (Map.Entry)i.next();
				 validationrules.add(me.getKey() +"  Duplicated   at  "+me.getValue()+" line " );  
				} 
				
				if(notvalid == true)
					validationrules.add("Not a Valid linkage map file" );
				
				validationrules.addAll(validate_chromsize);
				
				
				if(valideLinkage == false){
					
					 validationrules.add("No Genotyped markers are available on linkage map " );
					rootModel.setLinageMap_Error(true);
				}
				else{
					rootModel.setLinageMap_Error(false);
				}
				
				validation.setMapValidationrules(validationrules);
				 
				}catch(Exception e)
				{
				}
			return validationrules;
		}
}