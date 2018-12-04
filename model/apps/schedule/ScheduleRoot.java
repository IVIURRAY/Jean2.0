package com.model.apps.schedule;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import com.model.AppHandler;
import com.model.container.ContainerManager;
import com.model.user.User;
import com.observer.Listener;

import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.layout.GridPane;

public class ScheduleRoot extends AppHandler {

	double canvasWidth = 200;
	double canvasHeight = 200;
	int padding = 10;
	GridPane root;
	Canvas canvas;
	GraphicsContext gc;
	double timelineWidth;
	double timelineHeight;
	
	 
	
	public ScheduleRoot(User user, ContainerManager containerManager) {
		super(containerManager);
		this.user = user;
		this.root = new GridPane();
		this.canvas = new Canvas(canvasWidth, canvasHeight);
		this.gc = canvas.getGraphicsContext2D();
		root.add(canvas, 0, 0);
		containerManager.addConsoleSizeListener(new Listener() {
			@Override
			public void onChange() {
				consoleUpdate(containerManager.getConsoleHeight(), containerManager.getConsoleWidth());
				System.out.println("triggers");
			}
		});
	}

	@Override
	public ArrayList<JeanEvent> getEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GridPane getTables() {
		clearCanvas();
		drawRect();
		return root;
	}

	private void clearCanvas() {
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, canvasWidth, canvasHeight);
	}
	
	
	private void drawRect() {
		
		gc.setFill(Color.web("#98e6e6"));
		 timelineWidth = canvasWidth - (padding * 2);
		timelineHeight = (canvasHeight/7) - (padding * 2);
		
		gc.fillRect(padding, padding, timelineWidth, timelineHeight);
		
		
		 //draw the periods
		 //sleep - 9:30 - 4:30
		 LocalTime sleepStart1 = LocalTime.of(21, 30);
		 LocalTime sleepEnd1 = LocalTime.of(23, 59);
		 LocalTime sleepStart2 = LocalTime.of(00, 00);
		 LocalTime sleepEnd2 = LocalTime.of(4, 30);
		 drawPeriod(sleepStart1,sleepEnd1, Color.PURPLE);
		 drawPeriod(sleepStart2,sleepEnd2, Color.PURPLE);
		 //work
		 LocalTime workMorningStart = LocalTime.of(8, 00);
		 LocalTime workMorningEnd = LocalTime.of(12, 00);
		 LocalTime workEveningStart = LocalTime.of(13, 00);
		 LocalTime workEveningEnd = LocalTime.of(16, 30);
	
		 drawPeriod(workMorningStart,workMorningEnd, Color.HONEYDEW);
		 drawPeriod(workEveningStart,workEveningEnd, Color.HONEYDEW);
		
		//draws the segments 
		 gc.setStroke(Color.web("#008080"));
		int devisions = 24;
		for(int i=1; i<devisions; i++) {
			gc.strokeLine(((timelineWidth/devisions)*i)+padding, padding, ((timelineWidth/devisions)*i)+padding, timelineHeight+(padding));
		}
		for(int i=1; i<devisions; i++) {
			gc.strokeLine(((timelineWidth/devisions)*i)+padding, padding, ((timelineWidth/devisions)*i)+padding, timelineHeight+(padding));
		}
	
		//draws the time now line
		 LocalDateTime now = LocalDateTime.now(); 
		 //draw the line at now...
		 LocalTime time =now.toLocalTime();
		int hours = time.getHour();
		 
		 int mins = time.getMinute();
		 //hours = 6;
		 //mins = 0;
		 double xpos = (((timelineWidth/24)*hours) + ((timelineWidth/24)/60)*mins)+padding;
		 gc.setStroke(Color.RED);
		 gc.strokeLine(xpos, padding, xpos, timelineHeight+(padding)+15);
		 
	
	}
	public void drawPeriod(LocalTime start, LocalTime end, Color color) {
		gc.setFill(color);
		int startHours = start.getHour();
		int startMin= start.getMinute();
		double x1 = getXpos(startHours, startMin);
		int endHours = end.getHour();
		int endMin= end.getMinute();
		double x2 = getXpos(endHours, endMin);
		System.out.println(String.format("Drawing rec at: %s %s %s %s", x1+padding, padding, x2, timelineHeight));
		gc.fillRect(x1+padding, padding, x2-(x1), timelineHeight);
		
	}
	
	@Override
	protected void consoleUpdate(double consoleHeight, double consoleWidth) {
		this.canvasWidth = consoleWidth-10;
		this.canvasHeight = consoleHeight-80;
		canvas.setWidth(canvasWidth);
		canvas.setHeight(consoleHeight);
		 containerManager.updateSchedule();
	}
	 double getXpos(int hour, int mins) {
		 System.out.println(String.format("Hours %s Mins %s",hour, mins));
		 double results = (((timelineWidth/24)*hour) + ((timelineWidth/24)/60)*mins);
		 System.out.println("Results: "+results);
		return  results;
	 }
}
