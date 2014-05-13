package org.icrisat.mbdt.gef.editPart.targetGenotype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;


import org.eclipse.draw2d.Border;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.graphics.Color;
import org.icrisat.mbdt.gef.Activator;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.TargetGenotype.ColorAllele;
import org.icrisat.mbdt.model.TargetGenotype.TargetGeno;
import org.icrisat.mbdt.model.sessions.Session;

public class ColorAlleleEditPart extends AbstractGraphicalEditPart implements IPropertyChangeListener{

	
	RootModel rootModel;
	Rectangle position = null;
	String label = null;
	ColorAllele cAllele;
	HashMap<String, List<Figure>> figureMap; 
	Figure fig2;

	@Override
	protected IFigure createFigure() {

		Activator.getDefault().getPreferenceStore().addPropertyChangeListener(this);
		
		
		cAllele= (ColorAllele)getModel();

		Figure fig= new Figure();
		fig2= new Figure();
		position= new Rectangle(cAllele.getRectanglePositionX()+20, cAllele.getRectanglePositionX(), cAllele.getWidth(), cAllele.getHeight());
		String label = cAllele.getAlleleName();

		
		try{
			if (getFigureMap().containsKey(label)) {
				((ArrayList<Figure>)getFigureMap().get(label)).add(fig);
			} else {
				ArrayList<Figure> figureList = new ArrayList<Figure>();
				figureList.add(fig);
				getFigureMap().put(label, figureList);
			}
			
			
//			assignColorsToAllels(label, fig);
	
		
		fig.setBounds(position);
//		fig.setToolTip(new Label(cAllele.getMarkerName()+": "+label));
		if(label.equals("amono")){
		fig.setToolTip(new Label(cAllele.getMarkerName()+":  Monomorphic"));
		}else{
		fig.setToolTip(new Label(cAllele.getMarkerName()+": "+cAllele.getTargetAlleleValue()));
		}
		try{
			if(label.contains("@@")){
				if(label.contains("a50")){
					label="a50";
					assignColorsToAllels(label, fig);
					fig.add(fig);
				}else{
					String status="";
					try{
						rootModel = Session.getInstance().getRootmodel();
						HashMap mstatus=new HashMap();								
						mstatus=rootModel.getLinkagemap().get(0).getChromosomes().get(0).getMstatus();
						
						if(!(mstatus==null)){
						if(!(mstatus.get(cAllele.getMarkerName())==null)){					
							if(mstatus.get(cAllele.getMarkerName()).equals("Foreground")){
								status="Foreground";
							}
						}
						}
					}catch(Exception e){
					}
				Rectangle hetero1 = null;
				int i1 = Integer.parseInt(cAllele.getTargetAlleleValue().substring(0, cAllele.getTargetAlleleValue().indexOf(" ")));
				int i2 = Integer.parseInt(cAllele.getTargetAlleleValue().substring(cAllele.getTargetAlleleValue().indexOf(" ")+1, cAllele.getTargetAlleleValue().length()));
				if(i1<i2){
				if((status.equals("Foreground"))){
					hetero1= new Rectangle(cAllele.getRectanglePositionX()+20, cAllele.getRectanglePositionX()-1,  cAllele.getWidth(), 7);	
				}else{
					hetero1= new Rectangle(cAllele.getRectanglePositionX()+20, cAllele.getRectanglePositionX(), cAllele.getWidth(), 4);	
				}
				fig2.setBounds(hetero1);
				fig2.setToolTip(new Label(cAllele.getMarkerName()+" : "+cAllele.getTargetAlleleValue().substring(0, cAllele.getTargetAlleleValue().indexOf(" "))));
				assignColorsToAllels(label.substring(0,label.indexOf("@@")), fig2);
				
				fig2.setOpaque(true);
				fig.add(fig2);
				if (getFigureMap().containsKey(label.substring(0,label.indexOf("@@")))) {
					((ArrayList<Figure>)getFigureMap().get(label.substring(0,label.indexOf("@@")))).add(fig2);
				} else {
					ArrayList<Figure> figureList = new ArrayList<Figure>();
					figureList.add(fig2);
					getFigureMap().put(label.substring(0,label.indexOf("@@")), figureList);
				}
				fig2 = new Figure();
				if(status.equals("Foreground")){
					hetero1= new Rectangle(cAllele.getRectanglePositionX()+20, cAllele.getRectanglePositionX()+5, cAllele.getWidth(), 6);	
				}else{
					hetero1= new Rectangle(cAllele.getRectanglePositionX()+20, cAllele.getRectanglePositionX()+3, cAllele.getWidth(), 3);
				}
				fig2.setBounds(hetero1);
				fig2.setToolTip(new Label(cAllele.getMarkerName()+" : "+cAllele.getTargetAlleleValue().substring(cAllele.getTargetAlleleValue().indexOf(" ")+1, cAllele.getTargetAlleleValue().length())));
//				fig2.setBackgroundColor(ColorConstants.darkGreen);
				assignColorsToAllels(label.substring(label.indexOf("@@")+2,label.length()), fig2);
				fig2.setVisible(true);
				fig2.setOpaque(true);
				fig.add(fig2);
				if (getFigureMap().containsKey(label.substring(label.indexOf("@@")+2,label.length()))) {
					((ArrayList<Figure>)getFigureMap().get(label.substring(label.indexOf("@@")+2,label.length()))).add(fig2);
				} else {
					ArrayList<Figure> figureList = new ArrayList<Figure>();
					figureList.add(fig2);
					getFigureMap().put(label.substring(label.indexOf("@@")+2,label.length()), figureList);
				}
				}else{
					
					if(status.equals("Foreground")){
						hetero1= new Rectangle(cAllele.getRectanglePositionX()+20, cAllele.getRectanglePositionX()+5, cAllele.getWidth(), 6);	
					}else{
						hetero1= new Rectangle(cAllele.getRectanglePositionX()+20, cAllele.getRectanglePositionX()+3, cAllele.getWidth(), 3);
					}
					fig2.setBounds(hetero1);
					fig2.setToolTip(new Label(cAllele.getMarkerName()+" : "+cAllele.getTargetAlleleValue().substring(0, cAllele.getTargetAlleleValue().indexOf(" "))));
					assignColorsToAllels(label.substring(0,label.indexOf("@@")), fig2);
					
					fig2.setOpaque(true);
					fig.add(fig2);
					if (getFigureMap().containsKey(label.substring(0,label.indexOf("@@")))) {
						((ArrayList<Figure>)getFigureMap().get(label.substring(0,label.indexOf("@@")))).add(fig2);
					} else {
						ArrayList<Figure> figureList = new ArrayList<Figure>();
						figureList.add(fig2);
						getFigureMap().put(label.substring(0,label.indexOf("@@")), figureList);
					}
					fig2 = new Figure();
					if(status.equals("Foreground")){
						hetero1= new Rectangle(cAllele.getRectanglePositionX()+20, cAllele.getRectanglePositionX()-1,  cAllele.getWidth(), 7);	
					}else{
						hetero1= new Rectangle(cAllele.getRectanglePositionX()+20, cAllele.getRectanglePositionX(), cAllele.getWidth(), 4);	
					}
					fig2.setBounds(hetero1);
					fig2.setToolTip(new Label(cAllele.getMarkerName()+" : "+cAllele.getTargetAlleleValue().substring(cAllele.getTargetAlleleValue().indexOf(" ")+1, cAllele.getTargetAlleleValue().length())));
//					fig2.setBackgroundColor(ColorConstants.darkGreen);
					assignColorsToAllels(label.substring(label.indexOf("@@")+2,label.length()), fig2);
					fig2.setVisible(true);
					fig2.setOpaque(true);
					fig.add(fig2);
			
					if (getFigureMap().containsKey(label.substring(label.indexOf("@@")+2,label.length()))) {
						((ArrayList<Figure>)getFigureMap().get(label.substring(label.indexOf("@@")+2,label.length()))).add(fig2);
					} else {
						ArrayList<Figure> figureList = new ArrayList<Figure>();
						figureList.add(fig2);
						getFigureMap().put(label.substring(label.indexOf("@@")+2,label.length()), figureList);
					}
				}
				}
			}else{

				assignColorsToAllels(label, fig);
				
			}
			}catch(Exception e){
			}
		TargetGeno tG= TargetGeno.getTargetGeno();
		tG.setPosition(0);
		return fig;
		}catch(Exception e){
		}
		return fig;
	}

