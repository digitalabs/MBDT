package org.icrisat.mbdt.ui.wizards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.generationcp.middleware.exceptions.MiddlewareQueryException;
import org.generationcp.middleware.manager.Database;
import org.generationcp.middleware.manager.DatabaseConnectionParameters;
import org.generationcp.middleware.manager.GdmsType;
import org.generationcp.middleware.manager.ManagerFactory;
import org.generationcp.middleware.manager.api.GenotypicDataManager;
import org.generationcp.middleware.manager.api.MBDTDataManager;
import org.generationcp.middleware.pojos.gdms.Dataset;

import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.Datavalidation.SeconGenValidation;
import org.icrisat.mbdt.ui.Activator;

public class LoadTargetDatabasePage extends WizardPage implements ModifyListener, SelectionListener {

	private Combo combo;
	private Combo comboGen;
	private Combo comboGeno;
	private Text txtBox;
	
	String folderName = "";
	String subFolder = "";
	private Text lblSummary;
	DatabaseConnectionParameters local, central;
	ManagerFactory factory;
	MBDTDataManager mbdtmanager;
	GenotypicDataManager manager;
	List<Dataset> glist ;
	private boolean SecondGenboolean;
	
	protected LoadTargetDatabasePage(String pageName) {
		super(pageName);
		// TODO Auto-generated constructor stub
		setTitle("Load TargetData");
		setImageDescriptor(Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/load.gif"));
	}

	
	public void createControl(Composite parent) {
		// TODO Auto-generated method stub
		
		setPageComplete(false);
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		
		Label label = new Label(container,SWT.NONE);
		label.setText("Project");
		label.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));
		combo = new Combo(container, SWT.READ_ONLY);
		combo.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		try {
			String url = Platform.getLocation().toString().substring(0, Platform.getLocation().toString().lastIndexOf("/")+1);
			 local = new DatabaseConnectionParameters(url+"DatabaseConfig.properties","local");
			 central = new DatabaseConnectionParameters(url+"DatabaseConfig.properties","central");
			 factory = new ManagerFactory(local, central);
			 mbdtmanager = factory.getMbdtDataManager();
			 manager=factory.getGenotypicDataManager();
			 
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
		combo.addModifyListener(this);
		Label label1 = new Label(container,SWT.NONE);
		label1.setText("Generation");
		label1.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));
//		label.setLayoutData(new GridData(100,15));
		
			
		comboGen = new Combo(container, SWT.READ_ONLY);
		comboGen.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		comboGen.addModifyListener(this);
		combo.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				if(comboGen.getText() != null){
					comboGen.removeAll();
				}
				try{ 
					folderName = combo.getText();
					int id = mbdtmanager.getProjectIDByProjectName(folderName);
					for(int i = 0; i < mbdtmanager.getAllGenerations(id).size(); i++){
						comboGen.add(mbdtmanager.getAllGenerations(id).get(i).getGenerationName());
						}
					}catch (MiddlewareQueryException e1) {
							e1.printStackTrace();
					}
				super.widgetSelected(e);
			}
		});
			
		
		Label lblText = new Label(container, SWT.NONE);
		lblText.setText("Load Genotype");
		lblText.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));
		
		comboGeno = new Combo(container, SWT.READ_ONLY);
		comboGeno.setLayoutData(new GridData(GridData.FILL, GridData.END,true,false));
		comboGeno.addModifyListener(this);
		try {
			glist = manager.getDatasetsByType(GdmsType.TYPE_MAPPING);
		} catch (MiddlewareQueryException e) {
			e.printStackTrace();
		}

		for(int i=0;i<glist.size();i++){
			comboGeno.add(glist.get(i).getDatasetName());
		}
		
		setControl(container);
//		setPageComplete(true);
	}

	public String getLoadTargetSelected() {
		// TODO Auto-generated method stub
	
		String returnString = "";
		returnString = combo.getText()+"!@!"+comboGen.getText()+"!@!"+comboGeno.getText();
		return returnString;
	}

	
	public void modifyText(ModifyEvent e) {
					
		if((combo.getText() == "") && (comboGen.getText() == "") && (comboGeno.getText() == "")){
			setPageComplete(false);
		} else{
			setPageComplete(true);
		}
	}

	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}


	public void widgetSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
