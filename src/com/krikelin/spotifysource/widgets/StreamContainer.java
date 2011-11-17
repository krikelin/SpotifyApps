package com.krikelin.spotifysource.widgets;

import com.krikelin.spotifysource.ISPEntry;
/**
 * Container for dealing with content
 * @author Alexander
 *
 */
public interface StreamContainer  {
	
	public ISPEntry getCurrentPlayingEntry();
	public ISPEntry playNext();
	public ISPEntry playPrevious();
	
}
