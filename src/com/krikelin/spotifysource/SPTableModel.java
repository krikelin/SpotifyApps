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

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class SPTableModel implements TableModel {
	private ArrayList<ISPEntry> mResources;
	public ArrayList<ISPEntry> getResources()
	{
		return mResources;
	}
	public SPTableModel(ArrayList<ISPEntry> mResources)
	{
		this.mResources = mResources;
		
	}
	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		// TODO Auto-generated method stub
		return URI.class;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public String getColumnName(int columnIndex) {
		// TODO Auto-generated method stub
		switch(columnIndex)
		{
			case 0:
				return "Title";
			case 1:
				return "Artist";
			case 2:
				return "Length";
			case 3:
				return "Album";
			case 4:
				return "User";
	
		}
		return "";
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return mResources.size();
	}
	public Object getData(int rowIndex)
	{
		return mResources.get(rowIndex);
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		
		try{
			ISPEntry d = mResources.get(rowIndex);
			switch(columnIndex)
			{
			case 0:
				return d.getUri();
			case 1:
				return d.getAuthorUri();
			case 2:
				return ""; //d.getDuration();
			case 3:
				return d.getCollectionUri();
			case 4:
				return d.getPopularity();
			case 5:
				return d.getCollectionUri();
			case 6:
				return d.getPlaylistUri();
			default:
				return new URI("spotify:underfined");
			}
		}
		catch(Exception e){
			return new URI("spotify:underfined");
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		
	}

}
