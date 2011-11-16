package com.krikelin.spotifysource;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URLClassLoader;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

public class ResourceManager {
	
	private Context context;

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	public ResourceManager(Context context){
		this.context =context;
	}
	public Object getResourceFromURI(String uri) throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, IOException{
		String pkg = getClass().getPackage().getName();
		
		ResURI resUri = new ResURI(uri, this.context);
		if(resUri.getProclaimer() == '@'){
			if(resUri.getPackage().equals("com.android.spotifysource")){
				resUri.setPackage(pkg);
			}
			return getResource(resUri.getType(), resUri.getPackage(), resUri.getResId());
		}
		return resUri.getResId();
	}
	/**
	 * Gets an image from the resources
	 * @param res
	 * @return
	 */
	public Image getImageResource(int res){
		try {
			InputStream is = getResource("drawable","png",res);
			Image i = ImageIO.read(is);
			return i;
		} catch (ClassNotFoundException | IllegalArgumentException
				| IllegalAccessException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	/***
	 * Gets the specified resource for the stream
	 * @param resType
	 * @param ext
	 * @param r
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	public InputStream getResource(String resType,String ext, int r) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, IOException
	{
		String pkg = context.getClass().getPackage().getName(); // package source 
		
		return getResource(resType, ext,pkg, r);
	}
	/***
	 * Gets the specified resource for the stream
	 * @param resType
	 * @param ext
	 * @param r
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	public InputStream getResource(String resType,String ext, String pkg, int r) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, IOException
	{
		
		// com.krikelin.<package>.R
		URLClassLoader cl = (URLClassLoader)ClassLoader.getSystemClassLoader();
		
		
		Class<?> resources =Class.forName(pkg+".R."+resType); // get R class
		
		for(Field f : resources.getDeclaredFields()){
			// If the res value is equal to the one specified, get the name
			if(f.getInt(null)==r){
				String resName = f.getName(); // Get the name of the resource
				String jarAddress = SPContainer.EXTENSION_DIR + "\\" +getClass().getPackage() + ".jar";
				
				// Lookup the resource in the jar
				JarFile c = new JarFile(jarAddress);
				JarEntry resFile = (JarEntry) c.getEntry("res\\"+resType+"\\"+resName+"."+ext);
					// Gets the jar file
				
				// Now return the type of the resource
				InputStream is =  c.getInputStream(resFile);
				return is;
			}
		}
		return null;
	}
}
