package com.model.apps.chores;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JTable;

import com.model.apps.chores.elements.RepeatableTask;
import com.model.sql.SQLHandler;
import com.model.user.User;
import com.table3.ModelObject;
import com.table3.Tabluable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ChoresFactory{
	 static public ObservableList<ModelObject> getChores(User user) {
		 String sql = String.format(
				 "SELECT * FROM (\r\n" + 
				 "SELECT Chores.idChores, Chores.FK_Users_Chores, Chores.Name, Chores.DateAdded, Chores.Frequency, ChoresInstance.Timestamp\r\n" + 
				 "FROM Chores\r\n" + 
				 "LEFT JOIN ChoresInstance \r\n" + 
				 "ON Chores.idChores = ChoresInstance.FK_Chores_ChoresInstance \r\n" + 
				 "WHERE FK_Users_Chores = %s \r\n" + 
				 "ORDER BY ChoresInstance.Timestamp DESC)\r\n" + 
				 " AS sub\r\n" + 
				 "GROUP BY idChores", user.getId());
		JTable tbl = SQLHandler.getTable(sql,"Jean");
		ObservableList<ModelObject> repeatableTasks = FXCollections.observableArrayList();
		for (int i = 0; i < tbl.getRowCount(); i++) {
			int id = (int) tbl.getModel().getValueAt(i, tbl.getColumn("idChores").getModelIndex());
						int userId = (int) tbl.getModel().getValueAt(i, tbl.getColumn("FK_Users_Chores").getModelIndex());
			String name = (String) tbl.getModel().getValueAt(i, tbl.getColumn("Name").getModelIndex());
			Date dateAddedSQL = (Date) tbl.getModel().getValueAt(i, tbl.getColumn("DateAdded").getModelIndex());
			LocalDate dateAdded =dateAddedSQL.toLocalDate();
			Double frequency = (Double) tbl.getModel().getValueAt(i, tbl.getColumn("Frequency").getModelIndex());
			Date timestamp = (Date) tbl.getModel().getValueAt(i, tbl.getColumn("Timestamp").getModelIndex());
			LocalDate latestDate;
			if(timestamp!=null) {
			latestDate =timestamp.toLocalDate();}
			else {
				latestDate = null;
			}
			
			RepeatableTask repeatableTask = new RepeatableTask(id, userId, name, dateAdded, frequency,latestDate);
			repeatableTasks.add(repeatableTask);
		}
		return repeatableTasks;
	}
	 
	 
	 static public ArrayList<Tabluable> ArrayListToTabluable(ObservableList<ModelObject> list) {
		ArrayList<Tabluable> tableInfo = new ArrayList<Tabluable>();
		for (ModelObject o : list) {
			Tabluable t = (Tabluable) o;
			tableInfo.add(t);
		}
		return tableInfo;
	}
}
