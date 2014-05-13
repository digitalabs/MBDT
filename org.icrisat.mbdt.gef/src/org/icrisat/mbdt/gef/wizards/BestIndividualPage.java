package org.icrisat.mbdt.gef.wizards;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

import javax.swing.text.html.HTMLDocument.HTMLReader.HiddenAction;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.icrisat.mbdt.gef.Activator;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.GenotypeModel.Genotype;
import org.icrisat.mbdt.model.sessions.Session;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MultiHashtable;

public class BestIndividualPage extends WizardPage implements ModifyListener{
	private Shell shell;
	private ShowHiddenElementsFilter filter;
	private Table table,table1;
	private TableViewer tViewer, tViewer1;
	private Text filterText;
	private Label lblText;
	List arrColumnEle = new ArrayList();
	private Label label1,label2,label3;
	private Text txt1;
	LinkedHashSet<String> test = new LinkedHashSet<String>();
	static int count= 0;
	int comind=0;
	RootModel rootModel1;
	LinkageData linkage;

	public BestIndividualPage(String pageName) {
		super(pageName);
		setTitle("Selection of Best Individuals");
		setImageDescriptor(Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/load.gif"));
		// TODO Auto-generated constructor stub
	}

	public void createControl(Composite parent) {
		// TODO Auto-generated method stub
		setPageComplete(false);
		Composite container = new Composite(parent, SWT.NONE);
		container.setBounds(250, 270, 100, 150);
		GridLayout gridLayout = new GridLayout(2, false);
		container.setLayout(gridLayout);
		RootModel rootModel = RootModel.getRootModel();
				
		try{
		
		table = new Table(container, SWT.BORDER | SWT.V_SCROLL | SWT.CHECK);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		data.horizontalSpan = 2;
	    data.verticalSpan = 2;
	    data.heightHint = 155;
		    
	    table.setLayoutData(data);
	    
	        
	    tViewer = new TableViewer(table);
	    tViewer.setContentProvider(new BestIndividualsContentProvider());
	    tViewer.setLabelProvider(new BestIndividualsLabelProvider());
	    
	    
	    if(rootModel.getLoadFlag() == null){
			rootModel1 = RootModel.getRootModel();
			linkage = LinkageData.getLinkageData();
		}else{
			rootModel1 = Session.getInstance().getRootmodel();
			linkage = rootModel1.getLinkData();
		}
	    	   
		
		Genotype geno = (Genotype) rootModel1.getGenotype().get(0);
		try{
			List<String> foregroundMarkers = linkage.getForegroundMarker();
			List<String> bestmarkers = linkage.getBestMarkers();
			HashMap monomarkers=new HashMap();
			monomarkers=rootModel1.getLinkagemap().get(0).getLimsMarkers().get(0).getMonomorphicMarkers();
			
			if(foregroundMarkers==null){
				foregroundMarkers=new ArrayList<String>();
			}
			
			if(bestmarkers==null){
				bestmarkers=new ArrayList<String>();
			}
				for(int i=0; i<foregroundMarkers.size(); i++){
				if(geno.getHeaderList().contains(foregroundMarkers.get(i))){
					try {
						if(!(monomarkers==null)){
							if(!(monomarkers.get(foregroundMarkers.get(i))).equals("Monomorphic")){
								if(!(bestmarkers.contains(foregroundMarkers.get(i)))){
									bestmarkers.add(foregroundMarkers.get(i));
									}
							}
						}else{
							if(!(bestmarkers.contains(foregroundMarkers.get(i)))){
								bestmarkers.add(foregroundMarkers.get(i));
								}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
					}
				}
				
				}
				linkage.setBestMarkers(bestmarkers);
				rootModel1.getLinkData().setBestMarkers(bestmarkers);
			}catch(Exception e){
			} 
	    tViewer.setInput(linkage.getBestMarkers());
	    
	    for(int i=0;i<BestIndividualPage.this.tViewer.getTable().getItemCount();i++){
	    	arrColumnEle.add(BestIndividualPage.this.tViewer.getTable().getItem(i));
	    }
	   
	    
	    table.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event event) {
				for (int i = 0; i < arrColumnEle.size(); i++) {
		        	if(((TableItem) arrColumnEle.get(i)).getChecked() == true){
		        		test.add(((TableItem) arrColumnEle.get(i)).getText());
		        	}
		        }
				
				//-------------Start----------------
				List<Object> noselaccSubset = new ArrayList<Object>();
				List<String> selacc = new ArrayList<String>();
				List<Object> selaccSubset = new ArrayList<Object>();
				List<String> selmarker_wizard = new ArrayList<String>();
				List<String> lMarkers= new ArrayList<String>();
				List<String> markerPositions= new ArrayList<String>();
				List<String> markersList = new ArrayList<String>();
				
				HashMap<String, List<String>> bestindividuals;
				HashMap<String, List<String>> missingindividuals;
				HashMap<String, List<String>> flankingindividuals;
				HashMap flankmarkers;
				RootModel rModel = RootModel.getRootModel();;
				int missCount = 0;
				int flankCount = 0;
				if(rModel.getLoadFlag() == null){
					rModel = RootModel.getRootModel();
				}else{
					rModel = Session.getInstance().getRootmodel();
					
				}
			   try{
					 
					for (int i = 0; i < arrColumnEle.size(); i++) {
			        	//System.out.println(event.item+"===="+((TableItem) arrColumnEle.get(i)).getText()+"checking status:::======="+((TableItem) arrColumnEle.get(i)).getChecked());
			        	if(((TableItem) arrColumnEle.get(i)).getChecked() == true){
			        		selmarker_wizard.add(((TableItem) arrColumnEle.get(i)).getText());
			        	}
			        }
					
					if(selmarker_wizard.size()!=0){
					bestindividuals = rModel.getLinkData().getBestindividual();
					if(bestindividuals==null){
						bestindividuals=new HashMap<String, List<String>>();
					}
					missingindividuals = rModel.getLinkData().getMissingindividual();
					if(missingindividuals==null){
						missingindividuals=new HashMap<String, List<String>>();
					}
					
					flankingindividuals = rModel.getLinkData().getFlankingindividual();
					if(flankingindividuals==null){
						flankingindividuals=new HashMap<String, List<String>>();
					}
					flankmarkers = rModel.getLinkData().getFlankmarkers();
					if(flankmarkers == null){
						flankmarkers = new HashMap();
					}
					for(int i=0;i<selmarker_wizard.size();i++){
						String marker = selmarker_wizard.get(i).substring(0, selmarker_wizard.get(i).indexOf(" "));
						try{
						for(int a=0; a < bestindividuals.get(marker).size(); a++){
							if(!(selacc.contains(bestindividuals.get(marker).get(a))))
							selacc.add(bestindividuals.get(marker).get(a));
						}
						if(linkage.isMissing()==true){
							
							for(int a=0; a < missingindividuals.get(marker).size(); a++){
								if(!(selacc.contains(missingindividuals.get(marker).get(a))))
								selacc.add(missingindividuals.get(marker).get(a));
							}
						}
												
						}catch(Exception e){
						}
					}
					List<String> commonind = new ArrayList<String>();
					List<String> diffind = new ArrayList<String>();
					List common [] = new List [ selmarker_wizard.size()];
					List miss[] = new List [ selmarker_wizard.size()];
					List flank   [] = new List [ selmarker_wizard.size()];
					bestindividuals.get(selmarker_wizard.get(0).substring(0, selmarker_wizard.get(0).indexOf(" "))).size();
					common[0]=bestindividuals.get(selmarker_wizard.get(0).substring(0, selmarker_wizard.get(0).indexOf(" ")));
					
					if(linkage.isMissing()==true){
						missCount = 0;
						miss[0] = new ArrayList();
						try {
							for(int a=0; a < missingindividuals.get(selmarker_wizard.get(0).substring(0, selmarker_wizard.get(0).indexOf(" "))).size(); a++){
								if(!(common[0].contains(missingindividuals.get(selmarker_wizard.get(0).substring(0, selmarker_wizard.get(0).indexOf(" "))).get(a))))
									miss[0].add(missingindividuals.get(selmarker_wizard.get(0).substring(0, selmarker_wizard.get(0).indexOf(" "))).get(a));
							}
						} catch (Exception e) {
						}
					}
					if(linkage.isFlanking()==true){
						flankCount = 0;
						flank[0] = new ArrayList();
						String marker = selmarker_wizard.get(0).substring(0, selmarker_wizard.get(0).indexOf(" "));
						try {
							if(flankmarkers.containsKey(marker)){
								String lflank = flankmarkers.get(marker).toString().substring(0, flankmarkers.get(marker).toString().indexOf("!@!"));
								String rflank = flankmarkers.get(marker).toString().substring(flankmarkers.get(marker).toString().indexOf("!@!")+3,flankmarkers.get(marker).toString().length());
								
							try {
								for(int a=0; a < flankingindividuals.get(lflank).size(); a++){
									if(!flank[0].contains(flankingindividuals.get(lflank).get(a)))
										flank[0].add(flankingindividuals.get(lflank).get(a));
								
									
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
							}
							for(int a=0; a < flankingindividuals.get(rflank).size(); a++){
								if((!flank[0].contains(flankingindividuals.get(rflank).get(a))))
									flank[0].add(flankingindividuals.get(rflank).get(a));
							}
						
							}
							
						} catch (Exception e) {
						}
					}
					for(int c=1;c < selmarker_wizard.size(); c++){
						String marker = selmarker_wizard.get(c).substring(0, selmarker_wizard.get(c).indexOf(" "));
						
						 common[c]= bestindividuals.get(marker) ;
						 if(common[c]==null){
							 common[c]= new ArrayList();
						 }
						 
						 if(linkage.isMissing()==true){
							 miss[c] = new ArrayList();
								try {
									for(int a=0; a < missingindividuals.get(marker).size(); a++){
										if(!(common[c].contains(missingindividuals.get(marker).get(a))))
											miss[c].add(missingindividuals.get(marker).get(a));
									}
								} catch (Exception e) {
								}
							}
						 if(linkage.isFlanking()==true){
								flank[c] = new ArrayList();
								try {
									if(flankmarkers.containsKey(marker)){
										String lflank = flankmarkers.get(marker).toString().substring(0, flankmarkers.get(marker).toString().indexOf("!@!"));
										String rflank = flankmarkers.get(marker).toString().substring(flankmarkers.get(marker).toString().indexOf("!@!")+3,flankmarkers.get(marker).toString().length());
										
									try {
										for(int a=0; a < flankingindividuals.get(lflank).size(); a++){
											if((!flank[c].contains(flankingindividuals.get(lflank).get(a))))
												flank[c].add(flankingindividuals.get(lflank).get(a));
										
											
										}
									} catch (Exception e) {
										// TODO Auto-generated catch block
									}
									for(int a=0; a < flankingindividuals.get(rflank).size(); a++){
										if((!flank[c].contains(flankingindividuals.get(rflank).get(a))))
											flank[c].add(flankingindividuals.get(rflank).get(a));
																}
									
									}
									
								} catch (Exception e) {
								}
							}
						 
						if(!(common[c]==null)){
							
						
						int com= 0; 
						if(c==1){
							for (int d=0; d<common[c-1].size();d++) { 
								if (common[c].contains(common[c-1].get(d))) {
									com++;
									if(!(commonind.contains((String)common[c-1].get(d)))){
										commonind.add((String)common[c-1].get(d));
									}
								}
								if(linkage.isMissing()==true){
									if(miss[c].contains(common[c-1].get(d))){
										if(!(commonind.contains((String)common[c-1].get(d)))){
											commonind.add((String)common[c-1].get(d));
										}
									}
									}
								/*if(linkage.isFlanking()==true){
									
									try {
										
										commonind = (ArrayList)Subtract(flank[c],commonind);
									} catch (Exception e) {
									}
									
								}*/
								
							}
							if(linkage.isMissing()==true){
								for (int d=0; d<miss[c-1].size();d++) { 
									if (common[c].contains(miss[c-1].get(d))) {
										com++;
										if(!(commonind.contains((String)miss[c-1].get(d)))){
											commonind.add((String)miss[c-1].get(d));
										}
									}
									if(miss[c].contains(miss[c-1].get(d))){
										if(!(commonind.contains((String)miss[c-1].get(d)))){
											commonind.add((String)miss[c-1].get(d));
										}
									}
								}
								}
							/*if(linkage.isFlanking()==true){
								commonind = (ArrayList)Subtract(flank[c],commonind);
								}*/
						}else{
							for (int d=0; d<commonind.size();d++) { 
								   if (!(common[c].contains(commonind.get(d)))) {
									   diffind.add(commonind.get(d));
									 
								   }
								   if(linkage.isMissing()==true){
										if(miss[c].contains(commonind.get(d))){
											diffind.remove(commonind.get(d));
										}
								   }
							}
							for(int diff=0; diff<diffind.size();diff++){
								commonind.remove(diffind.get(diff));
							}
						}
						}
					}
					 comind = commonind.size();
			   if(selmarker_wizard.size()==1){
				   comind = bestindividuals.get(selmarker_wizard.get(0).substring(0, selmarker_wizard.get(0).indexOf(" "))).size();
				   if(linkage.isMissing()==true){
					   try {
						   comind =  comind  +(Integer) rModel.getLinkData().getMissingCount().get(selmarker_wizard.get(0).substring(0, selmarker_wizard.get(0).indexOf(" ")));
					} catch (Exception e) {
					}
				   }
				   
			   }
			   /*if((comind==0)&&(linkage.isFlanking()==true)){
				   MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Information", " No Flanking Markers are detected");
				   BestIndividualPage.this.tViewer1.getTable().getItem(1).setChecked(false);
				   linkage.setFlanking(false);
				   for(int i=0;i<BestIndividualPage.this.tViewer.getTable().getItemCount();i++){
					   BestIndividualPage.this.tViewer.getTable().getItem(i).setChecked(false);
				   }
			   }*/
			   setPageComplete(true);		   
		
			   label1.setText("Common Individuals are       :   "+comind);
					}
					if(selmarker_wizard.size()==0){
						   comind = 0;	
						   label1.setText("Common Individuals are       :   "+comind);
						  				 
						   setPageComplete(false);
					   }
					   txt1.setText(Integer.toString(comind));
					   linkage.setBcount(comind);
			   }catch(Exception e){
			   }
			   
			   //-------------end---------			  		 
			}
		});

	    MultiHashtable mhm = new MultiHashtable();
	   Table table1 = new Table(container, SWT.CHECK);
	   GridData data1 = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
	   data1.horizontalSpan = 2;
	   data1.heightHint = 36;
	   data1.minimumHeight = 5;
	   tViewer1 = new TableViewer(table1);
	    tViewer1.setContentProvider(new BestIndividualsContentProvider1());
	    tViewer1.setLabelProvider(new BestIndividualsLabelProvider1());
	   List test = new ArrayList();
	   test.add("missing");
	   test.add("Flanking");
	    tViewer1.setInput(test);
	   table1.setBackground(new Color(null,236,233,216)); 
	   table1.setLayoutData(data1);
	  
	   
	    table1.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event event) {
		        	try {
						if(BestIndividualPage.this.tViewer1.getTable().getItem(0).getChecked() == true){
							 linkage.setMissing(true);
							tViewer.setInput(linkage.getBestMarkers());
							
						}else{
							linkage.setMissing(false);
							tViewer.setInput(linkage.getBestMarkers());
						}
						if(BestIndividualPage.this.tViewer1.getTable().getItem(1).getChecked() == true){
							 linkage.setFlanking(true);
							tViewer.setInput(linkage.getBestMarkers());
							
						}else{
							linkage.setFlanking(false);
							tViewer.setInput(linkage.getBestMarkers());
						}
					} catch (Exception e) {
					}		
			}
		});
	    container.setBackground(new Color(null,236,233,216));
	   
		   	label1 = new Label(container,SWT.NONE);
			label1.setText("Common Individuals are       :   "+comind);
			GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
			gridData.horizontalSpan = 2;
			label1.setLayoutData(gridData);
			label1.setBackground(new Color(null,236,233,216));
			label2 = new Label(container,SWT.NONE);
			label2.setText("Number of Best Individuals  : ");
			label2.setBackground(new Color(null,236,233,216));
			txt1 = new Text(container,SWT.BORDER);
			txt1.setText(Integer.toString(comind));
			txt1.addModifyListener(this);
			txt1.setTextLimit(20);
			txt1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			
			  linkage.setBcount(comind);
		  // tViewer.addFilter(filter);
		}catch(Exception e){
		}
		
	   setControl(container);
	
	}

	public String getSelectedMarkers(){
		String returnString = "";
		for (int i = 0; i < arrColumnEle.size(); i++) {
        	//System.out.println(event.item+"===="+((TableItem) arrColumnEle.get(i)).getText()+"checking status:::======="+((TableItem) arrColumnEle.get(i)).getChecked());
        	if(((TableItem) arrColumnEle.get(i)).getChecked() == true){
//        		returnString = returnString + "!@!" + ((TableItem) arrColumnEle.get(i)).getText();
        		returnString = returnString+((TableItem) arrColumnEle.get(i)).getText()+"@!@";
        	}
        }
		
		return returnString;
	}

	
	public void modifyText(ModifyEvent e) {
		// TODO Auto-generated method stub
		int count = Integer.parseInt(txt1.getText());
		linkage.setBcount(count);
		if(comind<count){
			MessageDialog.openInformation(shell, "Alert", "Number of Best Individuals should be lessthan common indivuals");
			count = comind;
			txt1.setText(Integer.toString(count));
		}
		
	}
	public static Collection Subtract(Collection coll1, Collection coll2)
	{
	    Collection result = new ArrayList(coll2);
	    result.retainAll(coll1);
	    return result;
	}
}



