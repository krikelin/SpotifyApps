package com.krikelin.spotifysource.views;
import java.awt.GridLayout;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.krikelin.spotifysource.*;
import com.krikelin.spotifysource.widgets.RadioStream;
import com.krikelin.spotifysource.widgets.RadioStream.OnChangeEventHandler;
import com.sun.xml.internal.ws.util.xml.XmlUtil;
/**
 * Twittify app for Spotify App platform
 * @author Alexander
 *
 */
public class radio extends Activity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String query;
	public class Overview extends SPContentView{

		/**
		 * 
		 */
		private static final long serialVersionUID = -3704253664015811976L;
		/**
		 * Identify spotify link
		 * @param link
		 */
		public String identifySpotifyLink(String link){
			String startTag = "href=\""; // Start tag
		
			int startPos = link.indexOf(startTag+"http://open.spotify.com/"); // Get the starting position of the open.spotify.com
			int endPos = link.indexOf("\"",startPos); // Find the end of the link string
			String spotify_link = link.substring(startPos, endPos - (startPos + startTag.length()));
			return spotify_link;
			
		}
		RadioStream rs;
		public Overview(Activity activity, SpotifyWindow mContext) {
			super(activity, mContext);
			// TODO Auto-generated constructor stub
			
			// Create an buffered container with two rows, so we can create
			// an web control showing the twittify start page and an 
			// radio stream of the latest tweets
			BufferedContainer bc = new BufferedContainer(mContext);
			add(bc);
			
			// Set the grid layout
			bc.setLayout(new GridLayout(2,0));
			
			
			// Create the item buffer
			ArrayList<ISPEntry> entries =new ArrayList<ISPEntry>();
			
			// Traverse the fetched content and make an list
			NodeList nodes = readyDoc.getElementsByTagName("track");
			for(int i=0; i < nodes.getLength() && i < 3; i++){
				Element elm = (Element)nodes.item((int)(Math.random()*nodes.getLength()));
				try {
					ISPEntry _entry =
							new SimpleEntry(radio.this,
									Overview.this,
									elm.getAttribute("href"));
					entries.add(_entry);
				} catch (DOMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  catch (Exception e){
					
				}
			}
			
			// Create an browser that shows Twittifys start page
			SPWebBrowser sps = new SPWebBrowser("file://"+SPContainer.EXTENSION_DIR+
			"html/radio.html",
					mContext, this, 320);
			sps.navigate("file://"+SPContainer.EXTENSION_DIR+
			"html/radio.html");
			
			// Put it in an scroll pane
			SPScrollPane sp = new SPScrollPane(activity, sps);
		
			
			// Create the radio stream control
			rs = new RadioStream(activity,mContext);
			for(ISPEntry entry : entries){
				rs.getPlaylist().add(entry);
			}
			try{
				rs.setCurrentEntry(rs.getPlaylist().get(0));
			}catch(Exception e){
				
			}
			rs.setOnChangeListener(new OnChangeEventHandler() {
				
				@Override
				public void onLastItemClicked(RadioStream arg0, ISPEntry arg1) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onFirstItemClicked(RadioStream arg0, ISPEntry arg1) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public ArrayList<ISPEntry> fetchNewItems(RadioStream arg0) {
					ArrayList<ISPEntry> entries = new ArrayList<ISPEntry>();
					// TODO Auto-generated method stub
					if(query.equals("default")){
						query = "";
					}
					// Gets the parameter of the app, in our case an search query
					String dataURL;
					try {
						dataURL = (TWITTER_SEARCH_QUERY + "q=" + URLEncoder.encode(query, "ISO-8859-1")).replace("%2F", "%20") + "&page="+String.valueOf((int)(Math.random()*4));
						
						// Parse the feed into our format
						
						try {
							Document xmlDoc = DocumentBuilderFactory.newInstance()
									.newDocumentBuilder().parse(new URL(dataURL).
											openStream());
							NodeList nodes = xmlDoc.getElementsByTagName("track");
							for(int i=0; i < nodes.getLength() && i < 3; i++){
								Element elm = (Element)nodes.item((int)(Math.random()*nodes.getLength()));
								try {
									ISPEntry _entry =
											new SimpleEntry(radio.this,
													Overview.this,elm.getAttribute("href"));
									entries.add(_entry);
								} catch (DOMException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}  catch (Exception e){
									
								}
							}
						} catch (Exception e){
							e.printStackTrace();
						}
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					return entries;
				}
			});
			// Add the component
			bc.add(sp);
			bc.add(rs);
		}

		@Override
		public ISPEntry playNext() {
			// TODO Auto-generated method stub
			return rs.playNext();
		}

		@Override
		public ISPEntry playPrevious() {
			// TODO Auto-generated method stub
			return rs.playPrevious();
		}
		
	} 
	
	/**
	 * Twitter search
	 */
	public static String TWITTER_SEARCH_QUERY = "";
	static{
	
			TWITTER_SEARCH_QUERY = "http://ws.spotify.com/search/1/track?";
		
	}

	@Override
	public Object onLoad(URI arg0) {
		try {
			
			// TODO Auto-generated method stub
			query = arg0.getParameter(); 
			if(query.equals("default")){
				query = "";
			}
			// Gets the parameter of the app, in our case an search query
		
			String dataURL = (TWITTER_SEARCH_QUERY + "q=" + URLEncoder.encode(query, "ISO-8859-1")).replace("%2F", "%20") + "&page="+String.valueOf((int)(Math.random()*4));
		
			
			// Parse the feed into our format
			
			Document xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new URL(dataURL).openStream());
			
			return xmlDoc;
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			
		
		return null;
	}
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Radio";
	}

	/**
	 * The document we have downloaded
	 */
	private Document readyDoc; 
	@Override
	public void render(URI arg0, Object... arg1) {
		// TODO Auto-generated method stub
		// If the download were sucessfull, add it
		super.render(arg0, arg1);
		if(arg1[0] instanceof Document){
			this.readyDoc = (Document)arg1[0]; 
			addPage("Radio", new Overview(this,getContext())); // Create the first page
			// Instansiate the views here
			
		}
	}
	
}
