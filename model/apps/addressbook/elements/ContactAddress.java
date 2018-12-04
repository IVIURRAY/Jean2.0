package com.model.apps.addressbook.elements;

import java.util.ArrayList;

import com.databaseEnum.DatabaseNames;
import com.form.FormBuilder;
import com.form.FormObject;
import com.form.IFormCustomBehaviour;
import com.form.intField.IntField;
import com.form.stringField.StringField;
import com.model.AppHandler;
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

public class ContactAddress extends ModelObject implements Tabluable {
	IntegerProperty id;
	StringProperty title;
	StringProperty line1;
	StringProperty line2;
	StringProperty city;
	StringProperty county;
	StringProperty postcode;
	StringProperty country;
	
	public ContactAddress(int id,String title, String line1, String line2, String city, String county, String postcode,
			String country) {
		this.id = new SimpleIntegerProperty();
		this.title = new SimpleStringProperty();
		this.line1 = new SimpleStringProperty();
		this.line2 = new SimpleStringProperty();
		this.city = new SimpleStringProperty();
		this.county = new SimpleStringProperty();
		this.postcode = new SimpleStringProperty();
		this.country= new SimpleStringProperty();
		
		this.id.set(id);
		this.title.set(title);
		this.line1.set(line1);
		this.line2.set(line2);
		this.city.set(city);
		this.county.set(county);
		this.postcode.set(postcode);
		this.country.set(country);
		this.objectName="Address";
	}
	public ContactAddress() {
		this.id = new SimpleIntegerProperty();
		this.title = new SimpleStringProperty();
		this.line1 = new SimpleStringProperty();
		this.line2 = new SimpleStringProperty();
		this.city = new SimpleStringProperty();
		this.county = new SimpleStringProperty();
		this.postcode = new SimpleStringProperty();
		this.country= new SimpleStringProperty();
	}
	
	public StringProperty getTitleProperty() {
		return title;
	}
	public IntegerProperty getIdProperty() {
		return id;
	}
	public StringProperty getLine1Proprety() {
		return line1;
	}

	public StringProperty getLine2Proprety() {
		return line2;
	}

	public StringProperty getCityProprety() {
		return city;
	}

	public StringProperty getCountyProprety() {
		return county;
	}
	public StringProperty getPostcodeProprety() {
		return postcode;
	}
	
	public StringProperty getCountryProprety() {
		return country;
	}
	public String getTitle() {
		return title.get();
	}
	
	public String getLine1() {
		return line1.get();
	}

	public String getLine2() {
		return line2.get();
	}

	public String getCity() {
		return city.get();
	}

	public String getCounty() {
		return county.get();
	}
	public String getPostcode() {
		return postcode.get();
	}
	
	public String getCountry() {
		return country.get();
	}
	@Override
	public int getId() {
		return id.get();
	}
	public void setTitle(String string) {
		title.set(string);
	}
	public void setLine1(String string) {
		 line1.set(string);
	}

	public void setLine2(String string) {
		 line2.set(string);
	}

	public void setCity(String string) {
		 city.set(string);
	}

	public void setCounty(String string) {
		 county.set(string);
	}
	public void setPostcode(String string) {
		 postcode.set(string);
	}
	
	public void setCountry(String string) {
		 country.set(string);
	}
	public void setId(int i) {
		 id.set(i);
	}
	
	@Override
	public ArrayList<Property> display1() {
		ArrayList<Property> properties = new ArrayList<Property>();
		columnNames = new ArrayList<String>();
		properties.add(id);
		properties.add(title);
		properties.add(line1);
		properties.add(line2);
		properties.add(city);
		properties.add(county);
		properties.add(postcode);
		properties.add(country);
		columnNames.add("ID");
		columnNames.add("Title");
		columnNames.add("Line 1");
		columnNames.add("Line 2");
		columnNames.add("City");
		columnNames.add("County");
		columnNames.add("Postcode");
		columnNames.add("Country");
		boolean [] req = {false, true, false, false,false, false,false, false};
		requiredFields = req;
		return properties;
	}

