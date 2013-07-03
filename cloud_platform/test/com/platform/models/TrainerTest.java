package com.platform.models;

import com.google.gson.JsonObject;
import com.platform.api.ServletGlobalInit;

/**
 * User: dawei, dsybswsw@gmail.com
 * Date: 6/25/13
 */
public class TrainerTest {
	public static void main(String[] args) {
		ServletGlobalInit.initialize("/home/dawei/codes/web/cloud_platform/WebContent/");
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("parameters", "");
		jsonObject.addProperty("algorithm" ,"classification");
		Trainer.getInstance().train(jsonObject.toString());
	}
}
