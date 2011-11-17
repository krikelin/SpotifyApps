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
import javax.swing.*;

import org.lobobrowser.gui.FramePanel;
import org.lobobrowser.html.BrowserFrame;
import org.lobobrowser.html.gui.HtmlPanel;

import java.net.MalformedURLException;



import com.krikelin.spotifysource.*;
public class home extends Activity {
	public class Overview extends SPContentView
	{
		@SuppressWarnings("unused")
		private SPLabel mHeader=  new SPLabel(home.this.getContext(),"What's new");
		/**
		 * 
		 */
		private static final long serialVersionUID = 1580980909371828894L;
		public Overview()
		{
			super(home.this,home.this.getContext());
			
			// TODO Add another page provider
			// This page is only internal as so long,
			// so it wont work outside the dev
			
			SPWebBrowser fp = new SPWebBrowser("http://localhost/spotifysource/home.html", mContext, this,-1);
			
		
			setTexturedBackground(getContext().getSkin().getDashedBackground());
			// Add an default image box
		/*	InputStream stream = getClass().getResourceAsStream("start.png");
	
			try {
				BufferedImage src =  ImageIO.read(stream);
				ImageBox iv = new ImageBox(src, home.this.getContext());
				add(iv);
				iv.setPreferredSize(new Dimension(640,480));
				iv.setLocation(12,12);
			}catch(Exception e){
				e.printStackTrace();
			}*/
		/*
 			add(mHeader);
			mHeader.setBounds(2,2,33,33);
			mHeader.setForeground(new Color(0xaaffaa));
			
	
			int mTrackHeight = 18;
			int i=0;
			/*for(URI track : mAlbumTracks)
			{
				SPEntry entry = new SPEntry(this,getContext(),track,new URI("","spotify:a:a"),new URI("","spotify:ac:c"),new URI("","spotify:ac:c"));
				entry.setBounds(67,10+i*mTrackHeight,128,mTrackHeight);
				 
				
				add(entry);
				i++;
			}*/
	
			doLayout();
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
		addPage("Welcome", new Overview());
	
		doLayout();
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
