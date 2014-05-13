package org.icrisat.mbdt.gef.dialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Iterator;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
public class PopminLunchDialog extends Dialog  {

	private String value = "";
	private String value1 = "";
	private String value2 = "";
	private String value3 = "";
	String folderName = "";
	String subFolder = "";
	private   Text text;
	private   Text text1;
	private   Text text2;
	private   Text text3;
	private   String checklist="";
	private   String radiobox;
	private   String genotypeval;
	StringBuffer databat=new StringBuffer();
	
	   Button[] checkBoxs = new Button[3];
	   Button[] radios = new Button[3];
	
	public PopminLunchDialog(Shell parentShell) {
		super(parentShell);
		// TODO Auto-generated constructor stub
	}
	protected void configureShell(Shell newShell) {
		newShell.setText("POPMIN");
		super.configureShell(newShell);
	}

	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent,SWT.NONE);
		container.setLayout(new GridLayout(2,false));
				
		Label label = new Label(container,SWT.LEFT);
		label.setText("Specify risk of failure of the backcross program");
//		label.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));
		
		text = new Text(container, SWT.SINGLE);
		text.setTextLimit(8);
	    text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
	    
		
		//ISMAB_Project files path....
		folderName = Platform.getLocation().getDevice() ;
		 
		
		Label label1 = new Label(container,SWT.LEFT);
		label1.setText("Specify maximal duration of the backcross program ");
//		label1.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));
		
			
		text1 = new Text(container, SWT.SINGLE);
		text1.setTextLimit(8);
	    text1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
	   
		
	    Label label2 = new Label(container,SWT.LEFT);
		label2.setText("Specify distance of flanking markers from Target Geno d1 ");
//		label2.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));
		
			
		text2 = new Text(container, SWT.SINGLE);
		text2.setTextLimit(8);
	    text2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
	  
	    
	    Label label3 = new Label(container,SWT.LEFT);
		label3.setText("                                                                                        d2 ");
//		label3.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));
		
			
		text3 = new Text(container, SWT.SINGLE);
		text3.setTextLimit(8);
	    text3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
	    
	    
	    final Label initialgenotype = new Label(container,SWT.LEFT);
	    initialgenotype.setText("Specify initial genotype of M1-T-M2 to start recursion  ");
	    
     final   Combo  genotype = new Combo(container, SWT.NULL);
	    GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
//		gridData.horizontalSpan = 2;
		genotype.setLayoutData(gridData);
		genotype.add("R/R - D/R - D/R" );
		genotype.add("D/R - D/R - R/R");
		genotype.add("D/R - D/R - D/R" );
	    
		genotype.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
            	
//            	  genotypeval = genotype.getText();
            	  if(genotype.getText().equals("R/R - D/R - D/R"))
            		  genotypeval="I1";
            	  else if (genotype.getText().equals("D/R - D/R - R/R"))
            		  genotypeval="I2";
            	  else if (genotype.getText().equals("D/R - D/R - D/R"))	  
            		  genotypeval="I3";
            };
        });

	
	    
	    Label sampleSize = new Label(container,SWT.LEFT);
	    sampleSize.setText("Compute Sample sizes ");
		
		
		checkBoxs[0] = new Button(container, SWT.CHECK);
		checkBoxs[0].setSelection(true);
		checkBoxs[0].setText("Simulated Annealing");
		checkBoxs[0].setLocation(50,200);
		checkBoxs[0].pack();
	 
		checkBoxs[1] = new Button(container, SWT.CHECK);
		checkBoxs[1].setText("Exhaustive Search");
		checkBoxs[1].setLocation(120,200);
		checkBoxs[1].pack();
	 
		checkBoxs[2] = new Button(container, SWT.CHECK);
		checkBoxs[2].setText("Constant");
		checkBoxs[2].setLocation(190,200);
		checkBoxs[2].pack();
	 
		   
	
		
		
	    Label Printing = new Label(container,SWT.LEFT);
	    Printing.setText("Printing Options  ");
	   
	   
	 
	    radios[0] = new Button(container, SWT.RADIO);
	    radios[0].setSelection(true);
	    radios[0].setText("Detailed results");
	    radios[0].setBounds(10, 5, 75, 30);
	    radios[0].pack();
	    
	    radios[1] = new Button(container, SWT.RADIO);
	    radios[1].setText("Abbreviated results");
	    radios[1].setBounds(10, 30, 75, 30);

	    radios[2] = new Button(container, SWT.RADIO);
	    radios[2].setText("Verbose results");
	    radios[2].setBounds(10, 55, 75, 30);
	    
	    
	
		return container;
	}
	

		protected void okPressed() {
		
			if(text.getText() == ""){
			MessageDialog.openWarning(getShell(), "warning", " risk of failure of the backcross program is required!");
			setReturnCode(OK);
			open();
		}
		if(text1.getText() == ""){
			MessageDialog.openWarning(getShell(), "warning", " maximal duration of the backcross program is required!");
			setReturnCode(OK);
			open();
		}
		if(text2.getText() == ""){
			MessageDialog.openWarning(getShell(), "warning", " d1 is required!");
			setReturnCode(OK);
			open();
		}
		if(text3.getText() == ""){
			MessageDialog.openWarning(getShell(), "warning", " d2 is required!");
			setReturnCode(OK);
			open();
		}
		
			if(checkBoxs[0].getSelection())
				checklist=checklist+"s";
			if(checkBoxs[1].getSelection())
				checklist=checklist+"e";
			if(checkBoxs[2].getSelection())
				checklist=checklist+"c";			
						
		    if(radios[0].getSelection())
		    radiobox="d";
		    if (radios[1].getSelection())
		    radiobox ="a";	
		    if (radios[2].getSelection())	
		   	 radiobox ="v";	
		    
		   
		 value = text.getText();
         value1 = text1.getText();	
         value2 = text2.getText();
         value3 = text3.getText();
      
         databat=databat.append("popmin -").append(checklist).append(genotypeval).append("P0.5").append(radiobox).append(" "+ value +" "+value1+" "+value2+" "+value3);
         databat=databat.append("\n pause");
         databat=databat.append("\n exit");
         try {
        	 String batchFileName=Platform.getLocation().getDevice()+"\\popmin1.bat";
        	 
        		FileOutputStream stream = new FileOutputStream(batchFileName); 
        		PrintWriter writer = new PrintWriter(stream); 
        		writer.print(databat.toString());
        		writer.flush(); 
        		stream.close();
        		Runtime.getRuntime().exec(new String[]{"cmd","/c","start" , batchFileName});
        		
		} catch (Exception e) {
		}
     
         
         
         
		super.okPressed();
	}
	
	
}
