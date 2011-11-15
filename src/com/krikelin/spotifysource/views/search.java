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
import java.awt.Container;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import com.krikelin.spotifysource.ISPEntry;
import com.krikelin.spotifysource.SPActivity;
import com.krikelin.spotifysource.SPContentView;
import com.krikelin.spotifysource.SPListView;
import com.krikelin.spotifysource.SPTableModel;
import com.krikelin.spotifysource.SpotifySearch;
import com.krikelin.spotifysource.SpotifyWindow;
import com.krikelin.spotifysource.URI;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
public class search extends SPActivity {
	public search() {
	}
	private URI mReferrer;
	private ArrayList<ISPEntry> mContents = new ArrayList<ISPEntry>();
	
	public class Overview extends SPContentView
	{
		private Container mTableContainer;
		public SPListView listView;
		/**
		 * 
		 */
		private static final long serialVersionUID = 1580980909371828894L;
		public Overview()
		{
			super(search.this,search.this.getContext());
		// set layout to borderlayout
			
			mTableContainer = getContentView();
			mTableContainer.setLayout(new BorderLayout());
			listView = new SPListView(new SPTableModel(mContents),search.this.getContext());
			// Find the music
			for(ISPEntry entry : mContents)
			{
				entry.setHost(this);
			}
			mTableContainer.setBounds(new Rectangle(0,0,getWidth(),getHeight()));
			add(listView	);
			mTableContainer.add(listView.getTableHeader(),BorderLayout.PAGE_START);
			mTableContainer.add(listView,BorderLayout.CENTER);
			doLayout();
			mTableContainer.doLayout();	
		
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
		mReferrer = referrer;
		
		
		
	}
	private Overview mOverview;
	@Override
	public Object onLoad(URI args) {
		// TODO Auto-generated method stub
		try {
			SpotifySearch se = new SpotifySearch();
			mContents.addAll(se.getSongs("http://ws.spotify.com/search/1/track?q=%s",mReferrer.getParameters()[0],search.this,mOverview));
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mContents;
	}
	@Override
	public void render(URI args, Object... result) {
		// TODO Auto-generated method stub
		mOverview = new Overview();
		addPage("Overview", mOverview	);
	}
}
