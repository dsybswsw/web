package com.platform.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.platform.controller.DataSetManager;
import com.platform.controller.TaskController;
import com.platform.controller.TaskManager;
import com.platform.models.DataSet;
import com.platform.models.GlobalConfig;
import com.platform.models.TaskInfo;
import com.platform.models.TrainingConstants;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Logger;

/**
 * User: dawei, dsybswsw@gmail.com
 * Date: 6/27/13
 */
public class TaskServlet extends BaseServlet {
	private final static Logger logger = Logger.getLogger(ModelTrainingServlet.class.getName());

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	// Get the task information.
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String taskType = request.getParameter("tasktype");
		String tasks = TaskController.getInstance().getTasksByType(taskType);
		response.getWriter().write(tasks);
	}

	// Add new task.
	public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding(ServletConstants.CHN_ENCODING);
		String jsonString = request.getReader().readLine();
		JsonParser jsonParser = new JsonParser();
		logger.info("received json : " + jsonString);
		JsonElement jsonElement = jsonParser.parse(jsonString);
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		String taskName = jsonObject.get("taskname").getAsString();
		String taskType = jsonObject.get("tasktype").getAsString();
		String discription = jsonObject.get("description").getAsString();
		String datasetName = jsonObject.get("dataset_name").getAsString();
		String misc = "";
		logger.info("build new task " + taskName + ", " + taskType);
		TaskInfo newTask = new TaskInfo(taskName, taskType, discription, misc);
		newTask.setDataSet(DataSetManager.getInstance().getDataSet(datasetName));
		boolean isBuilt = TaskController.getInstance().buildNewTask(newTask);
		if (isBuilt) {
			response.getWriter().write("succeed");
		} else {
			response.getWriter().write("failed");
		}
	}

	// Connect the dataset with task.
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		handleDataSetSelection(request, response);
	}

	private void handleDataSetSelection(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// response.getWriter().write("Not file upload request");
		request.setCharacterEncoding(ServletConstants.CHN_ENCODING);
		String jsonString = request.getReader().readLine();
		JsonParser jsonParser = new JsonParser();
		logger.info("received json : " + jsonString);
		JsonElement jsonElement = jsonParser.parse(jsonString);
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		String taskName = jsonObject.get("taskname").getAsString();
		String datasetName = jsonObject.get("select_datasets").getAsString();
		DataSet dataSet = DataSetManager.getInstance().getDataSet(datasetName);
		TaskInfo info = TaskManager.getInstance().getTask(taskName);
		info.setDataSet(dataSet);
		TaskManager.getInstance().update(info);
		response.getWriter().write("update the dataset information");
	}

	private void buildDataSet(String taskName, String trainFile, String testFile) {
		// TODO (Shiwei Wu) : to be implemented.
		TaskInfo taskInfo = TaskController.getInstance().getTask(taskName);
		DataSet dataSet = new DataSet(taskName, taskInfo.getTaskType(), trainFile, testFile);
		taskInfo.setDataSet(dataSet);
		TaskManager.getInstance().update(taskInfo);
		DataSetManager.getInstance().addDataSet(dataSet);
	}
}
