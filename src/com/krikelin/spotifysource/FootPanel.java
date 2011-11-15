package com.krikelin.spotifysource;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class FootPanel extends BufferedContainer implements SPPart  {


		@Override
		public void draw(Graphics g) {
			// TODO Auto-generated method stub
			
			
			g.drawImage(getContext().getSkin().getHeaderImage(),0,0,getWidth(),getHeight(),0,0,12,21,null);	
		}
		private SpotifyWindow mHost;
		/**
		 * Search listener
		 * @author Alexander
		 *
		 */
		public abstract class OnSearchListener
		{
			public abstract void OnSearch(Object sender,String query);
		}
		private OnSearchListener mSearchListener;
		
		
		private TextField  mSearchBox;

		 public void update(Graphics g) 
	     { 
	          paint(g); 
	     } 
		/**
		 * 
		 */
		private static final long serialVersionUID = 255442890532618037L;
		public FootPanel(SpotifyWindow context)
		{
			super(context);
			this.mHost = context;
		//	setBackground(mHost.getSkin().getBackgroundColor());
			SPButton backButton = new SPButton(context);
			backButton.setPreferredSize(new Dimension(60,28));
			add(backButton);
			
			this.setLayout(new FlowLayout(FlowLayout.LEFT));
			
			
			setSize(new Dimension(100,620));
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
	
}
