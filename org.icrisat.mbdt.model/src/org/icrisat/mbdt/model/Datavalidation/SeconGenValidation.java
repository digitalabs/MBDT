package org.icrisat.mbdt.model.Datavalidation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

public class SeconGenValidation {

	List<String> NoofGenotypes = new ArrayList<String>();
	HashSet<String> MissingVal = new HashSet<String>();
	List<String> validationrules = new ArrayList<String>();
	List<String> validationrules_temp = new ArrayList<String>();
	
	public List<String> SecondGenValidation(String path){
		
		FileReader fr;
		try {
			fr = new FileReader(new File(path));
			BufferedReader br =new BufferedReader(fr);
			String strHeader;
			String allelvalue;
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
			  set.add(markerName.get(i));
			 }
			while((strHeader= br.readLine())!= null) {
				
				StringTokenizer stHeader = new StringTokenizer(strHeader,"\t");
				stHeader.nextToken();
				String Genoname=stHeader.nextToken();
				
				NoofGenotypes.add(Genoname);
				
			  	while(stHeader.hasMoreTokens()) {
			  		allelvalue=stHeader.nextToken().trim();
			  		
			 if( ((allelvalue.equals("A")) ||(allelvalue.equals("B")) ||(allelvalue.equals("C")) || (allelvalue.equals("D")) || (allelvalue.equals("H"))))
			{
					
			} 		
			 else{
				 count++;
				 String missing_val ="Missing value is represented with "+allelvalue ;
				 boolean val1 = 	 MissingVal.add(missing_val.trim());
				 if (val1 == true) {
//					 validationrules_temp.add(missing_val.trim());
					 validationrules.add(missing_val.trim());
				  }
			 }
			  		
			  	}
			}
			
			 
			 validationrules.add("Genotypes #  "+NoofGenotypes.size() );
			 validationrules.add("Markers   #  "+set.size());
			 validationrules.add("Missing   #  "+count);
             validationrules.add("                 ");
              
		 
			
		} catch (Exception e) {
		}
		
		
		
		
		return validationrules;
		
	}
}


