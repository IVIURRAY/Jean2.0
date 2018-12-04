package com.model.apps.email;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.model.AppHandler;

import com.model.apps.schedule.JeanEvent;
import com.model.container.ContainerManager;
import com.model.user.User;
import com.observer.Listener;


import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class EmailRoot extends AppHandler{
		
	boolean clockSetUp = false;
	static Emailer emailer = new Emailer();
	
	public EmailRoot(User user, ContainerManager containerManager) {
		super(containerManager);
		containerManager.addClockListener(new Listener() {
			int min = 0;
			@Override
			public void onChange() {
				LocalDateTime local = containerManager.getDateTime();
				if(!clockSetUp) {
					min=local.getMinute();
					clockSetUp = true;
				}
				if(min != local.getMinute()) {
					min=local.getMinute();
					System.out.println("---new min---");
					System.out.println();
					//emailer.send(new Email("chris@jamb.co.uk", "Testing - " + local.format(DateHelper.getDateFormatter()),"This is a test email."));
				}
			}});
	}
	
	
	
	@Override
	public GridPane getTables() {
		GridPane gridPane = new GridPane();
		gridPane.getChildren().add(new Label("Howdy"));
		return gridPane;
	}

	@Override
	protected void consoleUpdate(double consoleHeight, double consoleWidth) {
			
	}



	@Override
	public ArrayList<JeanEvent> getEvents() {
		// TODO Auto-generated method stub
		return null;
	}

}
