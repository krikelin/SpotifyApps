package com.krikelin.spotifysource;
import com.krikelin.spotifysource.widget.events.*;
import com.krikelin.spotifysource.*;
public interface SPWidget  {
	/**
	 * Gets the skin for the widget
	 * @return
	 */
 
 public SpotifyWindow getContext();

 
 /**
  * Sets the skin for the widget
  * @param skin
  */

 public SPOnClickListener getOnClickListener();
 public void setOnClickListener(SPOnClickListener listener);
 public void setLabel(String label);
 public String getLabel();
 
 
 
}
