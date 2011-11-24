package com.krikelin.spotifysource.views.thirdpart;

import com.krikelin.spotifysource.Activity;
import com.krikelin.spotifysource.URI;
import com.krikelin.spotifysource.WebView;

public class twittify extends Activity {

	@Override
	public Object onLoad(URI args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Twittify";
	}

	@Override
	public void render(URI args, Object... result) {
		// TODO Auto-generated method stub
		addPage("Twittify", new WebView("http://cow.neondragon.net/twittify/", this, getContext()));
		super.render(args, result);
	}

}
