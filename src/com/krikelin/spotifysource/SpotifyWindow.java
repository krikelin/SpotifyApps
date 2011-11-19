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

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import com.krikelin.mediasource.*;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JWindow;

/***
 * The base for the Spotify window
 * @author Alexander
 *
 */
public class SpotifyWindow extends JFrame implements Context, WindowListener {
	public String readString(String file) throws IOException{
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		StringBuilder sb = new StringBuilder();
		try{
			String s ="";
			while( ( s = br.readLine() ) != null){
				sb.append(s).append("\n");
			}
		}catch(Exception e){
			
		}
		br.close();
		return sb.toString();
	}
	/**
	 * Add url
	 * @from http://twit88.com/blog/2007/10/04/java-dynamic-loading-of-class-and-jar-file/
	 * @param u
	 * @throws IOException
	 */
	public void addClass(URL u) throws IOException {

		URLClassLoader sysLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		URL urls[] = sysLoader.getURLs();
		ArrayList<Object> newUrls = new ArrayList<Object>();
		for (int i = 0; i < urls.length; i++) {
			/*if (StringUtils.equalsIgnoreCase(urls[i].toString(), u.toString())) {
				if (log.isDebugEnabled()) {
					log.debug("URL " + u + " is already in the CLASSPATH");
				}
				return;
				}
			}*/
			
			newUrls.add(urls[i]);
		}
		newUrls.add(u);
		try {
			Class<?> sysclass = URLClassLoader.class;
			Method method = sysclass.getDeclaredMethod("addURL", URL.class);
			method.setAccessible(true);
			URL[] nurls = new URL[newUrls.size()];
			newUrls.toArray(nurls);
			method.invoke(sysLoader, new Object[]{u});
		} catch (Throwable t) {
			t.printStackTrace();
			throw new IOException("Error, could not add URL to system classloader");
		}
	}

	private Hashtable<String, Class<Activity>> mashups = new Hashtable<String, Class<Activity>>();
	/**
	 * Loads plugins from the desired folder to the arraylist
	 * @param fileName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Hashtable<String, Class<Activity>> getMashups(String fileName){
		ArrayList<String> plugs = map(fileName);
		Hashtable<String, Class<Activity>> plugins = new Hashtable<String, Class<Activity>>();
		
		for(String plugin : plugs){
			try{
				String[] parts = plugin.split("\\.");
				String package_name = "$";
				for(int i=0; i < parts.length -1; i++){
					package_name+="."+parts[i];
				}
				package_name = package_name.replace("$.","");
				String activity_name = parts[plugin.split("\\.").length - 1];
				
				File c = new File(SPContainer.EXTENSION_DIR+"\\jar\\"+package_name+".jar");
				
				URLClassLoader cl = new URLClassLoader(new URL[] { c.toURI().toURL() 	},Thread.currentThread().getContextClassLoader());	
				Thread.currentThread().setContextClassLoader(cl);
		//	addClass(new java.net.URL("file://"+SPContainer.EXTENSION_DIR+"jar/"+package_name+".jar"));
			
				Class sp_activity_type = (Class) cl.loadClass(plugin);
				plugins.put(activity_name, sp_activity_type);
			
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		return (plugins);
	}
	public void loadPlugins() throws IOException, ClassNotFoundException{ 
		String packages = readString(SPContainer.EXTENSION_DIR +"\\packages");
	//	String activities = readString(SPContainer.EXTENSION_DIR+"\\activities\\spotiapp.action.VIEW");
		getPlugins().addAll(map(packages));
		
		setActivities(getMashups(readString(SPContainer.EXTENSION_DIR+"\\activities\\spotiapp.action.VIEW")));
		setMashups(getMashups(readString(SPContainer.EXTENSION_DIR+"\\activities\\spotiapp.action.LIST")));
		
	}
	public ArrayList<String> map(String str){
		ArrayList<String> strings = new ArrayList<String>();
		for(String stri : str.split("\n")){
			strings.add(stri);
		}
		return strings;
	}
	/**
	 * List of installed activities
	 */
	private Hashtable<String, Class<Activity>> activities = new Hashtable<String, Class<Activity>>();
	/***
	 * Arraylist of installed packages, eg.
	 * com.krikelin.spotifysource
	 */
	private ArrayList<String> plugins = new ArrayList<String>();
	public ArrayList<String> getPlugins(){
		return plugins;
	}
	
	public static final String OAUTH_BASE_URL = "http://localhost:64/Project/mediachrome/";
	public static String access_code = ""; // access code for oauth
	public static String error_reason  = "";
	public static String session_id = null;
	public String access_token = null;
	public static String API_BASE_URL = "http://localhost:64/Project/mediachrome/api/1/";
	public String getAPIBase()
	{
		return SpotifyWindow.API_BASE_URL + access_token;
	}
	public static final String OAUTH_CLIENT_ID = "d1d4675b7acac9b0b20c41df2f552cb5";
	public static final String OAUTH_SECRET_ID = "cd4eaff3ef3deaaecac3e3f5e657a031";
	public Hashtable<String,IMCSource> mMediaSources = new Hashtable<String,IMCSource>();
	
