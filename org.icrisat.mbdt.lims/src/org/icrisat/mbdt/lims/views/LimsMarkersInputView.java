package org.icrisat.mbdt.lims.views;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PropertyDialogAction;
import org.eclipse.ui.part.ViewPart;
import org.icrisat.mbdt.gef.views.GraphicalView;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.GenotypeModel.Genotype;
import org.icrisat.mbdt.model.LinkageMapModel.Intervals;
import org.icrisat.mbdt.model.LinkageMapModel.LimsMarkers;
import org.icrisat.mbdt.model.LinkageMapModel.LinkageMap;
import org.icrisat.mbdt.model.sessions.Session;

public class LimsMarkersInputView extends ViewPart implements ISelectionListener {
	
	private TableViewer tViewer;
	private Action exportMarkers;
	private Action deleteAction;
	List<LimsMarkers> markers = new ArrayList<LimsMarkers>();	
	private Shell shell;
	List<LimsMarkers> markersDir = new ArrayList<LimsMarkers>();
	Listener sortListener ;
	List deletedmarkers = new ArrayList();
	
	private LimsMarkerStatusSorter limsSort;
	
	public LimsMarkersInputView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		
		Table tbl = new Table(parent,SWT.FULL_SELECTION);
		tbl.setHeaderVisible(true);
		tbl.setLinesVisible(true);
		
		TableColumn tColumn = new TableColumn(tbl, SWT.NONE);
		tColumn.setWidth(100);
		tColumn.setText("List of Markers");
		
		final TableColumn tColumn1 = new TableColumn(tbl, SWT.TOGGLE);
		tColumn1.setWidth(100);
		tColumn1.setText("Status");
		
		tViewer = new TableViewer(tbl);
		tViewer.setContentProvider(new LimsMarkersInputContentProvider());
		tViewer.setLabelProvider(new LimsMarkersInputLabelProvider());
		
		/************* registering this class listener to the listener *************/
		this.getSite().getPage().addSelectionListener(GraphicalView.class.getName(), this);
		
		
		sortListener = new Listener() {
			
	          public void handleEvent(Event e) {
	        	  // determine new sort column and direction
	              TableColumn sortColumn = LimsMarkersInputView.this.tViewer.getTable().getSortColumn();
	              TableColumn currentColumn = (TableColumn) e.widget;
	              int dir = LimsMarkersInputView.this.tViewer.getTable().getSortDirection();
	              if (sortColumn == currentColumn) {
	                  dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
	              } else {
	            	  LimsMarkersInputView.this.tViewer.getTable().setSortColumn(currentColumn);
	                  dir = SWT.UP;
	              }
	              // sort the data based on column and direction
	              String sortIdentifier = tColumn1.getText();
	              limsSort = new LimsMarkerStatusSorter(sortIdentifier,dir);
	              LimsMarkersInputView.this.tViewer.getTable().setSortDirection(dir);
	              LimsMarkersInputView.this.tViewer.setSorter(limsSort);
	             
	          }
	     };
	     tColumn1.addListener(SWT.Selection, sortListener); 
		
