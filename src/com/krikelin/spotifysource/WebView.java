package com.krikelin.spotifysource;

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
import com.krikelin.spotifysource.widgets.RadioStream;

public class WebView extends SPContentView{

	private SPWebBrowser browser;
	private RadioStream radioStream;
	private Activity activity;
	public WebView(String url, Activity act, SpotifyWindow mContext) {
		super( act, mContext);
		this.activity=act;
		// TODO Auto-generated constructor stub
		JPanel bc = new JPanel();
		add(bc);
		bc.setLayout(new GridLayout(1,1));
		
		browser = new SPWebBrowser("",getContext(),this,220);
		radioStream = new RadioStream(act,getContext()); 
		browser.setActionEventListener(new ActionEventHandler() {
			 
			@Override
			public void onSpotifyAction(String arg0) {
				// TODO Auto-generated method stub
				try {
					radioStream.getPlaylist().add(new com.krikelin.spotifysource.SimpleEntry(activity, WebView.this,arg0));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void onAction(String arg0, Object... arg1) {
				// TODO Auto-generated method stub
				
			}
		});
		browser.navigate(url);

		bc.add(browser);
//		bc.add(radioStream);
		radioStream.setPreferredSize(new Dimension(640,32));
	}
	
}