	/**
	 * Class for interfering with listresources of the left list
	 * @author Alexander
	 *
	 */
	
	public Hashtable<String, IMCSource> getMediaSources() {
		return mMediaSources;
	}
	/**
	 * Primary media Source
	 */
	private IMCSource mPrimaryMediaSource;
	/**
	 * Gets the primary media source
	 * @return
	 */
	public IMCSource getPrimaryMediaSource()
	{
		return mPrimaryMediaSource;
	}
	private SPSkin mSkin;
	public SPSkin getSkin()
	{
		return mSkin;
	}
	public void setSkin(SPSkin skin)
	{
		mSkin=skin;
	} 
	private URI mCurrentPlayingSong;
	private SPContentView mCurrentPlaylist;
	private Activity mCurrentPlayingActivity;
	public void initiateApps(){
		
	}
	public void playNext()
	{
		ISPEntry e = getCurrentPlayingEntry();
		mPrimaryMediaSource.stop();
		for(int i=0; i < getCurrentPlaylist().getEntries().size(); i++)
		{
			ISPEntry entry = getCurrentPlaylist().getEntries().get(i);
			/**
			 * Play the next entry
			 */
			if(entry.equals(e))
			{
				try
				{
					ISPEntry nextEntry = getCurrentPlaylist().getEntries().get(i+1);
					playSong(nextEntry);
				}
				catch(Exception ex)
				{
					
				}
			}
		}
	}
	public void playPrevious()
	{
		ISPEntry e = getCurrentPlayingEntry();
		mPrimaryMediaSource.stop();
		for(int i=0; i < getCurrentPlaylist().getEntries().size(); i++)
		{
			ISPEntry entry = getCurrentPlaylist().getEntries().get(i);
			/**
			 * Play the next entry
			 */
			if(entry.equals(e))
			{
				try
				{
					ISPEntry nextEntry = getCurrentPlaylist().getEntries().get(i-1);
					playSong(nextEntry);
				}
				catch(Exception ex)
				{
					
				}
			}
		}
	}
	private ISPEntry mCurrentPlayingEntry;
	public ISPEntry getCurrentPlayingEntry() {
		return mCurrentPlayingEntry;
	}
	public void setCurrentPlayingEntry(ISPEntry mCurrentPlayingEntry) {
		this.mCurrentPlayingEntry = mCurrentPlayingEntry;
	}
	/*
	 * Sets the current song to playing
	 */
	public void playSong(ISPEntry entry)
	{
		if(mPrimaryMediaSource!=null)
		{
			mPrimaryMediaSource.stop();
		}
		if(entry.getUri().getApplication().equals("song"))
		{
			for(IMCSource imc : getMediaSources().values())
			{
				URI newUri = imc.rawFind(entry.getUri());
				if(newUri != null)
				{
					mPrimaryMediaSource = imc;
					imc.play(newUri);
					return;
				}
				
				
			}
		}
		else
		{
			// Get the correct media source to handle the song
			for(IMCSource imc : getMediaSources().values())
			{
				if(imc.getNamespace().equals(entry.getUri().getApplication()))
					mPrimaryMediaSource = imc;
				
			}
		}
		setCurrentPlayingSong(entry.getUri());
		setCurrentPlayingActivity(entry.getActivity());
		setCurrentPlaylist(entry.getHost());
		setCurrentPlayingEntry(entry);
		// Play song
		if(mPrimaryMediaSource!=null)
		{
			mPrimaryMediaSource.play(entry.getUri());
			
		}
		for(ISPEntry d : getCurrentPlaylist().getEntries())
		{
			if(d instanceof Component)
			{
				((Component)d).repaint();
			}
		}
		
	}
	
