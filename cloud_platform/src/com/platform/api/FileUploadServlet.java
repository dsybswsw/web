package com.platform.api;

import com.platform.models.GlobalConfig;
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
 * ! NOT IN USE !
 *
 * User: dawei, dsybswsw@gmail.com
 * Date: 6/26/13
 */
public class FileUploadServlet extends BaseServlet {
	private final static Logger logger = Logger.getLogger(FileUploadServlet.class.getName());

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {
			response.getWriter().write("Not file upload request");
			return;
		}
		String taskName = "";
		logger.info("received taskname " + taskName);

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
		for (FileItem item : itemList) {
			if (item.getFieldName().equals("upload")) {
				File file = new File(GlobalConfig.getInstance().getModelWorkDir() + "/" + item.getName());
				try {
					item.write(file);
					logger.info("write " + item.getName() + " to remote disk!");
				} catch (Exception e) {
					logger.info(e.toString());
				}
			} else {
				logger.info(item.getFieldName() + ", " + item.getString());
				if (item.getFieldName().equals("taskname")) {
					taskName = item.getString();
					logger.info("received task name is " + taskName);
				}
			}
		}

		// Build a new dataset module and write it to database.
	}
}
