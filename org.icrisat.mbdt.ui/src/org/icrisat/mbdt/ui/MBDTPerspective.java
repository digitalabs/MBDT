package org.icrisat.mbdt.ui;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class MBDTPerspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);
		
		IFolderLayout leftFolder = layout.createFolder("left", IPageLayout.LEFT, 0.25f, IPageLayout.ID_EDITOR_AREA);
		leftFolder.addView("org.icrisat.mbdt.ui.views.mbdtProjectExplorer");
		
		IFolderLayout rightFolder = layout.createFolder("right", IPageLayout.RIGHT, 0.75f, IPageLayout.ID_EDITOR_AREA);
		rightFolder.addPlaceholder("org.icrisat.mbdt.ui.views.LinkageMapView");
		rightFolder.addPlaceholder("org.icrisat.mbdt.ui.views.GenotypeView");
		
		
	}

}
