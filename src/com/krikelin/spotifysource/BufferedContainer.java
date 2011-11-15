package com.krikelin.spotifysource;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

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
		// super add the element
		Component c =  super.add(elm);
		mTopPosition+=height+mPadding;
		return mTopPosition;
	}
	public void removeElement(Component elm)
	{
		int top = -1; 									// top of the current element
		int ptop = 0; 									// previous top of the element
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
		int ptop = 0; 									// previous top of the element
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
		int ptop = 0; 									// previous top of the element
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
	private boolean firstTime = true;
	Graphics bufferGraphics ;
	/**
	 * 
	 */
	private static final long serialVersionUID = 8423934581253592490L;
	private Image mBufferedImage;
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
