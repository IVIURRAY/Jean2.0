package com.model.apps.lanternManager;

import java.util.ArrayList;

import javax.swing.JTable;

import com.model.apps.lanternManager.elements.LanternConfig;
import com.model.apps.lanternManager.elements.LanternFamily;
import com.model.apps.lanternManager.elements.LanternSize;
import com.model.sql.SQLHandler;
import com.table3.ModelObject;
import com.table3.Tabluable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LanternManagerFactory {
	
	 static public ArrayList<Tabluable> ArrayListToTabluable(ObservableList<ModelObject> list) {
		ArrayList<Tabluable> tableInfo = new ArrayList<Tabluable>();
		for (ModelObject o : list) {
			Tabluable t = (Tabluable) o;
			tableInfo.add(t);
		}
		return tableInfo;
	}
	static public ObservableList<ModelObject> getFamily(){
		String[] columnNames = {"idLanternFamily","Type","Name","LTNnumb", "Description", 
				"QualityIssuesPlaceholder", "CustomDims", "IP44", "IP20", "ULDry", "ULDamp","ULWet"};
		String sql = String.format("SELECT %s FROM LanternFamily", String.join(", ", columnNames));
		System.out.println(sql);
		JTable tbl = SQLHandler.getTable(sql,"Jamb");
		ObservableList<ModelObject> familyLanterns = FXCollections.observableArrayList();
		for (int i = 0; i < tbl.getRowCount(); i++) {
			int id = (int) tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[0]).getModelIndex());
			String type = (String) tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[1]).getModelIndex());
			String name = (String) tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[2]).getModelIndex());
			String LTNnumb = (String) tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[3]).getModelIndex());
			String description = (String) tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[4]).getModelIndex());
			String qualityIssuePlaceholder = (String) tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[5]).getModelIndex());
			int customDims = (int) tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[6]).getModelIndex());
			Boolean IP44 = (boolean) Boolean.parseBoolean("" + tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[7]).getModelIndex()));
			Boolean IP20 = (boolean) Boolean.parseBoolean("" + tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[8]).getModelIndex()));
			Boolean ULDry = (boolean) Boolean.parseBoolean("" + tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[9]).getModelIndex()));
			Boolean ULDamp = (boolean) Boolean.parseBoolean("" + tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[10]).getModelIndex()));
			Boolean ULWet = (boolean) Boolean.parseBoolean("" + tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[11]).getModelIndex()));
			LanternFamily family = new LanternFamily(id, type, name, LTNnumb, description, qualityIssuePlaceholder, customDims, IP44, IP20, ULDry, ULDamp, ULWet);
			familyLanterns.add(family);
		}
		return familyLanterns;
	}
	
	public static ObservableList<ModelObject> getSizes(String familyId) {
		String[] columnNames = {"idLanternSize", "LanternFamily", "Height", "Width", "Depth", "Size", "Price", "CustomDims"};
		String sql = String.format("SELECT %s FROM LanternSize WHERE LanternFamily = '%s'", String.join(", ", columnNames),familyId);
		System.out.println(sql);
		JTable tbl = SQLHandler.getTable(sql,"Jamb");
		ObservableList<ModelObject> sizes = FXCollections.observableArrayList();
		for (int i = 0; i < tbl.getRowCount(); i++) {
			int id = (int) tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[0]).getModelIndex());
			String lanternNumber = (String) tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[1]).getModelIndex());
			double height = Double.parseDouble(""+ tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[2]).getModelIndex()));
			double width=  Double.parseDouble(""+ tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[3]).getModelIndex()));
			double depth=  Double.parseDouble(""+tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[4]).getModelIndex()));
			String sizeLabel= (String) tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[5]).getModelIndex());
			int price= (int) tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[6]).getModelIndex());
			String customDims= (String) tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[7]).getModelIndex());
			System.out.println(String.format("%s %s %s %s %s %s %s %s ", id, lanternNumber, height, width, depth, sizeLabel, price, customDims));
			LanternSize sizeObject = new LanternSize(id, lanternNumber, height, width, depth, sizeLabel, price, customDims);
			sizes.add(sizeObject);
		}
		return sizes;
	}
	public static ObservableList<ModelObject> getConfigs(int familyid) {
		
		String[] columnNames = {"idLanternConfig","FamilyId","Certification","SolutionId"};
		String sql = String.format("SELECT %s FROM LanternConfig WHERE FamilyId = '%s'", String.join(", ", columnNames),familyid);
		System.out.println(sql);
		JTable tbl = SQLHandler.getTable(sql,"Jamb");
		ObservableList<ModelObject> configs = FXCollections.observableArrayList();
		
		for (int i = 0; i < tbl.getRowCount(); i++) {	
			int id = (int) tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[0]).getModelIndex());
			int familyId2 = (int) tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[1]).getModelIndex());
			String cert = (String) tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[2]).getModelIndex());
			int solutionId = (int) tbl.getModel().getValueAt(i, tbl.getColumn(columnNames[3]).getModelIndex());
			System.out.println(String.format("%s %s %s %s", id, familyId2, cert, solutionId));
			LanternConfig configObject = new LanternConfig(id, familyId2, cert, solutionId);
			configs.add(configObject);
			}

		return configs;
	}
	
}
