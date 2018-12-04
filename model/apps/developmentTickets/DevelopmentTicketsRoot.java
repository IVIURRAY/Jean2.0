package com.model.apps.developmentTickets;

import java.util.ArrayList;

import com.model.AppHandler;
import com.model.apps.developmentTickets.elements.DesignCycle;
import com.model.apps.developmentTickets.elements.Ticket;
import com.model.apps.schedule.JeanEvent;
import com.model.container.ContainerManager;
import com.model.user.User;
import com.observer.Listener;
import com.table3.IRefreshDataBehaviour;
import com.table3.ModelObject;
import com.table3.Table3;
import com.table3.TableHelper;
import com.table3.TableSelectionBehaviour;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class DevelopmentTicketsRoot extends AppHandler{


	Table3 ticketsTable;
	Table3 designCyclesTable;

	double tableHeight;
	double tableWidth;
	double UiVertical = 80;
	double UiHorizontal = 0;
	User user;
	
	public DevelopmentTicketsRoot(User user, ContainerManager containerManager) {
		super(containerManager);
		this.user = user;
		this.ticketsTable = new Table3(new Ticket(), "Ticket Table", this);
		this.designCyclesTable = new Table3(new DesignCycle(), "Design Cycle Table", this);
		launchTicketsTable();
		launchDesignCycleTable();
		containerManager.addConsoleSizeListener(new Listener() {
			@Override
			public void onChange() {
				consoleUpdate(containerManager.getConsoleHeight(), containerManager.getConsoleWidth());
			}
		});
		refreshTickets();
	}

	@Override
	public GridPane getTables() {
		root = new GridPane();
		root.add(ticketsTable.getTable(), 0, 0);
		root.add(designCyclesTable.getTable(), 1, 0);
		return root;
	}

	@Override
	protected void consoleUpdate(double consoleHeight, double consoleWidth) {
		this.consoleWidth = consoleWidth;
		this.consoleHeight = consoleHeight;
		tableHeight = consoleHeight - UiVertical;
		tableWidth = consoleWidth - UiHorizontal;
		ticketsTable.setTableHeight((int) tableHeight);
		ticketsTable.setTableWidth((int) (tableWidth*0.5));
		designCyclesTable.setTableHeight((int) tableHeight);
		designCyclesTable.setTableWidth((int) (tableWidth*0.5));
	}

	
	public void launchTicketsTable() {
		
		ticketsTable.setSelectionBehaviour(new TableSelectionBehaviour() {
			@Override
			public ModelObject getSelection(Table3 table, ObservableList<ModelObject> objects) {
				
				GridPane gridPane = table.getSelectedGridPane();
				Label label = (Label) gridPane.getChildren().get(0);
				int selectedTicketId = Integer.parseInt(label.getText());
				Ticket result = null;
				for (ModelObject modelObject : objects) {
					Ticket ticket = (Ticket) modelObject;
					if (ticket.getId() == selectedTicketId) {
						result = ticket;
					}}
				return result;}});
		
		ticketsTable.setRefershDataBehaviour(new IRefreshDataBehaviour() {
			@Override
			public void refreshData() {
				refreshTickets();
			}});
		
		ticketsTable.addSelectionListener(new Listener() {
			@Override
			public void onChange() {
				try {
					Ticket ticket = (Ticket) ticketsTable.getSelection();
					System.out.println(String.format("%s %s", ticket.getId(), ticket.getTitle()));
					refreshDesignCycles();
				} catch (NullPointerException e) {
					System.out.println(e.getMessage());
					System.out.println("Selection Cleared");
					designCyclesTable.clearTable();
				}}});

		ticketsTable.initialiseTable();
	}
	
	public void launchDesignCycleTable() {
		designCyclesTable.setSelectionBehaviour(new TableSelectionBehaviour() {

			@Override
			public ModelObject getSelection(Table3 table, ObservableList<ModelObject> objects) {
				
				GridPane gridPane = table.getSelectedGridPane();
				Label label = (Label) gridPane.getChildren().get(0);
				int id = Integer.parseInt(label.getText());
				DesignCycle result = null;
				for (ModelObject modelObject : objects) {
					DesignCycle designCycle = (DesignCycle) modelObject;
					if (designCycle.getId() == id) {
						// Match
						result = designCycle;
					}
				}
				return result;
			}
		});
		designCyclesTable.setRefershDataBehaviour(new IRefreshDataBehaviour() {
			@Override
			public void refreshData() {
				refreshDesignCycles();
			}
		});
		designCyclesTable.addSelectionListener(new Listener() {
			@Override
			public void onChange() {
				try {
					DesignCycle designCycle = (DesignCycle) designCyclesTable.getSelection();
					System.out.println(String.format("%s", designCycle.getId()));
			} catch (NullPointerException e) {
					System.out.println(e.getMessage());
					System.out.println("Selection Cleared");
				}
			}
		});
		designCyclesTable.initialiseTable();
	}
	
	public void refreshTickets() {
		ticketsTable.newTableData(TableHelper.ArrayListToTabluable(DevelopmentTicketsFactory.getTickets()));
	}
	public void refreshDesignCycles() {
		designCyclesTable.newTableData(TableHelper.ArrayListToTabluable(DevelopmentTicketsFactory.getDeisgnCycles(ticketsTable.getSelection())));
	}

	public int getSelectedTicketId() {
		return ticketsTable.getSelection().getId();
	}

	@Override
	public ArrayList<JeanEvent> getEvents() {
		// TODO Auto-generated method stub
		return null;
	}

}
