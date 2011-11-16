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
package com.krikelin.spotifysource;
import java.util.ArrayList; 
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
	public Activity getActivity();
	public SPContentView getPlaylist();
	public Duration getDuration();
	public String getReleaseType();
	public void setReleaseType(String type);
}
