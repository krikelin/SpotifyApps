package com.krikelin.spotifysource;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class ListEntry extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 193703036632433996L;
	private String text ="";
	private SpotifyWindow context;
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		Color fgColor = getForeground();
		if(getText().equals("-")||getText().equals("")){
			g.setColor(getContext().getSkin().getBackgroundColor().darker());
			g.drawLine(0, getHeight()/2,getWidth(), getHeight()/2)	;
		}else if(getText().startsWith("#")){
			g.setColor(getContext().getSkin().getSelectionBg().darker().darker());
			g.drawLine(0, getHeight()-1,getWidth(), getHeight()-1)	;
			context.getSkin().drawText(text.replace("#", ""), getForeground().darker(), g, 10 , getHeight()/2+3, false);
		}else{
			g.setColor(fgColor);
			context.getSkin().drawText(text, getForeground(), g, 30 , getHeight()/2+4, false);
			//g.drawImage(getIcon(), 7, 2, 14, 14	, null);
		}
		
			
	}	
	private Image icon;
	public ListEntry(String text, SpotifyWindow context, Image icon){
		setText(text);
		this.context =context;
		setBackground(context.getSkin().getBackgroundColor());
		if(icon == null){
			icon = context.getSkin().getImageById(DefaultSkin.ICON_START+1);
		}
		setIcon(icon);
		if(icon != null){
			setIcon(getIcon());
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public SpotifyWindow getContext() {
		return context;
	}

	public Image getIcon() {
		return icon;
	}

	public void setIcon(Image icon) {
		this.icon = icon;
		
	}

	
}
