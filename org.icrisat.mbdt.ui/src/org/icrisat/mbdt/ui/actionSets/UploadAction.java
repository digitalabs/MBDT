package org.icrisat.mbdt.ui.actionSets;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.icrisat.mbdt.model.GenotypeModel.Genotype;
import org.icrisat.mbdt.model.LinkageMapModel.LinkageMap;
import org.icrisat.mbdt.model.loader.GenotypeDataLoader;
import org.icrisat.mbdt.model.loader.LinkageMapDataLoader;
import org.icrisat.mbdt.model.sessions.Session;
import org.icrisat.mbdt.ui.views.AccessionListView;
import org.icrisat.mbdt.ui.views.LinkageMapTableView;
import org.icrisat.mbdt.ui.wizards.FilesImportWizard;

public class UploadAction implements IWorkbenchWindowActionDelegate {

	
	
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub
		
	}

	
	public void run(IAction action) {
		/* System.out.println("action" +action.getText());
		FileDialog fileDialog= new FileDialog(Display.getDefault().getActiveShell());
		fileDialog.setText("Upload the File");
		fileDialog.setFilterExtensions(new String[]{"*.txt","*.*"});	
		String selectedPath= fileDialog.open();
		if((action.getText()).equals("Upload Genotype")){
		GenotypeDataLoader gLoader= new GenotypeDataLoader();
		Genotype gType= gLoader.load(selectedPath);
		try {
			AccessionListView accession=  (AccessionListView) PlatformUI.getWorkbench().getActiveWorkbenchWindow ().getActivePage().showView(AccessionListView.class.getName());
			accession.setInput(gType);
		} catch (Exception e) {
			}
		//through Import Wizard..........
		
	}else if((action.getText()).equals("Upload LinkageMap")){
		System.out.println(" in Upload linkage map");
		LinkageMapDataLoader lLoader= new LinkageMapDataLoader();
		LinkageMap lMap= lLoader.load(selectedPath);
		try {
//			LinkageMapTableView mapTable=  (LinkageMapTableView) PlatformUI.getWorkbench().getActiveWorkbenchWindow ().getActivePage().showView(LinkageMapTableView.class.getName());
//			mapTable.setInput(lMap);
			Session.getInstance().setLMap(lMap);
		} catch (Exception e) {
			}
		
	}*/
		WizardDialog wDialog = new WizardDialog(Display.getDefault().getActiveShell(),new FilesImportWizard());
		wDialog.open();	
	}

	
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub	
	}

}
