package com.krikelin.spotifysource;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

public class SPScrollPane extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1209191005188974823L;
	public SPScrollPane(SPActivity activity){
		super();
		this.getViewport().setBackground(activity.getContext().getSkin().getBackgroundColor());
		setBorder(BorderFactory.createEmptyBorder());
	}
	public SPScrollPane(SPActivity activity,Component c){
		super(c);
		this.getViewport().setBackground(activity.getContext().getSkin().getBackgroundColor());
		
	}
}
