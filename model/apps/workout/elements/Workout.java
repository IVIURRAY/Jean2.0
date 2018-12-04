package com.model.apps.workout.elements;

import java.time.LocalDate;
import java.util.ArrayList;

import com.databaseEnum.DatabaseNames;
import com.form.FormBuilder;
import com.form.FormObject;
import com.form.IFormCustomBehaviour;
import com.form.dateField.DateField;
import com.form.intField.IntField;
import com.form.stringField.StringField;
import com.model.apps.addressbook.AddressBookRoot;
import com.model.apps.addressbook.elements.Contact;
import com.model.apps.addressbook.elements.ContactEmail;
import com.model.sql.SQLHandler;
import com.model.user.UserInfo;
import com.table3.DateHelper;
import com.table3.DateProperty;
import com.table3.ModelObject;
import com.table3.Table3;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Workout extends ModelObject {

	IntegerProperty id;
	StringProperty workoutName;
	DateProperty dateAdded;
	
	public Workout(int id, String workoutName, LocalDate dateAdded ) {
		this.id = new SimpleIntegerProperty();
		this.workoutName = new SimpleStringProperty();
		this.dateAdded = new DateProperty();
			
		this.id.set(id);
		this.workoutName.set(workoutName);
		this.dateAdded.setValue(dateAdded);
		
	}
	
	public Workout() {
		this.id = new SimpleIntegerProperty();
		this.workoutName = new SimpleStringProperty();
		this.dateAdded = new DateProperty();}
	
	public IntegerProperty getIdProperty() {
		return id;
	}
	public StringProperty getWorkoutNameProperty() {
		return workoutName;
	}
	public DateProperty getDateAddedProperty() {
		return dateAdded;
	}
	public void setId(int id) {
		this.id.set(id);
	}
	public void setWorkoutName(String workoutName) {
		this.workoutName.set(workoutName);
	}
	public void setDateAdded(LocalDate dateAdded) {
		this.dateAdded.setValue(dateAdded);
	}
	
	
	@Override
	public int getId() {
		return id.get();
	}
	public String getWorkoutName() {
	return workoutName.get();
	}
	public LocalDate getDateAdded() {
		return dateAdded.getValue();
	}
	
	@Override
	public void Delete(int idFromSelected, Table3 table) {
		System.out.println("Delete " +objectName);
		SQLHandler.deleteRowInTable(DatabaseNames.JeanDatabase,"Workouts", "idWorkouts = " + idFromSelected);
		table.refreshData();
	}

	@Override
	public ArrayList<Property> display1() {
		ArrayList<Property> properties = new ArrayList<Property>();
		columnNames = new ArrayList<String>();
		
		properties.add(id);
		properties.add(workoutName);
		properties.add(dateAdded);
		
		columnNames.add("ID");
		columnNames.add("Name");
		columnNames.add("Date Added");
		boolean [] req = {false, true, true};
		requiredFields = req;
		return properties;
	}

	@Override
	public String getFilterString() {
		return workoutName.get() + " " + dateAdded.toString();
	}

	@Override
	public ModelObject extractFromForm(ArrayList<String> results, FormBuilder formBuilder) {
		Workout workout = new Workout();
		workout.setWorkoutName(results.get(1));
		if(!results.get(2).equals("")) {
		workout.setDateAdded(LocalDate.parse(results.get(2), DateHelper.getDateFormatter()));
		}
		ModelObject result = (ModelObject) workout;
		return result;
	}

	@Override
	public void mapModelObjectToForm(ModelObject modelObject, ArrayList<FormObject> formObjects) {
		Workout workout = (Workout) modelObject;
		IntField id = (IntField) formObjects.get(0);
		id.setInput(workout.getId());
		id.setDisabled();
		StringField workoutName = (StringField) formObjects.get(1);
		workoutName.setInput(workout.getWorkoutName());
		DateField dateAdded = (DateField) formObjects.get(2);
		dateAdded.setInput(workout.getDateAdded());
		
	}

	@Override
	public void modelObjectToSQL(ModelObject oldModelObject, Table3 table, FormBuilder formBuilder) {
		ModelObject submittedObject = formBuilder.getSubmittedObject();
		Workout newWorkout = (Workout) submittedObject;
		String workoutName = newWorkout.getWorkoutName();
		String dateAdded = newWorkout.getDateAdded().toString();
		String[] columnNames = {"FK_Users_Workouts","Name","Date"};
		String[] values = { UserInfo.getUser().getId()+"", workoutName, dateAdded};
		if(oldModelObject == null) {
			SQLHandler.insertInTable(DatabaseNames.JeanDatabase,"Workouts", columnNames, values);
		}else {
			int id = oldModelObject.getId();
			SQLHandler.editTable(DatabaseNames.JeanDatabase,"Workouts", columnNames, values, "idWorkouts = " + id);
		}
		formBuilder.closeForm();
		table.refreshData();
		
	}

	@Override
	public String getPropertyResult(int number) {
		
		if (number == 0) {
			return getId() + "";
		}
		
		if (number == 1) {
			return getWorkoutName() + "";
		}
		
		if (number == 2) {
			return getDateAdded() + "";
		}
		
		else {
			return null;
		}

	}

	@Override
	public IFormCustomBehaviour getCustomFormBuilderBehaviour() {
		return null;
	}

}
