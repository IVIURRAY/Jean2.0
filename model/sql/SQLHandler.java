package com.model.sql;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.databaseEnum.DatabaseNames;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class SQLHandler {

	public SQLHandler() {
	}

	public static Connection connectionJean() {
		Connection conn = null;
		try {
			Class.forName(SQLHelper.myDriver);
			conn = DriverManager.getConnection(SQLHelper.Jean_Url, SQLHelper.Jean_Username, SQLHelper.Jean_Password);
			System.out.println("Successful connection...");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Unable to connect to DB.");
		}

		return conn;
	}

	public static Connection connectionJamb() {
		Connection conn = null;
		try {
			Class.forName(SQLHelper.myDriver);
			conn = DriverManager.getConnection(SQLHelper.Jamb_Url, SQLHelper.Jamb_Username, SQLHelper.Jamb_Password);
			System.out.println("Successful connection...");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Unable to connect to DB.");
		}

		return conn;
	}

	public static Connection connectionDev() {
		Connection conn = null;
		try {
			Class.forName(SQLHelper.myDriver);
			conn = DriverManager.getConnection(SQLHelper.Dev_Url, SQLHelper.Dev_Username, SQLHelper.Dev_Password);
			System.out.println("Successful connection...");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Unable to connect to DB.");
		}

		return conn;
	}

	public static ResultSet executeSQL(String sql, String type) {
		// Sorry for ruining this clean system - Can you show me how you would set up an
		// Enum for this?
		Connection conn = null;
		if (type.equals("Jamb")) {
			conn = connectionJamb();
		}
		if (type.equals("Jean")) {
			conn = connectionJean();
		}
		if (type.equals("Dev")) {
			conn = connectionDev();
		}

		ResultSet rs = null;
		if (conn != null) {
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				System.out.println("SQL execution complete.");
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Unable to execute SQL - incorrectly formatted or wrong column/table names.");
			}
		}
		return rs;
	}

	public static void updateSQL(String sql, DatabaseNames databaseName) {

		Connection conn = null;
		switch (databaseName) {
		case JeanDatabase:
			conn = connectionJean();
			break;

		case JambProductDatabse:
			conn = connectionJamb();
			break;
		case JambDevelopmentDatabase:
			conn = connectionDev();
			break;
		}

		if (conn != null) {
			try {
				System.out.println(String.format("Executing update sql: %s", sql));
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.executeUpdate();
				System.out.println("SQL update complete.");
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Unable to update SQL - incorrectly formatted or wrong column/table names.");
			}
		}
		System.out.println("Couldn't make connection");
	}

	public static void deleteRowInTable(DatabaseNames databaseName, String tableName, String condition) {
		String sql = String.format("DELETE FROM `%s`.`%s` WHERE (%s)", databaseName.getDatabaseName(), tableName,
				condition);
		updateSQL(sql, databaseName);
		System.out.println("Deletion complete.");
	}

	public static void insertInTable(DatabaseNames databaseName, String tableName, String[] columnNames,
			String[] columnValues) {

		String sql = String.format("INSERT INTO %s.%s (%s) VALUES ('%s');", databaseName.getDatabaseName(), tableName,
				String.join(", ", columnNames), String.join("', '", columnValues));
		updateSQL(sql, databaseName);
		System.out.println("Insertion complete.");
	}

	public static void editTable(DatabaseNames databaseName, String tableName, String[] columnNames, String[] values,
			String condition) {
		System.out.println(columnNames.length + "Column Names length");
		System.out.println(values.length + " Values Length");
		String[] result = new String[columnNames.length];
		for (int i = 0; i < columnNames.length; i++) {
			String holder = columnNames[i] + " = '" + values[i] + "'";
			System.out.println(holder);
			result[i] = holder;
		}

		String columnNamesAndValues = String.join(", ", result);
		System.out.println(columnNamesAndValues);
		String sql = String.format("UPDATE %s SET %s WHERE %s;", tableName, columnNamesAndValues, condition);
		updateSQL(sql, databaseName);
		System.out.println("Editing complete.");
	}

	public static JTable getTable(String sql, String type) {
		ResultSet rs = executeSQL(sql, type);
		JTable table = new JTable();
		try {
			table = new JTable(buildTableModel(rs));
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Unable to create a table.");
		}
		// JOptionPane.showMessageDialog(null, new JScrollPane(table));
		System.out.println(String.format("Found a table with %s rows.", table.getRowCount()));

		return table;
	}

	public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {

		ResultSetMetaData metaData = rs.getMetaData();

		// names of columns
		Vector<String> columnNames = new Vector<String>();
		int columnCount = metaData.getColumnCount();
		for (int column = 1; column <= columnCount; column++) {
			columnNames.add(metaData.getColumnName(column));
		}

		// data of the table
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while (rs.next()) {
			Vector<Object> vector = new Vector<Object>();
			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				vector.add(rs.getObject(columnIndex));
			}
			data.add(vector);
		}

		return new DefaultTableModel(data, columnNames);

	}

}
