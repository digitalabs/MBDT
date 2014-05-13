package org.icrisat.mbdt.ui.wizards;

import java.io.File;
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

import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.Datavalidation.SeconGenValidation;
import org.icrisat.mbdt.ui.Activator;

public class LoadTargetDataPage extends WizardPage implements ModifyListener, SelectionListener {

	private Combo combo;
	private Combo comboGen;
	private Text txtBox;
	private Button button;
	
	String folderName = "";
	String subFolder = "";
	private Text lblSummary;
	
	private boolean SecondGenboolean;
	
	protected LoadTargetDataPage(String pageName) {
		super(pageName);
		// TODO Auto-generated constructor stub
		setTitle("Load TargetData");
		setImageDescriptor(Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/load.gif"));
	}

	
	public void createControl(Composite parent) {
		// TODO Auto-generated method stub
		
		setPageComplete(false);
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout(3, false);
		gridLayout.verticalSpacing = 25;
		//container.setLayout(new GridLayout(2, true));
		container.setLayout(gridLayout);
		
		Label label = new Label(container,SWT.NONE);
		label.setText("Project");
		label.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));
		
	
		combo = new Combo(container, SWT.READ_ONLY);
		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gridData.horizontalSpan = 2;
		combo.setLayoutData(gridData);
		//combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		
		//MBDT_Project files path....
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
		
			
		comboGen = new Combo(container, SWT.READ_ONLY);
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gridData.horizontalSpan = 2;
		comboGen.setLayoutData(gridData);
		//comboGen.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
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
		comboGen.addModifyListener(this);
		
		Label lblText = new Label(container, SWT.NONE);
		lblText.setText("Load Genotype");
		lblText.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));
		
		
		txtBox = new Text(container,SWT.BORDER);			
		txtBox.setLayoutData(new GridData(GridData.FILL, GridData.CENTER,true,false));
		txtBox.addModifyListener(this);
		
		button = new Button(container,SWT.NONE);
		button.setText("Browse");
		button.addSelectionListener(this);
		
		
		Group group = new Group(container, SWT.NONE);
		GridData layoutData2 = new GridData(SWT.FILL, SWT.FILL, true, true);
		layoutData2.horizontalSpan = 2;
		group.setLayoutData(layoutData2);
		group.setLayout(new GridLayout(1, false));
		group.setText("Summary");
		group.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));
		lblSummary = new Text(group, SWT.MULTI| SWT.V_SCROLL| SWT.H_SCROLL);
		lblSummary.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true));
		lblSummary.setBackground(ColorConstants.white);
		lblSummary.setSize(60, 50);
		
		
		setControl(container);
	}

	public String getLoadTargetSelected() {
		// TODO Auto-generated method stub
	
		String returnString = "";
		returnString = combo.getText()+"!@!"+comboGen.getText()+"!@!"+txtBox.getText();
		return returnString;
	}

	
	public void modifyText(ModifyEvent e) {
		
		String noFile = "File Does not exists !!";
		
		if((combo.getText() == "") && (comboGen.getText() == "") && (txtBox.getText() == "") && !(new File(txtBox.getText()).exists())){
			setErrorMessage(noFile);
		}else if((txtBox.getText().length() != 0) && (!new File(txtBox.getText()).exists())){
			setErrorMessage(noFile);
			setPageComplete(false);
		} else{
			
			
			try {
			 
				
			SeconGenValidation  secondGenValidation = new SeconGenValidation();
			
			if ((e.getSource().equals(txtBox)) ) {
					List SecGenList =	secondGenValidation.SecondGenValidation(txtBox.getText());
					SecondGenboolean =true;
					String gValidation = "";
					String gError="";
					
					for(int i=0;i<SecGenList.size();i++){
						String type = (String) SecGenList.get(i);
						gValidation =gValidation+"\n"+type;
					}
					lblSummary.append(gValidation);
				}  
			 
				
				
				if(SecondGenboolean == true   ) {
				setErrorMessage(null);
				setPageComplete(true);
			}
				else{
				
				setPageComplete(false);
			}
			} catch (RuntimeException e1) {
				// TODO Auto-generated catch block
				setErrorMessage("Invalid File");
				setPageComplete(false);
			}
			
			
			
		}
	}

	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}


	public void widgetSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		FileDialog fileDialog = new FileDialog(Display.getDefault().getActiveShell());
		fileDialog.setFilterExtensions(new String[]{"*.txt","*.*"});
		String filePath = fileDialog.open();
		
		//setting FilePath in the textbox...
		String match =(e.getSource()).toString();
		if(match.equals("Button {Browse}")){			
			txtBox.setText(filePath);
		}
	}

}
