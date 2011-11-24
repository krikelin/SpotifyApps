package com.krikelin.spotifysource.views.thirdpart;

import com.krikelin.spotifysource.Activity;
import com.krikelin.spotifysource.URI;
import com.krikelin.spotifysource.WebView;

public class stalkify extends Activity {

	@Override
	public Object onLoad(URI args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Stalkify";
	}

	@Override
	public void render(URI args, Object... result) {
		// TODO Auto-generated method stub
		String url = "http://stalkify.com/";
		if(args.getParameter() != null){
			url+=args.getParameter();
		}
		addPage("Stalkify", new WebView(url, this, getContext()));
		super.render(args, result);
	}
	

}
