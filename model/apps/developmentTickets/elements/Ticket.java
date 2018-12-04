package com.model.apps.developmentTickets.elements;

import java.time.LocalDate;
import java.util.ArrayList;

import com.databaseEnum.DatabaseNames;
import com.form.FormBuilder;
import com.form.FormObject;
import com.form.IFormCustomBehaviour;
import com.form.dateField.DateField;
import com.form.intField.IntField;
import com.form.stringField.StringField;
import com.model.sql.SQLHandler;
import com.table3.DateHelper;
import com.table3.DateProperty;
import com.table3.ModelObject;
import com.table3.Table3;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Ticket extends ModelObject{

	public Ticket() {
		this.id = new SimpleIntegerProperty();
		this.title = new SimpleStringProperty();
		this.type = new SimpleStringProperty();
		this.objective = new SimpleStringProperty();
		this.value = new SimpleIntegerProperty();
		this.dateCreated = new DateProperty();
		this.dateCompleted = new DateProperty();
	}
	
	public Ticket(int id, String title, String type, String objective, int value, LocalDate created, LocalDate completed) {
		this.id = new SimpleIntegerProperty();
		this.title = new SimpleStringProperty();
		this.type = new SimpleStringProperty();
		this.objective = new SimpleStringProperty();
		this.value = new SimpleIntegerProperty();
		this.dateCreated = new DateProperty();
		this.dateCompleted = new DateProperty();
		
		this.id.set(id);
		this.title.set(title);
		this.type.set(type);
		this.objective.set(objective);
		this.value.set(value);
		setDateCreated(created);
		setDateCompleted(completed);
	}
	
	IntegerProperty id;
	StringProperty title;
	StringProperty type;
	StringProperty objective;
	IntegerProperty value;
	DateProperty dateCreated;
	DateProperty dateCompleted;
	
	public IntegerProperty getIdProperty(){return id;}
	public StringProperty getTitleProperty() {return title;}
	public StringProperty getTypeProperty() {return type;}
	public StringProperty getObjectiveProperty() {return objective;}
	public IntegerProperty getValueProperty(){return value;}
	public DateProperty getDateCreatedProperty() {return dateCreated;}
	public DateProperty getDateCompletedProperty() {return dateCompleted;}

	public int getId() {return id.get();}
	public String getTitle() {return title.get();}
	public String getType() {return type.get();}
	public String getObjective() {return objective.get();}
	public int getValue() {return value.get();}
	public LocalDate getDateCreated() {return dateCreated.getValue();}
	public LocalDate getDateCompleted() {return dateCompleted.getValue();}

	public void setId(int id) {this.id.set(id);}
	public void setTitle(String title) {this.title.set(title);}
	public void setType(String type) {this.type.set(type);}
	public void setObjective(String objective) {this.objective.set(objective);}
	public void setValue(int value) {this.value.set(value);}
	public void setDateCreated(LocalDate created) {this.dateCreated.setValue(created);}
	public void setDateCompleted(LocalDate completed) {this.dateCompleted.setValue(completed);}

	@Override
	public ArrayList<Property> display1() {
		ArrayList<Property> properties = new ArrayList<Property>();
		columnNames = new ArrayList<String>();
		properties.add(id);
		properties.add(title);
		properties.add(type);
		properties.add(objective);
		properties.add(value);
		properties.add(dateCreated);
		properties.add(dateCompleted);
		columnNames.add("ID");
		columnNames.add("Title");
		columnNames.add("Type");
		columnNames.add("Objective");
		columnNames.add("Value");
		columnNames.add("Date Created");
		columnNames.add("Date Completed");
		boolean [] req = {false, true, false, true, true, true, false};
		requiredFields = req;
		return properties;
	}
	
	@Override
	public String getFilterString() {
		return (getTitle() + " " + getType() +" " + getId());
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
			return getType() + "";
		}
		if (number == 3) {
			return getObjective() + "";
		}
		if (number == 4) {
			return getValue() + "";
		}
		if (number == 5) {
			return getDateCreated() + "";
		}	if (number == 6) {
			return getDateCompleted() + "";
		} else {
			return null;
	}
	}
	
	@Override
	public ModelObject extractFromForm(ArrayList<String> results, FormBuilder formBuilder) {
		Ticket ticket = new Ticket();
		ticket.setTitle(results.get(1));
		ticket.setType(results.get(2));
		ticket.setObjective(results.get(3));
		ticket.setValue(Integer.parseInt(results.get(4)));
		ticket.setDateCreated(LocalDate.parse(results.get(5), DateHelper.getDateFormatter()));
		System.out.println(results.get(6));
		if(!results.get(6).equals("")) {
		ticket.setDateCreated(LocalDate.parse(results.get(6), DateHelper.getDateFormatter()));}
		ModelObject result = (ModelObject) ticket;
		return result;
	}
	
	@Override
	public void mapModelObjectToForm(ModelObject modelObject, ArrayList<FormObject> formObjects) {
		Ticket ticket = (Ticket) modelObject;
		IntField id = (IntField) formObjects.get(0);
		id.setInput(ticket.getId());
		id.setDisabled();
		StringField title = (StringField) formObjects.get(1);
		title.setInput(ticket.getTitle());
		StringField type = (StringField) formObjects.get(2);
		type.setInput(ticket.getType());
		StringField objective = (StringField) formObjects.get(3);
		objective.setInput(ticket.getObjective());
		IntField value = (IntField) formObjects.get(4);
		value.setInput(ticket.getValue());
		DateField created = (DateField) formObjects.get(5);
		created.setInput(ticket.getDateCreated());
		DateField completed = (DateField) formObjects.get(6);
		completed.setInput(ticket.getDateCompleted());

	}
	
	@Override
	public void modelObjectToSQL(ModelObject oldModelObject, Table3 table, FormBuilder formBuilder) {
		ModelObject submittedObject = formBuilder.getSubmittedObject();
		Ticket ticket = (Ticket) oldModelObject;
		Ticket newTicket= (Ticket) submittedObject;
		String title = newTicket.getTitle();
		String type = newTicket.getType();
		String objective = newTicket.getObjective();
		int value = newTicket.getValue();
		LocalDate created = newTicket.getDateCreated();
		LocalDate completed = newTicket.getDateCompleted();
		String createdS = created.format(DateHelper.getDateFormatter());
		String completedS = null;
		if(completed != null) {
		completedS = completed.format(DateHelper.getDateFormatter());
		}
		String[] columnNames = {"Title","Type","Objective","Value","Date_Created","Date_Completed"};
		
		
		String[] values = {title, type,objective,value+"",createdS,completedS};
		if(oldModelObject == null) {
			SQLHandler.insertInTable(DatabaseNames.JambDevelopmentDatabase,"Active_Projects", columnNames, values);
		}else {
			int id = ticket.getId();
			SQLHandler.editTable(DatabaseNames.JambDevelopmentDatabase,"Active_Projects", columnNames, values, "idActive_Projects = " + id);
		}
		formBuilder.closeForm();
		table.refreshData();
		
	}
	
	@Override
	public IFormCustomBehaviour getCustomFormBuilderBehaviour() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void Delete(int idFromSelected, Table3 table) {
		System.out.println("Delete " + objectName);
		SQLHandler.deleteRowInTable(DatabaseNames.JambDevelopmentDatabase,"Active_Projects", "idActive_Projects = " + idFromSelected);
		table.refreshData();
		
	} 
}