	@Override
	public String getFilterString() {
		return String.format("%s %s %s %s %s %s %s %s", id.get(),title.get(), line1.get(), line2.get(), city.get(), county.get(), postcode.get(), country.get());
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
			return getLine1() + "";
		}

		if (number == 3) {
			return getLine2() + "";
		}
		if (number == 4) {
			return getCity() + "";
		}
		if (number == 5) {
			return getCountry() + "";
		}

		if (number == 6) {
			return getPostcode() + "";
		}
		if (number == 7) {
			return getCountry() + "";
		}else {
			return null;
		}
	}


	@Override
	public ModelObject extractFromForm(ArrayList<String> results, FormBuilder formBuilder) {
		ContactAddress contactAddress = new ContactAddress();
		contactAddress.setTitle(results.get(1));
		contactAddress.setLine1(results.get(2));
		contactAddress.setLine2(results.get(3));
		contactAddress.setCity(results.get(4));
		contactAddress.setCounty(results.get(5));
		contactAddress.setPostcode(results.get(6));
		contactAddress.setCountry(results.get(7));
		
		
		ModelObject result = (ModelObject) contactAddress;
		return result;
	}
	@Override
	public void mapModelObjectToForm(ModelObject modelObject, ArrayList<FormObject> formObjects) {
		ContactAddress contactAddress = (ContactAddress) modelObject;
		IntField id = (IntField) formObjects.get(0);
		id.setInput(contactAddress.getId());
		id.setDisabled();
		StringField title = (StringField) formObjects.get(1);
		title.setInput(contactAddress.getTitle());
		StringField line1 = (StringField) formObjects.get(2);
		line1.setInput(contactAddress.getLine1());
		StringField line2 = (StringField) formObjects.get(3);
		line2.setInput(contactAddress.getLine2());
		StringField city = (StringField) formObjects.get(4);
		city.setInput(contactAddress.getCity());
		StringField county = (StringField) formObjects.get(5);
		county.setInput(contactAddress.getCounty());
		StringField postcode = (StringField) formObjects.get(6);
		postcode.setInput(contactAddress.getPostcode());
		StringField country = (StringField) formObjects.get(7);
		country.setInput(contactAddress.getCountry());
		
	}
	@Override
	public void modelObjectToSQL(ModelObject oldModelObject, Table3 table, FormBuilder formBuilder) {
		ModelObject submittedObject = formBuilder.getSubmittedObject();
		ContactAddress newContactAddress = (ContactAddress) submittedObject;
		String title = newContactAddress.getTitle();
		String line1 = newContactAddress.getLine1();
		String line2 = newContactAddress.getLine2();
		String city = newContactAddress.getCity();
		String county = newContactAddress.getCounty();
		String postcode = newContactAddress.getPostcode();
		String country = newContactAddress.getCountry();
		AddressBookRoot app = (AddressBookRoot)table.getAppHandler();
		
		String[] columnNames = {"FK_Contact_ContactAddress","Title","Line1", "Line2", "City", "County", "PostCode", "Country"};
		String[] values = { ""+app.getSelectedContactId(),title, line1, line2, city, county, postcode, country};
		if(oldModelObject == null) {
			SQLHandler.insertInTable(DatabaseNames.JeanDatabase,"ContactAddress", columnNames, values);
		}else {
			int id = oldModelObject.getId();
			SQLHandler.editTable(DatabaseNames.JeanDatabase,"ContactAddress", columnNames, values, "idContactAddress = " + id);
		}

		formBuilder.closeForm();
		table.refreshData();
		
	}
	@Override
	public void Delete(int idFromSelected, Table3 table) {
		System.out.println("Delete " +objectName);
		SQLHandler.deleteRowInTable(DatabaseNames.JeanDatabase, "ContactAddress", "idContactAddress = " + idFromSelected);
		table.refreshData();
		
		
	}
	@Override
	public IFormCustomBehaviour getCustomFormBuilderBehaviour() {
		// TODO Auto-generated method stub
		return null;
	}
}
