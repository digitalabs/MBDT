	package org.icrisat.mbdt.model.loader;

	import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.Platform;
import org.generationcp.middleware.manager.Database;
import org.generationcp.middleware.manager.DatabaseConnectionParameters;
import org.generationcp.middleware.manager.ManagerFactory;
import org.generationcp.middleware.manager.api.GenotypicDataManager;
import org.generationcp.middleware.pojos.Name;
import org.generationcp.middleware.pojos.gdms.AllelicValueWithMarkerIdElement;
import org.generationcp.middleware.pojos.gdms.DatasetElement;
import org.generationcp.middleware.pojos.gdms.MarkerIdMarkerNameElement;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.Chromosome.Chromosome;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.GenotypeModel.Accessions;
import org.icrisat.mbdt.model.GenotypeModel.AllelicValues;
import org.icrisat.mbdt.model.GenotypeModel.Genotype;
import org.icrisat.mbdt.model.sessions.SessionChromosome;

	public class GenotypeDatasetLoader{
		
		int j=0;
		DatabaseConnectionParameters local, central;  
		ManagerFactory factory;
		GenotypicDataManager manager;
		List<Integer> markerId;
		List<AllelicValueWithMarkerIdElement> allelicval= new ArrayList<AllelicValueWithMarkerIdElement>();
		List nids = new ArrayList();
		List<Name> names = new ArrayList<Name>();
		HashMap <String,String> markallele = new HashMap<String,String>();
		HashMap <String,HashMap> gidmarkallele = new HashMap<String,HashMap>();
		HashMap <Integer,String> gname = new HashMap<Integer, String>();
		HashMap <Integer,String> mname = new HashMap<Integer, String>();
		List<MarkerIdMarkerNameElement> markname = new ArrayList<MarkerIdMarkerNameElement>();
		List gids = new ArrayList();
		public RootModel loadGenotype(String filePath) {
			
			Genotype gType = new Genotype();
			
			// Get a singleton
			RootModel rootModel = RootModel.getRootModel();
			Chromosome chrom = Chromosome.getChromosome();
			LinkageData linkage = LinkageData.getLinkageData();		 
			try {
				
			

				try {
					String url = Platform.getLocation().toString().substring(0, Platform.getLocation().toString().lastIndexOf("/")+1);
					local = new DatabaseConnectionParameters(url+"DatabaseConfig.properties", "local");
					central = new DatabaseConnectionParameters(url+"DatabaseConfig.properties", "central");

					factory = new ManagerFactory(local, central);
					manager=factory.getGenotypicDataManager();
				} catch (Exception e) {
					e.printStackTrace();
				}
					
					List<DatasetElement> dbdtls = new ArrayList();
					List<AllelicValueWithMarkerIdElement> resultset=new ArrayList();
					markerId = new ArrayList();
					String dtype = "";
					int did = 0;
					dbdtls = manager.getDatasetDetailsByDatasetName(filePath, Database.CENTRAL) ;
					dtype = dbdtls.get(0).getDatasetType();
					did =  dbdtls.get(0).getDatasetId();
					rootModel.setGenotypeDataset(did);
					markerId = manager.getMarkerIdsByDatasetId(did);
					String mark="DatasetID\tGenotype/Marker";
					
					markname = manager.getMarkerNamesByMarkerIds(markerId);
					for(int m =0; m<markname.size(); m++){
						if(m==0){
							mark = markname.get(0).getMarkerName();
						}else{
							mark = mark+"\t"+markname.get(m).getMarkerName();
						}
						mname.put(markname.get(m).getMarkerId(), markname.get(m).getMarkerName());
					}
					linkage.setDataType("Haploid");
					if(dtype.equals("SSR")||(dtype.equals("DArT"))){
						allelicval = manager.getAllelicValuesFromAlleleValuesByDatasetId(did,0,(int)(manager.countAllelicValuesFromAlleleValuesByDatasetId(did)));
					}else if(dtype.equals("SNP")){
						allelicval = manager.getAllelicValuesFromCharValuesByDatasetId(did,0,(int) (manager.countAllelicValuesFromCharValuesByDatasetId(did)));
					}else if(dtype.equals("mapping")){
						allelicval = manager.getAllelicValuesFromMappingPopValuesByDatasetId(did,0,(int)(manager.countAllelicValuesFromMappingPopValuesByDatasetId(did) ));
					}
				
				StringTokenizer stHeader = new StringTokenizer(mark,"\t");
				
				//Contains all the Headers in the Genotype File including Duplicates.
				List<String> headerList = new ArrayList<String>();
				LinkedHashSet<String> CharValues = new LinkedHashSet<String>();
				
				int count = stHeader.countTokens();
			
				HashMap<String, Integer> markersCount = new HashMap<String, Integer>();
				HashMap<String, HashMap> acc_value = new HashMap<String, HashMap>();
				HashMap marker_value = new HashMap();
				List allelicValue = null;
				for(int i = 0; i < count; i++) {
					String temp = stHeader.nextToken();
					headerList.add(temp);
						markersCount.put(temp, 1);
				}
				
				markersCount.remove(0);
				markersCount.remove(0);
				
//				markersCount.remove(markersCount.get(1));
				gType.setHeaderList(headerList);
				
				
				String[] words = {"A", "B", "H"};  
				List<String> wordList = Arrays.asList(words);
				List ldid = new ArrayList();
				ldid.add(did);
				
				nids = manager.getNidsFromAccMetadatasetByDatasetIds(ldid, 0, (int) (manager.countNidsFromAccMetadatasetByDatasetIds(ldid))); 
				names = manager.getNamesByNameIds(nids);
				for(int n=0; n<names.size();n++){
				gname.put(names.get(n).getGermplasmId(),names.get(n).getNval());
				}
				//-------------genotype data----
				String germname ="", markername = "";
				for(int a=0; a<allelicval.size(); a++){
					if(!(gids.contains(allelicval.get(a).getGid())))
						gids.add(allelicval.get(a).getGid());
				}
				for(int a=0; a<gids.size(); a++){
					markallele = new HashMap<String,String>();
					if(gname.containsKey(gids.get(a)))
					 germname =gname.get(gids.get(a));
					
				for(int a1 = 0; a1<allelicval.size(); a1++){
					if(mname.containsKey(allelicval.get(a1).getMarkerId()))
						markername = mname.get(allelicval.get(a1).getMarkerId());
					
					if(gids.get(a).equals(allelicval.get(a1).getGid())){
						
						markallele.put(germname+"!@!"+markername, allelicval.get(a1).getData());
					}
					gidmarkallele.put(germname, markallele);
				}
				}
				List tempgname = new ArrayList(gidmarkallele.keySet());
				
				for(int g=0; g<gidmarkallele.size(); g++) {
					Accessions acc = new Accessions();
					acc.setGmark(headerList);
					acc.setMarkersCount(markersCount);
					allelicValue = new ArrayList();
					AllelicValues alleleVal= new AllelicValues();
					gType.getAccessions().add(acc);
					rootModel.getGenotype().add(gType);
					chrom.getGenotype().add(gType);
					SessionChromosome.getInstance().setChromosome(chrom);
					marker_value = new HashMap();
					int i=0;
					String value = null;
					
					for(int t =0; t<gidmarkallele.get(tempgname.get(g)).size(); t++) {
						value= (String) gidmarkallele.get(tempgname.get(g)).get((String) tempgname.get(g)+"!@!"+headerList.get(t));
						
							acc.setId(String.valueOf(did));
							
							acc.setName((String) tempgname.get(g));
							
							allelicValue.add(value);					
							alleleVal.getAllelicValues().add(value);
							acc.getAllelicValues().add(alleleVal);
							acc.setAlleValues(allelicValue);
							
								if ((value.matches("\\p{Alpha}+"))||(value.matches("-")))
									gType.setDataCheck("Char");
								else
									gType.setDataCheck("Integer");
								
							if(allelicValue.contains(wordList.get(0))){
								CharValues.add(value);
								if(CharValues.contains("-")){
									CharValues.remove("-");
								}else if(CharValues.contains("0")){
									CharValues.remove("0");
								}
							}
							
						
					}
					//-------start--- marker data hashmap---
//					System.out.println(acc.getName());
//					System.out.println(allelicValue.size()+"..."+headerList.size());
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
				e.printStackTrace();
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

