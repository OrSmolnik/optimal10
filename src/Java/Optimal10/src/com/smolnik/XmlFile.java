package com.smolnik;

import static com.smolnik.Consts.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

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

public class XmlFile {
	private String _fullPath;
	private ArrayList<XmlChild> _data;
	private String _rootElement;
	private String _childElement;
	
	public XmlFile(String fullPath, ArrayList<XmlChild> data, String rootElement, String chileElement){
		_fullPath = fullPath;
		_data = data;
		_rootElement = rootElement;
		_childElement = chileElement;
	}
	public XmlFile(String fullPath, String rootElement, String chileElement){
		try {
			File fXmlFile = new File(fullPath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			if (!isXmlExist(fullPath)){
				Logger.log("XmlFile constractor: The xml does not exist in path - " + fullPath);
				_fullPath = fullPath;
				_rootElement = rootElement;
				_childElement = chileElement;
				this.saveXmlFile();
			}
			
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			
			Logger.log("Root element :" + doc.getDocumentElement().getNodeName());
			 
			NodeList rootNodeList = doc.getElementsByTagName(chileElement);

			ArrayList<XmlChild> childList = new ArrayList<XmlChild>();
			
			for (int temp = 0; temp < rootNodeList.getLength(); temp++) {
				
				Node childNode = rootNodeList.item(temp);
				
				if (childNode.getNodeType() == Node.ELEMENT_NODE){
					ArrayList<XmlAttribute> childAttributes = new ArrayList<XmlAttribute>();
					Element childElement = (Element) childNode;
					String childName = childElement.getAttribute("name");
					
					Logger.log("XmlFile constractor: Got new child name - " + childName);
					childAttributes = getAllAttributes(childNode);
					if (!childAttributes.isEmpty()){
						Logger.log("XmlFile constractor: The node name - " + childName + " has attributes");
						childList.add(new XmlChild(childName, childAttributes));
					} else {
						Logger.log("XmlFile constractor: The node name - " + childName + " is empty");
						childList.add(new XmlChild(childName));
					}
					
//					for (int i = 0; i < attributesNodesList.getLength(); i++) {
//						Node attributeNode = attributesNodesList.item(i);
//						
//						Logger.log("getLength  attributesNodesList " + attributesNodesList.getLength());
//						Logger.log("getTextContent  attributeNode " + attributeNode.getTextContent());
//						Logger.log("getNodeType  attributeNode " + attributeNode.getNodeType());
//						
//					}
				
				}
			}
			_fullPath = fullPath;
			_data = childList;
			_rootElement = rootElement;
			_childElement = chileElement;
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		Logger.log("XmlFile constractor: params are:\n_fullPath = "+_fullPath+"\n_data = "+_data+"\n_rootElement = "+_rootElement+"\n_childElement = "+_childElement);

	}
	
		
	public void setFullPath(String fullPath){_fullPath = fullPath;}
	public void setData(ArrayList<XmlChild> data){_data = data;}
	public void setRootElement(String rootElement){_rootElement = rootElement;}
	public void setChildElement(String childElement){_childElement = childElement;}
	
	public String getFullPath(){return _fullPath;}
	public ArrayList<XmlChild> getData(){return _data;}
	public String getRootElement(){return _rootElement;}
	public String getChildElement(){return _childElement;}
	
	public XmlChild getChildData(String name){
		for (XmlChild ch : this.getData()) {
		    if (ch.getName().equals(name)) {
		        return ch;
		    }
		}
		return null;
	}
	
	public void setChildData(XmlChild child){
		this.removeChild(child.getName());
		_data.add(new XmlChild(child));
	}
	
	public XmlChild removeChild(String name){
		int i = 0;
		for (XmlChild ch : this.getData()) {
		    if (ch.getName().equals(name)) {
		    	_data.remove(i);
		        return ch;
		    }
		    else {i+=1;}
		}
		return null;
	}
	public XmlChild getAttributeData(String name){
		for (XmlChild att : this.getData()) {
		    if (att.getName().equals(name)) {
		        return new XmlChild(att);
		    }
		}
		return null;
	}
	
	public boolean isChildExist(String name){
		XmlChild tempXmlChild = getAttributeData(name);
		if (tempXmlChild != null) {return true;}
		return false;
	}
	public String[] getChildsNames(){
		ArrayList<XmlChild> childList = this.getData();
		
		if (this.getData()==null){
			return null;
		}
		
		String[] childString = new String[childList.size()];
		int i = 0;
		for (XmlChild ch : childList){
			childString[i] = ch.getName();
//			Logger.log("childNames = " + ch.getName());
			i++;
		}
		return childString;
	}
	
	public static ArrayList<XmlAttribute> getAllAttributes(Node node){
		NodeList childNodes = node.getChildNodes();
		ArrayList<XmlAttribute> childList = new ArrayList<XmlAttribute>();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node data = childNodes.item(i);
	        if ( data.getNodeType() == Node.ELEMENT_NODE ){
//	        	Logger.log("getAllAttributes: data.getTextContent = " + data.getTextContent());
	        	childList.add(new XmlAttribute(data.getNodeName(), Integer.parseInt(data.getTextContent()), TYPE_IN_ORDER));
	        }
		}
		return childList;
	}
	public void saveXmlFile(){
		this.doSave();
	}
	
	public void saveXmlFile(String[] listData){
		Logger.log("saveXmlFile: Saving xmlFileObject to path - " + this.getFullPath());
		
		int listDataLength = listData.length;
		ArrayList<XmlChild> newChildList = new ArrayList<XmlChild>();
		
		for (int iList = 0 ; iList < listDataLength ; iList++ ){
			if (this.isChildExist(listData[iList])){
				Logger.log("saveXmlFile: the child - '" + listData[iList] + "' is already exist");
			}
			else {
				Logger.log("saveXmlFile: New child found - '" + listData[iList] + "'");
				newChildList = this.getData();
				newChildList.add(new XmlChild(listData[iList]));
				this.setData(newChildList);
			}
		}
		this.removeLeftOvers(listData);
		this.doSave();

	}
	private void removeLeftOvers(String[] listData){
		Logger.log("removeLeftOvers: Removing child not used");
		Logger.log("removeLeftOvers: Number of child befor removel - " + this.getData().size());
		int xmlDataLength = this.getData().size();
		int listDataLength = listData.length;
		boolean isNotExist;
		
		if (xmlDataLength == listDataLength){
			Logger.log("removeLeftOvers: There is no childs to remove");
			return;
		}
		
		Iterator<XmlChild> i = this.getData().iterator();
		while (i.hasNext()) {
			isNotExist = false;
			XmlChild ch = i.next();
			for (int iList = 0 ; iList < listDataLength ; iList++ ){
				if (ch.getName().equals(listData[iList])){
					isNotExist = true;
				}
			}
			if (!isNotExist){
				Logger.log("removeLeftOvers: Removing child - '" + ch.getName() + "'");
				i.remove();
			}
		}
		Logger.log("removeLeftOvers: Number of child after removel - " + this.getData().size());
	}

	private void doSave(){
			try {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.newDocument();
				doc.getDocumentElement();
		
				// root elements
				Element mainRootElement = doc.createElementNS(
						"http://crunchify.com/CrunchifyCreateXMLDOM", this.getRootElement());
				doc.appendChild(mainRootElement);
				
				//xml content
				if (!isXmlExist(this.getFullPath())){
					Logger.log("doSave: saving empty xml file");
				}
				else{
					for (XmlChild ch : this.getData())
					{
						Logger.log("doSave: saving - '" + ch.getName() + "'");
						Node child = ch.getChildNode(doc, this.getChildElement());
						mainRootElement.appendChild(child);
//						
//						if (this.getData() == null){
//							Logger.log("getChildNode (XmlChild): The attributes of child '" + this.getName() + "' is empty");
//							return null;
					}
				}
				
	
				// output DOM XML to file
				TransformerFactory transformerFactory = TransformerFactory
						.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(this.getFullPath()));
				transformer.transform(source, result);
				Logger.log("XML DOM Created Successfully..");
				
			} catch (ParserConfigurationException | TransformerException pce) {
				pce.printStackTrace();
			}
	}
	public static boolean isXmlExist(String xmlPath) {
		File f = new File(xmlPath);
		if (f.exists() && !f.isDirectory()) {
			return true;
		}
		return false;
	}
	public String[] getAllChileNames(){
		String[] data = new String[this.getData().size()];
		int i = 0;
		for (XmlChild ch : this.getData()){
			data[i] = ch.getName();
			i++;
		}
		return data; 
	}
	
	public ArrayList<Attribute> getAttributeList(){
		Attribute tempAtt;
		ArrayList<Attribute> tempAttributeList = new ArrayList<Attribute>();
		for (XmlChild ch : this.getData()){
			for(XmlAttribute att : ch.getData()){
				tempAtt = new Attribute(att.getName(), att.getValue(), ch.getName());
				tempAttributeList.add(tempAtt);
			}
		}
		return tempAttributeList;
	}
}
