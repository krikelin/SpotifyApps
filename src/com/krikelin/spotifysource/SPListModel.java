package com.krikelin.spotifysource;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

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