package com.krikelin.spotifysource.widgets;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
	private Stack<ISPEntry> previous = new Stack<ISPEntry>();
	private ISPEntry currentEntry;
	private Stack<ISPEntry> forward = new Stack<ISPEntry>();
	public RadioStream(Activity act, SpotifyWindow mContext) {
		super(act, mContext);
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
				int middle = getWidth()/2;
				int relX = e.getX() -middle;
				// draw middle entry
				// Create entries for previous entry
				for(int i=0; i < forward.size(); i++){
					int startX = (i*height) ;
					int endX = (i+1)*height;
					
					if(relX <endX   &&relX > startX ){
						
						previous.push(RadioStream.this.currentEntry);
						RadioStream.this.currentEntry = forward.pop();
					
						currentEntry.play();
						
						// if the item were the last one, raise on latest listener
						if(onChangeListener!= null){
							onChangeListener.onLastItemClicked(RadioStream.this, (ISPEntry)currentEntry);
						}
					}
				}
				// Create entries for previous entry
				for(int i=0; i < previous.size(); i++){
					int startX= (-i-1)*height+middle ;
					int endX =(-i)*height+middle;
					if(relX <startX   &&relX > endX ){	
						forward.push(RadioStream.this.currentEntry);
						RadioStream.this.currentEntry = previous.pop();
						
						currentEntry.play();
						
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
		
		Color fore_color = this.getContext().getSkin().getForeColor();
		this.getContext().getSkin().drawText(entry.getUri().getTitle(), fore_color, g, minus_x+getHeight()/2, this.getHeight() - g.getFont().getSize() - 50, true);
		if(entry.getCover() != null){
			g.drawImage(entry.getCover(), minus_x + this.getHeight() /2, 2, minus_x - 20, minus_x - 20, null);
			
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
		drawEntry(0,g,currentEntry);
		// Create entries for previous entry
		for(int i=0; i < previous.size(); i++){
			minus_x -= height;
			if(minus_x < 0){
				drawEntry(minus_x, g, previous.get(i));
				
			}
			
		}
		// Create entries for previous entry
		
		for(int i=0; i < forward.size(); i++){
			minus_x += height;
			if(minus_x > 0){
				drawEntry(minus_x, g, forward.get(i));
				
			}
			
		}
		g.translate(-(middle-height/2), 0);
		
	}

	public Stack<ISPEntry> getPrevious() {
		return previous;
	}

	public void setPrevious(Stack<ISPEntry> previous) {
		this.previous = previous;
	}

	public ISPEntry getCurrentEntry() {
		return currentEntry;
	}

	public void setCurrentEntry(ISPEntry currentEntry) {
		this.currentEntry = currentEntry;
	}

	public Stack<ISPEntry> getForward() {
		return forward;
	}

	public void setForward(Stack<ISPEntry> forward) {
		this.forward = forward;
	}
	@Override
	public ISPEntry playNext() {
		// TODO Auto-generated method stub
		previous.push(this.currentEntry);
		this.currentEntry = forward.pop();
		
		return currentEntry;
	}
	@Override
	public ISPEntry playPrevious() {
		// TODO Auto-generated method stub
		forward.push(this.currentEntry);
		this.currentEntry = previous.pop();
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
