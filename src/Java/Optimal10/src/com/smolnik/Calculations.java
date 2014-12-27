package com.smolnik;

import java.io.File;

import static com.smolnik.Consts.*;

public class Calculations {
	private Calculations() {
		throw new AssertionError();
	}
	
	public static String Calculate(){
		if (!isXmlExist(FILE_PATH_PLAYERS) || !isXmlExist(FILE_PATH_POSITIONS)){
			Logger.log("Calculate: Players XML or Position XML not exist");
			return "";
		}
		XmlFile xmlFilePlayers = new XmlFile(FILE_PATH_PLAYERS, PLAYER_LIST_ROOT_ELEMENT, PLAYER_LIST_CHILD_ELEMENT);
		XmlFile xmlFilePositions = new XmlFile(FILE_PATH_POSITIONS, POSITION_LIST_ROOT_ELEMENT, POSITION_LIST_CHILD_ELEMENT);
//		ArrayList<Attribute> attributesPlayers = xmlFilePlayers.getAttributeList();
//		ArrayList<Attribute> attributesPositions = xmlFilePositions.getAttributeList();
//		Attribute tempAtt;
		
		String[] positions = xmlFilePositions.getAllChileNames();
		String[] players = xmlFilePlayers.getAllChileNames();
		int[][] mat = new int[positions.length][players.length];
		
//		for (int i = 0 ; i < positions.length ; i++){
//			for (int j = 0 ; j < players.length ; j++){
//				Logger.log("TEST1: position="+positions[i]+", player="+players[j]);
//			}
//		}
		
		for (int i = 0 ; i < players.length ; i++){
			int cost = 0;
			for (int j = 0 ; j < positions.length ; j++){
				cost = Calculations.calculateCost(xmlFilePlayers, xmlFilePositions, players[i], positions[j]);
				mat[i][j] = cost;
			}
		}
		
		
//		for (int i = 0 ; i < positions.length ; i++){
//			for (int j = 0 ; j < players.length ; j++){
//				System.out.print(mat[i][j] + " ");
//			}
//			Logger.log(" ");
//		}
		
		HungarianAlgorithm ha = new HungarianAlgorithm(mat);
		String resultString = "";
		int[] matResult = ha.execute();
		for (int i = 0 ; i < mat.length ; i++){
			resultString += positions[i] + ": " + players[matResult[i]] + "\n";
		}
		Logger.log(resultString);
		return resultString;
		
//		
//		for (int i = 0 ; i < testMat.length ; i++){
//			for (int j = 0 ; j < testMat[i].length ; j++){
//				System.out.print(testMat[i][j] + " ");
//			}
//			Logger.log(" ");
//		}
	}
	
	public static int calculateCost(XmlFile xmlPlayers, XmlFile xmlPositions, String player, String possition) {
		XmlChild playerAttributesXmlChild = xmlPlayers.getChildData(player);
		XmlChild positionAttributeXmlChild = xmlPositions.getChildData(possition);
		int cost = 0;
		for (XmlAttribute attPlayer : playerAttributesXmlChild.getData()){
			for (XmlAttribute attPosition : positionAttributeXmlChild.getData()){
				if (attPlayer.getName().equals(attPosition.getName())){
					cost += Math.max(0, attPlayer.getValue()-attPosition.getValue());
				}
			}
		}
		Logger.log("calculateCost: player="+player+", possition="+possition+", cost="+cost);
		return cost;
	}
	
	public static boolean isXmlExist(String xmlPath) {
		File f = new File(xmlPath);
		if (f.exists() && !f.isDirectory()) {
			return true;
		}
		return false;
	}
	
}
