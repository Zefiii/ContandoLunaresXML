import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.jdom2.Attribute;
import org.jdom2.Comment;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.jdom2.output.Format;
import org.jdom2.input.sax.XMLReaders;

import java.util.Scanner;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

//import org.json.JSONException;
//import org.json.JSONObject;
//import org.json.XML;

public class Example {

    /**
     * Read and parse an xml document from the file at example.xml.
     * @return the JDOM document parsed from the file.
     */
    public static Document readDocument() {
        try {
            SAXBuilder builder = new SAXBuilder();
            Document anotherDocument = builder.build(new File("myFile.xml"));
            return anotherDocument;
        } catch(JDOMException e) {
            e.printStackTrace();
        } catch(NullPointerException e) {
            e.printStackTrace();
        } catch(java.io.IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method creates a JDOM document with elements that represent the
     * properties of a car.
     * @return a JDOM Document that represents the properties of a car.
     */
    public static Document createDocument() {
        // Create the root element
        Element carElement = new Element("car");
        //create the document
        Document myDocument = new Document(carElement);
        //add an attribute to the root element
        carElement.setAttribute(new Attribute("vin", "123fhg5869705iop90"));
        //add conent
        carElement.addContent(new Comment("Description of a car"));

        //add some child elements
        Element make = new Element("make");
        make.addContent("Toyota");
        carElement.addContent(make);

        //add some more elements
        carElement.addContent(new Element("model").addContent("Celica"));
        carElement.addContent(new Element("year").addContent("1997"));
        carElement.addContent(new Element("color").addContent("green"));
        carElement.addContent(new Element("license").addContent("1ABC234").setAttribute("state", "CA"));

        return myDocument;
    }

    /**
     * This method accesses a child element of the root element 
     * @param myDocument a JDOM document 
     */
    public static void accessChildElement(Document myDocument) {
        //some setup
        Element carElement = myDocument.getRootElement();

        //Access a child element
        Element yearElement = carElement.getChild("year");

        //show success or failure
        if(yearElement != null) {
            System.out.println("Here is the element we found: " +
                yearElement.getName() + ".  Its content: " +
                yearElement.getText() + "\n");
        } else {
            System.out.println("Something is wrong.  We did not find a year Element");
        }
    }

    /**
     * This method removes a child element from a document.
     * @param myDocument a JDOM document.
     */
    public static void removeChildElement(Document myDocument) {
        //some setup
        System.out.println("About to remove the year element.\nThe current document:");
        outputDocument(myDocument);
        Element carElement = myDocument.getRootElement();

        //remove a child Element
        boolean removed = carElement.removeChild("year");

        //show success or failure
        if(removed) {
            System.out.println("Here is the modified document without year:");
            outputDocument(myDocument);
        } else {
            System.out.println("Something happened.  We were unable to remove the year element.");
        }
    }
    
    public static void commandReset(){
		Element root = new Element("carrental");
		root.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "carrental.xsd");
		Document doc = new Document();
		doc.setRootElement(root);
		outputDocumentToFile(doc);
	}
	
public static void commandNew(){
        
        System.out.print("Introdueix la marca del cotxe: ");
        String marca = System.console().readLine();
        System.out.print("Introdueix el model del cotxe: ");
        String model = System.console().readLine();
        System.out.print("Introdueix el nombre de dies: ");
        String dies = System.console().readLine();
        System.out.print("Introdueix el nombre de cotexes: ");
        String cotxes = System.console().readLine();
        System.out.print("Introdueix el descompte: ");
        String descompte = System.console().readLine();
        Document doc = readDocument();
        Element carElement = new Element("rental").addContent("3");
        carElement.addContent(new Element("make").addContent(marca));
        carElement.addContent(new Element("model").addContent(model));
        carElement.addContent(new Element("nofdays").addContent(dies));
        carElement.addContent(new Element("nofcars").addContent(cotxes));
        carElement.addContent(new Element("discount").addContent(descompte));
        Element root = doc.getRootElement();
        root.addContent(carElement);
        outputDocumentToFile(doc);
        
    }

	
	
	public static void commandList(){
		try {

			File fXmlFile = new File("myFile.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			org.w3c.dom.Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			//System.out.println("Root element : " + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("carrental");   
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					org.w3c.dom.Element eElement = (org.w3c.dom.Element) nNode;
					System.out.println("Rental id : " +","+ eElement.getAttribute("id"));
					System.out.println("Marca : " +","+ eElement.getElementsByTagName("make").item(0).getTextContent());
					System.out.println("Model : " +","+ eElement.getElementsByTagName("model").item(0).getTextContent());
					System.out.println("Nombre dies : " +","+ eElement.getElementsByTagName("nofdays").item(0).getTextContent());
					System.out.println("Nombre unitats : " +","+ eElement.getElementsByTagName("nofcars").item(0).getTextContent());
					System.out.println("Descompte : " +","+ eElement.getElementsByTagName("discount").item(0).getTextContent());
				}
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}	
		
	}

    /**
     * This method shows how to use XMLOutputter to output a JDOM document to
     * the stdout.
     * @param myDocument a JDOM document.
     */
    public static void outputDocument(Document myDocument) {
        try {
            // XMLOutputter outputter = new XMLOutputter("  ", true);
            XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
            outputter.output(myDocument, System.out);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method shows how to use XMLOutputter to output a JDOM document to
     * a file located at myFile.xml.
     * @param myDocument a JDOM document.
     */
    public static void outputDocumentToFile(Document myDocument) {
        //setup this like outputDocument
        try {
            // XMLOutputter outputter = new XMLOutputter("  ", true);
            XMLOutputter outputter = new XMLOutputter();

            //output to a file
            FileWriter writer = new FileWriter("myFile.xml");
            outputter.output(myDocument, writer);
            writer.close();

        } catch(java.io.IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method takes a JDOM document in memory, an XSLT file at example.xslt,
     * and outputs the results to stdout.
     * @param myDocument a JDOM document .
     */
    public static void executeXSLT(Document myDocument) {
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
            // Make the input sources for the XML and XSLT documents
            org.jdom2.output.DOMOutputter outputter = new org.jdom2.output.DOMOutputter();
            org.w3c.dom.Document domDocument = outputter.output(myDocument);
            javax.xml.transform.Source xmlSource = new javax.xml.transform.dom.DOMSource(domDocument);
            StreamSource xsltSource = new StreamSource(new FileInputStream("example.xslt"));
			//Make the output result for the finished document
            StreamResult xmlResult = new StreamResult(System.out);
			//Get a XSLT transformer
			Transformer transformer = tFactory.newTransformer(xsltSource);
			//do the transform
			transformer.transform(xmlSource, xmlResult);
			
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(TransformerConfigurationException e) {
            e.printStackTrace();
		} catch(TransformerException e) {
            e.printStackTrace();
        } catch(org.jdom2.JDOMException e) {
            e.printStackTrace();
        }
	}
	
	
	/*public static void commandToJSON() {
		
		 try {
            JSONObject xmlJSONObj = XML.toJSONObject(readDocument());
            String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
            System.out.println(jsonPrettyPrintString);
        } catch (JSONException je) {
            System.out.println(je.toString());
        }
		
	}*/
		

    /**
     * Main method that allows the various methods to be used.
     * It takes a single command line parameter.  If none are
     * specified, or the parameter is not understood, it prints
     * its usage.
     */
    public static void main(String argv[]) {
        if(argv.length == 1) {
            String command = argv[0];
            if(command.equals("showDocument")) outputDocument(createDocument());
            else if(command.equals("accessChild")) accessChildElement(createDocument());
            else if(command.equals("removeChild")) removeChildElement(createDocument());
            else if(command.equals("save")) outputDocumentToFile(createDocument());
            else if(command.equals("load")) outputDocument(readDocument());
            //else if(command.equals("xslt")) executeXSLT(createDocument());
            else if(command.equals("reset")) commandReset();
	    else if(command.equals("listnou")) commandList();
            else if(command.equals("new")) commandNew();
            else if(command.equals("list")) outputDocument(readDocument());
            else if(command.equals("xslt")) executeXSLT(readDocument());
            //else if(command.equals("xmltojson")) commandToJSON();
            else {
                System.out.println(command + " is not a valid option.");
                printUsage();
            }
        } else {
            printUsage();
        }
    }
	
	
	
    /**
     * Convience method to print the usage options for the class.
     */
    public static void printUsage() {
        System.out.println("Usage: Example [option] \n where option is one of the following:");
        System.out.println("  showDocument - create a new document in memory and print it to the console");
        System.out.println("  accessChild - create a new document and show its child element");
        System.out.println("  removeChild - create a new document and remove its child element");
        System.out.println("  save   - create a new document and save it to myFile.xml");
        System.out.println("  load   - read and parse a document from example.xml");
        System.out.println("  xslt    - create a new document and transform it to HTML with the XSLT stylesheet in example.xslt");
    }
}
