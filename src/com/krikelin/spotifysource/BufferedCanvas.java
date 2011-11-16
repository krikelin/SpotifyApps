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

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;

public abstract class BufferedCanvas extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3271497928277578740L;
	Dimension dim;
	private void init()
	{
	
		
	}
	Graphics bufferGraphics ;
	@Override 
	public void paint(Graphics g)
	{
		/*dim = getSize();
		mBufferedImage =  createImage(dim.width,dim.height);
		bufferGraphics = mBufferedImage.getGraphics();
			firstTime=false;
		
		draw(bufferGraphics);
		g.drawImage(mBufferedImage,0,0,this);*/
		draw(g);
	}
	public void super_paint(Graphics g)
	{
		super.paint(g);
	}
 	public void draw(Graphics g)
 	{
 		
 	}
	
	public BufferedCanvas(SpotifyWindow mContext)
	{
		
		super();
		
		setBackground(mContext.getSkin().getBackgroundColor());
		init();
	}
	@Override
	public void setSize(Dimension dim)
	{
	
		super.setSize(dim);
	
	}
	@Override
	public void update(Graphics g) 
	{ 
    	paint(g); 
	} 
}
