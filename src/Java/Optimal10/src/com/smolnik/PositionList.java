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

public class PositionList extends JFrame {
	private JTextField textFieldPosition = new JTextField();;
	private JList PositionList;
	private DefaultListModel positionListModel;
	private JScrollPane positionsPane;
	private JButton buttonRemovePosition;
	private JButton buttonAddPosition;
	private JLabel labelTitle;
	private JButton buttonSave;
	private JLabel lblPositionList;
	private JButton backButton;

	public PositionList() {
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
		textFieldPosition.setBounds(WINDOW_WIDTH - FIELD_WIDTH
				- SPACE_FROM_BORDER, SPACE_FROM_BORDER * 2, FIELD_WIDTH,
				FIELD_HEIGHT);
		buttonAddPosition = new JButton();
		buttonRemovePosition = new JButton();
		buttonSave = new JButton();
		setNewButton(buttonSave, "Save Position", WINDOW_WIDTH - FIELD_WIDTH
				- SPACE_FROM_BORDER, SPACE_FROM_BORDER * 4);

		setNewButton(buttonAddPosition, "Add Position", WINDOW_WIDTH - FIELD_WIDTH
				- SPACE_FROM_BORDER, SPACE_FROM_BORDER);
		setNewButton(buttonRemovePosition, "Remove Position", WINDOW_WIDTH
				- FIELD_WIDTH - SPACE_FROM_BORDER, SPACE_FROM_BORDER * 3);

		getContentPane().add(buttonSave);
		getContentPane().add(textFieldPosition);
		getContentPane().add(buttonRemovePosition);
		getContentPane().add(buttonAddPosition);

		buttonAddPosition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Logger.log("AddPositionAction: Adding Position");
				boolean addResult = addItemToList(positionListModel,
						textFieldPosition);
				if (addResult) {
					System.out
							.println("AddPositionAction: Position added successfully");
				} else {
					Logger.log("AddPositionAction: Could not add position");
				}
			}
		});

		buttonRemovePosition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Logger.log("RemovePositionAction: Removing position");
				boolean addResult = listRemoveSelectedItem(PositionList);
				if (addResult) {
					System.out
							.println("RemovePositionAction: Position removed successfully");
				} else {
					System.out
							.println("RemovePositionAction: Could not remove position");
				}

			}
		});

		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Logger.log("SavePositionAction: Saving positions");
				XmlFile xmlFile = new XmlFile(FILE_PATH_POSITIONS, POSITION_LIST_ROOT_ELEMENT, POSITION_LIST_CHILD_ELEMENT);
				xmlFile.saveXmlFile(getAllItemFromList(positionListModel));
			}
		});

		positionListModel = new DefaultListModel();
		positionsPane = new JScrollPane();
		positionsPane.setLocation(SPACE_FROM_BORDER, SPACE_FROM_BORDER);
		positionsPane.setSize(LIST_WIDTH, LIST_HEIGHT);
		getContentPane().add(positionsPane);
		PositionList = new JList();
		positionsPane.setViewportView(PositionList);
		PositionList.setModel(positionListModel);
		
		lblPositionList = new JLabel("Positions List");
		lblPositionList.setHorizontalAlignment(SwingConstants.CENTER);
		positionsPane.setColumnHeaderView(lblPositionList);

		labelTitle = new JLabel("Enter Positions");
		labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitle.setBounds(WINDOW_WIDTH / 2 - FIELD_WIDTH / 2, 10,
				FIELD_WIDTH, FIELD_HEIGHT);
		getContentPane().add(labelTitle);
		
		
		
		

	}

	public void start(PositionList form) {
		form.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		form.setSize(WINDOW_WIDTH, WINDOWS_HEIGHT);
		form.setVisible(true);
		form.setTitle("Edit Position List");
		form.setResizable(false);
		XmlFile xmlFile = new XmlFile(FILE_PATH_POSITIONS, POSITION_LIST_ROOT_ELEMENT, POSITION_LIST_CHILD_ELEMENT);
		String[] loadPosition = xmlFile.getChildsNames();
		try {
			addItemToList(positionListModel, loadPosition);
		} catch (NullPointerException npe) {
			Logger.log("start: Positions xml does not exist");
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
		for (int i = 0; i < positionListModel.size(); i++) {
			if (itemToAdd.equals(positionListModel.get(i).toString()
					.toLowerCase())) {
				Logger.log("addItemList: item - '" + itemToAdd
						+ "' in textFiled -'" + textFieldPosition.getName()
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
