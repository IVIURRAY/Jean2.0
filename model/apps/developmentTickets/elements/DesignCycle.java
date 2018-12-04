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
import com.model.apps.addressbook.AddressBookRoot;
import com.model.apps.developmentTickets.DevelopmentTicketsRoot;
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

public class DesignCycle extends ModelObject{


	IntegerProperty id;
	IntegerProperty parentId;
	StringProperty description;
	DateProperty dateCreated;
	DateProperty dateCompleted;
	
	public DesignCycle(int id, int parentId, String description, LocalDate dateCreated, LocalDate dateCompleted) {
		this.id = new	SimpleIntegerProperty();
		this.parentId = new SimpleIntegerProperty();
		this.description = new SimpleStringProperty();
		this.dateCreated = new DateProperty();
		this.dateCompleted = new DateProperty();

		this.id.set(id);
		this.parentId.set(parentId);
		this.description.set(description);
		this.dateCreated.setValue(dateCreated);
		this.dateCompleted.setValue(dateCompleted);
	}
	
	public DesignCycle() {
		this.id = new	SimpleIntegerProperty();
		this.parentId = new SimpleIntegerProperty();
		this.description = new SimpleStringProperty();
		this.dateCreated = new DateProperty();
		this.dateCompleted = new DateProperty();

	}
	public IntegerProperty getIdProperty() {return id;}
	public IntegerProperty getParentIdProperty() {return parentId;}
	public StringProperty getDescriptionProperty() {return description;}
	public DateProperty getDateCreatedProperty() {return dateCreated;}
	public DateProperty getDateCompletedProperty() {return dateCompleted;}
	@Override
	public int getId() {return id.get();}
	public int getParentId() {return parentId.get();}
	public String getDescription() {return description.get();}
	public LocalDate getDateCreated() {return dateCreated.getValue();}
	public LocalDate getDateCompleted() {return dateCompleted.getValue();}

	public void setId(int id) {this.id.set(id);}
	public void setParentId(int id) {this.parentId.set(id);}
	public void setDescription(String description) {this.description.set(description);}
	public void setDateCreated(LocalDate dateCreated) {this.dateCreated.setValue(dateCreated);}
	public void setDateCompleted(LocalDate dateCompleted) {this.dateCreated.setValue(dateCompleted);}
	
	@Override
	public ArrayList<Property> display1() {
		ArrayList<Property> properties = new ArrayList<Property>();
		columnNames = new ArrayList<String>();
		properties.add(id);
		properties.add(description);
		properties.add(dateCreated);
		properties.add(dateCompleted);
		columnNames.add("ID");
		columnNames.add("Description");
		columnNames.add("Created");
		columnNames.add("Completed");
		boolean [] req = {false, true, true, false};
		requiredFields = req;
		return properties;
	}
	@Override
	public String getFilterString() {
		return getDescription() + getId();
	}
	@Override
	public String getPropertyResult(int number) {
		if (number == 0) {
			return getId() + "";
		}
		if (number == 1) {
			return getDescription() + "";
		}
		if (number == 2) {
			return getDateCreated() + "";
		}	if (number == 3) {
			return getDateCompleted() + "";
		} else {
			return null;
	}
	}
	@Override
	public ModelObject extractFromForm(ArrayList<String> results, FormBuilder formBuilder) {
		DesignCycle designCycle = new DesignCycle();
		designCycle.setDescription(results.get(1));
		designCycle.setDateCreated(LocalDate.parse(results.get(2), DateHelper.getDateFormatter()));
		designCycle.setDateCreated(LocalDate.parse(results.get(3), DateHelper.getDateFormatter()));
		ModelObject result = (ModelObject) designCycle;
		return result;
	}
	
	
	
	@Override
	public void mapModelObjectToForm(ModelObject modelObject, ArrayList<FormObject> formObjects) {
		DesignCycle designCycle = (DesignCycle) modelObject;
		IntField id = (IntField) formObjects.get(0);
		id.setInput(designCycle.getId());
		id.setDisabled();
		StringField description = (StringField) formObjects.get(2);
		description.setInput(designCycle.getDescription());
		DateField created = (DateField) formObjects.get(5);
		created.setInput(designCycle.getDateCreated());
		DateField completed = (DateField) formObjects.get(5);
		completed.setInput(designCycle.getDateCompleted());

	}
	
	
	@Override
	public void modelObjectToSQL(ModelObject oldModelObject, Table3 table, FormBuilder formBuilder) {
		ModelObject submittedObject = formBuilder.getSubmittedObject();
		DesignCycle designCycle = (DesignCycle) oldModelObject;
		DesignCycle newDesignCycle= (DesignCycle) submittedObject;
		
		String description = designCycle.getDescription();
		LocalDate created = designCycle.getDateCreated();
		LocalDate completed = designCycle.getDateCompleted();
		DevelopmentTicketsRoot app = (DevelopmentTicketsRoot)table.getAppHandler();
		
		String[] columnNames = {"Parent_ID","Description","Date_Created","Date_Completed"};
		String[] values = {app.getSelectedTicketId()+"", description, created.format(DateHelper.getDateFormatter()), completed.format(DateHelper.getDateFormatter()) };
		if(oldModelObject == null) {
			SQLHandler.insertInTable(DatabaseNames.JambDevelopmentDatabase,"Design_Cycles", columnNames, values);
		}else {
			int id = designCycle.getId();
			SQLHandler.editTable(DatabaseNames.JambDevelopmentDatabase,"Design_Cycles", columnNames, values, "idDesign_Cycles = " + id);
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
		System.out.println("Delete " +objectName);
		SQLHandler.deleteRowInTable(DatabaseNames.JambDevelopmentDatabase,"Active_Projects", "idActive_Projects = " + idFromSelected);
		table.refreshData();
		
	}

}


