package com.krikelin.spotifysource;

import java.util.ArrayList;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;


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