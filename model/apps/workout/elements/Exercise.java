package com.model.apps.workout.elements;

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

public class Exercise extends ModelObject{
	IntegerProperty id;  
	StringProperty catagory;
	StringProperty exercise;
	IntegerProperty intensity;
	
public Exercise() {
	 id=new SimpleIntegerProperty();
	 catagory=new SimpleStringProperty();
	 exercise=new SimpleStringProperty();
	 intensity=new SimpleIntegerProperty();
	
}

public Exercise(int id, String catagory, String exercise,int intensity) {
	this.id=new SimpleIntegerProperty();
	this.catagory=new SimpleStringProperty();
	this.exercise=new SimpleStringProperty();
	this.intensity=new SimpleIntegerProperty();
	setId(id);
    setCatagory(catagory);
    setExercise(exercise);
    setIntensity(intensity);
	
	}
	
	public void setId(int id){this.id.set(id);}
	public void setCatagory(String catagory){this.catagory.set(catagory);}
	public void setExercise(String exercise){this.exercise.set(exercise);}
	public void setIntensity(int intensity){this.intensity.set(intensity);}
	@Override           
	public int getId(){return id.get();}
	public String getCatagory(){return catagory.get();}
	public String getExercise(){return exercise.get();}
	public int getIntensity(){return intensity.get();}
	       
	public IntegerProperty getIdProperty(){return id;}
	public StringProperty getCatagoryProperty(){return catagory;}
	public StringProperty getExerciseProperty(){return exercise;}
	public IntegerProperty getIntensityProperty(){return intensity;}


	@Override
	public void Delete(int idFromSelected, Table3 table) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Property> display1() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFilterString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelObject extractFromForm(ArrayList<String> results, FormBuilder formBuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void mapModelObjectToForm(ModelObject modelObject, ArrayList<FormObject> formObjects) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modelObjectToSQL(ModelObject oldModelObject, Table3 table, FormBuilder formBuilder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPropertyResult(int number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IFormCustomBehaviour getCustomFormBuilderBehaviour() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
