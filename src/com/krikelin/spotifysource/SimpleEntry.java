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


public class SimpleEntry implements ISPEntry {
	private URI mAuthorUri,mCollectionUri,mPlaylistUri,mUri;
	private SPActivity mActivity;
	private ArrayList<ISPEntry> mAssets = new ArrayList<ISPEntry>();
	private SPContentView mContentView;
	private Duration mDuration;
	private Float mPopularity;
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
	
	public SimpleEntry(SPActivity mActivity,SPContentView mContentView,URI mUri,URI mAuthorUri,URI mCollectionUri,URI mPlaylistUri)
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
	public SPActivity getActivity() {
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

}
