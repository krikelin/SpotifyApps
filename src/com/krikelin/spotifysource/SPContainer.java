package com.krikelin.spotifysource;

import java.awt.CardLayout;
import java.awt.Container;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;

import javax.swing.BorderFactory;


/**
 * Container for various SPActivities
 * @author Alexander
 *
 */
public class SPContainer  extends Container implements SPPart{
	/***
	 * @from http://stackoverflow.com/questions/1275113/dynamic-class-loading-in-java-enumeration
	 * @param pckgname
	 * @return
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("rawtypes")
	public static Class[] getClasses(String pckgname)
	        throws ClassNotFoundException {
		ArrayList<Class> classes = new ArrayList<Class>();
	    // Get a File object for the package
	    File directory = null;
	    try {
	        ClassLoader cld = Thread.currentThread().getContextClassLoader();
	        if (cld == null) {
	            throw new ClassNotFoundException("Can't get class loader.");
	        }
	        
	        String path = '/' + pckgname.replace('.', '/');
	        URL resource = cld.getResource(path);
	        if (resource == null) {
	            throw new ClassNotFoundException("No resource for " + path);
	        }
	        directory = new File(resource.getFile());
	    } catch (NullPointerException x) {
	        throw new ClassNotFoundException(pckgname + " (" + directory
	                + ") does not appear to be a valid package");
	    }
	    if (directory.exists()) {
	        // Get the list of the files contained in the package
	        String[] files = directory.list();
	        for (int i = 0; i < files.length; i++) {
	            // we are only interested in .class files
	            if (files[i].endsWith(".class")) {
	                // removes the .class extension
	                classes.add(Class.forName(pckgname + '.'
	                        + files[i].substring(0, files[i].length() - 6)));
	            }
	        }
	    } else {
	        throw new ClassNotFoundException(pckgname
	                + " does not appear to be a valid package");
	    }
	    Class[] classesA = new Class[classes.size()];
	    classes.toArray(classesA);
	    
	    return classesA;
	}
	public static final String EXTENSION_DIR = "C:/Spotiapps/";
	private Hashtable<String,SPActivity> mActivities = new Hashtable<String,SPActivity>();
	private SPActivity mCurrentPage;
	public SPActivity getCurrentPage()
	{
		return mCurrentPage;
	}
	@SuppressWarnings("rawtypes")
	Class[] loadedExtensions;
	private Stack< SPActivity> mHistory = new Stack<SPActivity>();
	private Stack< SPActivity> mFuture = new Stack<SPActivity>();
	public Stack< SPActivity> getHistory()
	{
		return mHistory;
	}
	
	public Stack< SPActivity> getFuture()
	{
		return mFuture; 
	}
	public void goForward()
	{
		if(getHistory().size()> 0)
		{
			// Set current activity to future
			getHistory().push(mCurrentPage);
			
			
			// Get the previous page from the history
			mCurrentPage = getFuture().pop();
			
			viewStack.show(this,mCurrentPage.getUri().toLinkString());
		}
		
	}
	public void goBack()
	{
		if(getHistory().size() > 0)
		{
			// Set current activity to future
			getFuture().push(mCurrentPage);
			
			
			// Get the previous page from the history
			mCurrentPage = getHistory().pop();
			
		
			viewStack.show(this,mCurrentPage.getUri().toLinkString());
			validate();
			
		}
		
	}
	private SpotifyWindow mContext;
	public void navigate(URI uri)
	{
		if(uri.getApplication().equals("track")){
			mContext.playSong(new SimpleEntry(null, null, uri, null, null, null));
			return;
		}
		getFuture().clear();
		// Push current page to history and remove it from the stack
		if(getCurrentPage()!=null)
		{
			getHistory().push(getCurrentPage());
			
			
		}
		// If the view already exists in the stack, prefetch it
		if(mActivities.get(uri.toLinkString()) != null)
		{
			viewStack.show(this,uri.toLinkString());
			// Set new current page
			mCurrentPage = mActivities.get(uri.toLinkString());
			validate();
			
			
			
			return;
		}
		getClass();
		SPActivity activity = null;
		/***
		 * Try load the user base plugins
		
		for(String ns : SpotifyWindow.plugins)
		{
			Class<?> c;
			try {
				c = Class.forName(ns);
		
				try
				{
					activity = (SPActivity) c.newInstance();
				}catch(Exception e)
				{
					
				}
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} */
		if (activity == null) // Otherwise load it from the collection
		{
		
		/**
		 * Try load the class internally, otherwise load it
		 * from the extension folder
		 */
			try
			{
				activity = (SPActivity)Class.forName(	"com.krikelin.spotifysource.views."+uri.getApplication().toLowerCase()).newInstance();
			}
			catch(Exception e)
			{
				try {
					String app =uri.getApplication();
				/*	for(String s : mContext.getAc){
						File c = new File(SPContainer.EXTENSION_DIR+"\\"+s+".jar");
						
						URLClassLoader cl = new URLClassLoader(new URL[] { c.toURI().toURL() 	},Thread.currentThread().getContextClassLoader());	
						Thread.currentThread().setContextClassLoader(cl);
					
						activity = (SPActivity)cl.loadClass(s+"."+app).newInstance();
						
					}*/
				
					activity = ((Class<SPActivity>)mContext.getActivities().get(app)).newInstance();
				}  catch (InstantiationException e2) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				} catch (IllegalAccessException e3) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}  catch (Exception e4){
					e.printStackTrace();
					return;
				}
			}
		}
		activity.onCreate(uri, getContext());
		add(activity,uri.toLinkString());
		mActivities.put(uri.toLinkString(),activity);
		viewStack.show(this,uri.toLinkString());
		validate();
		// Set current page to the new activity
		mCurrentPage=activity;
		
	}
	CardLayout viewStack;
	/**
	 * 
	 */
	private static final long serialVersionUID = 516469135016297927L;
	public SPContainer(SpotifyWindow context)
	{
		 
		viewStack = new CardLayout();
		setLayout( viewStack);
		mContext=context;
		navigate(new URI("spotify:home:a"));
		
		
	}
	public void setActivies(Hashtable<String,SPActivity> mActivities) {
		this.mActivities = mActivities;
	}
	public Hashtable<String,SPActivity> getActivies() {
		return mActivities;
	}
	@Override
	public SpotifyWindow getContext() {
		// TODO Auto-generated method stub
		return mContext;
	}
}