		makeAction();
		fillMenuBar();
		hookContextMenu();
	}

	private void hookContextMenu() {
		
		//Step 1 : Creating Menu Manager...
		MenuManager menuMgr = new MenuManager();
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener(){

			public void menuAboutToShow(IMenuManager manager) {
				manager.add(deleteAction);
				
				//for adding property field to the right click pop up menu...
				manager.add(new PropertyDialogAction(PlatformUI.getWorkbench().getActiveWorkbenchWindow(),tViewer));
			}
		});
		
		//Step 2: Register the Context Menu...
		Menu menu = menuMgr.createContextMenu(tViewer.getTable());
		tViewer.getTable().setMenu(menu);
	}

	private void fillMenuBar() {
		MenuManager menuMgr = new MenuManager("Export");
		menuMgr.add(exportMarkers);
		this.getViewSite().getActionBars().getMenuManager().add(menuMgr);		
	}

	private void makeAction() {
		shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		
		deleteAction = new Action("Delete SelectedMarker",SWT.PUSH){
			public void run(){
				if(deleteAction.isChecked()){
					Object selectedObject = ((IStructuredSelection)tViewer.getSelection()).getFirstElement();
//					List selMarkers = (List) tViewer.getInput();
				
					/*if(selMarkers.contains(selectedObject)){
						tViewer.remove(selectedObject);
						System.out.println("swapna");
					}*/
					
					RootModel rootModel = Session.getInstance().getRootmodel();					
					LinkageMap map = (LinkageMap)rootModel.getLinkagemap().get(0);
					List delmarkers = new ArrayList<Intervals>();
					HashSet<Object> delMarkersHashSet = new HashSet<Object>();
					
									
					if(!markersDir.isEmpty()){
						markers.clear();
						delMarkersHashSet.clear();
						
					}					
					for(int i = 0; i < map.getLimsMarkers().size(); i++){
						
						 if(map.getLimsMarkers().get(i).getstrUnscreenedMrks() != null){
															
							 LimsMarkers test = map.getLimsMarkers().get(i);
							  											 
							 if(markers.contains(selectedObject)){
								 map.getLimsMarkers().remove(selectedObject);
								 markers.remove(selectedObject);
								 deletedmarkers.add(((LimsMarkers) selectedObject).getstrUnscreenedMrks());									
							}
							 
							 if(markersDir.contains(selectedObject)){
								 map.getLimsMarkers().remove(selectedObject);
								 markersDir.remove(selectedObject);
								 deletedmarkers.add(((LimsMarkers) selectedObject).getstrUnscreenedMrks());											
									
							 }
															 
							 if(map.getLimsMarkers().get(i).getstrUnscreenedMrks() != null){
								 
								 for(int tt =0; tt < markers.size(); tt++){
									 if(map.getLimsMarkers().contains(markers.get(tt))){
										 delMarkersHashSet.add(map.getLimsMarkers().get(i));
									 }
								 }
								 
								 for(int tt1 =0; tt1 < markersDir.size(); tt1++){
									 if(map.getLimsMarkers().contains(markersDir.get(tt1))){										
										 delMarkersHashSet.add(markersDir.get(tt1));
									 }
								 }
							 }							
						 }
					}
					for(Iterator<Object> it = delMarkersHashSet.iterator(); it.hasNext();){
						delmarkers.add(it.next());	
					}
					tViewer.setInput(delmarkers);
					
					tViewer.refresh();
					
				}
			}
		};
		
		//For Export Markers to a .CSV file....
		exportMarkers = new Action("LIMS Markers",SWT.NONE){
			@Override
			public void run() {
				if((tViewer.getInput() != null) && (tViewer.getElementAt(0) != null)){
					
					String fileName = null;
					List<String> markersTofile = new ArrayList<String>();
					HashSet<String> markersTofileHSet = new HashSet<String>();
					
					 RootModel rootModel = Session.getInstance().getRootmodel();
		        	 LinkageMap map = (LinkageMap)rootModel.getLinkagemap().get(0);
		        	 
		  			 for(int i = 0; i < map.getLimsMarkers().size(); i++){
		  				 if(map.getLimsMarkers().get(i).getStrMarkersFrmTviewer() != null){
		  					 
		  					 markersTofileHSet.add(map.getLimsMarkers().get(i).getStrMarkersFrmTviewer());
		  				 }
		  			 }
		  			
		  			 
		  			for(Iterator<String> itMtoF = markersTofileHSet.iterator(); itMtoF.hasNext();){
		  				markersTofile.add(itMtoF.next());	
					}
		  			
		  			 
					FileDialog fileDialog = new FileDialog(Display.getDefault().getActiveShell(),SWT.SAVE);
					fileDialog.setFilterExtensions(new String[]{"*.csv","*.*"});
					boolean done = false;
					while (!done) {
						
						fileName = fileDialog.open();
						if (fileName == null) {
					        // User has cancelled, so quit and return....
					        done = true;
					      } else {
					    	  File file = new File(fileName);
					          if (file.exists()) {
					        	  
					            // The file already exists; asks for confirmation....
					            MessageBox mb = new MessageBox(fileDialog.getParent(), SWT.ICON_WARNING | SWT.YES | SWT.NO);
	
					            // We really should read this string from a resource bundle....
					            mb.setMessage(fileName + " already exists. Do you want to replace it?");
					           
					            // If they click Yes, we're done and we drop out. If
					            // they click No, we redisplay the File Dialog
					            if(mb.open() == SWT.YES){
									FileWriter writer;
									try {
										
										writer = new FileWriter(fileName);
										for(int j = 0; j< markersTofile.size(); j++){ 
											writer.write((String)markersTofile.get(j));
											writer.write("\n");							  		
										}
									
									MessageDialog.openInformation(shell, "Information", "File Created!");
									writer.flush();
									writer.close();
									
									} catch (IOException e) {
									}
					        	 } 		      
//					            done = mb.open() == SWT.YES;
					            done = true;					           
					          } else {
					        	 // File does not exist, so drop out...					        	 
					        	 FileWriter writer;
					        	 try {
					        		
									writer = new FileWriter(fileName);
									for(int j = 0; j< markersTofile.size(); j++){ 
	//						  			writer.append((String)markersTofile.get(j));
	//						  			writer.append(',');
							  			writer.write((String)markersTofile.get(j));
							  			writer.write("\n");							  		
									 }
									
									MessageDialog.openInformation(shell, "Information", "File Created!");
						  			writer.flush();
						  			writer.close();
						  		
					        	 } catch (IOException e) {
					        	 }				        	
					            done = true;
					          }
					      }
					 }				
				}else{
					MessageDialog.openError(shell, "Error", "No UnScreened Markers are available !");
				}
				super.run();
			}			
		};
		
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
	
	public void setInput(LimsMarkers limsMarkers) {
		
		markers.add(limsMarkers);		
		tViewer.setInput(markers);

	}

	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		IStructuredSelection ss = (IStructuredSelection)selection;
		List<Object> limsselectedMarkers = new ArrayList<Object>();
		int chkCount = 0;
		
		markersDir.clear();
		for(Iterator iterator = ss.iterator();iterator.hasNext();){			
			
			String itvalues = iterator.next()+"";
			if(itvalues.contains("MapMarkers")){
				
//				String itvalues1 = itvalues.substring(20,itvalues.indexOf(" )"));
				String itvalues1 = itvalues.substring(itvalues.indexOf("org"),itvalues.indexOf(" )"));
				
				RootModel rootModel = Session.getInstance().getRootmodel();			
				LinkageMap map = (LinkageMap)rootModel.getLinkagemap().get(0);
				Genotype geno = (Genotype) rootModel.getGenotype().get(0);
				
				for(int i = 0; i < map.getChromosomes().size(); i++){
								
					if(map.getChromosomes().get(i).getMarkers().get(0).toString().contains(itvalues1)){
						if(!deletedmarkers.contains(map.getLimsMarkers().get(i).getstrUnscreenedMrks())){
							limsselectedMarkers.add(map.getLimsMarkers().get(i));							
							markersDir.add(map.getLimsMarkers().get(i));							
							if(geno.getHeaderList().contains(map.getChromosomes().get(i).getMap_marker())){
								map.getLimsMarkers().get(i).setMarkerStatus("Screened");								
							}else {
								map.getLimsMarkers().get(i).setMarkerStatus("UnScreened");
							}
						}
					}
					chkCount = limsselectedMarkers.size();
				}
				map.setUnScreenedcount(chkCount);
			}
		}
		
		if(this.tViewer.getContentProvider()!=null)
		{
		tViewer.setInput(limsselectedMarkers);
		}LimsMarkersInputView.this.tViewer.setSorter(null); 
		deletedmarkers.clear();
	}

}
