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
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.krikelin.spotifysource.widgets.StreamContainer;

public class SPContentView extends BufferedScrollPane implements SPWidget, SPPart, StreamContainer {


	public int getSelectedIndex()
	{
		int i=0;
		// iterate through all items
		for(ISPEntry entry : getEntries())
		{
			if(entry.isSelected())
			{
				return i;
			}
			i++;
		}
		return -1;
		
	}
	public void setSelectedIndex(int mSelectedIndex)
	{
		// iterate through all items
		for(ISPEntry entries : getEntries())
		{
			entries.setSelected(false);
		}
		getEntries().get(mSelectedIndex).setSelected(true);
		
	}
	public void selectPrevious()
	{
		setSelectedIndex(getSelectedIndex()-1);
	}
	public void selectNext()
	{
		setSelectedIndex(getSelectedIndex()+1);
	}
	private ISPViewFilter mViewFilter;
	/**
	 * gets the view filter
	 * @return
	 */
	public ISPViewFilter getViewFilter()
	{
		return mViewFilter;
	}
	/**
	 * sets the view filter
	 * @param filter
	 */
	public void setFilter(ISPViewFilter filter)
	{
		this.mViewFilter = filter;
	}
	/**
	 * Occurs when filtering views
	 */
	public void filter(String title)
	{
		if(mViewFilter != null)
		{
			// TODO Add view filter
		}
		
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5311929409284734353L;

	@Override
	public SpotifyWindow getContext() {
		// TODO Auto-generated method stub
		return mContext;
	}
	
	private SpotifyWindow mContext;
	private Activity mActivity;
	public SPContentView(Activity activity,SpotifyWindow mContext)
	{
		super(mContext,new JPanel());
		  
		mActivity=activity;
		this.mContext=mContext;
		setBorder(BorderFactory.createEmptyBorder());
		// Set absolute layout
		setFocusable(true);
		super.addMouseListener(new MouseListener(){
 
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				for(ISPEntry entry : getEntries())
				{
					entry.setSelected(false);
					if(entry instanceof JComponent)
					{
						((JComponent)entry).repaint();
					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		mPanel.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				switch(arg0.getKeyCode())
				{
				case KeyEvent.VK_RIGHT:
					getContext().playNext();
					break;
				case KeyEvent.VK_LEFT:
					getContext().playPrevious();
					break;
				case KeyEvent.VK_DOWN:				
					selectNext();
					break;
				case KeyEvent.VK_UP:
					selectPrevious();
					break;
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				switch(arg0.getKeyCode())
				{
				
				}
			}
			
		});
		
		
		
	}
	
	/**
	 * Gets an item selection
	 * @return
	 */
	public ArrayList<ISPEntry> getSelectedItems()
	{
		ArrayList<ISPEntry> entries = new ArrayList<ISPEntry>();
		@SuppressWarnings("unused")
		int i=0;
		for(ISPEntry elm : getEntries())
		{
			
			
				if(elm instanceof ISPEntry)
				{
					if(((ISPEntry) elm).isSelected())
						entries.add((ISPEntry)elm);
				}
			
			i++;
			
		}
		return entries;
	}
	/**
	 * Gets a range of entries
	 * @param start
	 * @param count
	 * @return
	 */
	public ArrayList<ISPEntry> getEntriesByRange(int start,int count)
	{
		ArrayList<ISPEntry> entries = new ArrayList<ISPEntry>();
		int i=0;
		for(ISPEntry elm : getEntries())
		{
			if( i >= start && (start-i) <= count)
			{
				if(elm instanceof ISPEntry)
				{
					entries.add((ISPEntry)elm);
				}
			}
			i++;
			
		}
		return entries;
	}
	
	@Override
	public Component add(Component elm)
	{
		
		if(elm instanceof SPWidget)
			mSPElements.add((SPWidget)elm);
		return super.add(elm);
	}
	/**
	 * Returns all entries in the view
	 * @return
	 */
	public ArrayList<ISPEntry> getEntries()
	{
		
		ArrayList<ISPEntry> entries = new ArrayList<ISPEntry>();
		for(SPWidget elm : mSPElements)
		{
			// if the entry is instance of SPELementColletion assign it
			if(elm instanceof SPElementGroup)
			{
				for(SPElement elm2 : ((SPElementGroup)elm).getElements())
				{
					if(elm2 instanceof SPEntry)
						entries.add((SPEntry)elm2);
				}
			}
			if( elm instanceof SPListView)
			{
				SPTableModel model = (SPTableModel)((SPListView)elm).getModel();
				
				for(int i=0; i < model.getRowCount(); i++)
				{
				
					ISPEntry entry = (ISPEntry)model.getData(i);
					entries.add(entry);
					
				}
			}	
			if(elm instanceof SPEntry)
			{
				entries.add((SPEntry)elm);
			}
			
			if(elm instanceof SPListView)
			{
				SPTableModel spm = (SPTableModel)((SPListView)elm).getModel();
				for( int r = 0; r < spm.getRowCount() ; r++)
				{
					entries.add((ISPEntry)spm.getData(r));
				}
			}
		}
		
		return entries;
	}
	public Activity getHost()
	{
		return mActivity;
	}
	public ArrayList<SPWidget> getSPElements() {
		return mSPElements;
	}

	private ArrayList<SPWidget> mSPElements = new ArrayList<SPWidget>();
	@Override
	public ISPEntry playNext() {
		// TODO Auto-generated method stub
		for(int i=0; i < getEntries().size(); i++)
		{
			ISPEntry entry = getEntries().get(i);
			/**
			 * Play the next entry
			 */
			if(entry.equals(getCurrentPlayingEntry()))
			{
				try
				{
					ISPEntry nextEntry = getEntries().get(i+1);
					return (nextEntry);
				}
				catch(Exception ex)
				{
					
				}
			}
		}
		return null;
	}
	@Override
	public ISPEntry playPrevious() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		for(int i=getEntries().size(); i > 0; i--)
		{
			ISPEntry entry = getEntries().get(i);
			/**
			 * Play the next entry
			 */
			if(entry.equals(getCurrentPlayingEntry()))
			{
				try
				{
					ISPEntry nextEntry = getEntries().get(i-1);
					return (nextEntry);
				}
				catch(Exception ex)
				{
					
				}
			}
		}
		return null;
	}
	@Override
	public ISPEntry getCurrentPlayingEntry() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public SPOnClickListener getOnClickListener() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setOnClickListener(SPOnClickListener listener) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLabel(String label) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}
	private int id;
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return id;
	}
	@Override
	public void setID(int id) {
		// TODO Auto-generated method stub
		this.id = id;
	}
	@Override
	public JComponent findViewById(int id) {
		// TODO Auto-generated method stub
		for(Component component : this.getComponents()){
			if(component instanceof SPWidget){
				if(((SPWidget)component).getID() == id){
					return (JComponent)component;
				}
				Component subComponent = ((SPWidget) component).findViewById(id);
				if(subComponent!= null){
					return (JComponent)subComponent;
				}
			}
			
		}
		return null;
	}

}
