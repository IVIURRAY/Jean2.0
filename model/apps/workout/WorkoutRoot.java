package com.model.apps.workout;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

import com.model.AppHandler;
import com.model.apps.addressbook.AddressBookFactory;
import com.model.apps.addressbook.elements.Contact;
import com.model.apps.schedule.JeanEvent;
import com.model.apps.workout.elements.Workout;
import com.model.apps.workout.elements.WorkoutSet;
import com.model.container.ContainerManager;
import com.model.user.User;
import com.observer.Listener;
import com.table3.IRefreshDataBehaviour;
import com.table3.ModelObject;
import com.table3.Table3;
import com.table3.TableSelectionBehaviour;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class WorkoutRoot extends AppHandler {

	Table3 workoutTable;
	Table3 setsTable;
	// Table3 exercisesTable = new Table3();

	double tableHeight;
	double tableWidth;
	double UiVertical = 80;
	double UiHorizontal = 0;
	User user;

	public WorkoutRoot(User user, ContainerManager containerManager) {
		super(containerManager);
		this.user = user;
		this.workoutTable = new Table3(new Workout(), "Workout Table", this);
		this.setsTable = new Table3(new WorkoutSet(), "Sets Table", this);
		launchWorkoutTable();
		launchSetsTable();
		containerManager.addConsoleSizeListener(new Listener() {
			@Override
			public void onChange() {
				consoleUpdate(containerManager.getConsoleHeight(), containerManager.getConsoleWidth());
			}
		});
		refreshWorkouts();

	}

	@Override
	public GridPane getTables() {
		root = new GridPane();
		root.add(workoutTable.getTable(), 0, 0);
		root.add(setsTable.getTable(), 1, 0);
		return root;
	}

	@Override
	protected void consoleUpdate(double consoleHeight, double consoleWidth) {
		this.consoleWidth = consoleWidth;
		this.consoleHeight = consoleHeight;
		tableHeight = consoleHeight - UiVertical;
		tableWidth = consoleWidth - UiHorizontal;
		workoutTable.setTableHeight((int) tableHeight);
		workoutTable.setTableWidth((int) (tableWidth * 0.5));
		setsTable.setTableHeight((int) tableHeight);
		setsTable.setTableWidth((int) (tableWidth * 0.5));
	}

	public void launchWorkoutTable() {
		workoutTable.setSelectionBehaviour(new TableSelectionBehaviour() {

			@Override
			public ModelObject getSelection(Table3 table, ObservableList<ModelObject> objects) {

				GridPane gridPane = table.getSelectedGridPane();
				Label label = (Label) gridPane.getChildren().get(0);
				int slectedWorkoutId = Integer.parseInt(label.getText());
				Workout result = null;
				for (ModelObject modelObject : objects) {
					Workout workout = (Workout) modelObject;
					if (workout.getId() == slectedWorkoutId) {
						// Match
						result = workout;
					}
				}
				return result;
			}
		});
		workoutTable.setRefershDataBehaviour(new IRefreshDataBehaviour() {
			@Override
			public void refreshData() {
				refreshWorkouts();
			}
		});
		workoutTable.addSelectionListener(new Listener() {
			@Override
			public void onChange() {
				try {
					Workout workouts = (Workout) workoutTable.getSelection();
					System.out.println(String.format("%s %s", workouts.getWorkoutName(), workouts.getDateAdded()));
					refreshSets();
					// emailTable.newTableData(AddressBookFactory.ArrayListToTabluable(AddressBookFactory.getEmails(contact.getId())));
				} catch (NullPointerException e) {
					System.out.println(e.getMessage());
					System.out.println("Selection Cleared");
					setsTable.clearTable();

				}
				// refreshSets();

			}
		});

		workoutTable.initialiseTable();
	}

	public void launchSetsTable() {
		setsTable.setSelectionBehaviour(new TableSelectionBehaviour() {

			@Override
			public ModelObject getSelection(Table3 table, ObservableList<ModelObject> objects) {

				GridPane gridPane = table.getSelectedGridPane();
				Label label = (Label) gridPane.getChildren().get(0);
				int slectedWorkoutSetId = Integer.parseInt(label.getText());
				WorkoutSet result = null;
				for (ModelObject modelObject : objects) {
					WorkoutSet workout = (WorkoutSet) modelObject;
					if (workout.getId() == slectedWorkoutSetId) {
						// Match
						result = workout;
					}
				}
				return result;
			}
		});
		setsTable.setRefershDataBehaviour(new IRefreshDataBehaviour() {
			@Override
			public void refreshData() {
				refreshSets();
			}
		});
		setsTable.addSelectionListener(new Listener() {
			@Override
			public void onChange() {
				try {
					WorkoutSet workoutSet = (WorkoutSet) setsTable.getSelection();
					System.out.println(String.format("%s", workoutSet.getExerciseName()));
					// emailTable.newTableData(AddressBookFactory.ArrayListToTabluable(AddressBookFactory.getEmails(contact.getId())));
				} catch (NullPointerException e) {
					System.out.println(e.getMessage());
					System.out.println("Selection Cleared");
				}
			}
		});
		setsTable.initialiseTable();
	}

	public void refreshWorkouts() {
		workoutTable.newTableData(WorkoutFactory.ArrayListToTabluable(WorkoutFactory.getWorkouts(user)));
	}

	public void refreshSets() {
		setsTable
				.newTableData(WorkoutFactory.ArrayListToTabluable(WorkoutFactory.getSets(workoutTable.getSelection())));
	}

	public int getSelectedWorkoutId() {
		return workoutTable.getSelection().getId();
	}

	@Override
	public ArrayList<JeanEvent> getEvents() {

		// need to get the day

		LocalDate now = LocalDate.now();
		DayOfWeek dayOfWeek = now.getDayOfWeek();
		// Mornings - abs and arms
		switch (dayOfWeek) {
		case MONDAY:
			// Monday
			// Squat
				//Need to check the database for squat entries
				//Generate a for today's workout
					//How do we calculate todays workout?
					//Find the max for this exercise.
					
			// accessories
			
			break;
		case TUESDAY:
			// Tuesday
			// Bench
			// accessories
			break;
		case WEDNESDAY:
			// Wednesday
			// Deadlift
			// accessories
			break;
		case THURSDAY:
			// Thursday
			// OHP
			// accessories
			break;
		case FRIDAY:
			// Friday
			// Row
			// Pull Ups
			// accessories
			break;
		case SATURDAY:
			break;
		case SUNDAY:
			break;
		}
	
	
		// Logic
		// Look at what day it is..
		// See if there is any data on the workouts for today..
		// If there is... generate a workout based on that infomation
		// Place the sets in such a way that they can just be ticked off as the workout
		// goes...

		// if there is no data then the user needs to run a setup wizard?

		// How does the food app work
		// needs to tell me what todays planned meals are.
		// User variabled - current weight - current goals, meals per day, available
		// stock.
		// Figure out a bunch of meals that we can pre-make and eat easily...
		// Meal list for the day
		// Stock indication of how much prepared food is left.
		// Stock indication of how many raw ingredients we have left
		// shopping list for raw ingredients.

		return null;
	}
}
