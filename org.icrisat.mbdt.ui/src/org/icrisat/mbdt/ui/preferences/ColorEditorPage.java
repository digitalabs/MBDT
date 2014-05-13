package org.icrisat.mbdt.ui.preferences;

import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.ScaleFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.icrisat.mbdt.ui.Activator;

public class ColorEditorPage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	public ColorEditorPage() {
		this.setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	public ColorEditorPage(int style) {
		super(style);	
	}

	public ColorEditorPage(String title, int style) {
		super(title, style);
	}

	public ColorEditorPage(String title, ImageDescriptor image,
			int style) {
		super(title, image, style);
	}

	@Override
	protected void createFieldEditors() {
		// Add a radio group field
	    RadioGroupFieldEditor rfe = new RadioGroupFieldEditor("myRadioGroup",
	        "Radio Group", 2, new String[][] { { "First Value", "first" },
	            { "Second Value", "second" },
	            { "Third Value", "third" },
	            { "Fourth Value", "fourth" } }, getFieldEditorParent(),
	        true);
	    addField(rfe);
	 // Add a scale field
	    ScaleFieldEditor sfe = new ScaleFieldEditor("myScale", "Scale:",
	        getFieldEditorParent(), 0, 100, 1, 10);    addField(sfe);
	     // Add a string field
	        StringFieldEditor stringFe = new StringFieldEditor("myString",
	            "String:", getFieldEditorParent());
	        addField(stringFe);
   

		ColorFieldEditor cEditorA = new ColorFieldEditor("Abs1", "None         ", getFieldEditorParent());
		addField(cEditorA);
		String colorValue = Activator.getDefault().getPreferenceStore().getString("org.icrisat.mbdt.ui.preferences.ColorPreferencesInitializer");
	}

	public void init(IWorkbench workbench) {
	
	}
}
