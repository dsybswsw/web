package com.platform.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.platform.controller.TaskController;
import com.platform.models.GlobalConfig;
import com.platform.models.TaskInfo;
import com.platform.models.TrainingConstants;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * User: dawei, dsybswsw@gmail.com
 * Date: 6/27/13
 */
public class TaskServlet extends BaseServlet{
	private final static Logger logger = Logger.getLogger(ModelTrainingServlet.class.getName());

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
		logger.info("build new task " + taskName + ", " + taskType);
		boolean isBuilt = TaskController.getInstance().buildNewTask(new TaskInfo(taskName, taskType));
		if (isBuilt) {
			response.getWriter().write("succeed");
		} else {
			response.getWriter().write("failed");
		}
	}

	// post information to run the runnable scripts.
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {
			response.getWriter().write("Not file upload request");
			return;
		}

		// Create a factory for disk-based file items.
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Configure a repository (to ensuere a secure temp location is used)
		File repository = (File) this.getServletConfig().getServletContext()
						.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> itemList = null;
		try {
			itemList = upload.parseRequest(request);
		} catch (FileUploadException e) {
			logger.info(e.toString());
			return;
		}

		String taskName = null;
		for (FileItem item : itemList) {
			if (item.getFieldName().equals("taskname")) {
				taskName = item.getString();
			}
		}

		TaskInfo taskInfo = TaskController.getInstance().getTask(taskName);
		if (taskInfo == null) {
			response.getWriter().write("failed to post the task information.");
		}

		String type = taskInfo.getTaskType();
		for (FileItem item : itemList) {
			if (item.getFieldName().equals("train")) {
				File file = new File(GlobalConfig.getInstance().getModelWorkDir() + "/" + taskName + ".train");
				try {
					item.write(file);
					logger.info("write " + taskName + ".train" + " to remote disk!");
				} catch (Exception e) {
					logger.info(e.toString());
				}
			} else if (type.equals("classification") && item.getFieldName().equals("test")) {
				File file = new File(GlobalConfig.getInstance().getModelWorkDir() + "/" + taskName + ".test");
				try {
					item.write(file);
					logger.info("write " + taskName + ".test" + " to remote disk!");
				} catch (Exception e) {
					logger.info(e.toString());
				}
			}
		}
	}
}
