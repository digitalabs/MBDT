package org.icrisat.mbdt.gef.dialog;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.icrisat.mbdt.model.sessions.Session;

public class SearchAccessionDialog extends Dialog {

	private TableViewer viewer;
	List<SelectedAccessions> accession = new ArrayList<SelectedAccessions>();

	

	public SearchAccessionDialog(Shell parentShell) {
		super(parentShell);
		// TODO Auto-generated constructor stub
	}
	protected void configureShell(Shell newShell) {
		newShell.setText("Search Accessions");
		super.configureShell(newShell);
	}
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
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
		
		Genotype geno = (Genotype) rootModel1.getGenotype().get(0);
		List<Accessions> accstionlist = geno.getAccessions();
		List<SelectedAccessions> selectedAccessionList = new ArrayList<SelectedAccessions>();
		for (Accessions object : accstionlist) {
			selectedAccessionList.addAll(object.getSelectedAccessions());
		}
		
		viewer = new TableViewer(container, SWT.MULTI);
		viewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		viewer.setContentProvider(new AccessionContentProvider());
		viewer.setLabelProvider(new AccessionLabelProvider());
		
	
		viewer.setInput(selectedAccessionList);
		
		return container;
	}
	
	@Override
	protected void okPressed() {
		
		for (Iterator iterator = ((IStructuredSelection)viewer.getSelection()).iterator(); iterator.hasNext();) {
//			accession = (SelectedAccessions)((IStructuredSelection)viewer.getSelection()).getFirstElement();
			
			accession.add((SelectedAccessions) iterator.next());
			

		    }

		
	
		super.okPressed();
	}
	
	public List<SelectedAccessions> getAccession() {
		
		return  accession;
	}

}
