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
package com.krikelin.spotifysource.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.SwingUtilities;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.krikelin.spotifysource.*;

public class artist extends Activity {
	
	ArrayList<ISPEntry> mAlbums;
	private URI mUri;
	public class Listeners extends SPContentView
	{
		public class User extends SPEntry
		{
			@Override
			public SpotifyWindow getContext()
			{
				return artist.this.getContext();
			}

			/**
			 * 
			 */
			private static final long serialVersionUID = -8987665586315479610L;
			@Override
			public SPContentView getHost()
			{
				return super.getHost();
			}
			public User(SpotifyWindow mContext,String imageUrl,String UserName) {
				super(Listeners.this,artist.this.getContext(), artist.this, new URI("spotify:lastfm_user:"+UserName));
		
				// TODO Auto-generated constructor stub
				// Create the avatar
				ImageBox im = new ImageBox(artist.this.getContext().getSkin().getReleaseImage(),artist.this.getContext());
				if(imageUrl!=null)
				try {
					im.downloadImage(new URL(imageUrl));
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				im.setBounds(2,2,24,24);
				add(im);
				
				
				SPLabel labelName = new SPLabel(artist.this.getContext(),"");
				labelName.setText(UserName);
				labelName.setBounds(60,7,100,20);
				add(labelName);
				addMouseListener(new MouseListener(){

					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						selectItem();
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
				});
			
				
			}
			
		}
		/**
		 * 
		 */
		private static final long serialVersionUID = 42950951403445964L;
		public Listeners(final String artistName )
		{
			super(artist.this,artist.this.getContext());
			artist.this.getContext();
			
			Runnable action = new Runnable(){
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					// Collect list from last.fm
					try {
						Document d = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new URL("http://ws.audioscrobbler.com/2.0/?method=artist.gettopfans&artist="+artistName+"&api_key=b25b959554ed76058ac220b7b2e0a026").openStream());
						
				
						NodeList nc = d.getElementsByTagName("user");
						boolean cf = false; 
						for(int i=0; i < nc.getLength(); i++)
						{
							Element elm = (Element)nc.item(i);
							String userName = elm.getElementsByTagName("name").item(0).getFirstChild().getNodeValue();
						//	String image = elm.getElementsByTagName("image").item(0).getFirstChild().getNodeValue();
							User userEntry = new User(artist.this.getContext(),null,userName);
							userEntry.setAlternate(!cf);
							cf=!cf;
							userEntry.setPreferredSize(new Dimension(16,28));
							addElement(userEntry,0,1628,128);
								
							
						
						}
						doLayout();
						
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
				}
				
			};
			try {
				if(!SwingUtilities.isEventDispatchThread())
				{
					SwingUtilities.invokeAndWait(action);
				}
				else{
					SwingUtilities.invokeLater(action);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public class Overview extends SPContentView
	{
	
		/**
		 * 
		 */
		private static final long serialVersionUID = 1671854906555327802L;
		public  Overview()
		{
			super(artist.this,artist.this.getContext());
			mAlbums=mArtist;
			/*		mArtistHeader = new ArtistHeader(artist.this.getContext().getSkin().getReleaseImage(),"Test","Test");
			mArtistHeader.setPreferredSize(new Dimension(getWidth(),128));
			add(mArtistHeader);
		add(mHeader);
			mHeader.setBounds(2,2,33,33);
			mHeader.setForeground(new Color(0xaaffaa));
			*/
			ArtistHeader ah = new ArtistHeader(artist.this.getContext().getSkin().getReleaseImage(),"Artist","Biography");
			
			ah.setPreferredSize(new Dimension(600,128));
			add(ah);
			int top = 100;
			// Create albums
			// TEMPORARY for now all albums is an release
			top = addSection("Release","Releases",top);

			//top = addSection("Album","Albums",top);
			//top = addSection("EP","EP's",top);
			//top = addSection("Single","Singles",top);
			
			
			/*for(URI track : mAlbumTracks)
			{
				SPEntry entry = new SPEntry(this,getContext(),track,new URI("","spotify:a:a"),new URI("","spotify:ac:c"),new URI("","spotify:ac:c"));
				entry.setBounds(67,10+i*mTrackHeight,128,mTrackHeight); 
				
				add(entry);
				i++;
			}*/
			doLayout();
		}
		
		// Add an header list
		/***
		 * Adds a section with albums of the specified type
		 * @param releaseType
		 * @param i
		 * @return
		 */
		public int addSection(String releaseType,String displayName,int top)
		{
			int ctop  = 0;
			SPDivider divider = new SPDivider(artist.this.getContext());
			divider.setText(displayName);
			ctop = addElement(divider,0,1628,28);
			divider.setVisible(false);
			divider.setPreferredSize(new Dimension(3162,28));
			boolean hasAlbums = false;
			
			for(ISPEntry album : mAlbums)
			{
				
				if(album.getReleaseType() != releaseType)
					continue;
				hasAlbums=true;
				Album _album = new Album(100    ,100,album.getUri(),album.getUri().toString(), artist.this,album.getAssets(),artist.this.getContext(),this);
				
				addElement(_album,0,1628,128);
				
		
				
			}
			if(hasAlbums)
				divider.setVisible(true);
			return ctop;
			/*if(false){
			
			
				SPDivider divider = new SPDivider(artist.this.getContext());
				divider.setBounds(0,top,2640,23);
				divider.setText(displayName);
				
				top+=divider.getHeight();
			
		
			
			hasAlbums = false;
		
			for(ISPEntry album : mAlbums)
			{
				
				if(album.getReleaseType() != releaseType)
					continue;
				hasAlbums=true;
				Album _album = new Album(album.getUri(),album.getUri().toString(), artist.this,album.getAssets(),artist.this.getContext(),this);
				
				add(_album);
				_album.setBounds(0,top,2640,128);
				_album.autoSize();
				top+=_album.getHeight();
				
			}
			if(hasAlbums)
				add(divider);
				
				return top;
			}*/
		}
	}
	/**
	 * 
	 */

	
	

	public class ArtistHeader extends BufferedContainer
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1176723340441814670L;
		private ImageBox mIcon;
		
		public ArtistHeader(Image logo,String artistName,String bio)
		{
			super(artist.this.getContext());
			// Initialize the image
			mIcon = new ImageBox(logo,artist.this.getContext());
		
			
			setLayout(new BorderLayout());
			mIcon.setPreferredSize(new Dimension(128,128));
			add(mIcon,BorderLayout.WEST);
			
			// Add middle layout
			
			BufferedContainer middle = new BufferedContainer(artist.this.getContext());
			
			middle.setLayout(new FlowLayout());
			add(middle,BorderLayout.CENTER);
			
			// Add right layout
			BufferedContainer right = new BufferedContainer(artist.this.getContext());
			
			right.setLayout(new FlowLayout());
			right.setPreferredSize(new Dimension(128,128));
			// Add favorites button
			SPButton sb = new SPButton(artist.this.getContext());
			sb.setLabel("Add to favorites");
			sb.setPreferredSize(new Dimension(320,64));
			sb.addMouseListener(new MouseListener(){

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					artist.this.getContext().addToFavorites(artist.this.getUri());
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
			});
			right.add(sb);
			
			
			add(right,BorderLayout.EAST);
			
		}
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1247612331061592226L;

	@Override
	public void onCreate(URI referrer,SpotifyWindow context)
	{	
		super.onCreate( referrer, context);
		
		setUri(referrer);
	}
	
	public void setUri(URI mUri) {
		this.mUri = mUri;
	}
	public URI getUri() {
		return mUri;
	}
	private ArrayList<ISPEntry> mArtist;
	
	@Override
	public Object onLoad(URI args) {
		// TODO Auto-generated method stub
		SpotifySearch ss = new SpotifySearch();
		mArtist = ss.getArtist("http://ws.spotify.com/lookup/1/?uri="+args.toLinkString()+"&extras=albumdetail",artist.this,null);
		return mArtist;
	}

	@Override
	public void render(URI args,Object... result) {
		// TODO Auto-generated method stub
		addPage("Overview",new Overview());
		//addPage("Listeners",new Listeners("darude"));
	}
}
