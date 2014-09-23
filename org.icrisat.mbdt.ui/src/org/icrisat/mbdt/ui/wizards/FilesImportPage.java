package org.icrisat.mbdt.ui.wizards;

import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.icrisat.mbdt.gef.views.TargetGenotype;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.Datavalidation.GenotypeValidation;
import org.icrisat.mbdt.model.Datavalidation.LinkageMapValidation;
import org.icrisat.mbdt.model.Datavalidation.PhenotypeValidation;
import org.icrisat.mbdt.model.Datavalidation.QtlValidation;
import org.icrisat.mbdt.ui.Activator;
public class FilesImportPage extends WizardPage implements SelectionListener, ModifyListener {

	private Label label1;
	private Label label2;
	private Label label3;
	private Label label4;
	private Text txt1;
	private Text txt2;
	private Text txt3;
	private Text txt4;
	private Button btn1;
	private Button btn2;
	private Button btn3;
	private Button btn4;
	private boolean genotypeLoaderboolean;
	private boolean linkageLoaderbollean;
	private boolean qtlLoaderbollean;
	private boolean phenoLoaderbollean;
	private Text lblSummary;
	private Text lblError;
	private static Boolean isGenotype=false;
	
	private Label label5;
	private Combo combo;
	  String[] haploidlevel = new String[]{"1", "2" };
	protected FilesImportPage(String pageName) {
		super(pageName);
		setTitle("Upload Files");
		//setDescription("Upload Files");
		setImageDescriptor(Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/Upload.gif"));
	}

	public void createControl(Composite parent) {
		setPageComplete(false);
		Composite container = new Composite(parent,SWT.NONE);
//		container.setLayout(new GridLayout(2,false));
		GridLayout gridLayout = new GridLayout();
 		gridLayout.numColumns = 3;
 		container.setLayout(gridLayout);
		
 		try{
				label1 = new Label(container,SWT.NONE);
				label1.setText("Genotype File");
				
				txt1 = new Text(container,SWT.BORDER);	
				txt1.setLayoutData(new GridData(GridData.FILL, GridData.CENTER,true,false));
				txt1.addModifyListener(this);
				
				btn1 = new Button(container,SWT.NONE);
				btn1.setText("Browse");			
				btn1.addSelectionListener(this);
				 
				label5= new Label(container,SWT.NONE);
				label5.setText("Columns per Marker");
				
				 GridData gridData1 = new GridData(GridData.FILL_HORIZONTAL);
				  gridData1.grabExcessHorizontalSpace = true;
				    
				combo = new Combo(container,SWT.READ_ONLY | SWT.DROP_DOWN);
				combo.setLayoutData(gridData1);
				combo.setItems(haploidlevel);
				combo.addModifyListener(this);
			
				 new Label(container, SWT.NULL);
				
			
				label2 = new Label(container,SWT.NONE);
				label2.setText("Linkage Map File");
				
				txt2 = new Text(container,SWT.BORDER);			
				txt2.setLayoutData(new GridData(GridData.FILL, GridData.CENTER,true,false));
				txt2.addModifyListener(this);
				
				btn2 = new Button(container,SWT.NONE);
				btn2.setText(" Browse");
				btn2.addSelectionListener(this);
				label3 = new Label(container,SWT.NONE);
				label3.setText("QTL File");
				
				txt3 = new Text(container,SWT.BORDER);			
				txt3.setLayoutData(new GridData(GridData.FILL, GridData.CENTER,true,false));
				txt3.addModifyListener(this);
				
				btn3 = new Button(container,SWT.NONE);
				btn3.setText("Browse ");
				btn3.addSelectionListener(this);
			
				label4 = new Label(container,SWT.NONE);
				label4.setText("Phenotype File");
				
				txt4 = new Text(container,SWT.BORDER);			
				txt4.setLayoutData(new GridData(GridData.FILL, GridData.CENTER,true,false));
				txt4.addModifyListener(this);
				
				btn4 = new Button(container,SWT.NONE);
				btn4.setText(" Browse ");
				btn4.addSelectionListener(this);
			
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
		lblSummary.setSize(260, 150);
		
		
		Group group1 = new Group(container, SWT.NONE);
		GridData layoutData3 = new GridData(SWT.FILL, SWT.FILL, true, true);
		layoutData3.horizontalSpan = 2;
		group1.setLayoutData(layoutData3);
		group1.setLayout(new GridLayout(1, false));
		group1.setText("Warnings");
		try {
			String[]  agrs= Platform.getCommandLineArgs();
			File file =  new File(agrs[0]);
			
			String url = file.getCanonicalPath().toString().substring(0, file.getCanonicalPath().toString().indexOf("-product"));
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TargetGenotype.class.getName()).getViewSite().getActionBars().getStatusLineManager().setMessage(file.getAbsolutePath());
			
		} catch (IOException e) {
		// TODO Auto-generated catch block
		}
		
		
		group1.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));
		lblError  = new Text(group1, SWT.MULTI| SWT.V_SCROLL| SWT.H_SCROLL);
		lblError.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true));
		lblError.setBackground(ColorConstants.white);
		lblError.setForeground(ColorConstants.red);
		lblError.setSize(260, 150);
		}catch(Exception e){
		}
		setControl(container);		
	}

	public String getFilePath() {		
		String returnString="";
		
		if(!(txt1.getText().equals("")) || (txt1.getText()!="")){
			
			returnString = label1.getText()+"!@!"+txt1.getText()+"!@!";
			
		} 
		if(!(txt2.getText().equals("")) || (txt2.getText()!="")){
			
			returnString = returnString+label2.getText()+"!@!"+txt2.getText()+"!@!";
		} 
		if(!(txt3.getText().equals("")) || (txt3.getText()!="")){
			
			returnString = returnString+label3.getText()+"!@!"+txt3.getText()+"!@!";
			
		}
		if(!(txt4.getText().equals("")) || (txt4.getText()!="")){
			
			returnString = returnString+label4.getText()+"!@!"+txt4.getText()+"!@!";
			
		}
		//return txt.getText()+"!@!"+genoLabel.getText();
		return returnString;
	}

	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void widgetSelected(SelectionEvent e) {
		FileDialog fileDialog = new FileDialog(Display.getDefault().getActiveShell());
		fileDialog.setFilterExtensions(new String[]{"*.txt","*.*"});
		String filePath = fileDialog.open();
		
		//setting FilePath in the textbox...
		String match=(e.getSource()).toString();
		if(match.equals("Button {Browse}")){			
			txt1.setText(filePath);
		}else if(match.equals("Button { Browse}")){
			txt2.setText(filePath);
		}else if(match.equals("Button {Browse }")){
			txt3.setText(filePath);
		}else if(match.equals("Button { Browse }")){
			txt4.setText(filePath);
		}
		
	}

	public void modifyText(ModifyEvent e) {
		
		String tBox1 = txt1.getText();
		String tBox2 = txt2.getText();
		String tBox3 = txt3.getText();
		String tBox4 = txt4.getText();
		String tBox5 = combo.getText();
		
		String validMsg = "Enter a valid File Path !!";
		String noFile = "File Does not exists !!";
		
		if((tBox1.length() == 0) && (tBox2.length() == 0) && (tBox3.length() == 0) && (tBox4.length() == 0)){
			setErrorMessage(validMsg);					
		}else if((!new File(tBox1).exists()) && (!new File(tBox2).exists()) && (!new File(tBox3).exists()) && (!new File(tBox4).exists())){
			setErrorMessage(noFile);
			setPageComplete(false);					
		}else if((tBox2.length() != 0) && (!new File(tBox2).exists())){
			setErrorMessage(noFile);
			setPageComplete(false);	
		}else if((tBox3.length() != 0) && (!new File(tBox3).exists())){
			setErrorMessage(noFile);
			setPageComplete(false);	
		}else if((tBox4.length() != 0) && (!new File(tBox4).exists())){
			setErrorMessage(noFile);
			setPageComplete(false);	
		} 		
		else{
			
			try {
				RootModel rootModel = RootModel.getRootModel();
				
				
				if ( ! (e.getSource().equals(txt2)) && ! (e.getSource().equals(txt3)) && (!e.getSource().equals(txt4))   &&!(tBox5.equals(""))) {
					try {
						rootModel.setGenotypePath(tBox1);
						rootModel.setHaploidlevel(tBox5);

						GenotypeValidation  GenoValidation = new GenotypeValidation();					
						genotypeLoaderboolean =true;
						String gValidation = "";
						String gError="";
						List genotest =GenoValidation.GenotypeValidation();
						
						
						for(int i=0;i<genotest.size();i++){
							String type = (String) genotest.get(i);
							
							if(i<5){
							gValidation =gValidation+"\n"+type;
							}
							else{
							gError=gError+"\n"+type;	
							}
						}
						
											
						lblSummary.append(gValidation);
						lblError.append(gError);
					} catch (Exception e1) {
					}
					
		
				} else if (e.widget.equals(txt2) ){
					try {
						rootModel.setLinkagemapPath(tBox2);
						LinkageMapValidation  linkageValidation = new LinkageMapValidation();
						linkageLoaderbollean=true;
						String lValidation = "";
						String lError="";
						List Linkagetest =linkageValidation.LinkageValidation();
						
						
						for(int i=0;i<Linkagetest.size();i++){
							String type = (String) Linkagetest.get(i);
							
							if(i<4){
								lValidation =lValidation+"\n"+type;
							}
							else{
								lError=lError+"\n"+type;	
							}
							
						}
						
						/*if((rootModel.getLinageMap_Error() == true)&&(genotypeLoaderboolean == true))
						{
							linkageLoaderbollean=false;
						}*/
						lblSummary.append(lValidation);
						lblError.append(lError);
					} catch (Exception e1) {
					}
					
							} 
				else if(e.widget.equals(txt3))
				{
					try {
						rootModel.setQtlPath(tBox3);
						QtlValidation  qtlValidation = new QtlValidation();
						List qtltest = qtlValidation.qtlValidation();
						qtlLoaderbollean=true;
						
						String qValidation = "";
						String qError="";
						
						
						for(int i=0;i<qtltest.size();i++){
							String type = (String) qtltest.get(i);
							
							if(i<4){
								qValidation =qValidation+"\n"+type;
							}
							else{
								qError=qError+"\n"+type;	
							}
							
						}
						lblSummary.append(qValidation);
						lblError.append(qError);
					} catch (Exception e1) {
					}
					
				}
				 else if(e.widget.equals(txt4))
					{
					try {
						rootModel.setPhenotypePath(tBox4);
						PhenotypeValidation  phenoValidation = new PhenotypeValidation();
						List phenotest =	phenoValidation.phenoValidation();
						phenoLoaderbollean=true;
						String pValidation = "";
						String pError="";
						
						
						for(int i=0;i<phenotest.size();i++){
							String type = (String) phenotest.get(i);
							
							if(i<3){
							pValidation =pValidation+"\n"+type;
							}
							else{
							pError=pError+"\n"+type;	
							}
							
						}
						lblSummary.append(pValidation);
						lblError.append(pError);
					} catch (Exception e1) {
					}
					}
				if( !(rootModel.getGeneration()== null) ||(genotypeLoaderboolean == true && linkageLoaderbollean == true) )
				{
					
				setErrorMessage(null);
				setPageComplete(true);
			}
				else{
				setPageComplete(false);
			}
			} catch (RuntimeException e1) {
				setErrorMessage("Invalid File");
//				setPageComplete(false);
			}
		}	
		
	}	
}