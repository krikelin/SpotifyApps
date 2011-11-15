package com.krikelin.spotifysource;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;


public class SPLabel extends BufferedCanvas implements com.krikelin.spotifysource.SPPart {
	private String mText = "";
	private SpotifyWindow mContext;
	private Color mColor;
	private boolean mShadow = true;
	private boolean mIsLink = false;
	private Font mFont;
	public boolean hasShadow()
	{
		return getIsShadow();
	}
	public void setShadow(boolean value)
	{
		mShadow = value;
	}
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		super.draw(g);
		try{
			setFont(getFont());
			if(getBackground()!=null)
				g.setColor(getBackground());
			
				g.fillRect(0,0,getWidth(),getHeight());
			g.setColor(mColor);
			getContext().getSkin().drawText(getText(), mColor, g, 2, 12,getIsShadow());
		}catch(Exception e){
			
		}
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -4476039650778753787L;
	public SPLabel(SpotifyWindow context,String title)
	{
		super(context);
		mContext=context;
		
		setForeground(context.getSkin().getForeColor());
		mColor=context.getSkin().getForeColor();
		setFont(context.getSkin().getFont());
		
	}
	@Override
	public SpotifyWindow getContext() {
		// TODO Auto-generated method stub
		return mContext;
	}
	public void setText(String mText) {
		this.mText = mText;
	}
	public String getText() {
		return mText;
	}
	public void setColor(Color mColor) {
		this.mColor = mColor;
	}
	public Color getColor() {
		return mColor;
	}
	public void setFont(Font mFont) {
		this.mFont = mFont;
	}
	public Font getFont() {
		return mFont;
	}
	/**
	 * @return the mShadow
	 */
	public boolean getIsShadow() {
		return mShadow;
	}
	/**
	 * @param mIsLink the mIsLink to set
	 */
	public void setIsLink(boolean mIsLink) {
		this.mIsLink = mIsLink;
		if(mIsLink)
		{
			setCursor(new Cursor(Cursor.HAND_CURSOR));
			
		}
		else
		{
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			
		}
	}
	/**
	 * @return the mIsLink
	 */
	public boolean isIsLink() {
		return mIsLink;
	}
	
}
