package org.icrisat.mbdt.model.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.Platform;
import org.generationcp.middleware.exceptions.MiddlewareQueryException;
import org.generationcp.middleware.manager.Database;
import org.generationcp.middleware.manager.DatabaseConnectionParameters;
import org.generationcp.middleware.manager.ManagerFactory;
import org.generationcp.middleware.manager.api.GenotypicDataManager;
import org.generationcp.middleware.pojos.gdms.QtlDetailElement;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.Chromosome.Chromosome;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.QTLModel.QTL;
import org.icrisat.mbdt.model.QTLModel.QTLData;
import org.icrisat.mbdt.model.QTLModel.QTLPeakPoints;
import org.icrisat.mbdt.model.QTLModel.QTLPreviousData;
import org.icrisat.mbdt.model.sessions.SessionChromosome;

public class QTLDatasetLoader {
	DatabaseConnectionParameters local, central;  
	ManagerFactory factory;
	GenotypicDataManager manager;
	List<QtlDetailElement> qtldetailelement ; 
	public RootModel load(String filePath) throws MiddlewareQueryException{
		QTL qtl = new QTL();
		QTLPreviousData qtlPrev = new QTLPreviousData();
		Qtl_MapData qtl_MapData = Qtl_MapData.getQtl_MapData();
		LinkageData linkData= LinkageData.getLinkageData();

		//			QTLGrayLines qtlGray = new QTLGrayLines();

		// Get a singleton
		RootModel rootModel = RootModel.getRootModel();
		Chromosome chrom = Chromosome.getChromosome();
		String qtlinfo = "";
		String qtlInfosplited = "";
		int groupCount = 0;
		ArrayList<String> EnviName = new ArrayList<String>();
		ArrayList<String> TraitName = new ArrayList<String>();

		String prevGroupName = "";
		ArrayList<String> qtlChromList = new ArrayList<String>();
		ArrayList<String> qtlChromList1 = new ArrayList<String>();
		int defaultTraitCount = 3;
		int defaultEnviCount = 1;
		int noOfTraits = 0;
		int QtlYValue = 0;
		int QtlYValueForOthers = 0;
		int noOfEnvironments = 0;

		try {
			
			try {
//				local = new DatabaseConnectionParameters("localhost", "13306", "ibdbv2_groundnut_1_local", "root", "");
//				central = new DatabaseConnectionParameters("localhost", "13306", "ibdbv2_groundnut_central", "root", "");
				
//				local = new DatabaseConnectionParameters("localhost", "13306", "ibdbv2_cowpea_2_local", "root", "");
//				central = new DatabaseConnectionParameters("localhost", "13306", "ibdbv2_cowpea_central", "root", "");

				String url = Platform.getLocation().toString().substring(0, Platform.getLocation().toString().lastIndexOf("/")+1);
				local = new DatabaseConnectionParameters(url+"DatabaseConfig.properties","local");
				central = new DatabaseConnectionParameters(url+"DatabaseConfig.properties","central");
				
				factory = new ManagerFactory(local, central);
				manager=factory.getGenotypicDataManager();
			}catch(Exception e){
				
			}
			List<Integer> qtlid =new ArrayList<Integer>();
			List<Integer> did = new ArrayList<Integer>();
			did.add(manager.getDatasetDetailsByDatasetName(filePath, Database.CENTRAL).get(0).getDatasetId()); 
			qtlid=manager.getQTLIdsByDatasetIds(did);
			qtldetailelement =manager.getQtlByQtlIds(qtlid, 0, (int) manager.countQtlByQtlIds(qtlid));
			int count = 0;

			
//			while((str = br.readLine()) != null){
				for(int q = 0; q < qtldetailelement.size(); q++){
				QTLData qtlData = new QTLData();
				QTLPeakPoints qtlPeakPoint = new QTLPeakPoints();

				qtl.getQtlData().add(qtlData);
				rootModel.getQtl().add(qtl);
				chrom.getQtl().add(qtl);
				SessionChromosome.getInstance().setChromosome(chrom);
				qtlData.setQtlPrevStartPt(qtlPrev.getQtlPrevStart());
				qtlData.setQtlPrevEndPt(qtlPrev.getQtlPrevEnd());
				qtlData.setPrevGroupCount(qtlPrev.getQtlPrevgroupCount());
					
						qtlData.setQtlNames(qtldetailelement.get(q).getQtlName());
						qtlData.setQtlChromNames(qtldetailelement.get(q).getChromosome());
						qtlPeakPoint.setQtlPeakPoints(qtldetailelement.get(q).getMaxPosition().toString());
						qtlData.setQtlStartPt(qtldetailelement.get(q).getMinPosition().toString());
						qtlData.setQtlEndPt(qtldetailelement.get(q).getMaxPosition().toString());
						
						qtlData.setQtltraitName(qtldetailelement.get(q).gettRName());
						qtlData.setQtlEnviName(qtldetailelement.get(q).getExperiment());
						qtlData.setQtlEnviName("1");
						qtlData.setQtlAddEffects(qtldetailelement.get(q).getEffect().toString());
						qtlData.setQtlLodsqr(qtldetailelement.get(q).getScoreValue().toString());
						qtlData.setQtlRsqr(qtldetailelement.get(q).getrSquare().toString());
					
				String gcount = (String)linkData.getLinkage().get(qtlData.getQtlChromNames());

				if(qtlData.getQtlChromNames() != null){

					if(!(qtlData.getQtlChromNames().equals(prevGroupName))){
						qtlChromList.add(qtlData.getQtlChromNames());
						qtlChromList1.add(qtlData.getQtlChromNames());
						//									groupCount++;


						groupCount = Integer.parseInt(gcount.substring(0,gcount.indexOf("@@")));

					}
					prevGroupName = qtlData.getQtlChromNames();							

				}
				//Setting GroupCount...
				qtlData.setGroupCount(groupCount);
				qtlData.setPrevGroupCount(Integer.parseInt(gcount.substring(gcount.indexOf("@@")+2, gcount.length())));
				qtlData.getQtlPeakPoints().add(qtlPeakPoint);
				qtlData.setQtlChromList(qtlChromList);
				qtlData.setQtlModChromList(qtlChromList1);

				if(EnviName != null){
					if(!EnviName.contains(qtlData.getQtlEnviName())){
						EnviName.add(qtlData.getQtlEnviName());
					}
				}

				if(TraitName != null){
					if(!TraitName.contains(qtlData.getQtltraitName())){
						TraitName.add(qtlData.getQtltraitName());
					}
				}
				qtl.setQtlEnviListFromLoader(EnviName);
				qtl.setQtlTraitListFromLoader(TraitName);
				qtlData.setGroupCount(groupCount);
				qtlPrev.setQtlPrevgroupCount(groupCount);
			}

			/*** 17th NOV 2009 
			 * In this Loader we are setting the QTL Yvalue's and
			 * YValue for Linkage and HeatMap beco'z depending on
			 * the position of QTL we are setting the positions of
			 * Heat Map and Linkage Map..... 
			 ****/

			if(qtl.getQtlTraitListFromLoader().size() > 3){
				noOfTraits = qtl.getQtlTraitListFromLoader().size() - defaultTraitCount;
				noOfEnvironments = qtl.getQtlEnviListFromLoader().size() - defaultEnviCount; 

				//					QtlYValue =  10 + (10 * noOfTraits);
				QtlYValue =  10 + (10 * noOfTraits * noOfEnvironments);

				if(qtl.getQtlEnviListFromLoader().size() > 1){
					//						QtlYValueForOthers = 10 + (20 * noOfTraits);
					QtlYValueForOthers = 10 + (20 * noOfTraits) + ((10 * noOfTraits) * noOfEnvironments);

				}else{
					QtlYValueForOthers = 10 + (10 * noOfTraits);
				}
			}else{
				if(qtl.getQtlEnviListFromLoader().size() > 1){
					QtlYValue =  10 + 10;
				}else{
					QtlYValue =  10;
				}

				QtlYValueForOthers = 10;
			}

			qtl_MapData.setQtlYValue(QtlYValue);
			qtl_MapData.setQtlYValueForOthers(QtlYValueForOthers);

		} catch (Exception e) {		
		} 	
		return rootModel;
	}

}
