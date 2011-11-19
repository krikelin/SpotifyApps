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

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JSplitPane;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.lobobrowser.gui.FramePanel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import com.krikelin.spotifysource.Activity;
import com.krikelin.spotifysource.App;
import com.krikelin.spotifysource.AppInstaller;
import com.krikelin.spotifysource.SPContainer;
import com.krikelin.spotifysource.SPContentView;
import com.krikelin.spotifysource.SPListView;
import com.krikelin.spotifysource.SPScrollPane;
import com.krikelin.spotifysource.SpotifyWindow;
import com.krikelin.spotifysource.URI;
import com.sun.xml.internal.ws.util.xml.XmlUtil;

public class market extends Activity {
	
	private ArrayList<App> new_apps = new ArrayList<App>();
	@SuppressWarnings("unused")
	private ArrayList<App> top_apps = new ArrayList<App>();
	public void downloadApp(App app){
		
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
			"State", "title","Author","Category","Version"
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
				return app.isInstalled() ? "Installed" : "";
			case 1:
				return app;
			case 2:
				return app.getAuthor();
				
			case 3:
				return app.getVersion();
			case 4:
				return app.getCategory();
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
	public static final String MARKET_BASE = "http://localhost:64/myproject/spotiapps/";
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
		public Home(Activity activity, SpotifyWindow mContext,ArrayList<App> app_list) {
			super(activity, mContext);
			this.apps= app_list;
			// TODO Auto-generated constructor stub
			final SPListView v = new SPListView(new MarketTableModel(this.apps), mContext);
			SPScrollPane jsp = new SPScrollPane(activity, v);
			add(jsp);
			jsp.setBorder(BorderFactory.createEmptyBorder());
			v.addMouseListener(new MouseListener(){

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					if (e.getClickCount() == 2 && !e.isConsumed()) {
						App app = (App)v.getModel().getValueAt(v.getSelectedRow(), 1);
						app.download();
					}
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
		}
		
	}
	public class Overview extends SPContentView{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -1049366795495350915L;
		public Overview(Activity activity, SpotifyWindow mContext,ArrayList<App> app_list) {
			super(activity, mContext);
			//setTexturedBackground(getContext().getSkin().getDashedBackground());
			FramePanel fp = new FramePanel();
			add(fp);
			try
			{
				fp.navigate("http://localhost/spotifysource/market.html");
			}catch(Exception e){
				
			}
		}
		
	}
	
	protected void downloadList(String params,SpotifyWindow context){
		
		try {
			URL c = new URL(MARKET_BASE+"list/"+params);
			Document d = (Document)DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(c.openStream());
			NodeList apps = d.getElementsByTagName("app");
			for(int i=0; i < apps.getLength(); i++){
				if(apps.item(i) instanceof Element)
				{
					App app = new App((Element)apps.item(i), context);
					new_apps.add(app);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public Object onLoad(URI args) {
		// TODO Auto-generated method stub
		downloadList("a/", getContext());
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

			
			addPage("Recommonded", new Home(this, getContext(),new_apps));
			
			
		}
	}

}
