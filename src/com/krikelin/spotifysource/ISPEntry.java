package com.krikelin.spotifysource;
import java.awt.Container;
import java.util.ArrayList;

import com.krikelin.spotifysource.view.*;
/**
 * ISPEntry defines entry objects
 * @author Alexander
 *
 */
public interface ISPEntry {
	public int getTrackNumber();
	public void  setHost(SPContentView mHost);
	/**
	 * Returns the popularity of the song, between 0.0 and 1.0
	 * @return
	 */
	public float getPopularity();
	/**
	 * Returns the sub entry list
	 * @return
	 */
	public ArrayList<ISPEntry> getAssets();
	/**
	 * gets the parent content view
	 * @return
	 */
	public SPContentView getHost();
	/**
	 * Activates the entry
	 */
	public void activate();
	/**
	 * Opens the entry
	 */
	public void play(); 
	/**
	 * gets the uri
	 * @return
	 */
	public URI getUri();
	/**
	 * Sets the uri
	 * @param uri
	 */
	public void setUri(URI uri);
	/**
	 * Gets if the entry is selected
	 * @return
	 */
	public boolean isSelected();
	public void setAuthorUri(URI mAuthorUri) ;
	public URI getAuthorUri();
	
	public URI getCollectionUri() ;
	
	public URI getPlaylistUri();
	public void setSelected(boolean selected);
	public SPActivity getActivity();
	public SPContentView getPlaylist();
	public Duration getDuration();
	public String getReleaseType();
	public void setReleaseType(String type);
}
