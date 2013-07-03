package com.platform.models;

import com.platform.common.util.ConfigParser;

import java.util.Map;

/**
 * User: dawei, dsybswsw@gmail.com
 * Date: 6/26/13
 */
public class GlobalConfig {
	private final static String MODEL_WORK_DIR = "model_dir";

	private final static String WEB_APPS_DIR = "webapps_dir";

	private Map<String, String> keyMap;

	private GlobalConfig(String configMapFile) {
		this.keyMap = ConfigParser.loadConfigFile(configMapFile);
	}

	public static void initialize(String configMapFile) {
		globleConfig = new GlobalConfig(configMapFile);
	}

	private static GlobalConfig globleConfig = null;

	public static GlobalConfig getInstance() {
		return globleConfig;
	}

	public String getModelWorkDir() {
		return getWebAppsDir() + keyMap.get(MODEL_WORK_DIR);
	}

	public String getWebAppsDir() {
		return keyMap.get(WEB_APPS_DIR);
	}
}
