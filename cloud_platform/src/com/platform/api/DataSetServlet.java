package com.platform.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.platform.controller.DataSetManager;
import com.platform.controller.TaskController;
import com.platform.controller.TaskManager;
import com.platform.models.DataSet;
import com.platform.models.GlobalConfig;
import com.platform.models.TaskInfo;
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
import java.util.List;
import java.util.logging.Logger;

/**
 * User: dawei, dsybswsw@gmail.com
 * Date: 7/29/13
 */
public class DataSetServlet extends BaseServlet {
	private final static Logger logger = Logger.getLogger(DataSetServlet.class.getName());

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	// get dataset information.
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			String taskType = request.getParameter("tasktype");
			String tasks = handleDataSetList(taskType);
			response.getWriter().write(tasks);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String handleDataSetList(String taskType) {
		try {
			List<DataSet> dataSetList = DataSetManager.getInstance().getDataSets(taskType);
			JsonArray taskJsonArray = new JsonArray();
			for (DataSet dataSet : dataSetList) {
				JsonObject taskInfoObject = new JsonObject();
				taskInfoObject.addProperty("dataset_name", dataSet.getDataSetName());
				taskInfoObject.addProperty("tasktype", dataSet.getTaskType());
				taskJsonArray.add(taskInfoObject);
			}
			return taskJsonArray.toString();
		} catch (Exception e) {
			logger.info(e.toString());
			return null;
		}
	}

	// upload new dataset.
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			handleMultiPartRequest(request, response);
		} catch (IOException e) {
			logger.info(e.toString());
			e.printStackTrace();
		}
	}

	private void handleMultiPartRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {
			response.getWriter().write("message wrong.");
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

		String dataSetName = null;
		String type = null;
		for (FileItem item : itemList) {
			if (item.getFieldName().equals("dataset_name")) {
				dataSetName = item.getString();
				logger.info("receive dataset name " + dataSetName);
			} else if (item.getFieldName().equals("tasktype")) {
				type = item.getString();
				logger.info("receive task type " + type);
			}
		}

		DataSet dataSet = DataSetManager.getInstance().getDataSet(dataSetName);
		if (dataSet != null) {
			response.getWriter().write("dataSet " + dataSet.getDataSetName() + " has already exist.");
			return ;
		}

		String trainFile = null;
		String testFile = null;
		for (FileItem item : itemList) {
			if (item.getFieldName().equals("train")) {
				trainFile = dataSetName + ".train";
				File file = new File(GlobalConfig.getInstance().getModelWorkDir() + "/" + trainFile);
				try {
					item.write(file);
					logger.info("write " + dataSetName + ".train" + " to remote disk!");
				} catch (Exception e) {
					logger.info(e.toString());
				}
			} else if (type.equals("classification") && item.getFieldName().equals("test")) {
				testFile = dataSetName + ".test";
				File file = new File(GlobalConfig.getInstance().getModelWorkDir() + "/" + testFile);
				try {
					item.write(file);
					logger.info("write " + dataSetName + ".test" + " to remote disk!");
				} catch (Exception e) {
					logger.info(e.toString());
					e.printStackTrace();
				}
			}
		}
		DataSetManager.getInstance().addDataSet(new DataSet(dataSetName, type, trainFile, testFile));
	}
}
