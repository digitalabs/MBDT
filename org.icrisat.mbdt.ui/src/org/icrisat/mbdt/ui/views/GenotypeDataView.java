package org.icrisat.mbdt.ui.views;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.notifiers.ILoadNotifier;
import org.icrisat.mbdt.model.sessions.Session;

public class GenotypeDataView extends ViewPart implements ILoadNotifier {
	
	TableViewer gTViewer;
	
	public GenotypeDataView() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void createPartControl(Composite parent) {
		
		Table gTable= new Table(parent, SWT.NONE);
		gTable.setLinesVisible(true);
		gTable.setHeaderVisible(true);

		// Getting the number of columns
		TableColumn gColumn;
	
		// Registration for notifier
		Session.getInstance().addNotifyListener(this);
		int count= Session.getInstance().getRootmodel().getGenotype().get(0).getCount();
	
		for(int i=0; i<count;i++) {
		gColumn= new TableColumn(gTable, SWT.RIGHT);
		gColumn.setWidth(100);
		String header= Session.getInstance().getRootmodel().getGenotype().get(0).getHeaderList().get(i).toString();
		gColumn.setText(header);
		}
	
		gTViewer= new TableViewer(gTable);
		gTViewer.setContentProvider(new GenotypeContentProvider());
		gTViewer.setLabelProvider(new GenotypeLabelProvider());		
		
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	public void notifyLoad(RootModel rootmodel) {
		gTViewer.setInput(rootmodel);
		
	}
}
