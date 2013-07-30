package com.platform.controller;

import com.platform.models.DataSet;
import com.platform.models.SQLFields;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * User: dawei, dsybswsw@gmail.com
 * Date: 6/29/13
 */
public class DataSetManager {
	private final static String USERNAME = "root";
	private final static String PASSWORD = "root";
	private final static String DB_NAME = "tasks_dev";
	private final static String TABLE_NAME = "datasets";

	private final static Logger logger = Logger.getLogger(DataSetManager.class.getName());

	private DataBase dataBase;

	private DataSetManager(){
		try {
			dataBase = new MysqlDB(USERNAME, PASSWORD, DB_NAME);
		} catch (Exception e) {
			logger.info(e.toString());
		}
	}

	private static DataSetManager taskManager;

	public static void init() {
		taskManager = new DataSetManager();
	}

	public static DataSetManager getInstance() {
		return taskManager;
	}

	public boolean addDataSet(DataSet dataSet) {
		if (dataSet == null) {
			logger.info("dataset empty!");
			return false;
		}
		String name = dataSet.getDataSetName();
		DataSet oldSet = DataSetManager.getInstance().getDataSet(name);
		if (oldSet != null) {
			logger.info(name + " has already exists.");
			return false;
		}
		String type = dataSet.getTaskType();
		String trainingFileName = dataSet.getTrainingFileName();
		String testFileName = dataSet.getTestFileName();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("insert into ");
		sqlBuffer.append(TABLE_NAME);
		sqlBuffer.append(" values ('");
		sqlBuffer.append(name);
		sqlBuffer.append("','");
		sqlBuffer.append(type);
		sqlBuffer.append("','");
		sqlBuffer.append(trainingFileName);
		sqlBuffer.append("','");
		sqlBuffer.append(testFileName);
		sqlBuffer.append("');");
		logger.info(sqlBuffer.toString());
		try {
			dataBase.execute(sqlBuffer.toString());
			return true;
		} catch (SQLException e) {
			logger.info(e.toString());
			return false;
		}
	}

	public DataSet getDataSet(String dataSetName) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select * from ");
		sqlBuilder.append(TABLE_NAME);
		sqlBuilder.append(" where ");
		sqlBuilder.append(SQLFields.DATTASET_NAME);
		sqlBuilder.append(" = '");
		sqlBuilder.append(dataSetName);
		sqlBuilder.append("';");
		try {
			logger.info(sqlBuilder.toString());
			ResultSet resultSet = dataBase.executeQuery(sqlBuilder.toString());
			if (resultSet.next()) {
				String name = resultSet.getString(SQLFields.DATTASET_NAME);
				String type = resultSet.getString(SQLFields.DATASET_TYPE);
				String trainFileName = resultSet.getString(SQLFields.DATASET_TRAINNAME);
				String testFileName = resultSet.getString(SQLFields.DATASET_TESTNAME);
				return new DataSet(name, type, trainFileName, testFileName);
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.info(e.toString());
			return null;
		}
	}

	public List<DataSet> getDataSets(String taskType) throws Exception {
		List<DataSet> dataSets = new ArrayList<DataSet>();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select * from ");
		sqlBuilder.append(TABLE_NAME);
		if (!taskType.equals("all")) {
			sqlBuilder.append(" where ");
			sqlBuilder.append(SQLFields.DATASET_TYPE);
			sqlBuilder.append(" = '");
			sqlBuilder.append(taskType);
			sqlBuilder.append("';");
		}
		logger.info(sqlBuilder.toString());
		ResultSet resultSet = dataBase.executeQuery(sqlBuilder.toString());
		while (resultSet.next()) {
			String name = resultSet.getString(SQLFields.DATTASET_NAME);
			String type = resultSet.getString(SQLFields.DATASET_TYPE);
			String fileName = resultSet.getString(SQLFields.DATASET_TRAINNAME);
			String testMame = resultSet.getString(SQLFields.DATASET_TESTNAME);
			dataSets.add(new DataSet(name, type, fileName, testMame));
		}
		return dataSets;
	}
}
