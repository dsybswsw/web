package com.platform.models;

import com.platform.api.ServletGlobalInit;
import com.platform.controller.MysqlDB;

import java.sql.ResultSet;

/**
 * User: dawei, dsybswsw@gmail.com
 * Date: 8/18/13
 */
public class EnvironmentTest {
	public static void main(String[] args) throws Exception {
		ServletGlobalInit.initialize("./WebContent/");
		String sql = "select * from tasks";
		ResultSet rs = MysqlDB.getInstance().executeQuery(sql);
		while (rs.next()) {
			String id = rs.getString(1);
			System.out.println("name: " + id);
		}
		MysqlDB.getInstance().closeAll();
		System.out.println(GlobalConfig.getInstance().getModelWorkDir());
		System.out.println(GlobalConfig.getInstance().getScriptMapFile());
		System.out.println(GlobalConfig.getInstance().getWebAppsDir());
		System.out.println(TrainingConfig.getInstance().gerScriptDir());
	}
}
