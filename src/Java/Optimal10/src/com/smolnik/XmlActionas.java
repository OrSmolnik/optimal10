package com.smolnik;

import static com.smolnik.Consts.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlActionas {
	private XmlActionas() {
		throw new AssertionError();
	}
	
	@SuppressWarnings("null")
	public XmlFile getXmlFileObject(String fullPath, String rootElement, String chileElement){
		File fXmlFile = new File(fullPath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			Logger.log("Root element :" + doc.getDocumentElement().getNodeName());
			 
			NodeList rootNodeList = doc.getElementsByTagName(chileElement);
			ArrayList<XmlChild> childList = null;
			
			for (int temp = 0; temp < rootNodeList.getLength(); temp++) {
				Node rootNode = rootNodeList.item(temp);
				NodeList childNodes = rootNode.getChildNodes();
				Element nodeElement = (Element) childNodes.item(temp);
				
				ArrayList<XmlAttribute> childAttributes = null;		 
				if (rootNode.getNodeType() == Node.ELEMENT_NODE) {
					childAttributes = getAllAttributes(rootNode);
					childList.add(new XmlChild(nodeElement.getAttribute("name"), childAttributes));
				}
			}
			XmlFile result = new XmlFile(fullPath, childList, rootElement, chileElement);
			return result;
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static ArrayList<XmlAttribute> getAllAttributes(Node node){
		NodeList childNodes = node.getChildNodes();
		ArrayList<XmlAttribute> childList = null;
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node data = childNodes.item(i);
	        if ( data.getNodeType() == Node.TEXT_NODE )
	        	childList.add(new XmlAttribute(data.getNodeName(), Integer.parseInt(data.getNodeValue()), TYPE_IN_ORDER));
		}
		return childList;
	}


	
	private static void saveNewXMLFile(String fullPath, String[] data,
			String rootElement, String childElement){
		try {
			Logger.log("saveXmlFile: Saving new xml file to path - '"
					+ fullPath + "'");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element mainRootElement = doc.createElementNS(
					"http://crunchify.com/CrunchifyCreateXMLDOM", rootElement);
			doc.appendChild(mainRootElement);

			// append child elements to root element
			Logger.log("saveXmlFile: Making child element for data - '"
					+ data.toString() + "'");
			int sizeOfData = data.length;
			for (int i = 0; i < sizeOfData; i++) {
				mainRootElement.appendChild(setPlayer(doc, data[i],
						childElement));
			}

			// output DOM XML to file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(fullPath));
			StreamResult console = new StreamResult(System.out);
			transformer.transform(source, result);
			Logger.log("XML DOM Created Successfully..");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
	

	private static Node setPlayer(Document doc, String name, String childElement) {
		Element player = doc.createElement(childElement);
		player.setAttribute("name", name);
		return player;
		// player.appendChild(getPlayerElements(doc, player, attribute, value));

	}

	// utility method to create text node
	private static Node getPlayerElements(Document doc, Element element,
			String attribute, String value) {
		Element node = doc.createElement(attribute);
		node.appendChild(doc.createTextNode(value));
		return node;
	}

	public static String[] getXMLItemArray(String file, String tag) {
		Logger.log("getXMLItemsNameArray: new request for items");
		if (!isXmlExist(file)) {
			System.out
					.println("getXMLItemsNameArray: The file does not exist - '"
							+ file + "'");
			return null;
		}

		NodeList playersNodeList;
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(file);
			playersNodeList = doc.getElementsByTagName(tag);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		int numberOfPlayers = playersNodeList.getLength();
		System.out
				.println("getXMLItemsNameArray: Number of items found accurding to the tag - '"
						+ tag + "' is - '" + numberOfPlayers + "'");
		String[] playersList = new String[numberOfPlayers];
		for (int playerCount = 0; playerCount < numberOfPlayers; playerCount++) {
			Node playerNode = playersNodeList.item(playerCount);
			if (playerNode.getNodeType() == Node.ELEMENT_NODE) {
				Element playerNameElement = (Element) playerNode;
				playersList[playerCount] = playerNameElement
						.getAttribute("name");
			}
		}
		return playersList;

	}

	public static boolean isXmlExist(String xmlPath) {
		File f = new File(xmlPath);
		if (f.exists() && !f.isDirectory()) {
			return true;
		}
		return false;
	}
}
