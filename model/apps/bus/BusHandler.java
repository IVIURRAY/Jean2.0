package com.model.apps.bus;


import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.Main;
import com.model.AppHandler;
import com.model.apps.schedule.JeanEvent;
import com.model.container.ContainerManager;
import com.observer.Listener;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Example program to list links from a URL.
 */
public class BusHandler extends AppHandler {

	Document MitchamJunction127;
	String MitchamJunction127ETA;
	Document CarshaltonHighStreet127;
	String CarshaltonHighStreet127ETA;
	boolean running = false;
	Thread thread;
	BusWindowController busWindowController;
	GridPane root;
	long timeInterval = 3;

	public BusHandler(ContainerManager containerManager) {
		super(containerManager);
		
	}
	
	public GridPane launch() {

		System.out.println("Lanuching Bus Window");
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/com/model/apps/bus/BusWindow.fxml"));
			root = (GridPane) loader.load();
			busWindowController = loader.getController();
			Stage busWindow = new Stage();
			busWindow.setScene(new Scene(root));
			busWindow.setTitle("Bus Window");
			busWindow.setResizable(false);
			busWindowController.setHandler(this);
			busWindowController.setStage(busWindow);
			containerManager.addThreadStoppingListeners(new Listener() {

				@Override
				public void onChange() {
					running = false;
					
					
				}
				
			});
			//busWindow.show();
			// populate the factories arraylist from database
			// populate and construct the icons arraylist and the actual flowpane
			// factorySQL.LoadTickets();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return root;
		
	}
	
	Runnable updater = new Runnable() {
		int i=0;
	
		public void run() {
			running = true;
			while (running) {
				if(i>=timeInterval) {
					try {
						update();
						i=0;
					} catch (IOException e) {
						System.out.println("Error loading webpages in BUS HANDLER");
						e.printStackTrace();
					}
				}
				else {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							busWindowController.Notes.setText((timeInterval - i)+"");
							
						}
						
					});
					i++;
				}
			
				//busWindowController.setTextMitcham(MitchamJunction127ETA);
				//System.out.println("Mitcham Junction - 127: " + MitchamJunction127ETA);
				//busWindowController.setTextCarshalton(CarshaltonHighStreet127ETA);
				//System.out.println("Carshalton High Street - 127: " + CarshaltonHighStreet127ETA);

	
			}
			;
		}
	};

	
	
	public void startBusMonitor() {
		thread = new Thread(updater);
		thread.start();
		
	}


	public void update() throws IOException {

		MitchamJunction127 = Jsoup.connect("https://tfl.gov.uk/bus/stop/490001194A/mitcham-junction-station?lineId=127")
				.get();
		MitchamJunction127ETA = getLastestETA(MitchamJunction127);
		System.out.println(MitchamJunction127ETA);
		
		CarshaltonHighStreet127 = Jsoup
				.connect("https://tfl.gov.uk/bus/stop/490004821E/carshalton-high-street?lineId=127").get();
		CarshaltonHighStreet127ETA = getLastestETA(CarshaltonHighStreet127);
		
		Platform.runLater(new Runnable() {
			public void run() {
				busWindowController.setTextMitcham(MitchamJunction127ETA);
				busWindowController.setTextCarshalton(CarshaltonHighStreet127ETA);
			}
			
		});
		
	}

	String getLastestETA(Document doc) {
		String title = doc.title();
		//System.out.println(title);
		Elements content = doc.getElementsByClass("live-board-feed");
		String test = content.get(0).toString();
		//System.out.println(test);
		String result="";
		if(title.equals("Carshalton High Street - Transport for London")) {
			String[] split = test.split("Tooting Broadway</span> <span class=\"train-current-state internal\"><strong></strong></span> </span> <span class=\"live-board-eta\">");
			String[] split2 = split[1].split("</span>");
			
			result = split2[0];
			System.out.println(title+result);
		}
		if(title.equals("Mitcham Junction Station - Transport for London")) {
			String[] split = test.split("Purley</span> <span class=\"train-current-state internal\"><strong></strong></span> </span> <span class=\"live-board-eta\">");
			String[] split2 = split[1].split("</span>");
			
			result = split2[0];
			System.out.println(title+result);
		}
		return result;
	}

	public String getMitchamJunction127ETA() {
		return MitchamJunction127ETA;
	}

	public String getCarshaltonHighStreet127ETA() {
		return CarshaltonHighStreet127ETA;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	@Override
	public GridPane getTables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void consoleUpdate(double consoleHeight, double consoleWidth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<JeanEvent> getEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
