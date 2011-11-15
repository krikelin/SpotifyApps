package com.krikelin.mediasource;

import java.awt.Image;

import com.krikelin.spotifysource.IMCPlaybackEventListener;
import com.krikelin.spotifysource.URI;

/***
 * Interface for dealing with various kinds of media
 * @author Alexander
 *
 */
public interface  IMCSource {
	public IMCPlaybackEventListener getPlaybackListener();
	public void setPlaybackListener(IMCPlaybackEventListener event);
	/**
	 * Returns the title of the engine
	 * @return
	 */
	public String getTitle();
	/**
	 * Finds the media on the source, but only
	 * @param mediaSource
	 * @remarks This function is invoked on secondary thread
	 * @return
	 */
	public URI rawFind(URI mediaSource);
	/**
	 * Returns the icon of the media source
	 * @return Image the icon of the engine
	 */ 
	public Image getIcon();
	/**
	 * Occurs on initialization of the engine
	 */
	public void init();
	/**
	 * Returns if the user is logged in to the service provider
	 * @return
	 */
	public boolean isLoggedIn();
	
	/**
	 * Logs the user in. 
	 * @remark The login process is handled by the engine iself the method is only raising the login dialog.
	 * @return
	 */
	public boolean logIn();
	
	/**
	 * Logs the user out from the service
	 * @return
	 */
	public boolean logOut();
	
	/**
	 * Occurs on unload of the engine
	 */
	public void unload();
	/**
	 * Returns the namespace of the engine, used as the first
	 * string in the uri
	 * @return
	 */
	public String getNamespace();
	/**
	 * Invokes an custom action in the IPlayEngine, with
	 * the int as the definer for the action.
	 * @param action action to perform
	 * @param args arguments to the action
	 * @return
	 */
	public Object invoke(int action,Object... args);
	/**
	 * Starts playback of the specified resource
	 * @param resource
	 */
	public void play(URI resource);
	/**
	 * Stops playback of the current playing resource
	 */
	public void stop();
	/**
	 * Seeks through the song
	 * @param pos position of the song
	 */
	public void seek(int pos);
	/**
	 * Gets the current position of the song
	 * @return
	 */
	public int getCurrentPosition();
	/**
	 * Gets the current track
	 * @return
	 */
	public URI getCurrentTrack();
	
	public URI getArtist(URI source);
	public URI getPlaylist(URI source);
	public URI getAlbum(URI source);
}
