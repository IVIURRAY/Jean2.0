package com.model.apps.bus;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class BusWindowController {

	public BusWindowController() {}
	
	@FXML
	public Label MJeta;

	@FXML
	public Label CHSeta;

	@FXML
	public Label Notes;
	
	public BusHandler busHandler;

	public Stage busWindow;
	
	
	public void setHandler(BusHandler busHandler) {
		this.busHandler = busHandler;
		
	}

	public void setStage(Stage busWindow) {
		this.busWindow =  busWindow;
		
	}
	
	public void setTextMitcham(String s) {
		MJeta.setText(s);
	}
	
	public void setTextCarshalton(String s) {
		CHSeta.setText(s);
	}
}
