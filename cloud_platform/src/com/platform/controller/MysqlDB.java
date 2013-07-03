package com.platform.controller;

import java.sql.ResultSet;

public class MysqlDB extends DataBase {
	public final static String DRIVER = "com.mysql.jdbc.Driver";

	protected final static String URL =  "jdbc:mysql://localhost:3306/";

	public MysqlDB(String username, String passwd, String database) throws Exception {
		super(username, passwd, database);
	}

	@Override
	public String getDatabaseURL() {
		return URL;
	}

	@Override
	public String getDriver() {
		return DRIVER;
	}

	public static void main(String[] args) throws Exception {
		DataBase dataBase = new MysqlDB("root", "root", "tasks_dev");
		String sql = "select * from tasks";
		ResultSet rs = dataBase.executeQuery(sql);
		while (rs.next()) {
			String id = rs.getString(1);
			System.out.println("name: " + id);
		}
		dataBase.closeAll();
	}
}
