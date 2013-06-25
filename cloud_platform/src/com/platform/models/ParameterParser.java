package com.platform.models;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.logging.Logger;

/**
 * User: dawei, dsybswsw@gmail.com
 * Date: 6/23/13
 */
public class ParameterParser {
	private final static Logger logger = Logger.getLogger(ParameterParser.class.getName());

	public static Parameters parseJsonParams(String jsonString) {
		logger.info("parameters to be parsed " + jsonString);
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(jsonString);
		if (!jsonElement.isJsonObject())
			return null;
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		String taskName = jsonObject.get(TrainingConstants.ALGORITHMS).getAsString();
		String parameters = jsonObject.get(TrainingConstants.PARAMETERS).getAsString();
		logger.info(taskName + ", " + parameters);
		return new Parameters(taskName, parameters);
	}
}
