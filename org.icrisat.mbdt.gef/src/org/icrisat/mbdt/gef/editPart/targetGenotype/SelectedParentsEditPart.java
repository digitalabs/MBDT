package org.icrisat.mbdt.gef.editPart.targetGenotype;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.icrisat.mbdt.gef.views.GraphicalView;
import org.icrisat.mbdt.gef.views.TargetGenotype;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.TargetGenotype.SelectedParents;

public class SelectedParentsEditPart extends AbstractGraphicalEditPart {

	static int n= 0, k= 0, m= 0, x= 0, p= 0, l= 0, count= 0, i=0;
	int chrWidth= 0;
	List width= new ArrayList();
	//LinkageData lk= LinkageData.getLinkageData();
	static String prevString= null;
	static Integer prevWidth= 0;
	List chrName= new ArrayList();
	static String prevChrName= "";
	List<String> chrNameSubSet= new ArrayList<String>();
	static int ch=0;
	RootModel rootModel = RootModel.getRootModel();
	LinkageData lk;
	float scale = 0.0f;
	@Override
	protected IFigure createFigure() {
		SelectedParents sel=(SelectedParents)getModel();
		if(count== 0) {
			prevChrName= sel.getChrNo().get(count);
		}
		count=1;
		try {
			if(rootModel.getLoadFlag() == null){
				lk = LinkageData.getLinkageData();
			}else{				
				lk = rootModel.getLinkData();
			}
			scale = lk.getScale();
			String countTemp= lk.getCount();
			int index= sel.getChrNo().size();

			String chrName1= sel.getChrNo().get(index-1);
			/*StringTokenizer chr= new StringTokenizer(chrName1, ":Ch");
			chr.nextToken();
			Integer chrInt= Integer.parseInt(chr.nextToken());*/
			if(k!=0 && (!sel.getSelectedParents().equals(prevString))) {
				n= n+20;
				m=0;
				x=0;
				p=0;
				prevWidth=0;
				chrName.clear();
				l=0;
				ch=0;
			}
			if(k== (Integer.parseInt(lk.getCount())*3)) {
				k=0;
				n=0;
			}
			}catch(Exception e){}
			Figure fig= new Figure();	
			width= lk.getMarkerPositions();
			for(int i2=p; i2<(width.size()); i2++) {
				try {
					if(i2<width.size()) {
						int l= i2+1;
						if(i2+1 == width.size()) {
							l=i2;
						}
						if((width.get(l).equals("0") ||width.get(l).equals("0.0") || (i2+1 == width.size()))&&(!lk.getChromName().get(l).equals(lk.getChromName().get(l-1)))) {
							Float chrWid= Float.parseFloat((String) width.get(i2));
							chrWidth= Math.round(chrWid);
							fig.setBackgroundColor(ColorConstants.lightGray);
							fig.setOpaque(true);
							Rectangle position= new Rectangle((2+prevWidth+m+20), 20+n, Math.round(chrWidth*scale), 15);
							fig.setBounds(position);
							p= i2+1;
							ch++;
							prevWidth= prevWidth+Math.round(chrWidth*scale)+m;
							break;
						}
					}
				}catch(Exception e){
				}
			}
			prevString= sel.getSelectedParents();
			x= x+chrWidth;
			m= 30;
			k++;
			return fig;
	}

	@Override
	protected void createEditPolicies() {

	}

	protected List getModelChildren() {

		SelectedParents sel=(SelectedParents)getModel();
		chrName= sel.getChrNo();
		List result= new ArrayList();
		try{
				if((lk.getTgeno()=="create")&&(lk.getSortval()=="sort"))
		{
			l=0; 
			lk.setTgeno("");
		}
		
		try{
			
			
				if(prevChrName.equals(lk.getChromlist().get(i))){
			while(prevChrName.equals(chrName.get(l))) {
				chrNameSubSet.add((String) chrName.get(l));
//				System.out.println("prevChrName...."+prevChrName+"...chr..."+chrName.get(l)+"....l...."+l+"...chrNameSubSet..."+chrName.size());
				l++;
						
			}
			 
String strBuff = "";
			
		    char c,c1;
		    String str1 = (String) chrName.get(l);
		    c1=str1.charAt(0);
		    
		    try {
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
			} catch (Exception e) {
//				e.printStackTrace();
			}
			//---start striga--
			String temp =(String) chrName.get(l);
//			for(int z=0;z<lk.getChromlist().size();z++){
//				
//			}
			//----end
			if(lk.getChromlist().indexOf(temp)<ch){
				if(ch==Integer.parseInt(lk.getCount())){
					prevChrName= (String) chrName.get(l);
					i=0;
				}else{
					prevChrName = "";
					i++;
				}
			}else{
				prevChrName= (String) chrName.get(l);
				i++;
			}
			}else{
				i++;
				if(ch==Integer.parseInt(lk.getCount())){
					prevChrName= (String) chrName.get(l);
					i=0;
				}
			}
		}catch(Exception e){
//			e.printStackTrace();
		}
		
		
		for(int i=0; i<chrNameSubSet.size(); i++) {
			result.add(((SelectedParents)getModel()).getMParents().get(0));
		}
		return result;
		}catch(Exception e){
//			e.printStackTrace();
		}
		return result;
	}
}
