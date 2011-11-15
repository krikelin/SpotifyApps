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

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;


@SuppressWarnings("rawtypes")
public class MenuListModel implements ListModel
{
	private ArrayList<URI> mResources = new ArrayList<URI>();
	public ArrayList<URI> getResources()
	{
		return mResources;
	}
	public MenuListModel(ArrayList<URI> res)
	{
		mResources=res;
	}
	@Override
	public void addListDataListener(ListDataListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getElementAt(int arg0) {
		// TODO Auto-generated method stub
		return mResources.get(arg0);
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return mResources.size();
	}

	@Override
	public void removeListDataListener(ListDataListener arg0) {
		// TODO Auto-generated method stub
		
	}
	
}