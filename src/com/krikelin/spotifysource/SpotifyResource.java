package com.krikelin.spotifysource;
import com.krikelin.spotifysource.view.*;
public class SpotifyResource {
	public URI Uri;
	public String Title;
	/**
	 * Creates an new Spotify Resource
	 * @param res
	 * @param Title
	 */
	public SpotifyResource(URI res,String Title)
	{
		Uri=res;
		this.Title=Title;
	}
}
