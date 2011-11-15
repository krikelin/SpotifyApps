package com.krikelin.spotifysource.views;

import java.io.File;
import java.util.ArrayList;

import javax.swing.filechooser.FileFilter;

import com.krikelin.spotifysource.Album;
import com.krikelin.spotifysource.ISPEntry;
import com.krikelin.spotifysource.SPActivity;
import com.krikelin.spotifysource.SPContentView;
import com.krikelin.spotifysource.SPEntry;
import com.krikelin.spotifysource.SimpleEntry;
import com.krikelin.spotifysource.SpotifyWindow;
import com.krikelin.spotifysource.URI;

public class local_artist extends SPActivity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1902078139603824451L;
	
	public class  Overview extends SPContentView
	{

		public Overview() {
			super(local_artist.this,local_artist.this.getContext());
			// TODO Auto-generated constructor stub
			ArrayList<ISPEntry> entries = new ArrayList<ISPEntry>();
			
			String baseFolder = local_artist.this.getUri().getParameter();
			File file = new File("L:\\"+baseFolder+"\\Releases");
			for(File fil : file.listFiles())
			{
				ArrayList<ISPEntry> _entries = new ArrayList<ISPEntry>();
				for(File track : fil.listFiles())
				{
					if(track.getName().contains(".wav"))
					{
						SimpleEntry _track = new SimpleEntry(local_artist.this,this,new URI(track.getName(),"mp3:track:"+track.getAbsolutePath()),null,null,null);
						entries.add(_track);
					}
					
				}
				Album album = new Album(128,128,new URI(fil.getName(),"spotify:local_album:"+fil.getName()),fil.getName(),local_artist.this,_entries,local_artist.this.getContext(),this);
				add(album);
				
			}
		}

		
	}
	@Override
	public Object onLoad(URI args) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public void render(URI args, Object... result) {
		// TODO Auto-generated method stub
		
	}

}
