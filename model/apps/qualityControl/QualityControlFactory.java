package com.model.apps.qualityControl;

import java.sql.Date;

import javax.swing.JTable;

import com.model.apps.addressbook.elements.Contact;
import com.model.apps.qualityControl.elements.PassedInspection;
import com.model.apps.qualityControl.elements.QualityReport;
import com.model.sql.SQLHandler;
import com.table3.ModelObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class QualityControlFactory {

	 static public ObservableList<ModelObject> getReports(QualityControlRoot qualityControlRoot, String shipmentReference2) {
		String sql = "SELECT * FROM QualityReports WHERE ShipmentReference = '" +shipmentReference2+"';";
		System.out.println(sql);
		JTable tbl = SQLHandler.getTable(sql,"Jamb");
		ObservableList<ModelObject> reports = FXCollections.observableArrayList();
		for (int i = 0; i < tbl.getRowCount(); i++) {
			int id = (int) tbl.getModel().getValueAt(i, tbl.getColumn("idQualityReports").getModelIndex());
			Date timestamp = (Date) tbl.getModel().getValueAt(i, tbl.getColumn("Timestamp").getModelIndex());
			String productCode = (String) tbl.getModel().getValueAt(i, tbl.getColumn("ProductCode").getModelIndex());
			String shipmentReference = (String) tbl.getModel().getValueAt(i, tbl.getColumn("ShipmentReference").getModelIndex());
			String issueDescription = (String) tbl.getModel().getValueAt(i, tbl.getColumn("IssueDescription").getModelIndex());
			String type = (String) tbl.getModel().getValueAt(i, tbl.getColumn("Type").getModelIndex());
			int serverity = (int) tbl.getModel().getValueAt(i, tbl.getColumn("Serverity").getModelIndex());
			QualityReport qualityReport = new QualityReport(id, timestamp.toLocalDate(), productCode, shipmentReference, issueDescription,type, serverity);
			qualityReport.setQualityControlRoot(qualityControlRoot);
			reports.add(qualityReport);
		}
		return reports;
	}
	 

	 
	 static public ObservableList<ModelObject> getPassed(QualityControlRoot qualityControlRoot, String shipmentReference2){
		 String sql = "SELECT * FROM QualityPassedInspection WHERE ShipmentReference = '" +shipmentReference2+"';";
		 JTable tbl = SQLHandler.getTable(sql,"Jamb");
		 
		 ObservableList<ModelObject> passedItems = FXCollections.observableArrayList();
			for (int i = 0; i < tbl.getRowCount(); i++) {
				int id = (int) tbl.getModel().getValueAt(i, tbl.getColumn("idQualityPassedInspection").getModelIndex());
				String productCode = (String) tbl.getModel().getValueAt(i, tbl.getColumn("ProductCode").getModelIndex());
				String shipmentReference = (String) tbl.getModel().getValueAt(i, tbl.getColumn("ShipmentReference").getModelIndex());
				int boxNumber = (int) tbl.getModel().getValueAt(i, tbl.getColumn("BoxNumber").getModelIndex());
				PassedInspection passedInspection = new PassedInspection(id,productCode, shipmentReference, boxNumber);;
				passedItems.add(passedInspection);
			}
			return passedItems;
	 }
	 public static String idFormatter(int initalId) {
		 //Must be 5 00000 long
		 int goalStringLength = 5;
		 String result = "";
		 String initalIdAsString = initalId+"";
		 result=initalIdAsString;
		 int zerosToAdd = goalStringLength - initalIdAsString.length();
		 for(int i =0; i <zerosToAdd; i++) {
			 result="0"+result;
		 }
		 System.out.println(result);
		 return result;
	 }

	public static ObservableList<String> getShippingReferences() {
		String sql = "SELECT DISTINCT ShipmentReference FROM JambProduct.QualityReports ORDER BY ShipmentReference ASC;";
		JTable tbl = SQLHandler.getTable(sql,"Jamb");
		ObservableList<String> distinictShippingReferences = FXCollections.observableArrayList();
		for (int i = 0; i < tbl.getRowCount(); i++) {
			String shipmentReference = (String) tbl.getModel().getValueAt(i, tbl.getColumn("ShipmentReference").getModelIndex());
			distinictShippingReferences.add(shipmentReference);
		}
		return distinictShippingReferences;
	}
	 
}
