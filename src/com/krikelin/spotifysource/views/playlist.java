package com.krikelin.spotifysource.views;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.krikelin.spotifysource.*;
import com.krikelin.spotifysource.spml.SPWebView;
public class playlist extends SPActivity {
	private String mSlug;
	/**
	 * 
	 */
	public String getSlug()
	{
		return mSlug;
	}
	private static final long serialVersionUID = -2249423265022501988L;
	@Override
	public void dropItems(Object... args) {
		// TODO Auto-generated method stub
		super.dropItems(args);
		String address = getContext().getAPIBase()+"/playlist/"+getSlug()+"/add/?";
		
		String postData = "";
		for(Object obj :args)
		{
			if(obj instanceof String)
			{ 
				postData += (String)obj+"\n";
				
			}
			
		}
	
	    try {
	   	 	URL url = new URL(address);
		    URLConnection conn = url.openConnection();
		    conn.setRequestProperty
	        ("Content-Type", "application/x-www-form-urlencoded");
			
		    conn.setDoOutput(true);
	    	OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	    	wr.write(postData);
			 wr.flush();
			 
			// Get the response
		    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    String line;
		    while ((line = rd.readLine()) != null) {
		        // Process line...
		    }
		    wr.close();
		    rd.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
		
	}

	public class SPToolbar extends BufferedContainer
	{

		public SPToolbar() {
			super(getContext());
			// TODO Auto-generated constructor stub
		}
		
	}
	public class SPPlaylistHeader extends BufferedContainer
	{
		BufferedContainer vbox;
		private ImageBox mCover;
		private SPLabel mPlaylistTitle;
		public SPPlaylistHeader() {
			super(getContext());
			vbox = new BufferedContainer(getContext());
			vbox.setLayout(new GridLayout(3,1,2,2));
			// TODO Auto-generated constructor stub
			setLayout(new GridLayout(1,3,2,2));
			
			setPadding(20);
			mCover = new ImageBox(getContext().getSkin().getReleaseImage(),getContext());
			mCover.setMaximumSize(new Dimension(128,128));
			
			add(mCover); 
			add(vbox);
			mPlaylistTitle = new SPLabel(getContext(), "Title");
		
			vbox.add(mPlaylistTitle);
			
		}


		/**
		 * 
		 */
		private static final long serialVersionUID = 770784699054993705L;

		
		
	}
	ArrayList<ISPEntry> mContents = new ArrayList<ISPEntry>();
	
	public class Overview extends SPContentView
	{
		@Override
		public SpotifyWindow getContext(){
			return playlist.this.getContext();
		}
		/**
		 * 
		 */
		JScrollPane sv;
		JTable playlist;
		private JPanel mTableContainer;
		private JScrollPane mScrollPane;
		private SPListView listView;
		private static final long serialVersionUID = 1640855018158536150L;
		private SPWebView panel;
		public Overview(SPActivity activity, SpotifyWindow mContext) {
			super(activity, mContext);
			// TODO Auto-generated constructor stub
			mTableContainer = getContentView();
			panel = new SPWebView(playlist.this.getContext());
			panel.setBackground(getContext().getSkin().getBackgroundColor());
			listView = new SPListView(new SPTableModel(mContents),playlist.this.getContext());
			mScrollPane = new JScrollPane(listView);
			mScrollPane.getViewport().setBackground(getContext().getSkin().getBackgroundColor());
			// Find the music
			for(ISPEntry entry : mContents)
			{
				entry.setHost(this);
			}
//&			mTableContainer.add(panel,BorderLayout.NORTH);
			mTableContainer.add(panel,BorderLayout.NORTH);
			mTableContainer.add(mScrollPane,BorderLayout.CENTER);
			
			panel.setPreferredSize(new Dimension(120,120));
			doLayout();
			mTableContainer.doLayout();	
			
		}
		
	}
	public boolean mIsOwn = false;
	
	@Override
	public Object onLoad(URI args) {
		// TODO Auto-generated method stub
		// Load playlist from the service
		mSlug = args.getParameter();
		//dropItems(new String[]{"music://medichrome/Dr.+Sounds/test/test"  });
		ArrayList<ISPEntry> entries = new ArrayList<ISPEntry>();
		try {
			URL d = new URL("http://localhost:64/Project/mediachrome/api/1/"+getContext().access_token+"/playlist/"+args.getParameter()+"/");
			Document c= DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(d.openStream());
			NodeList playlists = c.getElementsByTagName("song");
			for(int i = 0; i < playlists.getLength(); i++)
			{
				if(playlists.item(i) instanceof Element)
				{
					Element song = (Element)playlists.item(i);
					// URI artist
					URI uri =  new com.krikelin.spotifysource.URI(song.getAttribute("href"));
					uri.setTitle(song.getElementsByTagName("title").item(0).getFirstChild().getNodeValue());
					// URI artist
					URI artist =  new com.krikelin.spotifysource.URI("spotify:underfined");
					artist.setTitle(song.getElementsByTagName("artist").item(0).getFirstChild().getNodeValue());
					// URI artist
					URI album =  new com.krikelin.spotifysource.URI("spotify:underfined");
					album.setTitle(song.getElementsByTagName("album").item(0).getFirstChild().getNodeValue());
					
					
					SimpleEntry entry = new SimpleEntry(this, null, uri, artist, album, album);
					entries.add(entry);
					
				}
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.mContents = entries;
		return entries;
	}
	
	@Override
	public void render(URI args, Object... result) {
		// TODO Auto-generated method stub
		addPage("Overview", new Overview(this,getContext()));
	}

}
