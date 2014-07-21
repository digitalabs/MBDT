	package org.icrisat.mbdt.ui.dialog;

	import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

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
import org.generationcp.middleware.exceptions.ConfigException;
import org.generationcp.middleware.exceptions.MiddlewareQueryException;
import org.generationcp.middleware.manager.DatabaseConnectionParameters;
import org.generationcp.middleware.manager.ManagerFactory;
import org.generationcp.middleware.manager.api.MBDTDataManager;

	

	public class MbdtLoadProjectDialog extends Dialog {
		
		private String value = "";
		private String value1 = "";
		private Combo combo;
		private Combo comboGen;
		String projectName = "";
		String genName = "";
		DatabaseConnectionParameters local, central;
		ManagerFactory factory;
		MBDTDataManager mbdtmanager;
		public MbdtLoadProjectDialog(Shell parentShell) {
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
			 try {
					String url = Platform.getLocation().toString().substring(0, Platform.getLocation().toString().lastIndexOf("/")+1);
					 local = new DatabaseConnectionParameters(url+"DatabaseConfig.properties","local");
					 central = new DatabaseConnectionParameters(url+"DatabaseConfig.properties","central");
					 factory = new ManagerFactory(local, central);
					 mbdtmanager = factory.getMbdtDataManager();
					
					 
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} 
				try{ 
				for(int i = 0; i < mbdtmanager.getAllProjects().size(); i++){
						combo.add(mbdtmanager.getAllProjects().get(i).getProjectName());
					}
				}catch (MiddlewareQueryException e) {
						e.printStackTrace();
				}
				
			Label label1 = new Label(container,SWT.NONE);
			label1.setText("Generation");
			label1.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));
//			label.setLayoutData(new GridData(100,15));
			
				
			comboGen = new Combo(container, SWT.READ_ONLY);
			comboGen.setLayoutData(new GridData(150, 35));
			
			combo.addSelectionListener(new SelectionAdapter(){
				@Override
				public void widgetSelected(SelectionEvent e) {
					// TODO Auto-generated method stub
					if(comboGen.getText() != null){
						comboGen.removeAll();
					}
					try{ 
						value = combo.getText();
						int id = mbdtmanager.getProjectIDByProjectName(value);
						for(int i = 0; i < mbdtmanager.getAllGenerations(id).size(); i++){
							comboGen.add(mbdtmanager.getAllGenerations(id).get(i).getGenerationName());
							}
						}catch (MiddlewareQueryException e1) {
								e1.printStackTrace();
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
