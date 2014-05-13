package org.icrisat.mbdt.ui.wizards;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.generationcp.middleware.manager.Database;
import org.generationcp.middleware.manager.DatabaseConnectionParameters;
import org.generationcp.middleware.manager.ManagerFactory;
import org.generationcp.middleware.manager.api.GenotypicDataManager;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.loader.GenotypeDataLoader;
import org.icrisat.mbdt.model.loader.LinkageMapDataLoader;
import org.icrisat.mbdt.model.loader.LinkageMapDataLoader1;
import org.icrisat.mbdt.model.loader.PhenotypeDataLoader;
import org.icrisat.mbdt.model.loader.QTLDataLoader;
import org.icrisat.mbdt.model.sessions.Session;
import org.icrisat.mbdt.ui.views.LinkageMapTableView;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.helpers.DefaultHandler;

public class FilesImportWizard extends Wizard  implements IImportWizard {

	int flag = 0;
	static int count = 0;

	public FilesImportWizard() {
		this.setWindowTitle("Upload Files");
	}
	ManagerFactory factory =null;
	public boolean performFinish() {
		FilesImportPage page = (FilesImportPage)getPage("FilesImportPage");
		String returnString = null;
		returnString = page.getFilePath();
		String linkageMapPath = null;
		String qTLPath = null;
		String[] filePath = null;

		// Splitting the returnString from FileImportPage.java.....
		filePath = returnString.split("!@!");
		
		
		for(int i = 0 ; i < filePath.length; i++) {
			if(filePath[i].equalsIgnoreCase("Genotype File")){
				GenotypeDataLoader gLoader = new GenotypeDataLoader();
				// for Genotype File Upload....
				RootModel gType = gLoader.loadGenotype(filePath[i+1]);				
				//Using Session Variables....
				gType.setGeneration("First");
				Session.getInstance().setRootModel(gType);				
			} 
			if(filePath[i].equalsIgnoreCase("Linkage Map File")){
				//for Linked Map File Upload....
				LinkageMapDataLoader gLoader= new LinkageMapDataLoader();
				RootModel gType = gLoader.load(filePath[i+1]);
				flag++;
				// Using Session Variables.........
				
				try{
					LinkageMapTableView view = (LinkageMapTableView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(LinkageMapTableView.class.getName());
//					Session.getInstance().setRootModel(gType);
				}catch(Exception e){
				}
				Session.getInstance().setRootModel(gType);
			} 
			if(filePath[i].equalsIgnoreCase("QTL File")) {

				//for QTL File Upload....
				QTLDataLoader qtlLoader = new QTLDataLoader();
				RootModel rootModel = qtlLoader.load(filePath[i+1]);
				
				if(flag==0) {
					LinkageMapDataLoader1 gLoader= new LinkageMapDataLoader1();
					RootModel gType = gLoader.load(filePath[i+1]);
				}
				
				Session.getInstance().setRootModel(rootModel);
			}
			if(filePath[i].equalsIgnoreCase("Phenotype File")) {

				//for Phenotype File Upload....
				PhenotypeDataLoader phenotypeLoader = new PhenotypeDataLoader();
				RootModel rootModel = phenotypeLoader.loadPhenotype(filePath[i+1]);
								
				Session.getInstance().setRootModel(rootModel);
			}
		}
		
		//----------------
//		try { 
//			
//			DatabaseConnectionParameters local = new DatabaseConnectionParameters("localhost", "13306", "ibdbv2_chickpea_central", "root", "");
//			DatabaseConnectionParameters central = new DatabaseConnectionParameters("localhost", "13306", "ibdbv2_chickpea_central", "root", "");
//			
//			ManagerFactory factory = new ManagerFactory(local, central);
//
//
////			DatabaseConnectionParameters local = new DatabaseConnectionParameters("DatabaseConfig.properties", "local");
////            DatabaseConnectionParameters central = new DatabaseConnectionParameters("DatabaseConfig.properties", "central");
////            factory = new ManagerFactory(local, central);
//            GenotypicDataManager manager=factory.getGenotypicDataManager();
//            
//            System.out.println("......................  :"+manager.getAllMaps(0, 15, Database.CENTRAL));
//        } catch (Exception e) { 
//        	e.printStackTrace();
//        } 
		//-----------
		
		return true;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {

	}

	@Override
	public void addPages() {
		addPage(new FilesImportPage("FilesImportPage"));
	}
}