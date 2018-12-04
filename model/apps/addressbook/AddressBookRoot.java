package com.model.apps.addressbook;

import java.util.ArrayList;

import com.model.AppHandler;
import com.model.apps.addressbook.elements.Contact;
import com.model.apps.addressbook.elements.ContactAddress;
import com.model.apps.addressbook.elements.ContactEmail;
import com.model.apps.addressbook.elements.ContactNumber;
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

public class AddressBookRoot extends AppHandler {

	Table3 contactsTable;
	Table3 addressTable;
	Table3 emailTable;
	Table3 phoneTable;

	double tableHeight;
	double tableWidth;
	double UiVertical = 80;
	double UiHorizontal = 0;

	
	/////////////////////// CONSTRUCTOR
	public AddressBookRoot(User user, ContainerManager containerManager) {
		super(containerManager);
		this.user = user;
		emailTable = new Table3(new ContactEmail(), "Email Table", this);
		contactsTable = new Table3(new Contact(), "Contacts Table", this);
		phoneTable = new Table3(new ContactNumber(), "Phone Table", this);
		addressTable = new Table3(new ContactAddress(), "Address Table", this);
		launchContactTable(); 
		launchEmailTable();
		launchNumbersTable();
		launchAddressTable();

		containerManager.addConsoleSizeListener(new Listener() {
			@Override
			public void onChange() {
				consoleUpdate(containerManager.getConsoleHeight(), containerManager.getConsoleWidth());
			}
		});
		contactsTable.newTableData(AddressBookFactory.ArrayListToTabluable(AddressBookFactory.getContacts(user)));
	}


	protected void consoleUpdate(double consoleHeight, double consoleWidth) {
		
		this.consoleWidth = consoleWidth;
		this.consoleHeight = consoleHeight;
		
		tableHeight = consoleHeight - UiVertical;
		tableWidth = consoleWidth - UiHorizontal;

		contactsTable.setTableHeight((int) tableHeight);
		contactsTable.setTableWidth((int) (tableWidth * 0.333));

		emailTable.setTableHeight(((int) (tableHeight * 0.333)) );
		emailTable.setTableWidth((int) (tableWidth * 0.66));

		phoneTable.setTableHeight(((int) (tableHeight * 0.333)));
		phoneTable.setTableWidth((int) (tableWidth * 0.66));

		addressTable.setTableHeight(((int) (tableHeight * 0.333)));
		addressTable.setTableWidth((int) (tableWidth * 0.66));
	
	}

	public GridPane getTables() {
		root = new GridPane();
		root.add(contactsTable.getTable(), 0, 0, 1, 3);
		root.add(emailTable.getTable(), 1, 0);
		root.add(phoneTable.getTable(), 1, 1);
		root.add(addressTable.getTable(), 1, 2);
		return root;
	}

	public void refreshContacts() {
		contactsTable.newTableData(AddressBookFactory.ArrayListToTabluable(AddressBookFactory.getContacts(user)));
	}

	void refreshEmail() {
		Contact contact = (Contact) contactsTable.getSelection();
		emailTable.newTableData(AddressBookFactory.ArrayListToTabluable(AddressBookFactory.getEmails(contact.getId())));
	}

	void refreshNumber() {
		Contact contact = (Contact) contactsTable.getSelection();
		phoneTable.newTableData(AddressBookFactory.ArrayListToTabluable(AddressBookFactory.getNumbers(contact.getId())));
	}

	void refreshAddress() {
		Contact contact = (Contact) contactsTable.getSelection();
		addressTable.newTableData(AddressBookFactory.ArrayListToTabluable(AddressBookFactory.getAddress(contact.getId())));

	}

	void launchContactTable() {

		contactsTable.setSelectionBehaviour(new TableSelectionBehaviour() {

			@Override
			public ModelObject getSelection(Table3 table, ObservableList<ModelObject> objects) {
				//System.out.println("selection");
				GridPane gridPane = table.getSelectedGridPane();
				Label label = (Label) gridPane.getChildren().get(0);
				int slectedContactId = Integer.parseInt(label.getText());
				Contact result = null;
				for (ModelObject modelObject : objects) {
					Contact contact = (Contact) modelObject;
					if (contact.getId() == slectedContactId) {
						// Match
						result = contact;
					}
				}
				return result;
			}
		});
		contactsTable.setRefershDataBehaviour(new IRefreshDataBehaviour() {
			@Override
			public void refreshData() {
				refreshContacts();
			}
		});
		contactsTable.addSelectionListener(new Listener() {
			@Override
			public void onChange() {

				try {
					Contact contact = (Contact) contactsTable.getSelection();
					System.out.println(String.format("%s %s", contact.getFirstName(), contact.getFamilyName()));
					emailTable.newTableData(
							AddressBookFactory.ArrayListToTabluable(AddressBookFactory.getEmails(contact.getId())));
					phoneTable.newTableData(
							AddressBookFactory.ArrayListToTabluable(AddressBookFactory.getNumbers(contact.getId())));
					addressTable.newTableData(
							AddressBookFactory.ArrayListToTabluable(AddressBookFactory.getAddress(contact.getId())));
				} catch (NullPointerException e) {
					System.out.println(e.getMessage());
					System.out.println("Selection Cleared");
				}
			}
		});

		contactsTable.initialiseTable();
	}

