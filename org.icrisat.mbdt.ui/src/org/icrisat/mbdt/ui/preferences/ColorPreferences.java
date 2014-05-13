package org.icrisat.mbdt.ui.preferences;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class ColorPreferences extends PreferencePage implements
		IWorkbenchPreferencePage {

	public ColorPreferences() {
		// TODO Auto-generated constructor stub
	}

	public ColorPreferences(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public ColorPreferences(String title, ImageDescriptor image) {
		super(title, image);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite container= new Composite(parent, SWT.NONE);
		return parent;
	}

	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub

	}

}
