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
 */package com.krikelin.spotifysource.views;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import com.krikelin.spotifysource.SPActivity;
import com.krikelin.spotifysource.SPContentView;
import com.krikelin.spotifysource.SPListView;
import com.krikelin.spotifysource.SPScrollPane;
import com.krikelin.spotifysource.SpotifyWindow;
import com.krikelin.spotifysource.URI;

public class market extends SPActivity {
	private ArrayList<App> new_apps = new ArrayList<App>();
	@SuppressWarnings("unused")
	private ArrayList<App> top_apps = new ArrayList<App>();
	
	public class App{
		public App() {
			
		}
		public App(Element n){
			name = n.getElementsByTagName("name").item(0).getFirstChild().getNodeValue();
			author = n.getElementsByTagName("author").item(0).getFirstChild().getNodeValue();
			category = n.getElementsByTagName("category").item(0).getFirstChild().getNodeValue();
			version =  n.getElementsByTagName("version").item(0).getFirstChild().getNodeValue();
			
		}
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
		
	}
	/***
	 * Market table model
	 * @author Alexander
	 *
	 */
	public class MarketTableModel implements TableModel{
		private List<App> apps = new ArrayList<App>();
		public MarketTableModel(List<App> apps){
			this.apps= apps;
		}
		public final String[] COLUMN_NAME = new String[]{
			"title","Author","Category","Version"
		};
		@Override
		public void addTableModelListener(TableModelListener l) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			// TODO Auto-generated method stub
			if(columnIndex == 0){
				return App.class;
			}
			return String.class;
		}

		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return COLUMN_NAME.length;
		}

		@Override
		public String getColumnName(int columnIndex) {
			// TODO Auto-generated method stub
			
			return COLUMN_NAME[columnIndex];
		}

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return apps.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			App app = (App)apps.get(rowIndex);
			switch(columnIndex){
			case 0:
				return app;
			case 1:
				return app.author;
				
			case 2:
				return app.version;
			case 3:
				return app.category;
			default:
				return "";
			}
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void removeTableModelListener(TableModelListener l) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			
		}
		
	}
	public static final String MARKET_BASE = "http://localhost:64/myproject/spotiapps/market/";
	/**
	 * 
	 */
	private static final long serialVersionUID = 8611216247309530594L;
	public class Home extends SPContentView{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -1049366795495350915L;
		private ArrayList<App> apps;
		public Home(SPActivity activity, SpotifyWindow mContext,ArrayList<App> app_list) {
			super(activity, mContext);
			this.apps= app_list;
			// TODO Auto-generated constructor stub
			SPListView v = new SPListView(new MarketTableModel(this.apps), mContext);
			SPScrollPane jsp = new SPScrollPane(activity, v);
			add(jsp);
			jsp.setBorder(BorderFactory.createEmptyBorder());
		}
		
	}
	public class Overview extends SPContentView{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -1049366795495350915L;
		public Overview(SPActivity activity, SpotifyWindow mContext,ArrayList<App> app_list) {
			super(activity, mContext);
			setTexturedBackground(getContext().getSkin().getDashedBackground());
		}
		
	}
	protected void downloadList(){
		
		try {
			URL c = new URL(MARKET_BASE+"list/1/");
			Document d = (Document)DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(c.openStream());
			NodeList apps = d.getElementsByTagName("app");
			for(int i=0; i < apps.getLength(); i++){
				if(apps.item(i) instanceof Element)
				{
					App app = new App((Element)apps.item(i));
					new_apps.add(app);
				}
			}
		} catch (SAXException | IOException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public Object onLoad(URI args) {
		// TODO Auto-generated method stub
		downloadList();
		return null;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Market";
	}

	@Override
	public void render(URI args, Object... result) {
		// TODO Auto-generated method stub
		if(args.getParameter().equals("")){
			addPage("Overview", new Overview(this, getContext(),new_apps));
			
			addPage("Recommonded", new Home(this, getContext(),new_apps));
			addPage("New apps", new Overview(this, getContext(),new_apps));
			addPage("Top List", new SPContentView(this, getContext()));
			
		}
	}

}
