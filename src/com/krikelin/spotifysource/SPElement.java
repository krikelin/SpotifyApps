package com.krikelin.spotifysource;

import java.awt.Graphics;
import java.awt.Point;

import com.krikelin.spotifysource.widget.events.*;
/***
 * Interface for dealing with abstract elements that can be drawn on the view
 * 
 * @author Alexander
 *
 */
public interface SPElement {
	/**
	 * Draws the element with the specified coordinates
	 * @param g
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
//	public void draw(Graphics g,int x,int y,int width,int height,boolean alt);
	/***
	 * Gets the cursor position relative to this object with relative to the object bounds
	 * @param srcLoc the position of the mouse
	 * @return
	 */
	public Point getMousePosition(Point srcLoc);
	public boolean isLink();
	public void setOnClickListener(SPOnClickListener mListener);
	public SPOnClickListener getOnClickListener();
	public boolean isVisible();
	public int getLeft();
	public int getTop();
	public void setLeft(int left);
	public void setTop(int top);
	public void setWidth(int width);
	public void setHeight(int height);
	public int getWidth();
	public int getHeight();
	public SPContentView getHost();
	
}
