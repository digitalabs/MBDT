package org.icrisat.mbdt.ui.wizards;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.generationcp.middleware.domain.etl.StudyDetails;
import org.generationcp.middleware.domain.oms.StudyType;
import org.generationcp.middleware.exceptions.MiddlewareQueryException;
import org.generationcp.middleware.manager.Database;
import org.generationcp.middleware.manager.DatabaseConnectionParameters;
import org.generationcp.middleware.manager.ManagerFactory;
import org.generationcp.middleware.manager.StudyDataManagerImpl;
import org.generationcp.middleware.manager.api.GenotypicDataManager;
import org.generationcp.middleware.manager.api.StudyDataManager;
import org.generationcp.middleware.pojos.gdms.AllelicValueWithMarkerIdElement;
import org.generationcp.middleware.pojos.gdms.DatasetElement;
import org.generationcp.middleware.pojos.gdms.Map;
import org.generationcp.middleware.pojos.gdms.MapDetailElement;
import org.generationcp.middleware.pojos.gdms.QtlDetailElement;
import org.generationcp.middleware.pojos.gdms.QtlDetails;
import org.icrisat.mbdt.ui.Activator;

public class LoadFromDatabasePage extends WizardPage implements ModifyListener, SelectionListener {

	private static final String E = null;
	private Label label1;
	private Label label2;
	private Label label3;
	private Label label4;
	private Combo comboGeno;
	private Combo comboMap;
	private Combo comboQTL;
	private Combo comboPheno;
	private boolean genotypeLoaderboolean;
	private boolean linkageLoaderbollean;
	private boolean qtlLoaderbollean;
	private boolean phenoLoaderbollean;
	private Text lblSummary;
	private Text lblError;
	private static Boolean isGenotype=false;


