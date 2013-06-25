package com.platform.models;

/**
 * User: dawei, dsybswsw@gmail.com
 * Date: 6/23/13
 */
public class Parameters {
	private String taskName;
	private String parameters;

	public Parameters(String taskName, String parameters) {
		this.taskName = taskName;
		this.parameters = parameters;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getParameters() {
		return parameters;
	}
}
