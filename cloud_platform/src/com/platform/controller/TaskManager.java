package com.platform.controller;

import com.platform.models.DataSet;
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
	private final static String TABLE_NAME = "tasks";

	private final static Logger logger = Logger.getLogger(TaskManager.class.getName());

	private DataBase dataBase;

	private TaskManager(){
		try {
			dataBase = MysqlDB.getInstance();
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
		String dataSetName = taskInfo.getDataSet().getDataSetName();

		TaskInfo oldInfo = getTask(name);
		if (oldInfo != null) {
			logger.info("task " + name + " has already exist.");
			return false;
		}
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("insert into ");
		sqlBuffer.append(TABLE_NAME);
		sqlBuffer.append(" (");
		sqlBuffer.append(SQLFields.NAME);
		sqlBuffer.append(",");
		sqlBuffer.append(SQLFields.TASK_ALGORITHM);
		sqlBuffer.append(",");
		sqlBuffer.append(SQLFields.DESCRIPTION);
		sqlBuffer.append(",");
		sqlBuffer.append(SQLFields.MISC);
		sqlBuffer.append(",");
		sqlBuffer.append(SQLFields.DATTASET_NAME);
		sqlBuffer.append(") values ('");
		sqlBuffer.append(name);
		sqlBuffer.append("','");
		sqlBuffer.append(type);
		sqlBuffer.append("','");
		sqlBuffer.append(discription);
		sqlBuffer.append("','");
		sqlBuffer.append(misc);
		sqlBuffer.append("','");
		sqlBuffer.append(dataSetName);
		sqlBuffer.append("');");
		try {
			dataBase.execute(sqlBuffer.toString());
			return true;
		} catch (SQLException e) {
			logger.info(e.toString());
			return false;
		}
	}

	public boolean deleteTask(String taskName) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("delete from ");
		sqlBuilder.append(TABLE_NAME);
		sqlBuilder.append(" where ");
		sqlBuilder.append(SQLFields.NAME);
		sqlBuilder.append(" = '");
		sqlBuilder.append(taskName);
		sqlBuilder.append("';");
		try {
			dataBase.execute(sqlBuilder.toString());
			return true;
		} catch (SQLException e) {
			logger.info(e.toString());
			return false;
		}
	}

	public boolean deleteTasks(String dataSetName) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("delete from ");
		sqlBuilder.append(TABLE_NAME);
		sqlBuilder.append(" where ");
		sqlBuilder.append(SQLFields.DATTASET_NAME);
		sqlBuilder.append(" = '");
		sqlBuilder.append(dataSetName);
		sqlBuilder.append("';");
		try {
			dataBase.execute(sqlBuilder.toString());
			return true;
		} catch (SQLException e) {
			logger.info(e.toString());
			return false;
		}
	}

	public boolean update(TaskInfo taskInfo) {
		String name = taskInfo.getTaskName();

		TaskInfo oldInfo = getTask(name);
		if (oldInfo == null) {
			logger.info("task " + name + " doesn't exist.");
			return false;
		}
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("update ");
		sqlBuffer.append(TABLE_NAME);
		sqlBuffer.append(" set ");
		sqlBuffer.append(SQLFields.DATTASET_NAME);
		sqlBuffer.append(" = '");
		sqlBuffer.append(taskInfo.getDataSet().getDataSetName());
		sqlBuffer.append("' where ");
		sqlBuffer.append(SQLFields.NAME);
		sqlBuffer.append(" = '");
		sqlBuffer.append(name);
		sqlBuffer.append("';");
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
				String type = resultSet.getString(SQLFields.TASK_ALGORITHM);
				String discription = resultSet.getString(SQLFields.DESCRIPTION);
				String misc = resultSet.getString(SQLFields.MISC);
				String dataSetName = resultSet.getString(SQLFields.DATTASET_NAME);
				TaskInfo taskInfo = new TaskInfo(name, type, discription, misc);
				if (dataSetName != null && dataSetName.length() > 0) {
					DataSet dataSet = DataSetManager.getInstance().getDataSet(dataSetName);
					taskInfo.setDataSet(dataSet);
				}
				return taskInfo;
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
			String type = resultSet.getString(SQLFields.TASK_ALGORITHM);
			String discription = resultSet.getString(SQLFields.DESCRIPTION);
			String misc = resultSet.getString(SQLFields.MISC);
			String dataSetName = resultSet.getString(SQLFields.DATTASET_NAME);
			TaskInfo taskInfo = new TaskInfo(name, type, discription, misc);
			if (dataSetName != null && dataSetName.length() > 0) {
				DataSet dataSet = DataSetManager.getInstance().getDataSet(dataSetName);
				taskInfo.setDataSet(dataSet);
			}
			taskInfos.add(taskInfo);
		}
		return taskInfos;
	}
}
