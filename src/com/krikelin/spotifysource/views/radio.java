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
package com.krikelin.spotifysource.views;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.krikelin.spotifysource.Activity;
import com.krikelin.spotifysource.SPContentView;
import com.krikelin.spotifysource.SPLabel;
import com.krikelin.spotifysource.SimpleEntry;
import com.krikelin.spotifysource.SpotifyWindow;
import com.krikelin.spotifysource.URI;
import com.krikelin.spotifysource.widgets.RadioStream;

public class radio extends Activity {
	public class Overview extends SPContentView
	{
		private SPLabel mHeader=  new SPLabel(radio.this.getContext(),"Radio");
		/**
		 * 
		 */
		private static final long serialVersionUID = 1580980909371828894L;
		public Overview()
		{
			super(radio.this,radio.this.getContext());
			JPanel panel = new JPanel();
			
			panel.setLayout(new GridLayout(2,1));
			add(panel);
			panel.add(Box.createVerticalGlue());
			RadioStream rs = new RadioStream(radio.this, radio.this.getContext());
			for(int i=0; i < 3; i++){
				rs.getForward().push(new SimpleEntry( null, radio.this, this,new URI("Test "+i,"spotify:track:2"),null,null,null));
				
			}
			panel.add(rs);
			rs.setCurrentEntry(rs.getForward().pop());
		}
		
		
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 8559581767908741443L;
	@Override
	public void onCreate(URI referrer,SpotifyWindow context)
	{	
		super.onCreate( referrer, context);
		addPage("Overview", new Overview());
	
	}
	@Override
	public Object onLoad(URI args) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void render(URI args, Object... result) {
		// TODO Auto-generated method stub
		
	}
}