package org.icrisat.mbdt.lims.views;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.GenotypeModel.Accessions;
import org.icrisat.mbdt.model.sessions.Session;
import org.icrisat.mbdt.ui.views.AccessionListView;

public class CreatingLIMSSubSetView extends ViewPart implements ISelectionListener {
	
	private TableViewer tViewer;
	private Action exportGenoIDs;
	private Action exportPcrPlate96;
	private Action exportPcrPlate384;
	private Shell shell;
	List accessionList = new ArrayList<Accessions>();
	
	public CreatingLIMSSubSetView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		
		Table tbl = new Table(parent,SWT.FULL_SELECTION);
		tbl.setHeaderVisible(true);
		tbl.setLinesVisible(true);
		
		TableColumn tColumn = new TableColumn(tbl,SWT.NONE);
		tColumn.setWidth(100);
		tColumn.setText("Accessions");
		
		tViewer= new TableViewer(tbl);
		tViewer.setContentProvider(new CreatingLIMSSubSetContentProvider());
		tViewer.setLabelProvider(new CreatingLIMSSubSetLabelProvider());
				
		// Registering this class as a listener....
		this.getSite().getPage().addSelectionListener(AccessionListView.class.getName(), this);
		
		makeActions();
		fillMenuBar();
	}

	private void fillMenuBar() {
		MenuManager menuMgr = new MenuManager("Export");
		MenuManager submenuMgr1 = new MenuManager("LIMS PCR Plate");
		menuMgr.add(exportGenoIDs);
		
		//adding Submenu to main menu...
		menuMgr.add(submenuMgr1);
		submenuMgr1.add(exportPcrPlate96);
		submenuMgr1.add(exportPcrPlate384);
		
//		SubMenuManager submenuMgr = new SubMenuManager(menuMgr);
//		menuMgr.add(submenuMgr);
		
		this.getViewSite().getActionBars().getMenuManager().add(menuMgr);
//		this.getViewSite().getActionBars().getMenuManager().add(submenuMgr);
	}

	private void makeActions() {
		shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		//For GenotypeId's File.....
		exportGenoIDs = new Action("LIMS Genotype List",SWT.PUSH){
			
			public void run(){
				String fileName = null;
				List accessionsTofile = new ArrayList();
				
				FileDialog fileDialog = new FileDialog(Display.getDefault().getActiveShell(),SWT.SAVE);
				fileDialog.setFilterExtensions(new String[]{"*.xls","*.*"});
				/*SaveAsDialog saveasdialog = new SaveAsDialog(Display.getDefault().getActiveShell());
				saveasdialog.getResult();
				System.out.println("swapna nair");
				saveasdialog.open();*/
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
										Label label = new Label(0,0,"Genotype ID",writeCell);
										sheet.addCell(label);
										
										RootModel rootModel = Session.getInstance().getRootmodel();
										for(int i = 0; i < rootModel.getGenotype().get(0).getAccessions().size(); i++){
	//										System.out.println("SWAPNA NAIR   "+rootModel.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions1());
											if(rootModel.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions1().size() != 0){
												accessionsTofile = rootModel.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions1();
											}
										}
										for(int j = 0; j < accessionsTofile.size(); j++){
											label = new Label(0, j+1, (String)accessionsTofile.get(j), writeCell1);
											sheet.addCell(label);
										}								
										workbook.write();
										MessageDialog.openInformation(shell, "Information", "File Created!");
										workbook.close();
										} catch (WriteException e) {
										}
					        	  } catch (IOException e) {
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
									Label label = new Label(0,0,"Genotype ID",writeCell);
									sheet.addCell(label);
									
									RootModel rootModel = Session.getInstance().getRootmodel();
									for(int i = 0; i < rootModel.getGenotype().get(0).getAccessions().size(); i++){
	//									System.out.println("SWAPNA NAIR   "+rootModel.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions1());
										if(rootModel.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions1().size() != 0){
											accessionsTofile = rootModel.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions1();
										}
									}
									for(int j = 0; j < accessionsTofile.size(); j++){
										label = new Label(0, j+1, (String)accessionsTofile.get(j), writeCell1);
										sheet.addCell(label);
									}								
									workbook.write();
									MessageDialog.openInformation(shell, "Information", "File Created!");
									workbook.close();
									} catch (WriteException e) {
									}
				        	  } catch (IOException e) {
				        	  }
				        	  
				            done = true;
				           }
				      }
				 }
				super.run();
			}			
		};
		//For 96 Plate..........
		exportPcrPlate96 = new Action("LIMS PCRPlate (96)",SWT.NONE){
			
			@Override
			public void run() {
				
				if(getText().equals("LIMS PCRPlate (96)")){
					String fileName = null;
					List accessionsTofile = new ArrayList();
					
					RootModel rootModel = Session.getInstance().getRootmodel();
					for(int i = 0; i < rootModel.getGenotype().get(0).getAccessions().size(); i++){
						if(rootModel.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions1().size() != 0){
							accessionsTofile = rootModel.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions1();
						}
					}
					
					FileDialog fileDialog = new FileDialog(Display.getDefault().getActiveShell(),SWT.SAVE);
					fileDialog.setFilterExtensions(new String[]{"*.xls","*.*"});
					boolean done = false;
					
					//Checking whether No. of selected accessions is greater than 96.....
					if(accessionsTofile.size() < 96){
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
												WritableSheet sheet=workbook.createSheet("96_plate",0);												
												WritableFont writeFont = new WritableFont(WritableFont.TIMES,WritableFont.DEFAULT_POINT_SIZE, WritableFont.BOLD,false,UnderlineStyle.SINGLE,Colour.DARK_PURPLE);												
												try {
													WritableCellFormat writeCell = new WritableCellFormat(writeFont);										
													WritableCellFormat writeCell1 = new WritableCellFormat(writeFont);
													
													writeCell.setBackground(Colour.LIGHT_TURQUOISE);
													writeCell.setBorder(Border.ALL, BorderLineStyle.HAIR, Colour.AUTOMATIC);
													
													writeCell1.setBackground(Colour.IVORY);
													writeCell1.setBorder(Border.ALL, BorderLineStyle.HAIR, Colour.AUTOMATIC);
													
													Label label = new Label(0,1,"",writeCell);
													sheet.addCell(label);
													
													for(int i = 1; i < 13 ;i++){
														if((i == 1) || (i == 3) || (i == 5) || (i == 7) || (i == 9) || (i == 11)){
															label = new Label(i, 1, String.valueOf(i), writeCell1);												
														}else{
															label = new Label(i, 1, String.valueOf(i), writeCell);												
														}
														sheet.addCell(label);
													}
													
													int ch = 65;
													for(int j = 2; j < 10; j++){
														label = new Label(0, j, new Character((char)ch).toString(),writeCell);
														sheet.addCell(label);
														ch++;
													}
													int p = 1;
													for(int m = 1; m < 13; m++){
														for(int n = 2; n < 10; n++){
															if((m == 1) || (m == 3) || (m == 5) || (m == 7) || (m == 9) || (m == 11)){																
																if(p <= accessionsTofile.size()){
																	label = new Label(m, n, (String)accessionsTofile.get(p-1), writeCell1);
																}else{
																	label = new Label(m, n, "Blank", writeCell1);
																}																
															}else{																
																if(p <= accessionsTofile.size()){
																	label = new Label(m, n, (String)accessionsTofile.get(p-1), writeCell);
																}else{
																	label = new Label(m, n, "Blank", writeCell);
																}
															}
															sheet.addCell(label);												
															p++;
														}														
													}																
													workbook.write();
													MessageDialog.openInformation(shell, "Information", "File Created!");
													workbook.close();												
													} catch (WriteException e) {}													
								        	   } catch (IOException e) {}
						            	}
						            done = true;
//						            done = mb.open() == SWT.YES;
						          } else {
						        	 // File does not exist, so drop out
						        	 try {
										WritableWorkbook workbook = Workbook.createWorkbook(new File(fileName));
										WritableSheet sheet=workbook.createSheet("96_plate",0);
										
										WritableFont writeFont = new WritableFont(WritableFont.TIMES,WritableFont.DEFAULT_POINT_SIZE, WritableFont.BOLD,false,UnderlineStyle.SINGLE,Colour.DARK_PURPLE);
//										WritableFont writeFont1 = new WritableFont(WritableFont.createFont("verdana"),WritableFont.DEFAULT_POINT_SIZE, WritableFont.NO_BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK);
										
										
										try {
											WritableCellFormat writeCell = new WritableCellFormat(writeFont);										
											WritableCellFormat writeCell1 = new WritableCellFormat(writeFont);
											
											writeCell.setBackground(Colour.LIGHT_TURQUOISE);
											writeCell.setBorder(Border.ALL, BorderLineStyle.HAIR, Colour.AUTOMATIC);
											
											writeCell1.setBackground(Colour.IVORY);
											writeCell1.setBorder(Border.ALL, BorderLineStyle.HAIR, Colour.AUTOMATIC);
											
											Label label = new Label(0,1,"",writeCell);
											sheet.addCell(label);
											
											for(int i = 1; i < 13 ;i++){
												if((i == 1) || (i == 3) || (i == 5) || (i == 7) || (i == 9) || (i == 11)){
													label = new Label(i, 1, String.valueOf(i), writeCell1);												
												}else{
													label = new Label(i, 1, String.valueOf(i), writeCell);												
												}
												sheet.addCell(label);
											}
											
											int ch = 65;
											for(int j = 2; j < 10; j++){
												label = new Label(0, j, new Character((char)ch).toString(),writeCell);
												sheet.addCell(label);
												ch++;
											}
											/*
											RootModel rootModel = Session.getInstance().getRootmodel();
											for(int i = 0; i < rootModel.getGenotype().get(0).getAccessions().size(); i++){
												if(rootModel.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions1().size() != 0){
													accessionsTofile = rootModel.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions1();
												}
											}*/
	//										
											int p = 1;
											for(int m = 1; m < 13; m++){
												for(int n = 2; n < 10; n++){
	//												System.out.println("m = "+m+"  n = "+n+"  p  = "+p+" accessionsTofile.get(p)"+accessionsTofile.size());
													if((m == 1) || (m == 3) || (m == 5) || (m == 7) || (m == 9) || (m == 11)){
														
														if(p <= accessionsTofile.size()){
															label = new Label(m, n, (String)accessionsTofile.get(p-1), writeCell1);
														}else{
															label = new Label(m, n, "Blank", writeCell1);
														}
														
													}else{
														
														if(p <= accessionsTofile.size()){
															label = new Label(m, n, (String)accessionsTofile.get(p-1), writeCell);
														}else{
															label = new Label(m, n, "Blank", writeCell);
														}
													}
													sheet.addCell(label);												
													p++;
												}
												
											}
														
											workbook.write();
											MessageDialog.openInformation(shell, "Information", "File Created!");
											workbook.close();
										
											} catch (WriteException e) {
											}
											
						        	   } catch (IOException e) {
						        		   
						        	   }
						            done = true;
						          }
						      }
						 }
					}else{
						MessageDialog.openError(shell, "Error", "Number of Selected Accessions is greater than 96!");
					}
				}
				super.run();				
			}
		};
		//For 384 Plate..........
		exportPcrPlate384 = new Action("LIMS PCRPlate (384)",SWT.NONE){
			
			@Override
			public void run() {			
				if(getText().equals("LIMS PCRPlate (384)")){
					String fileName = null;
					List accessionsTofile = new ArrayList();
					
					RootModel rootModel = Session.getInstance().getRootmodel();
					for(int i = 0; i < rootModel.getGenotype().get(0).getAccessions().size(); i++){
						if(rootModel.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions1().size() != 0){
							accessionsTofile = rootModel.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions1();
						}
					}
					
					FileDialog fileDialog = new FileDialog(Display.getDefault().getActiveShell(),SWT.SAVE);
					fileDialog.setFilterExtensions(new String[]{"*.xls","*.*"});
					boolean done = false;
					
					//Checking whether No. of selected accessions is greater than 384.....
					if(accessionsTofile.size() < 384){
						 while (!done) {
							
							fileName =fileDialog.open();
							if (fileName == null) {
						        // User has cancelled, so quit and return.....
						        done = true;
						      } else {
						    	  File file = new File(fileName);
						          if (file.exists()) {
						            // The file already exists; asks for confirmation....
						            MessageBox mb = new MessageBox(fileDialog.getParent(), SWT.ICON_WARNING | SWT.YES | SWT.NO);
		
						            // We really should read this string from a resource bundle....
						            mb.setMessage(fileName + " already exists. Do you want to replace it?");
		
						            // If they click Yes, we're done and we drop out. If they click No, we redisplay the File Dialog...
						            if(mb.open() == SWT.YES){
						            	try {
											WritableWorkbook workbook = Workbook.createWorkbook(new File(fileName));
											WritableSheet sheet=workbook.createSheet("384_plate",0);
											WritableFont writeFont = new WritableFont(WritableFont.TIMES,WritableFont.DEFAULT_POINT_SIZE, WritableFont.BOLD,false,UnderlineStyle.SINGLE,Colour.DARK_PURPLE);
											try {
												WritableCellFormat writeCell = new WritableCellFormat(writeFont);										
												WritableCellFormat writeCell1 = new WritableCellFormat(writeFont);
												
												writeCell.setBackground(Colour.LIGHT_TURQUOISE);
												writeCell.setBorder(Border.ALL, BorderLineStyle.HAIR, Colour.AUTOMATIC);
												
												writeCell1.setBackground(Colour.IVORY);
												writeCell1.setBorder(Border.ALL, BorderLineStyle.HAIR, Colour.AUTOMATIC);
												
												Label label = new Label(0,1,"",writeCell);
												sheet.addCell(label);
												
												for(int i = 1; i < 25 ;i++){
													if((i == 1) || (i == 3) || (i == 5) || (i == 7) || (i == 9) || (i == 11) || (i == 13) || (i == 15) || (i == 17) || (i == 19) || (i == 21) || (i == 23)){
														label = new Label(i, 1, String.valueOf(i), writeCell1);												
													}else{
														label = new Label(i, 1, String.valueOf(i), writeCell);												
													}
													sheet.addCell(label);
												}
												
												int ch = 65;
												for(int j = 2; j < 18; j++){
													label = new Label(0, j, new Character((char)ch).toString(),writeCell);
													sheet.addCell(label);
													ch++;
												}
												int p = 1;
												for(int m = 1; m < 25; m++){
													for(int n = 2; n < 18; n++){
														if((m == 1) || (m == 3) || (m == 5) || (m == 7) || (m == 9) || (m == 11) || (m == 13) || (m == 15) || (m == 17) || (m == 19) || (m == 21) || (m == 23)){															
															if(p <= accessionsTofile.size()){
																label = new Label(m, n, (String)accessionsTofile.get(p-1), writeCell1);
															}else{
																label = new Label(m, n, "Blank", writeCell1);
															}															
														}else{															
															if(p <= accessionsTofile.size()){
																label = new Label(m, n, (String)accessionsTofile.get(p-1), writeCell);
															}else{
																label = new Label(m, n, "Blank", writeCell);
															}
														}
														sheet.addCell(label);												
														p++;
													}													
												}															
												workbook.write();
												MessageDialog.openInformation(shell, "Information", "File Created!");
												workbook.close();
												} catch (WriteException e) {}
							        	   } catch (IOException e) {}
						            }
						            done = true;
//						            done = mb.open() == SWT.YES;
						          } else {
						        	 // File does not exist, so drop out
						        	 try {
										WritableWorkbook workbook = Workbook.createWorkbook(new File(fileName));
										WritableSheet sheet=workbook.createSheet("384_plate",0);
										
										WritableFont writeFont = new WritableFont(WritableFont.TIMES,WritableFont.DEFAULT_POINT_SIZE, WritableFont.BOLD,false,UnderlineStyle.SINGLE,Colour.DARK_PURPLE);
//										WritableFont writeFont1 = new WritableFont(WritableFont.createFont("verdana"),WritableFont.DEFAULT_POINT_SIZE, WritableFont.NO_BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK);
										
										
										try {
											 
											WritableCellFormat writeCell = new WritableCellFormat(writeFont);										
											WritableCellFormat writeCell1 = new WritableCellFormat(writeFont);
											
											writeCell.setBackground(Colour.LIGHT_TURQUOISE);
											writeCell.setBorder(Border.ALL, BorderLineStyle.HAIR, Colour.AUTOMATIC);
											
											writeCell1.setBackground(Colour.IVORY);
											writeCell1.setBorder(Border.ALL, BorderLineStyle.HAIR, Colour.AUTOMATIC);
											
											Label label = new Label(0,1,"",writeCell);
											sheet.addCell(label);
											
											for(int i = 1; i < 25 ;i++){
												if((i == 1) || (i == 3) || (i == 5) || (i == 7) || (i == 9) || (i == 11) || (i == 13) || (i == 15) || (i == 17) || (i == 19) || (i == 21) || (i == 23)){
													label = new Label(i, 1, String.valueOf(i), writeCell1);												
												}else{
													label = new Label(i, 1, String.valueOf(i), writeCell);												
												}
												sheet.addCell(label);
											}
											
											int ch = 65;
											for(int j = 2; j < 18; j++){
												label = new Label(0, j, new Character((char)ch).toString(),writeCell);
												sheet.addCell(label);
												ch++;
											}
											
											/*RootModel rootModel = Session.getInstance().getRootmodel();
											for(int i = 0; i < rootModel.getGenotype().get(0).getAccessions().size(); i++){
												if(rootModel.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions1().size() != 0){
													accessionsTofile = rootModel.getGenotype().get(0).getAccessions().get(i).getSelectedAccessions1();
												}
											}*/
	//										
											int p = 1;
											for(int m = 1; m < 25; m++){
												for(int n = 2; n < 18; n++){
	//												System.out.println("m = "+m+"  n = "+n+"  p  = "+p+" accessionsTofile.get(p)"+accessionsTofile.size());
													if((m == 1) || (m == 3) || (m == 5) || (m == 7) || (m == 9) || (m == 11) || (m == 13) || (m == 15) || (m == 17) || (m == 19) || (m == 21) || (m == 23)){
														
														if(p <= accessionsTofile.size()){
															label = new Label(m, n, (String)accessionsTofile.get(p-1), writeCell1);
														}else{
															label = new Label(m, n, "Blank", writeCell1);
														}
														
													}else{
														
														if(p <= accessionsTofile.size()){
															label = new Label(m, n, (String)accessionsTofile.get(p-1), writeCell);
														}else{
															label = new Label(m, n, "Blank", writeCell);
														}
													}
													sheet.addCell(label);												
													p++;
												}
												
											}
														
											workbook.write();
											MessageDialog.openInformation(shell, "Information", "File Created!");
											workbook.close();
											} catch (WriteException e) {
											}
						        	   } catch (IOException e) {
						        	   }
						        	  
						            done = true;
						          }
						      }
						 }
					}else{
						MessageDialog.openError(shell, "Error", "Number of Selected Accessions is greater than 384!");
					}
				}
				super.run();
			}
		};		
	}

	@Override
	public void setFocus() {

	}
	public void setInput(Accessions accessions) {
//		System.out.println(accessions);		
		accessionList.add(accessions);
		tViewer.setInput(accessionList);
	}

	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		IStructuredSelection ss= (IStructuredSelection) selection;
		List selectionPoints= new ArrayList<Accessions>();
		for(Iterator iterator= ss.iterator(); iterator.hasNext();) {
			Accessions ap= (Accessions) iterator.next();
			selectionPoints.add(ap);
		}
		if(this.tViewer.getContentProvider()!=null)
		{
		tViewer.setInput(selectionPoints);
		}
	}
}
