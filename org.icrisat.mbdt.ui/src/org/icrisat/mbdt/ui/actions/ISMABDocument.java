package org.icrisat.mbdt.ui.actions;

import java.io.File;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.icrisat.mbdt.model.RootModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;



public class ISMABDocument implements IsmabXMLDoc {
	
	private File savefile;
	Document document;
	
	public ISMABDocument(File file) {
		// TODO Auto-generated constructor stub
		savefile = file;
	}

	public void save(RootModel rootModel) {
		Element project = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.newDocument();
			document.appendChild(document.createComment(DOCUMENT_COMMENT + " "
					+ new Date()));
			Element root = (Element) document.createElement(ISMAB);
			document.appendChild(root);
			root.setAttribute(DOCUMENT_VERSION, CURRENT_DOCUMENT_VERSION);
			project = (Element) document.createElement(Project);
			root.appendChild(project);
			
		} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
		}
			
		
	
		Element cellElement = document.createElement(CELL);
		cellElement.setAttribute(ROW, Integer.toString(1));
		cellElement.setAttribute(COLUMN, Integer.toString(1));
		String stringValue = "SSSSSSSSS";	
		cellElement.setAttribute(VALUE, stringValue);
		project.appendChild(cellElement);
		
		// Use a Transformer for output
		TransformerFactory tFactory = TransformerFactory.newInstance();
		
		try {
			Transformer transformer = tFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(savefile);				
			transformer.transform(source, result);
			
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
		}
		
	}
}
