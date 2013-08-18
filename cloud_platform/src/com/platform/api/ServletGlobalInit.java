package com.platform.api;

import com.platform.controller.DataSetManager;
import com.platform.controller.MysqlDB;
import com.platform.controller.TaskController;
import com.platform.models.GlobalConfig;
import com.platform.models.TrainingConfig;
import com.platform.models.Trainer;

/**
 * User: dawei, dsybswsw@gmail.com
 * Date: 6/24/13
 */
public class ServletGlobalInit {
	public static void initialize(String rootDir) {
		GlobalConfig.initialize(rootDir);
		TrainingConfig.initialize(rootDir);
		MysqlDB.initialize(GlobalConfig.getInstance().getDbProfile());
		TaskController.initialize();
		DataSetManager.init();
		Trainer.init();
	}
}
