package com.krikelin.mediasource;

import java.awt.Image;
import java.net.URL;

import com.krikelin.spotifysource.IMCPlaybackEventListener;
import com.krikelin.spotifysource.URI;

public class MP3 implements IMCSource  {
	
	/**
	 * MP3 class
	 * @see {@link http://www.devx.com/tips/Tip/38856}
	 * @author Alexander
	 *
	 
	class mp3 extends Thread
	{
	 
		private URL url;
		private MediaLocator mediaLocator;
		private Player playMP3;
		 
		public mp3(String mp3)
		{
		try{
		   this.url = new URL(mp3);
		   }catch(java.net.MalformedURLException e)
		      {System.out.println(e.getMessage());}
		}
		 
		public void run()
		{
	
		try{
		   mediaLocator = new MediaLocator(url);     
		   playMP3 = Manager.createPlayer(mediaLocator);
		    }catch(java.io.IOException e)
		      {System.out.println(e.getMessage());
		    }catch(javax.media.NoPlayerException e)
		      {System.out.println(e.getMessage());}
	
		playMP3.addControllerListener(new ControllerListener()
		  {
		  public void controllerUpdate(ControllerEvent e)
		     {
		     if (e instanceof EndOfMediaEvent)
		         {
		         playMP3.stop();
		         playMP3.close();
		         }
		     }
		  }
		 );
		 playMP3.realize();
		 playMP3.start();
		 } 
	}*/
	
	@Override
	public IMCPlaybackEventListener getPlaybackListener() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPlaybackListener(IMCPlaybackEventListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI rawFind(URI mediaSource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLoggedIn() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean logIn() {
		// TODO Auto-generated method stub
		return false;
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
		return null;
	}

	@Override
	public Object invoke(int action, Object... args) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void play(URI resource) {
		// TODO Auto-generated method stub
		String url = resource.getParameter();
		
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

}
