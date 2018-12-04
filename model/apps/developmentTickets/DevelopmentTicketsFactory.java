package com.model.apps.developmentTickets;

import java.sql.Date;
import java.time.LocalDate;

import javax.swing.JTable;

import com.model.apps.developmentTickets.elements.DesignCycle;
import com.model.apps.developmentTickets.elements.Ticket;
import com.model.sql.SQLHandler;
import com.table3.ModelObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DevelopmentTicketsFactory {

/***
SELECT Active_Projects.Title, Active_Projects.Type, Active_Projects.Value ,Design_Cycles.Description, Design_Cycles.Date_Created, Design_Cycles.Date_Completed
FROM JambDevelopment.Active_Projects
JOIN JambDevelopment.Design_Cycles 
ON Active_Projects.idActive_Projects = Design_Cycles.Parent_ID
WHERE Design_Cycles.Date_Created between '2018-05-04 00:00:00' and '2018-05-11 23:59:00' 
order by Parent_ID desc;

Tickets have design cycles which are chronological steps that were taken to complete the project.
Tickets also have events/ deadlines which are displayed in a gantt chart.
events should just use dates for now and the complexity/ accuracy can be improved as we go.
 * @return
 */

	public static ObservableList<ModelObject> getTickets(){
		String[] columnNames = {"idActive_Projects","Title","Type","Objective","Value","Date_Created","Date_Completed"};
		String sql = String.format("SELECT * FROM Active_Projects;", String.join(", ", columnNames));
		System.out.println(sql);
		JTable tbl = SQLHandler.getTable(sql,"Dev");
		ObservableList<ModelObject> tickets = FXCollections.observableArrayList();
		for (int i = 0; i < tbl.getRowCount(); i++) {
			int id = (int) tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[0]).getModelIndex());
			String title = (String) tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[1]).getModelIndex());
			String type = (String) tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[2]).getModelIndex());
			String objective = (String) tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[3]).getModelIndex());
			int value = (int) tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[4]).getModelIndex());
			Date dateCreated1 = (Date) tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[5]).getModelIndex());
			Date dateCompleted1 = (Date) tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[6]).getModelIndex());
			LocalDate dateCreated =null;
			LocalDate dateCompleted =null;
			if(dateCreated1 != null) {
				dateCreated = dateCreated1.toLocalDate();
			}
			if(dateCompleted1 != null) {
				dateCompleted = dateCompleted1.toLocalDate();
			}
			Ticket family = new Ticket(id, title, type, objective, value, dateCreated, dateCompleted);
			tickets.add(family);
		}
		return tickets;
	}

	public static ObservableList<ModelObject>  getDeisgnCycles(ModelObject selection) {
		Ticket ticket = (Ticket)selection;
	
		String[] columnNames = {"idDesign_Cycles","Parent_ID","Description","Date_Created","Date_Completed"};
		String sql = String.format("SELECT %s FROM Design_Cycles WHERE Parent_ID = %s;", String.join(", ", columnNames), ticket.getId()+"");
		System.out.println(sql);
		JTable tbl = SQLHandler.getTable(sql,"Dev");
		ObservableList<ModelObject> designCycles = FXCollections.observableArrayList();
		for (int i = 0; i < tbl.getRowCount(); i++) {
			int id = (int) tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[0]).getModelIndex());
			int parentId = (int) tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[0]).getModelIndex());
			String description = (String) tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[2]).getModelIndex());
			Date dateCreated1 = (Date) tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[3]).getModelIndex());
			Date dateCompleted1 = (Date) tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[4]).getModelIndex());
			LocalDate dateCreated =null;
			LocalDate dateCompleted =null;
			if(dateCreated1 != null) {
				dateCreated = dateCreated1.toLocalDate();
			}
			if(dateCompleted1 != null) {
				dateCompleted = dateCompleted1.toLocalDate();
			}
			DesignCycle designCycle = new DesignCycle(id, parentId, description, dateCreated, dateCompleted);
			designCycles.add(designCycle);
		}
		return designCycles;
	}
	
}
