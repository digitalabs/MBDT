package org.icrisat.mbdt.model.loader;

	import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.generationcp.middleware.dao.dms.GeolocationDao;
import org.generationcp.middleware.domain.dms.TrialEnvironment;
import org.generationcp.middleware.domain.dms.TrialEnvironments;
import org.generationcp.middleware.domain.dms.VariableTypeList;
import org.generationcp.middleware.domain.h2h.Observation;
import org.generationcp.middleware.manager.DatabaseConnectionParameters;
import org.generationcp.middleware.manager.ManagerFactory;
import org.generationcp.middleware.manager.Operation;
import org.generationcp.middleware.manager.api.CrossStudyDataManager;
import org.generationcp.middleware.manager.api.GenotypicDataManager;
import org.generationcp.middleware.manager.api.GermplasmDataManager;
import org.generationcp.middleware.manager.api.StudyDataManager;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.GenotypeModel.Accessions;
import org.icrisat.mbdt.model.PhenotypeModel.Phenotype;
import org.icrisat.mbdt.model.PhenotypeModel.TraitValues;
import org.icrisat.mbdt.model.sessions.Session;

	public class PhenotypeDatasetLoader {
		DatabaseConnectionParameters local, central;  
		ManagerFactory factory;
		GenotypicDataManager manager;
		StudyDataManager phenomanager;
		CrossStudyDataManager manager1;
		GermplasmDataManager gmanager;
		GeolocationDao gd = new GeolocationDao();
		int j=0;
		public RootModel loadPhenotype(String filePath) {
			
			Phenotype gType = new Phenotype();
			
			// Get a singleton
			RootModel rootModel = RootModel.getRootModel();
			
			 
			try {
				String url = Platform.getLocation().toString().substring(0, Platform.getLocation().toString().lastIndexOf("/")+1);
				 
				 local = new DatabaseConnectionParameters(url+"DatabaseConfig.properties","local");
				 central = new DatabaseConnectionParameters(url+"DatabaseConfig.properties","central");
				 
				 factory = new ManagerFactory(local, central);
					manager=factory.getGenotypicDataManager();
					phenomanager = factory.getNewStudyDataManager();
					manager1 =factory.getCrossStudyDataManager();
					gmanager = factory.getGermplasmDataManager();
					int id = phenomanager.getStudyIdByName(filePath);
					rootModel.setPhenoDataset(id);
					VariableTypeList vlist = new VariableTypeList();
					vlist =  phenomanager.getAllStudyVariates(id);
					/*VariableTypeList flist = new VariableTypeList();
					flist = phenomanager.getAllStudyFactors(id);
					*/
					List vl = new ArrayList();
					HashMap <Integer, String> traitlist = new HashMap<Integer, String>();
					for(int l=0; l<vlist.size(); l++){
					vl.add(vlist.getVariableTypes().get(l).getId());
					traitlist.put(vlist.getVariableTypes().get(l).getId(), vlist.getVariableTypes().get(l).getLocalName());
					}
					
					TrialEnvironments environments = manager1.getEnvironmentsForTraits(vl);
					List<Integer> el = new ArrayList();
					Iterator<TrialEnvironment> iter = environments.getTrialEnvironments().iterator();
					while (iter.hasNext()) {
					  el.add(iter.next().getId());
					}
					RootModel root = Session.getInstance().getRootmodel();
					List<Observation> oft =manager1.getObservationsForTraits(vl,el);
					HashMap <String,HashMap> gobs = new HashMap<String, HashMap>();
					HashMap <String,String> tobs = new HashMap<String, String>();
					List gnames = new ArrayList();
					System.out.println("start");
										
						for(int acc = 0; acc < root.getGenotype().get(0).getAccessions().size(); acc++){	
							Integer gname = (gmanager.getGermplasmByName(root.getGenotype().get(0).getAccessions().get(acc).getName(),0,1,Operation.EQUAL)).get(0).getGid();
							String aname = root.getGenotype().get(0).getAccessions().get(acc).getName();
							tobs = new HashMap<String, String>();
							for(int i =0; i<oft.size(); i++){	
							if(gname==oft.get(i).getId().getGermplasmId()){	
								tobs.put(aname+"!@!"+traitlist.get(oft.get(i).getId().getTraitId()), oft.get(i).getValue());
								
								/*if(!(gnames.contains(root.getGenotype().get(0).getAccessions().get(acc).getName()))){
										gnames.add(root.getGenotype().get(0).getAccessions().get(acc).getName());
									}	*/
							}
						}
							gobs.put(aname.toString(), tobs);
							gnames.add(aname.toString());
					}
					System.out.println("gnames...");
					/*for(int i =0; i< gnames.size(); i++){
						tobs = new HashMap<String, String>();
						for( int j=0; j<oft.size(); j++ ){
							if(factory.getGermplasmDataManager().getGermplasmNameByID(oft.get(j).getId().getGermplasmId()).getNval().equals(gnames.get(i))){
								tobs.put(gnames.get(i)+"!@!"+traitlist.get(oft.get(j).getId().getTraitId()), oft.get(j).getValue());
							}							
						}
						gobs.put(gnames.get(i).toString(), tobs);
					}*/
					System.out.println("gobs..");
				List<String> headerList = new ArrayList<String>();
				headerList.addAll(traitlist.values());
				
				for(int i=0; i< gnames.size(); i++) {
					Accessions acc = new Accessions();
					acc.setTraits(headerList);
					gType.setTraitList(headerList);
					List temp = new ArrayList();
					TraitValues traitVal= new TraitValues();
//					System.out.println(acc.getName()+"...."+acc.getTraits()+"...."+acc.getTraitValues());
					gType.getAccessions().add(acc);
					rootModel.getPhenotype().add(gType);
					acc.setAflag(true);
					acc.setName(gnames.get(i).toString());
					for(int t =0; t<headerList.size();t++) {
							temp.add(gobs.get(gnames.get(i)).get(gnames.get(i)+"!@!"+headerList.get(t)));
							traitVal.setTraitValues(temp);
							acc.getTraitValues().add(traitVal);
					}
							
							
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			return rootModel;	
		}

		
	}

