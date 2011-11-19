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
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JComponent;

public class SPTabBar extends com.krikelin.spotifysource.BufferedCanvas implements SPWidget, com.krikelin.spotifysource.SPPart {
	private int id;
	/***
	 * Event handler for onLinkClick
	 * @author Alex
	 *
	 */
	public abstract class OnLinkClickHandler
	{
		/***
		 * Occurs on link click
		 * @param source
		 */
		public abstract void onLinkClick(URI source);
		
	}
	public class SPTab
	{
		public String Title;
	
	}
	private OnLinkClickHandler mOnLinkClickHandler;
	private OnTabChangeListener mOnTabChangeHandler;
	private ArrayList<SPTab> mTabs = new ArrayList<SPTab>();
	/**
	 * Returns tab
	 * @return
	 */
	public ArrayList<SPTab> getTabs()
	{
		return mTabs;
	}
	private int mX;
	private int mHoveredIndex=0;
	public void setActiveTab(int index){
		setActiveTab(index, true);
	}
	/**
	 * Adds an tab to the specified location
	 * @param name The name of the tab.
	 */
	public void add(String name)
	{
		SPTab overview = new SPTab();
		overview.Title=name;
		mTabs.add(overview);
	}
	/**
	 * Adds an tab to the specified location
	 * @param name The name of the tab.
	 */
	public void remove(String name)
	{
		SPTab g = null;
		for(SPTab t : mTabs){
			if(t.Title == name){
				g = t;
				break;
			}
		}
		if(g != null){
			mTabs.remove(g);
		}
	}
	private Activity currentActivity;
	/**
	 * Inserts an tab to the specified location
	 * @param name
	 * @param pos
	 */
	public void insert(String name,int pos)
	{
		SPTab overview = new SPTab();
		overview.Title=name;
		mTabs.add(pos,overview);
	}
	int tab_width =80;
	private SpotifyWindow mContext;
	public SPTabBar(SpotifyWindow context, Activity Activity)
	{
		super(context);
		mTabs = new ArrayList<SPTab>();
		mContext=context;
		this.currentActivity=Activity;
		addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				
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
				// TODO Auto-generated method stub
				
				if(mHoveredIndex > -1 )
				{
					mSelectedIndex=mHoveredIndex;
					// Throw the mousedown handler
					
					if(mOnTabChangeHandler!= null)
					{
						try
						{
							mOnTabChangeHandler.onTabChange(mHoveredIndex,mTabs.get(mHoveredIndex).Title);
						}
						catch(Exception e)
						{
							
						}
					}
				
				}
				if(currentActivity!=null){
					String name = currentActivity.getTitle();
					SPTabBar.this.getFontMetrics(getFont()).stringWidth(name);
					
				}
				int x = 0;
				for(URI uri : mViewTree)
				{
					
					int x2 = x - (SPTabBar.this.getFontMetrics(getFont()).stringWidth(uri.toString()) + 5);
					x2 += getWidth();
					x += getWidth();
					if(arg0.getPoint().x <= x && arg0.getPoint().x >= x2)
					{
						if(mOnLinkClickHandler!=null)
							SPTabBar.this.mOnLinkClickHandler.onLinkClick(uri);
					}
					x = x2;
					
				}
				
				repaint();
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		addMouseMotionListener(new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
				mX=arg0.getX();
				arg0.getY();
				int pos = 0;
				for(int i=0; i < mTabs.size(); i++)
				{
					tab_width = SPTabBar.this.getFontMetrics(getFont()).stringWidth(mTabs.get(i).Title) + card*2;
					
					if(mX >= pos  && mX <= pos + tab_width)
					{
						mHoveredIndex=i;
						
						break;
					}
					pos += tab_width ; 
					int x = getWidth();
					x = 0;
					for(URI uri : mViewTree)
					{
						
						int x2 = x - (SPTabBar.this.getFontMetrics(getFont()).stringWidth(uri.toString()) + 5);
						x2 += getWidth();
						x += getWidth();
						if(arg0.getPoint().x <= x && arg0.getPoint().x >= x2)
						{
							setCursor(new Cursor(Cursor.HAND_CURSOR));	
						}
						x = x2;
						
					}
				}
			}
			
		});
		setMinimumSize(new Dimension(30,28));
	}
	private ArrayList<URI> mViewTree =new ArrayList<URI>();
	public ArrayList<URI> getViewTree()
	{
		return mViewTree;
	}
	/**
	 * @return the mViewTree
	 */
	public ArrayList<URI> getmViewTree() {
		return mViewTree;
	}
	/**
	 * @param mViewTree the mViewTree to set
	 */
	public void setmViewTree(ArrayList<URI> mViewTree) {
		this.mViewTree = mViewTree;
	}
	int card = 15;
	@Override 
	public void draw(Graphics g) { 
		// TODO Auto-generated method stub
		
		g.drawImage(getContext().getSkin().getTabBarBackground(), 0,0,getWidth(),getHeight(), 0,0, 1, 25,null);
		int pos = 0;
		int left = 0;
		
		mHoveredIndex=0;
		for(SPTab tab : mTabs)
		{
			Image tabBackground =  pos == getSelectedIndex() ?  getContext().getSkin().getActiveTabBackground() : getContext().getSkin().getTabBackground();
			int width = g.getFontMetrics().stringWidth(tab.Title);
			int padding = 0;
			if( pos == getSelectedIndex() ){
				padding= 1;
					;
			}
			// draw the tab
			g.drawImage(tabBackground,left-padding,0,left+card+padding,getHeight(),0,0,card,25,null); // draw left corner
			g.drawImage(tabBackground,left+card,0,left+card +width,getHeight(),12,0,13,25, null); // draw fillings
			g.drawImage(tabBackground,left+width+card,0,left+width+card +card,getHeight(),tabBackground.getWidth(null)-card,0,tabBackground.getWidth(null),25,null);
			
			Color fgColor = (  pos == getSelectedIndex() ? getContext().getSkin().getActiveTabForeColor()   :  getContext().getSkin().getTabForeColor() );
			if(pos == getSelectedIndex())
			{
				getContext().getSkin().drawText(tab.Title,fgColor,g,left+card,16,false);
			}
			else
			{
				getContext().getSkin().drawText(tab.Title,fgColor,g,left+card,16,getContext().getSkin().getColorById(DefaultSkin.COLOR_TAB_SHADOW),0,-1);
				
			}

			left+=width+card*2;
			
			pos++;
		}
		int i = 0;
		g.setFont(getFont().deriveFont(Font.BOLD));
		
		/***
		 * Draw the view tree
		 */
		if(mViewTree != null)
		{
			i = - 10 ;
			for(URI uri : mViewTree)
			{
				i-= g.getFontMetrics().stringWidth(uri.toString()) + 5;
				getContext().getSkin().drawText(uri.toString(), getContext().getSkin().getTabForeColor(), g, getWidth() - 10 - i, 16, new Color(0xdddddd));
				
				
			}
		}
		int t_width = 0;
		if(currentActivity!=null){
			String name = currentActivity.getTitle();
			t_width = SPTabBar.this.getFontMetrics(getFont()).stringWidth(name) + 10;
			getContext().getSkin().drawText(name,getContext().getSkin().getTabForeColor(),g, getWidth()-t_width - i - 10, 16, new Color(0xeeeeee));
		}
		g.setFont(getFont().deriveFont(Font.BOLD));
		
	}
	
	private int mSelectedIndex = 0;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7677759887440328475L;
	
	@Override
	public SpotifyWindow getContext() {
		// TODO Auto-generated method stub
		return mContext;
	}

	@Override
	public SPOnClickListener getOnClickListener() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnClickListener(SPOnClickListener listener) {
		// TODO Auto-generated method stub
		
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
	public void setSelectedIndex(int mSelectedIndex) {
		this.mSelectedIndex = mSelectedIndex;
	}
	public int getSelectedIndex() {
		return mSelectedIndex;
	}
	public void addTabChangeHandler(OnTabChangeListener mOnTabChangeHandler) {
		this.mOnTabChangeHandler = mOnTabChangeHandler;
	}
	public OnTabChangeListener getOnTabChangeHandler() {
		return mOnTabChangeHandler;
	}
	public Activity getCurrentActivity() {
		return currentActivity;
	}
	public void setCurrentActivity(Activity currentActivity) {
		this.currentActivity = currentActivity;
	}
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return id;
	}
	@Override
	public void setID(int id) {
		// TODO Auto-generated method stub
		this.id = id;
	}
	@Override
	public JComponent findViewById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	public void setActiveTab(int index, boolean b) {
		// TODO Auto-generated method stub
		mSelectedIndex = index;
		if(b){
			try{
				getOnTabChangeHandler().onTabChange(index, mTabs.get(index).Title);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

}
