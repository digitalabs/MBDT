 
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

public class PhenotypeValidation implements Serializable {
	List<String> validationrules = new ArrayList<String>();
	List<String> validationrules_temp = new ArrayList<String>();
	RootModel rootModel = RootModel.getRootModel();
	DataValidation validation =  DataValidation.getValidationData();
	HashMap  DublicateGenotypes =  new HashMap();
	HashMap  ValidateDublicate = new HashMap();
	
	
	public List<String> phenoValidation(){
		
		try {
			 
			
			HashSet<String> GenotypeName = new HashSet<String>();
		 	GenotypeName=rootModel.getGenotypeName();

		 	int NoofTraits=0;
			int count=0;
			String Genotype;
			FileReader  fr = new FileReader(new File(rootModel.getPhenotypePath(  )));
			BufferedReader br =new BufferedReader(fr);
			String strHeader;
			List<String> AccessionName = new ArrayList<String>();
			HashSet set = new HashSet();
			br.readLine();
			
			
			while((strHeader= br.readLine())!= null) {
			StringTokenizer stHeader = new StringTokenizer(strHeader,"\t");
		    Genotype =stHeader.nextToken();
			count++;
			AccessionName.add(Genotype);
			NoofTraits=stHeader.countTokens();
			
			
			if ( ! DublicateGenotypes.containsKey( Genotype ) ) {
	             
				DublicateGenotypes.put( Genotype, count);
	        }else{
	        	String temp = DublicateGenotypes.get(Genotype)+"  "+count;
	        	DublicateGenotypes.put( Genotype,temp );
	        	ValidateDublicate.put( Genotype,temp );
	        }
			
			}
			
			
			
			
			  for (int i = 0; i < AccessionName.size(); i++) {
			  boolean val = set.add(AccessionName.get(i));
			  if (val == false) {

//				  validationrules_temp.add(AccessionName.get(i)+" index"+i );
			  	}
			 } 
			   
			  HashSet result = new HashSet(set.size());  
			    for (Iterator i = set.iterator(); i.hasNext(); ) {  
			   Object element = i.next();  
			  if (!GenotypeName.contains(element))
				  result.add(element);  
			  
			  }  
			    
			  if(result.size()>1){
				  validationrules_temp.add("Genotype data not available for following Genotypes");
			  }
			    Iterator itr = result.iterator();
			    
			    while(itr.hasNext()){
			    	validationrules_temp.add(""+itr.next());
			    }
			 
			    HashSet result1 = new HashSet(GenotypeName.size());  
			    for (Iterator i = GenotypeName.iterator(); i.hasNext(); ) {  
			   Object element = i.next();  
			  if (!set.contains(element))
				  result1.add(element);  
			  
			  }  
			    
			  if(result1.size()>1){
				  validationrules_temp.add("Phenotypic data not available for following Genotypes");
			  }
			    Iterator itr1 = result1.iterator();
			    
			    while(itr1.hasNext()){
			    	validationrules_temp.add(""+itr1.next());
			    	
			    }	
			 
			
			 validationrules.add(" ***Phenotype Data Validation *** ");    
			 validationrules.add("Genotypes #  "+ AccessionName.size() );
			 validationrules.add("Traits #  "+ NoofTraits);
			 
			 for (int i = 0; i <validationrules_temp.size(); i++) {
				 validationrules.add(validationrules_temp.get(i));	
			}
			 
			 
			    Set set1 =  ValidateDublicate.entrySet();
				// Get an iterator
				Iterator iter = set1.iterator();
				// Display elements
				while(iter.hasNext()) {
				Map.Entry me = (Map.Entry)iter.next();
				 validationrules.add(me.getKey() +" Duplicated   at  "+me.getValue()+"line " );  
				} 
			    	
			 
			 
			 
			 validationrules.add("                 ");
			 validation.setPhenoValidationrules(validationrules);
			  
			}catch(Exception e)
			{
			}
		return validationrules;
	}
}
