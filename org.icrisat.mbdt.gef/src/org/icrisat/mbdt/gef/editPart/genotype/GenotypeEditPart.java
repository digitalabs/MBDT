package org.icrisat.mbdt.gef.editPart.genotype;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.icrisat.mbdt.gef.editPart.targetGenotype.ExampleXYLayoutPolicy;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.GenotypeModel.Genotype;
import org.icrisat.mbdt.model.GenotypeModel.SelectedAccessions;
import org.icrisat.mbdt.model.sessions.Session;

public class GenotypeEditPart extends AbstractGraphicalEditPart {

	static int p = 0, q = 0;
	// LinkageData linkage=LinkageData.getLinkageData();
	RootModel rootModel = Session.getInstance().getRootmodel();
	LinkageData linkage;
	List sortOrder = new ArrayList<String>();
	
	@Override
	protected IFigure createFigure() {
		p++;
		FreeformLayer lyr = new FreeformLayer();
		lyr.setLayoutManager(new XYLayout());
		Genotype gt = (Genotype) getModel();
		// for(int i= 0; i<gt.getAccessions().size(); i++) {
		// for(int j=0;
		// j<gt.getAccessions().get(i).getSelectedAccessions().size(); j++) {
		// System.out.println("GenotypeEditPart=
		// "+gt.getAccessions().get(i).getSelectedAccessions().get(j).getSelAccession());
		// }
		// }
		getChildren().clear();
		return lyr;
	}

	@Override
	protected void createEditPolicies() {
		try{
			if(rootModel.getLoadFlag() == null){
				 linkage = LinkageData.getLinkageData();
			}else{
					linkage = rootModel.getLinkData();
			}
		if(!(linkage.isCview())){
			installEditPolicy(EditPolicy.LAYOUT_ROLE, new ExampleXYLayoutPolicy());
		}}catch(Exception e){
		}
	}

	protected List getModelChildren() {
		q++;
		List result = new ArrayList();
		try {
			RootModel rootModel = Session.getInstance().getRootmodel();
			if (rootModel.getLoadFlag() == null) {
				linkage = LinkageData.getLinkageData();
			} else {
				linkage = rootModel.getLinkData();
			}

			List gh = new ArrayList();
			List gh1 = new ArrayList();
			gh.addAll(linkage.getAccFrmGV());
			gh1 = gh;
			HashMap<String, String> result1 = new HashMap<String, String>();
			String accession_of_interest = "";

			if (linkage.getHideActionStatus() == true) {
				try {
					for (Iterator<String> itAcc = linkage.getAccession_of_interest().iterator(); itAcc.hasNext();) {
						accession_of_interest = itAcc.next();
						
						if (gh1.contains(accession_of_interest)) {
							// gh1.remove(accession_of_interest);
						}
					}
				} catch (Exception e) {
				}
				linkage.setHideActionStatus(false);
			}

			try{
			for (int i = 0; i < ((Genotype) getModel()).getAccessions().size(); i++) {
				for (int jh = 0; jh < ((Genotype) getModel()).getAccessions().get(i).getSelectedAccessions().size(); jh++) {
					
					if (((Genotype) getModel()).getAccessions().get(i).getSelectedAccessions().size() != 0) {
						((Genotype) getModel()).getAccessions().get(i).getSelectedAccessions().get(0).setType(((Genotype) getModel()).getAccessions().get(i).getType());
						result1.put((gh1.get(0)).toString(),((Genotype) getModel()).getAccessions().get(i).getSelectedAccessions().get(0).getType());
						gh1.remove(0);
						result.add(((Genotype) getModel()).getAccessions().get(i).getSelectedAccessions().get(0));
					}
				}
			}
			}catch(Exception e){
				
			}
			linkage.setAccWithLabels(result1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void setSortOrder(List<String> accessionName) {
		this.sortOrder = accessionName;

	}
}
