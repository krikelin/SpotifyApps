package com.krikelin.spotifysource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.sun.xml.internal.ws.util.xml.XmlUtil;

public class App{
	public class AlreadyDownloadedException extends Exception{

		/**
		 * 
		 */
		private static final long serialVersionUID = -5869878260283451736L;
		
	}
	private String _package;
	private URL file_url;
	public App() {
		
	}
	public App(Element n,SpotifyWindow context){
		name = n.getElementsByTagName("name").item(0).getFirstChild().getNodeValue();
		author = n.getElementsByTagName("author").item(0).getFirstChild().getNodeValue();
		category = n.getElementsByTagName("category").item(0).getFirstChild().getNodeValue();
		version =  n.getElementsByTagName("version").item(0).getFirstChild().getNodeValue();
		setPackage(n.getElementsByTagName("package").item(0).getFirstChild().getNodeValue());
		try {
			file_url =  new URL(XmlUtil.getTextForNode(n.getElementsByTagName("latestVersionFile").item(0)));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setInstalled(context.isAppInstalled(getPackage()));
	}
	/**
	 * Installs the package to the bucked
	 */
	public void download(){
		AppInstaller installer = new AppInstaller(file_url);
		try {
			installer.install();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private boolean installed= false;
	private String name;
	
	private String author;
	private String category;
	private String version;
	@Override
	public String toString()
	{
		return this.name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPackage() {
		return _package;
	}
	public void setPackage(String _package) {
		this._package = _package;
	}
	public boolean isInstalled() {
		return installed;
	}
	public void setInstalled(boolean installed) {
		this.installed = installed;
	}
	
}