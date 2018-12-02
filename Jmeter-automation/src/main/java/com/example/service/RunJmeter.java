package com.example.service;

import javax.xml.ws.Response;

public interface RunJmeter {
	 Response runScript(final String ScriptName )  throws Exception;
}
