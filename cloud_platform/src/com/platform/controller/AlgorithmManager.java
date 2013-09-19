package com.platform.controller;

import com.platform.models.SQLFields;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * User: dawei, dsybswsw@gmail.com
 * Date: 9/19/13
 */
public class AlgorithmManager {
	private final static String TABLE_NAME = "algorithm";

	private final static Logger logger = Logger.getLogger(DataSetManager.class.getName());

	private DataBase dataBase;

	private AlgorithmManager(){
		try {
			dataBase = MysqlDB.getInstance();
		} catch (Exception e) {
			logger.info(e.toString());
		}
	}

	private static AlgorithmManager algorithmManager;

	public static void init() {
		algorithmManager = new AlgorithmManager();
	}

	public static AlgorithmManager getInstance() {
		return algorithmManager;
	}

	public String getAlgorithm(String algorithm) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select * from ");
		sqlBuilder.append(TABLE_NAME);
		sqlBuilder.append(" where ");
		sqlBuilder.append(SQLFields.ALGORITHM_NAME);
		sqlBuilder.append(" = '");
		sqlBuilder.append(algorithm);
		sqlBuilder.append("';");
		try {
			logger.info(sqlBuilder.toString());
			ResultSet resultSet = dataBase.executeQuery(sqlBuilder.toString());
			if (resultSet.next()) {
				String name = resultSet.getString(SQLFields.ALGORITHM_NAME);
				return name;
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.info(e.toString());
			return null;
		}
	}

	public List<String> getAlgorithms(String taskType) throws Exception {
		List<String> algorithms = new ArrayList<String>();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select * from ");
		sqlBuilder.append(TABLE_NAME);
		if (!taskType.equals("all")) {
			sqlBuilder.append(" where ");
			sqlBuilder.append(SQLFields.ALGORITHM_TYPE);
			sqlBuilder.append(" = '");
			sqlBuilder.append(taskType);
			sqlBuilder.append("';");
		}
		logger.info(sqlBuilder.toString());
		ResultSet resultSet = dataBase.executeQuery(sqlBuilder.toString());
		while (resultSet.next()) {
			String name = resultSet.getString(SQLFields.ALGORITHM_NAME);
			algorithms.add(name);
		}
		return algorithms;
	}
}
