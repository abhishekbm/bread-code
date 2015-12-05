package com.pixo.serviceImpl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

/**
 * @author abhibm
 *
 */
@Component("logger")
public class FIleListUtility {

	public static List<JSONObject> abhi = new CopyOnWriteArrayList<JSONObject>();

	public void add(JSONObject name) {
		abhi.add(name);
	}

	public String getLog() {
		return abhi.toString();
	}
}
