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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;

public class BufferedContainer extends JComponent {
	Dimension dim;
	
	/**
	 * Current top position
	 */
	private int mTopPosition = 20;
	/**
	 * Returns the current top position;
	 * @return integer the top position
	 */
	public int getTopPosition()
	{
		return mTopPosition;
	}
	private int mPadding = 2;

	public int getPadding() {
		return mPadding;
	}
	public void setPadding(int mPadding) {
		this.mPadding = mPadding;
	}
	/**
	 * Adds an element to the content view.
	 * Element are stacked downwards
	 * @param elm component to add
	 * @param padding padding of the size
	 * @param height the height of the element
	 */
	public int addElement(Component elm,int left, int width,int height)
	{
		elm.setBounds(mPadding+left,mTopPosition,width-left-mPadding*2,height);
		mTopPosition+=height+mPadding;
		return mTopPosition;
	}
	public void removeElement(Component elm)
	{
		int top = -1; 									// top of the current element
		int elmHeight = elm.getBounds().height; 		// the height of the current element
		for(int i=0; i < getComponents().length; i++)	
		{
			Component c = getComponents()[i];
			
			/**
			 * If top is not -1 we know that we passed the current element
			 * and begin push all elements back
			 */
			if( top != -1)
			{
				c.setLocation(c.getLocation().x,c.getLocation().y-elmHeight);
			}
			
			/**
			 * If c is equals start witdraw the element and push
			 * all elements after it backward
			 */
			if(c.equals(elm))
			{
				top = elm.getLocation().y;
				remove(elm);
				
			}
			
		}
		// remove the top
		this.mTopPosition -= elmHeight + mPadding;
	}
	public void hideElement(Component elm)
	{
		int top = -1; 									// top of the current element
		int elmHeight = elm.getBounds().height; 		// the height of the current element
		for(int i=0; i < getComponents().length; i++)	
		{
			Component c = getComponents()[i];
			
			/**
			 * If top is not -1 we know that we passed the current element
			 * and begin push all elements back
			 */
			if( top != -1)
			{
				c.setLocation(c.getLocation().x,c.getLocation().y-elmHeight);
			}
			
			/**
			 * If c is equals start witdraw the element and push
			 * all elements after it backward
			 */
			if(c.equals(elm))
			{
				top = elm.getLocation().y;
				elm.setVisible(false);
				
			}
			
		}
		// remove the top
		this.mTopPosition -= elmHeight + mPadding;
	}
	public void showElement(Component elm)
	{
		int top = -1; 									// top of the current element
		int elmHeight = elm.getBounds().height; 		// the height of the current element
		for(int i=0; i < getComponents().length; i++)	
		{
			Component c = getComponents()[i];
			
			/**
			 * If top is not -1 we know that we passed the current element
			 * and begin push all elements back
			 */
			if( top != -1)
			{
				c.setLocation(c.getLocation().x,c.getLocation().y+elmHeight);
			}
			
			/**
			 * If c is equals start witdraw the element and push
			 * all elements after it backward
			 */
			if(c.equals(elm))
			{
				top = elm.getLocation().y;
				elm.setVisible(true);
				elm.setVisible(true);
			}
			
		}
		// remove the top
		this.mTopPosition -= elmHeight + mPadding;
	}
	private void init()
	{
		
		
	}
	Graphics bufferGraphics ;
	/**
	 * 
	 */
	private static final long serialVersionUID = 8423934581253592490L;
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		draw(g);
	}
	
	public void draw(Graphics g)
	{
		
	}
	
	public BufferedContainer(SpotifyWindow mContext)
	{
		
	
		setBackground(mContext.getSkin().getBackgroundColor());
	
	
		init();
		
	}
	@Override
	public void setSize(Dimension dim)
	{
	
		super.setSize(dim);
		init();
	}
	@Override
	public void update(Graphics g) 
	{ 
    	paint(g); 
	} 
}
