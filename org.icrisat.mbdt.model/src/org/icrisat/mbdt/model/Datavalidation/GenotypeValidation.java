package org.icrisat.mbdt.model.Datavalidation;

import java.io.Serializable;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;


public class GenotypeValidation implements Serializable {
	boolean isfinal =false;
	 static String tempCum=""; 
	 static int chromnum=0;
	 boolean diploid= false;
	boolean isSNIP =false;
	boolean isSSR =false;
	boolean isDart = false;
	RootModel rootModel = RootModel.getRootModel();
	LinkageData linkageData = LinkageData.getLinkageData();
	DataValidation validation =  DataValidation.getValidationData();
	HashSet<String> GenotypeName = new HashSet<String>();
	HashSet<String> MissingVal = new HashSet<String>();
	List<String> validationrules = new ArrayList<String>();
	List<String> validationrules_temp = new ArrayList<String>();
	List<String> NoofGenotypes = new ArrayList<String>();
	int Haploidlevel;
 public List<String> GenotypeValidation()
 {

		try {
		FileReader  fr = new FileReader(new File(rootModel.getGenotypePath()));
		BufferedReader br =new BufferedReader(fr);
		String strHeader;
		String allelvalue;
		String allelvalue1;
		List<String> markerName = new ArrayList<String>();
		HashSet<String> set = new HashSet<String>();
		String str = br.readLine();
		StringTokenizer strtoken= new StringTokenizer(str,"\t");
		int x =0; int y=0;
		int count=0;
		strtoken.nextToken();
		strtoken.nextToken();
		
		while(strtoken.hasMoreTokens()) {
			markerName.add(strtoken.nextToken());
			}
		
		 for (int i = 0; i < markerName.size(); i++) {
			  boolean val = set.add(markerName.get(i));
			  if (val == false) {
				  diploid=true;
			  }
		 }
		 Haploidlevel=Integer.valueOf(rootModel.getHaploidlevel());
		 
			while((strHeader= br.readLine())!= null) {
				
			
		 if(Haploidlevel==2){
			 
		StringTokenizer stHeader = new StringTokenizer(strHeader,"\t");
		stHeader.nextToken();
		String Genoname=stHeader.nextToken();
		
		NoofGenotypes.add(Genoname);
		
		 boolean val = 	GenotypeName.add(Genoname);
		 if (val == false) {
			 validationrules_temp.add(Genoname+" is duplicated" );
		  }
		  	while(stHeader.hasMoreTokens()) {

		  		try {
					allelvalue=stHeader.nextToken().trim();
					allelvalue1=stHeader.nextToken().trim();
					Boolean b = true;
					
					try {
						  x = Integer.parseInt(allelvalue);
						  y = Integer.parseInt(allelvalue1);
						}
						catch( Exception nFE) {
							
						b = false;
						}
					
					if( ((allelvalue.equals("A")) ||(allelvalue.equals("C")) ||(allelvalue.equals("G")) || (allelvalue.equals("T"))) && 
							((allelvalue1.equals("A")) ||(allelvalue1.equals("C")) ||(allelvalue1.equals("G")) || (allelvalue1.equals("T"))))
					{
						isSNIP=true;
					}
					else if((b==true) && x!=0 && y!=0){
						isSSR=true;
						
					}
					else {
						if((x==0)  && (y==0)){
							count++;	
						}
						isDart = false;
						linkageData.setDart(false);
						String missing_val ="Missing value is represented with "+allelvalue  +" "+allelvalue1;
						 boolean val1 = 	 MissingVal.add(missing_val.trim());
						 if (val1 == true) {
							 validationrules_temp.add(missing_val.trim());
						  }
					}
				} catch (Exception e) {
					
					if(diploid==false)
					{
//						 validationrules_temp.add("Data Indicate  ploidy level 1");
					}
				}
			}  
		}
		else if(Haploidlevel==1){
			StringTokenizer stHeader = new StringTokenizer(strHeader,"\t");
			stHeader.nextToken();
			String Genoname=stHeader.nextToken();
			NoofGenotypes.add(Genoname);
			
			 boolean val = 	GenotypeName.add(Genoname);
			 if (val == false) {
				 validationrules_temp.add(Genoname+" is duplicated" );
			  }
			 
			while(stHeader.hasMoreTokens()) {
				allelvalue=stHeader.nextToken().trim();
				Boolean b = true;
				
				try {
					  x = Integer.parseInt(allelvalue);
					}
					catch(NumberFormatException nFE) {
					b = false;
					}
				
			 
				if( (allelvalue.equals("A")) ||(allelvalue.equals("C")) ||(allelvalue.equals("G"))||(allelvalue.equals("H")) || (allelvalue.equals("T"))  || (allelvalue.equals("B")) )
				{
					isSNIP=true;
				}else if((allelvalue.equals("1")) ||(allelvalue.equals("0"))){
					isDart = true;
					linkageData.setDart(true);
				}
				else if(b==true  && x!=0){
					isSSR=true;
				}
				else {
					count++;
					String missing_val ="Missing value is"+allelvalue;
					 boolean val1 = 	 MissingVal.add(missing_val.trim());
					 if (val1 == true) {
						 validationrules_temp.add(missing_val.trim());
					  }
	    			}
				
				
				
    			}
			
			}
		 	rootModel.setMarkerName_validation(set);
			rootModel.setGenotypeName(GenotypeName);
 		}	
			
			if(Haploidlevel==1 && diploid==true )
			{
				 validationrules_temp.add("Data Indicate  Columns per Marker as 2");
			}
			else if(Haploidlevel==2 && diploid==false ){
				validationrules_temp.add("Data Indicate  Columns per Marker as 1");
			}
			
			
			validationrules.add(" ***Genotype Data Validation *** ");

			if(isSNIP==true){
//				 validationrules.add("Marker type SNIP Data");
				 
			 }else if(isSSR==true) {
//				 validationrules.add("Marker type SSR Data");
			 }
			 
			 
			 if(Haploidlevel==2){
//				 validationrules.add("Diplold Data");
				 linkageData.setDataType("Diploid"); 
			 }else if (Haploidlevel==1){
//				 validationrules.add("Haploid Data");
				 linkageData.setDataType("Haploid");
			 }
			 
			 validationrules.add("Genotypes #  "+NoofGenotypes.size() );
			 validationrules.add("Markers   #  "+set.size());
			 validationrules.add("Missing   #  "+count);
             validationrules.add("                 ");
		 	
		 	for (int i = 0; i < validationrules_temp.size(); i++) {
    		 		validationrules.add(validationrules_temp.get(i));
				}
			 validation.setGenoValidationrules(validationrules);
			 			 
	}catch(Exception e)
	{
	}
		return validationrules;
 }

}
