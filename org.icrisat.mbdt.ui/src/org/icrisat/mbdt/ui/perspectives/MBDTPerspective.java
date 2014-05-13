package org.icrisat.mbdt.ui.perspectives;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.icrisat.mbdt.gef.views.ChromosomeView;
import org.icrisat.mbdt.gef.views.GraphicalView;
import org.icrisat.mbdt.ui.views.AccessionListView;
import org.icrisat.mbdt.ui.views.SelectedAccessionsView;
import org.icrisat.mbdt.ui.views.UnScreenedMarkersView;

public class MBDTPerspective implements IPerspectiveFactory {
	
	public void createInitialLayout(IPageLayout layout) {
		String editorArea= layout.getEditorArea();
//		IFolderLayout leftFolder0 = layout.createFolder("left", IPageLayout.LEFT, 0.15f, IPageLayout.ID_EDITOR_AREA);
//		leftFolder0.addView("org.icrisat.mbdt.ui.views.MbdtProjectExplorer");
		
		IFolderLayout leftFolder= layout.createFolder("leftFolder", IPageLayout.LEFT, 0.18f, editorArea);
		leftFolder.addView(AccessionListView.class.getName());
		
		layout.getViewLayout(AccessionListView.class.getName()).setCloseable(false); 
		
		IFolderLayout bottomLeft= layout.createFolder("bottomLeft", IPageLayout.BOTTOM, 0.60f, "leftFolder");
		bottomLeft.addView(SelectedAccessionsView.class.getName());
		layout.getViewLayout(SelectedAccessionsView.class.getName()).setCloseable(false); 
		
		IFolderLayout topfolder= layout.createFolder("topfolder", IPageLayout.TOP, 0.60f, editorArea);
//		topfolder.addPlaceholder("org.icrisat.mbdt.ui.views.LinkageMapTableView"+":*");
		topfolder.addPlaceholder("org.icrisat.mbdt.ui.views.GenotypeDataView"+":*");
		topfolder.addPlaceholder("org.icrisat.mbdt.gef.views.GraphicalView"+":*");
		topfolder.addView("org.icrisat.mbdt.gef.views.GraphicalView");
		topfolder.addPlaceholder("org.icrisat.mbdt.gef.views.ChromosomeView");
		
		layout.getViewLayout("org.icrisat.mbdt.gef.views.GraphicalView").setCloseable(false); 
		layout.getViewLayout("org.icrisat.mbdt.gef.views.TargetGenotype").setCloseable(false); 

		IFolderLayout folderRight= layout.createFolder("FolderRight", IPageLayout.RIGHT, 0.90f, "topfolder");
		folderRight.addView(UnScreenedMarkersView.class.getName());
		
		layout.setEditorAreaVisible(false);	
		
		defineActions(layout);
	}
	
	public void defineActions(IPageLayout layout) {
        // Add "new wizards".
       // layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.folder");
        //layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.file");

        // Add "show views".
        layout.addShowViewShortcut("org.icrisat.mbdt.gef.views.GraphicalView");
        layout.addShowViewShortcut("org.icrisat.mbdt.gef.views.TargetGenotype");
        layout.addShowViewShortcut("org.icrisat.mbdt.ui.views.AccessionListView");
        layout.addShowViewShortcut("org.icrisat.mbdt.ui.views.SelectedAccessionsView");
        layout.addShowViewShortcut("org.icrisat.mbdt.ui.views.UnScreenedMarkersView");
	}
}