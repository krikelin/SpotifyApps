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

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import com.krikelin.spotifysource.URI;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.krikelin.mako.*;
public class View extends Container implements SPPart {
	private LayoutManager mLayoutManager;
	private SpotifyWindow mContext;
	public LayoutManager getLayout()
	{
		return mLayoutManager;
	}
	/** 
     * @param filePath      name of file to open. The file can reside
     *                      anywhere in the classpath
     */
    private String readFileAsString(String filePath) throws java.io.IOException {
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(new InputStreamReader(this
            .getClass().getClassLoader().getResourceAsStream(filePath)));
        char[] buf = new char[1024];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();
    }
	private MakoEngine mMakoEngine;
	/**
	 * The host view
	 * @return
	 */
	public SpotifyWindow getContext()
	{
		return mContext;
	}
	public String getViewPath()
	{
		return "views//";
	}
	public View(View host,URI Uri)
	{
		mUri = Uri;
		// Create the page 
		try {
			preprocess(Uri);
		} catch (SAXException e) {	
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	Document mDomDocument; 
	public Document getDOMDocument()
	{
		return mDomDocument;
	}
	public void preprocess(URI uri) throws SAXException, ParserConfigurationException
	{
		mMakoEngine = new MakoEngine();
		
		// Load the template
		String path =  getViewPath() + uri.getApplication()+".xml";
		try {
			String template = readFileAsString(path);
			// Preprocess the template
			String output = mMakoEngine.Preprocess(template, uri.getParameter(), false, uri.toString(),true);
			InputSource IS = new InputSource();
			IS.setCharacterStream(new StringReader(output));
			Document d = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(IS);
			mDomDocument=d;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	/**
	 * Creates an new element by the namer
	 * @param container
	 * @param elm
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	
	public void parse(Document d)
	{
		
	}
	private URI mUri;
	private long mIndex = 0L;
	public long getIndex ()
	{
		return mIndex;
	}
	/**
	 * Gets the URI of the page
	 * @return
	 */
	public URI getUri()
	{
		return mUri;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8482607092831535359L;
	
	/**
	 * Creates an new View
	 */
	public View()
	{
		super();
		
	}
	
	public java.awt.Component createElement(Container container, Element elm) throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		ElementFactory ef = (ElementFactory)Class.forName("com.krikelin.spotifysource.view.widget."+elm.getNodeName()).newInstance();
		return ef.createControlFromElement(elm, elm.getAttribute("id"),container, this);
		 
	}
	public void renderElements( Element e)
	{
		// Clear the container
		java.awt.Container armature = new java.awt.Container();
		armature.setLayout(new CardLayout());
		// Crate the view spectrum
		java.awt.Container tabBar = new java.awt.Container();
		tabBar.setLayout(new FlowLayout());
		
		NodeList sections = e.getElementsByTagName("view");
		
		for(int i=0; i < sections.getLength(); i++)
		{
			Element section = (Element)sections.item(i);
			// TODO Create section buttons
			java.awt.Button btn = new java.awt.Button();
			btn.setLabel(section.getAttribute("title"));
			btn.setActionCommand("section:"+i);
			btn.setSize(312,12);
			
			tabBar.add( btn);
			
		}
		// add tabbar
		armature.add(tabBar);
		// Create hideable sections
		for(int i=0; i < sections.getLength(); i++)
		{
			sections.item(i);
			Container secContainer = new Container();
			GridLayout gl = new GridLayout();
			secContainer.setLayout(gl);
			
			for(int j=0; i < e.getChildNodes().getLength(); j++)
			{
			
				
				Element elm = (Element)e.getChildNodes().item(j);
				try {
					createElement(secContainer,elm);
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				gl.setRows(i);
			}
		}
		
		// Add the layouts
		add(tabBar);
		add(armature);
		
	}
	
	
	/**
	 * Navigates to an page
	 * @param uri
	 */
	public void navigate(URI uri)
	{
		// TODO: Add code
	}
	
	/**
	 * Scripting engine
	 */
	private IScriptEngine mScriptEngine;
	/**
	 * Returns the scripting engine
	 * @return
	 */
	public IScriptEngine getScriptingEngine()
	{
		return mScriptEngine;
	}
}