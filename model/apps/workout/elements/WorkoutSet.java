package com.model.apps.workout.elements;

import java.util.ArrayList;

import com.databaseEnum.DatabaseNames;
import com.form.FormBuilder;
import com.form.FormObject;
import com.form.IFormCustomBehaviour;
import com.form.SetModelObjectBehaviour;
import com.form.comboField.ComboField;
import com.form.dateField.DateField;
import com.form.doubleField.DoubleField;
import com.form.intField.IntField;
import com.form.stringField.StringField;
import com.model.AppHandler;
import com.model.apps.addressbook.elements.Contact;
import com.model.apps.chores.elements.RepeatableTask;
import com.model.apps.workout.WorkoutFactory;
import com.model.apps.workout.WorkoutRoot;
import com.model.sql.SQLHandler;
import com.model.user.UserInfo;
import com.observer.Listener;
import com.table3.IFormSubmissionBehaviour;
import com.table3.ModelObject;
import com.table3.Table3;
import com.table3.Tabluable;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class WorkoutSet extends ModelObject {

	IntegerProperty id;
	IntegerProperty workoutId;
	IntegerProperty exerciseId;
	StringProperty exerciseName;
	StringProperty exerciseCatagory;
	IntegerProperty exerciseIntensity;
	IntegerProperty reps;
	DoubleProperty weight;
	StringProperty notes;

	public WorkoutSet(int id, int workoutId, int exerciseId, String exerciseName, String exerciseCatagory,
			int exerciseIntensity, int reps, double weight, String notes) {
		this.id = new SimpleIntegerProperty();
		this.workoutId = new SimpleIntegerProperty();
		this.exerciseId = new SimpleIntegerProperty();
		this.exerciseName = new SimpleStringProperty();
		this.exerciseCatagory = new SimpleStringProperty();
		this.exerciseIntensity = new SimpleIntegerProperty();
		this.reps = new SimpleIntegerProperty();
		this.weight = new SimpleDoubleProperty();
		this.notes = new SimpleStringProperty();

		this.id.set(id);
		this.workoutId.set(workoutId);
		this.exerciseId.set(exerciseId);
		this.exerciseName.set(exerciseName);
		this.exerciseCatagory.set(exerciseCatagory);
		this.reps.set(reps);
		this.weight.set(weight);
		this.notes.set(notes);
		this.objectName="Workout Set";
	}

	public WorkoutSet() {

		this.id = new SimpleIntegerProperty();
		this.workoutId = new SimpleIntegerProperty();
		this.exerciseId = new SimpleIntegerProperty();
		this.exerciseName = new SimpleStringProperty();
		this.exerciseCatagory = new SimpleStringProperty();
		this.exerciseIntensity = new SimpleIntegerProperty();
		this.reps = new SimpleIntegerProperty();
		this.weight = new SimpleDoubleProperty();
		this.notes = new SimpleStringProperty();
		this.objectName="Workout Set";
	}

	public IntegerProperty getIdProperty() {
		return id;
	}

	public IntegerProperty getWorkoutIdProperty() {
		return workoutId;
	}

	public IntegerProperty getExerciseIdProperty() {
		return exerciseId;
	}

	public StringProperty getExerciseNameProperty() {
		return exerciseName;
	}

	public StringProperty getExerciseCatagoryProperty() {
		return exerciseCatagory;
	}

	public IntegerProperty getExerciseIntensityProperty() {
		return exerciseIntensity;
	}

	public IntegerProperty getRepsProperty() {
		return reps;
	}

	public DoubleProperty getWeightProperty() {
		return weight;
	}

	public StringProperty getNotesProperty() {
		return notes;
	}

	public int getWorkoutId() {
		return workoutId.get();
	}

	public int getExerciseId() {
		return exerciseId.get();
	}

	public String getExerciseName() {
		return exerciseName.get();
	}

	public String getExerciseCatagory() {
		return exerciseCatagory.get();
	}

	public int getExerciseIntensity() {
		return exerciseIntensity.get();
	}

	public int getReps() {
		return reps.get();
	}

	public Double getWeight() {
		return weight.get();
	}

	public String getNotes() {
		return notes.get();
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public void setWorkoutId(int workoutId) {
		this.workoutId.set(workoutId);
	}

	public void setExerciseId(int exerciseId) {
		this.exerciseId.set(exerciseId);
	}

	public void setExerciseName(String exerciseName) {
		this.exerciseName.set(exerciseName);
	}

	public void setExerciseCatagory(String exerciseCatagory) {
		this.exerciseCatagory.set(exerciseCatagory);
	}

	public void setExercisevoidensity(int intensisty) {
		this.exerciseIntensity.set(intensisty);
	}

	public void setReps(int reps) {
		this.reps.set(reps);
	}

	public void setWeight(double weight) {
		this.weight.set(weight);
	}

	public void setNotes(String notes) {
		this.notes.set(notes);
	}

	@Override
	public int getId() {
		return id.get();
	}

	@Override
	public IFormCustomBehaviour getCustomFormBuilderBehaviour() {
		// this is where the instructions to add a combo box are.
		return new IFormCustomBehaviour() {

			@Override
			public ArrayList<FormObject> run(ArrayList<FormObject> formObjects) {
				ArrayList<Tabluable> exercises = WorkoutFactory.ArrayListToTabluable(WorkoutFactory.getExercise());
				ObservableList<String> exerciseCombos = FXCollections.observableArrayList();
				ComboField combo = new ComboField("Exercise");
				for (Tabluable t : exercises) {
					Exercise e = (Exercise) t;
					String option = String.format("%s - %s - %s - %s ", e.getId(), e.getExercise(), e.getCatagory(),
							e.getIntensity());
					// String option = String.format("%s ",e.getExercise());
					
					exerciseCombos.add(option);

				}
				combo.setup(exerciseCombos);

				formObjects.add(combo);

				return formObjects;
			}

		};
	}

	@Override
	public void Delete(int idFromSelected, Table3 table) {
		System.out.println("Delete " + objectName);
		SQLHandler.deleteRowInTable(DatabaseNames.JeanDatabase,"WorkoutSets", "idWorkoutSets = " + idFromSelected);
		table.refreshData();

	}

	@Override
	public void Create(Table3 table) {
		FormBuilder formBuilder = new FormBuilder("Create" + objectName);
		System.out.println("Create" + objectName);
		formBuilder.setSubmissionBehaviour(new IFormSubmissionBehaviour() {
			@Override
			public ModelObject Extract(ArrayList<String> results) {
				return extractFromForm(results, formBuilder);
			}
		});
		formBuilder.setSubmissionListener(new Listener() {
			@Override
			public void onChange() {
				modelObjectToSQL(null, table, formBuilder);
			}
		});
		formBuilder.setCustomBehaviour(getCustomFormBuilderBehaviour());
		formBuilder.create(display2(), columnNames, requiredFields);

	}

	@Override
	public void Edit(ModelObject modelObject, Table3 table) {

		FormBuilder formBuilder = new FormBuilder("Edit " + objectName);
		formBuilder.setSetModelObjectBehavour(new SetModelObjectBehaviour() {

			@Override
			public void run(ModelObject modelObject, ArrayList<FormObject> formObjects) {
				mapModelObjectToForm(modelObject, formObjects);
			}
		});
		formBuilder.setCustomBehaviour(getCustomFormBuilderBehaviour());
		formBuilder.setSubmissionBehaviour(new IFormSubmissionBehaviour() {
			@Override
			public ModelObject Extract(ArrayList<String> results) {
				return extractFromForm(results, formBuilder);
			}
		});

		formBuilder.setSubmissionListener(new Listener() {
			@Override
			public void onChange() {
				modelObjectToSQL(modelObject, table, formBuilder);
			}
		});

		formBuilder.create(display2(), columnNames, requiredFields);
		formBuilder.setModelObject(modelObject);
	}

	@Override
	public ArrayList<Property> display1() {
		ArrayList<Property> properties = new ArrayList<Property>();
		columnNames = new ArrayList<String>();
		properties.add(id);

		properties.add(exerciseName);

		properties.add(exerciseCatagory);
		properties.add(exerciseIntensity);

		properties.add(reps);
		properties.add(weight);
		properties.add(notes);

		columnNames.add("ID");
		columnNames.add("Exercise");
		columnNames.add("Catagory");
		columnNames.add("Intensity");
		columnNames.add("Reps");
		columnNames.add("Weight");
		columnNames.add("Notes");
		boolean [] req = {false, true, true,true, true,true, true};
		requiredFields = req;
		return properties;
	}

	public ArrayList<Property> display2() {
		ArrayList<Property> properties = new ArrayList<Property>();
		columnNames = new ArrayList<String>();

		properties.add(reps);
		properties.add(weight);
		properties.add(notes);

		columnNames.add("Reps");
		columnNames.add("Weight");
		columnNames.add("Notes");
		boolean [] req = {true, true, true};
		requiredFields = req;
		return properties;
	}

	@Override
	public String getFilterString() {
		return String.format("%s %s %s", exerciseName.get(), exerciseCatagory.get(), notes.get());
	}

	@Override
	public ModelObject extractFromForm(ArrayList<String> results, FormBuilder formBuilder) {
		WorkoutSet workoutSet = new WorkoutSet();
		String exercieSelection = results.get(0);
		ArrayList<Tabluable> exercises = WorkoutFactory.ArrayListToTabluable(WorkoutFactory.getExercise());
		int matchId = 0;
		for (Tabluable t : exercises) {
			Exercise e = (Exercise) t;
			String option = String.format("%s - %s - %s - %s ", e.getId(), e.getExercise(), e.getCatagory(),
					e.getIntensity());
			if (exercieSelection.equals(option)) {
				matchId = e.getId();
				break;
			}
		}
	
		workoutSet.setExerciseId(matchId);
		workoutSet.setReps(Integer.parseInt(results.get(1)));
		workoutSet.setWeight(Double.parseDouble(results.get(2)));
		workoutSet.setNotes(results.get(3));
		// push combo
		;
		ModelObject result = (ModelObject) workoutSet;
		return result;
	}

	@Override
	public void mapModelObjectToForm(ModelObject modelObject, ArrayList<FormObject> formObjects) {
		WorkoutSet workoutSet = (WorkoutSet) modelObject;

		ArrayList<Tabluable> exercises = WorkoutFactory.ArrayListToTabluable(WorkoutFactory.getExercise());
		Exercise match = null;
		for (Tabluable t : exercises) {
			Exercise e = (Exercise) t;
			int option = e.getId();

			if (workoutSet.getExerciseId() == option) {
				match = e;
				break;
			}
		}

		ComboField exercise = (ComboField) formObjects.get(0);
		exercise.setInput(String.format("%s - %s - %s - %s ", match.getId(), match.getExercise(), match.getCatagory(),
				match.getIntensity()));

		IntField reps = (IntField) formObjects.get(1);
		reps.setInput(workoutSet.getReps());

		DoubleField weight = (DoubleField) formObjects.get(2);
		weight.setInput(workoutSet.getWeight());

		StringField notes = (StringField) formObjects.get(3);
		notes.setInput(workoutSet.getNotes());
	}

	@Override
	public void modelObjectToSQL(ModelObject oldModelObject, Table3 table, FormBuilder formBuilder) {
		ModelObject submittedObject = formBuilder.getSubmittedObject();
		WorkoutSet newWorkoutSet = (WorkoutSet) submittedObject;

		int exerciseID = newWorkoutSet.getExerciseId();
		int reps = newWorkoutSet.getReps();
		double weight = newWorkoutSet.getWeight();
		String notes = newWorkoutSet.getNotes();
		WorkoutRoot workoutRoot = (WorkoutRoot) table.getAppHandler();
		
		
		String[] columnNames = { "FK_Workouts_WorkoutSets", "FK_WorkoutExercises_WorkoutSets", "Reps", "Weight",
				"Notes" };
		
		String[] values = { workoutRoot.getSelectedWorkoutId() + "", exerciseID + "", reps + "", weight + "", notes };

		if (oldModelObject == null) {
			SQLHandler.insertInTable(DatabaseNames.JeanDatabase,"WorkoutSets", columnNames, values);
		} else {
			int id = oldModelObject.getId();
			SQLHandler.editTable(DatabaseNames.JeanDatabase,"WorkoutSets", columnNames, values, "idWorkoutSets = " + id);
		}

		formBuilder.closeForm();
		table.refreshData();

	}

	@Override
	public String getPropertyResult(int number) {
		if (number == 0) {
			return getId() + "";
		}
		if (number == 1) {
			return getExerciseName() + "";
		}

		if (number == 2) {
			return getExerciseCatagory() + "";
		}
		if (number == 3) {
			return getExerciseIntensity() + "";
		}
		if (number == 4) {
			return getReps() + "";
		}
		if (number == 5) {
			return getWeight() + "";
		}
		if (number == 6) {
			return getNotes() + "";
		}
		else {
			return null;
		}

	}
}
