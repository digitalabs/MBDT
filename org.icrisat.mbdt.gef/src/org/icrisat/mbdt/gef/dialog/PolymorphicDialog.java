package org.icrisat.mbdt.gef.dialog;

import java.io.File;
import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
//import jxl.write.Label;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.icrisat.mbdt.gef.wizards.BestIndividualPage;
import org.icrisat.mbdt.gef.wizards.BestIndividualsContentProvider1;
import org.icrisat.mbdt.gef.wizards.BestIndividualsLabelProvider1;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.sessions.Session;

public class PolymorphicDialog extends Dialog implements ModifyListener{
	
		TableColumn column= null;
		TableItem item= null ;
		Vector<String> vdonor= new Vector<String>();
		private Label label1;
		private Text txt1;
		private TableViewer tViewer1;
		HashMap<String, Float> polymark = new HashMap<String, Float>();
		HashMap<String,HashMap> polymarkmap = new HashMap<String,HashMap>();
		HashMap<String,List> polymarklist = new HashMap<String,List>();
		RootModel rootModel = RootModel.getRootModel();
		LinkageData linkage;
		RootModel rootModel1;
		List<String> polymarkers = new ArrayList<String>();
		static Table table;
		private Shell shell;
		public PolymorphicDialog(Shell parentShell) {
			super(parentShell);
			// TODO Auto-generated constructor stub
		}
		
