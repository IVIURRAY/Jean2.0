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
import com.table3.ColumnFactory;
import com.table3.ModelObject;
import com.table3.Table3;
import com.table3.TableRowFactory;
import com.table3.Tabluable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.GridPane;

public class ContactEmail extends ModelObject implements Tabluable {

	IntegerProperty id;
	StringProperty title;
	StringProperty email;
	
	
	
	public ContactEmail(int id, String title, String email) {
		
		super();
		this.id = new SimpleIntegerProperty();
		this.title = new SimpleStringProperty();
		this.email = new SimpleStringProperty();
		this.id.set(id);
		this.title.set(title);
		this.email.set(email);
		this.objectName="Email";
	}
	public ContactEmail() {
		super();
		this.id = new SimpleIntegerProperty();
		this.title = new SimpleStringProperty();
		this.email = new SimpleStringProperty();
	}

	
	public StringProperty getTitleProperty() {
		return title;
	}

	public void setTitle(String title) {
		this.title.set(title);
	}

	public String getTitle() {
		return title.get();
	}
	public String getEmail() {
		return email.get();
	}
	public StringProperty getEmailProperty() {
		return email;
	}

	public void setEmail(String email) {
		this.email.set(email);
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public IntegerProperty getIdProperty() {
		return id;
	}
	@Override
	public int getId() {
		return id.get();
	}
	@Override
	public ArrayList<IntegerProperty> getColumns() {
		ColumnFactory columnFactory = new ColumnFactory();
		columnFactory.setupColumns(display1(), null);
		return columnFactory.getWidth();
	}

	@Override
	public ArrayList<String> getColumnNames() {
		return columnNames;
	}

	@Override
	public GridPane buildTableContainer(ArrayList<IntegerProperty> columnWidths, Table3 table) {
		TableRowFactory tableRowFactory = new TableRowFactory(table, null);
		tableRowFactory.setupTable(display1(), columnWidths);
		return tableRowFactory.getRoot();
	}


	@Override
	public ArrayList<Property> display1() {
		ArrayList<Property> properties = new ArrayList<Property>();
		columnNames = new ArrayList<String>();
		properties.add(id);
		properties.add(title);
		properties.add(email);
		columnNames.add("ID");
		columnNames.add("Title");
		columnNames.add("Email");
		boolean [] req = {false, true, true};
		requiredFields = req;
		return properties;
	}
	

	@Override
	public String getFilterString() {
		return getTitle() + " " + getEmail();
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
			return getEmail() + "";
		}
		else {
			return null;
		}
	}

	@Override
	public ModelObject extractFromForm(ArrayList<String> results, FormBuilder formBuilder) {
		ContactEmail contactEmail = new ContactEmail();
		contactEmail.setTitle(results.get(1));
		contactEmail.setEmail(results.get(2));
		ModelObject result = (ModelObject) contactEmail;
		return result;
	}
	@Override
	public void mapModelObjectToForm(ModelObject modelObject, ArrayList<FormObject> formObjects) {
		ContactEmail contactEmail = (ContactEmail) modelObject;
		IntField id = (IntField) formObjects.get(0);
		id.setInput(contactEmail.getId());
		id.setDisabled();
		StringField title = (StringField) formObjects.get(1);
		title.setInput(contactEmail.getTitle());
		StringField email = (StringField) formObjects.get(2);
		email.setInput(contactEmail.getEmail());
		
	}
	 //cant get it through the table because its this own table...
	
	@Override
	public void modelObjectToSQL(ModelObject oldModelObject, Table3 table, FormBuilder formBuilder) {
		ModelObject submittedObject = formBuilder.getSubmittedObject();
		ContactEmail newContactEmail = (ContactEmail) submittedObject;
		String title = newContactEmail.getTitle();
		String email = newContactEmail.getEmail();
		AddressBookRoot app = (AddressBookRoot) table.getAppHandler();
		String[] columnNames = {"FK_Contacts_ContactEmails","Title","EmailAddress"};
		String[] values = { ""+app.getSelectedContactId(), title, email};
		if(oldModelObject == null) {
			SQLHandler.insertInTable(DatabaseNames.JeanDatabase,"ContactEmails", columnNames, values);
		}else {
			int id = oldModelObject.getId();
			SQLHandler.editTable(DatabaseNames.JeanDatabase,"ContactEmails", columnNames, values, "idContactEmails = " + id);
		}

		formBuilder.closeForm();
		table.refreshData();
	}
		
	
	@Override
	public void Delete(int idFromSelected, Table3 table) {
		System.out.println("Delete " +objectName);
		SQLHandler.deleteRowInTable(DatabaseNames.JeanDatabase,"ContactEmails", "idContactEmails = " + idFromSelected);
		table.refreshData();
		
	}
	@Override
	public IFormCustomBehaviour getCustomFormBuilderBehaviour() {
		// TODO Auto-generated method stub
		return null;
	}

}
