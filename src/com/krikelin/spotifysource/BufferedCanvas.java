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
		super.paint(g);
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
		repaint();
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
