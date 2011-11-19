package com.krikelin.spotifysource.views;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.krikelin.spotifysource.URI;
import com.krikelin.spotifysource.WebActivity;

public class web extends WebActivity {

	@Override
	public void render(URI args, Object... result) {
		// TODO Auto-generated method stub
		try {
			createActivityFromXHTML("http://localhost/spotifysource/home.xml");
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
		super.render(args, result);
	}

	@Override
	public Object onLoad(URI args) {
		// TODO Auto-generated method stub
		
		return null;
	}

}
