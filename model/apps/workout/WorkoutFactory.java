package com.model.apps.workout;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JTable;

import com.model.apps.workout.elements.Exercise;
import com.model.apps.workout.elements.Workout;
import com.model.apps.workout.elements.WorkoutSet;
import com.model.sql.SQLHandler;
import com.model.user.User;
import com.table3.ModelObject;
import com.table3.Tabluable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class WorkoutFactory {


		 
	 static public ArrayList<Tabluable> ArrayListToTabluable(ObservableList<ModelObject> list) {
		ArrayList<Tabluable> tableInfo = new ArrayList<Tabluable>();
		for (ModelObject o : list) {
			Tabluable t = (Tabluable) o;
			tableInfo.add(t);
		}
		return tableInfo;
	}

	public static ObservableList<ModelObject> getWorkouts(User user) {
		String sql = String.format("SELECT * FROM Workouts WHERE FK_Users_Workouts = %d", user.getId());
		JTable tbl = SQLHandler.getTable(sql,"Jean");
		ObservableList<ModelObject> workouts = FXCollections.observableArrayList();
		for (int i = 0; i < tbl.getRowCount(); i++) {
			int id = (int) tbl.getModel().getValueAt(i, tbl.getColumn("idWorkouts").getModelIndex());
			String name = (String) tbl.getModel().getValueAt(i, tbl.getColumn("Name").getModelIndex());
			Date timestamp = (Date) tbl.getModel().getValueAt(i, tbl.getColumn("Date").getModelIndex());
			LocalDate localTimestamp = timestamp.toLocalDate();
			ModelObject workout= new Workout(id, name ,localTimestamp);
			workouts.add(workout);
		}
		return workouts;
	}
	
	public static ObservableList<ModelObject> getExercise() {
		String sql = String.format("SELECT * FROM WorkoutExercises");
		JTable tbl = SQLHandler.getTable(sql,"Jean");
		ObservableList<ModelObject> exercises = FXCollections.observableArrayList();
		for (int i = 0; i < tbl.getRowCount(); i++) {
			int id = (int) tbl.getModel().getValueAt(i, tbl.getColumn("idWorkoutExercises").getModelIndex());
			String exerciseName = (String) tbl.getModel().getValueAt(i, tbl.getColumn("Exercise").getModelIndex());
			String catagory = (String) tbl.getModel().getValueAt(i, tbl.getColumn("Catagory").getModelIndex());
			int intensity = (int) tbl.getModel().getValueAt(i, tbl.getColumn("Intensity").getModelIndex());
		
			ModelObject exercise = new Exercise(id, catagory,exerciseName , intensity);
			exercises.add(exercise);
		}
		System.out.println("Exercises " + exercises.size());
		return exercises;
	}

	public static ObservableList<ModelObject> getSets(ModelObject selection) {
	
		String sql = String.format("SELECT WorkoutSets.idWorkoutSets,WorkoutExercises.idWorkoutExercises, WorkoutExercises.Exercise, WorkoutExercises.Catagory, WorkoutExercises.Intensity, WorkoutSets.Reps, WorkoutSets.Weight, WorkoutSets.Notes\r\n" + 
				"FROM JeansDatabase.WorkoutSets\r\n" + 
				"JOIN JeansDatabase.WorkoutExercises\r\n" + 
				"ON WorkoutSets.FK_WorkoutExercises_WorkoutSets = WorkoutExercises.idWorkoutExercises"
				+ " WHERE FK_Workouts_WorkoutSets = %s;", selection.getId());
		JTable tbl = SQLHandler.getTable(sql,"Jean");
		ObservableList<ModelObject> sets = FXCollections.observableArrayList();
		for (int i = 0; i < tbl.getRowCount(); i++) {
			int id = (int) tbl.getModel().getValueAt(i, tbl.getColumn("idWorkoutSets").getModelIndex());
			int exerciseId = (int) tbl.getModel().getValueAt(i, tbl.getColumn("idWorkoutExercises").getModelIndex());
			String exerciseName = (String) tbl.getModel().getValueAt(i, tbl.getColumn("Exercise").getModelIndex());
			String catagory = (String) tbl.getModel().getValueAt(i, tbl.getColumn("Catagory").getModelIndex());
			int intensity = (int) tbl.getModel().getValueAt(i, tbl.getColumn("Intensity").getModelIndex());
			int reps = (int) tbl.getModel().getValueAt(i, tbl.getColumn("Reps").getModelIndex());
			double weight = (double) tbl.getModel().getValueAt(i, tbl.getColumn("Weight").getModelIndex());
			String notes = (String) tbl.getModel().getValueAt(i, tbl.getColumn("Notes").getModelIndex());
			ModelObject workoutSet = new WorkoutSet(id, selection.getId(),exerciseId, exerciseName, catagory, intensity, reps, weight, notes);
			sets.add(workoutSet);
		}
		return sets;
	}
	
}
