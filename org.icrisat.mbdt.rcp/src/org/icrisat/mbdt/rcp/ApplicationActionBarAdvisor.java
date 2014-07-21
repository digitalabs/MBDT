package org.icrisat.mbdt.rcp;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ContributionItemFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.registry.ActionSetRegistry;
import org.eclipse.ui.internal.registry.IActionSetDescriptor;
import org.icrisat.mbdt.ui.actions.LoadAction;
import org.icrisat.mbdt.ui.actions.SaveAction;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	private IWorkbenchAction exitAction;
	private IWorkbenchAction importAction;
	private IWorkbenchAction helpAction;
	private IWorkbenchAction aboutAction;
	private IWorkbenchAction preferencesAction;
	private MenuManager  winMenuMgr ;
	private SaveAction saveAction;
	private LoadAction loadAction;
	
    private IContributionItem views;
	
    public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }

    protected void makeActions(IWorkbenchWindow window) {
//    	exitAction= ActionFactory.QUIT.create(window);
//    	importAction= ActionFactory.IMPORT.create(window);
    	helpAction= ActionFactory.HELP_CONTENTS.create(window);
    	aboutAction= ActionFactory.ABOUT.create(window);
    	preferencesAction= ActionFactory.PREFERENCES.create(window);
        views = ContributionItemFactory.VIEWS_SHORTLIST.create(window);
    	
    	//Save Action....
        /*saveAction = new SaveAction(window);
    	register(saveAction);
    	
    	loadAction = new LoadAction(window);
    	register(loadAction);*/
    	winMenuMgr = createWindowMenu(window);
    }

    protected void fillMenuBar(IMenuManager menuBar) {
    	MenuManager fileMenu= new MenuManager("File", IWorkbenchActionConstants.M_FILE);
//    	
//    	MenuManager dbMenu= new MenuManager("Database", IWorkbenchActionConstants.NEW_GROUP);
//    	fileMenu.add(new Group());
    	MenuManager windowMenu= new MenuManager("Windows", IWorkbenchActionConstants.M_WINDOW);
    	windowMenu.add(preferencesAction);
//    	windowMenu.add(views);
		  
    	MenuManager viewMenu= new MenuManager("View", IWorkbenchActionConstants.M_EDIT);
//    	editMenu.add();
    	
    	MenuManager helpMenu= new MenuManager("Help", IWorkbenchActionConstants.M_VIEW);
    	helpMenu.add(helpAction);
    	// Separator
    	helpMenu.add(aboutAction);
    	// Separator
//    	fileMenu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
    	//fileMenu.add(saveAction);
//    	fileMenu.add(loadAction);
//    	fileMenu.add(saveAction);
    	
    	//fileMenu.add(customAction);
//    	fileMenu.add(exitAction);
    	
    	menuBar.add(fileMenu);
    	menuBar.add(windowMenu);
    	MenuManager layoutMenu = new MenuManager("Switch Layout", "layout");
		menuBar.add(layoutMenu);
		menuBar.add(winMenuMgr);
		menuBar.update();
    	menuBar.add(helpMenu);
    	
    	removeExtraneousActions();
    	PreferenceManager pm = PlatformUI.getWorkbench( ).getPreferenceManager();
    	pm.remove("org.eclipse.ui.preferencePages.Workbench");
    	pm.remove("org.eclipse.update.internal.ui.preferences.MainPreferencePage");
    }

    private MenuManager createWindowMenu(IWorkbenchWindow window) {
		MenuManager menu = new MenuManager("View",IWorkbenchActionConstants.M_VIEW);

		menu.add(new Separator());
		
		MenuManager viewMenu = new MenuManager("Show View");
		IContributionItem viewList = ContributionItemFactory.VIEWS_SHORTLIST.create(window);
		viewMenu.add(viewList);
				
		menu.add(viewMenu);

		menu.update();
		return menu;
	}
	private void removeExtraneousActions() {
		// TODO Auto-generated method stub
		 ActionSetRegistry reg = WorkbenchPlugin.getDefault().getActionSetRegistry();

	

		 removeStandardAction(reg, "org.eclipse.ui.WorkingSetActionSet");
		 removeStandardAction(reg, "org.eclipse.update.ui.softwareUpdates");
		 removeStandardAction(reg, "org.eclipse.ui.actionSet.keyBindings");
		 removeStandardAction(reg, "org.eclipse.ui.actionSet.openFiles");
		 removeStandardAction(reg, "org.eclipse.ui.edit.text.actionSet.convertLineDelimitersTo");
		 removeStandardAction(reg, "org.eclipse.search");
	}
	 private void removeStandardAction(ActionSetRegistry reg, String actionSetId) {

         IActionSetDescriptor[] actionSets = reg.getActionSets();

          for (int i = 0; i < actionSets.length; i++) {
                 if (!actionSets[i].getId().equals(actionSetId)) {
                          continue;
                 }
           IExtension ext = actionSets[i].getConfigurationElement()
                                 .getDeclaringExtension();
                 reg.removeExtension(ext, new Object[] { actionSets[i] });
         }
 }

	
	 protected void buttonPressed(int buttonId) {
			switch (buttonId) {
			case IDialogConstants.OK_ID: {
				return;
			}
			
			}
	 }
}