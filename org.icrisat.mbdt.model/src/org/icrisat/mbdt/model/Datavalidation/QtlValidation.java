package org.icrisat.mbdt.model.Datavalidation;

import java.io.Serializable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;

public class QtlValidation implements Serializable {
	HashMap  cumno_cumdist =  new HashMap();
	RootModel rootModel = RootModel.getRootModel();
	List<String> validationrules = new ArrayList<String>();
	List<String> validationrules_temp = new ArrayList<String>();
	DataValidation validation =  DataValidation.getValidationData();
	HashSet set = new HashSet();
	public List<String> qtlValidation(){
		
		try {
			
			FileReader  fr = new FileReader(new File(rootModel.getQtlPath()));
			BufferedReader br =new BufferedReader(fr);
			String strHeader;
			int count=0;
			LinkageData	linkage = LinkageData.getLinkageData();
			cumno_cumdist=linkage.getChrmno_cumdis();
			
			br.readLine();
			try {
				while((strHeader= br.readLine())!= null) {
				StringTokenizer stHeader = new StringTokenizer(strHeader,"\t");
				
				stHeader.nextToken();
				count++;
				String chromNum=	stHeader.nextToken();
				
				
String strBuff = "";
				
				char c,c1;
				String str1 =  chromNum;
				c1=str1.charAt(0);
 /*   
				if(Character.isDigit(c1)){
					for (int  i1 = 0; i1 < str1.length() ; i1++) {
				        c = str1.charAt(i1);
				        if (Character.isDigit(c)) {
				            strBuff = strBuff+c;
				        }else{
				        	break;
				        }
				    }
				}
				else{
					
					for (int  i1 = str1.length()-1; i1 >= 0 ; i1--) {
				        c = str1.charAt(i1);
				        if (Character.isDigit(c)) {
				            strBuff = c+strBuff;
				        }else{
				        	break;
				        }
				    }
				}*/
//			String temp1 =strBuff;
				
				try {
					String temp = (String) cumno_cumdist.get(chromNum);
					
					float f = Float.valueOf(temp.trim()).floatValue();

					int cumdist = (int)f;
					
					stHeader.nextToken();
					stHeader.nextToken();
					 	
					int  qtlmaxpos=(int)Float.parseFloat(stHeader.nextToken());


					if(qtlmaxpos >cumdist)
					{
						String info=stHeader.nextToken()+" QTL is out of range in Linkage Group "+chromNum;
						validationrules_temp.add(info);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
				}
				set.add(stHeader.nextToken());
				
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
			
			validationrules.add(" ***QTL Validation *** ");
			
		 	validationrules.add("QTLs  # "+count);
		 	validationrules.add("ENV's #  "+set.size()); 
		 	validationrules.add("                 ");
		 	
		 	for (int i = 0; i <validationrules_temp.size(); i++) {
		 			validationrules.add(validationrules_temp.get(i));
			}
		
			 validation.setQtlValidationrules(validationrules);
			}catch(Exception e)
			{
			}
		return validationrules;
	}
}

