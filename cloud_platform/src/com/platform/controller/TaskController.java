package com.platform.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.platform.models.TaskInfo;

import java.util.List;
import java.util.logging.Logger;

/**
 * User: dawei, dsybswsw@gmail.com
 * Date: 6/27/13
 */
public class TaskController {
	private TaskController() {
	}

	private final static Logger logger = Logger.getLogger(TaskManager.class.getName());

	private static TaskController taskController;

	public static TaskController getInstance() {
		return taskController;
	}

	public static void initialize() {
		TaskManager.init();
		taskController = new TaskController();
	}

	public boolean buildNewTask(TaskInfo taskInfo) {
		return TaskManager.getInstance().addNewTask(taskInfo);
	}

	public TaskInfo getTask(String taskName) {
		return TaskManager.getInstance().getTask(taskName);
	}

	public String getTasksByType(String taskType) {
		try {
			List<TaskInfo> taskInfoList = TaskManager.getInstance().getTaskByType(taskType);
			JsonArray taskJsonArray = new JsonArray();
			for (TaskInfo taskInfo : taskInfoList) {
				JsonObject taskInfoObject = new JsonObject();
				taskInfoObject.addProperty("taskname", taskInfo.getTaskName());
				taskInfoObject.addProperty("tasktype", taskInfo.getTaskType());
				taskInfoObject.addProperty("description", taskInfo.getDisciption());
				taskInfoObject.addProperty("misc", taskInfo.getMisc());
				taskJsonArray.add(taskInfoObject);
			}
			return taskJsonArray.toString();
		} catch (Exception e) {
			logger.info(e.toString());
			return null;
		}
	}

	public static void main(String[] args) {
		TaskController.initialize();
		boolean result = TaskController.getInstance().buildNewTask(
						new TaskInfo("task1", "classification", "classification", "nothing"));
		if (result) {
			System.out.println("succeed in build new task.");
		} else {
			System.out.println("Failed to build the new task.");
		}
		TaskInfo taskInfo = TaskController.getInstance().getTask("task1");
		if (taskInfo != null) {
			System.out.println(taskInfo.toString());
		} else {
			System.out.println("failed to get task");
		}

		System.out.println(TaskController.getInstance().getTasksByType("all"));
	}
}
