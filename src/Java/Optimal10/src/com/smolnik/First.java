package com.smolnik;

import static com.smolnik.Consts.*;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class First extends JFrame {
	
	private JButton btnPlayersList = new JButton();
	private JButton btnAttributes = new JButton();
	private JButton btnPosition = new JButton();
	private JButton btnSelectAttributes = new JButton();
	private JButton btnCalculate = new JButton();
	private String resultString;
	
	public First() {
		getContentPane().setLayout(null);
		
		setNewButton(btnPlayersList, "Players List", WINDOW_WIDTH / 2- (FIELD_WIDTH/2), SPACE_FROM_BORDER);
		setNewButton(btnAttributes, "Attributes List", (WINDOW_WIDTH / 2- (FIELD_WIDTH/2)) , SPACE_FROM_BORDER*2);
		setNewButton(btnPosition, "Positions List", (WINDOW_WIDTH / 2- (FIELD_WIDTH/2)) , SPACE_FROM_BORDER*3);
		setNewButton(btnSelectAttributes, "Rate Attributes", (WINDOW_WIDTH / 2- (FIELD_WIDTH/2)) , SPACE_FROM_BORDER*4);
		setNewButton(btnCalculate, "Go!", (WINDOW_WIDTH / 2- (FIELD_WIDTH/2)) , SPACE_FROM_BORDER*6);
		
		getContentPane().add(btnAttributes);
		getContentPane().add(btnPlayersList);
		getContentPane().add(btnSelectAttributes);
		getContentPane().add(btnPosition);
		getContentPane().add(btnCalculate);
		getContentPane().setBackground(SystemColor.inactiveCaptionBorder);
		
		btnPlayersList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PlayersList form = new PlayersList();
				form.start(form);				
			}
		});
		
		btnSelectAttributes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SelectAttribute form = new SelectAttribute();
				form.start(form);
			}
		});
		
		btnPosition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PositionList form = new PositionList();
				form.start(form);
			}
		});
		
		btnAttributes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AttributesList form = new AttributesList();
				form.start(form);
			}
		});
		
		
		btnCalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ResultForm form = new ResultForm();
				form.start(form);
			}
		});
	}
	
	
	
	public void start(First form) {

		form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		form.setSize(WINDOW_WIDTH, WINDOWS_HEIGHT);
		form.setVisible(true);
		
		form.setTitle("Main Manu");
		form.setResizable(false);
		
		}
	
	private void setNewButton(JButton btn, String text, int x, int y) {
		btn.setBounds(x, y, FIELD_WIDTH, FIELD_HEIGHT);
		// btn.setBounds(x, y, 130, 30);
		btn.setText(text);
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
