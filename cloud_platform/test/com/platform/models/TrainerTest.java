package com.platform.models;

import com.platform.api.ServletGlobalInit;

/**
 * User: dawei, dsybswsw@gmail.com
 * Date: 6/25/13
 */
public class TrainerTest {
	public static void main(String[] args) {
		ServletGlobalInit.initialize("./WebContent/");
		Trainer trainer = Trainer.getInstance();
		trainer.runScriptsTest("java");
	}
}
