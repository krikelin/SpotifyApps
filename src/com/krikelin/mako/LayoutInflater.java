package com.krikelin.mako;

import java.awt.Component;
import java.awt.Container;
import java.lang.reflect.Method;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 * Inflates application layout from XML
 * @author Alexander
 *
 */
public class LayoutInflater {
	/**
	 * Inflates an layout to an component
	 * @param xmlDocument
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public Component inflate(Document xmlDocument) throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		/**
		 * Create the adult component
		 */
		Element d = xmlDocument.getDocumentElement();
	
		return inflateChild(d);
	}
	public Component inflateChild(Element child) throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		/**
		 * Get the view's name and create an corresponding instance
		 */
		String name = child.getNodeName(); 									 // Get the name of the node
		Component host = (Component)Class.forName(name).newInstance();  // Instansiate the component
		
		/**
		 * Set the physical attributes to the component by the namespace property: (http://segurify.com/ns/layout)
		 * 
		 */
		Method[] properties =  host.getClass().getDeclaredMethods(); // Get all properties

		for(Method property : properties)
		{
			/****
			 * If the property name is starting with set, set the property to the according
			 * value
			 */
			if(property.getName().startsWith("set"))
			{
				String attribute = property.getName().replaceFirst("^get","");
				/**
				 * Invoke the property on the child
				 */
				Class<?> parameter  = property.getParameterTypes()[0];
				
				// Get parameter value
				String value = child.getAttributeNS("http://segurify.com/ns/layout",attribute.toLowerCase());
				/**
				 * Get class of first argument
				 */
				try
				{
					Object instance = (Object)parameter.newInstance();
					/**
					 * If the parameter is an string, pass it
					 */
					if(instance instanceof String)
					{
						property.invoke(host, value);
						continue;
					}
					if(instance instanceof Integer)
					{
						property.invoke(host,Integer.valueOf(value));
						continue;
					}
					// Otherwise serialize an instance of an complex class
					Class<?> c = instance.getClass();
					// Split the values as an passing array
					String[] parameterTypes = value.split(",");
					// TODO Fix things here when we can
				//	for(int i=0; i < c.getConstructtors().get)
					//Class<?> types = c.getConstructors()[0].getParameterTypes();
				}
				catch(Exception e)
				{
					
				}
				
			}
			
		}
		/***
		 * If the element is representing an container item (boxes, lists etc) grab it's children as subitems
		 * 
		 */
		if(host instanceof Container)
		{
			
			for(int i=0; i < child.getChildNodes().getLength(); i++)
			{
				try
				{
					Element elm = (Element)child.getChildNodes().item(i);
					((Container)host).add(inflateChild(elm));
				}
				catch(Exception e)
				{
					
				}
			}
		}
		return null;
	}
}
