package com.model.apps.lanternManager.elements;

import java.util.ArrayList;

import com.form.FormBuilder;
import com.form.FormObject;
import com.form.IFormCustomBehaviour;
import com.table3.ModelObject;
import com.table3.Table3;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LanternSize extends ModelObject {

	IntegerProperty id;
	StringProperty lanternFamily;
	DoubleProperty height;
	DoubleProperty width;
	DoubleProperty depth;
	StringProperty size;
	IntegerProperty price;
	StringProperty customDims;
	
	public LanternSize(int id2, String lanternNumber, double height2, double width2, double depth2, String sizeLabel,
			int price2, String customDims2) {
		this.id = new SimpleIntegerProperty();
		this.lanternFamily = new SimpleStringProperty();
		this.height = new SimpleDoubleProperty();
		this.width = new SimpleDoubleProperty();
		this.depth = new SimpleDoubleProperty();
		this.size = new SimpleStringProperty();
		this.price = new SimpleIntegerProperty();
		this.customDims = new SimpleStringProperty();	
		this.id.set(id2);
		this.lanternFamily.set(lanternNumber);
		this.height.set(height2); 	
		this.width.set(width2); 		
		this.depth.set(depth2); 		
		this.size.set(sizeLabel); 		
		this.price.set(price2);		
		this.customDims.set(customDims2);	


		
	}
	
	public LanternSize() {
		this.id = new SimpleIntegerProperty();
		this.lanternFamily = new SimpleStringProperty();
		this.height = new SimpleDoubleProperty();
		this.width = new SimpleDoubleProperty();
		this.depth = new SimpleDoubleProperty();
		this.size = new SimpleStringProperty();
		this.price = new SimpleIntegerProperty();
		this.customDims = new SimpleStringProperty();
		
	}
	
	public StringProperty getLanternFamilyProperty() {
		return lanternFamily;
	}

	public DoubleProperty getHeightProperty() {
		return height;
	}

	public DoubleProperty getWidthProperty() {
		return width;
	}

	public DoubleProperty getDepthProperty() {
		return depth;
	}

	public StringProperty getSizeProperty() {
		return size;
	}

	public IntegerProperty getPriceProperty() {
		return price;
	}
	public StringProperty getCustomDimsProperty() {
		return customDims;
	}
	
	public void setId(int id) {
		this.id.set(id);
	}

	public void setLanternFamily(String lanternFamily) {
		this.lanternFamily.set(lanternFamily); 
	}

	public void setHeight(int height) {
		this.height.set(height); 
	}

	public void setWidth(int  width) {
		this.width.set(width);
	}

	public void setDepth(int depth) {
		this.depth.set(depth);
	}

	public void setSize(String size) {
		this.size.set(size);
	}

	public void setPrice(int price) {
		this.price.set(price);
	}
	public void setCustomDims(String dims) {
		this.customDims.set(dims);
	}
	@Override
	public int getId() {
		return id.get();
	}

	public String getLanternFamily() {
		return lanternFamily.get(); 
	}

	public double getHeight() {
		return height.get(); 
	}

	public double getWidth() {
		return width.get();
	}

	public double getDepth() {
		return depth.get();
	}

	public String getSize() {
		return size.get();
	}

	public int getPrice() {
		return price.get();
	}
	public String getCustomDims() {
		return customDims.get();
	}

	@Override
	public ArrayList<Property> display1() {

		ArrayList<Property> properties = new ArrayList<Property>();
		columnNames = new ArrayList<String>();
		properties.add(id);
		properties.add(lanternFamily);
		properties.add(height);
		properties.add(width);
		properties.add(depth);
		properties.add(size);
		properties.add(price);
		properties.add(customDims);
		
		columnNames.add("ID");
		columnNames.add("Lantern Family");
		columnNames.add("Height");
		columnNames.add("Width");
		columnNames.add("Depth");
		columnNames.add("Size");
		columnNames.add("Price");
		columnNames.add("Custom Dimensions");
		boolean [] req = {false, true, true, true,true, true,true};
		requiredFields = req;
		return properties;
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
	public void Delete(int idFromSelected, Table3 table) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IFormCustomBehaviour getCustomFormBuilderBehaviour() {
		// TODO Auto-generated method stub
		return null;
	}

}
