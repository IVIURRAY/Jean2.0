package com.model.apps.lanternManager.elements;

import java.util.ArrayList;

import com.form.FormBuilder;
import com.form.FormObject;
import com.form.IFormCustomBehaviour;
import com.table3.ModelObject;
import com.table3.Table3;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LanternConfig extends ModelObject {
	IntegerProperty id;
	IntegerProperty familyId;
	StringProperty certification;
	IntegerProperty solutionId;
	
	public LanternConfig(){
		this.objectName = "LanternConfig";
		
		this.id = new SimpleIntegerProperty();
		this.familyId = new SimpleIntegerProperty();
		this.certification = new SimpleStringProperty();
		this.solutionId = new SimpleIntegerProperty();
	}
	
	public LanternConfig(int id, int familyId, String certification, int solutionId) {
		this.objectName = "LanternConfig";
		
		this.id = new SimpleIntegerProperty();
		this.familyId = new SimpleIntegerProperty();
		this.certification = new SimpleStringProperty();
		this.solutionId = new SimpleIntegerProperty();
		
		this.id.set(id);
		this.familyId.set(familyId);
		this.certification.set(certification);
		this.solutionId.set(solutionId);
	}
	
	@Override
	public int getId() {
		return id.get();
	}
	public IntegerProperty getIdProperty() {
		return id;
	}

	public IntegerProperty getFamilyIdProperty() {
		return familyId;
	}

	public StringProperty getCertificationProperty() {
		return certification;
	}

	public IntegerProperty getSolutionIdProperty() {
		return solutionId;
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public void setFamilyId(int id) {
		this.familyId.set(id);
	}

	public void setCertificaiton(String certification) {
		this.certification.set(certification);
	}

	public void setSolutionId(int id) {
		this.solutionId.set(id);
	}

	public int getFamilyId() {
		return familyId.get();
	}

	public String getCertificaiton() {
		return certification.get();
	}

	public int getSolutionId() {
		return solutionId.get();
	}



	@Override
	public ArrayList<Property> display1() {
		ArrayList<Property> properties = new ArrayList<Property>();
		columnNames = new ArrayList<String>();
		
		properties.add(id);
		properties.add(familyId);
		properties.add(certification);
		properties.add(solutionId);
		
		columnNames.add("ID");
		columnNames.add("Family ID");
		columnNames.add("Certification");
		columnNames.add("Solution ID");
		boolean [] req = {false, true, true, true};
		requiredFields = req;
		return properties;
	}
	
	@Override
	public String getFilterString() {
		return null;
	}
	
	@Override
	public String getPropertyResult(int number) {
		return null;
	}

	@Override
	public void Delete(int idFromSelected, Table3 table) {
	}

	@Override
	public ModelObject extractFromForm(ArrayList<String> results, FormBuilder formBuilder) {
		return null;
	}

	@Override
	public void mapModelObjectToForm(ModelObject modelObject, ArrayList<FormObject> formObjects) {
	}

	@Override
	public void modelObjectToSQL(ModelObject oldModelObject, Table3 table, FormBuilder formBuilder) {
	}

	@Override
	public IFormCustomBehaviour getCustomFormBuilderBehaviour() {
		// TODO Auto-generated method stub
		return null;
	}

}
