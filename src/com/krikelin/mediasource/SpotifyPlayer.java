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
package com.krikelin.mediasource;

import java.awt.Image;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.krikelin.spotifysource.IMCPlaybackEventListener;
import com.krikelin.spotifysource.ISPEntry;

import com.krikelin.spotifysource.URI;

public class SpotifyPlayer implements IMCSource {
	public static final int MOVE_SONG_PLAYLIST = 12515;
	
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}
	private URI currentSong;
	private int position;

	public SpotifyPlayer(){
		c = new Timer();
		c.scheduleAtFixedRate(new TimerTask(){

			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(playing){
					if(position >= getDuration() - 2){
						if(mPlaybackListener!=null)
						{
							mPlaybackListener.playbackCompleted( currentSong);
						}
					}
					position++;
				}

			}

			
			
		}, 0, 1000);
	
	}	
	/**
	 * Duration in seconds
	 * @return
	 */
	private int getDuration() {
		// TODO Auto-generated method stub
		return 180	;
	}
	@Override
	public URI rawFind(URI mediaSource) {
		// TODO Auto-generated method stub
		try{
			String query ="";
			for(String param : mediaSource.getParameters())
			{
				query+="%22"+param+"%22 ";
			}
			String strURI = "http://ws.spotify.com/search/1/track?q="+query;
			Document c = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new URL(strURI).openStream());
			try
			{
				Element d = (Element)c.getElementsByTagName("track").item(0);
				return new URI(d.getAttribute("href"));
				
			}
			catch(Exception e)
			{
				
			}
		}catch(Exception e)
		{
			
			
		}
		return null;
		//return new URI("spotify:track:0jw4ORgzwqbxridCnEScxa");
	}

	@Override
	public Image getIcon() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
		logIn();
	}

	@Override
	public boolean isLoggedIn() {
		// TODO Auto-generated method stub
		return false;
	}
	Process mSPShell;
	@Override
	public boolean logIn() {
		return true;
		
	}

	@Override
	public boolean logOut() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void unload() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getNamespace() {
		// TODO Auto-generated method stub
		return "spotify";
	}

	@Override
	public Object invoke(int action, Object... args) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void seek(int pos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getCurrentPosition() {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public URI getCurrentTrack() {
		// TODO Auto-generated method stub
		return currentSong;
	}

	@Override
	public URI getArtist(URI source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI getPlaylist(URI source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI getAlbum(URI source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IMCPlaybackEventListener getPlaybackListener() {
		// TODO Auto-generated method stub
		return mPlaybackListener;
	}
	public void pause(){
		playing = !playing;
	}
	@SuppressWarnings("unused")
	@Override
	public void play(final URI resource) {
		// TODO Auto-generated method stub
		// TODO Fix this to real, this is a dummy to see the code is working
		URL url;
		try {
			
			String sp_uri = URLEncoder.encode(resource.toLinkString(),"ISO-8859-1");
			url = new URL(("http://127.0.0.1:4380/remote/play.json?pause=false&cors=&csrf=bf3334319b27abb77085a5602da59798&oauth=M2ZhY2Vib29rIGRyc291bmRzIDEwMzA3OTczOTM5MiAxMzIwNjc4MjA4IDEzMjA5Mzc0MDggAHPkfiqBQQ4XyGz8gShX7LKjzLu5PhwD6MFPxN7icwJZnKecZvd788UZp5zL2mV8nTdgg2bwY9asnyBO-RZJdwy-N_8MxICrboMEClOhUJlXw3NPtkB_DJfSt5S4O5nu4gBChaeTs4uoLnqDLJ63MDnwebEoG1p6xZQGanEtG8iEcTVce97Kg94&button_id=uoqj9o_49&song="+sp_uri+"&appear_source=999&uri=" +sp_uri));
	//		JOptionPane.showMessageDialog(null, url.toString());
			url.openStream();
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();	
			
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	

		Runtime run = Runtime.getRuntime();
		Process pr = null;
		
		try {
			pr = run.exec("C:\\Users\\Alexander\\AppData\\Roaming\\Spotify\\Spotify.exe /uri " + resource.toFullPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		// Reset player handler
		position = 0;
		playing = true;
	
		
	
			
		
	}
	private boolean playing = false;

	private Timer c;
	private IMCPlaybackEventListener mPlaybackListener;
	@Override
	public void setPlaybackListener(IMCPlaybackEventListener listener) {
		// TODO Auto-generated method stub
		this.mPlaybackListener = listener;
	}

}
