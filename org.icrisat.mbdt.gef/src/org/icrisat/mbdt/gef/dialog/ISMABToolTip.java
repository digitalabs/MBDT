package org.icrisat.mbdt.gef.dialog;

import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;

public class ISMABToolTip extends ToolTip{

	public ISMABToolTip(Control control) {
		super(control);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Composite createToolTipContentArea(Event event, Composite parent) {
		// TODO Auto-generated method stub
		return null;
	}
	 @Override
	public void setHideDelay(int hideDelay) {
		// TODO Auto-generated method stub
		super.setHideDelay(hideDelay);
	}

}
