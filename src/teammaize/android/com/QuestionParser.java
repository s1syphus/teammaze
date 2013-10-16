package teammaize.android.com;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;

public class QuestionParser {

	ArrayList<dbEntry> QuestionList;
	
	public QuestionParser() {
		QuestionList = new ArrayList<dbEntry>();
	}
	
	// pass in a string with the location of the file to be uploaded
	public void parseXML(String fileLocation) {
		
	    try {
	    	 
	    		File fXmlFile = new File(fileLocation);
	    		
	    		if (fXmlFile.exists() == false) {
	    			throw new Exception();
	    		}
	    		
	    		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    		Document doc = dBuilder.parse(fXmlFile);
	     
	    		doc.getDocumentElement().normalize();
	    			
	    		NodeList nList = doc.getElementsByTagName("Entry");
	     
	    		for (int temp = 0; temp < nList.getLength(); temp++) {
	     
	    			String Q, ansC, ans2, ans3, ans4, subject;
	    			
	    			Node nNode = nList.item(temp);
	     
	    			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	     
	    				Element eElement = (Element) nNode;
	    			
	    				// getting elements
	    				subject = eElement.getAttribute("subject");
	    				Q = eElement.getElementsByTagName("Question").item(0).getTextContent();
	    				ansC = eElement.getElementsByTagName("ansCorrect").item(0).getTextContent();
	    				ans2 = eElement.getElementsByTagName("ans2").item(0).getTextContent();
	    				ans3 = eElement.getElementsByTagName("ans3").item(0).getTextContent();
	    				ans4 = eElement.getElementsByTagName("ans4").item(0).getTextContent();
	    				
	    				// creating a new dbEntry
	    				dbEntry tempEntry = new dbEntry(subject, Q, ansC, ans2, ans3, ans4);
	    			
	    				// populating question list
	    				QuestionList.add(tempEntry);
	    			}
	    		}
	        } 
	    	catch (Exception e) {
	    	System.out.println("File Doesn't exsist in Underlying File system");
	    	e.printStackTrace();
	        }
		
	}
	
	// returns the Question List
	public ArrayList<dbEntry> getQuestionList() {
		
		return QuestionList;
	}
	
}
