package com.platform.models;

/**
 * User: dawei, dsybswsw@gmail.com
 * Date: 6/23/13
 */
public class ModelTrainerThread extends Thread{
	private String jsonParams;

	public ModelTrainerThread(String jsonParams) {
		this.jsonParams = jsonParams;
	}

	public void run() {
		Trainer.getInstance().train(jsonParams);
	}
}
