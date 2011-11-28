package com.krikelin.spotifysource.views.thirdpart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.code.jspot.Results;
import com.google.code.jspot.Spotify;
import com.google.code.jspot.Track;
import com.krikelin.spotifysource.SimpleEntry;
import com.krikelin.spotifysource.Activity;
import com.krikelin.spotifysource.SPWebBrowser;
import com.krikelin.spotifysource.URI;
import com.krikelin.spotifysource.WebView;
import com.krikelin.spotifysource.widgets.RadioStream;

public class echofi extends Activity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4111461138795742470L;
	SPWebBrowser browser;
	RadioStream radioStream;
	private ArrayList<com.krikelin.spotifysource.SimpleEntry> entries = new ArrayList<com.krikelin.spotifysource.SimpleEntry>();
	private String artist;
	public ArrayList<com.krikelin.spotifysource.SimpleEntry> fetchNewSongs() throws JSONException, MalformedURLException, IOException{
		entries = new ArrayList<SimpleEntry>();
		for(int x=0; x < 10; x++){
			// Get entries
			String c = ("http://echofiapp.com/go.php?artist=" + URLEncoder.encode(artist, "ISO-8859-1"));
			InputStream conn = new URL(c).openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn));
			StringBuilder sb = new StringBuilder();
			while(true){
				try{
					String line = br.readLine();
					if (line == null)
						break;
					sb.append(line);
				}
				catch(Exception e){
					break;
				}
			}
			
			String data =sb.toString();
			JSONObject object = new JSONObject(data);
			JSONArray tracks = object.getJSONObject("response").getJSONArray("songs");
			for(int i=0; i < tracks.length();i++){
				JSONObject track = tracks.getJSONObject(i);
				String title = track.getString("title");
				String artist =track.getString("artist_name");
				Spotify spot = new Spotify();
				Results<Track> result = spot.searchTrack(artist,title);
				if(result.getTotalResults() > 0){
					SimpleEntry entry = new SimpleEntry(this, null, result.getItems().get(0).getId());
					entries.add(entry);
				}
			}
		}
		return entries;
	}
	@Override
	public Object onLoad(URI arg0) {
		// TODO Auto-generated method stub
		try{
			artist = arg0.getParameter();
			for(int x=0; x < 10; x++){
				// Get entries
				String c = ("http://echofiapp.com/go.php?artist=" + URLEncoder.encode(artist, "ISO-8859-1"));
				InputStream conn = new URL(c).openStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(conn));
				StringBuilder sb = new StringBuilder();
				while(true){
					try{
						String line = br.readLine();
						if (line == null)
							break;
						sb.append(line);
					}
					catch(Exception e){
						break;
					}
				}
				
				String data =sb.toString();
				JSONObject object = new JSONObject(data);
				JSONArray tracks = object.getJSONObject("response").getJSONArray("songs");
				for(int i=0; i < tracks.length();i++){
					JSONObject track = tracks.getJSONObject(i);
					String title = track.getString("title");
					String artist =track.getString("artist_name");
					Spotify spot = new Spotify();
					Results<Track> result = spot.searchTrack(artist,title);
					if(result.getTotalResults() > 0){
						SimpleEntry entry = new SimpleEntry(this, null, result.getItems().get(0).getId());
						entries.add(entry);
					}
				}
			}
			return entries;
		}catch(Exception e){
			e.printStackTrace();
		}
		return entries;
		
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Echofi";
	}

	@Override
	public void render(URI args, Object... result) {
		// TODO Auto-generated method stub
		super.render(args, result);
		WebView wv = new WebView("http://echofiapp.com/", this, getContext(), true);
		RadioStream rs = wv.getRadioStream();
		rs.getPlaylist().addAll(entries);
		
		addPage("Echofi", wv);
		
	}

}
