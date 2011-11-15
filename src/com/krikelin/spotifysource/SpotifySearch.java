/*
 * Copyright (C) 2011 Alexander Forselius
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.krikelin.spotifysource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 * Performs an simple search on Spotify
 * @author Alexander
 *
 */
public class SpotifySearch {
	/****
	 * We have contacted spotify about the issue with not being able to access songs from an artist xml
	 * with albums, and prefer an option like, with the message attached:
	 * Hello,

			I'm writting an open source spotify client called 'Spotify Source' and want let you to know that I'm need an ability to get a full xml file of an artist on the metadata api, including all album and it's tracks. Now I have to call several requests to the metadata API, which is not good for either mine and your side.
			
			For example, if you enter http://ws.spotify.com/lookup/1/?uri=http://open.spotify.com/artist/2FOROU2Fdxew72QmueWSUy&extras=trackdetail it gives nothing. I want you to make it so that you get the ordinary xml file of the artist, but also with tags like these:
			
			artist xmlns="http://www.spotify.com/ns/music/1">
			<name>Dr. Sounds</name>
			<albums>
			<album href="spotify:album:73acbjSikjUA6JkpokTS2j">
			<name>Twister</name>
			<artist href="spotify:artist:2FOROU2Fdxew72QmueWSUy">
			<name>Dr. Sounds</name>
			</artist>
			<released>2011</released>
			<!-- tracklist -->
			<tracks>
			<track href="spotify:track:48eXJ3IU5IXw7MKD10X7PK">
			 <name>Twister</name>
			 <artist>..</artist>
			 ...
			</track>
			....
			</tracks>
			<!-- End tracklist -->
			<availability>
			<territories/>
			</availability>
			</album>
			<album href="spotify:album:2GVe0LQL2cuGEsqTiHAaD8">
			<name>Flashback</name>
			<artist href="spotify:artist:2FOROU2Fdxew72QmueWSUy">
			<name>Dr. Sounds</name>
			</artist>
			<released>2011</released>
			<availability>
			<territories/>
			</availability>
			</album>
			<album href="spotify:album:39vXIDw9SlwYtwLFlaS1hc">
			<name>Transition</name>
			<artist href="spotify:artist:2FOROU2Fdxew72QmueWSUy">
			<name>Dr. Sounds</name>
			</artist>
			<released>2011</released>
			<!-- tracklist -->
			<tracks>
			<track href="spotify:track:48eXJ3IU5IXw7MKD10X7PK">
			 <name>Twister</name>
			 <artist>..</artist>
			 ...
			</track>
			....
			</tracks>
			<!-- End tracklist -->
			<availability>
			<territories>
			AD AE AF AG AI AL AM AN AO AQ AR AS AT AU AW AX AZ BA BB BD BE BF BG BH BI BJ BM BN BO BR BS BT BV BW BY BZ CA CC CD CF CG CH CI CK CL CM CN CO CR CU CV CX CY CZ DE DJ DK DM DO DZ EC EE EG EH ER ES ET FI FJ FK FM FO FR GA GB GD GE GF GG GH GI GL GM GN GP GQ GR GS GT GU GW GY HK HM HN HR HT HU ID IE IL IN IO IQ IR IS IT JM JO JP KE KG KH KI KM KN KP KR KW KY KZ LA LB LC LI LK LR LS LT LU LV LY MA MC MD ME MG MH MK ML MM MN MO MP MQ MR MS MT MU MV MW MX MY MZ NA NC NE NF NG NI NL NO NP NR NU NZ OM PA PE PF PG PH PK PL PM PN PR PS PT PW PY QA RE RO RS RU RW SA SB SC SD SE SG SH SI SJ SK SL SM SN SO SR ST SV SY SZ TC TD TF TG TH TJ TK TL TM TN TO TR TT TV TW TZ UA UG UM US UY UZ VA VC VE VG VI VN VU WF WS YE YT ZA ZM ZW
			</territories>
			</availability>
			</album>
	 * @param query
	 * @param mActivity
	 * @param mContentView
	 * @return
	 */
	@SuppressWarnings("unused")
	public ArrayList<ISPEntry> getArtist(String query, SPActivity mActivity,SPContentView mContentView)
	{
		ArrayList<ISPEntry> result = new ArrayList<ISPEntry>();
		String adress = query;
		
		Document d;
		try {
			d = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new URL(adress).openStream());
			d.getDocumentElement().normalize();
			
		
			NodeList albums = d.getElementsByTagName("album");
			for(int i=0; i <albums.getLength(); i++)
			{
				Element album = (Element)albums.item(i);
				ISPEntry _album = new SimpleEntry(mActivity,mContentView,new URI(album.getElementsByTagName("name").item(0).getFirstChild().getNodeValue(),album.getAttribute("href")),new URI("","spotify:underfined:a"),new URI("","spotify:underfined:a"),new URI("","spotify:underfined:a"));
				if(true)
				{
					_album.setReleaseType("Release");
				}
				else
				{
					_album.setReleaseType("Single");
					
					
				}
				/*
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ArrayList<ISPEntry> _songs = getSongs("http://ws.spotify.com/lookup/1/?uri="+album.getAttribute("href")+"&extras=trackdetail",mActivity,mContentView);
				_album.getAssets().addAll(_songs);
				String releaseType = "Album";
				if(_songs.size() <= 2)
				{
				_album.setReleaseType("Single");
				}
				if(_songs.size() >= 3 && _songs.size() <= 5)
				{
					_album.setReleaseType("EP");
				}*/
				
				if(album.getElementsByTagName("territories").item(0).hasChildNodes() )
				{
						result.add(_album);
				}
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<ISPEntry> getSongs(String url, String query,SPActivity mActivity,SPContentView mContentView) throws MalformedURLException, SAXException, IOException, ParserConfigurationException
	{
		ArrayList<ISPEntry> result = new ArrayList<ISPEntry>();
		query = URLEncoder.encode(query, "ISO-8859-1");
		Document d = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new URL(String.format(url, query)).openStream());
		d.getDocumentElement().normalize();
		// Get songs
		NodeList songs = d.getElementsByTagName("track");
		for(int i=0; i <songs.getLength(); i++)
		{
				
				Element song = (Element)songs.item(i);
				URI address = new URI("spotify:track:underfined");
				if(song.hasAttribute("href")){
					address= new URI(song.getAttribute("address"));
				} 
				URI title = new URI(song.getElementsByTagName("name").item(0).getFirstChild().getNodeValue(),song.hasAttribute("href") ? song.getAttribute("href") : "spotify:track:underfined");
				
				Element art = (Element)song.getElementsByTagName("artist").item(0);
				URI artist = new URI(art.getFirstChild().getNodeValue(),"spotify:artist:underfined");
				try{
					artist = new URI(art.getElementsByTagName("name").item(0).getFirstChild().getNodeValue(),art.hasAttribute("href") ? art.getAttribute("href"): "spotify:track:underfined");
				}catch(Exception e){
				}
				SimpleEntry se = new SimpleEntry(mActivity,mContentView,title,artist,null,null);
				try{
				se.setPopularity(Float.valueOf(song.getElementsByTagName("popularity").item(0).getFirstChild().getNodeValue()));
				}catch(Exception e){}
				try{
					se.setDuration(Math.round(Float.valueOf(song.getElementsByTagName("length").item(0).getFirstChild().getNodeValue())));
				}catch(Exception e){}
				se.setUri(address);
				result.add(se);
		}
		return result;
	}
}
