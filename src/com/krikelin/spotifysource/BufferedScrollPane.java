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
import java.awt.Image;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
 
public class BufferedScrollPane extends JScrollPane implements SPWidget {
	Dimension dim;
	private int mTopPosition = 20;
	private int mPadding = 2;
	private Image texturedBackground;
	/**
	 * Adds an element to the content view.
	 * Element are stacked downwards
	 * @param elm component to add
	 * @param padding padding of the size
	 * @param height the height of the element
	 */
	public int addElement(Component elm,int left, int width, int height)
	{
	add(elm);
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
	int psize =64;
	@Override
	public void paint(Graphics g) {
		
		super.paint(g);
	
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
		//		c.setLocation(c.getLocation().x,c.getLocation().y-elmHeight);
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
	private static final long serialVersionUID = 8423934581253592490L;
	public JPanel getContentView()
	{
		return mPanel;
	}
	
	public void draw(Graphics g)
	{
		
	}
	JPanel mPanel;
	private int id;
	public BufferedScrollPane(SpotifyWindow mContext,JPanel p)
	{
		
		super(p);
		/**
		 * Set layout handling
		 */
		BoxLayout fl = new BoxLayout(p,BoxLayout.Y_AXIS);
		p.setLayout(fl);
		
		p.setBackground(mContext.getSkin().getBackgroundColor());
		mPanel=p;
	
		p.setPreferredSize(null);
		getVerticalScrollBar().setUnitIncrement(20);
		
		getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener(){

			@Override
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				// TODO Auto-generated method stub
				// get table
				
				if(arg0.getAdjustable().getUnitIncrement() > 0)
				{
					arg0.getAdjustable().getUnitIncrement();
				
				}
				else
				{
					arg0.getAdjustable().getUnitIncrement();
				}
			}
			
		});
		// TODO Fix scrolling issue
		
	}
	@Override
	public Component add(Component comp, int index) {
		// TODO Auto-generated method stub
		return mPanel.add(comp,index);
	}
	@Override
	public Component add(Component comp) {
		// TODO Auto-generated method stub
		try
		{
			return mPanel.add(comp);
		}
		catch(Exception e)
		{
			return comp;
		}
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
    	
	}
	public Image getTexturedBackground() {
		return texturedBackground;
	}
	public void setTexturedBackground(Image texturedBackground) {
		this.texturedBackground = texturedBackground;
	}
	@Override
	public SpotifyWindow getContext() {
		// TODO Auto-generated method stub
		return null;
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
