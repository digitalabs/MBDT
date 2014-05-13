package org.icrisat.mbdt.gef.views;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.part.ViewPart;
import org.icrisat.mbdt.gef.editPart.genotype.ExampleAddChildCommand;
import org.icrisat.mbdt.gef.editPartFactory.TargetGenotypeEditPartFactory;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.GenotypeModel.Genotype;
import org.icrisat.mbdt.model.GenotypeModel.GenotypeMarkers;
import org.icrisat.mbdt.model.GenotypeModel.SelectedAccessions;
import org.icrisat.mbdt.model.GenotypeModel.SortedMarkers;
import org.icrisat.mbdt.model.LinkageMapModel.LinkageMap;
import org.icrisat.mbdt.model.TargetGenotype.ColorAllele;
import org.icrisat.mbdt.model.TargetGenotype.MarkersSelectedParents;
import org.icrisat.mbdt.model.TargetGenotype.Parents;
import org.icrisat.mbdt.model.TargetGenotype.SelectedParents;
import org.icrisat.mbdt.model.TargetGenotype.TargetGeno;
import org.icrisat.mbdt.model.notifiers.ILoadNotifierTargetGenotype;
import org.icrisat.mbdt.model.sessions.Session;
import org.icrisat.mbdt.model.sessions.SessionTargetGenotype;

/**
 * @author msravani
 *
 */
public class TargetGenotype extends ViewPart implements ILoadNotifierTargetGenotype, ISelectionListener, IWorkbenchWindowActionDelegate {

	private ScrollingGraphicalViewer gViewer;
	private IAction zoomInAction;
	private IAction zoomOutAction;
	private Action SaveToDataBase;
	private Action SaveToFolder;
	private IAction createTarget;
	
	private IAction clear;
	static int count=0;
	
	static List<String> typelistCount = new ArrayList<String>();
	static int tlcount = 0;
	static String ref="";
	String jj, jjType;
	List<String> sel= new ArrayList<String>();
	List<String> gh1= new ArrayList<String>();
	List<String> strType= new ArrayList<String>();
	private Shell shell;
	boolean isTargetgenotype = false;
	GraphicalView gview = new GraphicalView();
	ExampleAddChildCommand history = new ExampleAddChildCommand();
	public static String tgeno = "";
	int loopCount = 0;
	int count1 = 0;
	Boolean clearHJ = false;
	String prevhjValue = "";
	String prevAccType = "";
	List<String> check= new ArrayList<String>();
	
	@Override
	public void createPartControl(Composite parent) {

		gViewer = new ScrollingGraphicalViewer();
		gViewer.createControl(parent);
		gViewer.getControl().setBackground(ColorConstants.white);
		gViewer.setEditDomain(new EditDomain());
		
		// This is the controller that supports zoom and free form scrolling
		gViewer.setRootEditPart(new ScalableFreeformRootEditPart());
		gViewer.setEditPartFactory(new TargetGenotypeEditPartFactory());
		SessionTargetGenotype.getInstance().addNotifyListener(this);
		
		this.getSite().getPage().addSelectionListener(GraphicalView.class.getName(), this);
		this.getSite().getPage().addSelectionListener(ChromosomeView.class.getName(), this);
		
		
		makeActions();
		fillToolBar();
		fillMenuBar();
		hookContextMenu();
				
	}

	private void fillMenuBar() {
		MenuManager menuMgr = new MenuManager("Action");
		menuMgr.add(SaveToDataBase);
		this.getViewSite().getActionBars().getMenuManager().add(menuMgr);
	}

	private void fillToolBar() {
		this.getViewSite().getActionBars().getToolBarManager().add(zoomInAction);
		this.getViewSite().getActionBars().getToolBarManager().add(zoomOutAction);
		this.getViewSite().getActionBars().getToolBarManager().add(createTarget);
		this.getViewSite().getActionBars().getToolBarManager().add(clear);
	}

