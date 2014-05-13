package org.icrisat.mbdt.ui.dialog;

import java.io.File;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.sessions.Session;

/**
 * SwapnaNair December 2009
 **/

public class IsmabSaveAsDialog extends Dialog  {

	private Text text;
	private Text text1;
	private String value = "";
	private String value1 = "";
	private Combo combo;
	private Combo comboGen;
	String folderName = "";
	String subFolder = "";
	Boolean flag;
	String project = "";
	String generation = "";
	Boolean loadFlagVal;
	
	public IsmabSaveAsDialog(Shell parentShell) {
		
		super(parentShell);
		// TODO Auto-generated constructor stub
	}
		
	protected void configureShell(Shell newShell) {
		newShell.setText("Save");
		super.configureShell(newShell);
	}

	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent,SWT.NONE);
		container.setLayout(new GridLayout(2,true));
		
		//container.setLayout(new GridLayout());
		
		RootModel rootModel = RootModel.getRootModel();
		if(rootModel.getLoadFlag() == null){
			
		}else{
			RootModel rootModel1 = Session.getInstance().getRootmodel();
			project = rootModel1.getLoadProject();
			generation = rootModel1.getLoadGeneration();
			loadFlagVal = rootModel1.getLoadFlag();
		}
		
				
		Label label = new Label(container,SWT.NONE);
		label.setText("Project");
		label.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));
//		label.setLayoutData(new GridData(100,15));
		
		/*text = new Text(container,SWT.BORDER);	
		text.setLayoutData(new GridData(200, 15));*/
		
		
		combo = new Combo(container, SWT.NULL);
		combo.setLayoutData(new GridData(150, 35));
		//combo.select(project);
		combo.setText(project);
		
		//ISMAB_Project files path....
		folderName = Platform.getLocation().getDevice()+"//MBDT_PROJECTSFiles";
		File dir = new File(folderName);
		
		if(!dir.exists()){
			//System.out.println("File not FOUND ************");
		}else{
			//Here getting the list of projects...
			for(int i = 0; i < dir.list().length; i++){
				//System.out.println("GGGGGGGGGGGG :"+dir.list()[i]);
				combo.add(dir.list()[i]);
			}
		}
		
		Label label1 = new Label(container,SWT.NONE);
//		label1.moveBelow(container);
		label1.setText("Generation");
		label1.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));
//		label.setLayoutData(new GridData(100,15));
		
		/*text1 = new Text(container,SWT.BORDER);	
		text1.setLayoutData(new GridData(200, 15));*/
		
		comboGen = new Combo(container, SWT.NULL);
		comboGen.setLayoutData(new GridData(150, 35));
		comboGen.setText(generation);
		
		combo.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				if(comboGen.getText() != null){
					comboGen.removeAll();
				}
				//System.out.println("TEXT :"+combo.getText());
				
				subFolder = folderName+"//"+combo.getText();
				File subDir = new File(subFolder);
				
				if(!subDir.exists()){
					
				}else{
					//Here getting the list of projects...
					for(int i = 0; i < subDir.list().length; i++){
//						System.out.println("Sub Directory :"+subDir.list()[i]);
						comboGen.add(subDir.list()[i]);
					}
					flag = true;
				}
				super.widgetSelected(e);
			}
		});
		
		/*combo.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				comboGen.removeAll();	
				//System.out.println("TEXT :"+combo.getText());
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				//System.out.println("TEXT keyReleased:"+combo.getText());
			}		
			
		});*/
		
		combo.addFocusListener(new FocusListener(){

		
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}

			
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if(comboGen.getText() != null){
					
					if(loadFlagVal ==  null){
						comboGen.removeAll();
					}else{
						
					}
				}
				subFolder = folderName+"//"+combo.getText();
				File subDir = new File(subFolder);
				flag = false;
				
				if(!subDir.exists()){
					comboGen.removeAll();
				}else{
					//Here getting the list of projects...
					if(!flag){
						for(int i = 0; i < subDir.list().length; i++){
							comboGen.add(subDir.list()[i]);
						}
					}
				}
			}
		});
		
		comboGen.addFocusListener(new FocusListener(){

			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
				if(combo.getText() == ""){
					comboGen.removeAll();
				}
				
			}

		
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			
			
		});
		/*comboGen.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				//comboGen.removeAll();	
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});*/
		
		
		return container;
	}
	
	 /*
     *  Method declared on Dialog.
     */
	@Override
	protected void buttonPressed(int buttonId) {
		 if (buttonId == IDialogConstants.OK_ID) {
//	        /* value = text.getText();
//	         value1 = text1.getText();*/
			
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
	
	/*public String getSelectedSaveType(){
		return comboText;
	}*/
}
