package org.icrisat.mbdt.ui.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.WorkbenchState;
import org.icrisat.mbdt.model.Chromosome.Chromosome;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.TargetGenotype.TargetGeno;
import org.icrisat.mbdt.model.sessions.Session;
import org.icrisat.mbdt.model.sessions.SessionChromosome;
import org.icrisat.mbdt.model.sessions.SessionTargetGenotype;
import org.icrisat.mbdt.ui.dialog.IsmabSaveAsDialog;

/**
 * SwapnaNair February 2010
 **/

public class SaveAction implements IWorkbenchWindowActionDelegate {
	
	public static final String ID = "org.icrisat.mbdt.ui.SaveAction";
	private IWorkbenchWindow window;
	String mainFolder = "";
	String folder = "";
	String subFolder = "";
	String finalFolder = "";
	
	
	/*public SaveAction(IWorkbenchWindow window) {
		// TODO Auto-generated constructor stub
		super();
		this.window = window;
		setId(ID);
		setText("&Save");
	}*/
	
	public void run(IAction action) {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		
		//Creating our own Dialog Box.....
		IsmabSaveAsDialog saveAsdialog = new IsmabSaveAsDialog(shell);
		
		
		if(saveAsdialog.open()== Window.OK){
			String projectName = saveAsdialog.getTextData();
			String generationName = saveAsdialog.getText1Data();
			mainFolder = Platform.getLocation().getDevice()+"//MBDT_PROJECTSFiles";
			
			if(!new File(mainFolder).exists()){
		       	new File(mainFolder).mkdir();
			}
			folder = mainFolder+"//"+projectName;
			if(!new File(folder).exists()){
		       	new File(folder).mkdir();
			}
			
			subFolder = folder+"//"+generationName;
			
			if(Session.getInstance().getRootmodel().getLoadNextGen().isEmpty()){
				IWorkspace workspace = ResourcesPlugin.getWorkspace(); 
				IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
				IProject myWebProject = myWorkspaceRoot.getProject();
				   
				RootModel rootmodel = RootModel.getRootModel();
				WorkbenchState myObject = new WorkbenchState ();
				
				LinkageData linkData;
				Qtl_MapData qtlMapData;
				Chromosome chromosome = SessionChromosome.getInstance().getChromosome();
				TargetGeno targetGeno = SessionTargetGenotype.getInstance().getTargetGeno();
				
				if(rootmodel.getLoadFlag() == null){
					RootModel rootModel1 = Session.getInstance().getRootmodel();
					linkData = LinkageData.getLinkageData();
					qtlMapData = Qtl_MapData.getQtl_MapData();
					
					if(targetGeno == null){
						rootModel1.setSaveType("NoTargetData");
					}else{
						if(targetGeno.getParents().size()== 0) {
							rootModel1.setSaveType("NoTargetData");
						}else{
							rootModel1.setSaveType("TargetDataAvailable");
						}
					}
					
					myObject.setRootModelSer(rootModel1);
				}else{
					RootModel rootModel1 = Session.getInstance().getRootmodel();
					linkData = rootModel1.getLinkData();
					qtlMapData = rootModel1.getQtlMapData();
					if(targetGeno == null){
						rootModel1.setSaveType("NoTargetData");
					}else{
						if(targetGeno.getParents().size()== 0) {
							rootModel1.setSaveType("NoTargetData");
						}else{
							rootModel1.setSaveType("TargetDataAvailable");
						}
					}
					
					myObject.setRootModelSer(rootModel1);
					
			        
				}
				
		        myObject.setLinkData(linkData);
		        myObject.setQtlMapData(qtlMapData);
		        myObject.setTargetGeno(targetGeno);
		        myObject.setChromsome(chromosome);  
				try {
					String subFolderSplit = "";
					if(subFolder.contains(".ser")){
						subFolderSplit = subFolder.substring(0,subFolder.indexOf(".ser"));
					}else{
						subFolderSplit = subFolder;
					}
					finalFolder = subFolderSplit +".ser";
					if(!new File(finalFolder).exists()){
						 FileOutputStream fos = new FileOutputStream(subFolderSplit+".ser");
				         ObjectOutputStream oos;
						 oos = new ObjectOutputStream(fos);
						 oos.writeObject(myObject);
			             oos.flush();
			             oos.close();
			             
			             MessageDialog.openInformation(shell, "Information", "File Created!!");
					}else{
						 // The file already exists; asks for confirmation....
			            MessageBox mb = new MessageBox(shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
	
			            mb.setMessage(generationName + " already exists. Do you want to replace it?");
			            if(mb.open() == SWT.YES){
			            	 FileOutputStream fos = new FileOutputStream(finalFolder);
					         ObjectOutputStream oos;
							 oos = new ObjectOutputStream(fos);
							 oos.writeObject(myObject);
				             oos.flush();
				             oos.close();
				             MessageDialog.openInformation(shell, "Information", "File Created!!");
			            }
					}
		             
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			}else{
				IWorkspace workspace = ResourcesPlugin.getWorkspace(); 
				IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
				IProject myWebProject = myWorkspaceRoot.getProject();
				   
				RootModel rootmodel = RootModel.getRootModel();
				WorkbenchState myObject = new WorkbenchState ();
				LinkageData linkData;
				Qtl_MapData qtlMapData;
				Chromosome chromosome = SessionChromosome.getInstance().getChromosome();;
				TargetGeno targetGeno = SessionTargetGenotype.getInstance().getTargetGeno();				
				if(rootmodel.getLoadFlag() == null){
					RootModel rootModel1 = Session.getInstance().getRootmodel();
					linkData = LinkageData.getLinkageData();
					qtlMapData = Qtl_MapData.getQtl_MapData();
					
					myObject.setRootModelSer(rootModel1);
				}else{
					RootModel rootModel1 = Session.getInstance().getRootmodel();
					linkData = rootModel1.getLinkData();
					qtlMapData = rootModel1.getQtlMapData();
					myObject.setRootModelSer(rootModel1);
				}
								
		        myObject.setLinkData(linkData);
		        myObject.setQtlMapData(qtlMapData);
		        myObject.setTargetGeno(targetGeno);   
		        myObject.setChromsome(chromosome);
				try {
					String subFolderSplit = "";
					if(subFolder.contains(".ser")){
						subFolderSplit = subFolder.substring(0,subFolder.indexOf(".ser"));
					}else{
						subFolderSplit = subFolder;
					}
					finalFolder = subFolderSplit +".ser";
					if(!new File(finalFolder).exists()){
	//					 FileOutputStream fos = new FileOutputStream(subFolder+"//myObject.ser");
						 FileOutputStream fos = new FileOutputStream(subFolderSplit+".ser");
				         ObjectOutputStream oos;
						 oos = new ObjectOutputStream(fos);
						 oos.writeObject(myObject);
			             oos.flush();
			             oos.close();
			             
			             MessageDialog.openInformation(shell, "Information", "File Created!!");
					}else{
						 // The file already exists; asks for confirmation....
			            MessageBox mb = new MessageBox(shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
	
			            // We really should read this string from a resource bundle....
			            mb.setMessage(generationName + " already exists. Do you want to replace it?");
						//MessageDialog.openWarning(shell, "Warning", "Generation Name already exists!!");
			            if(mb.open() == SWT.YES){
			            	// String rewriteFile = finalFolder.substring(0,finalFolder.indexOf(".ser"));
			            	 FileOutputStream fos = new FileOutputStream(finalFolder);
					         ObjectOutputStream oos;
							 oos = new ObjectOutputStream(fos);
							 oos.writeObject(myObject);
				             oos.flush();
				             oos.close();
				             MessageDialog.openInformation(shell, "Information", "File Created!!");
			            }
					} 
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			}
			
		}  
	
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub
		this.window = window;
	}

	

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
		
	}

}
