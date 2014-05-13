package org.icrisat.mbdt.ui.views;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.icrisat.mbdt.gef.views.GraphicalView;
import org.icrisat.mbdt.gef.views.TargetGenotype;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.GenotypeModel.Accessions;
import org.icrisat.mbdt.model.TargetGenotype.TargetGeno;
import org.icrisat.mbdt.model.notifiers.ILoadNotifier;
import org.icrisat.mbdt.model.sessions.Session;
import org.icrisat.mbdt.model.sessions.SessionChromosome;
import org.icrisat.mbdt.ui.Activator;

public class AccessionListView extends ViewPart implements ILoadNotifier {

	private TreeViewer tViewer;
	public Action refresh;
	private IMemento memento;
	private ExampleViewerFilter exampleViewerFilter;
	List<Accessions> selectedAccessions;
	boolean filterOn;
	
	public boolean isFilterOn() {
		return filterOn;
	}
	
	public void setFilterOn(boolean filterOn) {
		this.filterOn = filterOn;
	}
	
	public void createPartControl(Composite parent) {
		final Label lbl=new Label(parent, SWT.LEFT);
		
		
		parent.setLayout(new GridLayout());
		//for adding a text box for searching...
			
		lbl.setText("Filter:");
//		new Label(parent, SWT.LEFT).setText("Filter:");
		final Text txt1 = new Text(parent,SWT.COLOR_GRAY);

		final Text txt = new Text(parent,SWT.BORDER);
		
		txt.addModifyListener(new ModifyListener(){			

			
			public void modifyText(ModifyEvent e) {
				
				
				
				exampleViewerFilter.setFilterText(txt.getText());
				tViewer.refresh();
				String msg = exampleViewerFilter.getCount()+" matches found out of "+exampleViewerFilter.getTotalcount();
				txt1.setText(msg);
				
				if (txt.getText().length() < 1) {
					setFilterOn(false);
				} else {
					setFilterOn(true);
				}
				
				
				if (!isFilterOn())
					tViewer.setSelection(new StructuredSelection(getSelectedAccessions()));
			}			
		});		
		
		
		
//		//setting the layout for Text box....
		txt.setLayoutData(new GridData(GridData.FILL,GridData.CENTER,true,false));
		txt1.setLayoutData(new GridData(GridData.FILL,GridData.CENTER,true,false));
		
		tViewer= new TreeViewer(parent);
		tViewer.setContentProvider(new AccessionListContentProvider());
		tViewer.setLabelProvider(new AccessionListLabelProvider());
		
		
		exampleViewerFilter = new ExampleViewerFilter();
		tViewer.addFilter(exampleViewerFilter);
		
		//for static filter ---tViewer.addFilter(new ExampleViewerFilter());
		//setting the layout for Tree Viewer......
		tViewer.getTree().setLayoutData(new GridData(GridData.FILL, GridData.FILL,true,true));
		
		// Registering the treeViewer as a selectionProvider
		this.getSite().setSelectionProvider(tViewer);
			
		tViewer.addPostSelectionChangedListener(new ISelectionChangedListener() {
			
			
			public void selectionChanged(SelectionChangedEvent arg0) {
			
				boolean clearText = false;
				IStructuredSelection iStructuredSelection = (IStructuredSelection)tViewer.getSelection();
				Iterator accessionIterator = iStructuredSelection.iterator();
				
				if (isFilterOn() && getSelectedAccessions().size() > 0 && !iStructuredSelection.isEmpty()) {
					
				if(getSelectedAccessions().size()<iStructuredSelection.size()){
					boolean result = MessageDialog.openQuestion(Display.getDefault().getActiveShell(), "Append Accessions !", "Do you want to append the Selected Accession to the already selected List ?");
					if (!result) {
						getSelectedAccessions().clear();
					}
					clearText = true;
				}
				} 
				
				for (Iterator iterator = accessionIterator; iterator.hasNext();) {
					Accessions accession = (Accessions) iterator.next();
					
					getSelectedAccessions().add(accession);
									
				}
				
				if (clearText) {
					txt.setText("");
				}
			}
		});
		
		// Registration for notifier
		Session.getInstance().addNotifyListener(this);
		//restoreState();
		//this.tViewer.setSelection(new StructuredSelection(tViewer.getInput()));
		
		//For creating default Config file.....
		if(!new File("MbdtLoadConfigFile.ser").exists()){
			try {
				FileOutputStream fos = new FileOutputStream("MbdtLoadConfigFile.ser");			
				fos.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
			}
		}
	}

	@Override
	public void setFocus() {
	}
	
	public List<Accessions> getSelectedAccessions() {
		if (selectedAccessions == null) {
			selectedAccessions = new  ArrayList<Accessions>();
		}
		return selectedAccessions;
	}

	public void notifyLoad(RootModel rootmodel) {
		
		if(rootmodel.getGenotype().size() > 0){
			tViewer.setInput(rootmodel.getGenotype().get(0));
			if((rootmodel.getGenotype().get(0).getNGHidingElement().size() > 0) && (rootmodel.getGenotype().get(0).getNGHidingStatus() == true)){
				tViewer.setSelection(new StructuredSelection(rootmodel.getGenotype().get(0).getNGHidingElement()));
				rootmodel.getGenotype().get(0).setNGHidingStatus(false);
			}
		}
		
//		System.out.println("123456 :::"+rootmodel.getGenotype().get(0).getAccessions());
	}
	
	public void makeActions() {
		refresh= new Action("Refresh", SWT.PUSH){
			public void run() {
				if(refresh.isEnabled()) {
					GraphicalView gv= new GraphicalView();
				}
			}
		};
	}
	
}
