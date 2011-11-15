package com.seguradroid;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.util.Enumeration;
import java.util.HashMap;

import com.krikelin.spotifysource.Context;
import com.krikelin.spotifysource.SpotifyWindow;
/**
 * Implementation of the android resource system like
 * to yours
 * @author Alexander
 *
 */
public class ResourceManager {
	private Context context;
	
	/**
	 * Lookups resources in android form @[android:][resType]/[resource]
	 * @param packet
	 * @param address
	 * @return
	 */
	public Object resolveAddress( String address){
		try {
			String resType = address.split("/",2)[0];
			String resource = address.split("/",2)[1];
			String domain = "com.krikelin.spotifysource.";
			if(!(resType.startsWith("@") || resType.startsWith("?"))){
				throw new IllegalArgumentException("Illegal argument");
			} 
			char addressType = resType.charAt(0);
			resType = resType.replace("@", "").replace("?", "");
			if(resType.contains(":") )
			{
				domain = context.getResDomains().get(resType.split(":")[0]);
				resType = resType.split(":")[1];
				
			}
			
			// Get the class by the domain
			Class<?> c = Class.forName(domain+".R");
			for(Field f : c.getFields()){
				if(f.getName().equals(resource)){
					if(addressType == ('?')){
						return f.getInt(null); // return the int
					}
					if(addressType == '?'){
						return getResource(resType,f.getInt(null));
					}
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	
		
		
	}
	private HashMap<Integer, URL> drawable = new HashMap<Integer, URL>();
	private HashMap<Integer, URL> string = new HashMap<Integer, URL>();
	/**
	 * @return the drawable
	 */
	public HashMap<Integer, URL> getDrawables() {
		return drawable;
	}
	/**
	 * @param drawable the drawable to set
	 */
	public void setDrawable(HashMap<Integer, URL> drawable) {
		this.drawable = drawable;
	}
	/**
	 * @return the string
	 */
	public HashMap<Integer, URL> getStrings() {
		return string;
	}
	/**
	 * @param string the string to set
	 */
	public void setString(HashMap<Integer, URL> string) {
		this.string = string;
	}
	/**
	 * @return the values
	 */
	public HashMap<Integer, URL> getValues() {
		return values;
	}
	/**
	 * @param values the values to set
	 */
	public void setValues(HashMap<Integer, URL> values) {
		this.values = values;
	}
	/**
	 * @return the layout
	 */
	public HashMap<Integer, URL> getLayouts() {
		return layout;
	}
	/**
	 * @param layout the layout to set
	 */
	public void setLayout(HashMap<Integer, URL> layout) {
		this.layout = layout;
	}
	private HashMap<Integer, URL> values = new HashMap<Integer, URL>();
	private HashMap<Integer, URL> layout = new HashMap<Integer, URL>();
	
	/**
	 * @from http://stackoverflow.com/questions/320542/how-to-get-the-path-of-a-running-jar-file
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getJarFolder() {
			
		try {
			CodeSource codeSource =  ResourceManager.class.getProtectionDomain().getCodeSource();
			File jarFile;
			
			jarFile = new File(codeSource.getLocation().toURI().getPath());
			String jarDir = jarFile.getParentFile().getPath();
			return jarDir;
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * Gets an resource by the unique code
	 * @param type
	 * @param res_code
	 * @return
	 */
	public InputStream getResource(String type,int res_code){
		try {
			Enumeration<URL> resources = context.getLocalResources(type);
			URL curURL = null;
			while( (curURL = resources.nextElement()) != null)
			{
				@SuppressWarnings("rawtypes")
				Class c = Class.forName(context.getClass().getPackage().getName()+".R."+type);
				for(Field field : c.getDeclaredFields()){
					String resName = curURL.getPath().split("/")[curURL.getPath().split("/").length-1].split(".")[0];
					if(res_code == field.getInt(null) && resName.equals(field.getName())){
						return curURL.openStream();
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public void associateResourceType(String type){
		try {
			Enumeration<URL> resources = context.getLocalResources(type);
			URL curURL = null;
			while( (curURL = resources.nextElement()) != null)
			{
				for(Field field : Class.forName(type+"R."+type).getDeclaredFields()){
					String resName = curURL.getPath().split("/")[curURL.getPath().split("/").length-1].split(".")[0];
					
					if(field.getName().equals(resName)){
						if(type == "drawable"){
							drawable.put(field.getInt(null), curURL);
						}
					}
				}
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private String packet;
	public ResourceManager(SpotifyWindow context,String packet){
		this.setPacket(packet);
		
	}
	public void getResourceFromDrawable(int drawable){
	
	}
	public static void main(){
		
	}
	public String getPacket() {
		return packet;
	}
	public void setPacket(String packet) {
		this.packet = packet;
	}

}
