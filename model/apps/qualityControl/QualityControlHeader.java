package com.model.apps.qualityControl;

import javafx.event.EventType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class QualityControlHeader {
//this is a small bar along the top of the program that alows you to browse the shipments that have records...
	
	
	//Shiping reference
		//generate a list of shipping references using SQL
		//populate a combobox with the options.
		//when the user selects and option it changes the tables.
	//Date period
		//select by timestamp of inspection - positives dont have timestamps...
	
	
	HBox hBox = new HBox();
	Label message = new Label("Select a shipping reference: ");
	ComboBox<String> comboBox = new ComboBox<String>();
	QualityControlRoot qualityControlRoot;
	
	QualityControlHeader(QualityControlRoot qualityControlRoot){
		hBox.getChildren().add(message);
		hBox.getChildren().add(comboBox);
		this.qualityControlRoot = qualityControlRoot;
		comboBox.setOnAction((event) -> {
			trigger();
		});
	}
	
	public void populateComboBox() {
		comboBox.setItems(QualityControlFactory.getShippingReferences());
	}
	
	private void trigger() {
		qualityControlRoot.refreshTables(comboBox.getSelectionModel().getSelectedItem());
		
	}

	public HBox getDisplay() {
		return hBox;
	}
	
	public void setHeight(int i) {
		// TODO Auto-generated method stub
		
	}

	public void setWidth(int i) {
		// TODO Auto-generated method stub
		
	}
}
