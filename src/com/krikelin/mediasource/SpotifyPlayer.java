package com.krikelin.mediasource;

import java.awt.Image;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.krikelin.spotifysource.IMCPlaybackEventListener;

import com.krikelin.spotifysource.URI;

import Spotify.Session;
import Spotify.Track;

public class SpotifyPlayer implements IMCSource {
	public static final int MOVE_SONG_PLAYLIST = 12515;
	
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
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
	private Session mSpotifySession;
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
	private String mUserName = "drsounds";
	private String mPassWord = "";
	

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
		return 0;
	}

	@Override
	public URI getCurrentTrack() {
		// TODO Auto-generated method stub
		return null;
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
		} catch (MalformedURLException | UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		if(false){
			if(c == null)
			{
				Runtime run = Runtime.getRuntime();
				Process pr = null;
				
					try {
						pr = run.exec("C:\\Documents and Settings\\Alex\\Application Data\\Spotify\\Spotify.exe /uri " + resource.toFullPath());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				if(false){
					c = new Timer();
					c.schedule(new TimerTask(){
			
						@Override
						public void run() {
							// TODO Auto-generated method stub
							if(mPlaybackListener!=null)
							{
								mPlaybackListener.playbackCompleted(resource);
							}
							c = null;
						}
						
					}, 3000);
				}
			}
		}
	}
	private Timer c;
	private IMCPlaybackEventListener mPlaybackListener;
	@Override
	public void setPlaybackListener(IMCPlaybackEventListener listener) {
		// TODO Auto-generated method stub
		this.mPlaybackListener = listener;
	}

}
