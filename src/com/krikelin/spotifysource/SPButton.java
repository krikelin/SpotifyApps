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

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import org.lobobrowser.html.style.FontStyleRenderState;

import com.android.ninepatch.NinePatch;

public class SPButton extends Canvas implements SPWidget, SPPart {
	private SPOnClickListener mClickListener;
	private String mLabel;
	private SpotifyWindow mHost;
	public SPButton(SpotifyWindow host)
	{
		mHost=host;
		addMouseListener(new SPMouseListener());
	
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
			
			getContext().getSkin().drawText(mLabel,new Color(0x777777), arg0,getWidth()/4, getLocation().y+getHeight()/3, new Color(0xcccccc));
		
			
		} 
		
	} 
	private void drawButton(NinePatch c,Graphics g)
	{
		c.draw((Graphics2D)g, 0, 0, getWidth(), getHeight());
	}
	public class SPMouseListener implements MouseListener{
		

			
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				((SPButton)arg0.getSource()).mClickListener.Click(arg0.getSource(), arg0);
			}

			
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

		
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				pressed=true;
				repaint();
			}

		
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				pressed=false;
				repaint();
			}
			
		};

		
	/**
	 * 
	 */
	private static final long serialVersionUID = 8854570483322615169L;

	public SPButton() {
		// TODO Auto-generated constructor stub
		addMouseListener(new SPMouseListener());
	}

	boolean pressed = false;
	private int id;
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
		return null;
	}

}
