package com.krikelin.spotifysource;

import java.lang.reflect.Field;

public class ResURI {
	private String pkg;
	private String resName;
	private String resType;
	private int resId;
	private char proclaimer;
	public ResURI(String uri, Context c) throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException{
		if((uri.startsWith("@") && uri.startsWith("?"))){
			this.proclaimer = uri.charAt(0);
			if(uri.contains("/")){
				String preAddress = uri.split("/",2)[0];
				this.setType("");
				if(preAddress.contains(":")){
					String packg = preAddress.replace("@", "").replace("?","").split(":")[0];
					this.setType(preAddress.replace("@", "").replace("?","").split(":")[1]);
					if(packg == "spotiapp"){
						setPackage("com.krikelin.spotifysource");
						
					}else{
						setPackage(packg);
					}
					
				}else{
					setType(uri.split("/")[0]);
				}
				this.setResName(uri.split("/",2)[1]);
				
			}
			// Set the resource id
			Class<?> resources = (Class<?>)Class.forName(getPackage()+".R."+resType); // get R class
			for(Field f : resources.getDeclaredFields()){
				// If the res value is equal to the one specified, get the name
				int resId =f.getInt(null);
				if(f.getName().equals(getResName())){
					setResId(resId);
				}
			}
			
		}
		throw new IllegalArgumentException("The uri address must start with either @ or ?");
		
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public String getPackage() {
		return pkg;
	}
	public void setPackage(String pkg) {
		this.pkg = pkg;
	}
	public String getType() {
		return resType;
	}
	public void setType(String resType) {
		this.resType = resType;
	}
	public char getProclaimer() {
		return proclaimer;
	}
	public void setProclaimer(char proclaimer) {
		this.proclaimer = proclaimer;
	}
	public int getResId() {
		return resId;
	}
	public void setResId(int resId) {
		this.resId = resId;
	}
}