	private FootPanel mBottomPanel;
	private SPTreeView mList;
	private SPContainer mViewContainer;
	private ArrayList<ISPEntry> mListModel;
	private static final long serialVersionUID = 1L;
	private HeaderPanel mHeaderPanel;
	private JSplitPane mJSplitPane;
	private BufferedContainer mLeftPane;
	private BufferedContainer  mContentBox;
	private void loadUserData()
	{
		Preferences prefs = Preferences.userRoot().node("/com/foo/myApp");
		access_token = prefs.get("access_token", "");
		
		//prefs.put("access_token","dc68494d545de644d69bdea8ecebe284");
		if(access_token.equals(""))
		{
			//OAuthWindow d = new OAuthWindow(this);
			if(SpotifyWindow.access_code != (null))
			{
				// Get access token
				try {
					InputStream is = new URL(OAUTH_BASE_URL+"oauth/access_token/?client_id="+OAUTH_CLIENT_ID+"&client_secret="+SpotifyWindow.OAUTH_SECRET_ID+"&redirect_uri=f&code="+access_code).openStream();
					access_token = new Scanner(is).useDelimiter("\\A").next().split("=")[0].split("&")[0];
					prefs.put("access_token", access_token);
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e2){
					
				}
				
			}
			
		}
		/**
		 * Load playlist stack
		 
		
		try {
			Document c = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new URL("http://localhost:64/Project/mediachrome/api/1/"+access_token+"/me/").openStream());
			NodeList playlists = c.getElementsByTagName("playlist");
			for(int i=0; i < playlists.getLength(); i++)
			{
				Element pls = (Element)playlists.item(i);
				URI playlist = new URI("spotify:playlist:"+pls.getElementsByTagName("slug").item(0).getFirstChild().getNodeValue());
				playlist.setTitle(pls.getElementsByTagName("title").item(0).getFirstChild().getNodeValue());
				
				SimpleEntry se = new SimpleEntry(null, null, playlist, null, null, null);
				mListModel.add(se);
			}
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
	/**
	 * Adds the view to the left list
	 * @param uri
	 */
	public void addToFavorites(URI uri)
	{
		mListModel.add(new SimpleEntry(null,null,uri,null,null,null));
	}
	private int mouseOffsetX = 0;
	private int mouseOffsetY = 0;
	public SpotifyWindow()
	{
		
		
		super(); 
	//	setUndecorated(true);
		try {
			loadPlugins();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		mSkin = new DefaultSkin(); // initialize the skin
		mContentBox = new BufferedContainer(this);
		mLeftPane = new BufferedContainer(this);
		mJSplitPane = new JSplitPane();
		new JSplitPane();
		
		mBottomPanel = new FootPanel(this);
		mBottomPanel.setPreferredSize(new Dimension(100,128));
		setSize(new Dimension(800,600));
		Color f = mSkin.getBackgroundColor();
		setBackground(f);
		
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent we)
			{
				System.exit(0);
			}
		});
		