	DatabaseConnectionParameters local, central;  
	ManagerFactory factory;
	GenotypicDataManager manager;
	StudyDataManager phenomanager;
	StudyDataManagerImpl pheno1;
	List<String> glist,plist;
	List<QtlDetails> qlist;
	List<Map> mlist;
	String dtype = "";
	int did = 0, mapid = 0;
	List<Integer> markerId;
	List<String>qtlid;
	List<MapDetailElement> mapdtls =new ArrayList<MapDetailElement>();	
	List<QtlDetailElement> qtldetailelement;
	List<StudyDetails> studydtls;
	StudyType studytype;
	Connection connection;
	protected LoadFromDatabasePage(String pageName) {
		super(pageName);
		setTitle("Load from Database");
		setImageDescriptor(Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/database.gif"));
	}


	public void createControl(Composite parent) {
		setPageComplete(false);
		Composite container = new Composite(parent,SWT.NONE);
		GridLayout gridLayout = new GridLayout();

		gridLayout.numColumns = 2;

		container.setLayout(gridLayout);
		try { 
			
			String url = Platform.getLocation().toString().substring(0, Platform.getLocation().toString().lastIndexOf("/")+1);
//			local = new DatabaseConnectionParameters("localhost", "13306", "ibdbv2_groundnut_1_local", "root", "");
//			central = new DatabaseConnectionParameters("localhost", "13306", "ibdbv2_groundnut_central", "root", "");
			 
			 local = new DatabaseConnectionParameters(url+"DatabaseConfig.properties","local");
			 central = new DatabaseConnectionParameters(url+"DatabaseConfig.properties","central");
			
//			local = new DatabaseConnectionParameters("localhost", "13306", "ibdbv2_cowpea_2_local", "root", "");
//			central = new DatabaseConnectionParameters("localhost", "13306", "ibdbv2_cowpea_central", "root", "");

			factory = new ManagerFactory(local, central);
			manager=factory.getGenotypicDataManager();
			phenomanager = factory.getNewStudyDataManager();
			
		} catch (Exception e) { 
			e.printStackTrace();
		} 
		
		//-----Start-----Loading Genotype datasets 
		label1 = new Label(container,SWT.NONE);
		label1.setText("Genotype Dataset");

		comboGeno = new Combo(container, SWT.READ_ONLY);
		comboGeno.setLayoutData(new GridData(GridData.FILL, GridData.END,true,false));

		try {
			glist = manager.getDatasetNames(0, 20, Database.CENTRAL);
		} catch (MiddlewareQueryException e) {
			e.printStackTrace();
		}

		for(int i=0;i<glist.size();i++){
			comboGeno.add(glist.get(i));
		}

		comboGeno.addSelectionListener(new SelectionAdapter(){			
			public void widgetSelected(SelectionEvent e) {
				if(comboMap.getText() != null){
					comboMap.removeAll();
				}
				
				List<DatasetElement> dbdtls = new ArrayList();
				List<AllelicValueWithMarkerIdElement> resultset=new ArrayList();
				markerId = new ArrayList(); 

				try {
					dbdtls = manager.getDatasetDetailsByDatasetName(comboGeno.getText(), Database.CENTRAL) ;
					dtype = dbdtls.get(0).getDatasetType();
					did =  dbdtls.get(0).getDatasetId();
					markerId = manager.getMarkerIdsByDatasetId(did);
					try {
						mapdtls = manager.getMapAndMarkerCountByMarkers(markerId);
						for(int i =0; i<mapdtls.size(); i++){
							comboMap.add(mapdtls.get(i).getMapName()+" ("+mapdtls.get(i).getMarkerCount()+")");								
						}
					} catch (Exception e1) {
					}
					//----end------getting mapnames based on markerId
					
				} catch (MiddlewareQueryException e1) {
				}
				super.widgetSelected(e);
			}
		});	
		//-----end-----Loading Genotype datasets
		//-------Start--------map data----------
		label2 = new Label(container,SWT.NONE);
		label2.setText("Linkage Map Dataset");

		comboMap = new Combo(container, SWT.READ_ONLY);			
		comboMap.setLayoutData(new GridData(GridData.FILL, GridData.CENTER,true,false));
		comboMap.addSelectionListener(new SelectionAdapter(){			
			public void widgetSelected(SelectionEvent e) {
				if(comboQTL.getText() != null){
					comboQTL.removeAll();
				}
				try {
					String name = comboMap.getText();
					name = name.substring(0, name.lastIndexOf(" ("));
					Integer mapid = manager.getMapIdByName(name);
					qlist = manager.getQtlDetailsByMapId(mapid);
					qtlid = new ArrayList<String>();
					
					for(int i=0;i<qlist.size(); i++){
						String qname =manager.getDatasetNamesByQtlId(qlist.get(i).getId().getQtlId(), 0,(int) manager.countDatasetNamesByQtlId(qlist.get(i).getId().getQtlId())).get(0);
						if(!qtlid.contains(qname)){
							qtlid.add(qname);
							comboQTL.add(qname);
						}
					}
					/*qtldetailelement = manager.getQtlByQtlIds(qtlid, 0, (int) manager.countQtlByQtlIds(qtlid));
					for(int i =0; i<qtldetailelement.size(); i++){
						comboQTL.add(qtldetailelement.get(i).getQtlName());
					}*/
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		//-----end ----map data---------
		//-------------Qtl data--------------
		label3 = new Label(container,SWT.NONE);
		label3.setText("QTL Dataset");

		comboQTL = new Combo(container, SWT.READ_ONLY);				
		comboQTL.setLayoutData(new GridData(GridData.FILL, GridData.CENTER,true,false));
		comboQTL.addModifyListener(this);
		//----end QTL data
		//-------------Phenotype data--------------
		label4 = new Label(container,SWT.NONE);
		label4.setText("Phenotype Dataset");

		comboPheno = new Combo(container, SWT.READ_ONLY);				
		comboPheno.setLayoutData(new GridData(GridData.FILL, GridData.CENTER,true,false));
		comboPheno.addModifyListener(this);
		
		try {
			studydtls = new ArrayList();
			studydtls = phenomanager.getAllStudyDetails(Database.CENTRAL, StudyType.E);		
			for(int i=0;i<studydtls.size();i++){
				comboPheno.add(studydtls.get(i).getStudyName());
			}
		}catch (MiddlewareQueryException e){
		}

		
		//-------------end of phenotype data--------------
		
		setControl(container);

	}


	public void modifyText(ModifyEvent e) {
		String tBox1 = comboGeno.getText();
		String tBox2 = comboMap.getText();
		String tBox3 = comboQTL.getText();
		String tBox4 = comboPheno.getText();

		String validMsg = "Select the genotype and map data";
		if((tBox1.length() == 0) && (tBox2.length() == 0)){
			setErrorMessage(validMsg);	
			setPageComplete(false);
		}else{			
			setPageComplete(true);
		}

	}

	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub

	}

	public void widgetSelected(SelectionEvent e) {


	}

	public String getFilePath() {		
		String returnString="";
		
		if(!(comboGeno.getText().equals("")) ){
			
			returnString = label1.getText()+"!@!"+comboGeno.getText()+"!@!";
			
		} 
		if(!(comboMap.getText().equals(""))){
			
			returnString = returnString+label2.getText()+"!@!"+comboMap.getText()+"!@!";
		} 
		if(!(comboQTL.getText().equals(""))){
			
			returnString = returnString+label3.getText()+"!@!"+comboQTL.getText()+"!@!";
			
		}
		if(!(comboPheno.getText().equals(""))){
			
			returnString = returnString+label4.getText()+"!@!"+comboPheno.getText()+"!@!";
			
		}
		return returnString;
	}

}
