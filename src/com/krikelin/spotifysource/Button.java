package com.krikelin.spotifysource;

import java.awt.Component;
import java.awt.Container;
import org.w3c.dom.Element;


public class Button extends ElementFactory {


	@Override
	public Component createControlFromElement(Element element, String name,
			Container container, com.krikelin.spotifysource.View view) {
		// TODO Auto-generated method stub
		SPButton c = new SPButton(view.getContext());
		c.setOnClickListener(new SPOnClickListener(){

			@Override
			public void Click(Object sender, Object args) {
				// TODO Auto-generated method stub
				
			}
			
		});
		container.add( c);
		return c;
	}

	

}
