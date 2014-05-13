package org.icrisat.mbdt.ui.dialog;

import java.io.File;

import org.eclipse.core.runtime.Platform;
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

/**
 * SwapnaNair April 2010
 **/

public class IsmabLoadDialog extends Dialog {
	
	private String value = "";
	private String value1 = "";
	private Combo combo;
	private Combo comboGen;
	String folderName = "";
	String subFolder = "";
	
	public IsmabLoadDialog(Shell parentShell) {
		super(parentShell);
		// TODO Auto-generated constructor stub
	}
	protected void configureShell(Shell newShell) {
		newShell.setText("Load...");
		super.configureShell(newShell);
	}

	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent,SWT.NONE);
		container.setLayout(new GridLayout(2,true));
		
				
		Label label = new Label(container,SWT.NONE);
		label.setText("Project");
		label.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));
		
	
		combo = new Combo(container, SWT.READ_ONLY);
		combo.setLayoutData(new GridData(150, 35));
		
		//ISMAB_Project files path....
		folderName = Platform.getLocation().getDevice()+"//MBDT_PROJECTSFiles";
		File dir = new File(folderName);
		
		if(!dir.exists()){
			//System.out.println("File not FOUND ************");
		}else{
			//Here getting the list of projects...
			for(int i = 0; i < dir.list().length; i++){
				combo.add(dir.list()[i]);
			}
		}
		
		Label label1 = new Label(container,SWT.NONE);
		label1.setText("Generation");
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
				
				subFolder = folderName+"//"+combo.getText();
				File subDir = new File(subFolder);
				
				if(!subDir.exists()){
					
				}else{
					//Here getting the list of projects...
					for(int i = 0; i < subDir.list().length; i++){
						comboGen.add(subDir.list()[i]);
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
				MessageDialog.openWarning(getShell(), "warning", "Project is required!");
				setReturnCode(OK);
				open();
			}
			
			if(comboGen.getText() == ""){
				MessageDialog.openWarning(getShell(), "warning", "Generation is required!");
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
	public String getTextData() {
		return value;
	}
	
	public String getText1Data() {
		return value1;
	}
}
