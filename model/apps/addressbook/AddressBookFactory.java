package com.model.apps.addressbook;

import java.util.ArrayList;

import javax.swing.JTable;

import com.model.apps.addressbook.elements.Contact;
import com.model.apps.addressbook.elements.ContactAddress;
import com.model.apps.addressbook.elements.ContactEmail;
import com.model.apps.addressbook.elements.ContactNumber;
import com.model.sql.SQLHandler;
import com.model.user.User;
import com.table3.ModelObject;
import com.table3.Tabluable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AddressBookFactory {
	
	 static public ObservableList<ModelObject> getContacts(User user) {
		String sql = String.format("SELECT * FROM Contact WHERE FK_Users_Contact = %d", user.getId());
		JTable tbl = SQLHandler.getTable(sql,"Jean");
		ObservableList<ModelObject> contacts = FXCollections.observableArrayList();
		for (int i = 0; i < tbl.getRowCount(); i++) {
			int id = (int) tbl.getModel().getValueAt(i, tbl.getColumn("idContact").getModelIndex());
			String firstname = (String) tbl.getModel().getValueAt(i, tbl.getColumn("FirstName").getModelIndex());
			String familyName = (String) tbl.getModel().getValueAt(i, tbl.getColumn("FamilyName").getModelIndex());
			Contact contact = new Contact(id, firstname, familyName);
			contacts.add(contact);
		}
		return contacts;
	}
	 
	 static public ObservableList<ModelObject> getEmails(int contactId) {
		String sql = String.format("SELECT * FROM ContactEmails WHERE FK_Contacts_ContactEmails = %d", contactId);
		JTable tbl = SQLHandler.getTable(sql,"Jean");
		ObservableList<ModelObject> contacts = FXCollections.observableArrayList();
		for (int i = 0; i < tbl.getRowCount(); i++) {
			int id = (int) tbl.getModel().getValueAt(i, tbl.getColumn("idContactEmails").getModelIndex());
			String title = (String) tbl.getModel().getValueAt(i, tbl.getColumn("Title").getModelIndex());
			String email = (String) tbl.getModel().getValueAt(i, tbl.getColumn("EmailAddress").getModelIndex());
			ContactEmail contactEmail = new ContactEmail(id, title, email);
			contacts.add(contactEmail);
		}
		return contacts;
	}
	 
	 static public ObservableList<ModelObject> getAddress(int contactId) {
			String sql = String.format("SELECT * FROM ContactAddress WHERE FK_Contact_ContactAddress = %d", contactId);
			JTable tbl = SQLHandler.getTable(sql,"Jean");
			ObservableList<ModelObject> contacts = FXCollections.observableArrayList();
			for (int i = 0; i < tbl.getRowCount(); i++) {
				int id = (int) tbl.getModel().getValueAt(i, tbl.getColumn("idContactAddress").getModelIndex());
				String title = (String) tbl.getModel().getValueAt(i, tbl.getColumn("Title").getModelIndex());
				String line1 = (String) tbl.getModel().getValueAt(i, tbl.getColumn("Line1").getModelIndex());
				String line2 = (String) tbl.getModel().getValueAt(i, tbl.getColumn("Line2").getModelIndex());
				String city = (String) tbl.getModel().getValueAt(i, tbl.getColumn("City").getModelIndex());
				String county = (String) tbl.getModel().getValueAt(i, tbl.getColumn("County").getModelIndex());
				String postcode = (String) tbl.getModel().getValueAt(i, tbl.getColumn("PostCode").getModelIndex());
				String country = (String) tbl.getModel().getValueAt(i, tbl.getColumn("Country").getModelIndex());
				ContactAddress contact = new ContactAddress(id,title, line1,line2, city, county, postcode,country);
				contacts.add(contact);
			}
			return contacts;
		}
		 
	 static public ObservableList<ModelObject> getNumbers(int contactId) {
			String sql = String.format("SELECT * FROM ContactNumbers WHERE FK_Contact_ContactNumbers = %d", contactId);
			JTable tbl = SQLHandler.getTable(sql,"Jean");
			ObservableList<ModelObject> contacts = FXCollections.observableArrayList();
			for (int i = 0; i < tbl.getRowCount(); i++) {
				int id = (int) tbl.getModel().getValueAt(i, tbl.getColumn("idContactNumbers").getModelIndex());
				String title = (String) tbl.getModel().getValueAt(i, tbl.getColumn("Title").getModelIndex());
				String number = (String) tbl.getModel().getValueAt(i, tbl.getColumn("Number").getModelIndex());
				String country = (String) tbl.getModel().getValueAt(i, tbl.getColumn("Country").getModelIndex());
				ContactNumber contact = new ContactNumber(id, title,number, country);
				contacts.add(contact);
			}
			return contacts;
		}
		 
	 static public ArrayList<Tabluable> ArrayListToTabluable(ObservableList<ModelObject> list) {
		ArrayList<Tabluable> tableInfo = new ArrayList<Tabluable>();
		for (ModelObject o : list) {
			Tabluable t = (Tabluable) o;
			tableInfo.add(t);
		}
		return tableInfo;
	}
}
