package com.model.cache;

import javax.swing.JTable;

public class TableCache {
	
	static JTable addrsTbl = null;
	
	public static void setAddressTable(JTable tbl) {
		TableCache.addrsTbl =  tbl;
	}
	
	public static JTable getAddressTable() {
		return addrsTbl;
	}

}
