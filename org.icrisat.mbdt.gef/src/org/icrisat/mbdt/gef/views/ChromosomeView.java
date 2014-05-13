package org.icrisat.mbdt.gef.views;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Vector;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.editparts.LayerManager;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.print.PrintGraphicalViewerOperation;
import org.eclipse.gef.ui.actions.ToggleGridAction;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.icrisat.mbdt.gef.dialog.PercentageOfRecoveryDialog;
import org.icrisat.mbdt.gef.dialog.QTLDialogBox;
import org.icrisat.mbdt.gef.dialog.SimilarityMatrixDialog;
import org.icrisat.mbdt.gef.editPartFactory.ChromosomeEditPartFactory;
import org.icrisat.mbdt.gef.wizards.BestIndividualsWizard;
import org.icrisat.mbdt.gef.wizards.ShowHiddenElementsWizard;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.Chromosome.Chromosome;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.GenotypeModel.Accessions;
import org.icrisat.mbdt.model.GenotypeModel.Genotype;
import org.icrisat.mbdt.model.GenotypeModel.GenotypeMarkers;
import org.icrisat.mbdt.model.GenotypeModel.SelectedAccessions;
import org.icrisat.mbdt.model.GenotypeModel.SelectedAccessions1;
import org.icrisat.mbdt.model.GenotypeModel.SortedMarkers;
import org.icrisat.mbdt.model.LinkageMapModel.LimsMarkers;
import org.icrisat.mbdt.model.LinkageMapModel.LinkageMap;
import org.icrisat.mbdt.model.LinkageMapModel.MapMarkers;
import org.icrisat.mbdt.model.QTLModel.QTL;
import org.icrisat.mbdt.model.QTLModel.QTLData;
import org.icrisat.mbdt.model.TargetGenotype.TargetGeno;
import org.icrisat.mbdt.model.notifiers.ILoadNotifier;
import org.icrisat.mbdt.model.notifiers.ILoadNotifierChromosome;
import org.icrisat.mbdt.model.sessions.Session;
import org.icrisat.mbdt.model.sessions.SessionChromosome;
import org.icrisat.mbdt.model.sessions.SessionTargetGenotype;

public class ChromosomeView extends ViewPart implements ISelectionListener, ILoadNotifierChromosome  {
	private ScrollingGraphicalViewer gViewer;
	private IAction zoomInAction;
	private IAction zoomOutAction;
	private IAction gridAction;
	private Action refresh;
	private Action sort;
	private Action SimilarityMatrix;
	private Action Percentageofrecovery;
	private Action export;
	private Action printAction;
	private Action foreground;
	private Action background;
	private Action graphicalAction;
	MenuManager menuMgr;
	MenuManager menuMgrsearch;
	private Action hideAction;
	private Action unhideAction;
	private Action CompareAction;
	private Action searchAction;
	private Action searchLinkageAction;
	private Action PopMinAction;
	private Action BestIndividuals;
	private Action phenosort;
	private Action polymorphic;
	
