 package org.icrisat.mbdt.gef.editPart.targetGenotype;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.icrisat.mbdt.gef.Activator;
import org.icrisat.mbdt.gef.editPart.genotype.ExampleFlowLayoutEditPolicy;
import org.icrisat.mbdt.gef.views.TargetGenotype;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.TargetGenotype.ColorAllele;
import org.icrisat.mbdt.model.TargetGenotype.MarkersSelectedParents;
import org.icrisat.mbdt.model.TargetGenotype.TargetGeno;
import org.icrisat.mbdt.model.sessions.Session;
public class MarkerParentsEditPart extends AbstractGraphicalEditPart implements PropertyChangeListener{
	List accList = new ArrayList();
	static int k = 0, p = 0, m = 0, n = 0, x = 0, q = 0, r = 0, temp = 0, notify = 0, gmt = 0, count = 0,tempQvalue = 0, tempCount = 0;
	static int chrWidth=0;
	static List<String> chrName = new ArrayList<String>();
	static List<Integer> markerPos = new ArrayList<Integer>();
	static List<Integer> markerPrevPos = new ArrayList<Integer>();
	static List<Integer> markerNextPos = new ArrayList<Integer>();
	List<String> markerName = new ArrayList<String>();
	List<String> markerAllele = new ArrayList<String>();
	List<String> targetAlleleValue = new ArrayList<String>();
	List<String> targetAlleleValueFrmDB = new ArrayList<String>();
	static HashMap<Integer,String> targetAlleleValueFrmDB1 = new HashMap<Integer,String>();
	List<String> targetCombiValues = new ArrayList<String>();
	static String prevChrName = "";
	List width = new ArrayList();
	List ss = new ArrayList();
	//LinkageData lk = LinkageData.getLinkageData();
	RootModel rootModel = RootModel.getRootModel();
	LinkageData lk;
	static String prevString = null;
	static String prevSel = null;
	static Integer prevWidth= 0;
	int markerCount;
	int temp1;
	String selectedParent1 = "";
	static List<String> allelvalueColor = new ArrayList<String>();
	static int markerNum ;	
	static List<String> donorAlleleValue = new ArrayList<String>();
	static List<String> donormarkerAllele = new ArrayList<String>();
	
	static List<String> recurrentAlleleValue = new ArrayList<String>();
	static List<String> recurrentmarkerAllele = new ArrayList<String>();
	static int qtltarget=0;
	int qtar=0;
	String marker;
	Rectangle position = null;
	static int prevStart1=0,widthStart1=0,ir=0;
	float scale = 0.0f;
	HashMap chromPos = new HashMap();
	HashMap mstatus=new HashMap();	
	
