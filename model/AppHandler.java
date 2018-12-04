package com.model;

import java.util.LinkedList;
import java.util.List;

import com.model.apps.schedule.ISchedulable;
import com.model.container.ContainerManager;
import com.model.user.User;
import com.observer.Listener;

import javafx.scene.layout.GridPane;

public abstract class AppHandler implements ISchedulable{
	public double consoleHeight;
	public double consoleWidth;
	protected GridPane root;
	protected User user;
	protected ContainerManager containerManager;
	List<Listener> listerns = new LinkedList<Listener>();
	
	protected AppHandler(ContainerManager containerManager){
		this.containerManager = containerManager;
		
	}
	public void addListener(Listener e) {
		listerns.add(e);
	}
	
	public double getConsoleHeight() {
		return consoleHeight;
	}

	public void setConsoleHeight(double consoleHeight) {
		this.consoleHeight = consoleHeight;
	}

	public double getConsoleWidth() {
		return consoleWidth;
	}

	public void setConsoleWidth(double consoleWidth) {
		this.consoleWidth = consoleWidth;
	}

	abstract public GridPane getTables();
	protected abstract void consoleUpdate(double consoleHeight, double consoleWidth);
	

}