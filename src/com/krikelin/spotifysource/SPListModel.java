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

import javax.swing.AbstractListModel;

@SuppressWarnings("rawtypes")
public class SPListModel extends AbstractListModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7964088007727782913L;
	private ArrayList<ISPEntry> mListModel;
	public SPListModel(ArrayList<ISPEntry> mListModel)
	{
		this.mListModel = mListModel;
	}
	@Override
	public Object getElementAt(int arg0) {
		// TODO Auto-generated method stub
		return mListModel.get(arg0);
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return mListModel.size();
	}
	
}