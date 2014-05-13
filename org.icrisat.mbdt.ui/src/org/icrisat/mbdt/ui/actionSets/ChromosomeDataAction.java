package org.icrisat.mbdt.ui.actionSets;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.icrisat.mbdt.gef.views.ChromosomeView;
import org.icrisat.mbdt.gef.views.GraphicalView;
import org.icrisat.mbdt.gef.views.TargetGenotype;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.Chromosome.Chromosome;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.sessions.Session;
import org.icrisat.mbdt.model.sessions.SessionChromosome;
import org.icrisat.mbdt.ui.dialog.ChromosomeDialog;
import org.icrisat.mbdt.ui.views.AccessionListView;
import org.icrisat.mbdt.ui.views.SelectedAccessionsView;


public class ChromosomeDataAction implements IWorkbenchWindowActionDelegate {

	RootModel rModel;
	LinkageData linkage;
	String selectedChromosome = null;
	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub

	}

	public void run(IAction action) {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		ChromosomeDialog chromDialog = new ChromosomeDialog(shell);
		Chromosome chrom;
		RootModel rootModel = RootModel.getRootModel();
		if(rootModel.getLoadFlag() == null){
			rModel = RootModel.getRootModel();
			linkage = LinkageData.getLinkageData();
			chrom = Chromosome.getChromosome();
		}else{
			rModel = Session.getInstance().getRootmodel();
			linkage = rModel.getLinkData();
			chrom = SessionChromosome.getInstance().getChromosome();
		}
		
		//if(rootModel.getGenotype()){
		
		//sortDialog.open();
		//}
		if(chromDialog.open()== Window.OK){
			try{
				selectedChromosome = chromDialog.getChromTextData(); 
				linkage.setSelectedChromosome(selectedChromosome);
				IViewPart iView = PlatformUI.getWorkbench().getActiveWorkbenchWindow ().getActivePage().findView(ChromosomeView.class.getName());
				
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(iView);
				ChromosomeView view1 = (ChromosomeView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ChromosomeView.class.getName());
				SessionChromosome.getInstance().setChromosome(chrom);
			}catch(Exception e){
			}
		}

	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
