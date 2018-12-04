package com.model.apps.workout;

public class WorkoutPlanner {

	//depending on what day it is make an event for that workout
	
	//Monday
		//squats
	//Tuesday
		//Bench
	//Wednesday
		
	public static double oneRepMaxCalculator(double weight, int reps) {
		return weight/ (1.0278 - (0.0278*reps));
	}
}