	static boolean issort1=false;
	int comparecount = 0;
	ScalableFreeformRootEditPart sEditPart= new ScalableFreeformRootEditPart();
	static List<String> editParts_unsort= new ArrayList<String>();
	static LinkedHashSet pp= new LinkedHashSet();
	static LinkedHashSet pp1= new LinkedHashSet();
	List<String> editParts1= new ArrayList<String>();
	List<String> editParts2= new ArrayList<String>();
	List<String> editParts3= new ArrayList<String>();
	List<String> testSwap = new ArrayList<String>();
	static String tempVar = "";
	static String tempVar1 = "";	
	boolean issort=false;
	public static String sortval = "";
	static int amme = 1;
	static String selection="";
	LinkedHashSet<String> selectedHideActionAcc = new LinkedHashSet<String>();
	LinkedHashSet<String> selectedHideActionAcc1;
	private Shell shell;
	public static HashMap qtl = new HashMap();
	List<String> sortedAccession = null;
	static boolean lfm = false;
	static boolean rfm = false;	
	RootModel rootModel = Session.getInstance().getRootmodel();
	LinkageData linkage;
	static List<Object> arrHiddenElements = new ArrayList<Object>();
	List<Object> selAccSubsetForUnhide = new ArrayList<Object>();
	LinkedHashSet  selectedMarkers1;
	List<String> editParts= new ArrayList<String>();
	String selectedmarker=null;
	HashMap idLabelMap=new HashMap();
	@Override
	public void createPartControl(Composite parent) {
		gViewer = new ScrollingGraphicalViewer();
		gViewer.setKeyHandler(new GraphicalViewerKeyHandler(gViewer));
		gViewer.createControl(parent);
		
		gViewer.getControl().setBackground(ColorConstants.white);
		gViewer.setEditDomain(new EditDomain());
		gViewer.setRootEditPart(new ScalableFreeformRootEditPart());
		ChromosomeEditPartFactory ge= new ChromosomeEditPartFactory();
		// step giving the factory
		gViewer.setEditPartFactory(ge);
		
		gViewer.setProperty(SnapToGrid.PROPERTY_GRID_ENABLED, true);
		gViewer.setProperty(SnapToGrid.PROPERTY_GRID_VISIBLE, true);
		gViewer.setContents(null);
		((FigureCanvas)gViewer.getControl()).setScrollBarVisibility(FigureCanvas.AUTOMATIC);

		this.getSite().getPage().addSelectionListener("org.icrisat.mbdt.ui.views.AccessionListView", this);
		gViewer.addSelectionChangedListener(new ISelectionChangedListener(){

			public void selectionChanged(SelectionChangedEvent event) {
				EditPart editPart = null;
				//				selection = "";
				pp1.clear();
				Chromosome chrom;
				RootModel rootModel1 = RootModel.getRootModel();
				if(rootModel1.getLoadFlag() == null){
					rootModel = RootModel.getRootModel();
					linkage = LinkageData.getLinkageData();
					chrom = Chromosome.getChromosome();
				}else{						
					rootModel = Session.getInstance().getRootmodel();
					linkage = rootModel.getLinkData();
					chrom = SessionChromosome.getInstance().getChromosome();
				}

				Object[] selectedObject1 = ((IStructuredSelection)gViewer.getSelection()).toArray();
				
				if((selectedObject1.length) != 1) {

					Object testObj = "";
					GraphicalView gView = null;
					try {
						gView = (GraphicalView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(GraphicalView.class.getName());
					} catch (Exception e2) {
						// TODO Auto-generated catch block
					}
					LinkedHashSet<Object> selUnscreenMarkers = new LinkedHashSet<Object>();
					List<EditPart> UnscreenEditParts = new ArrayList<EditPart>();
					List<Object> selectedUnscreeMarkers = new ArrayList<Object>();
					Genotype geno = (Genotype) chrom.getGenotype().get(0);
					LinkageMap map = (LinkageMap)chrom.getLinkagemap().get(0);
					String test, test1 = null;
					for (int i = 0; i < selectedObject1.length; i++) {

						if(Session.getInstance().getRootmodel().getLoadNextGen().isEmpty()){
							editPart = (EditPart) selectedObject1[i];								
							Object selectedObject = editPart.getModel();
							if((selectedObject1.length > 2) && ((selectedObject1[i].toString().contains("GenotypeMarkers")) || (selectedObject1[i].toString().contains("SortedMarkers")))){
								if(i > 1){
									try {
										final TargetGeno target= TargetGeno.getTargetGeno();											
										if(!(target.getParents().isEmpty())) {
											target.getParents().clear();
											if(target.getParents().size()== 0) {
												SessionTargetGenotype.getInstance().setTargetGeno(target);
												target.getParents().clear();														
											}
										}										

									}catch(Exception e1){
									}
								}
							}else if(selectedObject1[i].toString().contains("MapMarkers")){
								try{
									/***foreground and background markers based 
									 * on selection of markers on linkage map
									 */
									test = selectedObject1[i].toString();
									selection="linkage";
									test1 = test.substring(test.indexOf("org"),test.indexOf(" )"));
//									Genotype geno = (Genotype) chrom.getGenotype().get(0);
//									LinkageMap map = (LinkageMap)chrom.getLinkagemap().get(0);
									selectedMarkers1 = new LinkedHashSet();
									for(int i1=0;i1<map.getChromosomes().size();i1++){
										if(map.getChromosomes().get(i1).getMarkers().get(0).toString().contains(test1)){
											selectedmarker=map.getChromosomes().get(i1).getMap_marker();
										}
									}
									selectedMarkers1.add(selectedmarker);					
									pp1.addAll(selectedMarkers1);							

									 List selectedMarkers = new ArrayList();
									 int chkCount = 0;
								for(int i3 = 0; i3 < map.getChromosomes().size(); i3++){
									if(map.getChromosomes().get(i3).getMarkers().get(0).toString().contains(test1)){
										map.getLimsMarkers().get(i3).setstrUnscreenedMrks(map.getChromosomes().get(i3).getMap_marker());
										if(!geno.getHeaderList().contains(map.getChromosomes().get(i3).getMap_marker())){
											selectedMarkers.add(map.getChromosomes().get(i3));
											
											
										}					
									}
									
									chkCount = selectedMarkers.size();
									
									for(int kk = 0; kk < map.getChromosomes().get(i3).getMarkers().size(); kk++){
										if(map.getChromosomes().get(i3).getMarkers().get(kk).toString().equals(test1)){
											if(!selectedUnscreeMarkers.contains(map.getChromosomes().get(i3).getMarkers().get(kk)))
											selectedUnscreeMarkers.add(map.getChromosomes().get(i3).getMarkers().get(kk));
										}
									}
									
									
								}
								map.setSelectedUnscreenMarkers(selectedUnscreeMarkers);
								map.setUnScreenedcount(chkCount);
								
								} catch (Exception e) {
								}	

								
								
							}else if(selectedObject1[i].toString().contains("QTLData")) {


								try{
									Object selectedqtl ="";


									for (int i1 = 0; i1 < selectedObject1.length; i1++) {
										editPart = (EditPart) selectedObject1[i1];
										selectedObject = editPart.getModel();
									}

									QTLData qtlData = (QTLData)selectedObject;
									QTL qtl  = (QTL) rootModel.getQtl().get(0);	
									try {

										if(qtlData.getSelectedqtl()==null){
											qtlData.setSelectedqtl("foreqtl");
										}else{
											if(qtlData.getSelectedqtl().equals("foreqtl")){
												qtlData.setSelectedqtl("backqtl");
											}else{
												qtlData.setSelectedqtl("foreqtl");
											}
										}
									} catch (Exception e) {
									}					
									selection="qtl";
								}catch(Exception e){
								}


							}
						}
					}
					
					//----start--code for unscreened markers---
					
					for(int uns = 0; uns < rootModel.getLinkagemap().get(0).getSelectedUnscreenMarkers().size(); uns++){
						testObj = rootModel.getLinkagemap().get(0).getSelectedUnscreenMarkers().get(uns);					        		
						selUnscreenMarkers.add(testObj);
					}
					
					 for (Iterator<Object> iterator = selUnscreenMarkers.iterator(); iterator.hasNext();) {
						MapMarkers Unscreenmarker = (MapMarkers) iterator.next();
						EditPart UnscreeneditPart = (EditPart) gView.getGViewer().getEditPartRegistry().get(Unscreenmarker);
						UnscreenEditParts.add(UnscreeneditPart);										
						gView.getGViewer().setSelection(new StructuredSelection(UnscreenEditParts));
					 }
				
					//-----end
					
				}else if((selectedObject1.length) == 1) {
					IStructuredSelection ss = ((IStructuredSelection)gViewer.getSelection());
					for(Iterator iterator = ss.iterator();iterator.hasNext();){
						String test = iterator.next()+"";
						if(test.contains("MapMarkers")){
							selection="linkage";
							String test1 = test.substring(20,test.indexOf(" )"));
							Genotype geno = (Genotype) chrom.getGenotype().get(0);
							LinkageMap map = (LinkageMap)chrom.getLinkagemap().get(0);
							selectedMarkers1 = new LinkedHashSet();
							for(int i=0;i<map.getChromosomes().size();i++){
								if(map.getChromosomes().get(i).getMarkers().get(0).toString().contains(test1)){
									selectedmarker=map.getChromosomes().get(i).getMap_marker();
								}
							}
							selectedMarkers1.add(selectedmarker);					
							pp.addAll(selectedMarkers1);
							pp1.addAll(selectedMarkers1);
							linkage.setMarkers_of_intrest(selectedMarkers1);
							linkage.setListSelMarkers(pp);

						}else if(test.contains("QTLData")) {
							Object selectedObject ="";
							for (int i = 0; i < selectedObject1.length; i++) {
								editPart = (EditPart) selectedObject1[i];
								selectedObject = editPart.getModel();
							}
							QTLData qtlData = (QTLData)selectedObject;
							try {

								for(int q = 0;q < rootModel.getQtl().size(); q++){
									if(qtlData.getQtlNames().equals(rootModel.getQtl().get(q).getQtlData().get(q).getQtlNames())){
										if(qtlData.getSelectedqtl()==null){
											qtlData.setSelectedqtl("foreqtl");
										}else{
											if(qtlData.getSelectedqtl().equals("foreqtl")){
												if((GraphicalView.qtl.get(qtlData.getQtlNames()).equals("fore"))&&((qtl.get(qtlData.getQtlNames())==null))){
													qtlData.setSelectedqtl("backqtl");
												}
												else if(qtl.get(qtlData.getQtlNames()).equals("fore")){
													qtlData.setSelectedqtl("backqtl");
												}else{
													qtlData.setSelectedqtl("foreqtl");
												}
											}else{
												qtlData.setSelectedqtl("foreqtl");
											}
										}
									}
								}

							} catch (Exception e) {
							}

							selection="qtl";
							QTL qtl  = (QTL) rootModel.getQtl().get(0);							

							qtl.setDialogQtlStartPt(qtlData.getQtlStartPt());
							qtl.setDialogQtlEndPt(qtlData.getQtlEndPt());
							qtl.setDialogQtlLodSqr(qtlData.getQtlLodsqr());
							qtl.setDialogQtlRSqr(qtlData.getQtlRsqr());
							qtl.setDialogQtlTraitName(qtlData.getQtltraitName());
							qtl.setDialogQtlAddEffects(qtlData.getQtlAddEffects());							
							qtl.setDialogQtlPeakPt(qtlData.getQtlPeakPoints().get(0).getQtlPeakPoints());
							Shell shell = new Shell();
							QTLDialogBox qtlDialog = new QTLDialogBox(shell);
							qtlDialog.open();
							shell.dispose();
						}
					}		
				}

				for (int chk = 0; chk < selectedObject1.length; chk++) {
					String test = selectedObject1[chk]+"";
					if((!test.contains("GenotypeMarkers")) || (!test.contains("SortedMarkers"))){
						selectedHideActionAcc1 = new LinkedHashSet<String>();
						selectedHideActionAcc = new LinkedHashSet<String>();
					}
				}
				comparecount = 0;
				for (int i = 0; i < selectedObject1.length; i++) {
					String test = selectedObject1[i]+"";

					if((test.contains("GenotypeMarkers")) || (test.contains("SortedMarkers"))){
						comparecount++;
						String test1 = test.substring(test.indexOf("org"),test.indexOf(" )"));
						Genotype geno = (Genotype) rootModel.getGenotype().get(0);
						for(int hi = 0; hi < geno.getAccessions().size(); hi++) {
							for(int hj = 0; hj < geno.getAccessions().get(hi).getSelectedAccessions().size(); hj++) {
								for(int hk = 0; hk < geno.getAccessions().get(hi).getSelectedAccessions().get(hj).getSelacc1().size(); hk++) {
									if(linkage.getSortval().equals("sort")){
										for(int hm = 0; hm < geno.getAccessions().get(hi).getSelectedAccessions().get(hj).getSelacc1().get(hk).getSortedMarkers().size(); hm++) {
											if(geno.getAccessions().get(hi).getSelectedAccessions().get(hj).getSelacc1().get(hk).getSortedMarkers().get(hm).toString().equals(test1)){
												selectedHideActionAcc.add(geno.getAccessions().get(hi).getSelectedAccessions().get(hj).getSelacc1().get(hk).getSortedMarkers().get(hm).getAcc());
											}
										}
									}else{
										for(int hm = 0; hm < geno.getAccessions().get(hi).getSelectedAccessions().get(hj).getSelacc1().get(hk).getGenotypeMarkers().size(); hm++) {
											if(geno.getAccessions().get(hi).getSelectedAccessions().get(hj).getSelacc1().get(hk).getGenotypeMarkers().get(hm).toString().equals(test1)){
												selectedHideActionAcc.add(geno.getAccessions().get(hi).getSelectedAccessions().get(hj).getSelacc1().get(hk).getGenotypeMarkers().get(hm).getAcc());
											}
										}	
									}		
								}
							}
						}
						selectedHideActionAcc1 = selectedHideActionAcc;
						linkage.setAccession_of_interest(selectedHideActionAcc1);
					}

					if(test.contains("SelectedAccessions")){
						comparecount++;
						String test1 = test.substring(test.indexOf("org"),test.indexOf(" )"));
						Genotype geno = (Genotype) rootModel.getGenotype().get(0);
						for(int hi = 0; hi < geno.getAccessions().size(); hi++) {
							for(int hj = 0; hj < geno.getAccessions().get(hi).getSelectedAccessions().size(); hj++) {
								if(linkage.getSortval().equals("sort")){
									if(geno.getAccessions().get(hi).getSelectedAccessions().get(hj).toString().equals(test1)){
										selectedHideActionAcc.add(geno.getAccessions().get(hi).getSelectedAccessions().get(hj).getSelAccession());
									}

								}else{
									if(geno.getAccessions().get(hi).getSelectedAccessions().get(hj).toString().equals(test1)){
										selectedHideActionAcc.add(geno.getAccessions().get(hi).getSelectedAccessions().get(hj).getSelAccession());
									}	
								}
							}
						}
						selectedHideActionAcc1 = selectedHideActionAcc;
						linkage.setAccession_of_interest(selectedHideActionAcc1);
					}						
				}
			}
		});
		try {
			SessionChromosome.getInstance().addNotifyListener(this);
		} catch (Exception e2) {
		}
		notifyLoadChromosome(SessionChromosome.getInstance().getChromosome());
		makeActions();
		fillToolBar();
		hookContextMenu();
	}
	
	public void notifyLoadChromosome(Chromosome chromosome) {
		gViewer.setContents(chromosome);
		
	}
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
	}
	private void fillToolBar() {
		this.getViewSite().getActionBars().getToolBarManager().add(graphicalAction);
		this.getViewSite().getActionBars().getToolBarManager().add(zoomInAction);
		this.getViewSite().getActionBars().getToolBarManager().add(zoomOutAction);
		this.getViewSite().getActionBars().getToolBarManager().add(gridAction);
		this.getViewSite().getActionBars().getToolBarManager().add(refresh);
		this.getViewSite().getActionBars().getToolBarManager().add(sort);		
		this.getViewSite().getActionBars().getToolBarManager().add(SimilarityMatrix);
		this.getViewSite().getActionBars().getToolBarManager().add(export);
		this.getViewSite().getActionBars().getToolBarManager().add(Percentageofrecovery);
		this.getViewSite().getActionBars().getToolBarManager().add(printAction);
		
	}

