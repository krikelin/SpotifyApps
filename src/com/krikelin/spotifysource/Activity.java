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
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;

import javax.swing.SwingUtilities;

/***
 * An Activity is an activity running inside Spotify Source
 * 
 * @author Alexander
 *
 */
public abstract class Activity extends Container implements Context, com.krikelin.spotifysource.SPPart {
	public void navigate(){
		
	}
	protected SpotifyWindow mContext;
	private SPTabBar mTabBar;
	public String getTitle(){
		return getClass().getName();
	}
	public SPTabBar getTabBar()
	{
		return mTabBar;
	}
	/**
	 * Denotates if the view can be filtered
	 * @return
	 */
	public boolean canFilter(){
		return false;
	}
	/**
	 * Filters the view
	 * @param query
	 */
	public void filterView(String query){
		
	}
	public Image getIcon(){
		return null;
	}
	/***
	 * Checks if the drag over is allowed to the certain Activity
	 * @param args
	 * @return
	 */
	public boolean dragOver(Object... args)
	{
		return false;
	}
	
	/**
	 * Occurs on dragdrop operation
	 * @param args the uri that are being dropped into the view
	 */
	public void dropItems(Object... args)
	{
		
	}

	/***
	 * drag out the content from the window
	 * @param args
	 * @return
	 */
	public String dragOut(URI... args)
	{
		return mUri.toFullPath();
	}
	
	/**
	 * Top position of the current element view  
	 */
	

	/**
	 * Loads the view into the memory. Occurs on separate thread
	 * @param args
	 * @return Object an object with the data whose loaded
	 */
	public abstract Object onLoad(URI args);
	
	/**
	 * Renders the elements. Runs on the main thread after onLoad
	 * @param args the uri of the view
	 * @param result The result from the loading time
	 */
	public  void render( URI args,Object... result){
		this.mTabBar.remove("Loading");
	}
	
	// View stack of viwes
	private Container mViewStack;
	
	/**
	 * Gets the viewstack for the view
	 * @return
	 */
	public Container getView()
	{
		return mViewStack;
	}
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2263646627754317279L;
	CardLayout cl ;
	public void removePage(String name,Container view){
		mTabBar.remove(name);
		mViewStack.remove(mViewStack.getComponentZOrder(view));
	}
	/**
	 * Adds an page to the view
	 * @param name
	 * @param view
	 */
	public void addPage(String name,Container view)
	{
		mTabBar.add(name);
		mViewStack.add(name,view);
	}
	private SPContentView mContentView;
	public SPContentView getContentView()
	{
		return mContentView;
	}
	Object c ;
	public class Initializer implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			c =	Activity.this.onLoad(mUri);
			// Remove loading
			mViewStack.setLayout(cl);
			SwingUtilities.invokeLater(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					mTabBar.remove("Loading");
					mViewStack.remove(0);
					
					Activity.this.render(mUri,c);
					
					mContext.navigate(mUri);
				}
				
			});
			
			validate();
		}

		@Override
		protected void finalize() throws Throwable {
			// TODO Auto-generated method stub
			super.finalize();
		}
		
	}
	private URI mUri;
	public SPContentView getViewHost()
	{
		return mContentView;
	}
	/**
	 * Decides wheter you can drop an component in this view
	 * @param source
	 * @return
	 */
	public boolean canDropElement(URI source){
		return false;
	}
	/**
	 * Action to perform on drop
	 * @param source
	 */
	public void onDropElement(URI source){
		
	}
	/**
	 * Used for preprocessor directive
	 * @param section the section the preprocessor should load
	 * @return
	 */
	public Object[] createView(int section){
		return null;
	}
	/**
	 * Occurs on long drop
	 * @param source
	 */
	public void onLongDrop(URI source){
		
	}
	public class Loading extends SPContentView{
		/**
		 * 
		 */
		private static final long serialVersionUID = 4461240628766759646L;
		SPLabel loadLabel;
		@Override
		public void paint(Graphics g) {
			// TODO Auto-generated method stub
			super.paint(g);
		}
		public Loading() {
			super(Activity.this, Activity.this.getContext());
			loadLabel = new SPLabel(getContext(),"Loading");
			add(loadLabel);
		}
	}
	/**
	 * 
	 * @param locator the uri that called the view
	 */
	
	public void onCreate(  URI locator,SpotifyWindow context)
	{
		mContext=context;
		mTabBar = new SPTabBar(getContext(),this);
		
		setLayout(new BorderLayout());
		
		
		setBackground(getContext().getSkin().getBackgroundColor());
		setForeground(getContext().getSkin().getForeColor());
		setUri(locator);
		mViewStack = new Container();
		addPage("Loading", new Loading());
		cl = new CardLayout();
		mViewStack.setLayout(new FlowLayout());
		add(mViewStack,BorderLayout.CENTER); 
		mTabBar.setPreferredSize(new Dimension(0,24));
		add(mTabBar,BorderLayout.NORTH);
		
		mTabBar.addTabChangeHandler(new OnTabChangeListener(){
 
			@Override
			public void onTabChange(int index, String title) {
				// TODO Auto-generated method stub
				Activity.this.cl.show(mViewStack, title);
				
			}
			
		});
		
		doLayout();
		// Iniate the contents
		try {
			if(!SwingUtilities.isEventDispatchThread()){
				SwingUtilities.invokeAndWait(new Initializer());
			}else
			{
				SwingUtilities.invokeLater(new Initializer());
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			
		}
		
	}

	@Override
	public SpotifyWindow getContext() {
		// TODO Auto-generated method stub
		return mContext;
	}

	private void setUri(URI mUri) {
		this.mUri = mUri;
	}

	public URI getUri() {
		return mUri;
	}
	
	/**
	 * Not used yet
	 */
	
	@Override
	public HashMap<String, String> getResDomains() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration<URL> getLocalResources(String type) {
		// TODO Auto-generated method stub
		return null;
	}
}