	@Override
	protected IFigure createFigure() {
		TargetGeno tG = TargetGeno.getTargetGeno();
		markerCount = tG.getMarkerCount();
		Figure fig = new Figure();
		try {
		if(rootModel.getLoadFlag() == null){
			lk = LinkageData.getLinkageData();
		}else{	
		rootModel = Session.getInstance().getRootmodel();			
			lk = rootModel.getLinkData();
		}
		scale = lk.getScale();
		MarkersSelectedParents mp = (MarkersSelectedParents)getModel();
		String selectedParent = mp.getSelectedParents();
		if(count== 0) {
			prevChrName= mp.getChrNo().get(count);
		}
		count=1;
		
		
		
		
		
//		System.out.println("MarkerParents= "+selectedParent);
		if(selectedParent.endsWith("Target")) {
			selectedParent1 = selectedParent;
			int index = selectedParent.indexOf("Target");
			selectedParent = selectedParent.substring(0, index);
		}
//		System.out.println("TargetGeno= "+selectedParent1);
		ColorAllele colorAllele = new ColorAllele();
		colorAllele.setSelectedParents(selectedParent);
	
		accList = mp.getAccession();
		chrName = mp.getChrNo();
		markerPos = mp.getMarkerPosition();
		markerPrevPos = mp.getMarkerPrevPos();
		markerNextPos = mp.getMarkerNextPos();
		markerName = mp.getMarkerName();
		markerAllele = mp.getLabel();
		targetAlleleValue = mp.getTargetAlleleValue();
		if(mp.getType()=="Donor"){
			mp.setDonorAlleleValue(targetAlleleValue);
//			donormarkerAllele=mp.getLabel();
		}
		//Here we are getting the data from LoadDBImportWizard class....
		targetAlleleValueFrmDB = mp.getTargetAlleleFrmDataBase();
		targetCombiValues = mp.getTargetMACCombi();
		
		if(k!=0 && (!mp.getSelectedParents().equals(prevString))) {
			n= n+20;
			m=0; x=0; p=0; r=0; temp=0; prevWidth=0; chrWidth=0;
		}
//		System.out.println("getMarkerCount()= "+(tG.getMarkerCount()-2));
//		System.out.println("k...."+k+"...markercount....."+markerCount);
		if(k== ((markerCount)*3)) {
			k=0;
			n=0;
			allelvalueColor.clear();
			
		}
		if(r == 0) {
			if(accList.contains(selectedParent)) {
				markerNum=0;
				int index = accList.indexOf(selectedParent);
				q = index;
				temp1 = q;
				r++;
			}
		}
		int start=0;
		int prevStart= 0;
		int widthStart= 0;
		
		/**
		 * Code -- If target is already available....
		 **/	
		if(targetAlleleValueFrmDB.size() > 0){
			if(tempCount == 0){
				tempQvalue = q;
				for(int ta = 0; ta < targetAlleleValueFrmDB.size(); ta++){
					if(selectedParent1.endsWith("Target")) {					
						targetAlleleValueFrmDB1.put(tempQvalue, targetAlleleValueFrmDB.get(ta));
						tempQvalue ++;
					}	
					tempCount ++;
				}			
			}
		}
		if(selectedParent.equals(accList.get(q))) {
			String testing = "", prevRecValue = "", testing2 = "";int prevRecValue1 = 0;
			start = markerPos.get(q);
			prevStart = Math.round(markerPrevPos.get(q)*scale);
			widthStart =Math.round( markerNextPos.get(q)*scale);
			
			prevStart1 = prevStart;
			widthStart1 = widthStart;
			 position = new Rectangle(2+prevStart+x+m+20, 22+n, widthStart, 6);
			//----start--foreground back ground markers---
			try{
				HashMap mstatus=new HashMap();								
				mstatus=rootModel.getLinkagemap().get(0).getChromosomes().get(0).getMstatus();
			if(!(mstatus==null)){
				if(!(mstatus.get(markerName.get(q))==null)){					
				if(mstatus.get(markerName.get(q)).equals("Foreground")){
					position= new Rectangle(2+prevStart+x+m+20, 20+n, widthStart, 12);
				}else{
					 position= new Rectangle(2+prevStart+x+m+20, 22+n, widthStart, 6);
				}
				}else{
					 position= new Rectangle(2+prevStart+x+m+20, 22+n, widthStart, 6);
				}
			}else{
				 position= new Rectangle(2+prevStart+x+m+20, 22+n, widthStart, 6);
			}	
			}catch(Exception e){
			}
			//-------end---fore ground back ground markers----
			
			colorAllele.setRectanglePositionX(2+prevStart+x+m);
			colorAllele.setRectanglePositionY(20+n);
			colorAllele.setWidth(widthStart);
			colorAllele.setHeight(10);
			colorAllele.setChromosome(prevChrName);
			colorAllele.setMarkerName(markerName.get(q));
			//String label = markerAllele.get(q);
			String label = "";
			String	allVal = "";
			
			/**
			 * Setting Allele Value
			 **/
						
			if(selectedParent1.endsWith("Target")) {
				if(targetAlleleValueFrmDB1.size() > 0){
					allVal = targetAlleleValueFrmDB1.get(q);
					testing = markerName.get(q)+"::"+allVal;
					
					testing2 = allVal;
					prevRecValue1 = q;
				}else{
					allVal = targetAlleleValue.get(q);
					markerNum++;
					
					if(allVal.equals("0 0"))
					{
					allelvalueColor.add("-");
					
					}else{
					
					allelvalueColor.add("A");
					}
				}	
				//----start--foreground back ground markers---
				try{
												
					mstatus=rootModel.getLinkagemap().get(0).getChromosomes().get(0).getMstatus();

					if(mstatus==null){
						mstatus= new HashMap();
					}
					if(!(mstatus==null)){
					if(!(mstatus.get(markerName.get(q))==null)){					
						if(mstatus.get(markerName.get(q)).equals("Foreground")){
						position= new Rectangle(2+prevStart+x+m+20, 20+n, widthStart, 12);
						allVal=donorAlleleValue.get(qtltarget);
						
						//--------------------start-----
						allelvalueColor.remove(allelvalueColor.size()-1);
						allelvalueColor.add("B");
																		
						//------end----
						
//						allelvalueColor.add("B");
						qtar=q;
					}else{
						 position= new Rectangle(2+prevStart+x+m+20, 22+n, widthStart, 6);
					}
					}else{
						 position= new Rectangle(2+prevStart+x+m+20, 22+n, widthStart, 6);
					}
				}else{
					 position= new Rectangle(2+prevStart+x+m+20, 22+n, widthStart, 6);
				}	
				}catch(Exception e){
				}
										
				
				//-------end---fore ground back ground markers----
					
				qtltarget++;
				
			}else{
				allVal = targetAlleleValue.get(q);					
				if(mp.getType().equals("Donor")){
					donorAlleleValue.add(targetAlleleValue.get(q));
				}
				if(mp.getType().equals("Recurrent")){
					recurrentAlleleValue.add(targetAlleleValue.get(q));
				}
				
			}
			
			if(prevRecValue1 > 0){
				prevRecValue = targetAlleleValue.get(prevRecValue1);
			}

			if(selectedParent1.endsWith("Target")) {
				
				if(testing2 != null){
					if(testing2.equals(prevRecValue)){
						label = markerAllele.get(q);
						try {
							if(mstatus.get(markerName.get(q)).equals("Foreground")){
								label=donormarkerAllele.get(qtltarget-1);
							}
						} catch (Exception e) {
						}
					}else{
						for(int k1 = 0; k1 < targetCombiValues.size(); k1++){						
							if(targetCombiValues.get(k1).contains(testing)){							
								String[] testing1 = targetCombiValues.get(k1).split("::");						
								label = testing1[2];						
							}
						}
					}
				}else{
					label = markerAllele.get(q);
				}
				if((qtar==q)&&(q!=0)){ 
					label=donormarkerAllele.get(qtltarget-1);
				}
				try {
					HashMap monomarkers=new HashMap();
					monomarkers=rootModel.getLinkagemap().get(0).getLimsMarkers().get(0).getMonomorphicMarkers();
					if(monomarkers==null){
						monomarkers=new HashMap();
					}
					if(donorAlleleValue.get(qtltarget-1).equals(recurrentAlleleValue.get(qtltarget-1))){	
						label="amono";
						allelvalueColor.remove(allelvalueColor.size()-1);
						allelvalueColor.add("Monomorphic");
						monomarkers.put(markerName.get(q), "Monomorphic");
					}else{
						monomarkers.put(markerName.get(q), "polymorphic");
					}
					rootModel.getLinkagemap().get(0).getLimsMarkers().get(0).setMonomorphicMarkers(monomarkers);
				} catch (Exception e) {
				}
				
			}else{
				if(mp.getType().equals("Donor")){
					donormarkerAllele.add(markerAllele.get(q));
				}
				if(mp.getType().equals("Recurrent")){
					recurrentmarkerAllele.add(markerAllele.get(q));
				}
				label = markerAllele.get(q);				
			}
			
			colorAllele.setTargetCharAlleleValues(allelvalueColor);
			colorAllele.setTargetAlleleValue(allVal);			
			colorAllele.setAlleleName(label);
			
			fig.setOpaque(true);
			
			fig.setBounds(position);
			q++;
		}
		mp.getColorAllele().add(colorAllele);
//		width = lk.getMarkerPositions();
		chromPos = lk.getChromPos();
		String name="";
		if(chrName.size()==q)
		{		
			name= chrName.get(temp1);
		}
		else{
		 name= chrName.get(q);
		}
//		System.out.println("q..."+q+"...name..."+name);
		if(!prevChrName.equals(name)) {
			p= temp;		
			chrWidth = (Integer)chromPos.get(name);		
			x = chrWidth;
			m= 0;
		}
		if(chrName.size()==q)
		{		
			prevChrName= chrName.get(temp1);
		}
		else{
			prevChrName= chrName.get(q);
		}
		
//		prevChrName= chrName.get(q);
		prevString= mp.getSelectedParents();
		prevWidth= start;
		
		fig.setLayoutManager(new FlowLayout());
		k++;
	 
		}catch(Exception e) {
		}
		return fig;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new ExampleFlowLayoutEditPolicy());
	}
	
	@Override
	protected void refreshVisuals() {
		try{
			RootModel rootModel1 = Session.getInstance().getRootmodel();

			HashMap mstatus=new HashMap();
			mstatus=rootModel1.getLinkagemap().get(0).getChromosomes().get(0).getMstatus();
			
			if(mstatus==null){
				mstatus=new HashMap();
			}
			
			int marketPosition = markerName.indexOf(marker);
			if(marketPosition != -1 && (rootModel1.getLinkagemap().get(0).getChromosomes().get(marketPosition).getForestatus().equals("Foreground")||rootModel1.getLinkagemap().get(0).getChromosomes().get(marketPosition).getForestatus().equals("Foreground FL"))){
				position= new Rectangle(2+prevStart1+x+m+20, 20+n, widthStart1, 12);
				mstatus.put(marker, "Foreground");
				
			
			}else{
				position= new Rectangle(2+prevStart1+x+m+20, 22+n, widthStart1, 6);
				mstatus.put(marker, "Background");
			}
			
			rootModel1.getLinkagemap().get(0).getChromosomes().get(0).setMstatus(mstatus);
						
		}catch(Exception e){
		}
	}

	protected List getModelChildren() {
		// Remove the duplicate values here
		MarkersSelectedParents mp1= (MarkersSelectedParents)getModel();
		String selParent= mp1.getSelectedParents();
		TargetGeno tG= TargetGeno.getTargetGeno();
		int position= tG.getPosition();
		List result= new ArrayList();
		RootModel rp= RootModel.getRootModel();            
//		System.out.println("gmt= "+gmt);
//		System.out.println("parents= "+mp1.getAccession().size());
		try {
			if(gmt == (markerCount)) {
				gmt= 0;
			}
			if(!selParent.equals(prevSel)) {
				
				gmt= 0;
				if(position!= 0) {
					gmt= position;
				}
			}
//			System.out.println("selParent.."+selParent+"...."+prevSel);
		result.add(((MarkersSelectedParents)getModel()).getColorAllele().get(gmt));
		if(position == 0)
		gmt++;
//		System.out.println("gmt= "+gmt);
		prevSel= selParent;
		}catch(Exception e){
		}
//		System.out.println("result..."+result);
          		return result;
	}
	
	
	public void propertyChange(PropertyChangeEvent evt) {
		marker = evt.getPropertyName().substring(26, evt.getPropertyName().length());
		if (evt.getPropertyName().substring(0,26).equals("PROPERTY_CHANGE_Forestatus")) {
			refreshVisuals();
//			createFigure();
		}
	}

	
	
}
