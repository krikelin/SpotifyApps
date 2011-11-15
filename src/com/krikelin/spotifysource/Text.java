package com.krikelin.spotifysource;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.TextArea;

import javax.swing.text.View;

import org.w3c.dom.Element;


public class Text extends ElementFactory {

	

	@Override
	public Component createControlFromElement(Element elm, String name,
			Container container, com.krikelin.spotifysource.View view) {
		// TODO Auto-generated method stub
		java.awt.TextArea txt = new TextArea(elm.getNodeValue(),3,3,0);
		
		container.add("tb"+elm.getAttribute("id"), txt);
		return txt;
	}

}
