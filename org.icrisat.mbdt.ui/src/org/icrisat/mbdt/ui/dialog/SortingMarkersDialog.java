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

public class SortingMarkersDialog extends Dialog {
	
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
	
	public SortingMarkersDialog(Shell parentShell) {
		super(parentShell);
		// TODO Auto-generated constructor stub
	}
	
	protected void configureShell(Shell newShell) {
		newShell.setText("Sorting Markers");
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
			
//			System.out.println(geno.getHeaderList()+":::::::"+rModel.getLinkagemap().get(0).getChromosomes().get(i).getMap_marker());
			if(geno.getHeaderList().contains(rModel.getLinkagemap().get(0).getChromosomes().get(i).getMap_marker())){
				markersMap.put(rModel.getLinkagemap().get(0).getChromosomes().get(i).getChromosome()+"_"+chromCount, rModel.getLinkagemap().get(0).getChromosomes().get(i).getMap_marker());
			}
			
			
//			markersMap.put(rModel.getLinkagemap().get(0).getChromosomes().get(i).getChromosome()+"_"+chromCount, rModel.getLinkagemap().get(0).getChromosomes().get(i).getMap_marker());
			
			prevChrom = rModel.getLinkagemap().get(0).getChromosomes().get(i).getChromosome();
			chromCount++;
		}
		
		
		for(Iterator<String> chrIt = chromNames.iterator(); chrIt.hasNext();){
			//chromList.add(chrIt.next());
			combo.add(chrIt.next());
		}
		
		
		Label label1 = new Label(container,SWT.NONE);
		label1.setText("Markers");
		label1.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));
//		label.setLayoutData(new GridData(100,15));
		
			
		comboGen = new Combo(container, SWT.READ_ONLY);
		comboGen.setLayoutData(new GridData(150, 35));
		
		combo.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				if(comboGen.getText() != null){
					comboGen.removeAll();
				}
				
				for(int marker = 0; marker < rModel.getLinkagemap().get(0).getChromosomes().size(); marker++){
					if(markersMap.get(combo.getText()+"_"+marker) !=null){
						comboGen.add(markersMap.get(combo.getText()+"_"+marker));
					}
				}
				
				super.widgetSelected(e);
			}
		});
			
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
			
			if(comboGen.getText() == ""){
				MessageDialog.openWarning(getShell(), "warning", "Marker is required!");
				setReturnCode(OK);
				open();
			}
				
			 value = combo.getText();
	         value1 = comboGen.getText();
	         
	     } else {
	    	 value = null;
	    	 value1 = null;
	    	 //comboText = null;
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
	
	public String getMarkerTextData() {
		return value1;
	}
	
	/**Here we are overriding the createButtonsForButtonBar method in order to 
	 * hide default buttons and create our own button.
	 **/
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		// TODO Auto-generated method stub
		createButton(parent, IDialogConstants.OK_ID, "Sort", true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}
	

}
