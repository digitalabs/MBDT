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
import org.eclipse.ui.part.ViewPart;
import org.icrisat.mbdt.model.GenotypeModel.Accessions;

public class MissingValuesView extends ViewPart {

	private TableViewer tViewer;
	List<Accessions>  accessions = new ArrayList<Accessions>();
	
	public MissingValuesView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		
		final Table tbl = new Table(parent,SWT.FULL_SELECTION);
		tbl.setHeaderVisible(true);
		tbl.setLinesVisible(true);
		
		TableColumn tColumn = new TableColumn(tbl,SWT.NONE);
		tColumn.setWidth(100);
		tColumn.setText("Accession");
		tColumn.addListener(SWT.Selection, new Listener() {
	        public void handleEvent(Event e) {
	        	
	        	 TableColumn sortColumn = tbl.getSortColumn();
	             TableColumn currentColumn = (TableColumn) e.widget;
	      	   int dir = tbl.getSortDirection();
	             if (sortColumn == currentColumn) {
	               dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
	             } else {
	            	 tbl.setSortColumn(currentColumn);
	               dir = SWT.UP;
	             }
	             final int direction = dir;
	             
	          // sort column 1
	          TableItem[] items = tbl.getItems();
	          
	          Collator collator = Collator.getInstance(Locale.getDefault());
	          for (int i = 1; i < items.length; i++) {
	            String value1 = items[i].getText(0);
	            for (int j = 0; j < i; j++) {
	              String value2 = items[j].getText(0);
	              
	              if (direction == SWT.UP) {
	              if (collator.compare(value1, value2) < 0) {
	                String[] values = { items[i].getText(0),items[i].getText(1)};
	                items[i].dispose();
	                TableItem item = new TableItem(tbl, SWT.NONE, j);
	                item.setText(values);
	                items = tbl.getItems();
	                break;
	              }
	              }else{
	            	  if (collator.compare(value1, value2) > 0) {
	  	                String[] values = { items[i].getText(0),items[i].getText(1)};
	  	                items[i].dispose();
	  	                TableItem item = new TableItem(tbl, SWT.NONE, j);
	  	                item.setText(values);
	  	                items = tbl.getItems();
	  	                break;
	  	              } 
	              }
	              
	            }
	          } tbl.setSortDirection(dir);
	        }
		 });
		
		TableColumn tColumn1 = new TableColumn(tbl,SWT.NONE);
		tColumn1.setWidth(100);
		tColumn1.setText("Markers");
		tViewer = new TableViewer(tbl);
		tViewer.setContentProvider(new MissingValuesContentProvider());
		tViewer.setLabelProvider(new MissingValuesLabelProvider());
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
	
	public void setInput(Accessions accessions2) {
		// TODO Auto-generated method stub
		accessions.add(accessions2);
		tViewer.setInput(accessions);
	}

}
