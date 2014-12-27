package com.smolnik;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XmlChild {
	private String _name;
	private ArrayList<XmlAttribute> _data;
	
	public XmlChild(String name, ArrayList<XmlAttribute> data){
		_name = name;
		_data = data;
	}
	public XmlChild(String name){
		_name = name;
		_data = null;
	}
	
	public XmlChild(XmlChild child){
		_name = child.getName();
		_data = child.getDataCopy();
	}
	
	public String getName(){return _name;}
	public ArrayList<XmlAttribute> getData(){return _data;}
	public ArrayList<XmlAttribute> getDataCopy(){
		ArrayList<XmlAttribute> returnList = new ArrayList<XmlAttribute>(); 
		if (!(_data == null)){
			for (XmlAttribute att : _data){
				returnList.add(new XmlAttribute(att));
			}
		}
		return returnList;
		
		}
	
	public void setName(String name){_name = name;}
	public void setData(ArrayList<XmlAttribute> data){_data = data;}
	
	
	
	public void setAttributeData(XmlAttribute data){
		if (this.getData() == null){
			this.setData(new ArrayList<XmlAttribute>());
		}
		if (!this.isAttributeExist(data.getName())){
			_data.add(new XmlAttribute(data));
			return;
		}
		for (XmlAttribute att : this.getData()) {
		    if (att.getName().equals(data.getName())) {
		        att.setValue(data.getValue());
		        return;
		    }
		}
	}

	public XmlAttribute getAttributeData(String name){
		ArrayList<XmlAttribute> data = this.getData();
		if (data == null){
			return null;
		}
		for (XmlAttribute att : data) {
		    if (att.getName().equals(name)) {
		        return new XmlAttribute(att);
		    }
		}
		return null;
	}
	
	public int getAttributeValue(String name){
		XmlAttribute temp = getAttributeData(name);
		if (temp != null){return temp.getValue();}
		return 0;
	}
	
	public boolean isAttributeExist(String name){
		XmlAttribute tempXmlAttribute = getAttributeData(name);
		if (tempXmlAttribute != null) {return true;}
		return false;
	}
	
	public Node getChildNode(Document doc, String childElement){
		Element child = doc.createElement(childElement);
		ArrayList<XmlAttribute> attData = new ArrayList<XmlAttribute>();
		child.setAttribute("name", this.getName());
		
		if (this.getData() == null){
			Logger.log("getChildNode (XmlChild): The attributes of child '" + this.getName() + "' is empty");
			return child;
		}
		
		for (XmlAttribute att : this.getData())
		{
			child.appendChild(att.getChildNode(doc));
		}
		
		return child;
	}
	
	public void removeAttribute(String name){
		Logger.log("removeAttribute (XmlChild): Removing attribute - '"+name+"'");
		if (!this.isAttributeExist(name)){
			Logger.log("removeAttribute (XmlChild): Attribute - '"+name+"' does not exist");
			return;
		}
		for (XmlAttribute att : this.getData()) {
		    if (att.getName().equals(name)) {
		    	_data.remove(_data.indexOf(att));
		    	Logger.log("removeAttribute (XmlChild): Removal complete successfully");
		    	return;
		    }
		}
	}
	
	
	
}
