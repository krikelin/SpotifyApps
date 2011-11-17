package com.krikelin.spotifysource.widgets;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Stack;


import com.krikelin.spotifysource.*;

public class RadioStream extends SPContentView implements StreamContainer {
	
	/**
	 * 
	 */
	public interface OnChangeEventHandler
	{
		public void onLastItemClicked(RadioStream src, ISPEntry entry);
		public void onFirstItemClicked(RadioStream src, ISPEntry entry);
	}
	private OnChangeEventHandler onChangeListener;
	private static final long serialVersionUID = 6085856738073533924L;
	int position = 0;
	private ISPEntry currentEntry;
	private ArrayList<ISPEntry> playlist = new ArrayList<ISPEntry>();
	private SpotifyWindow context;
	public RadioStream(Activity act, SpotifyWindow mContext) {
		super(act, mContext);
		this.context = mContext;
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				int height = getHeight() ;
				int middle = getWidth()/2 - height /2;
				int relX = e.getX();
				// draw middle entry
				// Create entries for previous entry
				int firstEntry = -position*height  + middle;
				for(int i=0; i < playlist.size(); i++){
					
					
					
					int startX = firstEntry + i*height;
					int endX = firstEntry + i*height + height;
					if(relX <endX   &&relX > startX ){
						
						position  = i;
						RadioStream.this.currentEntry = playlist.get(i);
						try{
							context.playSong(currentEntry);
						}catch(Exception ex){
							
						}
						// if the item were the last one, raise on latest listener
						if( i == playlist.size() - 1){
							if(onChangeListener!= null){
								onChangeListener.onLastItemClicked(RadioStream.this, (ISPEntry)currentEntry);
							}
						}
					}
				}
				
				
				repaint();
			}
			
		});
		// TODO Auto-generated constructor stub
		addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	protected void drawEntry(int minus_x, Graphics g,ISPEntry entry){
		try{
			Color fore_color = this.getContext().getSkin().getForeColor();
			this.getContext().getSkin().drawText(entry.getUri().getTitle(), fore_color, g, minus_x+getHeight()/2, this.getHeight() - g.getFont().getSize() - 50, true);
			if(entry.getCover() != null){
				g.drawImage(entry.getCover(), minus_x + this.getHeight() /2, 2, minus_x - 20, minus_x - 20, null);
				
			}
		}catch(Exception e){
			
		}
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// TODO Auto-generated method stub
		int height = this.getHeight();
		
		int middle = getWidth()/2;
		g.translate(middle- height/2, 0);
		int plus_x = height;
		int minus_x = -height;
		// draw middle entry
		g.setColor(getContext().getSkin().getBackgroundColor().brighter());
		g.fillRect(0, 15, getHeight(), getHeight()-30);
		g.setColor(getForeground());
		
		// Create entries for previous entry
		for(int i=0; i < playlist.size(); i++){
			minus_x = ( -(position * height) + height * i);
			
			drawEntry(minus_x, g, playlist.get(i));
				
		
			
		}
		
		g.translate(-(middle-height/2), 0);
		
	}

	public ArrayList<ISPEntry> getPlaylist(){
		return playlist;
	}
	public ISPEntry getCurrentEntry() {
		return currentEntry;
	}

	public void setCurrentEntry(ISPEntry currentEntry) {
		this.currentEntry = currentEntry;
	}

	@Override
	public ISPEntry playNext() {
		// TODO Auto-generated method stub
		position +=1;
		this.currentEntry = playlist.get(position);
		
		return currentEntry;
	}
	@Override
	public ISPEntry playPrevious() {
		// TODO Auto-generated method stub
		position-=1;
		playlist.get(position);
		
		return super.playPrevious();
	}
	@Override
	public ISPEntry getCurrentPlayingEntry() {
		// TODO Auto-generated method stub
		return currentEntry;
	}
	public OnChangeEventHandler getOnChangeListener() {
		return onChangeListener;
	}
	public void setOnChangeListener(OnChangeEventHandler onChangeListener) {
		this.onChangeListener = onChangeListener;
	}

}
