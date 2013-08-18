package com.platform.api;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * User: dawei, dsybswsw@gmail.com
 * Date: 6/26/13
 */
public class BaseServlet extends HttpServlet {
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String rootDir = config.getServletContext().getRealPath("/") + "/";
		ServletGlobalInit.initialize(rootDir);
	}
}
