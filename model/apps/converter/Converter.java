package com.model.apps.converter;

import java.io.IOException;
import java.util.ArrayList;

import com.Main;
import com.model.AppHandler;
import com.model.apps.schedule.JeanEvent;
import com.model.container.ContainerManager;
import com.observer.Listener;
import com.observer.apps.converter.ConverterModelObserver;
import com.observer.apps.converter.ConverterViewObserver;
import com.view.apps.converter.ConvertWindowController;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Converter extends AppHandler {

	public Converter(ContainerManager containerManager) {
		super(containerManager);
		// TODO Auto-generated constructor stub
	}

	static GridPane root;
	ConvertWindowController converterController;
	ConverterManager converterManager;
	Stage stage = new Stage();
	double consoleHeight; 
	double consoleWidth;

	public GridPane launch() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/com/view/apps/converter/ConvertWindow.fxml"));
			root = (GridPane) loader.load();

			converterController = loader.getController();

			ConverterViewObserver converterViewObserver = new ConverterViewObserver(this, converterManager);
			ConverterModelObserver converterModelObserver = new ConverterModelObserver(this, converterController);

			converterController.setObserver(converterViewObserver);
			converterManager = new ConverterManager(converterModelObserver);
			
			containerManager.addConsoleSizeListener(new Listener() {
				@Override
				public void onChange() {
					consoleUpdate(containerManager.getConsoleHeight(),containerManager.getConsoleWidth());
				}
			});

		} catch (IOException e) {
			e.printStackTrace();
		}
		return root;
	}

	protected void consoleUpdate(double consoleHeight, double consoleWidth) {
		this.consoleWidth = consoleWidth;
		this.consoleHeight = consoleHeight;
	
		converterController.setRootSize(consoleWidth, consoleHeight);
	}
	
	public void updateFeet(Double input) {
		converterManager.updateFeet(input);
	}

	public void updateInch(Double input) {
		converterManager.updateInch(input);

	}

	public void updateM(Double input) {
		converterManager.updateM(input);

	}

	public void updateCm(Double input) {
		converterManager.updateCm(input);

	}

	public void updateMm(Double input) {
		converterManager.updateMm(input);

	}

	public void updateUi(Double feet, double inch, double M, double Cm, double Mm) {
		converterController.updateUI(feet, inch, M, Cm, Mm);
	}

	@Override
	public GridPane getTables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<JeanEvent> getEvents() {
		// TODO Auto-generated method stub
		return null;
	}

}
