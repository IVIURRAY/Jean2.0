package com.model.apps.readingList;

import java.time.LocalDate;
import java.util.ArrayList;

import com.form.FormBuilder;
import com.form.FormObject;
import com.form.IFormCustomBehaviour;
import com.table3.ColumnFactory;
import com.table3.DateProperty;
import com.table3.ModelObject;
import com.table3.Table3;
import com.table3.TableRowFactory;
import com.table3.Tabluable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.GridPane;

public class Book extends ModelObject implements Tabluable {

	IntegerProperty id = new SimpleIntegerProperty();
	StringProperty title = new SimpleStringProperty();
	StringProperty author = new SimpleStringProperty();
	IntegerProperty totalPageNumber = new SimpleIntegerProperty();
	DateProperty date = new DateProperty();

	public Book(int id, String title, String author, int totalPageNumber, LocalDate date) {
		setId(id);
		setTitle(title);
		setAuthor(author);
		setTotalPageNumber(totalPageNumber);
		setDate(date);
	}

	public Book() {
	}

	public LocalDate getDate() {
		return date.getValue();
	}

	public void setDate(LocalDate arg) {
		date.setValue(arg);
	}

	public DateProperty getDateProperty() {
		return date;
	}

	public final int getId() {
		return id.get();
	}

	public final void setId(int value) {
		id.set(value);
	}

	public IntegerProperty idProperty() {
		return id;
	}

	public final String getTitle() {
		return title.get();
	}

	public final void setTitle(String value) {
		title.set(value);
	}

	public StringProperty titleProperty() {
		return title;
	}

	public final String getAuthor() {
		return author.get();
	}

	public final void setAuthor(String value) {
		author.set(value);
	}

	public StringProperty AuthorProperty() {
		return author;
	}

	public final int getTotalPageNumber() {
		return totalPageNumber.get();
	}

	public final void setTotalPageNumber(int value) {
		totalPageNumber.set(value);
	}

	public IntegerProperty totalPageNumberProperty() {
		return totalPageNumber;
	}

	public ArrayList<Property> display1() {
		ArrayList<Property> properties = new ArrayList<Property>();
		columnNames = new ArrayList<String>();
		properties.add(id);
		properties.add(title);
		properties.add(author);
		properties.add(totalPageNumber);
		properties.add(date);

		columnNames.add("ID");
		columnNames.add("Title");
		columnNames.add("Author");
		columnNames.add("Total Page Number");
		columnNames.add("Date");
		boolean [] req = {false, true, true, true,true};
		requiredFields = req;
		return properties;
	}

	ArrayList<Property> display2() {
		ArrayList<Property> properties = new ArrayList<Property>();
		columnNames = new ArrayList<String>();
		properties.add(title);
		properties.add(author);
		properties.add(totalPageNumber);
		columnNames.add("Title");
		columnNames.add("Author");
		columnNames.add("Total Page Number");
		boolean [] req = { true, true, true,true};
		requiredFields = req;
		return properties;
	}

	@Override
	public GridPane buildTableContainer(ArrayList<IntegerProperty> columnWidths, Table3 table) {

		TableRowFactory tableRowFactory = new TableRowFactory(table, customRowBehaviour);
		tableRowFactory.setupTable(display1(), columnWidths);
		return tableRowFactory.getRoot();

	}

	@Override
	public ArrayList<IntegerProperty> getColumns() {
		ColumnFactory columnFactory = new ColumnFactory();
		columnFactory.setupColumns(display1(), customRowBehaviour);
		return columnFactory.getWidth();
	}

	@Override
	public ArrayList<String> getColumnNames() {

		return columnNames;
	}

	public String getPropertyResult(int number) {
		System.out.println("number: " + number);
		if (number == 0) {
			System.out.println(getId() + "");
			return getId() + "";
		}
		if (number == 1) {
			System.out.println(getTitle() + "");
			return getTitle() + "";
		}

		if (number == 2) {
			System.out.println(getAuthor() + "");
			return getAuthor() + "";
		}

		if (number == 3) {
			System.out.println(getTotalPageNumber() + "");
			return getTotalPageNumber() + "";
		}
		if (number == 4) {
			System.out.println(getTotalPageNumber() + "");
			return getDate() + "";
		} else {
			return null;
		}

	}

	@Override
	public void Delete(int idFromSelected, Table3 table) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getFilterString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelObject extractFromForm(ArrayList<String> results, FormBuilder formBuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void mapModelObjectToForm(ModelObject modelObject, ArrayList<FormObject> formObjects) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modelObjectToSQL(ModelObject oldModelObject, Table3 table, FormBuilder formBuilder) {
		// TODO Auto-generated method stub

	}

	@Override
	public IFormCustomBehaviour getCustomFormBuilderBehaviour() {
		// TODO Auto-generated method stub
		return null;
	}

}
