package com.krikelin.spotifysource;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.text.View;

import org.w3c.dom.Element;


public class HBox extends ElementFactory {



	@Override
	public Component createControlFromElement(Element e, String name,
			Container container, com.krikelin.spotifysource.View view) {
		// TODO Auto-generated method stub
		java.awt.GridLayout   fl = new java.awt.GridLayout();
		fl.setRows(1);
		Container c = new Container();
		c.setLayout(fl);
		
		for(int i=0; i < e.getChildNodes().getLength(); i++)
		{
			fl.setColumns(i);
			Element elm = (Element)e.getChildNodes().item(i);
			try {
				Component comp = view.createElement(container, elm);
				c.add( comp);
			} catch (InstantiationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		return null;
	}

}