	private void makeActions() {
		ScalableFreeformRootEditPart rootEditPart= ((ScalableFreeformRootEditPart)gViewer.getRootEditPart());
		zoomInAction= new ZoomInAction(rootEditPart.getZoomManager());
		zoomOutAction= new ZoomOutAction(rootEditPart.getZoomManager());
		
		try {
			gridAction= new ToggleGridAction(gViewer);
		} catch (Exception e1){
		}

		graphicalAction = new Action("Graphical View",SWT.KeyDown){
        
        	public void run() {
        		try{
        			RootModel rootModel1 = RootModel.getRootModel();
    				if(rootModel1.getLoadFlag() == null){
    					rootModel = RootModel.getRootModel();
    					linkage = LinkageData.getLinkageData();
    				}else{						
    					rootModel = Session.getInstance().getRootmodel();
    					linkage = rootModel.getLinkData();
    				}
        		IViewPart iView = PlatformUI.getWorkbench().getActiveWorkbenchWindow ().getActivePage().findView(ChromosomeView.class.getName());
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(iView);
				GraphicalView view1 = (GraphicalView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(GraphicalView.class.getName());
				linkage.setCview(false);
				linkage.setSelectedChromosome("");
				
				if(rootModel instanceof RootModel) {
					Session.getInstance().setRootModel(rootModel);
				}
        		}catch(Exception e){
    			}
			}
        	
        };
		printAction =  new Action("Print",SWT.KeyDown){
        	public void run() {

        		int style = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().getStyle();
        		Shell shell = new Shell ((style & SWT.MIRRORED)!=0 ? SWT.RIGHT_TO_LEFT:SWT.NONE);
        		PrintDialog dialog = new PrintDialog(shell,SWT.VERTICAL);
        		PrinterData data = dialog.open();
        		if(data != null){
        		PrintGraphicalViewerOperation operation = new PrintGraphicalViewerOperation(new Printer(data),gViewer);
        		operation.setPrintMode(2);
        		operation.run("Printing Gef");

        		}

        		}
        	
        };
        
        refresh= new Action("Refresh", SWT.KeyDown) {
			public void run() {
				if(refresh.isEnabled()) {
					
					//Code to be updated in your workspace...
					RootModel rootModel1 = RootModel.getRootModel();
					Chromosome chrom;
					//linkage.setSortval("");
					if(rootModel1.getLoadFlag() == null){
						rootModel = RootModel.getRootModel();
						chrom = Chromosome.getChromosome();
					}else{						
						rootModel = Session.getInstance().getRootmodel();
						chrom = SessionChromosome.getInstance().getChromosome();
					}
					
					if(rootModel instanceof RootModel) {
						SessionChromosome.getInstance().setChromosome(chrom);
					}
					
					arrHiddenElements = new ArrayList<Object>();
					selAccSubsetForUnhide = new ArrayList<Object>();
				}
			}
		};

		SimilarityMatrix= new Action("Similarity", SWT.KeyDown){
			public void run() {
				if(SimilarityMatrix.isEnabled()) {
					Shell shell = new Shell();
					
					
					if(Session.getInstance().getRootmodel().getLoadNextGen().isEmpty()){
						SimilarityMatrixDialog npd = new SimilarityMatrixDialog(shell);
						npd.open();
					}else{
						MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Information", " This option is available for First Generation only");	
					}
					
				}
				
			}
		};
		
		Percentageofrecovery= new Action("%Recovery", SWT.KeyDown){
			public void run() {
				if(SimilarityMatrix.isEnabled()) {
					Shell shell = new Shell();
					
					if(Session.getInstance().getRootmodel().getLoadNextGen().isEmpty()){
						MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Information", " This option is available for Second Generation only");
					}else{
						PercentageOfRecoveryDialog prd = new PercentageOfRecoveryDialog(shell);
						prd.open();
					}
					
				}
				
			}
		};
		
		BestIndividuals = new Action("Select Best Individuals ", SWT.NONE){
			public void run(){
				
				RootModel rootModel1 = RootModel.getRootModel();
				RootModel rModel = null;
				if(rootModel1.getLoadFlag() == null){
					rModel = RootModel.getRootModel();
					linkage = LinkageData.getLinkageData();
				}else{						
					rModel = Session.getInstance().getRootmodel();
					linkage = rModel.getLinkData();
				}
				if(Session.getInstance().getRootmodel().getLoadNextGen().isEmpty()){
					MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Information", " This option is available for Second Generation only");
				}else{
					
				if(BestIndividuals.isEnabled()){
					HashMap mstatus=new HashMap();								
					mstatus=rModel.getLinkagemap().get(0).getChromosomes().get(0).getMstatus();
					if(!(mstatus==null)){
					if(	mstatus.size()>0 ){
						BestIndividualsWizard mywizard = new BestIndividualsWizard();
					WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), mywizard);
					dialog.create();
					dialog.open();
					}}
					else{
						MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Information", "No ForeGround Markers Avilable ");
					}
				}
			}
			}
		};

