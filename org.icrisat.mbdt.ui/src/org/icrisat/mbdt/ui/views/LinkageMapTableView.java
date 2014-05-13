package org.icrisat.mbdt.ui.views;


import java.text.Collator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

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
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.GenotypeModel.Genotype;
import org.icrisat.mbdt.model.LinkageMapModel.LinkageMap;
import org.icrisat.mbdt.model.notifiers.ILoadNotifier;
import org.icrisat.mbdt.model.sessions.Session;



public class LinkageMapTableView extends ViewPart implements ILoadNotifier, ISelectionListener{
	private TableViewer tViewer;
	private LinkageMapSorter linkageMapSorter;
	Listener sortListener ;
	Listener sortListener1 ;
	public void createPartControl(Composite parent) {
	final	Table table = new Table(parent, SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		
		final TableColumn tColumn1 = new TableColumn(table,SWT.None);
		tColumn1.setWidth(100);
		tColumn1.setText("Chromosome");
		sortListener = new Listener() {
			
	          public void handleEvent(Event e) {
	        	  // determine new sort column and direction
	              TableColumn sortColumn = LinkageMapTableView.this.tViewer.getTable().getSortColumn();
	              TableColumn currentColumn = (TableColumn) e.widget;
	              int dir = LinkageMapTableView.this.tViewer.getTable().getSortDirection();
	              if (sortColumn == currentColumn) {
	                  dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
	              } else {
	            	  LinkageMapTableView.this.tViewer.getTable().setSortColumn(currentColumn);
	                  dir = SWT.UP;
	              }
	              // sort the data based on column and direction
	              String sortIdentifier = tColumn1.getText();
	              linkageMapSorter = new LinkageMapSorter(sortIdentifier,dir);
	              LinkageMapTableView.this.tViewer.getTable().setSortDirection(dir);
	              LinkageMapTableView.this.tViewer.setSorter(linkageMapSorter);
	             
	          }
	     };
	     tColumn1.addListener(SWT.Selection, sortListener); 
		
	    final TableColumn tColumn2 = new TableColumn(table,SWT.NONE);
		tColumn2.setWidth(100);
		tColumn2.setText("Markers");
		sortListener1 = new Listener() {
			
	          public void handleEvent(Event e) {
	        	  // determine new sort column and direction
	              TableColumn sortColumn = LinkageMapTableView.this.tViewer.getTable().getSortColumn();
	              TableColumn currentColumn = (TableColumn) e.widget;
	              int dir = LinkageMapTableView.this.tViewer.getTable().getSortDirection();
	              if (sortColumn == currentColumn) {
	                  dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
	              } else {
	            	  LinkageMapTableView.this.tViewer.getTable().setSortColumn(currentColumn);
	                  dir = SWT.UP;
	              }
	              // sort the data based on column and direction
	              String sortIdentifier = tColumn2.getText();
	              linkageMapSorter = new LinkageMapSorter(sortIdentifier,dir);
	              LinkageMapTableView.this.tViewer.getTable().setSortDirection(dir);
	              LinkageMapTableView.this.tViewer.setSorter(linkageMapSorter);
	             
	          }
	     };
	     tColumn2.addListener(SWT.Selection, sortListener1); 
		
		TableColumn tColumn = new TableColumn(table,SWT.NONE);
		tColumn.setWidth(100);
		tColumn.setText("Numbering");
		
		tColumn = new TableColumn(table,SWT.NONE);
		tColumn.setWidth(100);
		tColumn.setText("Distance");
		
		tColumn = new TableColumn(table,SWT.NONE);
		tColumn.setWidth(100);
		tColumn.setText("Cum_Dist");
		
		tColumn = new TableColumn(table,SWT.NONE);
		tColumn.setWidth(100);
		tColumn.setText("Marker Status");
	
		tViewer = new TableViewer(table);
		
		CellEditor[] cellEditors= new CellEditor[6];
		cellEditors[0]= new TextCellEditor(table);
		cellEditors[1]= new TextCellEditor(table);
		cellEditors[2]= new TextCellEditor(table);
		cellEditors[3]= new TextCellEditor(table);
		cellEditors[4]= new TextCellEditor(table);
		cellEditors[5]= new ComboBoxCellEditor(table, new String[]{"Foreground", "Background"}, SWT.DefaultSelection);

		tViewer.setCellEditors(cellEditors);
		tViewer.setCellModifier(new ChromosomeTypeModifier(tViewer));
		tViewer.setColumnProperties(new String[] {"Chromosome", "Markers","Numbering","Distance","Cum_Dist","Marker Status"});
		tViewer.setContentProvider(new LinkageMapContentProvider());
		tViewer.setLabelProvider(new LinkageMapLabelProvider());
//		linkageMapSorter = new LinkageMapSorter();
//		tViewer.setSorter(linkageMapSorter);
		
		
		
		this.getSite().getPage().addSelectionListener(GraphicalView.class.getName(), this);
		
		//Registering with Session Variables...
		Session.getInstance().addNotifyListener(this);
		
		// Registering this class as a provider
		this.getSite().setSelectionProvider(tViewer);
		
	
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}

	public void notifyLoad(RootModel rootmodel) {
		if(this.tViewer.getContentProvider()!= null)
		{
		tViewer.setInput(rootmodel.getLinkagemap().get(0));
		}	
	}

	
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		// TODO Auto-generated method stub
		
		
}
public void setInput(LinkageMap linkagemap) {
		// TODO Auto-generated method stub
			tViewer.setInput(linkagemap);
	}
}
