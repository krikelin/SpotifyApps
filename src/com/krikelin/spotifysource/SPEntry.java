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
import java.awt.Graphics;
import java.awt.Image;

import java.awt.Point;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

/***
 * A viewEntry is an playlist entry assigned on an view.
 * @author Alexander
 *
 */
public class SPEntry extends BufferedCanvas implements SPElement, ISPEntry, SPWidget, SPPart {
	public ArrayList<ISPEntry> getAssets()
	{
		return null;
	}
	public TransferHandler dragHandler = new TransferHandler(){
	
		/**
		 * 
		 */
		private static final long serialVersionUID = -6734623567755050432L;

		/* (non-Javadoc)
		 * @see javax.swing.TransferHandler#exportAsDrag(javax.swing.JComponent, java.awt.event.InputEvent, int)
		 */
		@Override
		public void exportAsDrag(JComponent arg0, InputEvent arg1, int arg2) {
			// TODO Auto-generated method stub
			super.exportAsDrag(arg0, arg1, arg2);
		}

		/* (non-Javadoc)
		 * @see javax.swing.TransferHandler#createTransferable(javax.swing.JComponent)
		 */
		@Override
		protected Transferable createTransferable(JComponent c) {
			// TODO Auto-generated method stub
			return new StringSelection(mUri.toLinkString());
		}
		
	};
	/**
	 * 
	 */
	private static final long serialVersionUID = -7577469568854194029L;
	private SpotifyWindow mContext;
	private SPContentView mContentView;
	private Activity mActivity;
	private Float mPopularity;
	private int mTrackNumber;
	/**
	 * If the entry is for music
	 */
	private boolean mMusicEntry = true;
	/**
	 * @return the mMusicEntry
	 */
	public boolean isMusicEntry() {
		return mMusicEntry;
	}
	/**
	 * @param mMusicEntry the mMusicEntry to set
	 */
	public void setMusicEntry(boolean mMusicEntry) {
		this.mMusicEntry = mMusicEntry;
	}
	/**
	 * Creates an new ViewEntry
	 * @param mUri
	 * @param mAuthorUri
	 * @param mCollectionUri
	 */
	private int mLeft,mTop;
	private ISPEntry mEntry;
	@Override
	public void setBackground(Color color)
	{
		super.setBackground(color);
		for(int i=0; i < getComponents().length; i++)
		{
			getComponents()[i].setBackground(getBackgroundColor(mAlternate));
			getComponents()[i].setForeground(getForeground());
		}
	}
	public void setSelected(boolean selected)
	{
		this.mSelected=selected;
		for(int i=0; i < getComponents().length; i++)
		{
			getComponents()[i].setBackground(getBackgroundColor(mAlternate));
			getComponents()[i].setForeground(getForeground());
		}
	}
	public SPEntry(SPContentView mContentView, SpotifyWindow mContext, Activity mActivity, URI uri)
	{
		super(mActivity.getContext());
		mMusicEntry=false;
		this.mContentView=mContentView;
		this.mContext = mContext;
		
	}
	@Override
	public void setHost(SPContentView mHost)
	{
		this.mContentView=mHost;
	}
	public SPEntry(Activity mActivity,SPContentView mContentView, SpotifyWindow mContext,ISPEntry entry)
	{
		
		super(mContext);
		this.mActivity=mActivity;
		this.mEntry=entry;
		
		
		// Initialize variables
		this.mContentView=mContentView;
		this.mContext=mContext;
		try{
			this.setUri(mEntry.getUri());
			setTransferHandler(new TransferHandler(getUri().toLinkString()));
			this.setAuthorUri(mEntry.getAuthorUri());
			this.mPopularity=entry.getPopularity();
			this.setCollectionUri(mEntry.getCollectionUri());
			this.setPlaylistUri(mEntry.getPlaylistUri());
		}catch(Exception e){
			
		
			
		}
		
		
		// add mouse handle
		setOnClickListener(new SPOnClickListener(){

			@Override
			public void Click(Object sender, Object args) {
				
			}
			
		});
		addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getClickCount() == 2)
				{
					play();
					
				}
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
				if(arg0.getButton() == MouseEvent.BUTTON1)
				{
					getTransferHandler().exportAsDrag((JComponent)arg0.getSource(), arg0,TransferHandler.COPY);
					
					selectItem();
				}
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
	
		});
	}
	public void selectItem()
	{
		// TODO Auto-generated method stub
		
		// Deselect all entries
		ArrayList<ISPEntry> entries = 	mContentView.getEntries();
		for(ISPEntry entry : entries)
		{
			// deselect the entry
			entry.setSelected(false);
			if(entry instanceof Component)
			{
				((Component)entry).repaint();
				
			}
	
		}
		
		// set this selected as true
		SPEntry.this.setSelected(true);
		
		// repaint the component
		SPEntry.this.repaint();
	}
	
	
	private URI mUri;
	private URI mAuthorUri;
	private URI mCollectionUri;
	private URI mPlaylistUri;
	private boolean mSelected;
	private boolean mAlternate = true;
	
	public Color getBackColor()
	{
		return getContext().getSkin().getBackgroundColor();
	}
	public Color getAltBackColor()
	{
		return getContext().getSkin().getAlternateBgColor();
	}
	public Color getForeground()
	{
		return getContext().getSkin().getForeColor();
	}
	public Color getSelectedBgColor()
	{
		return getContext().getSkin().getSelectionBg();
	}
	public Color getSelectedFgColor()
	{
		return getContext().getSkin().getSelectionFg();
	}
	public Image getSelectionBackground()
	{
		return getContext().getSkin().getSelectionBackground();
	}

	@Override
	public void  draw(Graphics g)
	{
		
			draw(g,0,0,getWidth(),getHeight(),mAlternate);
		
	}
	/***
	 * Returns the background color
	 * @param alternative
	 * @return
	 */
	public Color getBackgroundColor(boolean alternative)
	{
		return isSelected() ? getSelectedBgColor() :( alternative ?(getBackColor()) : getAltBackColor());
	
	}
	/**
	 * Draws the ViewEntry on the list 
	 * @param g
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	
	public void draw(Graphics g,int x,int y,int width,int height,boolean alternative)
	{
		alternative = this.mAlternate;
		URI currentPlayingSong =  getContext().getCurrentPlayingSong();
		SPContentView currentPlayingView = getContext().getCurrentPlaylist();
		// depending on the rowindex, fill it with either alternate or backcolor
		g.setColor(getBackgroundColor(alternative));
		g.fillRect(x,y,width,height);
		
		boolean playingEntity = false;
		if(getUri() != null)
			playingEntity = getUri().equals(currentPlayingSong);
		
		if(playingEntity)
		{
			g.setColor(getContext().getSkin().getPlayingBg());
		}
		
		
		setBackground(getBackgroundColor(alternative));	
		
		// if selected draw selected bg
		if(isSelected())
		{
	//		g.drawImage(getSelectionBackground(), x,y,width,height,null);
			g.setColor(getSelectedBgColor());
			g.fillRect(x,y,width,height);	
		}
		if(!mMusicEntry)
		{
			for(Component d : getComponents())
			{
				// If the item is SPLabel change the appearance
				if(d instanceof SPLabel)
				{
					((SPLabel)d).setShadow(!isSelected());
					((SPLabel)d).setColor(isSelected() ? getSelectedFgColor() : getContext().getSkin().getForeColor() );
					((SPLabel)d).setBackground(isSelected() ?  getSelectedBgColor() : (alternative ?  getContext().getSkin().getBackgroundColor()  :  getContext().getSkin().getAlternateBgColor()));
					
				}
				g.translate(d.getLocation().x,d.getLocation().y);
				d.paint(g);
				g.translate(-d.getLocation().x,-d.getLocation().y);
			}
			return;
		}
		
		Color foreColor =( isSelected() ? getSelectedFgColor() : ( playingEntity ? getContext().getSkin().getPlayingFg() : getForeground()));
		
		if(playingEntity)
		{
			g.setColor(getContext().getSkin().getPlayingBg());
		}
		g.setColor(foreColor);
		g.setFont(getContext().getSkin().getFont());
		// draw Strings
		if(getUri() != null)
		{
				getContext().getSkin().drawText(Duration.zerofill(getTrackNumber()),foreColor.darker(),g,x+30,y+12,!isSelected()&&!playingEntity);
				getContext().getSkin().drawText(getUri().getTitle(),foreColor,g, x+48, y+12,!isSelected()&&!playingEntity); // draw Artist name
				if(getAuthorUri()!=null)
					getContext().getSkin().drawText(getAuthorUri().getTitle(),foreColor,g, x+480,y+12,!isSelected()&&!playingEntity);
				
		}
		
		// draw speaker icon
		if(playingEntity)
		{
			
			int speakerIcon = isSelected() ? DefaultSkin.IMG_PLAYBACK_ICON_SELECTED : currentPlayingView == getHost() ? DefaultSkin.IMG_PLAYBACK_ICON : DefaultSkin.IMG_PLAYBACK_ICON_DIFFERENT_VIEW;
			try
			{
				getContext().getSkin().getImageById(speakerIcon);
			} 
			catch(Exception e)
			{
				
				
			}
		}
		// Draw popularity
		int mode = DefaultSkin.MODE_NORMAL;
		if(playingEntity)
			mode = DefaultSkin.MODE_PLAYING;
		if(isSelected())
			mode = DefaultSkin.MODE_SELECTED;
		if(isMusicEntry())
			getContext().getSkin().drawPopularity(getPopularity(),12,g,420,4,8,mode);
		
		Duration duration = getDuration();
		// Draw duration
		if(duration !=null && isMusicEntry())
		{
			
			getContext().getSkin().drawText(duration.toString(),foreColor.darker(),g,420-50, 12,false);
		}
		
	}

	@Override
	public void activate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public URI getUri() {
		// TODO Auto-generated method stub
		return mUri;
	}
	
	@Override
	public boolean isSelected() {
		// TODO Auto-generated method stub
		return mSelected;
	}

	@Override
	public void setUri(URI uri) {
		// TODO Auto-generated method stub
		this.mUri=uri;
	}
	public void setAuthorUri(URI mAuthorUri) {
		this.mAuthorUri = mAuthorUri;
	}
	public URI getAuthorUri() {
		return mAuthorUri;
	}
	public void setCollectionUri(URI mCollectionUri) {
		this.mCollectionUri = mCollectionUri;
	}
	public URI getCollectionUri() {
		return mCollectionUri;
	}
	public void setPlaylistUri(URI mPlaylistUri) {
		this.mPlaylistUri = mPlaylistUri;
	}
	public URI getPlaylistUri() {
		return mPlaylistUri;
	}
	@Override
	public SpotifyWindow getContext() {
		// TODO Auto-generated method stub
		return mContext;
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
	public boolean isLink() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setOnClickListener(SPOnClickListener mListener) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public SPContentView getHost() {
		// TODO Auto-generated method stub
		return mContentView;
	}
	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return super.isVisible();
	}
	@Override
	public int getLeft() {
		// TODO Auto-generated method stub
		return mLeft;
	}
	@Override
	public int getTop() {
		// TODO Auto-generated method stub
		return mTop;
	}
	@Override
	public void setLeft(int mLeft) {
		// TODO Auto-generated method stub
		this.mLeft = mLeft;
	}
	
	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return super.getHeight();
	}
	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return super.getWidth();
	}
	@Override
	public void setHeight(int height) {
		
	}
	
	@Override
	public void setTop(int top) {
		// TODO Auto-generated method stub
		this.mTop = top;
	}
	@Override
	public void setWidth(int width) {
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
	public void play() {
		if(getUri() != null)
		{
			// TODO Auto-generated method stub
			if(getUri().getApplication().equals("track"))
			{
				getContext().playSong(this);
			}
		}
	}
	@Override
	public Activity getActivity() {
		// TODO Auto-generated method stub
		return mActivity;
	}
	@Override
	public SPContentView getPlaylist() {
		// TODO Auto-generated method stub
		return mContentView;
	}
	public void setAlternate(boolean b) {
		// TODO Auto-generated method stub
		mAlternate=b;
	}

	@Override
	public float getPopularity() {
		// TODO Auto-generated method stub'
		if(mPopularity != null)
		return mPopularity;
		else
			 return 0.0f;
	}

	@Override
	public Duration getDuration() {
		// TODO Auto-generated method stub
		if(mEntry != null)
			if(mEntry.getDuration()!= null)
				return mEntry.getDuration();
			else
				return new Duration(0);
		else
			return new Duration(0);
			
	}
	public void setTrackLength(String mTrackLength) {
	}

	@Override
	public String getReleaseType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setReleaseType(String type) {
		// TODO Auto-generated method stub
		
	}

	public void setTrackNumber(int mTrackNumber) {
		this.mTrackNumber = mTrackNumber;
	}

	public int getTrackNumber() {
		return mTrackNumber;
	}

	
	

}
