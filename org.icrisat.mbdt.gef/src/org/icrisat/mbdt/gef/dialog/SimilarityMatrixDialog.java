package org.icrisat.mbdt.gef.dialog;

import java.text.Collator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.CommonModel.Qtl_MapData;
import org.icrisat.mbdt.model.sessions.Session;

public class SimilarityMatrixDialog extends Dialog implements SelectionListener  {
//	TableColumn column= null;
	TableItem item= null, item1= null;
	Vector<String> vdonor= new Vector<String>();
	Vector<String> rdonor= new Vector<String>();
	Vector<String> acc_with_alllabels= new Vector<String>();
	String sel_indR= null,ppD= null;
	List<String> selected_markers = new ArrayList<String>();
	List<String> selectedeles= new ArrayList<String>();
	String[][] matrix_accD= new String[1000][1000];
	String[][] matrix_accR= new String[1000][1000];
	int i=0, d=1, r=1, k=0, j1=0, t=0, t1=0, l1=0, t2=0, t3=0, p=0;
	int[][] count_similarity= new int[100][100];
//	int count = 0;
	int [][] count = new int[100][100];

	
	public SimilarityMatrixDialog(Shell shell) {
		
		super(shell);
		setShellStyle(getShellStyle() | SWT.MIN | SWT.MAX | SWT.RESIZE );
//		shell.getMaximized();
	}

	protected void configureShell(Shell newShell) {
		newShell.setText("Similarity Matrix");
		super.configureShell(newShell);
	}

	/*protected boolean isResizable() {
	    return true;
	}*/

