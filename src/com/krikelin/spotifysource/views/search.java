package com.krikelin.spotifysource.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import com.krikelin.spotifysource.Album;
import com.krikelin.spotifysource.ISPEntry;
import com.krikelin.spotifysource.MenuListModel;
import com.krikelin.spotifysource.SPActivity;
import com.krikelin.spotifysource.SPContentView;
import com.krikelin.spotifysource.SPLabel;
import com.krikelin.spotifysource.SPListView;
import com.krikelin.spotifysource.SPTableModel;
import com.krikelin.spotifysource.SpotifySearch;
import com.krikelin.spotifysource.SpotifyWindow;
import com.krikelin.spotifysource.URI;
import com.krikelin.spotifysource.views.home.Overview;

import javax.swing.*;
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
