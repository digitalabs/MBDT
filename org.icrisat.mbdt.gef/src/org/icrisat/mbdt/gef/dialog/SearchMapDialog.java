package org.icrisat.mbdt.gef.dialog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.GenotypeModel.Accessions;
import org.icrisat.mbdt.model.GenotypeModel.Genotype;
import org.icrisat.mbdt.model.GenotypeModel.SelectedAccessions;
import org.icrisat.mbdt.model.LinkageMapModel.Intervals;
import org.icrisat.mbdt.model.LinkageMapModel.LinkageMap;
import org.icrisat.mbdt.model.LinkageMapModel.MapMarkers;
import org.icrisat.mbdt.model.sessions.Session;

public class SearchMapDialog extends Dialog   {
	private TableViewer viewer;
	
	MapMarkers marker = new MapMarkers();;

	

	public SearchMapDialog(Shell parentShell) {
		super(parentShell);
	}
	
	protected void configureShell(Shell newShell) {
		newShell.setText("Search Markers");
		super.configureShell(newShell);
	}
	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout());
//		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridData data = new GridData(GridData.FILL_BOTH);
	    data.verticalSpan = 200;
	    data.heightHint = 300;
	    data.widthHint = 250;
	    container.setLayoutData(data);
		Text searchTxt = new Text(container, SWT.BORDER);
		searchTxt.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
		
		RootModel rootModel = RootModel.getRootModel();
		LinkageData linkage;
		RootModel rootModel1;
		
		
		if(rootModel.getLoadFlag() == null){
			rootModel1 = RootModel.getRootModel();
			linkage = LinkageData.getLinkageData();
		}else{
			rootModel1 = Session.getInstance().getRootmodel();
			linkage = rootModel.getLinkData();
		}
		
		
		/*Genotype geno = (Genotype) rootModel1.getGenotype().get(0);
		List<Accessions> accstionlist = geno.getAccessions();
		
		
		List<SelectedAccessions> selectedAccessionList = new ArrayList<SelectedAccessions>();
		for (Accessions object : accstionlist) {
			selectedAccessionList.addAll(object.getSelectedAccessions());
		}*/
		
		LinkageMap Map = (LinkageMap)rootModel1.getLinkagemap().get(0);
		
//		List<MapMarkers> maplist = Map.getChromosomes().get(0).getMarkers();
		List<MapMarkers> maplist = new ArrayList<MapMarkers>();
		
		for (Intervals chromosome : Map.getChromosomes()) {
			maplist.addAll(chromosome.getMarkers());
		}
		
		viewer = new TableViewer(container, SWT.NONE);
		viewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		viewer.setContentProvider(new MapContentProvider());
		viewer.setLabelProvider(new MapLabelProvider());
		
	
		viewer.setInput(maplist);
		
		return container;
	}
	
	protected void okPressed() {
		
		marker = (MapMarkers)((IStructuredSelection)viewer.getSelection()).getFirstElement();
			
		super.okPressed();
	}
	
	public MapMarkers getMarkar() {
		return marker;
	}

}
