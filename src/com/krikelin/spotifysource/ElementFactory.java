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

import java.awt.*;

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
