package com.krikelin.spotifysource;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;

import org.jibble.simplewebserver.SimpleWebServer;

import com.sun.net.*;
public class Smisk {
	private SimpleWebServer spw;
	public Smisk(){
		try {
			setSpw(new SimpleWebServer(new File(SPContainer.EXTENSION_DIR+"\\web"), 11215));
		
			spw.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public SimpleWebServer getSpw() {
		return spw;
	}
	public void setSpw(SimpleWebServer spw) {
		this.spw = spw;
	}
}
