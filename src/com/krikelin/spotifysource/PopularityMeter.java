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
