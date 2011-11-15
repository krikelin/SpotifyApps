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

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

public class SPScrollPane extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1209191005188974823L;
	public SPScrollPane(SPActivity activity){
		super();
		this.getViewport().setBackground(activity.getContext().getSkin().getBackgroundColor());
		setBorder(BorderFactory.createEmptyBorder());
	}
	public SPScrollPane(SPActivity activity,Component c){
		super(c);
		this.getViewport().setBackground(activity.getContext().getSkin().getBackgroundColor());
		
	}
}
