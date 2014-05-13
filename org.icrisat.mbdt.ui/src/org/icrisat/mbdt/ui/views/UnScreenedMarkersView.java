package org.icrisat.mbdt.ui.views;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.icrisat.mbdt.gef.views.ChromosomeView;
import org.icrisat.mbdt.gef.views.GraphicalView;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.Chromosome.Chromosome;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.GenotypeModel.Genotype;
import org.icrisat.mbdt.model.LinkageMapModel.LinkageMap;
import org.icrisat.mbdt.model.TargetGenotype.TargetGeno;
import org.icrisat.mbdt.model.sessions.Session;
import org.icrisat.mbdt.model.sessions.SessionChromosome;
import org.icrisat.mbdt.model.sessions.SessionTargetGenotype;
import org.icrisat.mbdt.ui.actions.LoadMultipleAction;
import org.icrisat.mbdt.ui.wizards.FilesImportWizard;

public class UnScreenedMarkersView extends ViewPart implements ISelectionListener{

	private TableViewer tViewer;
	private Action exportMarkers;
	private Shell shell;
	private IWorkbenchWindow window;
	static List<Object> selectedUnscreeMarkers = new ArrayList<Object>();
	static int count = 0;
	static List<Object> selectedMarkersDup = new ArrayList<Object>();
	Boolean flagChk = false;
	
	public UnScreenedMarkersView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		
		Table tbl = new Table(parent,SWT.FULL_SELECTION);
		tbl.setHeaderVisible(true);
		tbl.setLinesVisible(true);
		
		TableColumn tColumn = new TableColumn(tbl,SWT.NONE);
		tColumn.setWidth(100);
		tColumn.setText("UnScreened Markers");
		
		tViewer = new TableViewer(tbl);
		tViewer.setContentProvider(new UnScreenedMarkersContentProvider());
		tViewer.setLabelProvider(new UnScreenedMarkersLabelProvider());
		
		/*************registering this class as listener to the provider*************/
		RootModel rootModel = Session.getInstance().getRootmodel();
		LinkageData linkageData;
		try {
			if(rootModel.getLoadFlag() == null){
				 linkageData = LinkageData.getLinkageData();
			}else{
				linkageData = rootModel.getLinkData();
			}
			
			if(linkageData.isCview()){
				this.getSite().getPage().addSelectionListener(ChromosomeView.class.getName(), this);
				
			}else{
				this.getSite().getPage().addSelectionListener(GraphicalView.class.getName(), this);			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			this.getSite().getPage().addSelectionListener(GraphicalView.class.getName(), this);	
		}
		
		//for loading the saved projects.....
		LoadMultipleAction loadmultiple = new LoadMultipleAction(window);
		loadmultiple.run();
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		IStructuredSelection ss = (IStructuredSelection)selection;
		List selectedMarkers = new ArrayList();
		
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		String dialogboxTitle = "Information";
		int chkCount = 0;
		
		
		try {
			for(Iterator iterator = ss.iterator();iterator.hasNext();){			
				
				String test = iterator.next()+"";
				if(test.contains("MapMarkersEditPart")){
					String test1 = test.substring(test.indexOf("org"),test.indexOf(" )"));
					
					
					RootModel rootModel = Session.getInstance().getRootmodel();
					Genotype geno;
					LinkageData linkageData;
					if(rootModel.getLoadFlag() == null){
						 linkageData = LinkageData.getLinkageData();
					}else{
						linkageData = rootModel.getLinkData();
					}
					Chromosome chrom = SessionChromosome.getInstance().getChromosome();
					LinkageMap map;
					if(linkageData.isCview()){
						map = (LinkageMap)chrom.getLinkagemap().get(0);
						geno = (Genotype) chrom.getGenotype().get(0);
					}else{
						geno = (Genotype) rootModel.getGenotype().get(0);
						map = (LinkageMap)rootModel.getLinkagemap().get(0);
					}
					for(int i = 0; i < map.getChromosomes().size(); i++){
						if(map.getChromosomes().get(i).getMarkers().get(0).toString().contains(test1)){
							map.getLimsMarkers().get(i).setstrUnscreenedMrks(map.getChromosomes().get(i).getMap_marker());
							if(!geno.getHeaderList().contains(map.getChromosomes().get(i).getMap_marker())){
								selectedMarkers.add(map.getChromosomes().get(i));
								
							}					
						}
						
						chkCount = selectedMarkers.size();
						
						for(int kk = 0; kk < map.getChromosomes().get(i).getMarkers().size(); kk++){
							if(map.getChromosomes().get(i).getMarkers().get(kk).toString().equals(test1)){
								selectedUnscreeMarkers.add(map.getChromosomes().get(i).getMarkers().get(kk));
							}
						}
						
						
					}
					
					
					map.setSelectedUnscreenMarkers(selectedUnscreeMarkers);
					map.setUnScreenedcount(chkCount);	
				}
			}
			if(this.tViewer.getContentProvider()!= null){
				tViewer.setInput(selectedMarkers);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		//
	}
}
