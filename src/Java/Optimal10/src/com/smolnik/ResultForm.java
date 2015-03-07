package com.smolnik;

import static com.smolnik.Consts.*;

import java.awt.Color;
import java.awt.SystemColor;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class ResultForm extends JFrame {
	private JList resultList;
	private DefaultListModel resultListModel;
	private JScrollPane resultPane;
	private JLabel lbResultList;
	private JLabel labelResult;
	
	private ResultTuple[] resultTuple; 
	
	public ResultForm() {
//		infoBox(resultString,"Result!");
		getContentPane().setBackground(SystemColor.inactiveCaptionBorder);
		getContentPane().setLayout(null);
		
		

		// Initialize
		resultListModel = new DefaultListModel();
		resultPane = new JScrollPane();
		resultPane.setLocation(SPACE_FROM_BORDER, SPACE_FROM_BORDER);
		resultPane.setSize(LIST_WIDTH, LIST_HEIGHT);
		resultList = new JList();
		resultPane.setViewportView	(resultList);
		resultList.setModel(resultListModel);
		
		lbResultList = new JLabel("Shibutch");
		lbResultList.setHorizontalAlignment(SwingConstants.CENTER);
		resultPane.setColumnHeaderView(lbResultList);

		labelResult = new JLabel("Result");
		labelResult.setHorizontalAlignment(SwingConstants.CENTER);
		labelResult.setBounds(WINDOW_WIDTH / 2 - FIELD_WIDTH / 2, 10,
				FIELD_WIDTH, FIELD_HEIGHT);
		
		getContentPane().add(resultPane);
		getContentPane().add(labelResult);
		
		
		
		resultTuple = Calculations.Calculate();
		String[] resultString =  new String[resultTuple.length]; 
		for (int i = 0 ; i < resultTuple.length ; i++) {
			resultString[i] = resultTuple[i].toString();
		}
		addItemToList(resultListModel, resultString);
		
	}

	public void start(ResultForm form) {

		form.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		form.setSize(WINDOW_WIDTH, WINDOWS_HEIGHT);
		form.setVisible(true);
		
		form.setTitle("Result");
		form.setResizable(false);
		
	}
	
	private void setNewButton(JButton btn, String text, int x, int y) {
		btn.setBounds(x, y, FIELD_WIDTH, FIELD_HEIGHT);
		// btn.setBounds(x, y, 130, 30);
		btn.setText(text);
	}
	
	private void addItemToList(DefaultListModel list, String[] dataArray) {
		Logger.log("addItemToListResult: Adding array of data to list");
		for (int i = 0; i < dataArray.length; i++) {
			list.addElement(dataArray[i]);
		}
	}

	
	public static void infoBox(String infoMessage, String titleBar) {
		MsgBox(infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void MsgBox(String errorMessage, String titleBar,
			int typeOfMessage) {
		Logger.log("msgBox: Prompet message box type - '"
				+ typeOfMessage + "' with message - '" + errorMessage
				+ "' and title - '" + titleBar + "'");
		JOptionPane.showMessageDialog(null, errorMessage, titleBar,
				typeOfMessage);
	}
}
