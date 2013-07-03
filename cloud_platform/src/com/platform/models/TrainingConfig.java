package com.platform.models;

import com.platform.common.util.ConfigParser;

import java.util.Map;

/**
 * User: dawei, dsybswsw@gmail.com
 * Date: 6/25/13
 */
public class TrainingConfig {
	public String rootDir;

	private final static String SCRIPT_DIR = "WEB-INF/scripts/";
	private final static String DATA_DIR = "WEB-INF/data/";

	private final static String SCRIPT_MAP_FILENAME = "scripts_map.config";

	private Map<String, String> scriptMap;

	public TrainingConfig(String rootDir) {
		this.rootDir = rootDir;
		scriptMap = ConfigParser.loadConfigFile(rootDir + SCRIPT_DIR + SCRIPT_MAP_FILENAME);
	}

	private static TrainingConfig configurator;

	public static void initialize(String rootDir) {
		configurator = new TrainingConfig(rootDir);
	}

	public static TrainingConfig getInstance() {
		return configurator;
	}

	public String getRootDir() {
		return rootDir;
	}

	public String gerScriptDir() {
		return rootDir + SCRIPT_DIR;
	}

	public String getDataDir() {
		return rootDir + DATA_DIR;
	}

	public String getScript(String taskName) {
		return scriptMap.get(taskName);
	}
}
