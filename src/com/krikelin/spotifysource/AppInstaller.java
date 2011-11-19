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
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.jar.JarEntry;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class AppInstaller {
	private URL url;
	public AppInstaller(URL url){
		this.url = url;
	}
	public AppInstaller(File fil){
		this.app_jar = fil;
	}
	protected void copyFile(String src,String dest) throws IOException{
		File inputFile = new File(src);
	    File outputFile = new File(dest);

	    FileReader in = new FileReader(inputFile);
	    FileWriter out = new FileWriter(outputFile);
	    int c;

	    while ((c = in.read()) != -1)
	      out.write(c);

	    in.close();
	    out.close();
	}
	protected void extractJar(String destDir,String jarFile) throws IOException{
		File destination  = new File(destDir);
		java.util.jar.JarFile jar = new java.util.jar.JarFile(jarFile);
		java.util.Enumeration<JarEntry> enu = jar.entries();
		while (enu.hasMoreElements()) {
			try{
			    java.util.jar.JarEntry file = (java.util.jar.JarEntry) enu.nextElement();
			    java.io.File f = new java.io.File(destDir + java.io.File.separator + file.getName());
			    if (file.isDirectory()) { // if its a directory, create it
			        f.mkdir();
			        continue;
			    }
			    java.io.InputStream is = jar.getInputStream(file); // get the input stream
			    if(!(destination.exists())){
			    	destination.createNewFile();
			    }
			   
			    java.io.FileOutputStream fos = new java.io.FileOutputStream(f);
			    while (is.available() > 0) {  // write contents of 'is' to 'fos'
			        fos.write(is.read());
			    }
			    fos.close();
			    is.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	protected void addPackage(String file,String context) throws IOException{
		// Add the package to the file
	
		BufferedWriter bw = new BufferedWriter(new FileWriter(SPContainer.EXTENSION_DIR+"\\"+context, true));
		bw.write("\n"+file);
		bw.close();
	}
	
	public static final int size = 1024;
	protected File downloadJar(URL file) throws IOException{
		// TODO FIx this later!!!!
		
		
		// Download the Jar file {
		
		InputStream in = file.openStream();
		File c = new File(SPContainer.EXTENSION_DIR+"\\jar\\"+file.getFile().split("/")[file.getFile().split("/").length-1]);
		c.createNewFile();
		FileOutputStream fos = new FileOutputStream(c);
	
		this.app_jar = c;
		return c;
		// }
		
		
	}
	public static String xmlns = "http://spotiapps/";
	public static final String tmp_dir = "C:\\temp\\";
	private File app_jar;
	public void install() throws IOException, SAXException, ParserConfigurationException{
		if(url != null){
			downloadJar(url);
		}
		if( this.app_jar != null){
			installJar();
		}
	}
	public void installJar() throws IOException, SAXException, ParserConfigurationException{

	
		java.util.jar.JarFile jar = new java.util.jar.JarFile(app_jar);
		// Get manifest
		java.util.Enumeration<JarEntry> enu = jar.entries();
		while(enu.hasMoreElements()){
			JarEntry elm = enu.nextElement();
			if(elm.getName().equals("SpotifyAppManifest.xml")){
				DataInputStream dis = new DataInputStream(jar.getInputStream(elm));
				Document d = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(dis);
				dis.close();
				// Get package name
				String package_name = d.getDocumentElement().getAttribute("package");
				
				addPackage(package_name,"packages");
				// Get all activities
				NodeList activities = d.getElementsByTagName("activity");
				for(int i=0; i < activities.getLength(); i++){
					Element activity = (Element)activities.item(i);
					String name = activity.getAttribute("name"); // Get the name
					// Append the previous name if it starts with .
					if(name.startsWith(".")){
						name = name.replace(".", "");
						
					}
					if(!name.startsWith(package_name)){
						name = package_name + "." + name;
						
					}
					//addPackage(name,"activities");
					NodeList intentFilters = activity.getElementsByTagName("intent-filter");
					for(int j=0; j < intentFilters.getLength(); j++){
						NodeList actions =  ((Element)intentFilters.item(0)).getElementsByTagName("action");
						for(int k=0; k < actions.getLength(); k++){
							String action_name = ((Element)actions.item(k)).getAttribute("name");
							addPackage(name,"\\activities\\"+ action_name);
						}
						
					}
				}
				copyFile(app_jar.getAbsolutePath(), SPContainer.EXTENSION_DIR+"\\jar\\"+package_name+".jar");
				// Runtime.getRuntime().exec("copy \""+app_jar+"\" \""+SPContainer.EXTENSION_DIR+"\\jar\\"+package_name+".jar\"");
				
			}
		}
		
		
		
	}
	public URL getUrl() {
		return url;
	}
	public void setUrl(URL url) {
		this.url = url;
	}
}
