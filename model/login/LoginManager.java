package com.model.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JTable;
import com.model.sql.SQLHandler;
import com.model.user.User;
import com.observer.login.LoginModelObserver;

public class LoginManager {

	static Connection conn = null;
	static ResultSet rs = null;
	static PreparedStatement ps = null;
	static Statement st = null;
	static String query;
	LoginModelObserver loginModelObserver;
	

	public void setObserver(LoginModelObserver loginModelObserver) {
		this.loginModelObserver = loginModelObserver;
		
	}
	
	public static User findUser(String username, String password) {
		String sql = String.format("SELECT idusers, username, password, firstName, familyName from JeansDatabase.Users WHERE username = '%s' AND password = '%s'", username, password);
		User user = new User();
		JTable table = SQLHandler.getTable(sql,"Jean");
		
		if (table.getRowCount() > 0) {
			user.setId((int) table.getValueAt(0, table.getColumn("idUsers").getModelIndex()));
			user.setUsername(username);
			user.setFirstName((String) table.getValueAt(0, table.getColumn("firstName").getModelIndex()));
			user.setFamilyName((String) table.getValueAt(0, table.getColumn("familyName").getModelIndex()));
		}
		
		return user;
	}
	
	public void checkLogin(String username, String password) {
		loginModelObserver.pushUser(findUser(username, password));
	}
	
//	 public static void main(String[] args) {
//		 User user = new User();
//		 user.setId(1);
//		 findUser("haydn", "pass");
//	 }
}
	
	
	

