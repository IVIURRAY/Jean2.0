package com.model.apps.lanternManager;

import java.util.ArrayList;

import com.model.AppHandler;
import com.model.apps.lanternManager.elements.LanternConfig;
import com.model.apps.lanternManager.elements.LanternFamily;
import com.model.apps.lanternManager.elements.LanternSize;
import com.model.apps.schedule.JeanEvent;
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

public class LanternManagerRoot extends AppHandler {

	User user;
	Table3 familyTable;
	Table3 sizeTable;
	Table3 configTable;
	double tableHeight;
	double tableWidth;
	double UiVertical = 80;
	double UiHorizontal = 0;

	public LanternManagerRoot(User user, ContainerManager containerManager) {
		super(containerManager);
		this.user = user;
		familyTable = new Table3(new LanternFamily(), "Lantern Family Table",this);
		sizeTable = new Table3(new LanternSize(), "Lantern Size Table",this);
		configTable= new Table3(new LanternConfig(), "Lantern Config Table",this);
		launchFamilyTable();
		launchSizeTable();
		launchConfigTable();
		containerManager.addConsoleSizeListener(new Listener() {
			@Override
			public void onChange() {
				consoleUpdate(containerManager.getConsoleHeight(), containerManager.getConsoleWidth());
			}
		});
		familyTable.newTableData(LanternManagerFactory.ArrayListToTabluable(LanternManagerFactory.getFamily()));
	}

	 void launchConfigTable() {
			configTable.setSelectionBehaviour(new TableSelectionBehaviour() {

				@Override
				public ModelObject getSelection(Table3 table, ObservableList<ModelObject> objects) {
					/***
					 * GridPane gridPane = table.getSelectedGridPane(); Label label = (Label)
					 * gridPane.getChildren().get(0); int slectedContactId =
					 * Integer.parseInt(label.getText()); LanternFamily result = null; for
					 * (ModelObject modelObject : objects) { LanternFamily lanternFamily =
					 * (LanternFamily) modelObject; if (lanternFamily.getId() == slectedContactId) {
					 * // Match result = lanternFamily; } } return result;
					 ***/
					return null;
				}
			});

			configTable.setRefershDataBehaviour(new IRefreshDataBehaviour() {
				@Override
				public void refreshData() {
					 refreshSize();
				}

		
			});
			configTable.addSelectionListener(new Listener() {
				@Override
				public void onChange() {
					try {
						// LanternFamily lanternFamily = (LanternFamily) familyTable.getSelection();
						// System.out.println(String.format("%s %s", lanternFamily.getName(),
						// lanternFamily.getLTNnumb()));
					} catch (NullPointerException e) {
						// System.out.println(e.getMessage());
						System.out.println("Selection Cleared");
					}
				}
			});
			configTable.initialiseTable();
	}

	private void launchSizeTable() {

		sizeTable.setSelectionBehaviour(new TableSelectionBehaviour() {

			@Override
			public ModelObject getSelection(Table3 table, ObservableList<ModelObject> objects) {
				/***
				 * GridPane gridPane = table.getSelectedGridPane(); Label label = (Label)
				 * gridPane.getChildren().get(0); int slectedContactId =
				 * Integer.parseInt(label.getText()); LanternFamily result = null; for
				 * (ModelObject modelObject : objects) { LanternFamily lanternFamily =
				 * (LanternFamily) modelObject; if (lanternFamily.getId() == slectedContactId) {
				 * // Match result = lanternFamily; } } return result;
				 ***/
				return null;
			}
		});

		sizeTable.setRefershDataBehaviour(new IRefreshDataBehaviour() {
			@Override
			public void refreshData() {
				 refreshSize();
			}

	
		});
		sizeTable.addSelectionListener(new Listener() {
			@Override
			public void onChange() {
				try {
					// LanternFamily lanternFamily = (LanternFamily) familyTable.getSelection();
					// System.out.println(String.format("%s %s", lanternFamily.getName(),
					// lanternFamily.getLTNnumb()));
				} catch (NullPointerException e) {
					// System.out.println(e.getMessage());
					System.out.println("Selection Cleared");
				}
			}
		});
		sizeTable.initialiseTable();
	}

	private void launchFamilyTable() {

		familyTable.setSelectionBehaviour(new TableSelectionBehaviour() {

			@Override
			public ModelObject getSelection(Table3 table, ObservableList<ModelObject> objects) {
				GridPane gridPane = table.getSelectedGridPane();
				Label label = (Label) gridPane.getChildren().get(0);
				int slectedContactId = Integer.parseInt(label.getText());
				LanternFamily result = null;
				for (ModelObject modelObject : objects) {
					LanternFamily lanternFamily = (LanternFamily) modelObject;
					if (lanternFamily.getId() == slectedContactId) {
						// Match
						result = lanternFamily;
					}
				}
				return result;
			}
		});

		familyTable.setRefershDataBehaviour(new IRefreshDataBehaviour() {
			@Override
			public void refreshData() {
				refreshFamily();
			}
		});
		familyTable.addSelectionListener(new Listener() {
			@Override
			public void onChange() {
				try {
					LanternFamily lanternFamily = (LanternFamily) familyTable.getSelection();
					System.out.println(String.format("%s %s", lanternFamily.getName(), lanternFamily.getLTNnumb()));
					sizeTable.newTableData(LanternManagerFactory.ArrayListToTabluable(LanternManagerFactory.getSizes(lanternFamily.getLTNnumb())));
					configTable.newTableData(LanternManagerFactory.ArrayListToTabluable(LanternManagerFactory.getConfigs(lanternFamily.getId())));
				} catch (NullPointerException e) {
					System.out.println("Selection Cleared");
				}
			}
		});
		familyTable.initialiseTable();
	}

	@Override
	public GridPane getTables() {
		root = new GridPane();
		//root.setGridLinesVisible(true);
		root.add(familyTable.getTable(), 0, 0,1,2);
		root.add(sizeTable.getTable(), 1, 0);
		root.add(configTable.getTable(), 1, 1);
		
		//add a console - why isnt the config table working?
		return root;
	}

	@Override
	protected void consoleUpdate(double consoleHeight, double consoleWidth) {
		this.consoleWidth = consoleWidth;
		this.consoleHeight = consoleHeight;
		tableHeight = consoleHeight - UiVertical;
		tableWidth = consoleWidth - UiHorizontal;
		familyTable.setTableHeight((int) tableHeight);
		familyTable.setTableWidth((int) tableWidth / 2);
		sizeTable.setTableHeight((int) (tableHeight*0.3333));
		sizeTable.setTableWidth((int) tableWidth / 2);
		configTable.setTableHeight((int) (tableHeight*0.3333));
		configTable.setTableWidth((int) tableWidth / 2);
	}

	public void refreshFamily() {
		familyTable.newTableData(LanternManagerFactory.ArrayListToTabluable(LanternManagerFactory.getFamily()));
	}
	public void refreshSize() {
		LanternFamily family = (LanternFamily)familyTable.getSelection();
		sizeTable.newTableData(LanternManagerFactory.ArrayListToTabluable(LanternManagerFactory.getSizes(family.getLTNnumb())));
	
	}

	@Override
	public ArrayList<JeanEvent> getEvents() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
