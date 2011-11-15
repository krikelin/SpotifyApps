 /*
 * Copyright (C) 2011 Alexander Forselius
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.krikelin.spotifysource;

import java.awt.Component;
import java.awt.Container;
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
