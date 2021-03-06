package com.platform.models;

import com.platform.controller.TaskController;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

	public synchronized void doTraining(String jsonParams) {
		Parameters params = ParameterParser.parseJsonParams(jsonParams);
		if (params == null) {
			logger.info("Failed to parse parameter json format.");
			return;
		}
		String taskName = params.getTaskName();
		// String parameter = params.getParameters();
		trainingStatus = TrainingConstants.TRAINING;
		TaskInfo taskInfo = TaskController.getInstance().getTask(taskName);
		if (taskInfo == null) {
			logger.info("No this task !");
			return;
		}
		String taskType = taskInfo.getTaskType();
		DataSet dataSet = taskInfo.getDataSet();
		String parameters = taskInfo.getMisc();

		if (dataSet == null) {
			logger.info("no dataset.");
			return;
		}
		String script = TrainingConfig.getInstance().getScript(taskType);
		logger.info("scrpit is " + script);
		String trainingFile = GlobalConfig.getInstance().getModelWorkDir() + "/" + dataSet.getTrainingFileName();
		String modelFile = GlobalConfig.getInstance().getModelWorkDir() + "/" + taskName + ".model";
		String testFile = GlobalConfig.getInstance().getModelWorkDir() + "/" + dataSet.getTestFileName();
		String resultFile = GlobalConfig.getInstance().getModelWorkDir() + "/" + taskName + ".result";
		List<String> cmds = new ArrayList<String>();
		cmds.add(script);
		String[] paramArray = parameters.trim().split("\\s+");
		if (!parameters.trim().isEmpty()) {
			for (String param : paramArray) {
				cmds.add(param);
			}
		}
		if (taskType.equals("classification")) {
			cmds.add(trainingFile);
			cmds.add(modelFile);
			cmds.add(testFile);
			cmds.add(resultFile);
		} else {
			cmds.add(trainingFile);
			cmds.add(resultFile);
		}
		try {
			runScipts(cmds);
			// runScipts(script, parameter);
			// Thread.sleep(10000);
		} catch (Exception e) {
			logger.info(e.toString());
			statusMap.put(taskName, TrainingConstants.TRAINED);
		}
		trainingStatus = TrainingConstants.TRAINED;
	}

	public void runScriptsTest(String cmdLine) {
		String[] tokens = cmdLine.split(" ");
		List<String> cmds = new ArrayList<String>();
		for (String token : tokens) {
			cmds.add(token);
		}
		try {
			runScipts(cmds);
		} catch (IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

	private void runScipts(List<String> cmds) throws IOException {
		logger.info(cmds.toString());
		ProcessBuilder processBuilder = new ProcessBuilder(cmds);
		processBuilder.directory(new File(TrainingConfig.getInstance().gerScriptDir()));
		Process child = processBuilder.start();
		InputStream stream = child.getInputStream();
		int c;
		while ((c = stream.read()) != -1) {
			System.out.write(c);
		}
	}

	public int getStatus() {
		return trainingStatus;
	}
}