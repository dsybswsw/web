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

	private String rootDir;
	private String scriptMapFile;
	private String dbProfile;

	public String getRootDir() {
		return rootDir;
	}

	public String getScriptMapFile() {
		return scriptMapFile;
	}

	public String getDbProfile() {
		return dbProfile;
	}

	private Map<String, String> keyMap;

	private GlobalConfig(String rootDir) {
		this.rootDir = rootDir;
		this.keyMap = ConfigParser.loadConfigFile(rootDir + "/WEB-INF/config/global.config");
		this.scriptMapFile = rootDir + "/WEB-INF/config/scripts_map.config";
		this.dbProfile = rootDir + "/WEB-INF/config/db.config";
	}

	public static void initialize(String rootDir) {
		globleConfig = new GlobalConfig(rootDir);
	}

	private static GlobalConfig globleConfig = null;

	public static GlobalConfig getInstance() {
		return globleConfig;
	}

	public String getModelWorkDir() {
		return getWebAppsDir() + keyMap.get(MODEL_WORK_DIR);
	}

	public String getWebAppsDir() {
		return rootDir + "../";
	}
}
