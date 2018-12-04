package com.model.apps.qualityControl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.model.AppHandler;
import com.model.apps.addressbook.AddressBookFactory;
import com.model.apps.qualityControl.elements.PassedInspection;
import com.model.apps.qualityControl.elements.QualityReport;
import com.model.apps.schedule.JeanEvent;
import com.model.container.ContainerManager;
import com.model.user.User;
import com.observer.Listener;

import com.table3.IRefreshDataBehaviour;
import com.table3.ModelObject;
import com.table3.Table3;
import com.table3.TableSelectionBehaviour;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

public class QualityControlRoot extends AppHandler{
	QualityControlHeader header;
	QualityControlConsole console;
	Table3 qualityControlTable;
	Table3 passedInspectionTable;
	double tableHeight;
	double tableWidth;
	double UiVertical = 70;
	double UiHorizontal = 5;
	
	boolean loadingThreadRunning = true;
	List<ToLoad> toBeLoaded = Collections.synchronizedList(new ArrayList<ToLoad>());
	
	public QualityControlRoot(ContainerManager containerManager, User user) {
		super(containerManager);
		this.user = user;
		
		qualityControlTable = new Table3(new QualityReport(), "Quality Report", this);
		passedInspectionTable = new Table3(new PassedInspection(), "Passed Inspection", this);
	
		launchConsole();
		launchHeader();
		launchPassedInspectionTable();
		launchQualityControlTable();
		containerManager.addConsoleSizeListener(new Listener() {
			@Override
			public void onChange() {
				consoleUpdate(containerManager.getConsoleHeight(), containerManager.getConsoleWidth());
			}
		});
		//refreshReports();/////////////////////////////////////////////////////////
		Thread imageLoader = new Thread(new Runnable() {

			@Override
			public void run() {
				while(loadingThreadRunning = true) {
					//check if there is anything in the List to be loaded
					for(ToLoad toLoad : toBeLoaded) {
						if(!toLoad.isLoaded()) {
							toLoad.load();
						}
					}
					//if not wait
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			}
		);
		imageLoader.start();
	}
	
	void launchHeader() {
		header = new QualityControlHeader(this);
		header.populateComboBox();
	}

	private void launchConsole() {
		console = new QualityControlConsole();
		
	}

	private void launchQualityControlTable() {
		qualityControlTable.setSelectionBehaviour(new TableSelectionBehaviour() {

			@Override
			public ModelObject getSelection(Table3 table, ObservableList<ModelObject> objects) {
				GridPane gridPane = table.getSelectedGridPane();
				Label label = (Label) gridPane.getChildren().get(0);
				int slectedContactId = Integer.parseInt(label.getText());
				QualityReport result = null;
				for (ModelObject modelObject : objects) {
					QualityReport qualityReport = (QualityReport) modelObject;
					if (qualityReport.getId() == slectedContactId) {
						// Match
						result = qualityReport;
					}
				}
				
				ScrollPane scrollPane = (ScrollPane)gridPane.getChildren().get(7);
				console.setScrollPane(scrollPane.getContent());
				return result;
			}
		});
		qualityControlTable.setRefershDataBehaviour(new IRefreshDataBehaviour() {
			@Override
			public void refreshData() {
				//refreshReports(header.);
			}

	
		});
		qualityControlTable.addSelectionListener(new Listener() {
			@Override
			public void onChange() {

				try {
					QualityReport report = (QualityReport) qualityControlTable.getSelection();
					console.setQualityReport(report);
				} catch (NullPointerException e) {
					System.out.println(e.getMessage());
					System.out.println("Selection Cleared");
				}
			}
		});

		qualityControlTable.initialiseTable();
		
	}
	
	private void launchPassedInspectionTable() {
		passedInspectionTable.setSelectionBehaviour(new TableSelectionBehaviour() {

			@Override
			public ModelObject getSelection(Table3 table, ObservableList<ModelObject> objects) {
				GridPane gridPane = table.getSelectedGridPane();
				Label label = (Label) gridPane.getChildren().get(0);
				int slectedContactId = Integer.parseInt(label.getText());
				PassedInspection result = null;
				for (ModelObject modelObject : objects) {
					PassedInspection qualityReport = (PassedInspection) modelObject;
					if (qualityReport.getId() == slectedContactId) {
						// Match
						result = qualityReport;
					}
				}
				return result;
			}
		});
		passedInspectionTable.setRefershDataBehaviour(new IRefreshDataBehaviour() {
			@Override
			public void refreshData() {
				//refreshReports(header.);
			}

	
		});
		passedInspectionTable.addSelectionListener(new Listener() {
			@Override
			public void onChange() {

				try {
					//QualityReport report = (QualityReport) qualityControlTable.getSelection();
					//console.setQualityReport(report);
				} catch (NullPointerException e) {
					System.out.println(e.getMessage());
					System.out.println("Selection Cleared");
				}
			}
		});

		passedInspectionTable.initialiseTable();
		
	}


	@Override
	public ArrayList<JeanEvent> getEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GridPane getTables() {
		GridPane root = new GridPane();
		root.add(qualityControlTable.getTable(), 1, 1);
		root.add(passedInspectionTable.getTable(),0 ,1 );
		root.add(header.getDisplay(), 0, 0);
		root.add(console.getDisplay(), 2, 1);
	
		return root;
	}

	@Override
	protected void consoleUpdate(double consoleHeight, double consoleWidth) {
		this.consoleWidth = consoleWidth;
		this.consoleHeight = consoleHeight;
		
		tableHeight = consoleHeight - UiVertical;
		tableWidth = consoleWidth - UiHorizontal;

		qualityControlTable.setTableHeight((int) tableHeight*19/20);
		qualityControlTable.setTableWidth((int) (tableWidth*14/30));
		
		passedInspectionTable.setTableHeight((int) tableHeight*19/20);
		passedInspectionTable.setTableWidth((int)(tableWidth*9/30));
		
		console.setHeight((int) tableHeight*19/20);
		console.setWidth((int) tableWidth*7/30);
		
		header.setHeight((int) tableHeight/10);
		header.setWidth((int) tableWidth*20/30);
		
	}
	private void refreshReports(String shipmentReference) {
		qualityControlTable.newTableData(AddressBookFactory.ArrayListToTabluable(QualityControlFactory.getReports(this,shipmentReference)));
		
	}
	private void refreshPassed(String shipmentReference) {
		passedInspectionTable.newTableData(AddressBookFactory.ArrayListToTabluable(QualityControlFactory.getPassed(this,shipmentReference)));
	}
	public void refreshTables(String shipmentReference) {
		refreshReports(shipmentReference);
		refreshPassed(shipmentReference);
	}
	

	public void addToLoad(ToLoad toLoad) {
		toBeLoaded.add(toLoad);
		
	}


}
