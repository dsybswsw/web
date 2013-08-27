package com.platform.api;

import com.platform.controller.DataSetManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: dawei, dsybswsw@gmail.com
 * Date: 8/26/13
 */
public class DataSetDeleterServlet extends BaseServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			String datasetName = request.getParameter("dataset_name");
			DataSetManager.getInstance().deleteDataSet(datasetName);
			response.getWriter().write("delete dataset " +  datasetName + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
