package com.smolnik;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.ListModel;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import javax.swing.JList;
import java.awt.Window.Type;
import java.awt.Dialog.ModalExclusionType;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ListSelectionModel;

import static com.smolnik.Consts.*;
import com.smolnik.XmlActionas;
import com.smolnik.Logger;

public class PlayersList extends JFrame {
	private JTextField textFieldPlayer = new JTextField();;
	private JList playersList;
	private DefaultListModel playersListModel;
	private JScrollPane playersPane;
	private JButton buttonRemovePlayer;
	private JButton buttonAddPlayer;
	private JLabel labelTitle;
	private JButton buttonSave;
	private JLabel lblPlayerList;
	private JButton backButton;

	public PlayersList() {
		setTitle("Optimal10");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		getContentPane().setForeground(SystemColor.desktop);
		goBackButton();
		setupPanel();

	}

	private void setupPanel() {
		getContentPane().setBackground(SystemColor.inactiveCaptionBorder);
		getContentPane().setLayout(null);
		

		// Initialize
		textFieldPlayer.setBounds(WINDOW_WIDTH - FIELD_WIDTH
				- SPACE_FROM_BORDER, SPACE_FROM_BORDER * 2, FIELD_WIDTH,
				FIELD_HEIGHT);
		buttonAddPlayer = new JButton();
		buttonRemovePlayer = new JButton();
		buttonSave = new JButton();
		setNewButton(buttonSave, "Save Players", WINDOW_WIDTH - FIELD_WIDTH
				- SPACE_FROM_BORDER, SPACE_FROM_BORDER * 4);

		setNewButton(buttonAddPlayer, "Add Player", WINDOW_WIDTH - FIELD_WIDTH
				- SPACE_FROM_BORDER, SPACE_FROM_BORDER);
		setNewButton(buttonRemovePlayer, "Remove Player", WINDOW_WIDTH
				- FIELD_WIDTH - SPACE_FROM_BORDER, SPACE_FROM_BORDER * 3);

		getContentPane().add(buttonSave);
		getContentPane().add(textFieldPlayer);
		getContentPane().add(buttonRemovePlayer);
		getContentPane().add(buttonAddPlayer);

		buttonAddPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Logger.log("AddPlayerAction: Adding player");
				boolean addResult = addItemToList(playersListModel,
						textFieldPlayer);
				if (addResult) {
					System.out
							.println("AddPlayerAction: Player added successfully");
				} else {
					Logger.log("AddPlayerAction: Could not add player");
				}
			}
		});

		buttonRemovePlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Logger.log("RemovePlayerAction: Removing player");
				boolean addResult = listRemoveSelectedItem(playersList);
				if (addResult) {
					System.out
							.println("RemovePlayerAction: Player removed successfully");
				} else {
					System.out
							.println("RemovePlayerAction: Could not remove player");
				}

			}
		});

		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Logger.log("SavePlayersAction: Saving players");
				XmlFile xmlFile = new XmlFile(FILE_PATH_PLAYERS, PLAYER_LIST_ROOT_ELEMENT, PLAYER_LIST_CHILD_ELEMENT);
				xmlFile.saveXmlFile(getAllItemFromList(playersListModel));
			}
		});

		playersListModel = new DefaultListModel();
		playersPane = new JScrollPane();
		playersPane.setLocation(SPACE_FROM_BORDER, SPACE_FROM_BORDER);
		playersPane.setSize(LIST_WIDTH, LIST_HEIGHT);
		getContentPane().add(playersPane);
		playersList = new JList();
		playersPane.setViewportView(playersList);
		playersList.setModel(playersListModel);
		
		lblPlayerList = new JLabel("Players List");
		lblPlayerList.setHorizontalAlignment(SwingConstants.CENTER);
		playersPane.setColumnHeaderView(lblPlayerList);

		labelTitle = new JLabel("Enter Players");
		labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitle.setBounds(WINDOW_WIDTH / 2 - FIELD_WIDTH / 2, 10,
				FIELD_WIDTH, FIELD_HEIGHT);
		getContentPane().add(labelTitle);
		
		
		
		

	}

	public void start(PlayersList form) {

		form.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		form.setSize(WINDOW_WIDTH, WINDOWS_HEIGHT);
		form.setVisible(true);
		form.setTitle("Edit Player List");
		form.setResizable(false);
		XmlFile xmlFile = new XmlFile(FILE_PATH_PLAYERS, PLAYER_LIST_ROOT_ELEMENT, PLAYER_LIST_CHILD_ELEMENT);
		String[] loadPlayers = xmlFile.getChildsNames();
		try {
			addItemToList(playersListModel, loadPlayers);
		} catch (NullPointerException npe) {
			Logger.log("start: Players xml does not exist");
		}
		

	}

	private void setNewButton(JButton btn, String text, int x, int y) {
		btn.setBounds(x, y, FIELD_WIDTH, FIELD_HEIGHT);
		// btn.setBounds(x, y, 130, 30);
		btn.setText(text);

	}

	private boolean listRemoveSelectedItem(JList list) {
		if (list.getModel().getSize() == 0) {
			Logger.log("listRemoveSelectedItem: The list is empty");
			return false;
		}
		DefaultListModel model = (DefaultListModel) list.getModel();
		int selectedIndex = list.getSelectedIndex();
		Logger.log("listRemoveSelectedItem: Removing item in index - '"
				+ selectedIndex + "'");
		if (selectedIndex == -1) {
			Logger.log("listRemoveSelectedItem: Item not selected");
			errorBox("Please select an item to remove", "Item Not Selected");
			return false;
		}
		model.remove(selectedIndex);
		return true;
	}

	private boolean addItemToList(DefaultListModel list, JTextField textField) {
		String itemToAdd = textField.getText().toLowerCase();
		if (itemToAdd.equals("")) {
			Logger.log("addItemList: The textField - '"
					+ textField.getName() + "' is empty");
			errorBox("Please enter a value", "Empty Value");
			return false;
		}
		for (int i = 0; i < playersListModel.size(); i++) {
			if (itemToAdd.equals(playersListModel.get(i).toString()
					.toLowerCase())) {
				Logger.log("addItemList: item - '" + itemToAdd
						+ "' in textFiled -'" + textFieldPlayer.getName()
						+ "' already exist");
				infoBox("The item '" + itemToAdd
						+ "' already exist in the list", "Item Exist");
				return false;
			}
		}
		Logger.log("addItemList: Adding item - '" + itemToAdd);
		list.addElement(itemToAdd);
		textField.setText("");
		return true;
	}

	public static void infoBox(String infoMessage, String titleBar) {
		MsgBox(infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
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

	private void addItemToList(DefaultListModel list, String[] dataArray) {
		Logger.log("addItemToList: Adding array of data to list");
		for (int i = 0; i < dataArray.length; i++) {
			list.addElement(dataArray[i]);
		}
	}

	private String[] getAllItemFromList(DefaultListModel list) {
		int sizeOfList = list.getSize();
		String[] resultStrings = new String[sizeOfList];
		for (int i = 0; i < list.getSize(); i++) {
			resultStrings[i] = (String) list.getElementAt(i);
		}
		return resultStrings;

	}
	public void goBackButton(){
		backButton = new JButton("חזרה");
		setNewButton(backButton, "return", 0, 0);
		getContentPane().add(backButton);
		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Logger.log("GoBackAction: Going back");
				dispose();	
			}
		});
	}
}
