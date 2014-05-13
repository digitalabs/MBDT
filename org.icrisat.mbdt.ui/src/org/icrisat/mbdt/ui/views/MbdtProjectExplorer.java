package org.icrisat.mbdt.ui.views;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.icrisat.mbdt.model.SessionManager;

public class MbdtProjectExplorer extends ViewPart implements PropertyChangeListener{

	private TreeViewer tViewer;

	public MbdtProjectExplorer() {
		SessionManager.getInstance().addListener(this);
	}

	@Override
	public void createPartControl(Composite parent) {
		tViewer = new TreeViewer(parent);
		tViewer.setContentProvider(new MbdtProjectExplorerContentProvider());
		tViewer.setLabelProvider(new MbdtProjectExplorerLabelProvider());
		tViewer.setInput(SessionManager.getInstance());
		
		tViewer.addDoubleClickListener(new IDoubleClickListener() {
			
			public void doubleClick(DoubleClickEvent event) {
				
				Object selectedObject = ((IStructuredSelection)tViewer.getSelection()).getFirstElement();
				if (selectedObject instanceof LinkageMapTableView) {
					try {
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("org.icrisat.mbdt.ui.views.LinkageMapTableView");
					} catch (PartInitException e) {
						// TODO Auto-generated catch block
					}
				} else if (selectedObject instanceof GenotypeDataView){
					try {
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("org.icrisat.mbdt.ui.views.GenotypeDataView");
					} catch (PartInitException e) {
						// TODO Auto-generated catch block
					}
				}
				
			}
		});
		
		getSite().setSelectionProvider(tViewer);
		
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("ADD_PROJECT")) {
			tViewer.refresh();
		}
		
	}

}
