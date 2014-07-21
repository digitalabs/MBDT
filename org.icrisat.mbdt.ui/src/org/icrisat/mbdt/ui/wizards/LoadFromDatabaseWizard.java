package org.icrisat.mbdt.ui.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.generationcp.middleware.exceptions.MiddlewareQueryException;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.loader.GenotypeDatasetLoader;
import org.icrisat.mbdt.model.loader.LinkageMapDataLoader1;
import org.icrisat.mbdt.model.loader.LinkageMapDatasetLoader;
import org.icrisat.mbdt.model.loader.PhenotypeDataLoader;
import org.icrisat.mbdt.model.loader.PhenotypeDatasetLoader;
import org.icrisat.mbdt.model.loader.QTLDataLoader;
import org.icrisat.mbdt.model.loader.QTLDatasetLoader;
import org.icrisat.mbdt.model.sessions.Session;
import org.icrisat.mbdt.ui.views.LinkageMapTableView;

public class LoadFromDatabaseWizard extends Wizard implements IImportWizard {
	int flag = 0;
	static int count = 0;
	public LoadFromDatabaseWizard() {
		// TODO Auto-generated constructor stub
		this.setWindowTitle("Load dataset");
	}

	@Override
	public boolean performFinish() {
		LoadFromDatabasePage page = (LoadFromDatabasePage)getPage("LoadFromDatabasePage");
		String returnString = null;
		returnString = page.getFilePath();
		String linkageMapPath = null;
		String qTLPath = null;
		String[] filePath = null;
		
filePath = returnString.split("!@!");
		
		
		for(int i = 0 ; i < filePath.length; i++) {
//			System.out.println("proj"+filePath[0]);
			if(filePath[i].equalsIgnoreCase("Genotype Dataset")){
				GenotypeDatasetLoader gLoader = new GenotypeDatasetLoader();
				// for Genotype File Upload....
				RootModel gType = gLoader.loadGenotype(filePath[i+1]);				
				//Using Session Variables....
				gType.setGeneration("First");
				Session.getInstance().setRootModel(gType);				
			} 
			if(filePath[i].equalsIgnoreCase("Linkage Map Dataset")){
				//for Linked Map File Upload....
				LinkageMapDatasetLoader gLoader= new LinkageMapDatasetLoader();
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
			if(filePath[i].equalsIgnoreCase("QTL Dataset")) {

				//for QTL File Upload....
				QTLDatasetLoader qtlLoader = new QTLDatasetLoader();
				RootModel rootModel = null;
				try {
					rootModel = qtlLoader.load(filePath[i+1]);
				} catch (MiddlewareQueryException e) {
				}
				
				if(flag==0) {
					LinkageMapDataLoader1 gLoader= new LinkageMapDataLoader1();
					RootModel gType = gLoader.load(filePath[i+1]);
				}
				Session.getInstance().setRootModel(rootModel);
			}
			if(filePath[i].equalsIgnoreCase("Phenotype Dataset")) {

				//for Phenotype File Upload....
				PhenotypeDatasetLoader pLoader = new PhenotypeDatasetLoader();
				RootModel rootModel = pLoader.loadPhenotype(filePath[i+1]);
				Session.getInstance().setRootModel(rootModel);
			}
			
		
		}
		
		return true;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub

	}
	public void addPages() {
		addPage(new LoadFromDatabasePage("LoadFromDatabasePage"));
	}
}
