package com.krikelin.spotifysource.widgets;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.List;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.SwingUtilities;


import com.krikelin.spotifysource.*;

public class RadioStream extends SPContentView implements StreamContainer {
	
	/**
	 * 
	 */
	public interface OnChangeEventHandler
	{
		public void onLastItemClicked(RadioStream src, ISPEntry entry);
		public void onFirstItemClicked(RadioStream src, ISPEntry entry);
		public ArrayList<ISPEntry> fetchNewItems(RadioStream src);
	}
	private OnChangeEventHandler onChangeListener;
	private static final long serialVersionUID = 6085856738073533924L;
	int position = 0;
	private ISPEntry currentEntry;
	private ArrayList<ISPEntry> newItems = new ArrayList<ISPEntry>();
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
				if(e.getClickCount() != 2){
					return;
				}
				int height = getItemWidth() ;
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
							context.setCurrentPlaylist(RadioStream.this);
							context.playSong(currentEntry);
							
						}catch(Exception ex){
							ex.printStackTrace();
						}
						// if the item were the last one, raise on latest listener
						if( i >= playlist.size() - 1){
							if(onChangeListener!= null){
								onChangeListener.onLastItemClicked(RadioStream.this, (ISPEntry)currentEntry);
							}
							
							// Fetch new items
							Thread c = new Thread(new Runnable(){

								@Override
								public void run() {
									// TODO Auto-generated method stub
									newItems = getOnChangeListener().fetchNewItems(RadioStream.this);
									SwingUtilities.invokeLater(new Runnable(){

										@Override
										public void run() {
											// TODO Auto-generated method stub
											playlist.addAll(newItems);
											newItems.clear();
											repaint();
										}
										
									});
								}
								
							});
							c.start();
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
			int imageRatio =(int)(getItemWidth()*0.8);
			if(entry.getCover() != null){
				g.drawImage(entry.getCover(), minus_x + 20, 10,(int)(getItemWidth()*0.8) , (int)(this.getItemWidth()*0.8) , null);
					
			}
			Font oldFont = g.getFont();
			g.setFont(new Font("Tahoma",Font.BOLD,14));
			this.getContext().getSkin().drawText(SPSkin.stripe( entry.getUri().getTitle(),(int)(getItemWidth()*0.8),g.getFont(),g), fore_color, g, minus_x + 10, 10 + imageRatio + 16, true);
			g.setFont(new Font(oldFont.getFamily(),Font.PLAIN,oldFont.getSize()));
			this.getContext().getSkin().drawText(SPSkin.stripe( entry.getAuthorUri().getTitle(),(int)(getItemWidth()*0.8),g.getFont(),g), fore_color.darker(), g, minus_x + 10, 10 + imageRatio + 30, false);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public int getItemWidth(){
		return (int)(getHeight() *0.75);
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// TODO Auto-generated method stub
		int height =getItemWidth();
		
		int middle = getWidth()/2;
		g.translate(middle- height/2, 0);
		int plus_x = height;
		int minus_x = -height;
		// draw middle entry
		g.setColor(getContext().getSkin().getBackgroundColor().brighter());
		g.fillRect(0, 1, getItemWidth(), getHeight()-10);
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
		if( position >= playlist.size() - 1){
			if(onChangeListener!= null){
				onChangeListener.onLastItemClicked(RadioStream.this, (ISPEntry)currentEntry);
			}
		}
			
		repaint();
		return currentEntry;
	}
	@Override
	public ISPEntry playPrevious() {
		// TODO Auto-generated method stub
		position-=1;
		this.currentEntry = playlist.get(position);
		repaint();
		return this.currentEntry;
		
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