	private void makeActions() {
		ScalableFreeformRootEditPart rootEditPart= ((ScalableFreeformRootEditPart)gViewer.getRootEditPart());
		zoomInAction= new ZoomInAction(rootEditPart.getZoomManager());
		zoomOutAction= new ZoomOutAction(rootEditPart.getZoomManager());
		final TargetGeno target= TargetGeno.getTargetGeno();
		
		try {
			clear = new Action("Clear", SWT.KeyDown) {
				public void run() {
//					System.out.println("clear");
					TargetGeno target = null;
					RootModel rootModel =  RootModel.getRootModel();
					LinkageData ldata;
					
					if(rootModel.getLoadFlag() == null){
						target = TargetGeno.getTargetGeno();
						rootModel = RootModel.getRootModel();
						ldata=LinkageData.getLinkageData();
					}else{						
						target = SessionTargetGenotype.getInstance().getTargetGeno();
						rootModel = Session.getInstance().getRootmodel();
						ldata = rootModel.getLinkData();
						/*if(rootModel.getSaveType().equals("TargetDataAvailable")){
							ldata.setSortval("");
						}*/
					}
					
					if((target.getTcreate() == "")||(target.getTcreate().equals("create"))) {
						if(clear.isEnabled()) {
							target.getParents().clear();
							if(target.getParents().size()== 0) {
								SessionTargetGenotype.getInstance().setTargetGeno(target);
//								Session.getInstance().setRootModel(rootModel);
								target.getParents().clear();
								hj.clear();
								typeList.clear();
								mark= 0;
								prevSize= 0;
								isTargetgenotype=false;
								typelistCount.clear();
								target.setTcreate("");
								tlcount=0;
								check.clear();
							}
						}						
					}
					
					List<String> dmarkers = history.dragged_marker;
					HashMap mstatus=new HashMap();	
					List<String> foregroundMarkers = ldata.getForegroundMarker();
					if(mstatus==null){
						mstatus=new HashMap();
					}
					if(foregroundMarkers==null){
						foregroundMarkers=new ArrayList<String>();
					}
					mstatus=rootModel.getLinkagemap().get(0).getChromosomes().get(0).getMstatus();
					for (Iterator iterator = dmarkers.iterator(); iterator.hasNext();) {
						String marker = (String) iterator.next();
						mstatus.put(marker, "Foreground");
						
						for(int l=0;l<rootModel.getLinkagemap().get(0).getChromosomes().size();l++){
							
							if(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getMap_marker().equals(marker)){
								rootModel.getLinkagemap().get(0).getChromosomes().get(l).setForestatus("Background");
								if((foregroundMarkers.contains(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getMap_marker()))){
									foregroundMarkers.remove(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getMap_marker());
									}
							}
						}	
						
							
					}
					ldata.setForegroundMarker(foregroundMarkers);
					rootModel.getLinkagemap().get(0).getChromosomes().get(0).setMstatus(mstatus);
					
					Session.getInstance().setRootModel(rootModel);
				}
				public void setChecked(boolean checked) {

				}
			};
		}catch(Exception e){
			
			e.printStackTrace();}
		clear.setToolTipText("Double click to clear the data");
		try {
		createTarget= new Action("TargetGenotype", SWT.KeyDown) {
			public void run() {
				TargetGeno target = null;
				RootModel rootModel =  RootModel.getRootModel();
//				System.out.println("Flag in Target Refresh :"+rootModel.getLoadFlag());
				if(rootModel.getLoadFlag() == null){
					target = TargetGeno.getTargetGeno();
					rootModel = RootModel.getRootModel();
					
				}else{	
					target = TargetGeno.getTargetGeno();
					rootModel = Session.getInstance().getRootmodel();
				}
				
				try {
					for(int i=0; i<target.getParents().size(); i++) {						
						jj= target.getParents().get(i).getParent();
						String type= target.getParents().get(i).getType();
						if(type.equals("")){
							MessageDialog.openError(shell, "Error", "Please assign the type of parent (as Donor or Recurrent) ");
						}
						if(type.equals("Recurrent")) {
							jjType= target.getParents().get(i).getParent();
						}
						if(!(gh1.contains(jj))) {
							target.getParents().remove(i).getParent();
						}
						if(!(type.contains("Recurrent")||(type.contains("Donor")))) {
							target.getParents().clear();
							if(target.getParents().size()== 0) {
								SessionTargetGenotype.getInstance().setTargetGeno(target);
//								Session.getInstance().setRootModel(rootModel);
								target.getParents().clear();
								hj.clear();
								typeList.clear();
								mark= 0;
								prevSize= 0;
								isTargetgenotype=false;
							}
						}
					}
					if(createTarget.isEnabled()) {
						if(target instanceof TargetGeno) {
							isTargetgenotype=true;
							int i = 3;
//							System.out.println("Parents SIZE :::"+target.getParents().size());
							if(target.getParents().size() == 3) {								
								target.getParents().get(i-1).setParent(jjType+"Target");								
								int j= target.getParents().get(0).getSelParents().size();
								List<String> chrName= rootModel.getChrNo();
								target.getParents().get(i-1).getSelParents().get(j-1).setSelectedParents(jjType+"Target");
								target.getParents().get(i-1).setType("Target");
								target.getParents().get(i-1).getSelParents().get(j-1).setChrNo(chrName);
								int k= target.getParents().get(0).getSelParents().get(0).getMParents().size();
//								System.out.println("par.."+target.getParents().get(0).getSelParents().get(0).getMParents().get(0).getSelectedParents());
//								System.out.println("par.."+target.getParents().get(0).getSelParents().get(0).getMParents().get(0).getSelectedParents());
								target.getParents().get(i-1).getSelParents().get(j-1).getMParents().get(k-1).setSelectedParents(jjType+"Target");
								List<String> newAcc= rootModel.getAccession();
								List<String> markerName= rootModel.getMarkerName();
								List<Integer> markerPosition= rootModel.getMarkerPosition();
								List<Integer> markerPrevPos= rootModel.getMarkerPrevPos();
								List<Integer> markerNextPos= rootModel.getMarkerNextPos();
								List<String> label= rootModel.getLabel();
								List<String> allval= rootModel.getAccAllele();
								target.getParents().get(i-1).getSelParents().get(j-1).getMParents().get(k-1).setAccession(newAcc);
								target.getParents().get(i-1).getSelParents().get(j-1).getMParents().get(k-1).setChrNo(chrName);
								target.getParents().get(i-1).getSelParents().get(j-1).getMParents().get(k-1).setLabel(label);
								target.getParents().get(i-1).getSelParents().get(j-1).getMParents().get(k-1).setTargetAlleleValue(allval);
								target.getParents().get(i-1).getSelParents().get(j-1).getMParents().get(k-1).setMarkerName(markerName);
								target.getParents().get(i-1).getSelParents().get(j-1).getMParents().get(k-1).setMarkerPosition(markerPosition);
								target.getParents().get(i-1).getSelParents().get(j-1).getMParents().get(k-1).setMarkerPrevPos(markerPrevPos);
								target.getParents().get(i-1).getSelParents().get(j-1).getMParents().get(k-1).setMarkerNextPos(markerNextPos);
								SessionTargetGenotype.getInstance().setTargetGeno(target);
								hj.clear();
								typeList.clear();
								mark= 0;
								target.setTcreate("create");
								tlcount =1;
								ref="ref";
							}else{
//								System.out.println("refresh");
								MessageDialog.openWarning(shell, "Warning", "Please select one Donor and one Recurrent Parent!");
							}
						} 
						
						Session.getInstance().setRootModel(rootModel);
					}
				}catch(Exception e) {
							e.printStackTrace();
				}
			}
		};
		}catch(Exception e){
			e.printStackTrace();
		}
	
		SaveToDataBase = new Action("History of movements",SWT.NONE) {			
				@Override
				public void run() {
					String fileName = null;
					int test=0;
					
					TargetGeno target = null;
					List<String> movements = new ArrayList<String>();
					movements = history.movements;
//					System.out.println("movements......:"+movements);

					RootModel rootModel =  RootModel.getRootModel();
					if(rootModel.getLoadFlag() == null){
						target = TargetGeno.getTargetGeno();
//						System.out.println("import loop");
					}else{		
						target = SessionTargetGenotype.getInstance().getTargetGeno();
						movements=target.getMovements();
//						System.out.println("load loop");
						test++;
						if(test>0)
						{
							movements.addAll(history.movements);
						}
					}
					
					target.setMovements(movements);  			
					
					FileDialog fileDialog = new FileDialog(Display.getDefault().getActiveShell(),SWT.SAVE);
					fileDialog.setFilterExtensions(new String[]{"*.txt","*.*"});
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
										for(int j = 0; j< movements.size(); j++){ 
											writer.write((String)movements.get(j));
											writer.write("\n");							  		
										}
									MessageDialog.openInformation(shell, "Information", "File Created!");
									writer.flush();
									writer.close();
									
									} catch (IOException e) {
										e.printStackTrace();
									}
					        	 } 		      
//					            done = mb.open() == SWT.YES;
					            done = true;					           
					          } else {
					        	 // File does not exist, so drop out...					        	 
					        	 FileWriter writer;
					        	 try {
					        		
									writer = new FileWriter(fileName);
									for(int j = 0; j< movements.size(); j++){ 
//							  			writer.append((String)markersTofile.get(j));
//							  			writer.append(',');
							  			writer.write((String)movements.get(j));
							  			writer.write("\n");							  		
									 }
									
									MessageDialog.openInformation(shell, "Information", "File Created!");
						  			writer.flush();
						  			writer.close();
						  		
					        	 } catch (IOException e) {
									e.printStackTrace();
					        	 }				        	
					            done = true;
					          }
					      }
					 }				
					super.run();

				}
		};
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
	}

	
	
	
	
	
	int cat=0;
	static int mark=0, prevSize= 0;
	static String prevSel= null;
	static LinkedHashSet<String> hj= new LinkedHashSet<String>();
	static LinkedHashSet<String> typeList= new LinkedHashSet<String>();
	String prevEle= null;
	String testStr1, testStr2,testStr3;
	static int ifloopCount = 0;
	
	public void selectionChanged(IWorkbenchPart part,ISelection selection) { 
		TargetGeno tGeno = TargetGeno.getTargetGeno();
		RootModel rootModel = Session.getInstance().getRootmodel();
		if(rootModel == null){
		
		}else{
		
		LinkageData ldata;
		if(rootModel.getLoadFlag() == null){
			rootModel = RootModel.getRootModel();
			ldata=LinkageData.getLinkageData();
		}else{
			rootModel = Session.getInstance().getRootmodel();
			ldata = rootModel.getLinkData();
		}
		
		IStructuredSelection ss = (IStructuredSelection)selection;
		List<Object> listSelacc = new ArrayList<Object>();
		int RCount = 0, DCount = 0;
		typelistCount.clear();
		
		try{
			for(Iterator iterator = ss.iterator();iterator.hasNext();){
			testStr1 = iterator.next()+"";			
			testStr2 = testStr1.substring(testStr1.indexOf("org"),testStr1.indexOf(" )"));
			
			testStr3 = "["+testStr2+"]";
			for(int tg = 0; tg < rootModel.getGenotype().get(0).getAccessions().size(); tg++){
				for(int ii = 0; ii < rootModel.getGenotype().get(0).getAccessions().get(tg).getSelectedAccessions().size(); ii++){
					if(testStr3.equals(rootModel.getGenotype().get(0).getAccessions().get(tg).getSelectedAccessions().get(ii).getSelacc1().get(0).getGenotypeMarkers()+"")){
						listSelacc.add(rootModel.getGenotype().get(0).getAccessions().get(tg).getSelectedAccessions().get(ii).getSelacc1().get(0).getGenotypeMarkers());
					}	
				}
			}
		}
			
		for(int tg = 0; tg < rootModel.getGenotype().get(0).getAccessions().size(); tg++){
			rootModel.getGenotype().get(0).getAccessions().get(tg).setSelAccforTargetCreation(listSelacc);
			
		}
		}catch(Exception e){
			e.printStackTrace();
		}
				
//	System.out.println("ss ::::"+ss+"...."+ss.size());
	SelectedAccessions sa = new SelectedAccessions();
	
		String selection1= null;
		String type= null;
		Genotype geno= (Genotype)rootModel.getGenotype().get(0);
		String selectionClass = ss.toString();
		if((selectionClass.contains("MarkerPositionEditPart") || selectionClass.contains("MapMarkersEditPart")|| selectionClass.contains("QTLDataEditPart") )){
			try{
			if(rootModel.getGeneration().equals("First")){
			if(tGeno.getTcreate() == ""){
				
				tGeno.getParents().clear();
				if(tGeno.getParents().size()== 0) {
					SessionTargetGenotype.getInstance().setTargetGeno(tGeno);
//					Session.getInstance().setRootModel(rootModel);
					tGeno.getParents().clear();
					hj.clear();
					typeList.clear();
					mark= 0;
					prevSize= 0;
					isTargetgenotype=false;
					typelistCount.clear();
					tGeno.setTcreate("");
					check.clear();
				}
			}
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			//--------------
			try {

				if((tGeno.getTcreate().equals("create"))&&(tlcount!=0)&&(ss.size()!= prevSize )&&(ref.equals(""))) {
							tGeno.getParents().clear();
							hj.clear();
							typeList.clear();
							mark= 0;
							prevSize= 0;
							isTargetgenotype=false;
							typelistCount.clear();
							tlcount=0;
							tGeno.setTcreate("");
							check.clear();
				}
			} catch (Exception e2) {
//				e2.printStackTrace();
			}
			//---------------------
			
			if((tGeno.getTcreate() == "")||(tGeno.getTcreate()==null)){
				
			if((ss.size()!= prevSize && ss.size() < 3 ) && ((selectionClass.contains("AccessionsEditPart") ||selectionClass.contains("GMarkerEditPart") || selectionClass.contains("SMarkerEditPart"))) && (Session.getInstance().getRootmodel().getLoadNextGen().isEmpty())){
//				if(!check.containsAll(hj))
			
				
				if(ldata.isModified()){
					tGeno.getParents().clear();
					if(tGeno.getParents().size()== 0) {
						SessionTargetGenotype.getInstance().setTargetGeno(tGeno);
//						Session.getInstance().setRootModel(rootModel);
						tGeno.getParents().clear();
						hj.clear();
						typeList.clear();
						mark= 0;
						prevSize= 0;
						isTargetgenotype=false;
						typelistCount.clear();
						tGeno.setTcreate("");
						check.clear();
				}
					ldata.setModified(false);
				}
				
				try {
				gh1.clear();
				for(Iterator iterator = ss.iterator(); iterator.hasNext();) {
					String test = iterator.next()+"";
					String test1 = "";
					if(test.contains("AccessionsEditPart")){
						test1 = test.substring(20, test.indexOf(" )"));
					}else{
						test1 = test.substring(17, test.indexOf(" )"));
					}
					for(int i = 0; i < geno.getAccessions().size(); i++) {
						for(int j = 0; j < geno.getAccessions().get(i).getSelectedAccessions().size(); j++) {
							for(int k = 0; k < geno.getAccessions().get(i).getSelectedAccessions().get(j).getSelacc1().size(); k++) {
								
								if(ldata.getSortval().equals("sort")){
									if(test.contains("AccessionsEditPart")){
										if(test1.equals(geno.getAccessions().get(i).getSelectedAccessions().get(j))){
										test1 = geno.getAccessions().get(i).getSelectedAccessions().get(j).getSelacc1().get(k).getSortedMarkers().toString();
										}
									}
									String temp = "";
									HashMap<String,String> gentype = new HashMap<String, String>();
									if((rootModel.getSaveType().equals("TargetDataAvailable")) || (rootModel.getSaveType() == "")){
										temp = geno.getAccessions().get(i).getSelectedAccessions().get(j).getSelacc1().get(k).getSortedMarkers().toString();
									}else{
										HashMap<String,Object> genoObject = rootModel.getGenoObject();
										Object temp1 = genoObject.get(geno.getAccessions().get(i).getSelectedAccessions().get(j).getSelacc1().get(k).getSelAccession());
										temp = temp1+"";
										gentype = rootModel.getTypeValue();
									}
									if(temp.contains(test1)) {
										for(int m =0; m < geno.getAccessions().get(i).getSelectedAccessions().get(j).getSelacc1().get(k).getSortedMarkers().size(); m++) {
											SortedMarkers gM = (SortedMarkers) geno.getAccessions().get(i).getSelectedAccessions().get(j).getSelacc1().get(k).getSortedMarkers().get(m);
//											mark++;
											selection1 = gM.getAcc();
											prevEle = selection1;
											HashMap atype = ldata.getType();
											type =(String)atype.get(selection1) ;
//											if(gentype.size() == 0){
//												type = gM.getType();
//											}else{
//												Object tempType = gentype.get(geno.getAccessions().get(i).getSelectedAccessions().get(j).getSelacc1().get(k).getSelAccession());
//												type = tempType+"";
//											}
											
											if((count1 == 0 ) && (typeList.size() > 0)){
												hj.clear();
												clearHJ = true;
												typeList.clear();
												
												if(!selection1.equals(prevhjValue)){
													tGeno.getParents().clear();
													mark = 0;
												}
											}
											if((count1 == 0 ) && (typeList.size() == 0)){
												clearHJ = false;
											}
											mark++;
											
											hj.add(selection1);
											typeList.add(type);
											prevSel = selection1;
											cat = 1;
											
											if(loopCount == 0){
												loopCount++;
											}
											prevhjValue = selection1;
											prevAccType = type;
											
											if(ss.size() == 2){
//												System.out.println("stlist.."+typelistCount);
												typelistCount.add(type);
												ifloopCount = 0;
												RCount = 0;
												DCount = 0;
											}
										}	
									}
								}else{
									String temp = "";
									HashMap<String,String> gentype = new HashMap<String, String>();
									if(test.contains("AccessionsEditPart")){
										if(test1.equals(geno.getAccessions().get(i).getSelectedAccessions().get(j).toString())){
										test1 = geno.getAccessions().get(i).getSelectedAccessions().get(j).getSelacc1().get(k).getGenotypeMarkers().toString();
										}
									}
									if((rootModel.getSaveType().equals("TargetDataAvailable")) || (rootModel.getSaveType() == "")){
										temp = geno.getAccessions().get(i).getSelectedAccessions().get(j).getSelacc1().get(k).getGenotypeMarkers().toString();
									}else{
										HashMap<String,Object> genoObject = rootModel.getGenoObject();									
										Object temp1 = genoObject.get(geno.getAccessions().get(i).getSelectedAccessions().get(j).getSelacc1().get(k).getSelAccession());
										temp = temp1+"";
										gentype = rootModel.getTypeValue();
									}
									if(temp.contains(test1)) {
										for(int m =0; m < geno.getAccessions().get(i).getSelectedAccessions().get(j).getSelacc1().get(k).getGenotypeMarkers().size(); m++) {
											GenotypeMarkers gM = (GenotypeMarkers) geno.getAccessions().get(i).getSelectedAccessions().get(j).getSelacc1().get(k).getGenotypeMarkers().get(m);
											
//											mark++;
											
											selection1 = gM.getAcc();
											prevEle = selection1;
											HashMap atype = ldata.getType();
											type =(String)atype.get(selection1) ;
											
											/*if(gentype.size() == 0){
												type = gM.getType();
											}else{
												Object tempType = gentype.get(geno.getAccessions().get(i).getSelectedAccessions().get(j).getSelacc1().get(k).getSelAccession());
												type = tempType+"";
											}*/
											 											
											if((count1 == 0 ) && (typeList.size() > 0)){
												hj.clear();
												clearHJ = true;
												typeList.clear();
												
												if(!selection1.equals(prevhjValue)){
													tGeno.getParents().clear();
													mark = 0;
												}
											}
											if((count1 == 0 ) && (typeList.size() == 0)){
												clearHJ = false;
											}
											mark++;
											hj.add(selection1);
											typeList.add(type);
											
											prevSel = selection1;
											cat = 1;
											ldata.setTgeno("create");
											
											if(loopCount == 0){
												loopCount++;
											}
											prevhjValue = selection1;
											prevAccType = type;
											
											if(ss.size() == 2){
												typelistCount.add(type);
												ifloopCount = 0;
												RCount = 0;
												DCount = 0;
											}
										}	
									}
								}
							}
						}
					}
					count1++;
					if(count1 == ss.size()){
						count1 = 0;
					}
					
				}
			}catch(Exception e){
				
				e.printStackTrace();
			}
			for(int jk = 0; jk < typelistCount.size(); jk++){
				if(typelistCount.get(jk).equals("Recurrent")){
					RCount++;
				}
				if(typelistCount.get(jk).equals("Donor")){
					DCount++;
				}
			}			
			
			if(((RCount == 2) || (DCount == 2)) && (Session.getInstance().getRootmodel().getLoadNextGen().isEmpty())){
				try {
					TargetGeno target= TargetGeno.getTargetGeno();
					if(!(target.getParents().isEmpty())) {
							target.getParents().clear();
							if(target.getParents().size()== 0) {
								SessionTargetGenotype.getInstance().setTargetGeno(target);
//								Session.getInstance().setRootModel(rootModel);
								target.getParents().clear();
								hj.clear();
								typeList.clear();
								mark = 0;
								prevSize = 0;
								typelistCount.clear();
								RCount = 0;
								DCount = 0;
								mark = 0;
								target.setTcreate("");
								check.clear();
							}
						}
					isTargetgenotype=false;
					ifloopCount++;		
				
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}else{

			if(prevSel !=null){
				if(!prevSel.equals(selection1)) {
				try {
					if(cat==1) {
						List<String> gh = new ArrayList<String>();
						List<String> typeGh = new ArrayList<String>();
						gh1.addAll(hj);
						gh.addAll(hj);
						typeGh.addAll(typeList);
						Parents parents = new Parents();
						SelectedParents sp = new SelectedParents();
						MarkersSelectedParents mParents = new MarkersSelectedParents();
						try {
							if(gh.size()!=0){
								for(int h = 0; h < gh.size(); h++){
									parents.setParent(gh.get(h).toString());
									parents.setType(typeGh.get(h).toString());
									sp.setSelectedParents(gh.get(h).toString());
									mParents.setSelectedParents(gh.get(h).toString());
									mParents.setType(typeGh.get(h).toString());
								}
							}
						}catch(Exception e){
							
							e.printStackTrace();
						}
					
					}
					if(tGeno.getParents().size()>=4) {
						tGeno.getParents().remove(tGeno.getParents().size()-1);
					}
				} catch(Exception e){
					MessageDialog.openError(shell, "Error", "Please clear the previous data and choose one Donor and one Recurrent Parent!");
					e.printStackTrace();
				}
				prevSize = ss.size();
			} else{
					try {
					if(cat == 1) {
						List<String> gh = new ArrayList<String>();
						List<String> typeGh= new ArrayList<String>();
						gh1.addAll(hj);
						gh.addAll(hj);
						typeGh.addAll(typeList);
						Parents parents= new Parents();
						SelectedParents sp= new SelectedParents();
						MarkersSelectedParents mParents= new MarkersSelectedParents();
						if(!check.containsAll(gh)){
						
						try {
							if((gh.size()!=0) && (gh.size() == typeGh.size())){
								for(int h = 0; h < gh.size(); h++){
//									System.out.println("Type out= "+typeGh.get(h).toString());
	//								if((typeGh.get(h).toString().equalsIgnoreCase("Recurrent"))) {
										parents.setParent(gh.get(h).toString());
										parents.setType(typeGh.get(h).toString());
										sp.setSelectedParents(gh.get(h).toString());
										mParents.setSelectedParents(gh.get(h).toString());
										mParents.setType(typeGh.get(h).toString());
										check.add(gh.get(h).toString());
	//								}
								}
							}
							
						}catch(Exception e){
							e.printStackTrace();
						}
						List<String> newAcc= rootModel.getAccession();
						List<String> chrName= rootModel.getChrNo();
						List<String> markerName= rootModel.getMarkerName();
						List<Integer> markerPosition= rootModel.getMarkerPosition();
						List<Integer> markerPrevPos= rootModel.getMarkerPrevPos();
						List<Integer> markerNextPos= rootModel.getMarkerNextPos();
						List<String> alleleValue= rootModel.getAccAllele();
						List<String> label= rootModel.getLabel();
						sp.setChrNo(chrName);
						mParents.setAccession(newAcc);
						mParents.setChrNo(chrName);
						mParents.setMarkerName(markerName);
						//System.out.println("MarkerName= "+markerName);
						mParents.setMarkerPosition(markerPosition);
						mParents.setMarkerPrevPos(markerPrevPos);
						mParents.setMarkerNextPos(markerNextPos);
						mParents.setLabel(label);
						mParents.setTargetAlleleValue(alleleValue);
						sp.getMParents().add(mParents);
						parents.getSelParents().add(sp);
						tGeno.getParents().add(parents);
						for(int y=0;y<tGeno.getParents().size();y++)
						
						if(mark == 3 && tGeno.getParents().size()<3) {
							Parents parents1= new Parents();
							SelectedParents sp1= new SelectedParents();
							MarkersSelectedParents mParents1= new MarkersSelectedParents();
							parents1.setParent(gh.get(mark-2).toString());
//							System.out.println("mark1..."+mark+"...."+typeGh.size());
							parents1.setType(typeGh.get(mark-2).toString());
							sp1.setSelectedParents(gh.get(mark-2).toString());
							mParents1.setSelectedParents(gh.get(mark-2).toString());
							mParents1.setType(typeGh.get(mark-2).toString());
							sp1.getMParents().add(mParents1);
							parents1.getSelParents().add(sp1);
							tGeno.getParents().add(parents1);
						}
						count++;
						}else{
							mark--;
						}
					}
					if(tGeno.getParents().size()>=4) {
						tGeno.getParents().remove(tGeno.getParents().size()-1);
					}
				}catch(Exception e){
					e.printStackTrace();
	//				MessageDialog.openError(shell, "Error", "Please clear the previous data and choose one Donor and one Recurrent Parent");
					
	
					try {
						if(Session.getInstance().getRootmodel().getLoadNextGen().isEmpty()){
						final TargetGeno target= TargetGeno.getTargetGeno();
	//					final RootModel rootModel = RootModel.getRootModel();
							
								if(!(target.getParents().isEmpty())) {
										target.getParents().clear();
										if(target.getParents().size()== 0) {
											SessionTargetGenotype.getInstance().setTargetGeno(target);
//											Session.getInstance().setRootModel(rootModel);
											target.getParents().clear();
											hj.clear();
											typeList.clear();
											mark= 0;
											prevSize= 0;
											isTargetgenotype=false;
											target.setTcreate("");
										}
									}
						}
					}catch(Exception e1){
						e1.printStackTrace();}
				}	
				
				prevSize= ss.size(); 
				}
			
			}
			if((ss.size() == 2) && (RCount == 3)){
				RCount = RCount - 1;
			}
			if(tGeno.getTcreate()=="create"){
			if(((RCount == 2) || (DCount == 2)) && (Session.getInstance().getRootmodel().getLoadNextGen().isEmpty())){
				if(ifloopCount == 0){
					MessageDialog.openWarning(shell, "Warning", "Please select one Donor and one Recurrent Parent!");
					RCount=0;
					DCount=0;
					try {
						TargetGeno target= TargetGeno.getTargetGeno();
							
						if(!(target.getParents().isEmpty())) {
								target.getParents().clear();
								if(target.getParents().size()== 0) {
									SessionTargetGenotype.getInstance().setTargetGeno(target);
									//Session.getInstance().setRootModel(rootModel);
									target.getParents().clear();
									hj.clear();
									typeList.clear();
									mark = 0;
									prevSize = 0;
									typelistCount.clear();
									RCount = 0;
									DCount = 0;
									mark = 0;
									check.clear();
									target.setTcreate("");
								}
							}
						isTargetgenotype=false;
						ifloopCount++;		
					
					}catch(Exception e1){
						e1.printStackTrace();
					}
					
				}
			}	
			}
			}
		}else if(ss.size() > 2 && isTargetgenotype == true)	{
//			MessageDialog.openError(shell, "Error", "Please select  one Donor and one Recurrent Parent");
			try {
				final TargetGeno target= TargetGeno.getTargetGeno();
//				final RootModel rootModel = RootModel.getRootModel();
					
						if(!(target.getParents().isEmpty())) {
								target.getParents().clear();
								if(target.getParents().size()== 0) {
									SessionTargetGenotype.getInstance().setTargetGeno(target);
//									Session.getInstance().setRootModel(rootModel);
									target.getParents().clear();
									hj.clear();
									typeList.clear();
									mark= 0;
									prevSize= 0;
								}
							}
						isTargetgenotype=false;
			
			}catch(Exception e1){
				e1.printStackTrace();}
			
		}
		if((loopCount > 0) && (clearHJ == false)){
			prevSize = 0;			
		}
		}
		}
	}
	}
	public void hookContextMenu() {
		MenuManager mgr= new MenuManager();
		mgr.setRemoveAllWhenShown(true);
		mgr.addMenuListener(new IMenuListener() {
			// step1: creating Menu Manager
			public void menuAboutToShow(IMenuManager manager) {
				manager.add(createTarget);
				manager.add(clear);
				// for adding the property to the accessions
				// it will take selected accession..and then it will show the dialog page to that accession............
				// manager.add(new PropertyDialogAction(PlatformUI.getWorkbench().getActiveWorkbenchWindow(), gViewer));
			}	
		});
		// step2: going to behave like a manager for a tree
		// register the pop up menu
		Menu menu= mgr.createContextMenu(gViewer.getControl());
		gViewer.getControl().setMenu(menu);
	}

	public void notifyLoadTargetGenotype(TargetGeno geno) {
		gViewer.setContents(geno);
	}

	public void init(IWorkbenchWindow window) {
		shell = window.getShell();
	}

	public void run(IAction action) {
		// TODO Auto-generated method stub
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
	}
	
		public void refresh() {
			TargetGeno target = null;
			RootModel rootModel =  RootModel.getRootModel();
//			System.out.println("Flag in Target Refresh :"+rootModel.getLoadFlag());
			if(rootModel.getLoadFlag() == null){
				target = TargetGeno.getTargetGeno();
				rootModel = RootModel.getRootModel();
				
			}else{	
				target = SessionTargetGenotype.getInstance().getTargetGeno();
				rootModel = Session.getInstance().getRootmodel();
			}
			ref="";
			SessionTargetGenotype.getInstance().setTargetGeno(target);
			
//			Session.getInstance().setRootModel(rootModel);
		}

		
	
}