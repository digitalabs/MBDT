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
import org.icrisat.mbdt.model.GenotypeModel.Accessions;
import org.icrisat.mbdt.model.PhenotypeModel.Phenotype;
import org.icrisat.mbdt.model.PhenotypeModel.TraitValues;

	public class PhenotypeDataLoader {
		
		int j=0;
		public RootModel loadPhenotype(String filePath) {
			
			Phenotype gType = new Phenotype();
			
			// Get a singleton
			RootModel rootModel = RootModel.getRootModel();
			
			 
			try {
				FileReader fr = new FileReader(new File(filePath));
				BufferedReader br =new BufferedReader(fr);
				String strHeader;
				String str;
				strHeader = br.readLine();
				StringTokenizer stHeader = new StringTokenizer(strHeader,"\t");
				List<String> headerList = new ArrayList<String>();
				LinkedHashSet<String> CharValues = new LinkedHashSet<String>();
				
				int count = stHeader.countTokens();
				HashMap<String, Integer> markersCount = new HashMap<String, Integer>();
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
				
				
				markersCount.remove("Genotype");
				gType.setTraitList(headerList);
				
				headerList.remove("Genotype");
				
				
				while((str= br.readLine())!= null) {
					Accessions acc = new Accessions();
					acc.setTraits(headerList);
					gType.setTraitList(headerList);
					acc.setHashtraitCount(markersCount);
					List temp = new ArrayList();
					TraitValues traitVal= new TraitValues();
//					System.out.println(acc.getName()+"...."+acc.getTraits()+"...."+acc.getTraitValues());
					gType.getAccessions().add(acc);
					rootModel.getPhenotype().add(gType);
					StringTokenizer st1 = new StringTokenizer(str, "\t");
					int i=0;
					String geno = null;
					while(st1.hasMoreTokens()) {
						geno= st1.nextToken();
						switch(i) {
						case 0:
							
							//----start compare phenotype accession with genotype accession.....
							
							for(int i1 = 0; i1 < rootModel.getGenotype().get(0).getAccessions().size(); i1++){
								if(geno.equals(rootModel.getGenotype().get(0).getAccessions().get(i1))){
									acc.setAflag(true);
								}
							}
							
							
							
							//----end compare phenotype accession with genotype accession.....
							
							acc.setName(geno);
							break;
						default :
							temp.add(geno);
							traitVal.setTraitValues(temp);
							acc.getTraitValues().add(traitVal);
							
							j++;
						break;
						}
						i++;
					}
				}
				
			}catch(Exception e) {
			}
			
			return rootModel;	
		}

		
	}

	//getStudyIdByName, getAllStudyVariates(int studyId) , getAllStudyFactors(int studyId)
