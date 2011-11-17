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

public class URI {
	private String mAdress = "spotify";
	private String mDomain = "open.spotify.com";
	private String mApplication = "";
	private String[] mParameters = {""};
	private String mTitle;
	
	private ArrayList<URI> mAssets = new ArrayList<URI>();
	public String getDomain()
	{
		return mDomain;
	}
	public void setDomain(String value)
	{
		mDomain=value; 
	}
	@Override
	public boolean equals(Object target)
	{
		// If the target is not instance of an uri return
		if(!(target instanceof URI))
			return false;
		
		URI c = (URI)target;
		boolean result =  this.toLinkString().equals(c.toLinkString());
			return result;
		
		
	}
	public String toLinkString()
	{
		return mAdress + ":" + mApplication + ":" + getParameter();
	}
	/**
	 * Returns the uri string
	 */
	@Override
	public String toString()
	{
	//	
		return getTitle();
	}
	public String toURI()
	{
		return mApplication+":"+mDomain+":"+getParameters();
	}
	public String getApplication()
	{
		return mApplication;
	}
	public String[] getParameters()
	{
		return mParameters;
	}
	public String getParameter(int pos)
	{
		return mParameters[pos];
	}
	public String getParameter()
	{
		String c = "";
		int i=0;
		for(String param : mParameters)
		{
			if(param!=(null))
			{
				c+=( i != 0 ?":" : "")+param;
				
				i++;
			}
		}
		
		return mParameters.length > 0 ? c : "";
	}
	
	public String getAdress()
	{
		return mAdress;
	}
	public void setAdress(String value)
	{
		mAdress=value;
	}
	public void setParameters(String value)
	{	
		if(value == null)
			return;

		if(value.contains(":"))
			mParameters=value.split(":");
		else
		{
			mParameters = new String[]{value};
		}
	}
	public void setApplication(String value)
	{
		mApplication=value;
	}
	
	/**
	 * Creates an new Spotify uri
	 * @param uri the uri 
	 */
	public URI(String uri)
	{
		boolean flax=false;
		// Get new domain if //
		if(uri.contains("://")){
			mApplication = uri.split("://")[0];
			mDomain = uri.split("://")[1].split("/")[0];
			flax=true;
		}
		uri = uri.replace("//",":");
		uri  = uri.replace("/",":");
		uri = uri.replace("::",":");
		String[] c = uri.split(":");
		if(!flax)
		{
			this.mAdress=c[0];
			this.mApplication=c[1];
			
		}
		this.mParameters=new String[c.length-2];
		try{
		for(int i=2; i <  c.length ;i++)
		{
			mParameters[i-2]=c[i];
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	/**
	 * Creates an new Spotify uri
	 * @param uri the uri 
	 * @param title the title of the resource
	 */
	public URI(String title, String uri)
	{
		try{
			this.setTitle(title);
			uri = uri.replace("http://","");
			uri = uri.replace("/", ":");
			uri = uri.replace("open.spotify.com", "spotify");
			String[] c = uri.split(":");
			this.mAdress=c[0];
			this.mApplication=c[1];
			this.mParameters=new String[uri.length()-2];
			for(int i=2; i <  c.length ;i++)
			{
				mParameters[i]=c[i];
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public String toFullPath() 
	{
		String result = mDomain.startsWith("http://") ? mDomain : "http://"+mDomain+"/";
		result+=mApplication;
		result+="/"+getParameter().replace(":","/");
		return result; 
	}
	/**
	 * Parses the uri
	 * @param uri
	 */
	public void parse(String uri)
	{
		String[] c = uri.split(":");
		this.mAdress=c[0];
		this.mApplication=c[1];
		this.mParameters=new String[uri.length()-2];
		for(int i=0; i <  c.length ;i++)
		{
			mParameters[i]=c[i+2];
		}
		
	}
	public void setTitle(String mTitle) {
		this.mTitle = mTitle;
	}
	public String getTitle() {
		return mTitle;
	}
	public void setAssets(ArrayList<URI> mAssets) {
		this.mAssets = mAssets;
	}
	public ArrayList<URI> getAssets() {
		return mAssets;
	}
	
}
