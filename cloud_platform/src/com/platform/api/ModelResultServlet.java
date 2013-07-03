package com.platform.api;

import com.platform.models.GlobalConfig;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.logging.Logger;

/**
 * User: dawei, dsybswsw@gmail.com
 * Date: 6/30/13
 */
public class ModelResultServlet extends BaseServlet {
	private final static Logger logger = Logger.getLogger(ModelResultServlet.class.getName());

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String taskName = request.getParameter("taskname");
		logger.info("taskname is " + taskName);
		String resultFile = GlobalConfig.getInstance().getModelWorkDir() + "/" + taskName + ".result";
		File file = new File(resultFile);
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
	}
}
