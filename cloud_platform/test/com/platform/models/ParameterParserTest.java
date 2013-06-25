package com.platform.models;

import com.google.gson.JsonObject;

/**
 * User: dawei, dsybswsw@gmail.com
 * Date: 6/25/13
 */
public class ParameterParserTest {
	public static void main(String[] args) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("parameters", "parameters");
		jsonObject.addProperty("algorithms" ,"algorithms");
		Parameters parameters = ParameterParser.parseJsonParams(jsonObject.toString());
		System.out.println(parameters.getTaskName());
		System.out.println(parameters.getParameters());
	}
}
