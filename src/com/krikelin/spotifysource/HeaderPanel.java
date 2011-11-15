package com.krikelin.spotifysource;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;



public class HeaderPanel extends BufferedContainer implements SPPart {

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
		
		g.drawImage(getContext().getSkin().getHeaderImage(),0,0,getWidth(),getHeight(),0,0,16,22,null);	
		for(Component d : getComponents())
		{
			g.translate(d.getLocation().x,d.getLocation().y);
			d.paint(g);
			g.translate(-d.getLocation().x,-d.getLocation().y);
		}
	}
	private SpotifyWindow mHost;
	/**
	 * Search listener
	 * @author Alexander
	 *
	 */
	
	private OnSearchListener mSearchListener;
	
	
	private TextField  mSearchBox;

	 public void update(Graphics g) 
     { 
          paint(g); 
          
     } 
	/**
	 * 
	 */
	 public abstract class NavigateListener
	 {
		 public static final int MODE_BACK = -1; 
		 public static final int MODE_FORWARD = 1;
		 public abstract void navigate(int mode);
	 }
	 private NavigateListener mNavigateHandler;
	private static final long serialVersionUID = 255442890532618037L;
	public HeaderPanel(SpotifyWindow context)
	{
		
		super(context);
		this.mHost = context;
		mSearchBox = new TextField();
		setBorder(BorderFactory.createEmptyBorder());
		
		setBackground(mHost.getSkin().getBackgroundColor());
		FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
		this.setLayout(fl );
		// Add back and forward button and an search box
		SPButton btn= new SPButton(getContext());
		btn.setText("<");
		btn.setPreferredSize(new Dimension(24,24));
		btn.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(mNavigateHandler!=null)
					mNavigateHandler.navigate(NavigateListener.MODE_BACK);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		this.add(btn);
		SPButton btnForward = new SPButton(getContext());
		btnForward.setText(">");
		btnForward.setPreferredSize(new Dimension(24,24));
		btnForward.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(mNavigateHandler!=null)
					mNavigateHandler.navigate(NavigateListener.MODE_FORWARD);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		add(btnForward);
		this.add(mSearchBox);
		setPreferredSize(new Dimension(40,40));
		mSearchBox.setPreferredSize(new Dimension(80,20));
		mSearchBox.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					// append spotify:search: to the beginning of the string
					// if it not are preceed with spotify: tag
					String query = mSearchBox.getText();
					if(!query.startsWith("spotify:"))
					{
						query="spotify:search:"+query;
					}
					// Invoke search
					HeaderPanel.this.mSearchListener.OnSearch(HeaderPanel.this,query);
					
					
				}
			}
		});
		repaint();
	}
	/**
	 * Gets the search query
	 * @return
	 */
	public String getSearchQuery()
	{
		return mSearchBox.getText();
		
	}
	/**
	 * Sets the handler for the search
	 * @param mSearchListener
	 */
	public void setSearchListener(OnSearchListener mSearchListener) {
		this.mSearchListener = mSearchListener;
	}
	
	/**
	 * Gets the handler for the search
	 * @return OnSearchListener the search listener
	 */
	public OnSearchListener getSearchListener() {
		return mSearchListener;
	}
	public void setContext(SpotifyWindow mHost) {
		this.mHost = mHost;
	}
	public SpotifyWindow getContext() {
		return mHost;
	}
	public void setNavigateHandler(NavigateListener mNavigateHandler) {
		this.mNavigateHandler = mNavigateHandler;
	}
	public NavigateListener getNavigateHandler() {
		return mNavigateHandler;
	}
}
