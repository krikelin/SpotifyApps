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

import java.awt.Image;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import Spotify.Track;

import com.google.code.jspot.Spotify;


public class SimpleEntry implements ISPEntry {
	private URI mAuthorUri,mCollectionUri,mPlaylistUri,mUri;
	private Activity mActivity;
	private ArrayList<ISPEntry> mAssets = new ArrayList<ISPEntry>();
	private SPContentView mContentView;
	private Duration mDuration;
	private Float mPopularity;
	private Image icon;
	private String mReleaseType;
	private int mTrackNumber;
	public void setDuration(int length)
	{
		this.mDuration=new Duration(length);
	}
	@Override
	public String toString()
	{
		return mUri.toString();
	}
	public SimpleEntry(Image icon, Activity mActivity,SPContentView mContentView,URI mUri,URI mAuthorUri,URI mCollectionUri,URI mPlaylistUri)
	{
		this.icon = icon;
		this.mContentView=mContentView;
		this.mActivity = mActivity;
		this.mAuthorUri=mAuthorUri;
		this.mCollectionUri=mCollectionUri;
		this.mUri = mUri;
		
		
	}
	/**
	 * Identify spotify link
	 * @param link
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public String identifySpotifyLink(String link) throws MalformedURLException, IOException{
		
		try{
			String startTag = "href=\""; // Start tag
		
			int startPos = link.indexOf("http://"); // Get the starting position of the open.spotify.com
			int endPos = link.indexOf(" ",startPos); // Find the end of the link string
			if(endPos == -1){
				endPos = link.length();
			}
			String spotify_link = link.substring(startPos, endPos );
			if(spotify_link.startsWith("http://t.co") || spotify_link.startsWith("http://bit.ly") || spotify_link.startsWith("http://spot.tm")){
				URLConnection conn = new URL(spotify_link).openConnection();
				conn.setRequestProperty("User-Agent", "curl/7.22.0 (amd64-pc-win32) libcurl/7.22.0 OpenSSL/0.9.8r zlib/1.2.5");
            
	            conn.connect();
	            
				for(int i=0; ; i++){
					
					String headerName = conn.getHeaderFieldKey(i);
			 
		        	spotify_link = conn.getURL().toString();
		        	break;
				       
			       
				}
			
			}
			return spotify_link;
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * Creates an ISPEntry from the tweet
	 * @param mActivity
	 * @param mContentView
	 * @param tweet
	 * @throws IOException 
	 */
	public SimpleEntry(Activity mActivity,SPContentView mContentView, String tweet) throws IOException{
		
		// Lookup the song
		String link = identifySpotifyLink(tweet);
		if(link == null){
			throw new IOException();
		}
		Spotify spot = new Spotify();
		try{
			com.google.code.jspot.Track track = spot.lookupTrack(link);
			this.mUri = (new URI(track.getName(),link));
			this.mAuthorUri = (new URI(track.getArtistName(),track.getArtistId()));
			this.mTrackNumber = (track.getTrackNumber());
			this.mCollectionUri = new URI(track.getAlbum().getName(),track.getAlbum().getId());
		}catch(Exception e){
			
		}
		
	}
	public SimpleEntry(Activity mActivity,SPContentView mContentView,URI mUri,URI mAuthorUri,URI mCollectionUri,URI mPlaylistUri)
	{
		this.mContentView=mContentView;
		this.mActivity = mActivity;
		this.mAuthorUri=mAuthorUri;
		this.mCollectionUri=mCollectionUri;
		this.mUri = mUri;
		
		
	}
	@Override
	public void activate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public URI getAuthorUri() {
		// TODO Auto-generated method stub
		return mAuthorUri;
	}

	@Override
	public URI getCollectionUri() {
		// TODO Auto-generated method stub
		return mCollectionUri;
	}
	@Override
	public void setHost(SPContentView mHost)
	{
		this.mContentView=mHost;
	}
	@Override
	public SPContentView getHost() {
		// TODO Auto-generated method stub
		return mContentView;
	}

	@Override
	public URI getPlaylistUri() {
		// TODO Auto-generated method stub
		return mPlaylistUri;
	}

	@Override
	public URI getUri() {
		// TODO Auto-generated method stub
		return mUri;
	}

	@Override
	public boolean isSelected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAuthorUri(URI mAuthorUri) {
		// TODO Auto-generated method stub
		this.mAuthorUri=mAuthorUri;
	}

	@Override
	public void setSelected(boolean selected) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUri(URI uri) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void play() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Activity getActivity() {
		// TODO Auto-generated method stub
		return mActivity;
	}
	@Override
	public SPContentView getPlaylist() {
		// TODO Auto-generated method stub
		return mContentView;
	}
	@Override
	public ArrayList<ISPEntry> getAssets() {
		// TODO Auto-generated method stub
		return mAssets;
	}
	@Override
	public float getPopularity() {
		// TODO Auto-generated method stub
		return mPopularity;
	}
	public void setPopularity(Float mPopularity){
		this.mPopularity = mPopularity;
	}
	@Override
	public Duration getDuration() {
		// TODO Auto-generated method stub
		return this.mDuration;
	}
	@Override
	public void setReleaseType(String mReleaseType) {
		this.mReleaseType = mReleaseType;
	}
	@Override
	public String getReleaseType() {
		return mReleaseType;
	}
	public void setTrackNumber(int mTrackNumber) {
		this.mTrackNumber = mTrackNumber;
	}
	public int getTrackNumber() {
		return mTrackNumber;
	}
	public Image getIcon() {
		// TODO Auto-generated method stub
		return icon;
	}
	private Image cover;
	@Override
	public Image getCover() {
		// TODO Auto-generated method stub
		return cover;
	}
	@Override
	public void setCover(Image cover) {
		// TODO Auto-generated method stub
		this.cover = cover;
	}

}
