package com.smolnik;

import static com.smolnik.Consts.*;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class PlayerAttributes extends JFrame {
	
	private JList ratedList;
	private DefaultListModel ratedListModel;
	private JScrollPane ratedPane;
	
	private JList unratedList;
	private DefaultListModel unratedListModel;
	private JScrollPane unratedPane;
	private int NEW_WINDOW_WIDTH = WINDOW_WIDTH+100;
	private int NEW_WINDOWS_HEIGHT = WINDOWS_HEIGHT - 100;
	
	private JButton btnAdd = new JButton();
	private JButton btnRemove = new JButton();
	private JButton btnSave = new JButton();
	
	public PlayerAttributes(String attributeName, int xmlType){
		setTitle("Optimal10");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		getContentPane().setForeground(SystemColor.desktop);
		setupPanel(attributeName, xmlType);
	}
	
	private void setupPanel(final String attributeName, final int xmlType) {
		getContentPane().setBackground(SystemColor.inactiveCaptionBorder);
		getContentPane().setLayout(null);
		
		setNewButton(btnAdd, "<--", NEW_WINDOW_WIDTH / 2- (FIELD_WIDTH_HALF/2), SPACE_FROM_BORDER);
		setNewButton(btnRemove, "-->", NEW_WINDOW_WIDTH / 2- (FIELD_WIDTH_HALF/2), SPACE_FROM_BORDER*2);
		setNewButton(btnSave, "Save", NEW_WINDOW_WIDTH / 2- (FIELD_WIDTH_HALF/2), SPACE_FROM_BORDER*4);
		
		ratedListModel = new DefaultListModel();
		ratedPane = new JScrollPane();
		ratedPane.setLocation(25, SPACE_FROM_BORDER);
		ratedPane.setSize(LIST_WIDTH, LIST_HEIGHT);
		ratedList = new JList();
		ratedPane.setViewportView(ratedList);
		ratedList.setModel(ratedListModel);
		
		
		unratedListModel = new DefaultListModel();
		unratedPane = new JScrollPane();
		unratedPane.setLocation(NEW_WINDOW_WIDTH-LIST_WIDTH-25, SPACE_FROM_BORDER);
		unratedPane.setSize(LIST_WIDTH, LIST_HEIGHT);		
		unratedList = new JList();
		unratedPane.setViewportView(unratedList);
		unratedList.setModel(unratedListModel);
		
		
		getContentPane().add(ratedPane);
		getContentPane().add(unratedPane);
		getContentPane().add(btnAdd);
		getContentPane().add(btnRemove);
		getContentPane().add(btnSave);
		
		ArrayList<Attribute> attributesExist = new ArrayList<Attribute>();
		ArrayList<Attribute> attributesNotExist = new ArrayList<Attribute>();
		
		
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Logger.log("AddPlayerToRatedListAction: Adding player");
				int selectedIndex = unratedList.getSelectedIndex();
				if (selectedIndex == -1) {
					errorBox("Please select a player to add", "Player Not Selected");
					return;
				}
				String selectedValue = unratedList.getSelectedValue().toString();
				addItemToList(ratedListModel, selectedValue);
				listRemoveSelectedItem(unratedList);
				
			}
		});
		
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Logger.log("AddPlayerToUnratedListAction: Adding player to unrated list");
				int selectedIndex = ratedList.getSelectedIndex();
				if (selectedIndex == -1) {
					errorBox("Please select a player to remove", "Player Not Selected");
					return;
				}
				String selectedValue = ratedList.getSelectedValue().toString();
				addItemToList(unratedListModel, selectedValue);
				listRemoveSelectedItem(ratedList);
				
			}
		});
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Logger.log("SavingAttributes: Saving to xml in type "+xmlType);
				XmlFile xml;
				if (xmlType == XML_TYPE_PLAYERES){
					xml = new XmlFile(FILE_PATH_PLAYERS, PLAYER_LIST_ROOT_ELEMENT, PLAYER_LIST_CHILD_ELEMENT);
				}
				else{
					xml = new XmlFile(FILE_PATH_POSITIONS, POSITION_LIST_ROOT_ELEMENT, POSITION_LIST_CHILD_ELEMENT);
				}
				
				int ratedListSize = ratedList.getModel().getSize();
				int unratedListSize = unratedList.getModel().getSize();
				ArrayList<XmlChild> childsList = xml.getData();
				XmlAttribute xmlAttribute;
				String playereName;
				
				for (int i = 0 ; i < ratedListSize ; i++){
					playereName = ratedList.getModel().getElementAt(i).toString();
					for (XmlChild ch : childsList){
						if (ch.getName().equals(playereName)){
							xmlAttribute = new XmlAttribute(attributeName, i+1, TYPE_RATED);
							ch.setAttributeData(xmlAttribute);
							break;
						}
					}
				}
				
				for (int i = 0 ; i < unratedListSize ; i++){
					playereName = unratedList.getModel().getElementAt(i).toString();
					for (XmlChild ch : childsList){
						if (ch.getName().equals(playereName)){
							ch.removeAttribute(attributeName);
						}
					}
				}
				
				xml.saveXmlFile();
			}
		});
		
		
		boolean isOrdered = orderLists(attributeName, xmlType, attributesExist, attributesNotExist);
		
		if (!isOrdered){
			Logger.log("PlayereAttributes (setupPanel): The xml does not exist");
			String message;
			if (xmlType == XML_TYPE_PLAYERES){
				message = "Please enter players fitst (from the first window)";
			}
			else{
				message = "Please enter possitions first (from the first window)";
			}
			MsgBox(message, "Could Not Find File", JOptionPane.ERROR_MESSAGE);
		}
		
		addItemToList(unratedListModel, attributesNotExist);
		addItemToList(ratedListModel, attributesExist);
		
		
		
		
	}

	private boolean orderLists(final String attributeName, final int xmlType,
			ArrayList<Attribute> attributesExist,
			ArrayList<Attribute> attributesNotExist) {
		XmlFile xmlFile;
		if (xmlType == XML_TYPE_PLAYERES){
			xmlFile = new XmlFile(FILE_PATH_PLAYERS, PLAYER_LIST_ROOT_ELEMENT, PLAYER_LIST_CHILD_ELEMENT);
		}
		else{
			xmlFile = new XmlFile(FILE_PATH_POSITIONS, POSITION_LIST_ROOT_ELEMENT, POSITION_LIST_CHILD_ELEMENT);
		}
		
		if (xmlFile.getFullPath() == null){
			return false;
			
		}
		
		int attributeValue;
		for (XmlChild ch : xmlFile.getData()){
			attributeValue = ch.getAttributeValue(attributeName);
			if (attributeValue == 0){
				attributesNotExist.add(new Attribute(attributeName, ch.getName()));
			}
			else{
				attributesExist.add(new Attribute(attributeName, attributeValue, ch.getName()));
			}
		}
		
		Collections.sort(attributesExist, new Comparator<Attribute>() {
			@Override
			public int compare(Attribute att1, Attribute att2) {
				return att1.compareTo(att2);
			}
	    });
//		
//		for (Attribute att : attributesExist){
//			Logger.log("TEST: value = "+att.getValue()+", playere name = "+att.getPlayer()+", attribute name = "+att.getName());
//		}
		return true;
	}	

	
	
	public void start(PlayerAttributes form) {

		form.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		form.setSize(NEW_WINDOW_WIDTH, NEW_WINDOWS_HEIGHT);
		form.setVisible(true);
		form.setTitle("Attribute to Players");
		form.setResizable(false);
	}
	
	private void setNewButton(JButton btn, String text, int x, int y) {
		btn.setBounds(x, y, FIELD_WIDTH_HALF, FIELD_HEIGHT);
		// btn.setBounds(x, y, 130, 30);
		btn.setText(text);
	}
	
	private void addItemToList(DefaultListModel list, ArrayList<Attribute> dataList) {
		Logger.log("addItemToList: Adding array list of data to list");
		for (Attribute att : dataList){
			list.addElement(att.getPlayer());
		}
	}
	
	private void addItemToList(DefaultListModel list, String data) {
		Logger.log("addItemToList: Adding array list of data to list");
		list.addElement(data);
	}
	
	private void listRemoveSelectedItem(JList list) {
		if (list.getModel().getSize() == 0) {
			Logger.log("listRemoveSelectedItem: The list is empty");
			return;
		}
		DefaultListModel model = (DefaultListModel) list.getModel();
		int selectedIndex = list.getSelectedIndex();
		Logger.log("listRemoveSelectedItem: Removing item in index - '"
				+ selectedIndex + "' from list - '"+list.getName()+"'");
		model.remove(selectedIndex);
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