	void launchEmailTable() {
		emailTable.initialiseTable();
		emailTable.setSelectionBehaviour(new TableSelectionBehaviour() {

			@Override
			public ModelObject getSelection(Table3 table, ObservableList<ModelObject> objects) {
				GridPane gridPane = table.getSelectedGridPane();
				Label label = (Label) gridPane.getChildren().get(0);
				int slectedId = Integer.parseInt(label.getText());
				ContactEmail result = null;
				for (ModelObject modelObject : objects) {
					ContactEmail contact = (ContactEmail) modelObject;
					if (contact.getId() == slectedId) {
						// Match
						result = contact;
					}
				}
				return result;
			}
		});
		emailTable.setRefershDataBehaviour(new IRefreshDataBehaviour() {
			@Override
			public void refreshData() {
				refreshEmail();
			}

		});

		emailTable.addSelectionListener(new Listener() {
			@Override
			public void onChange() {
				try {
					ContactEmail email = (ContactEmail) emailTable.getSelection();
					System.out.println(String.format("%s", email.getEmail()));
				} catch (NullPointerException e) {
					System.out.println("Selection Cleared");
				}
			}
		});

	}

	private void launchNumbersTable() {
		phoneTable.initialiseTable();
		phoneTable.setSelectionBehaviour(new TableSelectionBehaviour() {

			@Override
			public ModelObject getSelection(Table3 table, ObservableList<ModelObject> objects) {
				GridPane gridPane = table.getSelectedGridPane();
				Label label = (Label) gridPane.getChildren().get(0);
				int slectedId = Integer.parseInt(label.getText());
				ContactNumber result = null;
				for (ModelObject modelObject : objects) {
					ContactNumber contact = (ContactNumber) modelObject;
					if (contact.getId() == slectedId) {
						// Match
						result = contact;
					}
				}
				return result;
			}
		});
		phoneTable.setRefershDataBehaviour(new IRefreshDataBehaviour() {
			@Override
			public void refreshData() {
				refreshNumber();
			}

		});

		phoneTable.addSelectionListener(new Listener() {
			@Override
			public void onChange() {
				try {
					ContactNumber number = (ContactNumber) phoneTable.getSelection();
					System.out.println(String.format("%s", number.getNumber()));
				} catch (NullPointerException e) {
					System.out.println("Selection Cleared");
				}
			}
		});

	}

	private void launchAddressTable() {
		addressTable.initialiseTable();
		addressTable.setSelectionBehaviour(new TableSelectionBehaviour() {

			@Override
			public ModelObject getSelection(Table3 table, ObservableList<ModelObject> objects) {
				GridPane gridPane = table.getSelectedGridPane();
				Label label = (Label) gridPane.getChildren().get(0);
				int slectedId = Integer.parseInt(label.getText());
				ContactAddress result = null;
				for (ModelObject modelObject : objects) {
					ContactAddress contact = (ContactAddress) modelObject;
					if (contact.getId() == slectedId) {
						result = contact;
					}
				}
				return result;
			}
		});
		addressTable.setRefershDataBehaviour(new IRefreshDataBehaviour() {
			@Override
			public void refreshData() {
				refreshAddress();
			}

		});

		addressTable.addSelectionListener(new Listener() {
			@Override
			public void onChange() {
				try {
					ContactAddress address = (ContactAddress) addressTable.getSelection();
					System.out.println(String.format("%s", address.getPostcode()));
				} catch (NullPointerException e) {
					System.out.println("Selection Cleared");
				}
			}
		});

	}


	public int getSelectedContactId() {
		
		return contactsTable.getSelection().getId();
	}


	@Override
	public ArrayList<JeanEvent> getEvents() {
		// TODO Auto-generated method stub
		return null;
	}
}