		// Set the fundamental layout
		mListModel = new ArrayList<ISPEntry>();
		mList = new SPTreeView(this,mListModel); 
		mPrimaryMediaSource = new com.krikelin.mediasource.SpotifyPlayer();
		getMediaSources().put("spotify", new com.krikelin.mediasource.SpotifyPlayer());
		getMediaSources().get("spotify").setPlaybackListener(new IMCPlaybackEventListener(){

			@Override
			public void playbackCompleted(URI source) {
				// TODO Auto-generated method stub
				SpotifyWindow.this.playNext();
			}
			
		});
		mHeaderPanel = new HeaderPanel(this);
		mHeaderPanel.setPreferredSize(new Dimension(0,50));
mHeaderPanel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				mouseOffsetX = e.getX();
				mouseOffsetY = e.getY();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		mHeaderPanel.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				if(mouseOffsetX > 0 && mouseOffsetY > 0 ){
					int currentX = getLocation().x;
					int currentY = getLocation().y;
					
					setLocation( e.getXOnScreen() - mouseOffsetX, e.getYOnScreen() - mouseOffsetY);
				}
			}
		});
		this.add( mHeaderPanel,BorderLayout.NORTH);
	
		mViewContainer = new SPContainer(this);
		mHeaderPanel.setSearchListener(new OnSearchListener(){

			@Override
			public void OnSearch(Object sender, String query) {
				// TODO Auto-generated method stub
				
					SpotifyWindow.this.mViewContainer.navigate(new URI(query));
				
			}
			
		});
		this.add(mJSplitPane,BorderLayout.CENTER);
		mJSplitPane.setBorder(BorderFactory.createEmptyBorder());
		mJSplitPane.setLeftComponent(mLeftPane);
		// Add contents to the left pane
		mLeftPane.setLayout(new BorderLayout()); // Set border layout
		mLeftPane.add(mList,BorderLayout.CENTER);
		mJSplitPane.setDividerSize(2);
		mContentBox.setPreferredSize(new Dimension(128,100));
		mJSplitPane.setRightComponent(mViewContainer);
		// Content pane
	/*	mContentPane.setBorder(null);
		mContentPane.setLeftComponent(mViewContainer);
		mContentPane.setRightComponent(new SPTreeView(this,new ArrayList<ISPEntry>()));
		mContentPane.setResizeWeight(0.75D);
		mContentPane.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				SpotifyWindow.this.navigate(SpotifyWindow.this.mCurrentPlayingActivity.getUri());
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		mList.setBackground(getSkin().getBackgroundColor());
		
		/**
		 * Add items to the jlist
		 */
		mHeaderPanel.setNavigateHandler(mHeaderPanel.new NavigateListener(){

			@Override
			public void navigate(int mode) {
				// TODO Auto-generated method stub
				if(mode == HeaderPanel.NavigateListener.MODE_BACK)
				{
					mViewContainer.goBack();
				}
				else
				{
					mViewContainer.goForward();
				}
			}
			
		});
		
		mList.setPreferredSize(new Dimension(200,100));
		//mJSplitPane.setLeftComponent(mList);
		FootPanel mBottom = new FootPanel(this);
		mBottom.setPreferredSize(new Dimension(600,48));
		this.add(mBottom,BorderLayout.SOUTH);
		
		
		
		
		mListModel.add(new SimpleEntry(null,null,new URI("Home","spotify:home:a"),null,null,null));
		mListModel.add(new SimpleEntry(null,null,new URI("Market","spotify:market"),null,null,null));
		mListModel.add(new SimpleEntry(null,null,new URI("-","spotify:market"),null,null,null));
		for(Class<Activity> act : getMashups().values()){
			try {
				Activity activity = act.newInstance();
				// add list entry
				Image icon = null;
				try
				{
					icon = activity.getIcon();
				}
				catch(Exception e){
					e.printStackTrace();
				}
				mListModel.add(new SimpleEntry(icon, null, null, new URI(activity.getTitle(),"spotify:"+act.getSimpleName()+":default"), null, null, null));
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
		//mListModel.add(new SimpleEntry(null,null,new URI("Purchases","spotify:playlist:purchases"),null,null,null));
		//mListModel.add(new SimpleEntry(null,null,new URI("Local files","spotify:library:coal"),null,null,null));
		//mListModel.add(new SimpleEntry(null,null,new URI("-","spotify:playlist:purchases"),null,null,null));
		
		// Get your playlists
		
		//mListModel.add(new SimpleEntry(null,null,new URI("Local files","spotify:playlist:purchases"),null,null,null));
		
		setFont(getSkin().getFont());
		mList.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				int selIndex = mList.getSelectionModel().getMaxSelectionIndex();
				if(selIndex > -1)
					mViewContainer.navigate((URI)((SimpleEntry)mList.getSelectedValue()).getUri());
			 
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		// Add the foot
		loadUserData();
		doLayout();
		
	}
	/**
	 * Adds an shortcut to the activity
	 * @param name
	 * @param uri
	 */
	public void addShortcut(String name,URI uri)
	{
		mListModel.add(new SimpleEntry(null,null,uri,null,null,null));
		
	}
	/***
	 * Register an plugin to SpotifyWindow
	 * @param namespace
	 * @param act
	 */
	public void registerActivity(String namespace)
	{
		// Create the list if null
		if(plugins == null)
			plugins = new ArrayList<String>();
		
		// Add the plugin to the list
		plugins.add(namespace);
	}
	/**
	 * Navigates to an particular view
	 * @param target 
	 */
	public void navigate(URI target)
	{
		mViewContainer.navigate(target);
		
	}
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		
/*		dim = getSize();
		mBufferedImage =  createImage(dim.width,dim.height);
		bufferGraphics = mBufferedImage.getGraphics();
		firstTime=false;
	*/
		super.paint(g);
		//g.drawImage(mBufferedImage,0,0,this);
	}
	public void setHeaderPanel(HeaderPanel mHeaderPanel) {
		this.mHeaderPanel = mHeaderPanel;
	}
	public HeaderPanel getHeaderPanel() {
		return mHeaderPanel;
	}
	public void setViewContainer(SPContainer mViewContainer) {
		this.mViewContainer = mViewContainer;
	}
	public Container getViewContainer() {
		return mViewContainer;
	}
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		System.exit(0);
		
	}
	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void setCurrentPlayingActivity(Activity mCurrentPlayingActivity) {
		this.mCurrentPlayingActivity = mCurrentPlayingActivity;
	}
	public Activity getCurrentPlayingActivity() {
		return mCurrentPlayingActivity;
	}
	public void setCurrentPlaylist(SPContentView mCurrentPlaylist) {
		this.mCurrentPlaylist = mCurrentPlaylist;
	}
	public SPContentView getCurrentPlaylist() {
		return mCurrentPlaylist;
	}
	public void setCurrentPlayingSong(URI mCurrentPlayingSong) {
		this.mCurrentPlayingSong = mCurrentPlayingSong;
	}
	public URI getCurrentPlayingSong() {
		return mCurrentPlayingSong;
	}
	public Hashtable<String, Class<Activity>> getActivities() {
		return activities;
	}
	public void setActivities(Hashtable<String, Class<Activity>> activities) {
		this.activities = activities;
	}
	public Hashtable<String, Class<Activity>> getMashups() {
		return mashups;
	}
	public void setMashups(Hashtable<String, Class<Activity>> mashups) {
		this.mashups = mashups;
	}
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
