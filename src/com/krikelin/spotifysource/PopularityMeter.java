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

import java.awt.Graphics;

public class PopularityMeter extends BufferedCanvas {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7038679847438653049L;
	private SpotifyWindow mContext;
	public PopularityMeter(SpotifyWindow mContext) {
		super(mContext);
		this.mContext = mContext;
		
		
		// TODO Auto-generated constructor stub
	}
	private float mPopularity = 0.5f;
	@Override
	public void draw(Graphics g)
	{
		getContext().getSkin().drawPopularity(mPopularity,getWidth()/3,g,0,getHeight()/4,getHeight()/2,0);
		
	}
	
	public SpotifyWindow getContext()
	{
		return mContext;
	}

	public void setPopularity(float mPopularity) {
		this.mPopularity = mPopularity;
	}

	public float getPopularity() {
		return mPopularity;
	}

}
