package com.krikelin.spotifysource.views;
import com.krikelin.spotifysource.widget.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.ScrollPane;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.krikelin.spotifysource.view.*;
import com.krikelin.spotifysource.*;
public class album extends SPActivity {
	private URI mUri;
	ArrayList<ISPEntry> _tracks;
	public class Description extends Container
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -4722774524268074289L;
		public class MediaPhocket implements Runnable
		{

			@Override
			public void run() { 
				// TODO Auto-generated method stub
				
			}
			
		}
		public Description()
		{
			setLayout(new BorderLayout());
			Label review = new Label();
			review.setText("Users listening to this album now");
			
			
		
			
			
		}
	}
	public class Overview extends SPContentView
	{
		private SPLabel mHeader=  new SPLabel(album.this.getContext(),"What's new");
		/**
		 * 
		 */
		private static final long serialVersionUID = 1580980909371828894L;
		public Overview()
		{
			super(album.this,album.this.getContext());
		
			add(mHeader);
			mHeader.setBounds(2,2,33,33);
			mHeader.setForeground(new Color(0xaaffaa));
			ArrayList<ISPEntry> tracks = new ArrayList<ISPEntry>();
			SpotifySearch ss = new SpotifySearch();
			 
				for(ISPEntry uri : _tracks)
				{
					tracks.add(uri);
				}
			
			Album c = new Album(100,100,album.this.getUri(),album.this.getUri().toString(), album.this,tracks,album.this.getContext(),this);
			c.setBounds(2,2,1640,1480);
			add(c);
			
			int mTrackHeight = 18;
			int i=0;
			/*for(URI track : mAlbumTracks)
			{
				SPEntry entry = new SPEntry(this,getContext(),track,new URI("","spotify:a:a"),new URI("","spotify:ac:c"),new URI("","spotify:ac:c"));
				entry.setBounds(67,10+i*mTrackHeight,128,mTrackHeight);
				 
				
				add(entry);
				i++;
			}*/
			add(Box.createGlue());
			doLayout();
		}
		
		
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 8559581767908741443L;
	public void setUri(URI mUri) {
		this.mUri = mUri;
	}
	public URI getUri() {
		return mUri;
	}
	@Override
	public void onCreate(URI referrer,SpotifyWindow context)
	{	
		super.onCreate( referrer, context);


		setUri(referrer);
		
		
		doLayout();
	}
	@Override
	public Object onLoad(URI args) {
		// TODO Auto-generated method stub
		SpotifySearch ss = new SpotifySearch();
		try {
			_tracks = ss.getSongs("http://ws.spotify.com/lookup/1/?uri=%s&extras=trackdetail",  args.toLinkString() , album.this,null);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return _tracks;
	}
	@Override
	public void render(URI args,Object... result) {
		// TODO Auto-generated method stub
		addPage("Overview",new Overview());
	}
	
}
