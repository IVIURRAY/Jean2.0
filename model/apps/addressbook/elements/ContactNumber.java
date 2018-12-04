package com.model.apps.addressbook.elements;

import java.util.ArrayList;

import com.databaseEnum.DatabaseNames;
import com.form.FormBuilder;
import com.form.FormObject;
import com.form.IFormCustomBehaviour;
import com.form.intField.IntField;
import com.form.stringField.StringField;
import com.model.apps.addressbook.AddressBookRoot;
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

public class ContactNumber extends ModelObject implements Tabluable {

	IntegerProperty id;
	StringProperty title;
	StringProperty number;
	StringProperty country;

	public ContactNumber(int id, String title, String number, String country) {
		this.id = new SimpleIntegerProperty();
		this.title = new SimpleStringProperty();
		this.number = new SimpleStringProperty();
		this.country = new SimpleStringProperty();

		this.id.set(id);
		this.title.set(title);
		this.number.set(number);
		this.country.set(country);
		this.objectName="Number";
	}

	public ContactNumber() {
		this.id = new SimpleIntegerProperty();
		this.title = new SimpleStringProperty();
		this.number = new SimpleStringProperty();
		this.country = new SimpleStringProperty();

	}

	public IntegerProperty getIdProperty() {
		return id;
	}

	public StringProperty getTitleProperty() {
		return title;
	}

	public StringProperty getNumberProperty() {
		return number;
	}

	public StringProperty getCountryProperty() {
		return country;
	}

	@Override
	public int getId() {
		return id.get();
	}

	public String getTitle() {
		return title.get();
	}

	public String getNumber() {
		return number.get();
	}

	public String getCountry() {
		return country.get();
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public void setTitle(String title) {
		this.title.set(title);
	
	}

	public void setNumber(String number) {
		this.number.set(number);
	
	}

	public void setCountry(String country) {
		this.country.set(country);
	}

	@Override
	public ArrayList<Property> display1() {
		ArrayList<Property> properties = new ArrayList<Property>();
		columnNames = new ArrayList<String>();
		properties.add(id);
		properties.add(title);
		properties.add(number);
		properties.add(country);
		columnNames.add("ID");
		columnNames.add("Title");
		columnNames.add("Number");
		columnNames.add("Country");
		boolean [] req = {false, true, true, true};
		requiredFields = req;
		return properties;
	}

	@Override
	public String getFilterString() {
		return String.format("%s %s %s %s", id.get(), title.get(), number.get(), country.get());
	}

	@Override
	public String getPropertyResult(int number) {
		if (number == 0) {
			return getId() + "";
		}
		if (number == 1) {
			return getTitle() + "";
		}

		if (number == 2) {
			return getNumber() + "";
		}
		if (number == 3) {
			return getCountry() + "";
		}
		else {
			return null;
		}
	}



	@Override
	public ModelObject extractFromForm(ArrayList<String> results, FormBuilder formBuilder) {
		ContactNumber contactNumber = new ContactNumber();
		contactNumber.setTitle(results.get(1));
		contactNumber.setNumber(results.get(2));
		contactNumber.setCountry(results.get(3));
		ModelObject result = (ModelObject) contactNumber;
		return result;
		

	}

	@Override
	public void mapModelObjectToForm(ModelObject modelObject, ArrayList<FormObject> formObjects) {
		ContactNumber contactNumber = (ContactNumber) modelObject;
		IntField id = (IntField) formObjects.get(0);
		id.setInput(contactNumber.getId());
		id.setDisabled();
		StringField title = (StringField) formObjects.get(1);
		title.setInput(contactNumber.getTitle());
		StringField number = (StringField) formObjects.get(2);
		number.setInput(contactNumber.getNumber());
		StringField country = (StringField) formObjects.get(3);
		country.setInput(contactNumber.getCountry());
		
	}

	@Override
	public void modelObjectToSQL(ModelObject oldModelObject, Table3 table, FormBuilder formBuilder) {
		ModelObject submittedObject = formBuilder.getSubmittedObject();
		ContactNumber newContactAddress = (ContactNumber) submittedObject;
		String title = newContactAddress.getTitle();
		String number = newContactAddress.getNumber();
		String country = newContactAddress.getCountry();
		AddressBookRoot app = (AddressBookRoot)table.getAppHandler();
		String[] columnNames = {"FK_Contact_ContactNumbers","Title","Number","Country"};
		String[] values = {app.getSelectedContactId()+"" + "",title, number, country};	
		if(oldModelObject == null) {
			SQLHandler.insertInTable(DatabaseNames.JeanDatabase,"ContactNumbers", columnNames, values);
		}else {
			int id = oldModelObject.getId();
			SQLHandler.editTable(DatabaseNames.JeanDatabase,"ContactNumbers", columnNames, values, "idContactNumbers= " + id);
		}
		formBuilder.closeForm();
		table.refreshData();
	}
	
	@Override
	public void Delete(int idFromSelected, Table3 table) {
		System.out.println("Delete " +objectName);
		SQLHandler.deleteRowInTable(DatabaseNames.JeanDatabase,"ContactNumbers", "idContactNumbers = " + idFromSelected);
		table.refreshData();
	}

	@Override
	public IFormCustomBehaviour getCustomFormBuilderBehaviour() {
		// TODO Auto-generated method stub
		return null;
	}
}
