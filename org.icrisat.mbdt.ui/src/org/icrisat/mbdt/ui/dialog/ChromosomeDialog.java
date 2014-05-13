package org.icrisat.mbdt.ui.dialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.GenotypeModel.Genotype;
import org.icrisat.mbdt.model.sessions.Session;

public class ChromosomeDialog extends Dialog {

	private String value = "";
	private String value1 = "";
	private Combo combo;
	private Combo comboGen;
	LinkedHashSet<String> chromNames = new LinkedHashSet<String>();
	HashMap<String, String> markersMap = new HashMap<String, String>();
	String prevChrom = "";
	List<String> markersList = new ArrayList<String>();
	RootModel rModel;
	int chromCount = 0;
	
	
	
	public ChromosomeDialog(Shell parentShell) {
		super(parentShell);
		// TODO Auto-generated constructor stub
	}

	protected void configureShell(Shell newShell) {
		newShell.setText("Chromosome View");
		super.configureShell(newShell);
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent,SWT.NONE);
		container.setLayout(new GridLayout(2,true));
		
				
		Label label = new Label(container,SWT.NONE);
		label.setText("Chromosome");
		label.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));
		
	
		combo = new Combo(container, SWT.READ_ONLY);
		combo.setLayoutData(new GridData(150, 35));
		
		RootModel rootModel = RootModel.getRootModel();
		if(rootModel.getLoadFlag() == null){
			rModel = RootModel.getRootModel();
		}else{
			rModel = Session.getInstance().getRootmodel();
		}
		
		Genotype geno = (Genotype) rModel.getGenotype().get(0);
		
		for(int i = 0; i < rModel.getLinkagemap().get(0).getChromosomes().size(); i++){
			chromNames.add(rModel.getLinkagemap().get(0).getChromosomes().get(i).getChromosome());
			
			if(i == 0){
				chromCount = 0;
			}			
			if(!rModel.getLinkagemap().get(0).getChromosomes().get(i).getChromosome().equals(prevChrom)){
				chromCount = 0;
			}
			
			prevChrom = rModel.getLinkagemap().get(0).getChromosomes().get(i).getChromosome();
			chromCount++;
		}
		
		
		for(Iterator<String> chrIt = chromNames.iterator(); chrIt.hasNext();){
			//chromList.add(chrIt.next());
			combo.add(chrIt.next());
		}
		
		
			
		
		
			
		return container;
	}
	
	 /*
     *  Method declared on Dialog.
     */
	@Override
	protected void buttonPressed(int buttonId) {
		 if (buttonId == IDialogConstants.OK_ID) {
		
			if(combo.getText() == ""){
				MessageDialog.openWarning(getShell(), "warning", "Chromosome is required!");
				setReturnCode(OK);
				open();
			}
			
			 value = combo.getText();
	                  
	     } else {
	    	 value = null;
	    	
	     }		
		super.buttonPressed(buttonId);
	}
	
	 /**
	 * Returns the string typed into this dialog.
	 * 
	 * @return the input string
	 */
	public String getChromTextData() {
		return value;
	}
	
		
	/**Here we are overriding the createButtonsForButtonBar method in order to 
	 * hide default buttons and create our own button.
	 **/
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		// TODO Auto-generated method stub
		createButton(parent, IDialogConstants.OK_ID, "View", true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}
	

}
