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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import org.lobobrowser.gui.FramePanel;

import com.krikelin.spotifysource.BufferedContainer;
import com.krikelin.spotifysource.Activity;
import com.krikelin.spotifysource.SPContentView;
import com.krikelin.spotifysource.SPLabel;
import com.krikelin.spotifysource.SpotifyWindow;
import com.krikelin.spotifysource.URI;

public class app extends Activity {
	String app_description;
	/**
	 * 
	 */
	private static final long serialVersionUID = -4138666058898342503L;
	protected class Overview extends SPContentView {

		/**
		 * 
		 */
		private static final long serialVersionUID = -8899296963213710228L;

		public Overview(Activity activity, SpotifyWindow mContext) {
			super(activity,mContext);
			// TODO Auto-generated constructor stub
	
		/*	BufferedContainer panel = new BufferedContainer(mContext);
			panel.setBackground(mContext.getSkin().getBackgroundColor());
			SPLabel text = new SPLabel(mContext, app_description);
			panel.setLayout(new BorderLayout());
			panel.setPreferredSize(new Dimension(640,120));
			((BorderLayout)panel.getLayout()).setHgap(10);
			((BorderLayout)panel.getLayout()).setVgap(10);
			add(panel,BorderLayout.NORTH);
			add(text,BorderLayout.CENTER);
			text.setText(description);*/
			FramePanel fp = new FramePanel();
			add(fp);
			try
			{
				fp.navigate("http://localhost/spotifysource/app.html");
			}catch(Exception e){
				
			}
		}
		
	}
	public String description = "No description found";
	@Override
	public Object onLoad(URI args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void render(URI args, Object... result) {
		// TODO Auto-generated method stub
		addPage("Overview", new Overview(this,getContext()));
	}

}
