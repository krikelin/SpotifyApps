/*
 * Copyright (C) 2011 Alexander Forselius
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.krikelin.spotifysource.spml;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import com.krikelin.spotifysource.SpotifyWindow;

public class SPMLTest {
	/**
	 * 
	 */
	
	@SuppressWarnings("deprecation")
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
