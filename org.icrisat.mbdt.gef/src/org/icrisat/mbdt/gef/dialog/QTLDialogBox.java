package org.icrisat.mbdt.gef.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.sessions.Session;

public class QTLDialogBox extends Dialog  {


	String qtlStartPos = ""; String qtlEndPos = "";
	String qtlTraitname = ""; String qtlLodsqr = "";
	String qtladditiveEff = ""; String qtlRSqr = "";
	String qtlPeakPoint = "";

	public QTLDialogBox(Shell parentShell) {
		
		super(parentShell);
		// TODO Auto-generated constructor stub
	}
		
	protected void configureShell(Shell newShell) {
		newShell.setText("QTL Information");
		super.configureShell(newShell);
	}

	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent,SWT.NONE);
		container.setLayout(new GridLayout(2,true));
				
		
		RootModel rootModel = Session.getInstance().getRootmodel();
		for(int i = 0; i < rootModel.getQtl().size(); i++){			
			qtlStartPos = rootModel.getQtl().get(i).getDialogQtlStartPt();
			qtlEndPos = rootModel.getQtl().get(i).getDialogQtlEndPt();
			qtlTraitname = rootModel.getQtl().get(i).getDialogQtlTraitName();
			qtlLodsqr = rootModel.getQtl().get(i).getDialogQtlLodSqr();
			qtladditiveEff = rootModel.getQtl().get(i).getDialogQtlAddEffects();
			qtlRSqr = rootModel.getQtl().get(i).getDialogQtlRSqr();
			qtlPeakPoint = rootModel.getQtl().get(i).getDialogQtlPeakPt();
		}
		
		
		Label lbl = new Label(container,SWT.NONE);
		lbl.setText("Trait Name");
		lbl.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.BANNER_FONT));
		
		Label label = new Label(container,SWT.NONE);
		label.setText(qtlTraitname);		
		
		
		Label lbl1 = new Label(container,SWT.NONE);
		lbl1.setText("Start Point");
		lbl1.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.BANNER_FONT));
		
		Label label1 = new Label(container,SWT.NONE);
		label1.setText(qtlStartPos);
		
		
		Label lbl2 = new Label(container,SWT.NONE);
		lbl2.setText("End Point");
		lbl2.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.BANNER_FONT));
		
		Label label2 = new Label(container,SWT.NONE);
		label2.setText(qtlEndPos);
		
		Label lbl3 = new Label(container,SWT.NONE);
		lbl3.setText("Lod Score");
		lbl3.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.BANNER_FONT));
		
		Label label3 = new Label(container,SWT.NONE);
		label3.setText(qtlLodsqr);
		
		Label lbl4 = new Label(container,SWT.NONE);
		lbl4.setText("R2");
		lbl4.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.BANNER_FONT));
		
		Label label4 = new Label(container,SWT.NONE);
		label4.setText(qtlRSqr);
		
		Label lbl5 = new Label(container,SWT.NONE);
		lbl5.setText("Additive Effects");
		lbl5.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.BANNER_FONT));
//		label.setLayoutData(new GridData(100,15));
		
		Label label5 = new Label(container,SWT.NONE);
		label5.setText(qtladditiveEff);
		
		Label lbl6 = new Label(container, SWT.NONE);
		lbl6.setText("Peak Point");
		lbl6.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.BANNER_FONT));
		
		Label label6 = new Label(container,SWT.NONE);
		label6.setText(qtlPeakPoint);
		
		return container;
	}
	
	 /*
     *  Method declared on Dialog.
     */
	@Override
	protected void buttonPressed(int buttonId) {
		super.buttonPressed(buttonId);
	}
	

	/**
	 * Adds buttons to this dialog's button bar.
	 * The Dialog adds standard ok and cancel buttons using the <code>createButton</code>
	 * framework method. These standard buttons will be accessible from
	 * <code>getCancelButton</code>, and <code>getOKButton</code>.
	 * Subclasses may override.
	 * 
	 * Here we are overriding the createButtonsForButtonBar method in order to 
	 * hide default buttons and create our own button.
	 */
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,	true);
	}
	
}
