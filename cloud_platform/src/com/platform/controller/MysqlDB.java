package com.platform.controller;

import com.platform.common.util.ConfigParser;
import com.platform.models.SQLFields;

import java.util.Map;

public class MysqlDB extends DataBase {
	public final static String DRIVER = "com.mysql.jdbc.Driver";

	protected final static String URL =  "jdbc:mysql://localhost:3306/";

	public MysqlDB(String profilePath) throws Exception {
		Map<String, String> dbParamMap = ConfigParser.loadConfigFile(profilePath);
		String username = dbParamMap.get(SQLFields.DB_USER_NAME);
		String passwd = dbParamMap.get(SQLFields.DB_PASSWD);
		String dbname = dbParamMap.get(SQLFields.DB_NAME);
		init(username, passwd, dbname);
	}

	private static DataBase dataBase;

	public static void initialize(String profile) {
		try {
			dataBase = new MysqlDB(profile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static DataBase getInstance() {
		return dataBase;
	}

	@Override
	public String getDatabaseURL() {
		return URL;
	}

	@Override
	public String getDriver() {
		return DRIVER;
	}
}
