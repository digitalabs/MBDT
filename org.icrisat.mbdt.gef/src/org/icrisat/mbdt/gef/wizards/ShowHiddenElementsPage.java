package org.icrisat.mbdt.gef.wizards;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import javax.swing.text.html.HTMLDocument.HTMLReader.HiddenAction;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.icrisat.mbdt.gef.Activator;
import org.icrisat.mbdt.model.RootModel;
import org.icrisat.mbdt.model.CommonModel.LinkageData;
import org.icrisat.mbdt.model.sessions.Session;

public class ShowHiddenElementsPage extends WizardPage {

	private ShowHiddenElementsFilter filter;
	private Table table;
	private TableViewer tViewer;
	private Text filterText;
	private Label lblText;
	List arrColumnEle = new ArrayList();
	
	LinkedHashSet<String> test = new LinkedHashSet<String>();
	
	protected ShowHiddenElementsPage(String pageName) {
		super(pageName);
		setTitle("Show Hidden Elements");
		setImageDescriptor(Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/load.gif"));
		// TODO Auto-generated constructor stub
	}

	
	public void createControl(Composite parent) {
		// TODO Auto-generated method stub
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout(2, false);
		//gridLayout.verticalSpacing = 30;
		//container.setLayout(new GridLayout(2, true));
		container.setLayout(gridLayout);
		RootModel rootModel = RootModel.getRootModel();
		RootModel rootModel1;
		/*****************************start of working version*************************************/
		/*
		Label lblText = new Label(container, SWT.NONE);
		lblText.setText("Filter");
		lblText.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));
		final Text txt = new Text(container,SWT.BORDER);
		
		
		//container.addf
		txt.addModifyListener(new ModifyListener(){			

			@Override
			public void modifyText(ModifyEvent e) {
				// TODO Auto-generated method stub
				filter.setFilterText(txt.getText());
//				tViewer.refresh();
				
				
			}			
		});	
		filter = new ShowHiddenElementsFilter();
		
		
		
		
		
		final Table table = new Table(container, SWT.BORDER | SWT.V_SCROLL | SWT.CHECK);
//		Table table = new Table(container, SWT.CHECK);
		GridData data = new GridData(GridData.FILL_BOTH);
	    data.verticalSpan = 2;
	    data.heightHint = 35;
	    table.setLayoutData(data);
	    List test = new ArrayList();
	   final String[] test1;
	    	test.add("one");test.add("two");test.add("three");test.add("four");test.add("five");
	    	String test2 = "one;two;three;four;five;six;seven;eight;nine;ten;eleven;twelve;thirteen;fourteen;one;two;three;four;five;six;seven;eight;nine;ten;eleven;twelve;thirteen;";
	    	test1 = test2.split(";");
	    for (int i = 0; i < test1.length; i++) {
	        TableItem item = new TableItem(table, SWT.NONE);
	        item.setText(test1[i]);
	       // item.setChecked(true);
	     }
	    
	   Button button = new Button(container,SWT.PUSH);
	   button.setText("Select All");
	   button.addSelectionListener(new SelectionAdapter(){
		   @Override
		public void widgetSelected(SelectionEvent e) {
			// TODO Auto-generated method stub
			   TableItem[] items = table.getItems();
		        for (int i = 0; i < test1.length; i++) {
		          items[i].setChecked(true);
		        }
		}
		   
	   });
	   Button button1 = new Button(container,SWT.PUSH);
	   button1.setText("Deselct All");
	   button1.addSelectionListener(new SelectionAdapter(){
		   @Override
		public void widgetSelected(SelectionEvent e) {
			// TODO Auto-generated method stub
			   TableItem[] items = table.getItems();
		        for (int i = 0; i < test1.length; i++) {
		          items[i].setChecked(false);
		        }
		}
		   
	   });
	   */
		try{
		/*************************End Of working version*****************************/
			/*Label lbl = new Label(container, SWT.NONE);
			lbl.setText("Test");
			final Text txt = new Text(container,SWT.COLOR_GREEN);
			txt.setLayoutData(new GridData(GridData.FILL,GridData.CENTER,true,false));
			lblText = new Label(container, SWT.NONE);
			lblText.setText("Filter");
			lblText.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));
			filterText = new Text(container,SWT.BORDER);
//			filter = new ShowHiddenElementsFilter();
			filterText.addModifyListener(new ModifyListener(){			

			@Override
			public void modifyText(ModifyEvent e) {
				// TODO Auto-generated method stub
				filter.setFilterText(filterText.getText());
				tViewer.refresh();
				String msg = filter.getCount()+" matches found out of "+filter.getTotalcount();
				txt.setText(msg);
			}			
			});	

			
		filterText.setLayoutData(new GridData(GridData.FILL,GridData.CENTER,true,false));*/
		table = new Table(container, SWT.BORDER | SWT.V_SCROLL | SWT.CHECK);
		GridData data = new GridData(GridData.FILL_BOTH);
	    data.verticalSpan = 2;
	    data.heightHint = 55;
	    table.setLayoutData(data);
	    
	    /*TableColumn tColumn= new TableColumn(table, SWT.NONE);
		tColumn.setWidth(100);
		tColumn.setText("Name");*/
	    
	    tViewer = new TableViewer(table);
	    tViewer.setContentProvider(new ShowHiddenEleContentProvider());
	    tViewer.setLabelProvider(new ShowHiddenEleLabelProvider());
	    
	    //filter = new ShowHiddenElementsFilter();
	    
	    if(rootModel.getLoadFlag() == null){
			rootModel1 = RootModel.getRootModel();
		}else{
			rootModel1 = Session.getInstance().getRootmodel();
		}
	    
	    tViewer.setInput(rootModel1.getGenotype().get(0));
	    
	   
	    for(int i=0;i<ShowHiddenElementsPage.this.tViewer.getTable().getItemCount();i++){
	    	arrColumnEle.add(ShowHiddenElementsPage.this.tViewer.getTable().getItem(i));
	    	//ShowHiddenElementsPage.this.tViewer.getTable().getItem(i).addListener(this);
	    }
	    
	    
	    table.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event event) {
				for (int i = 0; i < arrColumnEle.size(); i++) {
		        	if(((TableItem) arrColumnEle.get(i)).getChecked() == true){
		        		//returnString = returnString + "!@!" + ((TableItem) arrColumnEle.get(i)).getText();
		        		test.add(((TableItem) arrColumnEle.get(i)).getText());
		        	}
		        }
			}
		});
	   
	    
	    Button button = new Button(container,SWT.PUSH);
		   button.setText("Select All");
		   button.addSelectionListener(new SelectionAdapter(){
			   @Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				   
				  // TableItem[] items = table.getItems();
			        for (int i = 0; i < arrColumnEle.size(); i++) {
			        	((TableItem) arrColumnEle.get(i)).setChecked(true);
			        }
				    
			}
			   
		   });
		   
		   Button button1 = new Button(container,SWT.PUSH);
		   button1.setText("Deselect All");
		   
		   button1.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				   for (int i = 0; i < arrColumnEle.size(); i++) {
			        	((TableItem) arrColumnEle.get(i)).setChecked(false);
			        }
			}
			   
		   });
		  // tViewer.addFilter(filter);
		}catch(Exception e){
		}
		
	   setControl(container);
	
	}

	public String getSelectedAccforUnHide(){
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
}
