package com.smolnik;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.smolnik.Consts.*;


public class Logger {
	private Logger() {
		throw new AssertionError();
	}
	
	public static void log(String str){
		System.out.println(str);
		if (!isXmlExist(LOG_FILE)){
			
		}
		BufferedWriter bw = null;
		 
		try {
	         // APPEND MODE SET HERE
	         bw = new BufferedWriter(new FileWriter(LOG_FILE, true));
	     bw.write(getTime() + " : " + str);
	     bw.newLine();
	     bw.flush();
	      } catch (IOException ioe) {
	     ioe.printStackTrace();
	      } finally {                       // always close the file
	     if (bw != null) try {
	        bw.close();
	     } catch (IOException ioe2) {
	        // just ignore it
	     }
	      } // end try/catch/finally
	 
	}
	public static String getTime(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	public static boolean isXmlExist(String xmlPath) {
		File f = new File(xmlPath);
		if (f.exists() && !f.isDirectory()) {
			return true;
		}
		return false;
	}
}
