package org.icrisat.mbdt.gef.preferences;

import java.util.Iterator;

import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.icrisat.mbdt.gef.Activator;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.*;
import org.icrisat.mbdt.model.TargetGenotype.TargetGeno;
import org.icrisat.mbdt.model.sessions.Session;
import org.icrisat.mbdt.model.sessions.SessionTargetGenotype;

public class ColorEditorPage extends FieldEditorPreferencePage implements
IWorkbenchPreferencePage {

//	LinkageData lk = LinkageData.getLinkageData();
	RootModel rootModel = Session.getInstance().getRootmodel();
	LinkageData lk;
	

	public ColorEditorPage() {
		this.setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	public ColorEditorPage(int style) {
		super(style);	
	}

	public ColorEditorPage(String title, int style) {
		super(title, style);
	}

	public ColorEditorPage(String title, ImageDescriptor image, int style) {
		super(title, image, style);
	}

	@Override
	protected void createFieldEditors() {
		int colorCount = 0;
		
		if(rootModel.getLoadFlag() == null){
			 lk = LinkageData.getLinkageData();
		}else{
			
			lk = rootModel.getLinkData();
		}
		
		if(Session.getInstance().getRootmodel().getLoadNextGen().isEmpty()){
			colorCount = lk.getMaximumAllele();
			
		}else{
			//System.out.println("pref ::"+rootModel.getGenotype().get(0).getNGTColorPrefCount().size());
			/*for(Iterator iterator = rootModel.getGenotype().get(0).getNGTColorPrefCount().iterator(); iterator.hasNext();){
				System.out.println("val :"+iterator.next());
			}*/
			colorCount = rootModel.getGenotype().get(0).getNGTColorPrefCount().size();
		}
		for(int i = 1; i <= colorCount; i++) {
			ColorFieldEditor cEditor1;
			ColorFieldEditor cEditor2;
			ColorFieldEditor cEditor3;
			if(i < 10){
				cEditor1= new ColorFieldEditor("a"+i, "Allele 0"+i+"              ", getFieldEditorParent());
			}else{
				cEditor1= new ColorFieldEditor("a"+i, "Allele "+i+"              ", getFieldEditorParent());
			}
			addField(cEditor1);
			if(i==colorCount){			
			
				cEditor2= new ColorFieldEditor("a50", "Missing                ", getFieldEditorParent());
				addField(cEditor2);
				
				cEditor3= new ColorFieldEditor("amono", "Monomorphic       ", getFieldEditorParent());
				addField(cEditor3);	
			}
			
		}
		
	}
	
	
	public void init(IWorkbench workbench) {
		
		
	}
}
