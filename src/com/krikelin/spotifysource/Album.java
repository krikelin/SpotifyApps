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
package com.krikelin.spotifysource;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JComponent;

/**
 * Album view
 * @author Alexander
 *
 */
public class Album extends BufferedContainer implements SPElementGroup, SPWidget, SPPart,SPElement {
	private SpotifyWindow mContext;
	/**
	 * Gets listeners from last.FM
	 * @author Alexander
	 *
	 */
	
	/**
	 * Denotates an group of songs
	 * @author Alexander
	 *
	 */
	public class TrackSection
	{
		public ArrayList<ISPEntry> tracks = new ArrayList<ISPEntry>();
		public String Key = "";
		public String Name = "";
		public TrackSection(String key,String name)
		{
			this.Key=key;
			this.Name = name;
		}
	}
	public ArrayList<TrackSection> trackSections = new ArrayList<TrackSection>();
	private SPContentView mContentView;
	private ArrayList<ISPEntry> mAlbumTracks = new ArrayList<ISPEntry>();
	private Activity mActivity;
	public Activity getActivity()
	{
		return mActivity;
	}
	private int mTrackHeight = 18;
	private SPLabel mAlbumName;
	private ArrayList<SPElement> mElements = new ArrayList<SPElement>();
	private ImageBox mCover;
	int top = 10;
	private URI mUri;
	private int mTrackLeft = 128;
	
