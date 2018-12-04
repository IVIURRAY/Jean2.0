package com.model.apps.converter;

import com.observer.apps.converter.ConverterModelObserver;

public class ConverterManager {
	ConverterModelObserver converterObserver;
	double feet=0;
	double inch=0;
	double m=0;
	double cm=0;
	double mm=0;
	
	public ConverterManager(ConverterModelObserver converterObserver){
	this.converterObserver = converterObserver;
	}

	public void updateFeet(Double input) {
		// starting from input make everything else
		feet = input;
		inch = input*12;
		mm = inch*25.4;
		cm = mm/10;
		m = mm/1000;
		push();
	}

	public void updateInch(Double input) {
		// starting from input make everything else
		inch = input;
		mm = input * 25.4;
		cm = mm/10;
		m = mm/1000;
		feet = inch/12;
		push();
	}

	public void updateM(Double input) {
		// starting from input make everything else
		m = input;
		mm = input*1000;
		cm = mm/10;
		inch = mm/25.4;
		feet = inch/12;
		push();
	}

	public void updateCm(Double input) {
		// starting from input make everything else
		cm = input;
		mm = input*10;
		m = mm/1000;
		inch = mm/25.4;
		feet = inch/12;
		push();
	}

	public void updateMm(Double input) {
		// starting from input make everything else
		mm = input;
		cm = mm/10;
		m = mm/1000;
		inch = mm/25.4;
		feet = inch/12;
		push();
	}

	void push() {
		converterObserver.pushResults(feet, inch, m, cm, mm);
	}
	public void addListener(ConverterModelObserver converterObserver) {
		this.converterObserver = converterObserver;
		
	}
	
}
