package com.smolnik;

public final class Consts {
	//button\textfield size
	public static final int FIELD_WIDTH = 150;
	public static final int FIELD_HEIGHT = 30;
	public static final int FIELD_WIDTH_HALF = 75;
	
	//help fieldSize
//	public static final int HELP_WIDTH = 350;
	public static final int HELP_HEIGHT = 100;
	
	
	//List size
	public static final int LIST_WIDTH = 200;
	public static final int LIST_HEIGHT = 260;
	
	//window size
	public static final int SPACE_FROM_BORDER = 50;
	public static final int WINDOW_WIDTH = 500;
	public static final int WINDOWS_HEIGHT = 500;
	
	//files paths
	public static final String FILE_PATH_PLAYERS = "C:\\temp\\Players.xml";
	public static final String FILE_PATH_POSITIONS = "C:\\temp\\Positions.xml";
	public static final String FILE_PATH_LINEUP = "C:\\temp\\LineUp.xml";
	public static final String FILE_PATH_ATTRIBUTES = "C:\\temp\\Attributes.xml";
	
	//PlayerList 
	public static final String PLAYER_LIST_ROOT_ELEMENT = "Players";
	public static final String PLAYER_LIST_CHILD_ELEMENT = "Player";
	public static final String PLAYER_HELP = "<html>Adding player - write the player name in the text filed above and prese the 'Add Player' button.<br>Removing player - choose a player from the 'Players List' above and prese the 'Remove Player' button.<br>For saving the current player list prese the 'Save Players' button</html>";
	
	//AttributeList 
	public static final String ATTRIBUTE_LIST_ROOT_ELEMENT = "Attributes";
	public static final String ATTRIBUTE_LIST_CHILD_ELEMENT = "Attribute";
	public static final String ATTRIBUTE_HELP = "<html>Adding attribute - write the attribute name in the text filed above and prese the 'Add Attribute' button.<br>Removing attribute - choose an attribute from the 'Attributes List' above and prese the 'Remove Attribute' button.<br>For saving the current attributes list prese the 'Save Attributes' button</html>";
	
	//PossitionList
	public static final String POSITION_LIST_ROOT_ELEMENT = "Positions";
	public static final String POSITION_LIST_CHILD_ELEMENT = "Position";
	public static final String POSITION_HELP = "<html>Adding position - write the position name in the text filed above and prese the 'Add Position' button.<br>Removing position - choose a position from the 'Positions List' above and prese the 'Remove Position' button.<br>For saving the current positions list prese the 'Save Positions' button</html>";
	
	//XmlAttributes Type
	public static final int TYPE_IN_ORDER = 0;
	public static final int TYPE_RATED = 0;
	
	//XmlTypes
	public static final int XML_TYPE_PLAYERES = 0;
	public static final int XML_TYPE_POSITIONS = 1;
	public static final int XML_TYPE_ATTRIBUTES = 2;
	
	//Logger
	public static final String LOG_FILE = "C:\\temp\\log.txt";
	
	

}