	protected Control createDialogArea(Composite parent) {


		Composite composite = new Composite(parent, SWT.BORDER );	

		RootModel rootModel = RootModel.getRootModel();
		LinkageData linkage;
		RootModel rootModel1;
		
		try {
		if(rootModel.getLoadFlag() == null){
			rootModel1 = RootModel.getRootModel();
			linkage = LinkageData.getLinkageData();
		}else{
			rootModel1 = Session.getInstance().getRootmodel();
			linkage = rootModel.getLinkData();
		}
		for(int s = 0; s<rootModel1.getLinkagemap().get(0).getChromosomes().size();s++){
			if((rootModel1.getLinkagemap().get(0).getChromosomes().get(s).getChromosome().equals(linkage.getSelectedChromosome()))&&(!rootModel1.getLinkagemap().get(0).getLimsMarkers().get(s).getUnScreenedmarkers().contains(rootModel1.getLinkagemap().get(0).getLimsMarkers().get(s).getMarkers()))){
				selected_markers.add(rootModel1.getLinkagemap().get(0).getLimsMarkers().get(s).getMarkers());
			}
		}
		for(int n = 0; n < linkage.getAccallellic().size(); n++){
					
			acc_with_alllabels.add(linkage.getAccallellic().get(n));
			acc_with_alllabels.add(rootModel1.getAccAllele().get(n));
		}

		HashMap accWithLabels = linkage.getAccWithLabels();
		List<String> gh = new ArrayList<String>();
		gh.addAll(accWithLabels.keySet());

		List<String> gh1=new ArrayList<String>();
		gh1.addAll(accWithLabels.values());

		vdonor.add("Individuals");
		for(int i=0; i<gh1.size(); i++) {
			String sel_string= (String) gh.get(i);
			String hmcomponent= (String) gh1.get(i); 
				vdonor.add(sel_string);
				rdonor.add(sel_string);
		}

	final Table table= new Table(composite, SWT.BORDER  | SWT.H_SCROLL | SWT.V_SCROLL |SWT.FULL_SELECTION  |SWT.SCROLL_PAGE );
	  if(90*(vdonor.size())<1600){
		table.setSize(90*(vdonor.size()), 50*(vdonor.size()));
	  }else{
		table.setSize(1800, 900);
	  }
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		final int columsize=vdonor.size();
		for(int col=0; col<vdonor.size(); col++) {
			final int value=col;
			
		TableColumn column =	column 	= new TableColumn(table,SWT.NONE);
			column.setWidth(70);
			column.setText(vdonor.get(col).toString());
		    column.pack();
			column.addListener(SWT.Selection, new Listener() {
				
			
		        public void handleEvent(Event e) {
		        	
		        	
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
		 		        	
		          int temp=value;
		          TableItem[] items = table.getItems();
		          Collator collator = Collator.getInstance(Locale.getDefault());
		          
		          for (int i = 1; i <columsize; i++) {
		        	  if(currentColumn.getText().equals("Individuals")){
		        		  String value1 = items[i].getText(temp);
				            for (int j = 0; j < i; j++) {
				              String value2 = items[j].getText(temp);
				              
				              table.setSortDirection(dir);
				              
				              if (direction == SWT.UP) {
				              if (collator.compare(value1, value2) < 0) {
				            	  int size =columsize+1;
					                String[] values = new String[size];
					                
					                for (int k = 0; k <columsize; k++) {
					                	values[k]=items[i].getText(k);
									}
				                items[i].dispose();
				                TableItem item = new TableItem(table, SWT.NONE, j);
				                item.setText(values);
				                items = table.getItems();
				                break;
				              }
				              }else{
				            	  if (collator.compare(value1, value2) > 0) {
					            	  int size =columsize+1;
					            	  String[] values = new String[size];
						                
						                for (int k = 0; k <columsize; k++) {
						                	values[k]=items[i].getText(k);
										}
					                items[i].dispose();
					                TableItem item = new TableItem(table, SWT.NONE, j);
					                item.setText(values);
					                items = table.getItems();
					                break;
					              }
				              }
				            }
		        	  }else{
		        	  
		            String value1 = items[i].getText(temp).substring(0, items[i].getText(temp).length() - 1);
		            float value = Float.parseFloat(value1);
		            for (int j = 0; j < i; j++) {
		              String value2 = items[j].getText(temp).substring(0, items[j].getText(temp).length() - 1);
		              float valuet = Float.parseFloat(value2);
		              table.setSortDirection(dir);
		              
		              if (direction == SWT.UP) {
		              if (Float.compare(value, valuet) < 0) {
		            	  int size =columsize+1;
		            	  String[] values = new String[size];
			                
			                for (int k = 0; k <columsize; k++) {
			                	values[k]=items[i].getText(k);
							}
		                items[i].dispose();
		                TableItem item = new TableItem(table, SWT.NONE, j);
		                item.setText(values);
		                items = table.getItems();
		                break;
		              }
		              }else{
		            	  if (Float.compare(value, valuet) > 0) {
		            		  int size =columsize+1;
			            	  String[] values = new String[size];
				                
				                for (int k = 0; k <columsize; k++) {
				                	values[k]=items[i].getText(k);
								}
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
		        }		        
		      });
		}
		
		//Calculating the similarity and putting that in a 2d matrix
		/*for(int i = 0; i < gh.size(); i++) {
			String pp = (String) gh.get(i);
				for(int j=0; j<acc_with_alllabels.size(); j++) {
					if(gh.get(i).equals(acc_with_alllabels.elementAt(j))) {
						String pp1= (String) acc_with_alllabels.elementAt(j+1);
						matrix_accD[t1][j1] = pp1;
						j1++;
						t=j1;
					}
				} t1++;
			j1=0;
				for(int l = 0; l<acc_with_alllabels.size(); l++) {
					if(gh.get(i).equals(acc_with_alllabels.elementAt(l))) {
						String pp1= (String) acc_with_alllabels.elementAt(l+1);
						matrix_accR[t3][l1]= pp1;
						l1++;
						t2= l1;
					}
				}
				t3++;
			l1=0;
		}
		}*/
		if(linkage.isCview()==true){
			try {
				for(int i = 0; i < gh.size(); i++) {
					HashMap markerdata = (HashMap) linkage.getMarkerData().get(gh.get(i));
						for(int a = 0; a<rootModel.getLinkagemap().get(0).getChromosomes().size(); a++){
							if((markerdata.containsKey(rootModel.getLinkagemap().get(0).getChromosomes().get(a).getMap_marker().toString()))&&(linkage.getSelectedChromosome().equals(rootModel.getLinkagemap().get(0).getChromosomes().get(a).getChromosome()))){

								String pp1= (String) markerdata.get(linkage.getMarker().get(a));
								matrix_accD[t1][j1] = pp1;
								matrix_accR[t1][j1]= pp1;
								j1++;
								t2= j1;
							} 									
						}
					t1++;
					t3++;
					j1=0;
				}
			} catch (Exception e) {
			}	
		}else{
		try {
			for(int i = 0; i < gh.size(); i++) {
				HashMap markerdata = (HashMap) linkage.getMarkerData().get(gh.get(i));
					for(int a = 0; a<linkage.getMarker().size(); a++){
						if(markerdata.containsKey(linkage.getMarker().get(a))){

							String pp1= (String) markerdata.get(linkage.getMarker().get(a));
							matrix_accD[t1][j1] = pp1;
							matrix_accR[t1][j1]= pp1;
							j1++;
							t2= j1;
						} 									
					}
				t1++;
				t3++;
				j1=0;
			}
		} catch (Exception e) {
		}
		}
		for(i=0;i<t3;i++) {
			
			for(int k=0; k<t1;k++) {
				int noofmarkers =0;
				int missingmarkers=0;
				for(int j=0; j<t2;j++) {
					
					if(matrix_accD[k][j].equals(matrix_accR[i][j]) && (!(matrix_accD[k][j].equals("0 0") &&  matrix_accR[i][j].equals("0 0"))) ) {
						count_similarity[i][k]++;
					}
					if(!(matrix_accD[k][j].equals("0 0") &&  matrix_accR[i][j].equals("0 0")))
					{
					noofmarkers++;
					}
				}
				count[i][k]=noofmarkers;
			}
			
		}

		// Putting the percentage of similarity inside a table
		for(int i=0;i<t3;i++) {
			item1= new TableItem(table, SWT.NONE);	
			for(int j=0;j<t1+1;j++) {
				if(j==0) {
					item1.setText(j, rdonor.elementAt(i));
				}
				else {
					
					float percentage=(count_similarity[i][j-1] * 100) /count[i][j-1];
					String per= Float.toString(percentage);	
					item1.setText(j, per.substring(0, per.indexOf("."))+"%"); 
				}
			}
		}

		table.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				String pp = event.item+"";
				StringTokenizer st = new StringTokenizer(pp, "{");
				String pp1 = st.nextToken();
				String pp2 = st.nextToken();
				StringTokenizer st1 = new StringTokenizer(pp2,"}");
				sel_indR = (st1.nextToken()).substring(0,2);
			}
		});
	} catch (Exception e) {
	}
		return composite;
	
	}

	public void widgetDefaultSelected(SelectionEvent e) {
	}


	public void widgetSelected(SelectionEvent e) {
		TableColumn col= (TableColumn) e.widget;
		sel_indR= (col.getText()).substring(0,2);
	}


	



	protected void createButtonsForButtonBar(Composite parent) {
		
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,	true);
	}
}