		export= new Action("Export", SWT.KeyDown){
			public void run() {
				
				int format = 0 ;	
				String filePath="";
				int fileExtention=0;
				if(export.isEnabled()) {
					
					FileDialog fileDialog = new FileDialog(Display.getDefault().getActiveShell(),SWT.SAVE);
					
					String[] filterExtensions = new String[] {"*.jpeg", "*.bmp", "*.ico"};
					
					ScalableFreeformRootEditPart rootEditPart = (ScalableFreeformRootEditPart)gViewer.getEditPartRegistry().get(LayerManager.ID);
					IFigure rootFigure = ((LayerManager) rootEditPart).getLayer(LayerConstants.PRINTABLE_LAYERS);
					
					Rectangle rootFigureBounds = rootFigure.getBounds();
					Control figureCanvas = gViewer.getControl();
					fileDialog.setFilterExtensions(filterExtensions);	
					filePath=fileDialog.open();					
					
					if( filePath.endsWith(".jpeg") )
						fileExtention = SWT.IMAGE_JPEG;
					else if( filePath.endsWith(".bmp") )
						fileExtention = SWT.IMAGE_BMP;
					else if( filePath.endsWith(".ico") )
						fileExtention = SWT.IMAGE_ICO;
					
					boolean imagesave =true;
					boolean fileexits =false;
					File file = new File(filePath);
			          if (file.exists()) {
			        	  imagesave=false;
			            // The file already exists; asks for confirmation....
			            MessageBox mb = new MessageBox(fileDialog.getParent(), SWT.ICON_WARNING | SWT.YES | SWT.NO);
			            // We really should read this string from a resource bundle....
			            mb.setMessage(filePath + " already exists. Do you want to replace it?");
			            if(mb.open() == SWT.YES){
			            	fileexits=true;
			            }
			          }
					if(imagesave==true || fileexits==true )
					{
					/*ScalableFreeformRootEditPart rootEditPart = (ScalableFreeformRootEditPart)gViewer.getEditPartRegistry().get(LayerManager.ID);
					IFigure rootFigure = ((LayerManager) rootEditPart).getLayer(LayerConstants.PRINTABLE_LAYERS);
					
					Rectangle rootFigureBounds = rootFigure.getBounds();
					Control figureCanvas = gViewer.getControl()*/;
					GC figureCanvasGC = new GC(figureCanvas);				
					
					Image img = new Image(null, rootFigureBounds.width, rootFigureBounds.height);
					GC imageGC = new GC(img);
					Graphics imgGraphics = new SWTGraphics(imageGC);

					int x = rootFigureBounds.x, y = rootFigureBounds.y;
                    Rectangle clipRect = new Rectangle();

                    while (y < rootFigureBounds.y + rootFigureBounds.height) {
                     while (x < rootFigureBounds.x + rootFigureBounds.width) {
                     imgGraphics.pushState();
//                    getPrinter().startPage();
                     imgGraphics.translate(-x, -y);
                     imgGraphics.getClip(clipRect);
                      clipRect.setLocation(x, y);
                      imgGraphics.clipRect(clipRect);
                      rootFigure.paint(imgGraphics);
//                    getPrinter().endPage();
                      imgGraphics.popState();
                      x += clipRect.width;
                     }
                     x = rootFigureBounds.x;
                     y += clipRect.height;
                    }

					ImageData[] imgData = new ImageData[1];
					imgData[0] = img.getImageData();

					ImageLoader imgLoader = new ImageLoader();
					imgLoader.data = imgData;
					imgLoader.save(filePath, fileExtention);

					figureCanvasGC.dispose();
					imageGC.dispose();
					img.dispose();
					MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Information", "File Created!");
					}					
				}
			}
		};
		sort = new Action("Sort", SWT.KeyDown){
			public void run() {
				if(sort.isEnabled()) {
					issort1 = true;
					int accMarkersCount = 0;
					Chromosome chrom = Chromosome.getChromosome();
					RootModel rootModel1 = RootModel.getRootModel();
					if(rootModel1.getLoadFlag() == null){
						rootModel = RootModel.getRootModel();
						linkage = LinkageData.getLinkageData();
						chrom = Chromosome.getChromosome();
					}else{						
						rootModel = Session.getInstance().getRootmodel();
						linkage = rootModel.getLinkData();
						chrom = SessionChromosome.getInstance().getChromosome();
					}
					
					Genotype geno = (Genotype) rootModel.getGenotype().get(0);
					LinkageMap map = (LinkageMap)rootModel.getLinkagemap().get(0);
					for(int ik = 0; ik < map.getChromosomes().size(); ik++){
						if(geno.getHeaderList().contains(map.getChromosomes().get(ik).getMap_marker())){
							accMarkersCount++;
						}
					}
					SessionChromosome.getInstance().getChromosome().setGenotype(rootModel.getGenotype());
					SessionChromosome.getInstance().getChromosome().setLinkagemap(rootModel.getLinkagemap());
//					if(issort == true) {
					if(linkage.getIsSortingDone() == true){

						selectedMarkers1.clear();
						List eles1=new ArrayList();
						List<String> eles=new ArrayList();
						eles1.addAll(editParts);
						eles.addAll(eles1);
						try {
							for(int i=0; i<chrom.getGenotype().get(0).getAccessions().size(); i++){
								for(int j=0; j<chrom.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions().size(); j++){
									chrom.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions().get(j).setKeyvalue(null);
									if(!(chrom.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions().get(j).getSelAccession().equals(null))){
										String gaga=(eles.get(0)).toString();
										chrom.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions().get(j).setSelAccession(gaga);	
										String ele=chrom.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions().get(j).getSelAccession().toString();	
										for(int k=0; k<chrom.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions().get(j).getSelacc1().size(); k++){
											chrom.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions().get(j).getSelacc1().get(k).setSelAccession(ele);
										}
										eles.remove(0);
									}
								}
								
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
						}
						/*if(rootModel instanceof RootModel) {
							Session.getInstance().setRootModel(rootModel);
						}*/
						selectedMarkers1.add(selectedmarker);
						//sortval = "";
						linkage.setSortval("");
					}
					try{
					if(!(selectedMarkers1.contains(null))){
						SessionChromosome.getInstance().setChromosome(chrom);
					}
					}catch(Exception e){
					}
					List<String> gg=new ArrayList<String>();
					List<String> gg1=new ArrayList<String>();
					Vector<String> acc_bef_sot=new Vector<String>();
					Vector<String> lab_bef_sot=new Vector<String>();
					try {
						acc_bef_sot.addAll(linkage.getAccallellic());
						lab_bef_sot.addAll(linkage.getLabelallelic());
						String id=new String();
						String label=new String();  
						ArrayList labelList = new ArrayList();
						labelList.clear();
						for(int u=0; u<acc_bef_sot.size(); u++){
							id=(String) acc_bef_sot.elementAt(u);
							label=(String) lab_bef_sot.elementAt(u);
							if(idLabelMap.containsKey(id))
							{
								labelList =(ArrayList)idLabelMap.get(id);
								if(labelList.size() < accMarkersCount){
									labelList.add(label);
									idLabelMap.put(id,labelList);
								}
							}
							// If the Map does not contains ID, create new ArrayList and add it to Map
							else
							{
								labelList=new ArrayList();
								labelList.add(label);
								idLabelMap.put(id,labelList);
							}
						}
						gg.addAll(linkage.getSortedAccessions());
						gg1 = gg;
						linkage.setIsSortingDone(true);
						linkage.setSortval("sort");
					}catch(Exception e){
					}
					try {
						for(int i = 0; i < chrom.getGenotype().get(0).getAccessions().size(); i++){
							for(int j = 0; j < chrom.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions().size(); j++){
								try{
								if(!(chrom.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions().get(j).getSelAccession().equals(null))){							
									
									chrom.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions().get(j).setSelAccession(gg1.get(0));
									String ele = chrom.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions().get(j).getSelAccession().toString();	
									for(int k = 0; k < chrom.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions().get(j).getSelacc1().size(); k++){
										chrom.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions().get(j).getSelacc1().get(k).setSelAccession(ele);
									}
									gg1.remove(0);
								}
								chrom.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions().get(j).setKeyvalue(idLabelMap);
								}catch(Exception e){
								}
								}
						}
					} catch (Exception e) {
					}
					if(rootModel instanceof RootModel) {
//						Session.getInstance().setRootModel(rootModel);
						SessionChromosome.getInstance().setChromosome(chrom);
					}
					shell.dispose();
				}
			}
		};
		
		foreground= new Action("Foreground", SWT.KeyDown) {
			public void run() {
				if(foreground.isEnabled()) {
					Qtl_MapData qtl_MapData;
					float scale = 0.0f;
					//-----start---
					try{
						RootModel rootModel1 = RootModel.getRootModel();
						Chromosome chrom;
						if(rootModel1.getLoadFlag() == null){
							rootModel = RootModel.getRootModel();
							linkage = LinkageData.getLinkageData();
							qtl_MapData = Qtl_MapData.getQtl_MapData();
							chrom = Chromosome.getChromosome();
						}else{                                                                
							rootModel = Session.getInstance().getRootmodel();
							linkage = rootModel.getLinkData();
							qtl_MapData = rootModel.getQtlMapData();
							chrom = SessionChromosome.getInstance().getChromosome();
						}
					
						List<String> foregroundMarkers = linkage.getForegroundMarker();
						List<LimsMarkers> limsmarkers = rootModel.getLinkagemap().get(0).getLimsMarkers();
						HashMap mstatus=new HashMap();
						mstatus=rootModel.getLinkagemap().get(0).getChromosomes().get(0).getMstatus();
						scale = linkage.getScale();
						int i=0;
						if(foregroundMarkers==null){
							foregroundMarkers=new ArrayList<String>();
						}
						if(mstatus==null){
							mstatus=new HashMap();
						}
						if(selection=="linkage"){
							for(int l=0;l<rootModel.getLinkagemap().get(0).getChromosomes().size();l++){
								if(pp1.contains(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getMap_marker())){
									mstatus.put(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getMap_marker(), "Foreground");
									rootModel.getLinkagemap().get(0).getChromosomes().get(l).setForestatus("Foreground");
									if(!(foregroundMarkers.contains(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getMap_marker()))){
										foregroundMarkers.add(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getMap_marker());
									}
								}else{
									if(!(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getForestatus().equals("Foreground"))){
										mstatus.put(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getMap_marker(), "Background");
										rootModel.getLinkagemap().get(0).getChromosomes().get(l).setForestatus("Background");
										if(foregroundMarkers.contains(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getMap_marker())){
											foregroundMarkers.remove(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getMap_marker());
										}
									}
								}
							}
							rootModel.getLinkagemap().get(0).getChromosomes().get(0).setMstatus(mstatus);
							selection="";
							pp1.clear();
							linkage.setForegroundMarker(foregroundMarkers);
							
						}else{
							float qtlStartPos = 0, qtlEndPos = 0;
							int markerdist=0;
							int qflag=0,flag=0;
							qtl_MapData.setQtlas("fore");
							HashMap type= new HashMap(); 
							for(int l=0;l<rootModel.getLinkagemap().get(0).getLimsMarkers().size();l++){
						
								if(Math.round(Float.parseFloat(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getMarkpositions().get(0).getDistance()))==0)
									markerdist=0;
								else
									markerdist=markerdist+Math.round(Float.parseFloat(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getMarkpositions().get(0).getDistance()));

								for(int q = 0; q < rootModel.getQtl().size(); q++){   
									if(rootModel.getQtl().get(q).getQtlData().get(q).getSelectedqtl()=="foreqtl"){
										qtl.put(rootModel.getQtl().get(q).getQtlData().get(q).getQtlNames(), "fore");
										
										String strBuff = "";
										char c,c1;
									    String str1 = (String) rootModel.getQtl().get(q).getQtlData().get(q).getQtlChromNames();
									    c1=str1.charAt(0);
									      if(Character.isDigit(c1)){
									    	for (int  i1 = 0; i1 < str1.length() ; i1++) {
										        c = str1.charAt(i1);
										        if (Character.isDigit(c)) {
										            strBuff = strBuff+c;
										        }else{
										        	break;
										        }
										    }
									    }
									    else{
									    	
									    	for (int  i1 = str1.length()-1; i1 >= 0 ; i1--) {
										        c = str1.charAt(i1);
										        if (Character.isDigit(c)) {
										            strBuff = c+strBuff;
										        }else{
										        	break;
										        }
										    }
									    }
									  qflag =Integer.parseInt(strBuff);
									  String strBuff1 = "";
									  char c2,c3;
									  		    String str = (String) rootModel.getLinkagemap().get(0).getChromosomes().get(l).getChromosome();
									  		    c3=str.charAt(0);
									  		      if(Character.isDigit(c3)){
									  		    	for (int  i1 = 0; i1 < str.length() ; i1++) {
									  			        c2 = str.charAt(i1);
									  			        if (Character.isDigit(c2)) {
									  			            strBuff1 = strBuff1+c2;
									  			        }else{
									  			        	break;
									  			        }
									  			    }
									  		    }
									  		    else{
									  		    	
									  		    	for (int  i1 = str.length()-1; i1 >= 0 ; i1--) {
									  			        c2 = str.charAt(i1);
									  			        if (Character.isDigit(c2)) {
									  			            strBuff1 = c2+strBuff1;
									  			        }else{
									  			        	break;
									  			        }
									  			    }
									  		    }
									    
									  		    flag = Integer.parseInt(strBuff1);
										
										if(qflag==flag){
											qtlStartPos = Float.parseFloat(rootModel.getQtl().get(q).getQtlData().get(q).getQtlStartPt());
											qtlEndPos = Float.parseFloat(rootModel.getQtl().get(q).getQtlData().get(q).getQtlEndPt());
											
																	
											if((markerdist>=qtlStartPos)&&(markerdist<=qtlEndPos)){
												i++;
												mstatus.put(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getMap_marker(), "Foreground");
												mstatus.put(rootModel.getLinkagemap().get(0).getChromosomes().get(l-1).getMap_marker(), "Foreground");
												mstatus.put(rootModel.getLinkagemap().get(0).getChromosomes().get(l+1).getMap_marker(), "Foreground");
												rootModel.getLinkagemap().get(0).getChromosomes().get(l).setForestatus("Foreground");
												rootModel.getLinkagemap().get(0).getChromosomes().get(l-1).setForestatus("Foreground");
												rootModel.getLinkagemap().get(0).getChromosomes().get(l+1).setForestatus("Foreground");
												for(int p=l-1; p<=l+1; p++){
													if(!(foregroundMarkers.contains(rootModel.getLinkagemap().get(0).getChromosomes().get(p).getMap_marker()))){
														foregroundMarkers.add(rootModel.getLinkagemap().get(0).getChromosomes().get(p).getMap_marker());
														
													}
												}
												//----Start flanking Markers.....
												
												if(markerdist-Math.round(Float.parseFloat(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getMarkpositions().get(0).getDistance()))<qtlStartPos){
													rootModel.getLinkagemap().get(0).getChromosomes().get(l-1).setForestatus("Foreground FL");
												}
												if(markerdist+Math.round(Float.parseFloat(rootModel.getLinkagemap().get(0).getChromosomes().get(l+1).getMarkpositions().get(0).getDistance()))>qtlEndPos){
												
													rootModel.getLinkagemap().get(0).getChromosomes().get(l+1).setForestatus("Foreground FL");
												}
												//------end Flanking Markers.....
												
											}else{
												 if(!((rootModel.getLinkagemap().get(0).getChromosomes().get(l).getForestatus().equals("Foreground"))||((rootModel.getLinkagemap().get(0).getChromosomes().get(l).getForestatus().equals("Foreground FL"))))){
													mstatus.put(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getMap_marker(), "Background");
													rootModel.getLinkagemap().get(0).getChromosomes().get(l).setForestatus("Background");											
													if(foregroundMarkers.contains(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getMap_marker())){
														foregroundMarkers.remove(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getMap_marker());
													}
												}
											}
											type.put(q, i);
										}
									}
								}
							}
							for(int t = 0; t < rootModel.getQtl().size(); t++){
								if(!(type.get(t)==null))
									if(type.get(t).equals(0)){
										MessageDialog.openInformation(shell, "Alert", "No markers under this QTL region");
										type.remove(t);
										rootModel.getQtl().get(t).getQtlData().get(t).setSelectedqtl("backqtl");
									}
							}
							rootModel.getLinkagemap().get(0).getChromosomes().get(0).setMstatus(mstatus);
						}
						linkage.setForegroundMarker(foregroundMarkers); 
						TargetGeno tG = TargetGeno.getTargetGeno();
		                if(tG.getParents().size()>0){
		                	SessionTargetGenotype.getInstance().setTargetGeno(tG);
		                }
						if(rootModel instanceof RootModel) {
//							Session.getInstance().setRootModel(rootModel);
							SessionChromosome.getInstance().setChromosome(chrom);
						}
					}catch(Exception e){
					}
					//----end---
				}
			}
		};
		
		background= new Action("Background", SWT.KeyDown) {
            public void run() {
                        if(background.isEnabled()) {
                                    Qtl_MapData qtl_MapData;
                                    //-----start---
                                    try{
                                    			Chromosome chrom;
                                                RootModel rootModel1 = RootModel.getRootModel();
                                                if(rootModel1.getLoadFlag() == null){
                                                            rootModel = RootModel.getRootModel();
                                                            linkage = LinkageData.getLinkageData();
                                                            qtl_MapData = Qtl_MapData.getQtl_MapData();
                                                            chrom = Chromosome.getChromosome();
                                                }else{                                                                
                                                            rootModel = Session.getInstance().getRootmodel();
                                                            linkage = rootModel.getLinkData();
                                                            qtl_MapData = rootModel.getQtlMapData();
                                                            chrom = SessionChromosome.getInstance().getChromosome();
                                                }
                                    List<String> foregroundMarkers = linkage.getForegroundMarker();              
                                    HashMap mstatus=new HashMap();
                                    mstatus=rootModel.getLinkagemap().get(0).getChromosomes().get(0).getMstatus();
                                    if(foregroundMarkers==null){
                                                foregroundMarkers=new ArrayList<String>();
                                    }
                                    if(mstatus==null){
                                                mstatus=new HashMap();
                                    }
                                    if(selection=="linkage"){
                                                for(int l=0;l<rootModel.getLinkagemap().get(0).getChromosomes().size();l++){
                                                	if(pp1.contains(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getMap_marker())){
                                                            mstatus.put(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getMap_marker(), "Background");
                                                            rootModel.getLinkagemap().get(0).getChromosomes().get(l).setForestatus("Background");
                                                            if((foregroundMarkers.contains(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getMap_marker()))){
                                                                        foregroundMarkers.remove(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getMap_marker());
                                                            }
                                                            }
                                                            }
                                                rootModel.getLinkagemap().get(0).getChromosomes().get(0).setMstatus(mstatus);
                                                selection="";
                                                pp1.clear();
                                                linkage.setForegroundMarker(foregroundMarkers);
                                    }else{
                                                qtl_MapData.setQtlas("back");
                                    float qtlStartPos = 0, qtlEndPos = 0;
                                    int markerdist=0;
                                    for(int l=0;l<rootModel.getLinkagemap().get(0).getChromosomes().size();l++){
                                    	if(Math.round(Float.parseFloat(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getMarkpositions().get(0).getDistance()))==0)
                                            markerdist=0;
                                        else
                                        	markerdist=markerdist+Math.round(Float.parseFloat(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getMarkpositions().get(0).getDistance()));
                                   for(int q = 0; q < rootModel.getQtl().size(); q++){   
                                	   if(rootModel.getQtl().get(q).getQtlData().get(q).getSelectedqtl()=="backqtl"){
                                		   qtl.put(rootModel.getQtl().get(q).getQtlData().get(q).getQtlNames(), "back");
                                		   String strBuff = "";
        							    char c;
        							    String str = (String) rootModel.getQtl().get(q).getQtlData().get(q).getQtlChromNames();
        							    for (int  i1 = str.length()-1; i1 >= 0 ; i1--) {
        							        c = str.charAt(i1);
        							        
        							        if (Character.isDigit(c)) {
        							            strBuff = c+strBuff;
        							        }else{
        							        	break;
        							        }
        							    }
        								
        							    int qflag =Integer.parseInt(strBuff);
        							    
        							    String strBuff1 = "";
        							    char c1;
        							    String str1 = (String) rootModel.getLinkagemap().get(0).getChromosomes().get(l).getChromosome();
        							    for (int  i1 = str1.length()-1; i1 >= 0 ; i1--) {
        							        c1 = str1.charAt(i1);
        							        
        							        if (Character.isDigit(c1)) {
        							            strBuff1 = c1+strBuff1;
        							        }else{
        							        	break;
        							        }
        							    }
        								
        							    int flag  = Integer.parseInt(strBuff1);
                                		   
                                		  
                                                if(qflag==flag){
                                                qtlStartPos = Float.parseFloat(rootModel.getQtl().get(q).getQtlData().get(q).getQtlStartPt());
                                                qtlEndPos = Float.parseFloat(rootModel.getQtl().get(q).getQtlData().get(q).getQtlEndPt());
                                                if((markerdist>=qtlStartPos)&&(markerdist<=qtlEndPos)){
                                                    mstatus.put(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getMap_marker(), "Background");
                                                    mstatus.put(rootModel.getLinkagemap().get(0).getChromosomes().get(l-1).getMap_marker(), "Background");
                                                    mstatus.put(rootModel.getLinkagemap().get(0).getChromosomes().get(l+1).getMap_marker(), "Background");
                                                    rootModel.getLinkagemap().get(0).getChromosomes().get(l).setForestatus("Background");
                                                    rootModel.getLinkagemap().get(0).getChromosomes().get(l-1).setForestatus("Background");
                                                    rootModel.getLinkagemap().get(0).getChromosomes().get(l+1).setForestatus("Background");
                                                    for(int p=l-1; p<=l+1; p++){
                                                                if((foregroundMarkers.contains(rootModel.getLinkagemap().get(0).getChromosomes().get(p).getMap_marker()))){
                                                                            foregroundMarkers.remove(rootModel.getLinkagemap().get(0).getChromosomes().get(p).getMap_marker());
                                                                            }
                                                                }
                                                }                       
                                                }
                                	   }
                                   }
                                                                                    }
                                    rootModel.getLinkagemap().get(0).getChromosomes().get(0).setMstatus(mstatus);
                                    }
                                    linkage.setForegroundMarker(foregroundMarkers);
                                    TargetGeno tG = TargetGeno.getTargetGeno();
                                    if(tG.getParents().size()>0){
                                    	SessionTargetGenotype.getInstance().setTargetGeno(tG);
                                    }
                                    if(rootModel instanceof RootModel) {
//                                                Session.getInstance().setRootModel(rootModel);
                                                SessionChromosome.getInstance().setChromosome(chrom);
                                                
                                    }
                                  }catch(Exception e){
                                  }
                                    //----end---
                              }
            }
};

hideAction = new Action("Hide", SWT.NONE){
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(hideAction.isEnabled()){
			RootModel rootModel1 = RootModel.getRootModel();
			Chromosome chrom;
			RootModel rModel = null;
			if(rootModel1.getLoadFlag() == null){
				rModel = RootModel.getRootModel();
				linkage = LinkageData.getLinkageData();
				chrom = Chromosome.getChromosome();
			}else{						
				rModel = Session.getInstance().getRootmodel();
				linkage = rModel.getLinkData();
				chrom = SessionChromosome.getInstance().getChromosome();
			}
			
			try{
			Genotype genoHideAction = (Genotype) rootModel.getGenotype().get(0);
			String accession_of_interest = "";
			List<String> accession_of_interest_arr = new ArrayList<String>();
			arrHiddenElements.clear();
//			selAccSubsetForUnhide.clear();
			selAccSubsetForUnhide=	rModel.getGenotype().get(0).getSelAccForUnHide();			
			
			for(Iterator<String> itAcc = linkage.getAccession_of_interest().iterator();itAcc.hasNext();){
				
				accession_of_interest = itAcc.next();
				accession_of_interest_arr.add(accession_of_interest);
				arrHiddenElements.add(accession_of_interest);
			
				for(int si = 0; si < rModel.getGenotype().get(0).getAccessions().size(); si++){
					if(rModel.getGenotype().get(0).getAccessions().get(si).getSelectedAccessions().size() != 0){
						if(rModel.getGenotype().get(0).getAccessions().get(si).getName().equals(accession_of_interest)){
							rModel.getGenotype().get(0).getAccessions().get(si).getSelectedAccessions().clear();
						}
					}
				}						
			}
			linkage.setHideActionStatus(true);
			List<Object> selaccSubset = new ArrayList<Object>();
			
			for(int acc = 0;acc < linkage.getNGloadSelectedAcc().size(); acc++){
				
				Accessions ap = (Accessions) linkage.getNGloadSelectedAcc().get(acc);
				if(arrHiddenElements.contains(ap.getName())){
					selAccSubsetForUnhide.add(linkage.getNGloadSelectedAcc().get(acc));
				}
				
				if(!accession_of_interest_arr.contains(ap.getName())){
					selaccSubset.add(linkage.getNGloadSelectedAcc().get(acc));
				}else{
					if((linkage.getNGloadSelectedAcc().size() == 1)){
						selaccSubset.add("");
					}
				}
			}
//			rModel.getLinkData().setLoadAcc(selaccSubset);
			rModel.getGenotype().get(0).getAccessions().get(0).setLoadAcc(selaccSubset);
			rModel.getGenotype().get(0).setNGHidingElement(selaccSubset);
			rModel.getGenotype().get(0).setNGHidingStatus(true);
			rModel.getGenotype().get(0).setSelAccForUnHide(selAccSubsetForUnhide);
			
			
			//AccessionListView view1 = (AccessionListView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(AccessionListView.class.getName());
			if(rModel instanceof RootModel) {
//				Session.getInstance().setRootModel(rModel);
				SessionChromosome.getInstance().setChromosome(chrom);
				linkage.setSortval("");
			}
			}catch(Exception e){
			}
		}
		//super.run();
	}
};

CompareAction = new Action("Compare", SWT.NONE){
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(hideAction.isEnabled()){
			RootModel rootModel1 = RootModel.getRootModel();
			RootModel rModel = null;
			Chromosome chrom;
			issort1=false;
			if(rootModel1.getLoadFlag() == null){
				rModel = RootModel.getRootModel();
				linkage = LinkageData.getLinkageData();
				chrom = Chromosome.getChromosome();
			}else{						
				rModel = Session.getInstance().getRootmodel();
				linkage = rModel.getLinkData();
				chrom = SessionChromosome.getInstance().getChromosome();
			}
			if(comparecount>0){
			try{
			
			String accession_of_interest = "";
			List<String> accession_of_interest_arr = new ArrayList<String>();
			arrHiddenElements.clear();
			selAccSubsetForUnhide=	rModel.getGenotype().get(0).getSelAccForUnHide();			
			
			
			
			for(Iterator<String> itAcc = linkage.getAccession_of_interest().iterator();itAcc.hasNext();){
				
				accession_of_interest = itAcc.next();
				accession_of_interest_arr.add(accession_of_interest);
				arrHiddenElements.add(accession_of_interest);
			
				for(int si = 0; si < rModel.getGenotype().get(0).getAccessions().size(); si++){
					if(rModel.getGenotype().get(0).getAccessions().get(si).getSelectedAccessions().size() != 0){
						if(!(rModel.getGenotype().get(0).getAccessions().get(si).getName().equals(accession_of_interest))){
							rModel.getGenotype().get(0).getAccessions().get(si).getSelectedAccessions().clear();
						}
					}
				}						
			}
			linkage.setHideActionStatus(true);
			List<Object> selaccSubset = new ArrayList<Object>();
			
			for(int acc = 0;acc < linkage.getNGloadSelectedAcc().size(); acc++){
				
				Accessions ap = (Accessions) linkage.getNGloadSelectedAcc().get(acc);
				if(!(arrHiddenElements.contains(ap.getName()))){
					selAccSubsetForUnhide.add(linkage.getNGloadSelectedAcc().get(acc));
				}
				
				
				if(accession_of_interest_arr.contains(ap.getName())){
					selaccSubset.add(linkage.getNGloadSelectedAcc().get(acc));
				}else{
					if((linkage.getNGloadSelectedAcc().size() == 1)){
						selaccSubset.add("");
					}
				}
				
				
			}
			
//			rModel.getLinkData().setLoadAcc(selaccSubset);
			rModel.getGenotype().get(0).getAccessions().get(0).setLoadAcc(selaccSubset);
			rModel.getGenotype().get(0).setNGHidingElement(selaccSubset);
			rModel.getGenotype().get(0).setNGHidingStatus(true);
			rModel.getGenotype().get(0).setSelAccForUnHide(selAccSubsetForUnhide);
			
			
			//AccessionListView view1 = (AccessionListView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(AccessionListView.class.getName());
			if(rModel instanceof RootModel) {
//				Session.getInstance().setRootModel(rModel);
				SessionChromosome.getInstance().setChromosome(chrom);
				linkage.setSortval("");
			}
			}catch(Exception e){
			}
			}
		}
		//super.run();
	}
};

