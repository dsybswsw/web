package com.platform.api;

import com.platform.controller.DataSetManager;
import com.platform.models.DataSet;
import com.platform.models.GlobalConfig;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

/**
 * User: dawei, dsybswsw@gmail.com
 * Date: 8/26/13
 */
public class DataSetDownloaderServlet extends BaseServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			String datasetName = request.getParameter("dataset_name");
			DataSet dataSet = DataSetManager.getInstance().getDataSet(datasetName);
			String dataSetPath = GlobalConfig.getInstance().getModelWorkDir() + dataSet.getTrainingFileName();
			File file = new File(dataSetPath);
			if (file.exists()) {
				String filename = URLEncoder.encode(file.getName(), "utf-8");
				response.reset();
				response.setContentType("application/x-msdownload");
				response.addHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
				int fileLength = (int) file.length();
				response.setContentLength(fileLength);
				if (fileLength != 0) {
					InputStream inStream = new FileInputStream(file);
					byte[] buf = new byte[4096];
					ServletOutputStream servletOS = response.getOutputStream();
					int readLength;
					while (((readLength = inStream.read(buf)) != -1)) {
						servletOS.write(buf, 0, readLength);
					}
					inStream.close();
					servletOS.flush();
					servletOS.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
