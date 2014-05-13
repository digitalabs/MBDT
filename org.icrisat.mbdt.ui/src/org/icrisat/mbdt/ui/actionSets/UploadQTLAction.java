package org.icrisat.mbdt.ui.actionSets;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.QTLModel.QTL;
import org.icrisat.mbdt.model.QTLModel.QTLData;
import org.icrisat.mbdt.model.loader.QTLDataLoader;

public class UploadQTLAction implements IWorkbenchWindowActionDelegate {

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub

	}

	public void run(IAction action) {
		//for File Upload Dialog...
		FileDialog fileDialog = new FileDialog(Display.getDefault().getActiveShell());
		fileDialog.setText("Upload QTL File");
		fileDialog.setFilterExtensions(new String[]{"*.txt","*.*"});
		String filePath = fileDialog.open();
		
		//for instantiating class...
		QTLDataLoader qtlLoader = new QTLDataLoader();
		RootModel qtl = qtlLoader.load(filePath);
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
