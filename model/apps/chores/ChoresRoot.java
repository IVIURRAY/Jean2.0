package com.model.apps.chores;

import java.time.LocalDate;
import java.util.ArrayList;

import com.databaseEnum.DatabaseNames;
import com.model.AppHandler;
import com.model.apps.chores.elements.RepeatableTask;
import com.model.apps.schedule.JeanEvent;
import com.model.container.ContainerManager;
import com.model.sql.SQLHandler;
import com.model.user.User;
import com.observer.Listener;
import com.table3.ButtonRowBehaviour;
import com.table3.IRefreshDataBehaviour;
import com.table3.ModelObject;
import com.table3.Table3;
import com.table3.TableSelectionBehaviour;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ChoresRoot extends AppHandler {

	Table3 choresTable;
	double tableHeight;
	double tableWidth;
	double UiVertical = 80;
	double UiHorizontal = 0;

	public ChoresRoot(User user, ContainerManager containerManager) {
		super(containerManager);

		this.user = user;
		choresTable = new Table3(new RepeatableTask(), "Chores Table", this);
		launchChoresTable();

		containerManager.addConsoleSizeListener(new Listener() {
			@Override
			public void onChange() {
				consoleUpdate(containerManager.getConsoleHeight(), containerManager.getConsoleWidth());
			}
		});
		choresTable.newTableData(ChoresFactory.ArrayListToTabluable(ChoresFactory.getChores(user)));

	}

	private void launchChoresTable() {
		choresTable.setSelectionBehaviour(new TableSelectionBehaviour() {

			@Override
			public ModelObject getSelection(Table3 table, ObservableList<ModelObject> objects) {
				GridPane gridPane = table.getSelectedGridPane();
				Label label = (Label) gridPane.getChildren().get(0);
				int selectedChoresId = Integer.parseInt(label.getText());
				RepeatableTask result = null;
				for (ModelObject modelObject : objects) {
					RepeatableTask task = (RepeatableTask) modelObject;
					System.out.println(String.format("Task id: %s  Selectect Chores id: %s", task.getId()+"",selectedChoresId));
					if (task.getId() == selectedChoresId) {
						// Match
						result = task;
					}
				}
				return result;
			}
		});
		choresTable.setRefershDataBehaviour(new IRefreshDataBehaviour() {
			@Override
			public void refreshData() {
				refreshChores();
			}

		});
		choresTable.addSelectionListener(new Listener() {
			@Override
			public void onChange() {

				try {
					RepeatableTask repeatableTask = (RepeatableTask) choresTable.getSelection();
					System.out.println( repeatableTask.getName());
					
					}catch(Exception e) {
						System.out.println(e.getMessage());
						System.out.println("Selection Cleared");
				
					}
			}
		});
		choresTable.addCustomButtonRowBehaviour(new ButtonRowBehaviour() {

			@Override
			public HBox addBehaviour(HBox previousBehaviour, Table3 table) {
				Button addInstance = new Button("Add Instance");
				previousBehaviour.getChildren().add(addInstance);
				addInstance.setOnAction(new EventHandler<ActionEvent>() {
					@Override public void handle(ActionEvent event) {
						LocalDate now = LocalDate.now();
						String[] columnNames = {"FK_Chores_ChoresInstance","Timestamp"};
						String[] values = {""+table.getSelection().getId(),now.toString()};
						SQLHandler.insertInTable(DatabaseNames.JeanDatabase,"ChoresInstance", columnNames,values );
						refreshChores();
					}
				});
				return previousBehaviour;}
		});
		choresTable.initialiseTable();
		}
	private void refreshChores() {
		// RepeatableTask repeatableTask = (RepeatableTask) choresTable.getSelection();
		choresTable.newTableData(ChoresFactory.ArrayListToTabluable(ChoresFactory.getChores(user)));

	}

	@Override
	public GridPane getTables() {
		root = new GridPane();
		root.add(choresTable.getTable(), 0, 0);
		return root;
	}

	@Override
	protected void consoleUpdate(double consoleHeight, double consoleWidth) {
		this.consoleWidth = consoleWidth;
		this.consoleHeight = consoleHeight;

		tableHeight = consoleHeight - UiVertical;
		tableWidth = consoleWidth - UiHorizontal;

		choresTable.setTableHeight((int) tableHeight);
		choresTable.setTableWidth((int) tableWidth);

	}

	@Override
	public ArrayList<JeanEvent> getEvents() {
		// TODO Auto-generated method stub
		return null;
	}

}
