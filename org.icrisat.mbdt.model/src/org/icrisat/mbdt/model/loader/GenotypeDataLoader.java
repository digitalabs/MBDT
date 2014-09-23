package org.icrisat.mbdt.model.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.StringTokenizer;

import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.Chromosome.Chromosome;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.GenotypeModel.Accessions;
import org.icrisat.mbdt.model.GenotypeModel.AllelicValues;
import org.icrisat.mbdt.model.GenotypeModel.Genotype;
import org.icrisat.mbdt.model.sessions.SessionChromosome;

public class GenotypeDataLoader{
	
	int j=0;
	public RootModel loadGenotype(String filePath) {
		
		Genotype gType = new Genotype();
		
		// Get a singleton
		RootModel rootModel = RootModel.getRootModel();
		Chromosome chrom = Chromosome.getChromosome();
		LinkageData linkage = LinkageData.getLinkageData();		 
		try {
			FileReader fr = new FileReader(new File(filePath));
			BufferedReader br =new BufferedReader(fr);
			String strHeader;
			String str;
			strHeader = br.readLine();
			StringTokenizer stHeader = new StringTokenizer(strHeader,"\t");
			
			//Contains all the Headers in the Genotype File including Duplicates.
			List<String> headerList = new ArrayList<String>();
			LinkedHashSet<String> CharValues = new LinkedHashSet<String>();
			
			int count = stHeader.countTokens();
			
			
			//Markers Count - This is a Map that contains
			// Key - Marker Name
			// Value - Number of Times these markers appear in the File.
			HashMap<String, Integer> markersCount = new HashMap<String, Integer>();
			HashMap<String, HashMap> acc_value = new HashMap<String, HashMap>();
			HashMap marker_value = new HashMap();
			List allelicValue = null;
			for(int i = 0; i < count; i++) {
				String temp = stHeader.nextToken();
				headerList.add(temp);
				if(!markersCount.containsKey(temp)){
					markersCount.put(temp, 1);
				}else{
					int hashValue = ((Integer)markersCount.get(temp)).intValue() + 1;
					markersCount.put(temp, hashValue);
				}
			}
			
			markersCount.remove(0);
			markersCount.remove(0);
			
//			markersCount.remove(markersCount.get(1));
			gType.setHeaderList(headerList);
			headerList.remove(0);
			headerList.remove(0);
			
			String[] words = {"A", "B", "H","C","G","T"};  
			List<String> wordList = Arrays.asList(words);
			
			while((str= br.readLine())!= null) {
				Accessions acc = new Accessions();
				acc.setGmark(headerList);
				acc.setMarkersCount(markersCount);
				allelicValue = new ArrayList();
				AllelicValues alleleVal= new AllelicValues();
				gType.getAccessions().add(acc);
				rootModel.getGenotype().add(gType);
				chrom.getGenotype().add(gType);
				SessionChromosome.getInstance().setChromosome(chrom);
				StringTokenizer st1 = new StringTokenizer(str, "\t");
				marker_value = new HashMap();
				int i=0;
				String value = null;
				while(st1.hasMoreTokens()) {
					value= st1.nextToken();
					switch(i) {
					case 0:
						acc.setId(value);
						break;
					case 1:
						acc.setName(value);
						break;
					default :
						allelicValue.add(value);					
//						alleleVal.setAllelicValues(allelicValue);
						alleleVal.getAllelicValues().add(value);
						acc.getAllelicValues().add(alleleVal);
						acc.setAlleValues(allelicValue);
						
							if ((value.matches("\\p{Alpha}+"))||(value.matches("-")))
								gType.setDataCheck("Char");
							else
								gType.setDataCheck("Integer");
							
						if(allelicValue.contains(wordList.get(0))){
//							gType.setDataCheck("Char");
							CharValues.add(value);
							if(CharValues.contains("-")){
								CharValues.remove("-");
							}else if(CharValues.contains("0")){
								CharValues.remove("0");
							}
						}else{
//							gType.setDataCheck("Integer");
						}
						
//						j++;
					break;
					}
					i++;
				}
				//-------start--- marker data hashmap---
//				System.out.println(acc.getName());
//				System.out.println(allelicValue.size()+"..."+headerList.size());
				try {
					for(int i1 = 0; i1<allelicValue.size(); i1++ ){
						if(markersCount.get(headerList.get(i1)) ==1){
							marker_value.put(headerList.get(i1), allelicValue.get(i1).toString());
								
						}else if(markersCount.get(headerList.get(i1)) ==2){
							marker_value.put(headerList.get(i1), allelicValue.get(i1)+" "+allelicValue.get(i1+1));
							i1++;
						}
						
					}
					acc_value.put(acc.getName(), marker_value);
					linkage.setMarkerData(acc_value);
				} catch (Exception e) {
				}
				
				//-----end-----
			}
			
			gType.setNGTColorPrefCount(CharValues);
			
			
		}catch(Exception e) {
		}
		
		return rootModel;	
	}

	private void assignPositionsForAccessionPoints(Genotype type) {
		List<Accessions> accessions = type.getAccessions();

		int i= 0;
		int xValue = 10;
		int yValue = 10;
	}
}
