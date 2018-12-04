package com.model.apps.lanternManager.elements;

import java.util.ArrayList;

import com.form.FormBuilder;
import com.form.FormObject;
import com.form.IFormCustomBehaviour;
import com.table3.ModelObject;
import com.table3.Table3;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LanternFamily extends ModelObject {

	IntegerProperty id;
	StringProperty type;
	StringProperty name;
	StringProperty LTNnumb;
	StringProperty description;
	StringProperty qualityIssuesPlaceholder;
	IntegerProperty customDims;
	BooleanProperty IP44;
	BooleanProperty IP20;
	BooleanProperty ULDry;
	BooleanProperty ULDamp;
	BooleanProperty ULWet;

	public LanternFamily() {

		this.id = new SimpleIntegerProperty();
		this.type = new SimpleStringProperty();
		this.name = new SimpleStringProperty();
		this.LTNnumb = new SimpleStringProperty();
		this.description = new SimpleStringProperty();
		this.qualityIssuesPlaceholder = new SimpleStringProperty();
		this.customDims = new SimpleIntegerProperty();
		this.IP44 = new SimpleBooleanProperty();
		this.IP20 = new SimpleBooleanProperty();
		this.ULDry = new SimpleBooleanProperty();
		this.ULDamp = new SimpleBooleanProperty();
		this.ULWet = new SimpleBooleanProperty();

	};

	public LanternFamily(int id, String type, String name, String LTNnumb, String description,
			String qualityIssuesPlaceholder, int customDims, boolean IP44, boolean IP20, boolean ULDry, boolean ULDamp,
			boolean ULWet) {

		this.id = new SimpleIntegerProperty();
		this.type = new SimpleStringProperty();
		this.name = new SimpleStringProperty();
		this.LTNnumb = new SimpleStringProperty();
		this.description = new SimpleStringProperty();
		this.qualityIssuesPlaceholder = new SimpleStringProperty();
		this.customDims = new SimpleIntegerProperty();
		this.IP44 = new SimpleBooleanProperty();
		this.IP20 = new SimpleBooleanProperty();
		this.ULDry = new SimpleBooleanProperty();
		this.ULDamp = new SimpleBooleanProperty();
		this.ULWet = new SimpleBooleanProperty();

		this.id.set(id);
		this.type.set(type);
		this.name.set(name);
		this.LTNnumb.set(LTNnumb);
		this.description.set(description);
		this.qualityIssuesPlaceholder.set(qualityIssuesPlaceholder);
		this.customDims.set(customDims);
		this.IP44.set(IP44);
		this.IP20.set(IP20);
		this.ULDry.set(ULDry);
		this.ULDamp.set(ULDamp);
		this.ULWet.set(ULWet);

	};

	public StringProperty getTypeProperty() {
		return type;
	}

	public StringProperty getNameProperty() {
		return name;
	}

	public StringProperty getLTNnumbProperty() {
		return LTNnumb;
	}

	public StringProperty getDescriptionProperty() {
		return description;
	}

	public StringProperty getQualityIssuesPlaceholderProperty() {
		return qualityIssuesPlaceholder;
	}

	public IntegerProperty getCustomDimsProperty() {
		return customDims;
	}

	public BooleanProperty getIP44Property() {
		return IP44;
	}

	public BooleanProperty getIP20Property() {
		return IP20;
	}

	public BooleanProperty getULDryProperty() {
		return ULDry;
	}

	public BooleanProperty getULDampProperty() {
		return ULDamp;
	}

	public BooleanProperty getULWetProperty() {
		return ULWet;
	}

	public IntegerProperty getIdProperty() {
		return id;
	}

	@Override
	public int getId() {
		return id.get();
	}

	public String getType() {
		return type.get();
	}

	public String getName() {
		return name.get();
	}

	public String getLTNnumb() {
		return LTNnumb.get();
	}

	public String getDescription() {
		return description.get();
	}

	public String getQualityIssuesPlaceholder() {
		return qualityIssuesPlaceholder.get();
	}

	public int getCustomDims() {
		return customDims.get();
	}

	public boolean getIP44() {
		return IP44.get();
	}

	public boolean getIP20() {
		return IP20.get();
	}

	public boolean getULDry() {
		return ULDry.get();
	}

	public boolean getUDamp() {
		return ULDamp.get();
	}

	public boolean getULWet() {
		return ULWet.get();
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public void setType(String type) {
		this.type.set(type);
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public void setLTNNumb(String input) {
		this.LTNnumb.set(input);
	}

	public void setDescription(String input) {
		description.set(input);
	}

	public void setQualityIssuesPlaceholder(String input) {
		qualityIssuesPlaceholder.set(input);
	}

	public void setCustomDims(int input) {
		customDims.set(input);
	}

	public void setIP44(boolean input) {
		IP44.set(input);
	}

	public void setIP20(boolean input) {
		IP20.set(input);
	}

	public void setULWet(boolean input) {
		ULWet.set(input);
	}

	public void setULDamp(boolean input) {
		ULDamp.set(input);
	}

	public void setULDry(boolean input) {
		ULDry.set(input);
	}

	@Override
	public void Delete(int idFromSelected, Table3 table) {

	}

	@Override
	public ArrayList<Property> display1() {
		ArrayList<Property> properties = new ArrayList<Property>();

		columnNames = new ArrayList<String>();

		properties.add(id);
		properties.add(type);
		properties.add(name);
		properties.add(LTNnumb);
		
		properties.add(description);
		properties.add(qualityIssuesPlaceholder);
		properties.add(customDims);
		properties.add(IP44);
		
		properties.add(IP20);
		properties.add(ULDry);
		properties.add(ULDamp);
		properties.add(ULWet);

		columnNames.add("ID");
		columnNames.add("Type");
		columnNames.add("Name");
		columnNames.add("LTN Number");
		
		columnNames.add("Description");
		columnNames.add("Quality Issues");
		columnNames.add("Number of Custom Dims");
		columnNames.add("IP44");
		
		columnNames.add("IP20");
		columnNames.add("ULDry");
		columnNames.add("ULDamp");
		columnNames.add("ULWet");
		boolean [] req = {false, true, true, true,true, true,true, true,true,true,true,true,true};
		requiredFields = req;
		return properties;
	}

	@Override
	public String getFilterString() {
		return String.format("%s %s %s", type.get(), name.get(), LTNnumb.get());
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
	public String getPropertyResult(int number) {

		if (number == 0) {
			return getId() + "";
		}
		if (number == 1) {
			return getType() + "";
		}
		if (number == 2) {
			return getName() + "";
		} 
		if (number == 3) {
			return getLTNnumb() + "";
		}
		if (number == 4) {
			return getQualityIssuesPlaceholder() + "";
		}
		if (number == 5) {
			return getDescription() + "";
		} 
		if (number == 6) {
			return getCustomDims() + "";
		}
		if (number == 7) {
			return getIP44() + "";
		}
		if (number == 8) {
			return getIP20() + "";
		} 
		if (number == 9) {
			return getULDry() + "";
		}
		if (number == 10) {
			return getUDamp() + "";
		}
		if (number == 11) {
			return getULWet() + "";
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

	// Engineering Drawing
	// Hero Image

}