		protected void configureShell(Shell newShell) {
			newShell.setText("Polymorphic Markers");
			super.configureShell(newShell);
		}
		@Override
		protected Control createDialogArea(Composite parent) {
			// TODO Auto-generated method stub
			
			Composite composite = new Composite(parent, SWT.BORDER);		
			
			
				
			if(rootModel.getLoadFlag() == null){
				rootModel1 = RootModel.getRootModel();
				linkage = LinkageData.getLinkageData();
				
			}else{
				rootModel1 = Session.getInstance().getRootmodel();
				linkage = rootModel.getLinkData();
				}
			vdonor.add("Marker");
			vdonor.add("Chromosome");
			vdonor.add("Position");
			vdonor.add("Trait");
			table= new Table(composite, SWT.BORDER | SWT.V_SCROLL | SWT.CHECK);
			
			table.setSize(550, 350);
			
			table.setLinesVisible(true);
			table.setHeaderVisible(true);
			
	 
			TableColumn column1 = new TableColumn(table, SWT.NONE);
		    column1.setText("Marker");
		    column1.setWidth(175);
		    column1.setResizable(true);
		    TableColumn column2 = new TableColumn(table, SWT.NONE);
		    column2.setText("Chromosome");
		    column2.setWidth(175);
		    column2.setResizable(true);
		    TableColumn column3 = new TableColumn(table, SWT.NONE);
		    column3.setText("Position");
		    column3.setResizable(true);
		    column3.setWidth(100);
		    TableColumn column4 = new TableColumn(table, SWT.NONE);
		    column4.setText("Trait");
		    column4.setResizable(true);
		    column4.setWidth(100);
		    
		    
		    Table table1 = new Table(parent, SWT.CHECK);
			   GridData data1 = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
			   data1.horizontalSpan = 2;
			   data1.heightHint = 3;
			   data1.minimumHeight = 2;
			   tViewer1 = new TableViewer(table1);
			    tViewer1.setContentProvider(new TraitContentProvider());
			    tViewer1.setLabelProvider(new TraitLabelProvider());
			   List test = new ArrayList();
			   test.add("Trait");
			    tViewer1.setInput(test);
			   table1.setBackground(new Color(null,236,233,216)); 
			   table1.setLayoutData(data1);
			  
			   
			    table1.addListener (SWT.Selection, new Listener () {
					public void handleEvent (Event event) {
				        	try {
				        		callBinSize(txt1.getText());							
							} catch (Exception e) {
							}		
					}
				});
			    
			    
			    
		    Composite composite1 = new Composite(parent, SWT.NONE);	
		    GridLayout gridLayout = new GridLayout(5, false);
		    composite1.setLayout(gridLayout);		  
			label1 = new Label(composite1,SWT.NONE);
			label1.setText("Bin size       :   ");
			txt1 = new Text(composite1,SWT.BORDER);
//			txt1.setText("000");
			txt1.setSize(150, 12);
			txt1.addModifyListener(this);
//			txt1.setTextLimit(20);
//			txt1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			
			
//		    column1.pack();
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
		          Collator collator = Collator.getInstance(Locale.getDefault());
		          for (int i = 1; i < items.length; i++) {
		        	  String value1 = items[i].getText(1);
		            for (int j = 0; j < i; j++) {
		            	String value2 = items[j].getText(1);
		            	 if (direction == SWT.UP) {
		              if (collator.compare(value1, value2) < 0) {
		                String[] values = { items[i].getText(0),
		                    items[i].getText(1),items[i].getText(2) };
		                items[i].dispose();
		                TableItem item = new TableItem(table, SWT.NONE, j);
		                item.setText(values);
		                items = table.getItems();
		                break;
		              }
		            }else{
		            	if (collator.compare(value1, value2) > 0) {
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
		        	  float value1 = Float.parseFloat(items[i].getText(2));
		            for (int j = 0; j < i; j++) {
		            	float value2 = Float.parseFloat(items[j].getText(2));
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
		    
		    int acount = 0;
		    HashMap acc1 = new HashMap();
			   HashMap acc2 = new HashMap();
			   
			   List<String> markers = new ArrayList<String>();
		    for(int i=0; i<rootModel1.getGenotype().get(0).getAccessions().size(); i++){
				for(int j=0; j<rootModel1.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions().size(); j++){
					acount++;
					
					 if(rootModel1.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions().get(j).getSelacc().size()>2){
						 MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Information", " This option is available for any two selected accessions");
						 return null;
					 }else{
				
					for(int k = 0; k < rootModel1.getGenotype().get(0).getAccessions().get(i).getAllelicValues().size(); k++){
						 if(acount == 1){
						if(linkage.getDataType().equals("Diploid")){
							acc1.put(rootModel1.getGenotype().get(0).getAccessions().get(i).getGmark().get(k),rootModel1.getGenotype().get(0).getAccessions().get(i).getAlleValues().get(k)+" "+rootModel1.getGenotype().get(0).getAccessions().get(i).getAlleValues().get(k+1));
							k++;
							markers.add(rootModel1.getGenotype().get(0).getAccessions().get(i).getGmark().get(k));
						}else{
							acc1.put(rootModel1.getGenotype().get(0).getAccessions().get(i).getGmark().get(k),rootModel1.getGenotype().get(0).getAccessions().get(i).getAlleValues().get(k));
							markers.add(rootModel1.getGenotype().get(0).getAccessions().get(i).getGmark().get(k));
						}
						}else if(acount == 2){
							if(linkage.getDataType().equals("Diploid")){
								acc2.put(rootModel1.getGenotype().get(0).getAccessions().get(i).getGmark().get(k),rootModel1.getGenotype().get(0).getAccessions().get(i).getAlleValues().get(k)+" "+rootModel1.getGenotype().get(0).getAccessions().get(i).getAlleValues().get(k+1));
								k++;
							}else{
								acc2.put(rootModel1.getGenotype().get(0).getAccessions().get(i).getGmark().get(k),rootModel1.getGenotype().get(0).getAccessions().get(i).getAlleValues().get(k));
							}
						}
					}
				}
				}
		    }
		    for(int i = 0; i < markers.size(); i++){
		    	if(!acc1.get(markers.get(i)).equals(acc2.get(markers.get(i)))){
		    		polymarkers.add(markers.get(i));
		    	}
		    }
			for(int i = 0; i < rootModel1.getLinkagemap().get(0).getChromosomes().size(); i++){
				if(polymarkers.contains((String)rootModel1.getLinkagemap().get(0).getChromosomes().get(i).getMap_marker())){
					item= new TableItem(table, SWT.NONE);
					item.setText(0, rootModel1.getLinkagemap().get(0).getChromosomes().get(i).getMap_marker());
					item.setText(1, rootModel1.getLinkagemap().get(0).getChromosomes().get(i).getChromosome());
					item.setText(2, rootModel1.getLinkagemap().get(0).getChromosomes().get(i).getMarkerposition());
					try {
						float qtlStartPos = 0, qtlEndPos = 0;
						String trait="";
						float markerdist = Float.parseFloat(rootModel1.getLinkagemap().get(0).getChromosomes().get(i).getMarkerposition());
						for(int q = 0; q < rootModel.getQtl().size(); q++){ 
							qtlStartPos = Float.parseFloat(rootModel.getQtl().get(q).getQtlData().get(q).getQtlStartPt());
							qtlEndPos = Float.parseFloat(rootModel.getQtl().get(q).getQtlData().get(q).getQtlEndPt());
							if(((markerdist>=qtlStartPos)&&(markerdist<=qtlEndPos))&&(rootModel1.getLinkagemap().get(0).getChromosomes().get(i).getChromosome().equals(rootModel.getQtl().get(q).getQtlData().get(q).getQtlChromNames()))){
								if(trait.length()==0)
								trait= trait+rootModel.getQtl().get(q).getQtlData().get(q).getQtltraitName();
								else
									trait= trait+","+rootModel.getQtl().get(q).getQtlData().get(q).getQtltraitName();
								
							}
							
							
						}
						item.setText(3, trait);
					} catch (Exception e1) {
					}
					
				}
			}
			
			 
			
			 Button export = new Button(composite1,SWT.PUSH);
			 export.setText("  Export  ");
			 export.addSelectionListener(new SelectionAdapter(){
				   @Override
				public void widgetSelected(SelectionEvent e) {
					   String fileName = null;
						List markersTofile = new ArrayList();
						
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
												jxl.write.Label label = new jxl.write.Label(0,0,"Marker",writeCell);
												sheet.addCell(label);
												for (int i1 = 0; i1 < table.getItems().length; i1++) {
								                	if(table.getItems()[i1].getChecked()==true){
								                		markersTofile.add(table.getItem(i1).getText());
								                	}
												}
												
												for(int j = 0; j < markersTofile.size(); j++){
													label = new jxl.write.Label(0, j+1, (String)markersTofile.get(j), writeCell1);
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
//						              done = mb.open() == SWT.YES;				            
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
											jxl.write.Label label = new jxl.write.Label(0,0,"Marker",writeCell);
											sheet.addCell(label);
											for (int i1 = 0; i1 < table.getItems().length; i1++) {
							                	if(table.getItems()[i1].getChecked()==true){
							                		markersTofile.add(table.getItem(i1).getText());
							                	}
											}
											
											for(int j = 0; j < markersTofile.size(); j++){
												label = new jxl.write.Label(0, j+1, (String)markersTofile.get(j), writeCell1);
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
			
			 Button selectall = new Button(composite1,SWT.PUSH);
			 selectall.setText(" Select All ");
			 selectall.addSelectionListener(new SelectionAdapter(){
				   @Override
				public void widgetSelected(SelectionEvent e) {
					   
					   for (int i1 = 0; i1 < table.getItems().length; i1++) {
				                	table.getItems()[i1].setChecked(true);
			           }	    
				}
				   
			   });
			   
			 Button deselectall = new Button(composite1,SWT.PUSH);
			 deselectall.setText("DeSelect All");
			 deselectall.addSelectionListener(new SelectionAdapter(){
				   @Override
				public void widgetSelected(SelectionEvent e) {
					   
					   for (int i1 = 0; i1 < table.getItems().length; i1++) {
				                	table.getItems()[i1].setChecked(false);
			           }	    
				}
				   
			   });
			 
			
			 return composite;
			}
		
		public void callBinSize(String txt){
			int peak =0;
			try{
			 peak = Integer.parseInt(txt);
			}catch(Exception e){
			}
			for (int i1 = 0; i1 < table.getItems().length; i1++) {			            
            	table.getItems()[i1].setChecked(false);
            	if(PolymorphicDialog.this.tViewer1.getTable().getItem(0).getChecked() == true){
					
            	if(table.getItems()[i1].getText(3).length()!=0){
            		table.getItems()[i1].setChecked(true);
            	}
            	}
			}
			
			if(peak>0){
			for(int l = 0; l < linkage.getChromlist().size(); l++){
				polymark = new HashMap();
				for(int i = 0; i < rootModel1.getLinkagemap().get(0).getChromosomes().size(); i++){
					if(linkage.getChromlist().get(l).equals(rootModel1.getLinkagemap().get(0).getChromosomes().get(i).getChromosome())){
						if(polymarkers.contains((String)rootModel1.getLinkagemap().get(0).getChromosomes().get(i).getMap_marker())){
								polymark.put(rootModel1.getLinkagemap().get(0).getChromosomes().get(i).getMap_marker(),Float.parseFloat(rootModel1.getLinkagemap().get(0).getChromosomes().get(i).getMarkerposition()));
						}
					}
				}
				List map1 = new ArrayList();
				Map<String, Float> map = new HashMap<String, Float>();
	            map = polymark;
	            
	            List<Map.Entry<String, Float>> list = new Vector<Map.Entry<String, Float>>(map.entrySet());
	            java.util.Collections.sort(list, new Comparator<Map.Entry<String, Float>>(){

	               public int compare(Map.Entry<String, Float> entry, Map.Entry<String, Float> entry1){
	                  return (entry.getValue().equals(entry1.getValue()) ? 0 : (entry.getValue() >entry1.getValue() ? 1 : -1));
	               }
	            });
	            map1.clear();
	            for (Map.Entry<String, Float> entry: list){
	               map.put(entry.getKey(), entry.getValue());
	               map1.add(entry.getKey());
	            }
	            polymarklist.put(linkage.getChromlist().get(l), map1);
				polymarkmap.put(linkage.getChromlist().get(l), (HashMap)map);
			}
			
	        int cnt =0;
			for(int i = 0; i < linkage.getChromlist().size(); i++){
				for(float j = peak; j < Math.round(Float.parseFloat(linkage.getCumlativeDistance().get(i))) ; j=j+peak){
					List<Float> max = new ArrayList<Float>();
//					List<Float> min = new ArrayList<Float>();
					HashMap tableItems = new HashMap();
					try{
					for(int k = 0; k < polymarkmap.get(linkage.getChromlist().get(i)).size(); k++){
					if((Float)polymarkmap.get(linkage.getChromlist().get(i)).get(polymarklist.get(linkage.getChromlist().get(i)).get(k))>=j){
						max.add((Float)polymarkmap.get(linkage.getChromlist().get(i)).get(polymarklist.get(linkage.getChromlist().get(i)).get(k)));
						tableItems.put((Float)polymarkmap.get(linkage.getChromlist().get(i)).get(polymarklist.get(linkage.getChromlist().get(i)).get(k)), polymarklist.get(linkage.getChromlist().get(i)).get(k));
						j=(Float)polymarkmap.get(linkage.getChromlist().get(i)).get(polymarklist.get(linkage.getChromlist().get(i)).get(k))+peak;
					}
				}
					
					for (int i1 = 0; i1 < table.getItems().length; i1++) {
			            String value1 = table.getItems()[i1].getText(0);
			            for(int j1 = 0; j1 < max.size(); j1++){
			                if(tableItems.get(max.get(j1)).equals(value1)){
			                	table.getItems()[i1].setChecked(true);
			                	break;
			                }
			            }
					}
					}catch(Exception ex){
					}
			}
			}
		}	
		}

		
		public void modifyText(ModifyEvent e) {
			callBinSize(txt1.getText());
			
		}
		protected void createButtonsForButtonBar(Composite parent) {
			
			createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,	true);
		}
	}

