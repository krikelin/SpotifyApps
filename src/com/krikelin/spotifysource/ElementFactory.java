package com.krikelin.spotifysource;

import java.awt.*;

import javax.swing.text.FlowView;
import javax.swing.text.View;

import org.w3c.dom.Element;


/**
 * Creates an element
 * @author Alexander
 *
 */ 
public abstract class ElementFactory {
	/***
	 * Creates an new component
	 * @param element the element
	 * @param name name of the component type
	 * @param container the container to create on
	 * @param view page of the component
	 * @return component The component
	 */
	public abstract Component createControlFromElement(Element element,String name,Container container,com.krikelin.spotifysource.View view);
}
