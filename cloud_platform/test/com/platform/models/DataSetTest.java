package com.platform.models;

import com.platform.controller.DataSetManager;

/**
 * User: dawei, dsybswsw@gmail.com
 * Date: 7/29/13
 */
public class DataSetTest {
	private static void outputDataSet(DataSet dataSet) {
		System.out.println(dataSet.getDataSetName());
		System.out.println(dataSet.getTaskType());
		System.out.println(dataSet.getTrainingFileName());
		System.out.println(dataSet.getTestFileName());
	}

	public static void main(String[] args) {
		DataSetManager.init();
		DataSet testDataSet = new DataSet("test1", "classification", "training1", "testing1");
		DataSetManager.getInstance().addDataSet(testDataSet);
		DataSet newDataSet = DataSetManager.getInstance().getDataSet("test1");
		if (newDataSet != null)
			outputDataSet(newDataSet);
		else
			System.out.println("no this dataset");
	}
}
