package com.krikelin.spotifysource.views;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.krikelin.spotifysource.ISPEntry;
import com.krikelin.spotifysource.SPActivity;
import com.krikelin.spotifysource.SPContentView;
import com.krikelin.spotifysource.SPListView;
import com.krikelin.spotifysource.SPScrollPane;
import com.krikelin.spotifysource.SPTableModel;
import com.krikelin.spotifysource.SimpleEntry;
import com.krikelin.spotifysource.SpotifySearch;
import com.krikelin.spotifysource.SpotifyWindow;
import com.krikelin.spotifysource.URI;

public class lastfm_profile extends SPActivity {
	/*public class Song extends SimpleEntry{
		public Song(SPActivity mActivity, SPContentView mContentView, URI mUri,
				URI mAuthorUri, URI mCollectionUri, URI mPlaylistUri) {
			super(mActivity, mContentView, mUri, mAuthorUri, mCollectionUri, mPlaylistUri);
			// TODO Auto-generated constructor stub
		}
		private String title;
		private String artist;
		private String album;
		private String isrc;
		private String user;
		private URI artist_uri;
		
		private URI album_uri;
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getArtist() {
			return artist;
		}
		public void setArtist(String artist) {
			this.artist = artist;
		}
		public String getAlbum() {
			return album;
		}
		public void setAlbum(String album) {
			this.album = album;
		}
		public String getIsrc() {
			return isrc;
		}
		public void setIsrc(String isrc) {
			this.isrc = isrc;
		}
		public String getUser() {
			return user;
		}
		public void setUser(String user) {
			this.user = user;
		}
		public URI getArtist_uri() {
			return artist_uri;
		}
		public void setArtist_uri(URI artist_uri) {
			this.artist_uri = artist_uri;
		}
		public URI getAlbum_uri() {
			return album_uri;
		}
		public void setAlbum_uri(URI album_uri) {
			this.album_uri = album_uri;
		}
		
	}*/
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1759023472425051339L;
	ArrayList<SimpleEntry> last_played_songs = new ArrayList<SimpleEntry>();
	private ArrayList<ISPEntry> user_songs;
	public class Overview extends SPContentView{

		public Overview(SPActivity activity, SpotifyWindow mContext) {
			super(activity, mContext);
			
			SPListView listView = new SPListView(new SPTableModel(user_songs), mContext);
			SPScrollPane sp = new SPScrollPane(lastfm_profile.this, listView);
			// TODO Auto-generated constructor stub
			add(sp);
		}

		/**
		 * 
		 */
		private static final long serialVersionUID = -9204578967946885219L;

	
		
	}
	protected void fetchSongs(String user){
		SpotifySearch ss = new SpotifySearch();
		try {
			this.user_songs = ss.getSongs("http://ws.audioscrobbler.com/2.0/?method=user.getrecenttracks&user=%s&api_key=b25b959554ed76058ac220b7b2e0a026",user, this, null);
			
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
		
	}
	@Override
	public Object onLoad(URI args) {
		// TODO Auto-generated method stub
		fetchSongs(args.getParameter());
		return null;
	}
	private String lastfm_user;
	@Override
	public String getTitle(){
		return String.format("Lastfm User profile (%s)",lastfm_user);
	}
	@Override
	public void render(URI args, Object... result) {
		// TODO Auto-generated method stub
	//	addPage("Overview",this.new Overview(this, getContext()));
		this.lastfm_user=args.getParameter();
		addPage("Play history", this.new  Overview(this, getContext()));
		//addPage("Friends",this.new  Overview(this, getContext()));
		
	}

}
