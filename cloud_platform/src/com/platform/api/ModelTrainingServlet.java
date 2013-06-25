package com.platform.api;

import com.platform.models.ModelTrainerThread;
import com.platform.models.Trainer;
import com.platform.models.TrainingConstants;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * User: dawei, dsybswsw@gmail.com
 * Date: 6/23/13
 */
public class ModelTrainingServlet extends HttpServlet {
	private final static Logger logger = Logger.getLogger(ModelTrainingServlet.class.getName());

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletGlobalInit.initialize();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			handleRequest(request, response);
		} catch (Exception e) {
			logger.info("Failed to do training");
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("receive get request");
		if (Trainer.getInstance().getStatus() == TrainingConstants.TRAINING) {
			logger.info("received get request and model is training.");
			response.getWriter().print("training");
		} else {
			logger.info("received get request and model has been trained.");
			response.getWriter().print("trained");
		}
	}

	private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(ServletConstants.CHN_ENCODING);
		String jsonString = request.getReader().readLine();
		logger.info("received training request is " + jsonString);
		if (Trainer.getInstance().getStatus() != TrainingConstants.TRAINED) {
			response.getWriter().write("there is model training");
			return ;
		}
		new ModelTrainerThread(jsonString).start();
		response.getWriter().write("new thread has been build to train the model");
	}
}
