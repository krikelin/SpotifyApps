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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.TextField;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.text.FlowView.FlowStrategy;



public class HeaderPanel extends BufferedContainer implements SPPart {

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
		
		g.drawImage(getContext().getSkin().getHeaderImage(),0,0,getWidth(),getHeight(),0,0,16,22,null);	
		for(Component d : getComponents())
		{
			g.translate(d.getLocation().x,d.getLocation().y);
			d.paint(g);
			g.translate(-d.getLocation().x,-d.getLocation().y);
		}
	}
	private SpotifyWindow mHost;
	/**
	 * Search listener
	 * @author Alexander
	 *
	 */
	
	private OnSearchListener mSearchListener;
	
	
	private TextField  mSearchBox;

	 public void update(Graphics g) 
     { 
          paint(g); 
          
     } 
	/**
	 * 
	 */
	 public abstract class NavigateListener
	 {
		 public static final int MODE_BACK = -1; 
		 public static final int MODE_FORWARD = 1;
		 public abstract void navigate(int mode);
		 public abstract void saveView();
		 
	 }
	 private NavigateListener mNavigateHandler;
	SPButton btnAddView;
	private static final long serialVersionUID = 255442890532618037L;
	public HeaderPanel(SpotifyWindow context)
	{
		
		super(context);
		this.mHost = context;
		mSearchBox = new TextField();
		setBorder(BorderFactory.createEmptyBorder());
		
		setBackground(mHost.getSkin().getBackgroundColor());
		FlowLayout fl = new FlowLayout(FlowLayout.LEADING);
		this.setLayout(fl );
		// Add back and forward button and an search box
		SPButton btn= new SPButton(getContext());
		btn.setText("<");
		btn.setPreferredSize(new Dimension(24,24));
		btn.setOnClickListener(new SPOnClickListener() {
			
			@Override
			public void Click(Object sender, Object args) {
				// TODO Auto-generated method stub
				if(mNavigateHandler!=null)
					mNavigateHandler.navigate(NavigateListener.MODE_BACK);
			}
		});
		this.add(btn);
		SPButton btnForward = new SPButton(getContext());
		btnForward.setText(">");
		btnForward.setPreferredSize(new Dimension(24,24));
		btnForward.setOnClickListener(new SPOnClickListener() {
			
			@Override
			public void Click(Object sender, Object args) {
				// TODO Auto-generated method stub
				
				// TODO Auto-generated method stub
				if(mNavigateHandler!=null)
					mNavigateHandler.navigate(NavigateListener.MODE_FORWARD);
			}
		});
		add(btnForward);
		this.add(mSearchBox);
		btnAddView = new SPButton(getContext());
		btnAddView.setText("+");
		btnAddView.setPreferredSize(new Dimension(24,24));
		btnAddView.setOnClickListener(new SPOnClickListener() {
			
			@Override
			public void Click(Object sender, Object args) {
				// TODO Auto-generated method stub
				
			}
		});
	
		setPreferredSize(new Dimension(40,40));
		mSearchBox.setPreferredSize(new Dimension(80,20));
		mSearchBox.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					// append spotify:search: to the beginning of the string
					// if it not are preceed with spotify: tag
					String query = mSearchBox.getText();
					if(!query.startsWith("spotify:"))
					{
						query="spotify:search:"+query;
					}
					// Invoke search
					HeaderPanel.this.mSearchListener.OnSearch(HeaderPanel.this,query);
					
					
				}
			}
		});
		add(btnAddView);
		repaint();
	}
	/**
	 * Gets the search query
	 * @return
	 */
	public String getSearchQuery()
	{
		return mSearchBox.getText();
		
	}
	/**
	 * Sets the handler for the search
	 * @param mSearchListener
	 */
	public void setSearchListener(OnSearchListener mSearchListener) {
		this.mSearchListener = mSearchListener;
	}
	
	/**
	 * Gets the handler for the search
	 * @return OnSearchListener the search listener
	 */
	public OnSearchListener getSearchListener() {
		return mSearchListener;
	}
	public void setContext(SpotifyWindow mHost) {
		this.mHost = mHost;
	}
	public SpotifyWindow getContext() {
		return mHost;
	}
	public void setNavigateHandler(NavigateListener mNavigateHandler) {
		this.mNavigateHandler = mNavigateHandler;
	}
	public NavigateListener getNavigateHandler() {
		return mNavigateHandler;
	}
}
