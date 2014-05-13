package org.icrisat.mbdt.ui.wizards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.Datavalidation.DataValidation;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class ValidationDialog extends Dialog     {
 
	TableItem item= null;
	public ValidationDialog(Shell shell) {
		super(shell);
	}

	protected void configureShell(Shell newShell) {
		newShell.setText("  Validation");
		super.configureShell(newShell);
	}

	protected Control createDialogArea(Composite parent) {
		Composite composite = new Composite(parent, SWT.BORDER);		
		RootModel rootModel = RootModel.getRootModel();
		List<String> validationrules = new ArrayList<String>();
		composite.setLayout(new FillLayout());
	
		try {
			    
			DataValidation validation =  DataValidation.getValidationData();
//			validationrules=rootModel.getDataValidation()
			 final Table table= new Table(composite,SWT.MULTI  | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
			 table.setSize(450,150);
	         final TableColumn column 	= new TableColumn(table,SWT.NONE);
			 column.setWidth(400);
			 for (int i = 0; i < validation.getGenoValidationrules().size(); i++) {
				 final TableItem item1 = new TableItem(table, SWT.NONE);	
				  item1.setText(validation.getGenoValidationrules().get(i) );
			}
			 
			 for (int i = 0; i < validation.getMapValidationrules().size(); i++) {
				 final TableItem item1 = new TableItem(table, SWT.NONE);	
				  item1.setText(validation.getMapValidationrules().get(i) );
			}
			 for (int i = 0; i < validation.getQtlValidationrules().size(); i++) {
				 final TableItem item1 = new TableItem(table, SWT.NONE);	
				  item1.setText(validation.getQtlValidationrules().get(i) );
			}
			 for (int i = 0; i < validation.getPhenoValidationrules().size(); i++) {
				 final TableItem item1 = new TableItem(table, SWT.NONE);	
				  item1.setText(validation.getPhenoValidationrules().get(i) );
			}
			
		}catch(Exception e)
		{
			
		}
//		
		return composite;
	
	}

	 

	 

	

	 

	 

 

 
	 
}