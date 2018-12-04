package com.model.apps.developmentGantt;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;

import com.model.AppHandler;
import com.model.apps.schedule.JeanEvent;
import com.model.container.ContainerManager;
import com.model.user.User;
import com.observer.Listener;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

//recreating the organiser on the wall at the office.
//draw each day - shade weekends
//draw each hour 
//draw each month
//figure out how to zoom in and out

public class DevelopmentGanttRoot extends AppHandler {
	GridPane root = new GridPane();
	ScrollPane scrollPane = new ScrollPane();
	ColumnConstraints col1 = new ColumnConstraints();
	RowConstraints row1 = new RowConstraints();
	public DevelopmentGanttRoot(User user, ContainerManager containerManager) {
		super(containerManager);
		this.user = user;

		root.getColumnConstraints().add(col1);
		root.getRowConstraints().add(row1);
		containerManager.addConsoleSizeListener(new Listener() {
			@Override
			public void onChange() {
				consoleUpdate(containerManager.getConsoleHeight(), containerManager.getConsoleWidth());
			}
		});
		consoleUpdate(containerManager.getConsoleHeight(), containerManager.getConsoleWidth());
	}

	@Override
	public ArrayList<JeanEvent> getEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GridPane getTables() {
		return root;
	}
	public void updateRoot() {
		root.getChildren().clear();
		root.setGridLinesVisible(true);

		scrollPane.setContent(weekDevisions());
		root.add(scrollPane, 0, 0);
	}
	@Override
	protected void consoleUpdate(double consoleHeight, double consoleWidth) {
	
		double actualHeight = consoleHeight -90;
		double actualWidth = consoleWidth  - 20;
		System.out.println(String.format("Console Width %s Console Height %s", actualWidth, actualHeight));
		row1.setMaxHeight(actualHeight);
		row1.setMinHeight(actualHeight);
		row1.setPrefHeight(actualHeight);
		col1.setMaxWidth(actualWidth*3);
		col1.setMinWidth(actualWidth*3);
		col1.setPrefWidth(actualWidth*3);
		
		scrollPane.setMaxWidth(actualWidth);
		scrollPane.setMinWidth(actualWidth);
		scrollPane.setPrefWidth(actualWidth);
		
		scrollPane.setMaxHeight(actualHeight);
		scrollPane.setMinHeight(actualHeight);
		scrollPane.setPrefHeight(actualHeight);
		updateRoot();
	}

	public void howManyWeeksThisYear() {
		LocalDate now = LocalDate.now();
		now.getYear();
	}

	public boolean isLeapYear(int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
	}


	
	public Group drawWeekLines(double width, double height,double offset, double heightOffset, Color color) {
		
		Group weekDevisions = new Group();
		for (int i = 0; i <= 53; i++) {
			Line line = new Line(i * offset, heightOffset, i * offset, height);
			line.setStroke(color);
			line.setStrokeWidth(2);
			weekDevisions.getChildren().add(line);
		}
		return weekDevisions;
	}
	public Group labelWeeks(double offset, double height, int weekHeightOffset) {
		Group weekLabels = new Group();
		for (int i = 1; i <= 52; i++) {
			Label label = new Label(i+"");
			label.setTranslateX((i-1) * offset+5);
			label.setTranslateY(weekHeightOffset);
			weekLabels.getChildren().add(label);
		}
		return weekLabels;
	}
	
	public Group weekDevisions() {
		Group weekDevisions = new Group();
		double width = col1.minWidthProperty().get();
		double height = row1.minHeightProperty().get();
		System.out.println(String.format("Width %s Height %s", width, height));
		
		int numberOfDaysInYear = 365;
		int year = LocalDate.now().getYear();
		if(isLeapYear(year)) {numberOfDaysInYear = 366;}
		//Drawing days
		double blockSize = width/numberOfDaysInYear;
		
	
		weekDevisions.getChildren().add(drawMonthLines(width,height,0,50,blockSize, numberOfDaysInYear, year, Color.BLACK, Color.RED));
		//weekDevisions.getChildren().add(drawLines(width,height,numberOfWeeks*numberOfDaysInWeek,20,Color.rgb(153,191,204)));
		int weekHeightOffset = 25;
		weekDevisions.getChildren().add(drawWeekLines(width,height,blockSize*7,weekHeightOffset,Color.rgb(201,184,179)));
		weekDevisions.getChildren().add(labelWeeks(blockSize*7,height,weekHeightOffset));
	
		return weekDevisions;
		
	}
	public Group drawMonthLines(double width, double height, double monthHeightOffset, double dayHeightOffset,
								double blockSize,int numberOfDaysInYear,int year,Color dayColour, Color monthColour) {
		Group monthDivisions = new Group();

		for (int i = 0; i <= numberOfDaysInYear; i++) {
			Line line = new Line(i * blockSize, dayHeightOffset, i * blockSize, height);
			line.setStroke(dayColour);
			monthDivisions.getChildren().add(line);
		}
		//DrawingMonths
		Double drawnSoFar =0.0;
		for(int i = 1; i<=12; i++) {
			YearMonth yearMonthObject = YearMonth.of(year, i);
			int daysInMonth = yearMonthObject.lengthOfMonth(); 
			double linePosition = daysInMonth*blockSize;
			Label label = new Label(getMonth(i));
			label.setTranslateX(drawnSoFar+5);
			drawnSoFar = drawnSoFar + linePosition;
			Line line = new Line(drawnSoFar, monthHeightOffset, drawnSoFar, height);
			line.setStroke(monthColour);
			line.setStrokeWidth(2);
			
			monthDivisions.getChildren().add(label);
			monthDivisions.getChildren().add(line);
		}
		return monthDivisions;
	}
	
	
	public String getMonth(int month) {
	    return new DateFormatSymbols().getMonths()[month-1];
	}
	
	public Line currentDateLine() {
		LocalDate now = LocalDate.now();
		int month = now.getMonth().getValue();
		int day = now.getDayOfMonth();
		
		return null;
		
	}
	
	//should be able to draw a year given a year
	//
}
