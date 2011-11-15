package com.krikelin.spotifysource.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import com.krikelin.spotifysource.BufferedContainer;
import com.krikelin.spotifysource.SPActivity;
import com.krikelin.spotifysource.SPContentView;
import com.krikelin.spotifysource.SPLabel;
import com.krikelin.spotifysource.SpotifyWindow;
import com.krikelin.spotifysource.URI;

public class app extends SPActivity {
	String app_description;
	/**
	 * 
	 */
	private static final long serialVersionUID = -4138666058898342503L;
	protected class Overview extends BufferedContainer {

		/**
		 * 
		 */
		private static final long serialVersionUID = -8899296963213710228L;

		public Overview(SPActivity activity, SpotifyWindow mContext) {
			super(mContext);
			// TODO Auto-generated constructor stub
			setLayout(new GridLayout(2, 1));
			BufferedContainer panel = new BufferedContainer(mContext);
			panel.setBackground(mContext.getSkin().getBackgroundColor());
			SPLabel text = new SPLabel(mContext, app_description);
			panel.setLayout(new BorderLayout());
			panel.setPreferredSize(new Dimension(640,120));
			((BorderLayout)panel.getLayout()).setHgap(10);
			((BorderLayout)panel.getLayout()).setVgap(10);
			add(panel,BorderLayout.NORTH);
			add(text,BorderLayout.CENTER);
			text.setText(description);
		}
		
	}
	public String description = "No description found";
	@Override
	public Object onLoad(URI args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void render(URI args, Object... result) {
		// TODO Auto-generated method stub
		addPage("Overview", new Overview(this,getContext()));
	}

}
