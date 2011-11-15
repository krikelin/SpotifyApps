package com.krikelin.spotifysource;

public interface IMCPlaybackEventListener {
	/**
	 * Occurs on playback completed
	 * @param source
	 */
	void playbackCompleted(URI source);
}
