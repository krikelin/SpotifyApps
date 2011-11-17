package com.krikelin.spotifysource;

import java.awt.Dimension;
import java.net.URL;

import javax.swing.JComponent;

import org.lobobrowser.gui.FramePanel;
import org.lobobrowser.ua.NavigationEvent;
import org.lobobrowser.ua.NavigationListener;
import org.lobobrowser.ua.NavigationVetoException;
/***
 * SPWebPanel, based on lobo's web browser
 * @author Alexander
 *
 */
public class SPWebBrowser extends FramePanel implements SPWidget {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1328418311417201897L;
	private SpotifyWindow context; // Spotify Window
	public static final String substitute = "http://localhost:6310/";
	/**
	 * Creates url from the spotilink
	 * @param url
	 * @return
	 */
	private URI substitute(URL url){
		String c = url.toString().substring(substitute.length());
		return new URI(c.replace(":", "/"));
	}
	public SPWebBrowser(String url,SpotifyWindow window,JComponent parent, int height){
		super();
		if(height > -1){
			setPreferredSize(new Dimension(640,height));
		}
		this.addNavigationListener(new NavigationListener() {
			@Override
			public void beforeWindowOpen(NavigationEvent arg0)
					throws NavigationVetoException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeNavigate(NavigationEvent arg0)
					throws NavigationVetoException {
				// TODO Auto-generated method stub
				if(arg0.getURL().toString().startsWith(substitute)){
					context.navigate(substitute(arg0.getURL()));
				}
				
			}
			
			@Override
			public void beforeLocalNavigate(NavigationEvent arg0)
					throws NavigationVetoException {
				// TODO Auto-generated method stub
				
			}
		});
		parent.add(this);
		try{
			
			this.navigate(url);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	@Override
	public SpotifyWindow getContext() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public SPOnClickListener getOnClickListener() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setOnClickListener(SPOnClickListener listener) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLabel(String label) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void setID(int id) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public JComponent findViewById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}
