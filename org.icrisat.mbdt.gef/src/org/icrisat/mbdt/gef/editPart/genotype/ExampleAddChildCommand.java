package org.icrisat.mbdt.gef.editPart.genotype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.icrisat.mbdt.gef.editPart.targetGenotype.MarkerParentsEditPart;
import org.icrisat.mbdt.gef.views.TargetGenotype;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.TargetGenotype.ColorAllele;
import org.icrisat.mbdt.model.TargetGenotype.MarkersSelectedParents;
import org.icrisat.mbdt.model.TargetGenotype.TargetGeno;
import org.icrisat.mbdt.model.sessions.Session;

public class ExampleAddChildCommand extends Command {

	private EditPart child;
	private EditPart after;
	private Shell shell;
	HashMap<String, String> mapDragDetails = new HashMap<String, String>();
	public static List<String> movements= new ArrayList<String>();
	public static List<String> dragged_marker = new ArrayList<String>();
	public static List<String> TargetCharAllele= new ArrayList<String>();
	
	public ExampleAddChildCommand(EditPart child, EditPart after) {
		this.child = child;
		this.after = after;
	}

	public ExampleAddChildCommand() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		super.execute();
		try{
			MarkersSelectedParents mp1= (MarkersSelectedParents) ((MarkerParentsEditPart)child.getParent()).getModel();
			MarkersSelectedParents mp2= (MarkersSelectedParents) ((MarkerParentsEditPart)after.getParent()).getModel();
			int position= mp2.getColorAllele().indexOf(after.getModel());
			String message="";
			TargetGenotype tg = new TargetGenotype();
			if(mp2.getSelectedParents().contains("T")){
				if(((ColorAllele)after.getModel()).getAlleleName().equals("amono"))
				{
					message = "Monomorphic Marker "+((ColorAllele)child.getModel()).getMarkerName() + " moved from Parent (" +mp1.getSelectedParents() + ") to Target ( " + mp2.getSelectedParents() +")";
					 movements.add(message);
					 MessageDialog.openInformation(shell, "alert", message);
				}else{
				if(((ColorAllele)child.getModel()).getMarkerName().equals(((ColorAllele)after.getModel()).getMarkerName())){
				mp1.getColorAllele().remove(child.getModel());
				mp2.getColorAllele().remove(after.getModel());
				TargetGeno tG= TargetGeno.getTargetGeno();
				tG.setPosition(position);
				tG.setPositionParents(position);
				mp2.getColorAllele().add(position, (ColorAllele)child.getModel());
				mp1.getColorAllele().add(position, (ColorAllele) child.getModel());
				
				
				//setting AlleleValue pair + Dragged value into Model for database....
				mapDragDetails.put(((ColorAllele)child.getModel()).getTargetAlleleValue(), "Dragged");
				((ColorAllele)child.getModel()).setTargetDraggedValue(mapDragDetails);
				
				
				TargetCharAllele=((ColorAllele)child.getModel()).getTargetCharAlleleValues();
				if((TargetCharAllele.get(position).equals("A"))){
					TargetCharAllele.remove(position);
					TargetCharAllele. add(position, "B");
				}else{
					TargetCharAllele.remove(position);
					TargetCharAllele.add(position, "A");
				}
				((ColorAllele)child.getModel()).setTargetCharAlleleValues(TargetCharAllele);
				//-----start-----
				RootModel rootModel = RootModel.getRootModel();
				LinkageData linkage;
				if(rootModel.getLoadFlag() == null){
					rootModel = RootModel.getRootModel();
					linkage = LinkageData.getLinkageData();
				}else{						
					rootModel = Session.getInstance().getRootmodel();
					linkage = rootModel.getLinkData();
				}
				
				HashMap mstatus=new HashMap();								
				mstatus=rootModel.getLinkagemap().get(0).getChromosomes().get(0).getMstatus();
				List<String> foregroundMarkers = linkage.getForegroundMarker();
				if(mstatus==null){
					mstatus=new HashMap();
				}
				if(foregroundMarkers==null){
					foregroundMarkers=new ArrayList<String>();
				}
				mstatus.put(((ColorAllele)child.getModel()).getMarkerName(), "Foreground");
				for(int l=0;l<rootModel.getLinkagemap().get(0).getChromosomes().size();l++){
					
					if(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getMap_marker().equals(((ColorAllele)child.getModel()).getMarkerName())){
						
						if(mp1.getType().equals("Recurrent")){
							rootModel.getLinkagemap().get(0).getChromosomes().get(l).setForestatus("Background");
							if((foregroundMarkers.contains(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getMap_marker()))){
								foregroundMarkers.remove(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getMap_marker());
								}
							
						}else{
							rootModel.getLinkagemap().get(0).getChromosomes().get(l).setForestatus("Foreground");
							if(!(foregroundMarkers.contains(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getMap_marker()))){
								foregroundMarkers.add(rootModel.getLinkagemap().get(0).getChromosomes().get(l).getMap_marker());
								}
							dragged_marker.add(((ColorAllele)child.getModel()).getMarkerName());
						}
					}
					
					}
				linkage.setForegroundMarker(foregroundMarkers);
				rootModel.getLinkagemap().get(0).getChromosomes().get(0).setMstatus(mstatus);
				
				
				//-----end------
				
				child.getParent().refresh();
				after.getParent().refresh();
				 message = "Marker "+((ColorAllele)child.getModel()).getMarkerName() + " moved from Parent (" +mp1.getSelectedParents() + ") to Target ( " + mp2.getSelectedParents() +")";
				 movements.add(message);
				}
				}
			}else{
				 message ="";
			}
			tg.refresh();	
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TargetGenotype.class.getName()).getViewSite().getActionBars().getStatusLineManager().setMessage(message);
		}catch(Exception e){
		}
	}
}