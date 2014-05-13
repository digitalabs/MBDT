package org.icrisat.mbdt.gef.dialog;

import java.io.File;
import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Vector;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.TargetGenotype.MarkersSelectedParents;
import org.icrisat.mbdt.model.TargetGenotype.TargetGeno;
import org.icrisat.mbdt.model.sessions.Session;
import org.icrisat.mbdt.model.sessions.SessionTargetGenotype;


public class PercentageOfRecoveryDialog extends Dialog {
	static HashMap<String, List> allelval1 = new HashMap<String, List>();
	TableColumn column= null;
	TableItem item= null ;
	Vector<String> vdonor= new Vector<String>();
	Vector<String> rdonor= new Vector<String>();
//	final Table table;
	List<String> list;
	String[][] matrix_accD= new String[1000][1000];
	static int count1 =0,count2 =0;
	int t1=0, j1=0, temp=0;
	private Shell shell;
	public static List<String> TargetCharAllele= new ArrayList<String>();
	Vector<String> acc_with_alllabels= new Vector<String>();
	
	public PercentageOfRecoveryDialog(Shell parentShell) {
		super(parentShell);
		// TODO Auto-generated constructor stub
	}
	
	protected void configureShell(Shell newShell) {
		newShell.setText("Percentage of Recovery");
		super.configureShell(newShell);
	}
	@Override
	protected Control createDialogArea(Composite parent) {
		// TODO Auto-generated method stub
		
		Composite composite = new Composite(parent, SWT.BORDER);		
		RootModel rootModel = RootModel.getRootModel();
		LinkageData linkage;
		RootModel rootModel1;
		TargetGeno target;
		count1=0;
		count2=0;
		try {
		
			
		if(rootModel.getLoadFlag() == null){
			rootModel1 = RootModel.getRootModel();
			linkage = LinkageData.getLinkageData();
			
		}else{
			rootModel1 = Session.getInstance().getRootmodel();
			linkage = rootModel.getLinkData();
			}
		
		target = SessionTargetGenotype.getInstance().getTargetGeno();	 
		 
		 
		for(int i = 0; i < target.getParents().size();i++){
			if(target.getParents().get(i).getParent().contains("Target")){
					TargetCharAllele=target.getParents().get(i).getSelParents().get(0).getMParents().get(0).getColorAllele().get(0).getTargetCharAlleleValues();
			}
		}
		
		HashMap accWithLabels = linkage.getAccWithLabels();
		List<String> gh = new ArrayList<String>();
		gh.addAll(accWithLabels.keySet());
		vdonor.add("Individuals");
		vdonor.add("% Rec");
		vdonor.add("% Rec mis");
		
		
		final Table table= new Table(composite, SWT.BORDER);
		
		table.setSize(450, 450);
		
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		
 
		TableColumn column1 = new TableColumn(table, SWT.NONE);
	    column1.setText("Individuals");
	    column1.setWidth(175);
	    column1.setResizable(true);
	    TableColumn column2 = new TableColumn(table, SWT.NONE);
	    column2.setText("% Rec");
	    column2.setWidth(175);
	    column2.setResizable(true);
	    TableColumn column3 = new TableColumn(table, SWT.NONE);
	    column3.setText("% Rec mis");
	    column3.setResizable(true);
	    column3.setWidth(250);
		
	    column1.pack();
	    column2.pack();
	    column3.pack();
	    
	    column1.addListener(SWT.Selection, new Listener() {
	        public void handleEvent(Event e) {
	          // sort column 1
	        	
	        	
	        	 TableColumn sortColumn = table.getSortColumn();
		         TableColumn currentColumn = (TableColumn) e.widget;
		         
		        	   int dir = table.getSortDirection();
		             
		        	   if (sortColumn == currentColumn) {
		                 dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
		               } 
		               else {
		                 table.setSortColumn(currentColumn);
		                 dir = SWT.DOWN;
		               }
		               
		              final int direction = dir;
			 		        	
	          TableItem[] items = table.getItems();
	        
	          
	          Collator collator = Collator.getInstance(Locale.getDefault());
	          for (int i = 1; i < items.length; i++) {
	            String value1 = items[i].getText(0);
	            for (int j = 0; j < i; j++) {
	              String value2 = items[j].getText(0);
	              if (direction == SWT.UP) {
	              if (collator.compare(value1, value2) < 0) {
	                String[] values = { items[i].getText(0),items[i].getText(1),items[i].getText(2)};
	                
	                items[i].dispose();
	                TableItem item = new TableItem(table, SWT.NONE, j);
	                item.setText(values);
	                items = table.getItems();
	                break;
	              }
	              }else{
	            	  if (collator.compare(value1, value2) > 0) {
	  	                String[] values = { items[i].getText(0),items[i].getText(1),items[i].getText(2)};
	  	                
	  	                items[i].dispose();
	  	                TableItem item = new TableItem(table, SWT.NONE, j);
	  	                item.setText(values);
	  	                items = table.getItems();
	  	                break;
	  	              }
	              }
	            }
	          }
	        }
	      });
	    column2.addListener(SWT.Selection, new Listener() {
	        public void handleEvent(Event e) {
	          // sort column 2
	        	TableColumn sortColumn = table.getSortColumn();
		         TableColumn currentColumn = (TableColumn) e.widget;
		         
		        	   int dir = table.getSortDirection();
		             
		        	   if (sortColumn == currentColumn) {
		                 dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
		               } 
		               else {
		                 table.setSortColumn(currentColumn);
		                 dir = SWT.DOWN;
		               }
		               
		              final int direction = dir;
		              
	          TableItem[] items = table.getItems();
	          for (int i = 1; i < items.length; i++) {
	            float value1 = Float.parseFloat(items[i].getText(1));
	            for (int j = 0; j < i; j++) {
	            	float value2 = Float.parseFloat(items[j].getText(1));
	            	 if (direction == SWT.UP) {
	              if (Float.compare(value1, value2) < 0) {
	                String[] values = { items[i].getText(0),
	                    items[i].getText(1),items[i].getText(2) };
	                items[i].dispose();
	                TableItem item = new TableItem(table, SWT.NONE, j);
	                item.setText(values);
	                items = table.getItems();
	                break;
	              }
	            }else{
	            	if (Float.compare(value1, value2) > 0) {
		                String[] values = { items[i].getText(0),
		                    items[i].getText(1),items[i].getText(2) };
		                items[i].dispose();
		                TableItem item = new TableItem(table, SWT.NONE, j);
		                item.setText(values);
		                items = table.getItems();
		                break;
		              } 
	              }
	            }
	          }
	        }
	      });
	    column3.addListener(SWT.Selection, new Listener() {
	        public void handleEvent(Event e) {
	          // sort column 2
	        	TableColumn sortColumn = table.getSortColumn();
		         TableColumn currentColumn = (TableColumn) e.widget;
		         
		        	   int dir = table.getSortDirection();
		             
		        	   if (sortColumn == currentColumn) {
		                 dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
		               } 
		               else {
		                 table.setSortColumn(currentColumn);
		                 dir = SWT.DOWN;
		               }
		               
		              final int direction = dir;
		              
	          TableItem[] items = table.getItems();
	          for (int i = 1; i < items.length; i++) {
	        	  float value1 = Float.parseFloat(items[i].getText(2).substring(0, items[i].getText(2).indexOf(" ")));
	            for (int j = 0; j < i; j++) {
	            	float value2 = Float.parseFloat(items[j].getText(2).substring(0, items[j].getText(2).indexOf(" ")));
	            	if (direction == SWT.UP) {
	            	if (Float.compare(value1, value2) < 0) {
	                String[] values = { items[i].getText(0),
	                    items[i].getText(1),items[i].getText(2) };
	                items[i].dispose();
	                TableItem item = new TableItem(table, SWT.NONE, j);
	                item.setText(values);
	                items = table.getItems();
	                break;
	              }
	            	}else{
	            		if (Float.compare(value1, value2) > 0) {
	    	                String[] values = { items[i].getText(0),
	    	                    items[i].getText(1),items[i].getText(2) };
	    	                items[i].dispose();
	    	                TableItem item = new TableItem(table, SWT.NONE, j);
	    	                item.setText(values);
	    	                items = table.getItems();
	    	                break;
	    	              }
	            	}
	            }
	          }
	        }
	      });
	
	    
	    
		for (int i = 0; i < TargetCharAllele.size(); i++) {
			if(TargetCharAllele.get(i).equals("A") || TargetCharAllele.get(i).equals("-"))
			{
				count1++;
			}
		}
	
	for(int n = 0; n < linkage.getAccallellic().size(); n++){
			
			acc_with_alllabels.add(linkage.getAccallellic().get(n));
			acc_with_alllabels.add(rootModel1.getAccAllele().get(n));
		}
	
	for(int i = 0; i < gh.size(); i++) {
				
			for(int j=0; j<acc_with_alllabels.size(); j++) {
					
			if(gh.get(i).equals(acc_with_alllabels.elementAt(j))) {
			String pp1= (String) acc_with_alllabels.elementAt(j+1);
			matrix_accD[t1][j1] = pp1;
			j1++;
				}
			}t1++;	j1=0;
			
	}
	
	
	for(int i=0; i<gh.size(); i++) {
			
			item= new TableItem(table, SWT.NONE);	
			int noofmarkers =0;
			for(int j=0;j<3;j++) {
				if(j==0) {
					String sel_string= (String) gh.get(i);
					item.setText(j, sel_string);
				}
				if(j==1) {
					
				try {
					for(int l=0;l<TargetCharAllele.size();l++){
					String alelval1 =matrix_accD[i][l];
					if(((alelval1.equals(TargetCharAllele.get(l))) && ( !TargetCharAllele.get(l).equals("B")))  || ( alelval1.equals("A") && TargetCharAllele.get(l).equals("-"))   ){
							if( !((alelval1.equals("-")) && ( TargetCharAllele.get(l).equals("-")) ))
							{
									noofmarkers++;
							}
						}
						}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
				}
				
				float percentage= (noofmarkers * 100) /count1;
				 float temp =(noofmarkers * 100);
				 float temp1 =count1;
				 
				 float temp2 =  (temp/temp1);
				 
				
					String per= Float.toString(Math.round(temp2));	
					item.setText(j,per );
				}
				if(j==2){
					count2=0;
					noofmarkers =0;
					int tmissing=0;
					int Genomissing=0;
				  for(int l=0;l<TargetCharAllele.size();l++){
					
					  try {
						  String alelval1 =matrix_accD[i][l];

						  if( (alelval1.equals(TargetCharAllele.get(l)))  && ( !TargetCharAllele.get(l).equals("B")) && ( !TargetCharAllele.get(l).equals("-"))     ){

							  noofmarkers++;
						  }

						  if(TargetCharAllele.get(l).equals("A")  && (!alelval1.equals("-") ))
						  {
							  count2++;
						  }
						  if(TargetCharAllele.get(l).equals("-"))
						  {
							  tmissing++;
						  }
						  if(alelval1.equals("-") )
						  {
							  Genomissing++;
						  }
					  } catch (Exception e1) {
						  // TODO Auto-generated catch block
					  }
			}
				  		try{
						float percentage=(noofmarkers * 100) /count2;
						
						 float temp =(noofmarkers * 100);
						 float temp1 =count2;
						 
						 float temp2 =  (temp/temp1);
						 
						 
						String info="   "+tmissing+" T  and "+Genomissing+" Geno are Missing ";
						String per= Float.toString(Math.round(temp2));	
						
						
						
						item.setText(j,per+info );
				  		}catch(Exception e){
				  			item.setText(j,"   "+tmissing+" T  and All Geno are Missing " );
				  		}
//					}
					
				}
			}
		}
	Composite composite1 = new Composite(parent, SWT.NONE);	
    GridLayout gridLayout = new GridLayout(1, false);
    composite1.setLayout(gridLayout);
	 Button export = new Button(composite1,SWT.PUSH);
	 export.setText("  Export  ");
	 export.addSelectionListener(new SelectionAdapter(){
		   @Override
		public void widgetSelected(SelectionEvent e) {
			   String fileName = null;
				List individualsTofile = new ArrayList();
				
				FileDialog fileDialog = new FileDialog(Display.getDefault().getActiveShell(),SWT.SAVE);
				fileDialog.setFilterExtensions(new String[]{"*.xls","*.*"});
				
				 boolean done = false;
				 while (!done) {
					
					fileName =fileDialog.open();
					if (fileName == null) {
				        // User has cancelled, so quit and return
				        done = true;
				      } else {
				    	  File file = new File(fileName);
				          if (file.exists()) {
				            // The file already exists; asks for confirmation
				            MessageBox mb = new MessageBox(fileDialog.getParent(), SWT.ICON_WARNING | SWT.YES | SWT.NO);

				            // We really should read this string from a
				            // resource bundle
				            mb.setMessage(fileName + " already exists. Do you want to replace it?");

				            // If they click Yes, we're done and we drop out. If
				            // they click No, we redisplay the File Dialog
				            if(mb.open() == SWT.YES){
				            	try {
									WritableWorkbook workbook = Workbook.createWorkbook(new File(fileName));
									WritableSheet sheet=workbook.createSheet("Sheet1",0);
									
									WritableFont writeFont = new WritableFont(WritableFont.ARIAL,WritableFont.DEFAULT_POINT_SIZE, WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK);
									WritableFont writeFont1 = new WritableFont(WritableFont.createFont("verdana"),WritableFont.DEFAULT_POINT_SIZE, WritableFont.NO_BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK);
									 
									WritableCellFormat writeCell = new WritableCellFormat(writeFont);
									WritableCellFormat writeCell1 = new WritableCellFormat(writeFont1);
									
									try {
										jxl.write.Label label = new jxl.write.Label(0,0,"Individuals",writeCell);
										sheet.addCell(label);
										for (int i1 = 0; i1 < table.getItems().length; i1++) {
						                		individualsTofile.add(table.getItem(i1).getText());
										}
										
										for(int j = 0; j < individualsTofile.size(); j++){
											label = new jxl.write.Label(0, j+1, (String)individualsTofile.get(j), writeCell1);
											sheet.addCell(label);
										}								
										workbook.write();
										shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
										MessageDialog.openInformation(shell, "Information", "File Created!");
										workbook.close();
										} catch (WriteException ex) {
										}
					        	  } catch (IOException ex) {
					        	  }
				              }
				            
				              done = true;
//				              done = mb.open() == SWT.YES;				            
				           } else {
				        	 // File does not exist, so drop out
				        	 try {
								WritableWorkbook workbook = Workbook.createWorkbook(new File(fileName));
								WritableSheet sheet=workbook.createSheet("Sheet1",0);
								
								WritableFont writeFont = new WritableFont(WritableFont.ARIAL,WritableFont.DEFAULT_POINT_SIZE, WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK);
								WritableFont writeFont1 = new WritableFont(WritableFont.createFont("verdana"),WritableFont.DEFAULT_POINT_SIZE, WritableFont.NO_BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK);
								 
								WritableCellFormat writeCell = new WritableCellFormat(writeFont);
								WritableCellFormat writeCell1 = new WritableCellFormat(writeFont1);
								try {
									jxl.write.Label label = new jxl.write.Label(0,0,"Individuals",writeCell);
									sheet.addCell(label);
									for (int i1 = 0; i1 < table.getItems().length; i1++) {
					                		individualsTofile.add(table.getItem(i1).getText());
									}
									
									for(int j = 0; j < individualsTofile.size(); j++){
										label = new jxl.write.Label(0, j+1, (String)individualsTofile.get(j), writeCell1);
										sheet.addCell(label);
									}								
									workbook.write();
									shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
									MessageDialog.openInformation(shell, "Information", "File Created!");
									workbook.close();
									} catch (WriteException ex) {
									}
								
				        	  } catch (IOException ex) {
				        	  }
				        	  
				            done = true;
				           }
				      }
				 }
			  	    
		}
		   
	   });
	
		}catch(Exception e){
		}
		return composite;
	}
protected void createButtonsForButtonBar(Composite parent) {
		
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,	true);
	}
}