	/**
	 * 
	 */
	public Album(int iWidth,int iHeight, URI mUri,String name,Activity mActivity,ArrayList<ISPEntry> mAlbumTracks, SpotifyWindow mContext,SPContentView mContentView)
	{
		
		super(mContext);
		setLayout(null);
		this.mUri=mUri;
		this.mAlbumTracks = mAlbumTracks;
		this.mActivity=mActivity;
		this.mContext=mContext;
		this.mContentView=mContentView;
		try
		{
			mCover = new ImageBox(mContext.getSkin().getReleaseImage(),mContext);
			mCover.downloadImage(new URL("http://segorify.com/cover.png"));
		}
		catch(Exception e)
		{
			
			
		}
	
		
		
		
		mAlbumName = new SPLabel(mContext,name);
		mAlbumName.setBounds(new Rectangle(mTrackLeft,top,600,28));
		mAlbumName.setCursor(new Cursor(Cursor.HAND_CURSOR));
		mAlbumName.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				getContext().navigate(Album.this.mUri);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
				
		});
		mAlbumName.setText(name);
		mAlbumName.setFont(new Font("Tahoma",Font.BOLD,15));
		add(mAlbumName);
		
		this.mAlbumTracks=(mAlbumTracks);
		
		mCover.setBounds(15,top,iWidth,iWidth);
		top += mAlbumName.getHeight();
		add(mCover);
		ArrayList<TrackSection> trackSections = new ArrayList<TrackSection>();
		trackSections.add(new TrackSection("radio","Radio"));

		trackSections.add(new TrackSection("live","Live remixes"));
		trackSections.add(new TrackSection("bonus","Bonus tracks"));
		trackSections.add(new TrackSection("mix","Remixes "));
		trackSections.add(new TrackSection("vs","Collaborative Remixes"));
		
		
		int totalMinutes = 60;
		int minutes = 0;
		int discNo = 1;
		int i = 0;// Create tracklist
		
		// Remove special songs
		
		for(int x=0; x < mAlbumTracks.size(); x++)
		{
			try
			{
				
				
				ISPEntry entry = mAlbumTracks.get(x);
				for(TrackSection section : trackSections)
				{
					if(entry.getUri().getTitle().toLowerCase().contains(section.Key))
					{
						section.tracks.add(entry);
						mAlbumTracks.remove(entry);
						
					}
				}
			}
			catch(Exception e)
			{
				
			}
		}
		
		// Calculate the total minutes
		for(ISPEntry track : mAlbumTracks)
		{
			minutes+=track.getDuration().getMinutes();
		}
		
		// List radio versions first
		if(trackSections.get(0).tracks.size() > 0)
		{
			top = addTracks(trackSections.get(0).Name, trackSections.get(0).tracks, top);
			trackSections.remove(0);
			top+=30;
		}
		
		// Make the tracklist
		for(ISPEntry track : mAlbumTracks)
		{
			/**
			 * Add disc separator, only if total minutes exceed 60
			 */
			if(minutes >= 60)
			if(totalMinutes >= 60)
			{
				top+=5;
				totalMinutes =0;
				SPLabel disc = new SPLabel(Album.this.getContext(),"Disc");
				disc.setText("#"+discNo);
				disc.setBounds(160,top,45,16);
				discNo++;
				disc.setBackground(getBackground().darker());
				disc.setColor(new Color(255,255,211));
				add(disc);
				top+=disc.getHeight()+5;
			}
			SPEntry entry = new SPEntry(mActivity,mContentView,mContext,track);
			entry.setPreferredSize(new Dimension(mTrackLeft,mTrackHeight));
			entry.setBounds(mTrackLeft,top,3260,mTrackHeight);
			
			mElements.add(entry);
			
			add(entry);
			entry.setVisible(true);
			entry.validate();
			entry.setAlternate(i % 2 == 1);
			totalMinutes+=entry.getDuration().getMinutes();	
			repaint();
			validate();
			i++;
			top+=mTrackHeight;
		}
		for(TrackSection section : trackSections)
		{
			top = addTracks(section.Name, section.tracks, top);
		}
		mHeight = i >= 7 ? mAlbumTracks.size()*15 + 100 : 160;
		setPreferredSize(new Dimension(6120,mHeight));
	
	}
	
	
	
	/**
	 * Add specified track collection
	 * @param section
	 * @param mAlbumTracks
	 * @param top
	 * @return
	 */
	public int addTracks(String section,ArrayList<ISPEntry> tracks,int top)
	{
		if(tracks.size() > 0)
			
		{
			top+=2;
			SPLabel bt = new SPLabel(Album.this.getContext(),section);
			bt.setText(section);
			bt.setShadow(false);
			bt.setBounds(mTrackLeft,top,3260,16);
			bt.setBackground(getBackground().darker());
			bt.setFont(new Font("Tahoma",Font.PLAIN,11));
			add(bt);
			top+=bt.getHeight()+2;
		}
		int i = 0;
		for(ISPEntry track : tracks)
		{
				
			SPEntry entry = new SPEntry(mActivity,mContentView,mContext,track);
			entry.setPreferredSize(new Dimension(mTrackLeft,mTrackHeight));
			entry.setBounds(mTrackLeft ,top,3260,mTrackHeight);
		
			mElements.add(entry);
			
			add(entry);
			entry.setVisible(true);
			entry.validate();
			entry.setAlternate(i % 2 == 1);
		
			repaint();
			validate();
			i++;
			top+=mTrackHeight;
		}
		return top;
	}
	public void autoSize()
	{
		setBounds(getLeft(),getTop(),getWidth(),mHeight);
		
	}
	private int mHeight = 0 ;
	private int id;
	@SuppressWarnings("unused")
	@Override
	public void draw(Graphics g)
	{
		super.draw(g); 
	//	g.drawImage(mCoverImage,10,30, iWidth,iWidth, null);
		if(false)
			if(mAlbumTracks != null)
				if(mAlbumTracks.size() > 0)
				{
					g.drawImage(getContext().getSkin().getImageById(DefaultSkin.IMG_SHADOW),mTrackLeft,top,3620,18,null);
				}
	}
	private static final long serialVersionUID = -8135177082254300730L;

	@Override
	public SpotifyWindow getContext() {
		// TODO Auto-generated method stub
		return mContext;
	}

	
	public void draw(Graphics g, int x, int y, int width, int height,
			boolean alt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getLeft() {
		// TODO Auto-generated method stub
		return super.getLocation().x;
	}

	@Override
	public Point getMousePosition(Point srcLoc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SPOnClickListener getOnClickListener() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTop() {
		// TODO Auto-generated method stub
		return super.getLocation().y;
	}

	@Override
	public boolean isLink() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setHeight(int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLeft(int left) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setOnClickListener(SPOnClickListener mListener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTop(int top) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setWidth(int width) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SPContentView getHost() {
		// TODO Auto-generated method stub
		return mContentView;
	}

	@Override
	public ArrayList<SPElement> getElements() {
		// TODO Auto-generated method stub
		return mElements;
	}
	
	public ArrayList<ISPEntry> getAlbumTracks() {
		return mAlbumTracks;
	}
	@Override
	public void setLabel(String label) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return this.id;
	}



	@Override
	public void setID(int id) {
		// TODO Auto-generated method stub
		this.id = id;
	}



	@Override
	public JComponent findViewById(int id) {
		// TODO Auto-generated method stub
		for(Component component : this.getComponents()){
			if(component instanceof SPWidget){
				if(((SPWidget)component).getID() == id){
					return (JComponent)component;
				}
				Component subComponent = ((SPWidget) component).findViewById(id);
				if(subComponent!= null){
					return (JComponent)subComponent;
				}
			}
			
		}
		return null;
	}

}
