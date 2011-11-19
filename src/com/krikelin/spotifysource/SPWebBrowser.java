package com.krikelin.spotifysource;

import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

import org.lobobrowser.clientlet.ClientletResponse;
import org.lobobrowser.gui.ContentEvent;
import org.lobobrowser.gui.ContentListener;
import org.lobobrowser.gui.FramePanel;
import org.lobobrowser.ua.NavigationEvent;
import org.lobobrowser.ua.NavigationListener;
import org.lobobrowser.ua.NavigationVetoException;
import org.lobobrowser.ua.NavigatorFrame;
/***
 * SPWebPanel, based on lobo's web browser
 * @author Alexander
 *
 */
public class SPWebBrowser extends FramePanel implements SPWidget {
	private  String readString(InputStream is) throws IOException {
		// TODO Auto-generated method stub
		
		DataInputStream i = new DataInputStream(is);
		StringBuilder sb = new StringBuilder();
		String c = "";
		while((c = i.readLine()) != null){
			sb.append(c);
		}
	
		
		return sb.toString();
		
	}
	private  String downloadString(String string) throws IOException {
		// TODO Auto-generated method stub
		InputStream is = new URL(string).openStream();
		DataInputStream i = new DataInputStream(is);
		StringBuilder sb = new StringBuilder();
		String c = "";
		while((c = i.readLine()) != null){
			sb.append(c);
		}
	
		
		return sb.toString();
		
	}
	private void writeString(String htmlContent, String string) throws IOException {
		// TODO Auto-generated method stub
		FileWriter fstream = new  FileWriter(string);
		BufferedWriter bw = new BufferedWriter(fstream);
		bw.write(htmlContent);
		bw.close();
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1328418311417201897L;
	private SpotifyWindow context; // Spotify Window
	public static final String substitute = "http://localhost:6310/";

	public void handleDocumentRendering(NavigatorFrame frame, ClientletResponse response){
		
	}
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
		this.context=window;
		setBorder(BorderFactory.createEmptyBorder());
		if(height > -1){
			setPreferredSize(new Dimension(640,-1));
		}
		addContentListener(new ContentListener() {
			
			@Override
			public void contentSet(ContentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
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
				else{
					if(!arg0.getURL().toString().contains("$")){
						// Download string
						String c = arg0.getURL().toString();
						try {
							String f = readString(getClass().getResourceAsStream("webHeader.html"));
							f = f.replace("${css}", getContext().getSkin().getCSS()).replace("${CSS}", getContext().getSkin().getCSS());
							f = f.replace("${content}", downloadString(c));
							String url = arg0.getURL().toString();
							String path = url.replace(url.split("/")[url.split("/").length-1] , "");
							f = f.replace("src=\"", "src=\""+ path);
							f = f.replace("href=\"", "href=\""+ path);
							
							writeString(f, "C:\\temppage$.html");
							navigate("C:\\temppage$.html");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} 
					
				}
				
			}
			
			@Override
			public void beforeLocalNavigate(NavigationEvent arg0)
					throws NavigationVetoException {
				// TODO Auto-generated method stub
				
			}
		});
		setBackground(getContext().getBackground());
		parent.add(this);
		try{
			
			this.navigate(url);
			
		}catch(MalformedURLException e){
			e.printStackTrace();
		}
		
	}
	@Override
	public SpotifyWindow getContext() {
		// TODO Auto-generated method stub
		return context;
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
