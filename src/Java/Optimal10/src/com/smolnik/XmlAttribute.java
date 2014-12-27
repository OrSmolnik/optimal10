package com.smolnik;
import static com.smolnik.Consts.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XmlAttribute {
	private String _name;
	private int _value;
	private int _type;
	
	public XmlAttribute(String name, int value, int type){
		_name = name;
		_value = value;
		_type = TYPE_IN_ORDER; 
	}
	public XmlAttribute(XmlAttribute data){
		_name = data.getName();
		_value = data.getValue();
		_type = data.getType();
	}
	
	public String getName(){return _name;}
	public int getValue(){return _value;}
	public int getType(){return _type;}
	
	public void setName(String name){_name = name;}
	public void setValue(int value){_value = value;}
	public void setType(int type){_type = type;}
	
	public Node getChildNode(Document doc){
		Element node = doc.createElement(this.getName());
		node.appendChild(doc.createTextNode(Integer.toString(this.getValue())));
		return node;
	}
}
