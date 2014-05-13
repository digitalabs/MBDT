package org.icrisat.mbdt.gef.editPartFactory;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.icrisat.mbdt.gef.editPart.genotype.AccessionsEditPart;
import org.icrisat.mbdt.gef.editPart.genotype.GMarkerEditPart;
import org.icrisat.mbdt.gef.editPart.genotype.GenotypeEditPart;
import org.icrisat.mbdt.gef.editPart.genotype.SMarkerEditPart;
import org.icrisat.mbdt.gef.editPart.genotype.SelectedAccessionsEditpart;
import org.icrisat.mbdt.gef.editPart.linkageMap.LinkageEditPart;
import org.icrisat.mbdt.gef.editPart.linkageMap.MapIntervalsEditPart;
import org.icrisat.mbdt.gef.editPart.linkageMap.MapMarkersEditPart;
import org.icrisat.mbdt.gef.editPart.linkageMap.MapMarkersScaleEditPart;
import org.icrisat.mbdt.gef.editPart.linkageMap.MarkerPositionEditPart;
import org.icrisat.mbdt.gef.editPart.linkageMap.MarkerPositionScaleEditPart;
import org.icrisat.mbdt.gef.editPart.linkageMap.ScaleEditPart;
import org.icrisat.mbdt.gef.editPart.nextGenerations.LoadNextGen1EditPart;
import org.icrisat.mbdt.gef.editPart.nextGenerations.LoadNextGen2EditPart;
import org.icrisat.mbdt.gef.editPart.nextGenerations.LoadNextGen3EditPart;
import org.icrisat.mbdt.gef.editPart.nextGenerations.LoadNextGenEditPart;
import org.icrisat.mbdt.gef.editPart.qtl.QTLDataEditPart;
import org.icrisat.mbdt.gef.editPart.qtl.QTLEditPart;
import org.icrisat.mbdt.gef.editPart.qtl.QTLGrayLinesEditPart;
import org.icrisat.mbdt.gef.editPart.qtl.QTLPeakPointEditPart;
import org.icrisat.mbdt.gef.editPart.qtl.QTLTraitLlinesEditPart;
import org.icrisat.mbdt.gef.rootEditPart.ChromosomeEditPart;
import org.icrisat.mbdt.gef.rootEditPart.RootEditPart;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.Chromosome.Chromosome;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.GenotypeModel.Genotype;
import org.icrisat.mbdt.model.GenotypeModel.GenotypeMarkers;
import org.icrisat.mbdt.model.GenotypeModel.SelectedAccessions;
import org.icrisat.mbdt.model.GenotypeModel.SelectedAccessions1;
import org.icrisat.mbdt.model.GenotypeModel.SortedMarkers;
import org.icrisat.mbdt.model.LinkageMapModel.Intervals;
import org.icrisat.mbdt.model.LinkageMapModel.LinkageMap;
import org.icrisat.mbdt.model.LinkageMapModel.LinkageScaleModel;
import org.icrisat.mbdt.model.LinkageMapModel.MapMarkers;
import org.icrisat.mbdt.model.LinkageMapModel.MapMarkersScale;
import org.icrisat.mbdt.model.LinkageMapModel.MarkerPosition;
import org.icrisat.mbdt.model.LinkageMapModel.MarkerPositionScale;
import org.icrisat.mbdt.model.NextGeneration.LoadNextGenData;
import org.icrisat.mbdt.model.NextGeneration.LoadNextGenDataFirstChild;
import org.icrisat.mbdt.model.NextGeneration.LoadNextGenDataSecondChild;
import org.icrisat.mbdt.model.QTLModel.QTL;
import org.icrisat.mbdt.model.QTLModel.QTLData;
import org.icrisat.mbdt.model.QTLModel.QTLPeakPoints;

public class ChromosomeEditPartFactory implements EditPartFactory {

	static int p = 0;
	public EditPart createEditPart(EditPart context, Object model) {
		RootModel rootModel = RootModel.getRootModel();
		LinkageData linkage;
		
		if(rootModel.getLoadFlag() == null){
			 linkage = LinkageData.getLinkageData();
		}else{
				linkage = rootModel.getLinkData();
		}
		linkage.setCview(true);
		EditPart ep = null;
		try {
		if(model instanceof Chromosome){	
			ep=new ChromosomeEditPart();
		}else if(model instanceof LinkageMap){
			ep = new LinkageEditPart();
		}else if(model instanceof Intervals){
			ep = new MapIntervalsEditPart();	
		}else if(model instanceof MapMarkers) {
			ep = new MapMarkersEditPart();	
		}else if(model instanceof MarkerPosition) {
			ep = new MarkerPositionEditPart();	
		}else if (model instanceof QTL) {
			ep = new QTLEditPart();
		}else if(model instanceof QTLData){
			ep = new QTLDataEditPart();
		}else if(model instanceof Genotype) {
			ep= new GenotypeEditPart();
		}else if(model instanceof SelectedAccessions) {
			ep= new AccessionsEditPart();	
		}else if(model instanceof SelectedAccessions1) {
			ep= new SelectedAccessionsEditpart();	
		}else if(model instanceof GenotypeMarkers) {
			ep= new GMarkerEditPart();
		}else if(model  instanceof SortedMarkers){
			ep= new SMarkerEditPart();
		}else if(model  instanceof QTLPeakPoints) {
			ep = new QTLPeakPointEditPart();			
		}else if(model.toString().contains("ENVI")) {
			ep = new QTLGrayLinesEditPart();			
		}else if(model  instanceof LinkageScaleModel) {
			ep = new ScaleEditPart();			
		}else if(model instanceof MapMarkersScale) {
			ep = new MapMarkersScaleEditPart();	
		}else if(model instanceof MarkerPositionScale) {
			ep = new MarkerPositionScaleEditPart();	
		}else if(model instanceof LoadNextGenData) {
			ep = new LoadNextGenEditPart();	
		}else if(model instanceof LoadNextGenDataFirstChild) {
			ep = new LoadNextGen1EditPart();		
		}else if(model.toString().contains("Target")) {
			ep = new LoadNextGen2EditPart();	
		}else if(model instanceof LoadNextGenDataSecondChild) {
			ep = new LoadNextGen3EditPart();	
		}else {
			ep = new QTLTraitLlinesEditPart();
		}
	     ep.setModel(model);
		}catch(Exception e){
		}
		return ep;
	}
}
