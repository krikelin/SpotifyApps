package com.krikelin.spotifysource.views;

import java.awt.Color;
import java.awt.Container;

import javax.swing.Box;
import javax.swing.BoxLayout;

import com.krikelin.spotifysource.SPActivity;
import com.krikelin.spotifysource.SPContentView;
import com.krikelin.spotifysource.SPLabel;
import com.krikelin.spotifysource.SpotifyWindow;
import com.krikelin.spotifysource.URI;
import com.krikelin.spotifysource.views.home.Overview;

public class radio extends SPActivity {
	public class Overview extends Container
	{
		private SPLabel mHeader=  new SPLabel(radio.this.getContext(),"Radio");
		/**
		 * 
		 */
		private static final long serialVersionUID = 1580980909371828894L;
		public Overview()
		{
		
			setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			add(mHeader);
			mHeader.setForeground(new Color(0xaaffaa));
			add(Box.createVerticalGlue());
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
		addPage("Overview", new Overview());
	
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