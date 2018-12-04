package com.model.apps.addressbook.elements;

import java.util.ArrayList;

import com.databaseEnum.DatabaseNames;
import com.form.FormBuilder;
import com.form.FormObject;
import com.form.IFormCustomBehaviour;
import com.form.intField.IntField;
import com.form.stringField.StringField;
import com.model.sql.SQLHandler;
import com.model.user.UserInfo;
import com.table3.ModelObject;
import com.table3.Table3;
import com.table3.Tabluable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Contact extends ModelObject implements Tabluable {
	IntegerProperty id;
	StringProperty firstName;
	StringProperty familyName;
	

	public Contact(int id, String firstName, String familyName) {
		super();
		this.id = new SimpleIntegerProperty();
		this.firstName = new SimpleStringProperty();
		this.familyName = new SimpleStringProperty();
		this.id.set(id);
		this.firstName.set(firstName);
		this.familyName.set(familyName);
		this.objectName="Contact";
	}

	public Contact() {
		super();
		this.id = new SimpleIntegerProperty();
		this.firstName = new SimpleStringProperty();
		this.familyName = new SimpleStringProperty();
		this.objectName="Contact";
	}

	public void setId(int value) {
		id.set(value);
	}

	public int getId() {
		return id.get();
	}

	public IntegerProperty getIdProperty() {
		return id;
	}

	public void setFirstName(String value) {
		firstName.set(value);
	}

	public String getFirstName() {
		return firstName.get();
	}

	public StringProperty getFirstNameProperty() {
		return firstName;
	}

	public void setFamilyName(String value) {
		familyName.set(value);
	}

	public String getFamilyName() {
		return familyName.get();
	}

	public StringProperty getFamilyNameProperty() {
		return familyName;
	}

	public ArrayList<Property> display1() {
		ArrayList<Property> properties = new ArrayList<Property>();
		columnNames = new ArrayList<String>();
		properties.add(id);
		properties.add(firstName);
		properties.add(familyName);
		columnNames.add("ID");
		columnNames.add("First Name");
		columnNames.add("Family Name");
		boolean [] req = {false, true, true};
		requiredFields = req;
		return properties;
	}

	
	@Override
	public String getFilterString() {
		return (getFirstName() + " " + getFamilyName() + getId());
	}
	
	public String getPropertyResult(int number) {
		if (number == 0) {
			return getId() + "";
		}
		if (number == 1) {
			return getFirstName() + "";
		}
		if (number == 2) {
			return getFamilyName() + "";
		} else {
			return null;
		}

	}
	
	@Override
	public ModelObject extractFromForm(ArrayList<String> results, FormBuilder formBuilder) {
		Contact contact = new Contact();
		contact.setFirstName(results.get(1));
		contact.setFamilyName(results.get(2));
		ModelObject result = (ModelObject) contact;
		return result;
	}
	
	@Override
	public void mapModelObjectToForm(ModelObject modelObject, ArrayList<FormObject> formObjects) {
		Contact contact = (Contact) modelObject;
		IntField id = (IntField) formObjects.get(0);
		id.setInput(contact.getId());
		id.setDisabled();
		StringField firstName = (StringField) formObjects.get(1);
		firstName.setInput(contact.getFirstName());
		StringField familyName = (StringField) formObjects.get(2);
		familyName.setInput(contact.getFamilyName());
	}
	
	@Override
	public void modelObjectToSQL(ModelObject oldModelObject, Table3 table, FormBuilder formBuilder) {

		ModelObject submittedObject = formBuilder.getSubmittedObject();
		Contact contact = (Contact) oldModelObject;
		Contact newContact = (Contact) submittedObject;
		String firstName = newContact.getFirstName();
		String familyName = newContact.getFamilyName();
		String[] columnNames = {"FK_Users_Contact","FirstName","FamilyName"};
		String[] values = {UserInfo.getUser().getId() + "", firstName, familyName};
		if(oldModelObject == null) {
			SQLHandler.insertInTable(DatabaseNames.JeanDatabase,"Contact", columnNames, values);
		}else {
			int id = contact.getId();
			SQLHandler.editTable(DatabaseNames.JeanDatabase,"Contact", columnNames, values, "idcontact = " + id);
		}
		formBuilder.closeForm();
		table.refreshData();
	}
	
	@Override
	public void Delete(int idFromSelected, Table3 table) {
		System.out.println("Delete " +objectName);
		SQLHandler.deleteRowInTable(DatabaseNames.JeanDatabase,"Contact", "idcontact = " + idFromSelected);
		table.refreshData();
	}

	@Override
	public IFormCustomBehaviour getCustomFormBuilderBehaviour() {
		// TODO Auto-generated method stub
		return null;
	}
}
