package com.krikelin.spotifysource;

import java.awt.GridLayout;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.lang.model.util.Elements;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.lobobrowser.gui.FramePanel;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.krikelin.spotifysource.widgets.RadioStream;
import com.krikelin.spotifysource.widgets.RadioStream.OnChangeEventHandler;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import com.sun.xml.internal.ws.util.xml.XmlUtil;

public abstract class WebActivity extends Activity {
	
	/**
	 * @from http://stackoverflow.com/questions/3300839/get-a-nodes-inner-xml-as-string-in-java-dom
	 * @param node
	 * @return
	 */
	public String innerXml(Node node) {
	    DOMImplementationLS lsImpl = (DOMImplementationLS)node.getOwnerDocument().getImplementation().getFeature("LS", "3.0");
	    LSSerializer lsSerializer = lsImpl.createLSSerializer();
	    NodeList childNodes = node.getChildNodes();
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < childNodes.getLength(); i++) {
	       sb.append(lsSerializer.writeToString(childNodes.item(i)));
	    }
	    return sb.toString(); 
	}
	/**
	 * Get ISPEntry from html table row
	 * @param host
	 * @param row
	 * @return
	 */
	public ISPEntry getISPEntryFromRow(SPContentView host,Element row){
		NodeList rows = row.getElementsByTagName("td");
		SimpleEntry se = new SimpleEntry(this, host, getURIEntryFromTable((Element)rows.item(0)), getURIEntryFromTable((Element)rows.item(1)), getURIEntryFromTable((Element)rows.item(2)), null);
		return se;
	}
	/**
	 * Gets URI from entry
	 * @param row
	 * @return
	 */
	public URI getURIEntryFromTable(Element td){
	
			// Get string
			String name = XmlUtil.getTextForNode(td);
			String uri = XmlUtil.getAttributeNSOrNull(td, "href", "");
			return new URI(name,uri);
		
	}
	public SPContentView createPage(String title, Element element) throws IllegalAccessException{
		// var{
			SPContentView view;
			NodeList childNodes;
			NodeList childRows;
			Element currentChild;
			int elm_count = 0;
			NodeList metaTags;
			
			ArrayList<JComponent> controlsToAdd = new ArrayList<JComponent>(); // Components to add
		// }
		// init{
			childNodes = element.getChildNodes();
			view = new SPContentView(this, getContext());
			
		// }
			
		// process{
			
			// Get META attributes
			
			for(int i=0; i < childNodes.getLength(); i++){
				// Create layout depending of amount elements involved
				
				if(childNodes.item(i) instanceof Element){
					
					currentChild = (Element)childNodes.item(i);
					
					// Switch pares
					if(currentChild.getTagName().equals("table")){
						// Get all contents before we do something
						childRows = currentChild.getElementsByTagName("tr");
						ArrayList<ISPEntry> entries = new ArrayList<ISPEntry>();
						for(int j=0; j < childRows.getLength(); j++) {
					
							entries.add(getISPEntryFromRow(view, (Element)childRows.item(j)));
							// Get entries
							
						}
						SPListView spListView = new SPListView(new SPTableModel(entries), mContext);
						SPScrollPane scv = new  SPScrollPane(WebActivity.this, spListView);
						controlsToAdd.add(scv);
						elm_count++;
					}
					// Create a web view
					if(currentChild.getTagName().equals("div")){
						// TODO Add web page factuality
						
						if(XmlUtil.getAttributeOrNull(currentChild, "class").equals("entry")){
							Element link = (Element)currentChild.getElementsByTagName("a").item(0);
							SPEntry entry = new SPEntry( null, mContext, this, new URI(XmlUtil.getTextForNode(link),XmlUtil.getAttributeOrNull(link, "href")));
							
									
							controlsToAdd.add(entry);
							elm_count++;
						}
						if(XmlUtil.getAttributeOrNull(currentChild, "class").equals("content")){
							elm_count+=1;
							// HTML content
							String htmlContent = innerXml(currentChild);
							try {
								writeString(htmlContent,"C:\\temppage.html");
								SPScrollPane p = new SPScrollPane(this);
								SPWebBrowser spb = new SPWebBrowser("C:\\temppage.html", getContext(), p, 320);
								p.add(spb);
								
								controlsToAdd.add(p);
								
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								throw new IllegalAccessException();
							}
						}
						
						// Radio view
						if(XmlUtil.getAttributeOrNull(currentChild, "class").equals("radio")){
							elm_count+=1;
							RadioStream rs = new RadioStream(this, getContext());
							String id = XmlUtil.getAttributeOrNull(currentChild, "id");
							rs.setName(id);
							
							// Get the radio's src
							
							rs.setOnChangeListener(new OnChangeEventHandler() {
								
								@Override
								public void onLastItemClicked(RadioStream src, ISPEntry entry) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void onFirstItemClicked(RadioStream src, ISPEntry entry) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public ArrayList<ISPEntry> fetchNewItems(RadioStream src) {
									// TODO Auto-generated method stub
									String address = meta.get(src.getName()+"_src");
									ArrayList<ISPEntry> entries = new ArrayList<ISPEntry>();
									try {
										Document xmlDoc = DocumentBuilderFactory.newInstance()
												.newDocumentBuilder().parse(new URL(address).
														openStream());
										NodeList nodes = xmlDoc.getElementsByTagName("entry");
										for(int i=0; i < nodes.getLength() && i < 3; i++){
											Element elm = (Element)nodes.item(i);
											try {
												ISPEntry _entry =
														new SimpleEntry(WebActivity.this,
																null,
																elm.getElementsByTagName("title").item(0).
																getFirstChild().getNodeValue());
												entries.add(_entry);
											} catch (DOMException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (Exception e){
												
											}
										}
									} catch (Exception e){
										e.printStackTrace();
									}
									return entries;
								}
							}); 
							controlsToAdd.add(rs);
						}
						
					}
				}
			}
		// }
			
		// finalize{
			// Now create a vertical container with all elements onboard
			if(elm_count > 0){
				if(elm_count > 1){
					BufferedContainer bc = new BufferedContainer(getContext());
					bc.setLayout(new GridLayout(elm_count, 0));
					// Add the controls
					for(int i=0; i < controlsToAdd.size(); i++){
						bc.add(controlsToAdd.get(i));
					}
					view.add(bc);
				}else{
					view.add(controlsToAdd.get(0));
				}
			}else{
					
				
			}
			
		// }
			
		// return{
			return view;
		// }
	}
	private void writeString(String htmlContent, String string) throws IOException {
		// TODO Auto-generated method stub
		FileWriter fstream = new  FileWriter(string);
		BufferedWriter bw = new BufferedWriter(fstream);
		bw.write(htmlContent);
		bw.close();
	}
	Hashtable<String,String> meta;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8382634550903633762L;
	Document xmlDoc;
	/**
	 * Creates activity from XHTML
	 * @param activity
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public void createActivityFromXHTML(String activity) throws SAXException, IOException, ParserConfigurationException{
		// var{
		SPContentView currentSection; // Current section
		
		NodeList nodes;
		NodeList metaTags;
		// }
		//
		/// initialization{
			xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(activity);
			
			meta = new Hashtable<String, String>();
			
			
			
		// }
		//
		// process{
			// Get Meta tags
			metaTags = xmlDoc.getElementsByTagName("meta");
			for(int i=0; i < metaTags.getLength(); i++){
				if((Element)metaTags.item(i) instanceof Element){
					Element metaTag = (Element)metaTags.item(i);
					String name = XmlUtil.getAttributeOrNull(metaTag, "name");
					String content = XmlUtil.getAttributeOrNull(metaTag, "content");
					meta.put(name, content);
				}
				
			}
			nodes = xmlDoc.getElementsByTagName("body");
			for(int i=0; i < nodes.getLength(); i++){
				Element element = (Element)nodes.item(i);
				String title = XmlUtil.getTextForNode(element.getElementsByTagName("h1").item(0));
				
				try {
					SPContentView c;
					c = createPage(title,element);
					addPage(title, c);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		// }
		
	}
}
