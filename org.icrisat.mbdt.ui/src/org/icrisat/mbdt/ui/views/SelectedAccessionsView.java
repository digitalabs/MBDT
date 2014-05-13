package org.icrisat.mbdt.ui.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;
import org.icrisat.mbdt.gef.views.GraphicalView;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.GenotypeModel.Accessions;
import org.icrisat.mbdt.model.sessions.Session;

public class SelectedAccessionsView extends ViewPart implements ISelectionListener {

	private TableViewer tViewer;
	static int dbCount = 0;
	LinkageData ldata;
	static HashMap type= new HashMap();
	public SelectedAccessionsView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {

		Table table= new Table(parent, SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tColumn= new TableColumn(table, SWT.NONE);
		tColumn.setWidth(100);
		tColumn.setText("Name");

		tColumn= new TableColumn(table, SWT.NONE);
		tColumn.setWidth(100);
		tColumn.setText("Type");

		tViewer= new TableViewer(table);
		tViewer.setContentProvider(new SelectedAccessionContentProvider());
		tViewer.setLabelProvider(new SelectedAccessionLabelProvider());

		// Registering this class as a listener
		this.getSite().getPage().addSelectionListener(AccessionListView.class.getName(), this);

		
		CellEditor[] cellEditors= new CellEditor[2];
		cellEditors[0]= new TextCellEditor(table);
		cellEditors[1]= new ComboBoxCellEditor(table, new String[]{"Recurrent", "Donor"});

		tViewer.setCellEditors(cellEditors);
		tViewer.setCellModifier(new SelectedAccessionTypeModifier(tViewer));
		tViewer.setColumnProperties(new String[] {"Name", "Type"});
		
		// Registering this class as a provider
		this.getSite().setSelectionProvider(tViewer);

	}
	
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
	}

	// for catching the selected items from a tree
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		IStructuredSelection ss = (IStructuredSelection) selection;	
		List selectionPoints = new ArrayList<Accessions>();
		
		RootModel rootModel = RootModel.getRootModel();
		RootModel rModel = null;
		LinkageData linkage;
		if(rootModel.getLoadFlag() == null){				
			 linkage = LinkageData.getLinkageData();
		}else{
			rModel = Session.getInstance().getRootmodel();
			linkage = rModel.getLinkData();
		}
		linkage.setSortval("");
		for(Iterator iterator = ss.iterator(); iterator.hasNext();) {
			Accessions ap= (Accessions) iterator.next();
			type = linkage.getType();
			if(type == null){
				type = new HashMap();
				ap.setType("Recurrent");
			}else{
			if(ap.getType()==""){
			ap.setType("Recurrent");
			}
			}
			selectionPoints.add(ap);
			ap.setLoadAcc(selectionPoints);
			linkage.setLoadAcc(selectionPoints);
//			System.out.println("ACCESSIONLIST VIEW ::::"+ap.getName()+"...."+ap.getType());
			type.put(ap.getName(),ap.getType());
			
			linkage.setType(type);
		}
		linkage.setNGloadSelectedAcc(selectionPoints);
		
		if(this.tViewer.getContentProvider()!=null){
			tViewer.setInput(selectionPoints);
		}
//		performFinish();
	}
}
