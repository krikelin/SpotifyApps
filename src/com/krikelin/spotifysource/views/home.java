package com.krikelin.spotifysource.views;
import com.krikelin.spotifysource.widget.*;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.ScrollPane;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;

import org.netbeans.lib.awtextra.AbsoluteLayout;

import com.krikelin.spotifysource.view.*;
import com.krikelin.spotifysource.*;
public class home extends SPActivity {
	public class Overview extends SPContentView
	{
		private SPLabel mHeader=  new SPLabel(home.this.getContext(),"What's new");
		/**
		 * 
		 */
		private static final long serialVersionUID = 1580980909371828894L;
		public Overview()
		{
			super(home.this,home.this.getContext());
		
			JScrollPane jsc = new JScrollPane();
			setTexturedBackground(getContext().getSkin().getDashedBackground());
			// Add an default image box
		/*	InputStream stream = getClass().getResourceAsStream("start.png");
	
			try {
				BufferedImage src =  ImageIO.read(stream);
				ImageBox iv = new ImageBox(src, home.this.getContext());
				add(iv);
				iv.setPreferredSize(new Dimension(640,480));
				iv.setLocation(12,12);
			}catch(Exception e){
				e.printStackTrace();
			}*/
		/*
 			add(mHeader);
			mHeader.setBounds(2,2,33,33);
			mHeader.setForeground(new Color(0xaaffaa));
			
	
			int mTrackHeight = 18;
			int i=0;
			/*for(URI track : mAlbumTracks)
			{
				SPEntry entry = new SPEntry(this,getContext(),track,new URI("","spotify:a:a"),new URI("","spotify:ac:c"),new URI("","spotify:ac:c"));
				entry.setBounds(67,10+i*mTrackHeight,128,mTrackHeight);
				 
				
				add(entry);
				i++;
			}*/
	
			doLayout();
		}
		
		
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 8559581767908741443L;
	@Override
	public void onCreate(URI referrer,SpotifyWindow context)
	{	
		super.onCreate( referrer, context);
		addPage("Welcome", new Overview());
	
		doLayout();
	}
	@Override
	public Object onLoad(URI args) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void render(URI args, Object... result) {
		// TODO Auto-generated method stub
		
	}
}
