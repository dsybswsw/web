package com.platform.models;

/**
 * User: dawei, dsybswsw@gmail.com
 * Date: 6/27/13
 */
public class TaskInfo {
	private String taskName;
	private String taskType;
	private String disciption;
	private String misc;

	public TaskInfo(String taskName, String taskType, String disciption, String misc) {
		init(taskName, taskType, disciption, misc);
	}

	public TaskInfo(String taskName, String taskType) {
		init(taskName, taskType, "", "");
	}

	private void init(String taskName, String taskType, String disciption, String misc) {
		this.taskName = taskName;
		this.taskType = taskType;
		this.disciption = disciption;
		this.misc = misc;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getTaskType() {
		return taskType;
	}

	public String getDisciption() {
		return disciption;
	}

	public String getMisc() {
		return misc;
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("(");
		stringBuilder.append(taskName);
		stringBuilder.append(",");
		stringBuilder.append(taskType);
		stringBuilder.append(",");
		stringBuilder.append(disciption);
		stringBuilder.append(",");
		stringBuilder.append(misc);
		stringBuilder.append(")");
		return stringBuilder.toString();
	}
}
