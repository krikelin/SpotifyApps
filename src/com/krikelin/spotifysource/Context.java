package com.krikelin.spotifysource;

import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
/***
 * An resource context
 * @author Alexander
 *
 */
public interface Context {
	/**
	 * Hashmap of resource domains
	 */
	public HashMap<String, String> getResDomains();
	/**
	 * Returns an path to the local resources provided by the context
	 * @param type
	 * @return
	 */
	public Enumeration<URL> getLocalResources(String type);
//	public void navigate(URI uri);
	
}