unhideAction = new Action("Show Hidden Elements", SWT.NONE){
	public void run(){
		
		RootModel rootModel1 = RootModel.getRootModel();
		RootModel rModel = null;
		Chromosome chrom;
		if(rootModel1.getLoadFlag() == null){
			rModel = RootModel.getRootModel();
			linkage = LinkageData.getLinkageData();
			chrom = Chromosome.getChromosome();
		}else{						
			rModel = Session.getInstance().getRootmodel();
			linkage = rModel.getLinkData();
			chrom = SessionChromosome.getInstance().getChromosome();
		}
		
		if(unhideAction.isEnabled()){
			if(	rModel.getGenotype().get(0).getSelAccForUnHide().size()>0 ){
				issort1=false;
			ShowHiddenElementsWizard mywizard = new ShowHiddenElementsWizard();
			WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), mywizard);
			dialog.create();
			dialog.open();
			}
			else{
				MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Information", "No hidden elements Avilable ");
			}
		}
	}
};

		menuMgr = new MenuManager("Assign Marker as");
		menuMgr.add(foreground);
		menuMgr.add(background);
		

	}
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}

	public ScrollingGraphicalViewer getGViewer() {
		return gViewer;
	}
	public void init(IWorkbenchWindow window) {
		Display.getDefault().getActiveShell();
	}

	public void run(IAction action) {
		// TODO Auto-generated method stub
		
	}

	public void selectionChanged(IWorkbenchPart part,ISelection selection) {

		try {
			//Graphical View
			IStructuredSelection ss= (IStructuredSelection) selection;
			List<String> getSelAcc= new ArrayList<String>();
			List selectionPoints= new ArrayList<Accessions>();
			RootModel rootModel1 = RootModel.getRootModel();
			LinkageData linkage;
			if(rootModel1.getLoadFlag() == null){
				rootModel = RootModel.getRootModel();
				linkage = LinkageData.getLinkageData();
			}else{						
				rootModel = Session.getInstance().getRootmodel();
				linkage = rootModel.getLinkData();
			}
			
			editParts.clear();
			int first=0;
			
			for(Iterator iterator= ss.iterator(); iterator.hasNext();) {
				SelectedAccessions selAcc= new SelectedAccessions();	
				SelectedAccessions1 selAcc1= new SelectedAccessions1();
				SortedMarkers smarkers=new SortedMarkers();
				Accessions accessions = (Accessions)iterator.next();
				GenotypeMarkers genotypeMarkers=new GenotypeMarkers();
				String gh= accessions.getName();
				if(first == 0) {
					rootModel.setFirstAcc(gh);					
				}
				first++;
				//added on 3rd NOV 2009.....
				accessions.getSelectedAccessions().clear();
				
				selectionPoints.add(accessions.getName());		
				accessions.setSelectedAccessions1(selectionPoints);
				List allellic = accessions.getAlleValues();
				
				getSelAcc.add(gh);
				selAcc.setSelAccession(gh);
				selAcc1.setSelAccession(gh);
				smarkers.setAcc(gh);
				genotypeMarkers.setAcc(gh);
				genotypeMarkers.setMarkerName(accessions.getGmark());
				genotypeMarkers.setAlleles(allellic);
				smarkers.setAlleles(allellic);
				selAcc1.getGenotypeMarkers().add(genotypeMarkers);
				selAcc1.getSortedMarkers().add(smarkers);
				selAcc.getSelacc1().add(selAcc1);
				accessions.getSelectedAccessions().add(selAcc);
				selAcc.setParent(accessions);
				TargetGeno tG = TargetGeno.getTargetGeno();
				
				//------------start marker count------
				try{
				int markercount=0;
					for(int i=0;i<rootModel.getLinkagemap().get(0).getChromosomes().size();i++){
						if(rootModel.getGenotype().get(0).getHeaderList().contains(rootModel.getLinkagemap().get(0).getChromosomes().get(i).getMap_marker())){
							markercount++;
						}
					}
				
				tG.setMarkerCount(markercount);
				}catch(Exception e){
				}
				//---------------end--------
			
			}
			editParts_unsort.addAll(getSelAcc);
			editParts.addAll(getSelAcc);
			try {
				linkage.setAccFrmGV(editParts);
			}catch(Exception e){
			}
			RootModel r1 = Session.getInstance().getRootmodel();
			for(int i = 0; i < r1.getGenotype().get(0).getAccessions().size(); i++){
				for(int j=0; j<r1.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions().size(); j++){
					r1.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions().get(j).setSelacc(editParts);
					if(!(editParts.contains(r1.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions().get(j).getSelAccession()))){
						
						r1.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions().remove(j).getSelAccession();
					}
				}
			}
			
		} catch(Exception e){	
		}
		notifyLoadChromosome(SessionChromosome.getInstance().getChromosome());
	}

	public void hookContextMenu() {
		/*MenuManager mgr= new MenuManager();
		mgr.setRemoveAllWhenShown(true);
		mgr.addMenuListener(		new IMenuListener() {
			// step1: creating Menu Manager
			public void menuAboutToShow(IMenuManager manager) {
				manager.add(menuMgrsearch);
				manager.add(phenosort);
				manager.add(polymorphic);
			}	
		});
		*/
		MenuManager mgr1 = new MenuManager();
		mgr1.setRemoveAllWhenShown(true);
		mgr1.addMenuListener(new IMenuListener() {
			
			public void menuAboutToShow(IMenuManager manager) {
				// TODO Auto-generated method stub
				manager.add(refresh);
				manager.add(sort);
				manager.add(export);
				manager.add(menuMgr);
				manager.add(CompareAction);
				manager.add(hideAction);
				manager.add(unhideAction);
				manager.add(BestIndividuals);
				
			}
		});
		Menu menu1 = mgr1.createContextMenu(gViewer.getControl());
		gViewer.getControl().setMenu(menu1);
	}
	

	

	

}
