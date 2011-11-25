package com.krikelin.spotifysource.views.thirdpart;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Scanner;

import com.krikelin.spotifysource.Activity;
import com.krikelin.spotifysource.ISPEntry;
import com.krikelin.spotifysource.SimpleEntry;
import com.krikelin.spotifysource.URI;
import com.krikelin.spotifysource.WebView;
import com.krikelin.spotifysource.widgets.RadioStream;

public class spartify extends Activity {
	private String artist;
	private ArrayList<com.krikelin.spotifysource.SimpleEntry> entries = new ArrayList<com.krikelin.spotifysource.SimpleEntry>();
	@Override
	public Object onLoad(URI args) {
		// TODO Auto-generated method stub
		
		// Get radio stream
		return null;
		
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Spotfm";
	}

	@Override
	public void render(URI args, Object... result) {
		// TODO Auto-generated method stub
		String userName = args.getParameter();
		String url ="http://www.spartify.com/";
		if(userName != null){
			 url+=userName;
		}
		WebView ws =new WebView(url, this, getContext(), false);
	
		
		addPage("Spartify", ws);
		super.render(args, result);
		// http://echofiapp.com/go.php?
	}

}