	public void assignColorsToAllels(String label, Figure figure) {
		String colorValue =  Activator.getDefault().getPreferenceStore().getString(label);
	int color1=0,color2=0,color3=0;
	if(label.equals("a50"))
	{
		 StringTokenizer st= new StringTokenizer(colorValue, ",");
		 
		 color1= Integer.parseInt(st.nextToken());
		 color2= Integer.parseInt(st.nextToken());
		 color3= Integer.parseInt(st.nextToken());
	}else if(label.equals("amono"))
	{
//		 colorValue="192,192,192";
		 StringTokenizer st= new StringTokenizer(colorValue, ",");
		 color1= Integer.parseInt(st.nextToken());
		 color2= Integer.parseInt(st.nextToken());
		 color3= Integer.parseInt(st.nextToken());
	}else{
		 StringTokenizer st= new StringTokenizer(colorValue, ",");
		 color1= Integer.parseInt(st.nextToken());
		 color2= Integer.parseInt(st.nextToken());
		 color3= Integer.parseInt(st.nextToken());
	}
	
	figure.setBackgroundColor(new Color(null, color1 , color2 ,color3));
	figure.setOpaque(true);
	}
	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub
		
	}

	public void propertyChange(org.eclipse.jface.util.PropertyChangeEvent arg0) {
		List<Figure> figureList = getFigureMap().get(arg0.getProperty());
		if (figureList != null) {
			for (Figure figure : figureList) {
				assignColorsToAllels(arg0.getProperty(), figure);
			}
		}
//		assignColorsToAllels(event.getProperty(), fig);
	}

	public HashMap<String, List<Figure>> getFigureMap() {
		if(figureMap == null) {
			figureMap = new HashMap<String, List<Figure>>();
		}
		return figureMap;
	}
	
}
