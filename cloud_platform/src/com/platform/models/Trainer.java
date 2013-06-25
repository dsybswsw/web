package com.platform.models;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * User: dawei, dsybswsw@gmail.com
 * Date: 6/23/13
 */
public class Trainer {
	private Map<String, Integer> statusMap;
	private Map<String, String> cmdMap;

	private final static Logger logger = Logger.getLogger(Trainer.class.getName());

	private int trainingStatus;

	private Trainer() {
		statusMap = new HashMap<String, Integer>();
		cmdMap = new HashMap<String, String>();
		trainingStatus = TrainingConstants.TRAINED;
		initStatusMap(statusMap);
	}

	private void initStatusMap(Map<String, Integer> statusMap) {
		for (String taskName : cmdMap.keySet()) {
			statusMap.put(taskName, TrainingConstants.TRAINED);
		}
	}

	public static void init() {
		modelTrainer = new Trainer();
	}

	private static Trainer modelTrainer;

	public static Trainer getInstance() {
		return modelTrainer;
	}

	public synchronized void train(String jsonParams) {
		Parameters params = ParameterParser.parseJsonParams(jsonParams);
		String taskName = params.getTaskName();
		String parameter = params.getParameters();
		trainingStatus = TrainingConstants.TRAINING;
		try {
			// runScipts(taskName, parameter);
			Thread.sleep(10000);
		} catch (Exception e) {
			logger.info(e.toString());
			statusMap.put(taskName, TrainingConstants.TRAINED);
		}
		trainingStatus = TrainingConstants.TRAINED;
	}

	private void runScipts(String taskName, String parameters) throws IOException {
		String cmd = cmdMap.get(taskName) + " " + parameters;
		Process child = Runtime.getRuntime().exec(cmd);
		InputStream stream = child.getInputStream();

		int c;
		while ( (c = stream.read()) != -1) {
			System.out.write(c);
		}
	}

	public int getStatus() {
		return trainingStatus;
	}
}
