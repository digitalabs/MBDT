package org.icrisat.mbdt.ui.wizards;
import org.generationcp.middleware.manager.Database;
import org.generationcp.middleware.manager.DatabaseConnectionParameters;
import org.generationcp.middleware.manager.ManagerFactory;
import org.generationcp.middleware.manager.api.GenotypicDataManager;
import org.icrisat.mbdt.ui.wizards.MySAXApp;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class MySAXApp extends DefaultHandler
{

    public MySAXApp ()
    {
	super();
    }
    
    public static void main (String args[])
	throws Exception
    {
	XMLReader xr = XMLReaderFactory.createXMLReader();
	MySAXApp handler = new MySAXApp();
	xr.setContentHandler(handler);
	xr.setErrorHandler(handler);
	
	DatabaseConnectionParameters local = new DatabaseConnectionParameters("localhost", "13306", "ibdbv2_cowpea_2_local", "root", "");
	DatabaseConnectionParameters central = new DatabaseConnectionParameters("localhost", "13306", "ibdbv2_cowpea_central", "root", "");
	
	ManagerFactory factory = new ManagerFactory(local, central);
	 GenotypicDataManager manager=factory.getGenotypicDataManager();
     
     System.out.println("......................  :"+manager.getAllMaps(0, 15, Database.CENTRAL));
     
    }

}
