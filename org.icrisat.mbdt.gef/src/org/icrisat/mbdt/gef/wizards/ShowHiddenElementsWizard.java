package org.icrisat.mbdt.gef.wizards;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.Chromosome.Chromosome;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.GenotypeModel.Accessions;
import org.icrisat.mbdt.model.GenotypeModel.SelectedAccessions;
import org.icrisat.mbdt.model.sessions.Session;
import org.icrisat.mbdt.model.sessions.SessionChromosome;

public class ShowHiddenElementsWizard extends Wizard implements IImportWizard{
	
	List<String> arrReturnString = new ArrayList<String>();
	List<Object> noselaccSubset = new ArrayList<Object>();
	List<Object> selaccSubset = new ArrayList<Object>();
	List<Object> selacc_wizard = new ArrayList<Object>();
	public ShowHiddenElementsWizard() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		
		ShowHiddenElementsPage page = (ShowHiddenElementsPage)getPage("ShowHiddenElementsPage");
		String returnString = page.getSelectedAccforUnHide();
		RootModel rootModel = RootModel.getRootModel();
		RootModel rModel = null;
		LinkageData linkage;
		SelectedAccessions selAcc= new SelectedAccessions();	
		Chromosome chrom;
		if(rootModel.getLoadFlag() == null){
			rModel = RootModel.getRootModel();
			linkage = LinkageData.getLinkageData();
			chrom = Chromosome.getChromosome();
		}else{
			rModel = Session.getInstance().getRootmodel();
			linkage = rModel.getLinkData();
			chrom = SessionChromosome.getInstance().getChromosome();
		}
		
		String[] splitReturnString = returnString.split("@!@");
		for(int i = 0; i < splitReturnString.length; i++){
			arrReturnString.add(splitReturnString[i]);
			selacc_wizard.add(splitReturnString[i]);
		}
		
		for(int acc = 0;acc < rModel.getGenotype().get(0).getNGHidingElement().size(); acc++){
			Accessions ap = (Accessions) rModel.getGenotype().get(0).getNGHidingElement().get(acc);
			arrReturnString.add(ap.getName());
		}
		
/******* here write logic for comparing arrReturnString and ap.getName() and create a list 
 * and set that in model.After that in AccessionLIstView.java class notify method
 * write code for setselection().
 * ******/		
		linkage.setHideActionStatus(true);
	
		String accession_of_interest ="";
		for(int i=0; i<selacc_wizard.size(); i++ )
		{
			accession_of_interest =(String) selacc_wizard.get(i);
		for(int si = 0; si < rModel.getGenotype().get(0).getAccessions().size(); si++){
			if(rModel.getGenotype().get(0).getAccessions().get(si).getSelectedAccessions().size() != 0){
				if(rModel.getGenotype().get(0).getAccessions().get(si).getName().equals(accession_of_interest)){
					selAcc.setSelAccession(accession_of_interest);
					rModel.getGenotype().get(0).getAccessions().get(si).getSelectedAccessions().add(selAcc) ;
				}
			}
		}	
		
	}
		for(int si = 0; si < rModel.getGenotype().get(0).getAccessions().size(); si++){
			if(arrReturnString.contains(rModel.getGenotype().get(0).getAccessions().get(si).getName())){
				selaccSubset.add(rModel.getGenotype().get(0).getAccessions().get(si));
			}
			else{
				if(!(rootModel.getLoadFlag() == null)){
				noselaccSubset.add(rModel.getGenotype().get(0).getAccessions().get(si));
				}else{
					if(rModel.getGenotype().get(0).getAccessions().get(si).getSelectedAccessions().size() != 0){
						noselaccSubset.add(rModel.getGenotype().get(0).getAccessions().get(si));
					}
				}
				
			}
		}
//		rModel.setLinkData(linkage);
		try{
		rModel.getLinkData().setLoadAcc(selaccSubset);
		}catch(Exception e){
			
		}
		rModel.getGenotype().get(0).getAccessions().get(0).setLoadAcc(selaccSubset);
		rModel.getGenotype().get(0).setNGHidingElement(selaccSubset);
		rModel.getGenotype().get(0).setNGHidingStatus(true);
		rModel.getGenotype().get(0).setSelAccForUnHide(noselaccSubset);
		
		if(rModel instanceof RootModel) {
			Session.getInstance().setRootModel(rModel);
			SessionChromosome.getInstance().setChromosome(chrom);
			linkage.setSortval("");
		}
		
		return true;
	}

	
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addPages() {
		// TODO Auto-generated method stub
		addPage(new ShowHiddenElementsPage("ShowHiddenElementsPage"));
	}

}
