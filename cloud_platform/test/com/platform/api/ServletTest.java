package com.platform.api;

import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * User: dawei, dsybswsw@gmail.com
 * Date: 6/25/13
 */
public class ServletTest {
	private final static String url = "http://localhost:8080/platform/training";

	public static void main(String[] args) throws Exception {
		URIBuilder uriBuilder = new URIBuilder(url);
		HttpPost httpPost = new HttpPost(uriBuilder.build());
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("parameters", "parameters");
		jsonObject.addProperty("algorithm" ,"algorithms");
		httpPost.setEntity(new StringEntity(jsonObject.toString(), "UTF8"));
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpResponse response = httpClient.execute(httpPost);
		System.out.println(EntityUtils.toString(response.getEntity(), "UTF8"));
	}
}
