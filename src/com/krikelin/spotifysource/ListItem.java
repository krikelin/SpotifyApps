package com.krikelin.spotifysource;

import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Image;


public class ListItem
{
	private SPListView mHost;
	public SPListView getParent()
	{
		return mHost;
	}
	public ListItem(SPListView parent)
	{
		mHost=parent;
	}
	private String mKey;
	private Image mIcon;
	public void drawItem(Graphics g,int left,int top,int width,int height,int itemHeight)
	{
		// FIll the background if selected
		if(isSelected())
		{
			g.setColor(getParent().getContext().getSkin().getSelectionBg());
			g.fillRect(0, top,width,itemHeight);
			
		}
		if(getIcon()!= null)
		{
			g.drawImage(getIcon(),0, top,itemHeight,itemHeight,null);
			g.setColor(isSelected() ? getParent().getContext().getSkin().getSelectionFg() : getParent().getContext().getSkin().getForeColor()  );
			g.drawString(getTitle(),itemHeight+10,top+itemHeight/3);
			
		}
	}
	private String mTitle;
	private boolean mSelected;
	public void setTitle(String mTitle) {
		this.mTitle = mTitle;
	}

	public String getTitle() {
		return mTitle;
	}
	

	public ArrayList<ListItem> getSubItems() {
		return mSubItems;
	}
	public void setKey(String mKey) {
		this.mKey = mKey;
	}

	public String getKey() {
		return mKey;
	}
	public void setSelected(boolean mSelected) {
		this.mSelected = mSelected;
	}

	public boolean isSelected() {
		return mSelected;
	}
	public void setIcon(Image mIcon) {
		this.mIcon = mIcon;
	}

	public Image getIcon() {
		return mIcon;
	}
	private ArrayList<ListItem> mSubItems = new ArrayList<ListItem>();
	
	
}
