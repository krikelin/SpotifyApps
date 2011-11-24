package com.krikelin.spotifysource.views.thirdpart;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;

import javax.swing.JPanel;

import com.krikelin.spotifysource.Activity;
import com.krikelin.spotifysource.BufferedContainer;
import com.krikelin.spotifysource.SPContentView;
import com.krikelin.spotifysource.SPWebBrowser;
import com.krikelin.spotifysource.SPWebBrowser.ActionEventHandler;
import com.krikelin.spotifysource.SpotifyWindow;
import com.krikelin.spotifysource.URI;
import com.krikelin.spotifysource.WebActivity;
import com.krikelin.spotifysource.WebView;
import com.krikelin.spotifysource.widgets.RadioStream;

public class echofi extends Activity {
	SPWebBrowser browser;
	RadioStream radioStream;
	
	@Override
	public Object onLoad(URI arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Echofi";
	}

	@Override
	public void render(URI args, Object... result) {
		// TODO Auto-generated method stub
		super.render(args, result);
		addPage("Echofi", new WebView("http://echofiapp.com/", this, getContext()));
		
	}

}
