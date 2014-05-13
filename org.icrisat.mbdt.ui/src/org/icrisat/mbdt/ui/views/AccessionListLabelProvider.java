package org.icrisat.mbdt.ui.views;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.icrisat.mbdt.model.GenotypeModel.Accessions;
import org.icrisat.mbdt.ui.Activator;

public class AccessionListLabelProvider extends LabelProvider {
	public Image test;
	private HashMap imageCache = new HashMap();
	public String getText(Object element) {
		//System.out.println("Accession element :"+element);
		return ((Accessions)element).getName();
		
	}
	
	public Image getImage(Object element) {
		
		ImageDescriptor test1 = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/ss9.gif");
		/*if(test1 != null){
			test = test1.createImage();
		}else{
			test = null;
		}*/
		//System.out.println("test ::: :"+test);
		//obtain the cached image corresponding to the descriptor
		   Image image = (Image)imageCache.get(test1);
		   if (image == null) {
		       image = test1.createImage();
		       imageCache.put(test1, image);
		   }
		   
		   return image;

//		return Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/ss9.gif").createImage();
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
//		Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/ss9.gif").createImage().dispose();
		//test.dispose();
		//test = null;
		 for (Iterator i = imageCache.values().iterator(); i.hasNext();) {
		       ((Image) i.next()).dispose();
		  }
		   imageCache.clear();

	}
	
}
