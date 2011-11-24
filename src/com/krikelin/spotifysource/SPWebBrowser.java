package com.krikelin.spotifysource;

import java.awt.Dimension;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import org.lobobrowser.clientlet.ClientletResponse;
import org.lobobrowser.gui.ContentEvent;
import org.lobobrowser.gui.ContentListener;
import org.lobobrowser.gui.FramePanel;
import org.lobobrowser.ua.NavigationEvent;
import org.lobobrowser.ua.NavigationListener;
import org.lobobrowser.ua.NavigationVetoException;
import org.lobobrowser.ua.NavigatorFrame;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserCommandEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserListener;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserNavigationEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowOpeningEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowWillOpenEvent;

import com.krikelin.spotifysource.spml.Element;
/***
 * SPWebPanel, based on lobo's web browser
 * @author Alexander
 *
 */
public class SPWebBrowser extends JWebBrowser implements SPWidget {
	public static final String APP_HTML_PATH = SPContainer.EXTENSION_DIR+"\\html\\";
	private  String readString(InputStream is) throws IOException {
		// TODO Auto-generated method stub
		
		BufferedReader in = new BufferedReader( new InputStreamReader( is, "UTF8"));
		StringBuilder sb = new StringBuilder();
		String c = "";
		while((c = in.readLine()) != null){
			sb.append("\r\n"+c);
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
		PrintWriter fstream = new  PrintWriter((string));
		
	
		
			fstream.write(htmlContent);
	
		fstream.close();
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
	/**
	 * Provides an bridge between the web browser DOM events and the
	 * Activity
	 * @author Alexander
	 *
	 */
	public interface ActionEventHandler{
		/**
		 * Occurs when invoking onAction(<action>[,<param>..])
		 * @param action
		 * @param params
		 */
		public void onAction(String action, Object... params);
		public void onSpotifyAction(String url);
		
	}
	/****
	 * Listen to changes
	 */
	private void parseEvent(){
		
	}
	private ActionEventHandler actionEventListener;
	public SPWebBrowser(String url,SpotifyWindow window,JComponent parent, int height){
		super();
		final String urlx = url;
		this.context=window;
		setBorder(BorderFactory.createEmptyBorder());
		if(height > -1){
			setPreferredSize(new Dimension(640,-1));
		}
		
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				parseEvent();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				parseEvent();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				parseEvent();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				parseEvent();
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				parseEvent();
			}
		});
		addWebBrowserListener(new WebBrowserListener() {
			
			@Override
			public void windowWillOpen(WebBrowserWindowWillOpenEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowOpening(WebBrowserWindowOpeningEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WebBrowserEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void titleChanged(WebBrowserEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void statusChanged(WebBrowserEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void locationChanging(WebBrowserNavigationEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getNewResourceLocation().startsWith("spotify:")){
					if(getActionEventListener()!=null){
						getActionEventListener().onSpotifyAction(arg0.getNewResourceLocation());
						
					}
				}
			}
			
			@Override
			public void locationChanged(WebBrowserNavigationEvent arg0) {
				// TODO Auto-generated method stub
				
				if(arg0.getNewResourceLocation().endsWith(".html")){
					arg0.consume();
					String f = "";
					try {
						f = readString(getClass().getResourceAsStream("webHeader.html"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					f = f.replace("${css}", getContext().getSkin().getCSS()).replace("${CSS}", getContext().getSkin().getCSS());
					String content =  getHTMLContent();
					String url = arg0.getNewResourceLocation();
					String path = url.replace(url.split("/")[url.split("/").length-1] , "");
					
					// Get head
					try{
						String head = content.substring(content.indexOf("<script>")+8, content.indexOf("</script>"));
						f = f.replace("${js}", head);
						content = content.replace("<script>"+head+"</script>", "");
					}catch(Exception e){
						e.printStackTrace();
					}
					try{
						String head = content.substring(content.indexOf("<style>")+6, content.indexOf("</style>")+1);
						f = f.replace("${style}", head);
						content = content.replace("<style>"+head+"</style>", "");
					}catch(Exception e){
						e.printStackTrace();
					}
					f = f.replace("${content}", content);
					f = f.replace("~", path);
				
					setHTMLContent(f);
			
				}
			}
			
			@Override
			public void locationChangeCanceled(WebBrowserNavigationEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void loadingProgressChanged(WebBrowserEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void commandReceived(WebBrowserCommandEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		setBackground(getContext().getBackground());
		//parent.add(this);
		
		setBarsVisible(false);
			
		
			
		
		
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
	public ActionEventHandler getActionEventListener() {
		return actionEventListener;
	}
	public void setActionEventListener(ActionEventHandler actionEventListener) {
		this.actionEventListener = actionEventListener;
	}
}
