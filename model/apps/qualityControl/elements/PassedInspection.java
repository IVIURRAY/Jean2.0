package com.model.apps.qualityControl.elements;

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
import com.table3.ModelObject;
import com.table3.Table3;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PassedInspection extends ModelObject{

	IntegerProperty Id;
	StringProperty productCode;
	StringProperty shipmentReference;
	IntegerProperty boxNumber;
	
	public IntegerProperty getIdProperty() {return Id;}
	public StringProperty getProductCodeProperty() {return productCode;}
	public StringProperty getShipmentReferenceProperty() {return shipmentReference;}
	public IntegerProperty getBoxNumberProperty() {return boxNumber;}
	
	@Override 
	public int getId() {return Id.get();}
	public String getProductCode() {return productCode.get();}
	public String getShipmentReference() {return shipmentReference.get();}
	public int getBoxNumber() {return boxNumber.get();}
	
	public void setId(int id) {this.Id.set(id);}
	public void setProductCode(String productCode) {this.productCode.set(productCode);}
	public void setShipmentReference(String shipmentReference) {this.shipmentReference.set(shipmentReference);}
	public void setBoxNumber(int boxNumber) {this.boxNumber.set(boxNumber);}
	
	public PassedInspection() {
		this.Id = new SimpleIntegerProperty();
		this.productCode = new SimpleStringProperty();
		this.shipmentReference = new SimpleStringProperty();
		this.boxNumber = new SimpleIntegerProperty();
		objectName = "Passed Inspection";
	}
	
	public PassedInspection(int id, String productCode, String shipmentReference, int boxNumber) {
		this.Id = new SimpleIntegerProperty();
		this.productCode = new SimpleStringProperty();
		this.shipmentReference = new SimpleStringProperty();
		this.boxNumber = new SimpleIntegerProperty();
		this.Id.set(id);
		this.productCode.set(productCode);
		this.shipmentReference.set(shipmentReference);
		this.boxNumber.set(boxNumber);
		objectName = "Passed Inspection";
	}
	@Override
	public void Delete(int idFromSelected, Table3 table) {
		System.out.println("Delete " +objectName);
		SQLHandler.deleteRowInTable(DatabaseNames.JambProductDatabse,"QualityPassedInspection", "idQualityPassedInspection= " + idFromSelected);
		table.refreshData();

	}
	
	@Override
	public ArrayList<Property> display1() {
		ArrayList<Property> properties = new ArrayList<Property>();
		columnNames = new ArrayList<String>();
		properties.add(Id);
		properties.add(productCode);
		properties.add( shipmentReference);
		properties.add( boxNumber);
		columnNames.add("ID");
		columnNames.add("Product Code");
		columnNames.add("Shipment Reference");
		columnNames.add("Box Number");
		boolean [] req = {false, true, true, true};
		requiredFields = req;
		return properties;
	}
	
	
	@Override
	public String getFilterString() {
		// TODO Auto-generated method stub
		return productCode.get() + " " + boxNumber.get();
	}
	@Override
	public ModelObject extractFromForm(ArrayList<String> results, FormBuilder formBuilder) {
		PassedInspection passedInspection = new PassedInspection();
	
		passedInspection.setProductCode(results.get(2));
		passedInspection.setShipmentReference(results.get(3));
		passedInspection.setBoxNumber(Integer.parseInt(results.get(4)));
		
		ModelObject result = (ModelObject) passedInspection;
		return result;
	}
	@Override
	public void mapModelObjectToForm(ModelObject modelObject, ArrayList<FormObject> formObjects) {
		PassedInspection passedInspection = (PassedInspection) modelObject;
		IntField id = (IntField) formObjects.get(0);
		id.setInput(passedInspection.getId());
		id.setDisabled();
		
		StringField productCode = (StringField) formObjects.get(1);
		productCode.setInput(passedInspection.getProductCode());
		
		StringField shipmentReference = (StringField) formObjects.get(2);
		shipmentReference.setInput(passedInspection.getShipmentReference());
		
		IntField boxNumber = (IntField) formObjects.get(3);
		boxNumber.setInput(passedInspection.getBoxNumber());
	}
	
	@Override
	public void modelObjectToSQL(ModelObject oldModelObject, Table3 table, FormBuilder formBuilder) {
		ModelObject submittedObject = formBuilder.getSubmittedObject();
		PassedInspection passedInspection = (PassedInspection) oldModelObject;
		PassedInspection newPassedInspection = (PassedInspection) submittedObject;
		
		String productCode = newPassedInspection.getProductCode();
		String shipmentReference = newPassedInspection.getShipmentReference();
		int boxNumber = newPassedInspection.getBoxNumber();
		
	
		String[] columnNames = {"ProductCode","ShipmentReference","BoxNumber"};
		String[] values = { productCode, shipmentReference, boxNumber+""};
		if(oldModelObject == null) {
			SQLHandler.insertInTable(DatabaseNames.JambProductDatabse,"QualityPassedInspection", columnNames, values);
		}else {
			int id = passedInspection.getId();
			SQLHandler.editTable(DatabaseNames.JambProductDatabse,"QualityPassedInspection", columnNames, values, "idQualityPassedInspection=" + id);
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
			return getProductCode() + "";
		}
		if (number == 2) {
			return getShipmentReference() + "";
		}
		if (number == 3) {
			return getBoxNumber() + "";
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
