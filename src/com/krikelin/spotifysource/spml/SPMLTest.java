package com.krikelin.spotifysource.spml;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import com.krikelin.spotifysource.SpotifyWindow;

public class SPMLTest {
	/**
	 * 
	 */
	
	public static void main(String[] args){
		JFrame frame = new JFrame();
		SpotifyWindow sw = new SpotifyWindow();
		SPWebView webview = new SPWebView(sw);
		
		frame.add(webview,BorderLayout.CENTER);
		hbox f = new hbox(sw,webview);
		text a = new text(sw,webview);
		text b = new text(sw,webview);
		text c = new text(sw,webview);
		
		a.setText("test");
		a.setWidth(120);
		a.setPadding(3);
		b.setText("test");
		b.setFlex(2);
		c.setText("test");
		c.setWidth(120);
		f.getChildren().add(a);
		f.getChildren().add(b);
		f.getChildren().add(b);
		webview.getElements().add(f);
		frame.setSize(new Dimension(640,480));
		frame.show();
	}
}
