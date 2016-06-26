package com.push.utils;

import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonObject;

public class JsonUtils {

	public static JsonObject mapToJson(Map<String, Object> map) {
		JsonObject json = new JsonObject();
		for (Entry<String, Object> entry : map.entrySet()) {
			json.addProperty(entry.getKey(), String.valueOf(entry.getValue()));
			//System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
		}
		return json;
	}
	
	
}
