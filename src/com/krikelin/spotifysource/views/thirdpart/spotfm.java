package com.krikelin.spotifysource.views.thirdpart;

import com.krikelin.spotifysource.Activity;
import com.krikelin.spotifysource.URI;
import com.krikelin.spotifysource.WebView;

public class spotfm extends Activity {

	@Override
	public Object onLoad(URI args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Spotfm";
	}

	@Override
	public void render(URI args, Object... result) {
		// TODO Auto-generated method stub
		String userName = args.getParameter();
		
		addPage("SpotFM", new WebView("http://www.spofm.net/", this, getContext(), false));
		super.render(args, result);
	}

}
