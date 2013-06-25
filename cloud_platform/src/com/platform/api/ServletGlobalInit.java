package com.platform.api;

import com.platform.models.Trainer;

/**
 * User: dawei, dsybswsw@gmail.com
 * Date: 6/24/13
 */
public class ServletGlobalInit {
	public static void initialize() {
		Trainer.init();
	}
}
