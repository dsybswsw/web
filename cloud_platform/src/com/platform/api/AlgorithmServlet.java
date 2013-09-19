package com.platform.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.platform.controller.AlgorithmManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * User: dawei, dsybswsw@gmail.com
 * Date: 9/19/13
 */
public class AlgorithmServlet extends BaseServlet {
	private final static Logger logger = Logger.getLogger(AlgorithmServlet.class.getName());

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			String taskType = request.getParameter("tasktype");
			String algorithms = handleAlgorithmList(taskType);
			response.getWriter().write(algorithms);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String handleAlgorithmList(String taskType) {
		try {
			List<String> algorithms = AlgorithmManager.getInstance().getAlgorithms(taskType);
			JsonArray taskJsonArray = new JsonArray();
			for (String algorithm : algorithms) {
				JsonObject taskInfoObject = new JsonObject();
				taskInfoObject.addProperty("algorithm", algorithm);
				taskJsonArray.add(taskInfoObject);
			}
			return taskJsonArray.toString();
		} catch (Exception e) {
			logger.info(e.toString());
			return null;
		}
	}
}
