package com.krikelin.spotifysource;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.text.View;

import org.w3c.dom.Element;


public class ImageView extends ElementFactory {
	public class ImageBox extends java.awt.Canvas
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = -2226107120577730295L;

		@Override
		public void paint(Graphics g) {
			// TODO Auto-generated method stub
			super.paint(g);
		}
		
	}
	
	@Override
	public Component createControlFromElement(Element element, String name,
			Container container, com.krikelin.spotifysource.View view) {
		// TODO Auto-generated method stub
		ImageBox ib = new ImageBox();
		container.add("iv_"+element.getAttribute("id"), ib);
		
		return ib;
	}

}
