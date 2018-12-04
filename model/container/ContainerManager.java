package com.model.container;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.model.apps.addressbook.AddressBookRoot;
import com.model.apps.bus.BusHandler;
import com.model.apps.chores.ChoresRoot;
import com.model.apps.converter.Converter;
import com.model.apps.developmentGantt.DevelopmentGanttRoot;
import com.model.apps.developmentTickets.DevelopmentTicketsRoot;
import com.model.apps.email.EmailRoot;
import com.model.apps.email.functions.ChoresReminder;
import com.model.apps.lanternManager.LanternManagerRoot;
import com.model.apps.qualityControl.QualityControlRoot;
import com.model.apps.schedule.ScheduleRoot;
import com.model.apps.workout.WorkoutRoot;
import com.model.user.User;
import com.model.user.UserInfo;
import com.observer.Listener;
import com.observer.container.ContainerModelObserver;

public class ContainerManager {

	List<Listener> consoleSizeListeners = new LinkedList<Listener>();
	List<Listener> threadStoppingListeners = new LinkedList<Listener>();
	ContainerModelObserver containerModelObserver;
	User user;
	double windowHeight;
	double windowWidth;
	double consoleHeight;
	double consoleWidth;
	AddressBookRoot addressBook;
	LanternManagerRoot lanternManagerRoot;
	ScheduleRoot scheduleRoot;
	int headerHeight = 20;
	int toolBarWidth = 140;
	boolean clockRunning;
	EmailRoot emailRoot;
	ArrayList<Listener> clockListeners = new ArrayList<Listener>();
	LocalDateTime dateTime;

	  void startClock() {
		clockRunning = true;
		Runnable run = new Runnable() {

			@Override
			public void run() {
				while (clockRunning) {
					dateTime = LocalDateTime.now();
					//System.out.println("Clock Tick: " + dateTime.toString());
					for (Listener listener : clockListeners) {
						listener.onChange();
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		};
		Thread clockThread = new Thread(run);
		clockThread.start();
		ChoresReminder choresReminder = new ChoresReminder();
		addClockListener(new Listener() {
			
			boolean sentTodayFlag = false;
			@Override
			public void onChange() {
				LocalDateTime alarm = LocalDate.now().atTime(6,14);
				
				long diff = ChronoUnit.MINUTES.between(alarm, dateTime);;
				//System.out.println("Time difference - " + diff);
				if(diff==0 && !sentTodayFlag) {
					choresReminder.remind();
					sentTodayFlag = true;
				}	
			}
		});
	}

	public ContainerManager() {
	}

	private void clearListeners() {
		consoleSizeListeners.clear();
	}
	
	public synchronized void addClockListener(Listener listener) {
		clockListeners.add(listener);
	}

	public LocalDateTime getClock() {
		return dateTime;
	}

	public void addThreadStoppingListeners(Listener e) {
		threadStoppingListeners.add(e);
	}

	public void activateThreadStoppingListeners() {
		// setClockRunning(false);
		for (Listener l : threadStoppingListeners) {
			l.onChange();
		}
	}

	public void setClockRunning(boolean input) {
		System.out.println("clock running = " + input);
		this.clockRunning = input;
	}

	public void setObserver(ContainerModelObserver containerModelObserver) {
		this.containerModelObserver = containerModelObserver;
	}

	public void launchInchMmConver() {
		clearListeners();
		activateThreadStoppingListeners();
		Converter converter = new Converter(this);
		containerModelObserver.setDisplay(converter.launch());
	}

	public void setUser(User user) {
		this.user = user;
		containerModelObserver.pushUser(user);
		UserInfo.setUser(user);
		emailRoot = new EmailRoot(user, this);
		startClock();
	}

	public void launchAddressBook() {
		clearListeners();
		activateThreadStoppingListeners();
		addressBook = new AddressBookRoot(user, this);
		System.out.println("Launching Contacts");
		containerModelObserver.setDisplay(addressBook.getTables());
	}
	public void launchQualityControl() {
		clearListeners();
		activateThreadStoppingListeners();
		QualityControlRoot qualityControlRoot = new QualityControlRoot(this, user);
		System.out.println("Launching Contacts");
		containerModelObserver.setDisplay(qualityControlRoot.getTables());
		
	}

	public void launchScheduler() {
		clearListeners();
		activateThreadStoppingListeners();
		scheduleRoot = new ScheduleRoot(user, this);
		containerModelObserver.setDisplay(scheduleRoot.getTables());
	}
	
	public void updateSchedule() {
		containerModelObserver.setDisplay(scheduleRoot.getTables());
	}

	public void launchBus() {
		clearListeners();
		activateThreadStoppingListeners();
		BusHandler busHandler = new BusHandler(this);
		containerModelObserver.setDisplay(busHandler.launch());
		busHandler.startBusMonitor();
	}

	public void launchChores() {
		clearListeners();
		activateThreadStoppingListeners();
		ChoresRoot choreRoot = new ChoresRoot(user, this);
		containerModelObserver.setDisplay(choreRoot.getTables());

	}

	public void launchLanternManager() {
		clearListeners();
		activateThreadStoppingListeners();
		lanternManagerRoot = new LanternManagerRoot(user, this);
		containerModelObserver.setDisplay(lanternManagerRoot.getTables());
	}

	public void launchWorkout() {
		clearListeners();
		activateThreadStoppingListeners();
		WorkoutRoot workoutRoot = new WorkoutRoot(user, this);
		containerModelObserver.setDisplay(workoutRoot.getTables());
	}

	public void launchEmail() {
		clearListeners();
		activateThreadStoppingListeners();
		containerModelObserver.setDisplay(emailRoot.getTables());
	}

	public void updateLayout(double height, double width) {
		this.windowHeight = height;
		this.windowWidth = width;

		consoleHeight = height - headerHeight;
		consoleWidth = width - toolBarWidth;
		for (Listener l : consoleSizeListeners) {
			l.onChange();
		}
	}
	
	public void launchDesignTickets() {
		clearListeners();
		activateThreadStoppingListeners();
		DevelopmentTicketsRoot developmentTicketsRoot = new DevelopmentTicketsRoot(user, this);
		containerModelObserver.setDisplay(developmentTicketsRoot.getTables());
	}
	
	public void launchDevelopmentGantt() {
		clearListeners();
		activateThreadStoppingListeners();
		DevelopmentGanttRoot developmentGanttRoot = new DevelopmentGanttRoot(user, this);
		containerModelObserver.setDisplay(developmentGanttRoot.getTables());
	}
	
	public void addConsoleSizeListener(Listener listener) {
		consoleSizeListeners.add(listener);

	}

	public double getWindowHeight() {
		return windowHeight;
	}

	public double getWindowWidth() {
		return windowWidth;
	}

	public double getConsoleHeight() {
		return consoleHeight;
	}

	public double getConsoleWidth() {
		return consoleWidth;
	}

	public LocalDateTime getDateTime() {
		return dateTime;

	}

	

	

	

}
