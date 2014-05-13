package org.icrisat.mbdt.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.icrisat.mbdt.ui.Activator;

public class ColorPreferencesInitializer extends AbstractPreferenceInitializer {

	public ColorPreferencesInitializer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initializeDefaultPreferences() {
		// TODO Auto-generated method stub
		Activator.getDefault().getPreferenceStore().setDefault("DIRECTORYPATH", "c:/temp");
	}
}
