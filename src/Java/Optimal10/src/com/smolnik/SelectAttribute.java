package com.smolnik;

import static com.smolnik.Consts.*;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class SelectAttribute extends JFrame {

	private JList attributesList;
	private DefaultListModel attributeListModel;
	private JScrollPane attributePane;
	
	private JButton btnPlayer = new JButton();
	private JButton btnPosition = new JButton();
	
	public SelectAttribute(){
		setTitle("Optimal10");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		getContentPane().setForeground(SystemColor.desktop);
		setupPanel();
	}
	
	private void setupPanel() {

		getContentPane().setBackground(SystemColor.inactiveCaptionBorder);
		getContentPane().setLayout(null);
		
		attributeListModel = new DefaultListModel();
		attributePane = new JScrollPane();
		attributePane.setLocation((WINDOW_WIDTH/2)-(LIST_WIDTH/2), SPACE_FROM_BORDER);
		attributePane.setSize(LIST_WIDTH, LIST_HEIGHT);
		attributesList = new JList();
		attributePane.setViewportView(attributesList);
		attributesList.setModel(attributeListModel);
		
		setNewButton(btnPlayer, "For Players", SPACE_FROM_BORDER, WINDOWS_HEIGHT-(SPACE_FROM_BORDER*2)-FIELD_HEIGHT);
		setNewButton(btnPosition, "For Positions", WINDOW_WIDTH-FIELD_WIDTH-SPACE_FROM_BORDER, WINDOWS_HEIGHT-(SPACE_FROM_BORDER*2)-FIELD_HEIGHT);
		
		getContentPane().add(btnPlayer);
		getContentPane().add(btnPosition);
		getContentPane().add(attributePane);
		
		btnPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO: open "FOR PLAYERS" Window
				Logger.log("forPlayersClick: Start...");
				DefaultListModel model = (DefaultListModel) attributesList.getModel();
				int selectedIndex = attributesList.getSelectedIndex();
				if (selectedIndex == -1) {
					errorBox("Please select an attributFe", "Attribute Not Selected");
					return;
				}
				
				Logger.log("forPlayersClick: The selected index is - " + selectedIndex);
				PlayerAttributes form = new PlayerAttributes(attributesList.getSelectedValue().toString(), XML_TYPE_PLAYERES);
				form.start(form);
			}
		});
		
		btnPosition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO: open "FOR PLAYERS" Window
				Logger.log("forPositionClick: Start...");
				DefaultListModel model = (DefaultListModel) attributesList.getModel();
				int selectedIndex = attributesList.getSelectedIndex();
				if (selectedIndex == -1) {
					errorBox("Please select an attribute", "Attribute Not Selected");
					return;
				}
				
				Logger.log("forPlayersClick: The selected index is - " + selectedIndex);
				PlayerAttributes form = new PlayerAttributes(attributesList.getSelectedValue().toString(), XML_TYPE_POSITIONS);
				form.start(form);
			}
		});
		
		btnPosition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO: open "FOR POSITIONS" Window
			}
		});
		
		
	}

	
	
	public void start(SelectAttribute form) {

		form.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		form.setSize(WINDOW_WIDTH, WINDOWS_HEIGHT);
		form.setVisible(true);
		form.setTitle("Select Attribute");
		
		form.setResizable(false);
		XmlFile xmlFile = new XmlFile(FILE_PATH_ATTRIBUTES, ATTRIBUTE_LIST_ROOT_ELEMENT, ATTRIBUTE_LIST_CHILD_ELEMENT);
		String[] loadPlayers = xmlFile.getChildsNames();
		try {
			addItemToList(attributeListModel, loadPlayers);
		} catch (NullPointerException npe) {
			Logger.log("start: Players xml does not exist");
		}
	}
	
	private void addItemToList(DefaultListModel list, String[] dataArray) {
		Logger.log("addItemToList: Adding array of data to list");
		for (int i = 0; i < dataArray.length; i++) {
			list.addElement(dataArray[i]);
		}
	}
	
	private void setNewButton(JButton btn, String text, int x, int y) {
		btn.setBounds(x, y, FIELD_WIDTH, FIELD_HEIGHT);
		// btn.setBounds(x, y, 130, 30);
		btn.setText(text);
	}
	public static void errorBox(String errorMessage, String titleBar) {
		MsgBox(errorMessage, titleBar, JOptionPane.ERROR_MESSAGE);
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
