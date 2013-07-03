package com.platform.controller;

import com.platform.models.SQLFields;
import com.platform.models.TaskInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * User: dawei, dsybswsw@gmail.com
 * Date: 6/29/13
 */
public class TaskManager {
	private final static String USERNAME = "root";
	private final static String PASSWORD = "root";
	private final static String DB_NAME = "tasks_dev";
	private final static String TABLE_NAME = "tasks";

	private final static Logger logger = Logger.getLogger(TaskManager.class.getName());

	private DataBase dataBase;

	private TaskManager(){
		try {
			dataBase = new MysqlDB(USERNAME, PASSWORD, DB_NAME);
		} catch (Exception e) {
			logger.info(e.toString());
		}
	}

	private static TaskManager taskManager;

	public static void init() {
		taskManager = new TaskManager();
	}

	public static TaskManager getInstance() {
		return taskManager;
	}

	public boolean addNewTask(TaskInfo taskInfo) {
		String name = taskInfo.getTaskName();
		String type = taskInfo.getTaskType();
		String discription = taskInfo.getDisciption();
		String misc = taskInfo.getMisc();
		TaskInfo oldInfo = getTask(name);
		if (oldInfo != null) {
			logger.info("task " + name + " has already exist.");
			return false;
		}
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("insert into ");
		sqlBuffer.append(TABLE_NAME);
		sqlBuffer.append(" values ('");
		sqlBuffer.append(name);
		sqlBuffer.append("','");
		sqlBuffer.append(type);
		sqlBuffer.append("','");
		sqlBuffer.append(discription);
		sqlBuffer.append("','");
		sqlBuffer.append(misc);
		sqlBuffer.append("');");
		try {
			dataBase.execute(sqlBuffer.toString());
			return true;
		} catch (SQLException e) {
			logger.info(e.toString());
			return false;
		}
	}

	public TaskInfo getTask(String taskName) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select * from ");
		sqlBuilder.append(TABLE_NAME);
		sqlBuilder.append(" where name = '");
		sqlBuilder.append(taskName);
		sqlBuilder.append("';");
		try {
			logger.info(sqlBuilder.toString());
			ResultSet resultSet = dataBase.executeQuery(sqlBuilder.toString());
			if (resultSet.next()) {
				String name = resultSet.getString(SQLFields.NAME);
				String type = resultSet.getString(SQLFields.TYPE);
				String discription = resultSet.getString(SQLFields.DISCRIPTION);
				String misc = resultSet.getString(SQLFields.MISC);
				return new TaskInfo(name, type, discription, misc);
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.info(e.toString());
			return null;
		}
	}

	public List<TaskInfo> getTaskByType(String taskType) throws Exception {
		List<TaskInfo> taskInfos = new ArrayList<TaskInfo>();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select * from ");
		sqlBuilder.append(TABLE_NAME);
		if (!taskType.equals("all")) {
			sqlBuilder.append(" where type = '");
			sqlBuilder.append(taskType);
			sqlBuilder.append("';");
		}
		ResultSet resultSet = dataBase.executeQuery(sqlBuilder.toString());
		while (resultSet.next()) {
			String name = resultSet.getString(SQLFields.NAME);
			String type = resultSet.getString(SQLFields.TYPE);
			String discription = resultSet.getString(SQLFields.DISCRIPTION);
			String misc = resultSet.getString(SQLFields.MISC);
			taskInfos.add(new TaskInfo(name, type, discription, misc));
		}
		return taskInfos;
	}
}
