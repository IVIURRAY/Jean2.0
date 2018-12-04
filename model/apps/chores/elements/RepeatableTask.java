package com.model.apps.chores.elements;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

import com.databaseEnum.DatabaseNames;
import com.form.FormBuilder;
import com.form.FormObject;
import com.form.IFormCustomBehaviour;
import com.form.dateField.DateField;
import com.form.doubleField.DoubleField;
import com.form.stringField.StringField;
import com.model.sql.SQLHandler;
import com.model.user.UserInfo;
import com.table3.DateHelper;
import com.table3.DateProperty;
import com.table3.ModelObject;
import com.table3.Table3;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RepeatableTask extends ModelObject{
	IntegerProperty id;
	IntegerProperty userId;
	StringProperty name;
	DateProperty dateAdded;
	DoubleProperty frequency;
	DateProperty latestDate;
	StringProperty health;
	
	public RepeatableTask(int id, int userId, String name, LocalDate dateAdded, Double frequency, LocalDate lastestDate2) {
		 this.id = new SimpleIntegerProperty();
		 this.userId = new SimpleIntegerProperty();
		 this.name = new SimpleStringProperty();
		 this.dateAdded = new DateProperty();
		 this.frequency = new SimpleDoubleProperty();
		 this.latestDate = new DateProperty();
		 this.health = new SimpleStringProperty();
		 
		 this.id.set(id);
		 this.userId.set(userId);
		 this.name.set(name);
		 this.dateAdded.setValue(dateAdded);
		 this.frequency.set(frequency);
		 this.latestDate.setValue(lastestDate2);
		 if(lastestDate2 !=null) 
		 	{this.health.set(calculateHealth(frequency, lastestDate2));}
		 else {
			 
		 }
		 

	}
	private String calculateHealth(Double days, LocalDate lastestDate) {
		LocalDate now = LocalDate.now();
		long longDays = days.longValue();
		LocalDate deadline = lastestDate.plusDays(longDays);
		System.out.println(String.format("%s %s ", now.toString(), lastestDate.toString()));
		Period period = Period.between ( now , deadline );
		Double daysElapsed = (double) period.getDays ();
		
		double health = daysElapsed/longDays*100;
		String strDouble = String.format("%.0f",health);
		//System.out.println(String.format("%s/%s=%s",daysElapsed, longDays, health));
		return strDouble +"%";
		

	}
	public RepeatableTask() {
		 this.id = new SimpleIntegerProperty();
		 this.userId = new SimpleIntegerProperty();
		 this.name = new SimpleStringProperty();
		 this.dateAdded = new DateProperty();
		 this.frequency = new SimpleDoubleProperty();
		 this.latestDate = new DateProperty();
		 this.health = new SimpleStringProperty();

	}
	

	public IntegerProperty getIdProperty(){return id;}
	public IntegerProperty getUserIdProperty(){return userId;}
	public StringProperty getNameProperty() {return name;}
	public DateProperty getDateAddedProperty() {return dateAdded;}
	public DoubleProperty getFrequencyProperty() {return frequency;}
	public DateProperty getLastestDateProperty() {return latestDate;}
	public StringProperty getHealthProperty() { return health;}
	
	@Override
	public int getId() {return id.get();}
	public int getUserId() {return userId.get();}
	public String getName() {return name.get();}
	public LocalDate getDateAdded() {return dateAdded.getValue();}
	public double getFrequency() {return frequency.get();}
	public LocalDate getLatestDate() {return latestDate.getValue();}
	public String getHealth() {return health.get();}
	
	public void setId(int id) {this.id.set(id);;}
	public void setUserId(int userId) { this.userId.set(userId);}
	public void setName(String name) { this.name.set(name);}
	public void setDateAdded(LocalDate dateAdded) { this.dateAdded.setValue(dateAdded);}
	public void setFrequency(double frequency ) { this.frequency.set(frequency);}
	public void setLatestDate(LocalDate latestdate) {this.latestDate.setValue(latestdate);}
	public void setHealth(String health) {this.health.set(health);}

	@Override
	public void Delete(int idFromSelected, Table3 table) {
		System.out.println("Delete " +objectName);
		SQLHandler.deleteRowInTable(DatabaseNames.JeanDatabase,"Chores", "idChores= " + idFromSelected);
		table.refreshData();
		
	}


	@Override
	public ArrayList<Property> display1() {
		
		ArrayList<Property> properties = new ArrayList<Property>();
		columnNames = new ArrayList<String>();
		properties.add(id);
		properties.add(name);
		properties.add(dateAdded);
		properties.add(frequency);
		properties.add(latestDate);
		properties.add(health);
		columnNames.add("ID");
		columnNames.add("Name");
		columnNames.add("Date Added");
		columnNames.add("Frequency");
		columnNames.add("Latest Date");
		columnNames.add("Health");
		boolean [] req = {false, true, true, true,true, true};
		requiredFields = req;
		return properties;
	}

	@Override
	public String getFilterString() {
			return String.format("%s %s", id.get(),name.get());
	}

	@Override
	public ModelObject extractFromForm(ArrayList<String> results, FormBuilder formBuilder) {
		RepeatableTask repeatableTask = new RepeatableTask();

		repeatableTask.setName(results.get(1));
		repeatableTask.setDateAdded(LocalDate.parse(results.get(2), DateHelper.getDateFormatter()));
		repeatableTask.setFrequency(Double.parseDouble(results.get(3)));
		
		ModelObject result = (ModelObject) repeatableTask;
		return result;
	}

	@Override
	public void mapModelObjectToForm(ModelObject modelObject, ArrayList<FormObject> formObjects) {
		RepeatableTask repeatableTask = (RepeatableTask) modelObject;
		StringField name = (StringField) formObjects.get(1);
		name.setInput(repeatableTask.getName());
		DateField dateAdded = (DateField) formObjects.get(2);
		dateAdded.setInput(repeatableTask.getDateAdded());
		dateAdded.setDisabled();
		DoubleField frequency = (DoubleField) formObjects.get(3);
		frequency.setInput(repeatableTask.getFrequency());
	}

	@Override
	public void modelObjectToSQL(ModelObject oldModelObject, Table3 table, FormBuilder formBuilder) {
		ModelObject submittedObject = formBuilder.getSubmittedObject();
		RepeatableTask newrepeatableTask = (RepeatableTask) submittedObject;
	
	
		String name = newrepeatableTask.getName();
		String dateAdded = newrepeatableTask.getDateAdded().toString();
		String frequency = newrepeatableTask.getFrequency()+"";
		
		
		String[] columnNames = {"FK_Users_Chores", "Name", "DateAdded", "Frequency"};
		String[] values = { UserInfo.getUser().getId()+"", name, dateAdded, frequency};
		
		if(oldModelObject == null) {
			SQLHandler.insertInTable(DatabaseNames.JeanDatabase,"Chores", columnNames, values);
		}else {
			int id = oldModelObject.getId();
			SQLHandler.editTable(DatabaseNames.JeanDatabase,"Chores", columnNames, values, "idChores = " + id);
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
			return getName() + "";
		}

		if (number == 2) {
			return getDateAdded() + "";
		}
		if (number == 3) {
			return getFrequency() + "";
		}
		if (number == 4) {
			return getLatestDate()+"";
		}
		if (number == 5) {
			return getHealth() + "";
		}

		else {
			return null;
		}
	}
	@Override
	public IFormCustomBehaviour getCustomFormBuilderBehaviour() {
		// TODO Auto-generated method stub
		return null;
	}

}
