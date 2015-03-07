package com.smolnik;

public class ResultTuple{ 
	  public final String _pos; 
	  public final String _player; 
	  public ResultTuple(String pos, String player) { 
	    this._pos = pos; 
	    this._player = player; 
	  } 
	  
	  public String toString(){
		  return this._pos + ": " + this._player;
	  }
	} 
