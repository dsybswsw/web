package com.platform.controller;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: dawei, dsybswsw@gmail.com
 * Date: 6/29/13
 */
public abstract class DataBase {
	protected String username;
	protected String password;
	protected String database;
	protected Connection connection = null;

	public DataBase(String username, String passwd, String database) throws Exception {
		init(username, passwd, database);
	}

	protected DataBase() {
	}

	protected void init(String username, String passwd, String database) throws Exception {
		this.username = username;
		this.password = passwd;
		this.database = database;
		Class.forName(getDriver());
		this.connection = (Connection) DriverManager.getConnection(getDatabaseURL() + database, username, password);
	}

	public abstract String getDatabaseURL();
	public abstract String getDriver();

	public ResultSet executeQuery(String sql) throws Exception {
		Statement stmt =  (Statement) this.connection.createStatement();
		ResultSet resultSet = stmt.executeQuery(sql);
		return resultSet;
	}

	public Boolean execute(String sql) throws SQLException {
		Statement stmt =  (Statement) this.connection.createStatement();
		return stmt.execute(sql) ;
	}

	public void closeAll() throws Exception {
		if(null != connection){
			connection.close();
		}
	}
}

