package com.smolnik;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JOptionPane;

import com.smolnik.PlayersList;
import com.smolnik.First;
import static com.smolnik.Consts.*;
public class Run {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Logger.log("Welcome! This is a new run of the AMAZING program 'Optimal10'. Have fun :) ");
		handlePreRun();
		First form = new First();
		form.start(form);
		
		
	}
	
	private static void handlePreRun(){
		Logger.log("handlePreRun: setting environment...");
		File baseFolder = new File(PROGRAM_FOLDER);
		if (!baseFolder.exists()){
			Logger.log("handlePreRun: Base folder '" + PROGRAM_FOLDER + "' does not exist. Creating one");
			boolean result = baseFolder.mkdirs();
			if (!result){
				Logger.log("handlePreRun: Could not create base folder" + baseFolder + "'. Exit program.");
				JOptionPane.showMessageDialog(null, "Please run the program as administrator", "Error",
						JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		}
	}

}
