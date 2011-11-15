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

import java.awt.BorderLayout;
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
	private JList<?> mList;
	
	public MainContent(SpotifyWindow context)
	{
		super(context);
		this.mContext=context;
		mList = new JList<Object>();
		mContainer = new SPContainer(getContext());
		add(mContainer,BorderLayout.CENTER);
		add(mList,BorderLayout.WEST);
		
		
		mList.setBackground(getContext().getSkin().getAlternateBgColor());
		
		
		
		
		
		
		
	}
	
	public void setList(JList<?> mList) {
		this.mList = mList;
	}

	public JList<?> getList() {
		return mList;
	}

}
