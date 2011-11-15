package com.krikelin.spotifysource;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.android.ninepatch.NinePatch;

public class SPButton extends Canvas implements SPWidget, SPPart {
	private SPOnClickListener mClickListener;
	private SPSkin mSkin;
	
	private String mLabel;
	private SpotifyWindow mHost;
	public SPButton(SpotifyWindow host)
	{
		mHost=host;
	}
	
	@Override
	public void paint(Graphics arg0) { 
		// TODO Auto-generated method stub 
		super.paint(arg0); 
		NinePatch c = pressed ? getContext().getSkin().getButtonPressedImage() : getContext().getSkin().getButtonImage();
		// draw left corner
		drawButton(c,arg0);
		if(mLabel != null)
		{
			getContext().getSkin().drawText(mLabel,getContext().getSkin().getForeColor(), arg0,getLocation().x+getWidth()/4, getLocation().y+getHeight()/4, new Color(0xffffff));
		} 
		
	} 
	private void drawButton(NinePatch c,Graphics g)
	{
		int threshold=12;
		c.draw((Graphics2D)g, 0, 0, getWidth(), getHeight());
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 8854570483322615169L;

	public SPButton() {
		// TODO Auto-generated constructor stub
		addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				((SPButton)arg0.getSource()).mClickListener.Click(arg0.getSource(), arg0);
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
				pressed=true;
				repaint();
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				pressed=false;
				repaint();
			}
			
		});
	}

	boolean pressed = false;
	@Override
	public SPOnClickListener getOnClickListener() {
		// TODO Auto-generated method stub
		return mClickListener;
	}

	@Override
	public void setOnClickListener(SPOnClickListener listener) {
		// TODO Auto-generated method stub
		mClickListener=listener;
	}
	
	@Override
	public void setLabel(String label) {
		// TODO Auto-generated method stub
		mLabel=label;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return mLabel;
	}

	@Override
	public SpotifyWindow getContext() {
		// TODO Auto-generated method stub
		return mHost;
	}

	public void setText(String string) {
		// TODO Auto-generated method stub
		mLabel = string;
	}

}
