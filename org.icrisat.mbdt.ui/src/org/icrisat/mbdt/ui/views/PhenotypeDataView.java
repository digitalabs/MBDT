package org.icrisat.mbdt.ui.views;

import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import org.icrisat.mbdt.gef.views.GraphicalView;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.GenotypeModel.Accessions;
import org.icrisat.mbdt.model.PhenotypeModel.Phenotype;
import org.icrisat.mbdt.model.sessions.Session;
import org.icrisat.mbdt.ui.views.MissingValuesContentProvider;
import org.icrisat.mbdt.ui.views.MissingValuesLabelProvider;

public class PhenotypeDataView extends ViewPart {
	private TableViewer tViewer;
	List<Accessions>  accessions = new ArrayList<Accessions>();
	Listener sortListener ;
	
	private SortListener phenosort;
	public PhenotypeDataView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		
		final Table tbl = new Table(parent,SWT.FULL_SELECTION);
		tbl.setHeaderVisible(true);
		tbl.setLinesVisible(true);
		
		final TableColumn tColumn = new TableColumn(tbl,SWT.NONE);
		tColumn.setWidth(100);
		tColumn.setText("Genotype/Accession");		
		
		
		sortListener = new Listener() {
			 
	          public void handleEvent(Event e) {
	        	  // determine new sort column and direction
	              TableColumn sortColumn = PhenotypeDataView.this.tViewer.getTable().getSortColumn();
	              TableColumn currentColumn = (TableColumn) e.widget;
	              int dir = PhenotypeDataView.this.tViewer.getTable().getSortDirection();
	              if (sortColumn == currentColumn) {
	                  dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
	              } else {
	            	  PhenotypeDataView.this.tViewer.getTable().setSortColumn(currentColumn);
	                  dir = SWT.UP;
	              }
	              
	              
	              // sort the data based on column and direction
	              String sortIdentifier = tColumn.getText();
	              phenosort = new SortListener(sortIdentifier,dir);
	              phenosort.propertyIndex=-1;
	              PhenotypeDataView.this.tViewer.getTable().setSortDirection(dir);
	              PhenotypeDataView.this.tViewer.setSorter(phenosort);
	              
	              getSortedAccessions();
	             
	             
	          }
	     };
	     
	     tColumn.addListener(SWT.Selection, sortListener); 
	    
		
		
		RootModel rootModel = Session.getInstance().getRootmodel();
				
		final TableColumn tColumn1[] = new TableColumn[rootModel.getPhenotype().get(0).getAccessions().get(0).getTraits().size()];
		try{
		
		for(  int i = 0; i < rootModel.getPhenotype().get(0).getAccessions().get(0).getTraits().size(); i++){
//		while(rootModel.getPhenotype().get(0).getAccessions().get(0).getTraits().iterator().hasNext()){
//		int i=0;
		tColumn1[i] = new TableColumn(tbl,SWT.NONE);
		tColumn1[i].setWidth(100);
		tColumn1[i].setText(rootModel.getPhenotype().get(0).getAccessions().get(0).getTraits().get(i));
		final int j=i;
		sortListener = new Listener() {
			
	          public void handleEvent(Event e) {
	        	  // determine new sort column and direction
	              TableColumn sortColumn = PhenotypeDataView.this.tViewer.getTable().getSortColumn();
	              TableColumn currentColumn = (TableColumn) e.widget;
	              int dir = PhenotypeDataView.this.tViewer.getTable().getSortDirection();
	              if (sortColumn == currentColumn) {
	                  dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
	              } else {
	            	  PhenotypeDataView.this.tViewer.getTable().setSortColumn(currentColumn);
	                  dir = SWT.UP;
	              }
	              
	              
	              // sort the data based on column and direction
	              String sortIdentifier = tColumn1[j].getText();
	              phenosort = new SortListener(sortIdentifier,dir);
	              phenosort.propertyIndex=j;
	              PhenotypeDataView.this.tViewer.getTable().setSortDirection(dir);
	              PhenotypeDataView.this.tViewer.setSorter(phenosort);
	              getSortedAccessions();
	          }
	     };
	     
	     tColumn1[j].addListener(SWT.Selection, sortListener); 
	     
		}
		}catch(Exception e){
		}
		tViewer = new TableViewer(tbl);
		tViewer.setContentProvider(new PhenotypeDataContentProvider());
		tViewer.setLabelProvider(new PhenotypeDataLabelProvider());

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	public void setInput(Accessions accessions2) {
		// TODO Auto-generated method stub
		if(!accessions.contains(accessions2))
		accessions.add(accessions2);
		tViewer.setInput(accessions);
	}

	private void getSortedAccessions() {
		List<String> accessionName = new ArrayList<String>();
		  for(int i=0; i<tViewer.getTable().getItems().length; i++){
			  TableItem tblitem  = tViewer.getTable().getItem(i);
			  Accessions phenotype = (Accessions)tblitem.getData();
			  accessionName.add(phenotype.getName());
		  }
		  
		 GraphicalView gView = (GraphicalView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView("org.icrisat.mbdt.gef.views.GraphicalView");
	     gView.setSort(accessionName); 
	}
	

}
