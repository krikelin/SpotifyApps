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
import java.awt.Graphics;
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
