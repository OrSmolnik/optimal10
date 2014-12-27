package com.smolnik;

import java.util.ArrayList;

public class Attribute {
	private String _name;
	private int _value;
	private String _player;
	
	public Attribute(String name, int value, String player){
		_player = player;
		_name = name;
		_value = value;
	}
	
	public Attribute(String name, String player){
		_name = name;
		_value = -999;
		_player = player;
	}
	public int getValue(){return _value;}
	public String getName(){return _name;}
	public String getPlayer(){return _player;}
	
	public void setName(String name){_name = name;}
	public void setValue(int value){_value = value;}
	public void setPlayer(String player){_player = player;}
	
	public int compareTo(Attribute att){
		if (this.getValue() > att.getValue()){return 1;}
		if (this.getValue() < att.getValue()){return -1;}
		return 0;
		
	}
}
