package com.platform.api;

import com.platform.controller.TaskManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: dawei, dsybswsw@gmail.com
 * Date: 8/26/13
 */
public class TaskDeletetServlet extends BaseServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			String taskname = request.getParameter("taskname");
			TaskManager.getInstance().deleteTask(taskname);
			response.getWriter().write("delete task " +  taskname + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

