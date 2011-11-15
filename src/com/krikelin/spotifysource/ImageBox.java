package com.krikelin.spotifysource;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.swing.SwingUtilities;

public class ImageBox extends BufferedCanvas
{
	private SpotifyWindow mContext;
	/**
	 * 
	 */
	private static final long serialVersionUID = -2226107120577730295L;  
	private Image mImage;
	public ImageBox(Image mImage,SpotifyWindow context)
	{
		super(context);
		mContext=context;
		this.mImage = mImage;
		
	}
	private URL mUri;
	public static Hashtable<String,Image> bitmaps = new Hashtable<String,Image>();
	private void download(URL source){
		
		InputStream IS;
		if(bitmaps.containsKey(source.toString()) )
		{
			mUri = source;
			mImage = bitmaps.get(source);
			
			return;
		}
		try {
			mUri = source;
			IS = source.openStream();
			
			bitmaps.put(source.toString(),mContext.getSkin().getReleaseImage());
			Image bitmap =	ImageIO.read(IS);
			
			mImage= bitmap;
			bitmaps.put(source.toString(),bitmap);
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
	}
	public void downloadImage(final URL source)
	{
		try {
			Runnable c = new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					download(source);
					
				}
					
			};
			
			if(!SwingUtilities.isEventDispatchThread())
			{
				
				SwingUtilities.invokeAndWait(c);
			
			}
			else
			{
				SwingUtilities.invokeLater(c);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub'
		if(mUri != null)
		{
			mImage = bitmaps.get(mUri.toString());
		}
		if(mImage != null)
			g.drawImage(mImage,0,0,getWidth(),getHeight(),null);
	}

	public void setImage(Image mImage) {
		this.mImage = mImage;
	}

	public Image getImage() {
		return mImage;
	}
	
}
