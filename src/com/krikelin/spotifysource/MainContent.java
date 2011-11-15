package com.krikelin.spotifysource;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.List;

import javax.swing.JList;

public class MainContent extends BufferedContainer implements SPPart {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7266573613238406101L;
	private SpotifyWindow mContext;
	private SPContainer mContainer;
	@Override
	public SpotifyWindow getContext() {
		// TODO Auto-generated method stub
		return mContext;
	}
	private JList mList;
	
	public MainContent(SpotifyWindow context)
	{
		super(context);
		this.mContext=context;
		mList = new JList();
		mContainer = new SPContainer(getContext());
		add(mContainer,BorderLayout.CENTER);
		add(mList,BorderLayout.WEST);
		
		
		mList.setBackground(getContext().getSkin().getAlternateBgColor());
		
		
		
		
		
		
		
	}
	
	public void setList(JList mList) {
		this.mList = mList;
	}

	public JList getList() {
		return mList;
	}

}
