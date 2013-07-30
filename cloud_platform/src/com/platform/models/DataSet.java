package com.platform.models;

/**
 * User: dawei, dsybswsw@gmail.com
 * Date: 7/28/13
 */
public class DataSet {
	private String dataSetName;
	private String taskType;
	private String trainingFileName;
	private String testFileName;

	public String getTestFileName() {
		return testFileName;
	}

	public void setTestFileName(String testFileName) {
		this.testFileName = testFileName;
	}

	public DataSet(String dataSetName, String taskType, String fileName, String testFileName) {
		this.dataSetName = dataSetName;
		this.taskType = taskType;
		this.trainingFileName = fileName;
		this.testFileName = testFileName;
	}

	public String getDataSetName() {
		return dataSetName;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public void setDataSetName(String dataSetName) {
		this.dataSetName = dataSetName;
	}

	public String getTrainingFileName() {
		return trainingFileName;
	}

	public void setTrainingFileName(String trainingFileName) {
		this.trainingFileName = trainingFileName;
	}

	public void insist() {

	}
}
