package com.krikelin.spotifysource;

import java.awt.Graphics;

public class SPDivider extends BufferedCanvas {
	private SpotifyWindow mContext;
	private String mText = "";
	/**
	 * 
	 */
	private static final long serialVersionUID = -7327077209196379514L;

	public SPDivider(SpotifyWindow mContext) {
		
		super(mContext);
		this.mContext=mContext;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void draw(Graphics g)
	{
		g.drawImage(getContext().getSkin().getImageById(DefaultSkin.IMG_VERTICAL_DIVIDER),0,0,getWidth(),getHeight(), null);
		g.setFont(getContext().getSkin().getFont());
		g.setColor(getContext().getSkin().getColorById(DefaultSkin.IMG_VERTICAL_DIVIDER_FG));
		g.drawString(getText(),2,12);
	}
	public SpotifyWindow getContext()
	{
		return mContext;
	}
	public void setText(String mText) {
		this.mText = mText;
	}
	public String getText() {
		return mText;
	}
}
