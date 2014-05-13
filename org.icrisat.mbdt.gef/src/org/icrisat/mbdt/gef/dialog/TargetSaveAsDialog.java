package org.icrisat.mbdt.gef.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class TargetSaveAsDialog extends Dialog  {

	private Text text;
	private Text text1;
	private String value = "";
	private String value1 = "";

	public TargetSaveAsDialog(Shell parentShell) {
		
		super(parentShell);
		// TODO Auto-generated constructor stub
	}
		
	protected void configureShell(Shell newShell) {
		newShell.setText("Save Dialog");
		super.configureShell(newShell);
	}

	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent,SWT.NONE);
		container.setLayout(new GridLayout(15,false));
				
		Label label = new Label(container,SWT.NONE);
		label.setText("Study");
		label.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.BANNER_FONT));
//		label.setLayoutData(new GridData(100,15));
		
		text = new Text(container,SWT.BORDER);	
		text.setLayoutData(new GridData(200, 15));
		
		
		Label label1 = new Label(container,SWT.NONE);
//		label1.moveBelow(container);
		label1.setText("Save As");
		label1.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.BANNER_FONT));
//		label.setLayoutData(new GridData(100,15));
		
		text1 = new Text(container,SWT.BORDER);	
		text1.setLayoutData(new GridData(250, 15));		
		
		return container;
	}
	
	 /*
     *  Method declared on Dialog.
     */
	@Override
	protected void buttonPressed(int buttonId) {
		 if (buttonId == IDialogConstants.OK_ID) {
	         value = text.getText();
	         value1 = text1.getText();
	     } else {
	    	 value = null;
	    	 value1 = null;
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
