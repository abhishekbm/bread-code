package com.pixo.serviceImpl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

/**
 * @author abhibm
 *
 *
 *Utility class to keep all file data for a rest call and clear it at the end of the call
 */


@Component("ListUtility")
public class FIleListUtility {

	public static List<JSONObject> listOfFiles = new CopyOnWriteArrayList<JSONObject>();

	public void add(JSONObject name) {
		listOfFiles.add(name);
	}

	public String getLog() {
		return listOfFiles.toString();
	}
	
	public void clear(){
		listOfFiles.clear();
	}
}
