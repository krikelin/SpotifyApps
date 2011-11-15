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

import java.awt.Color;

public class SPColor extends Color {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4730551477667664522L;
	public SPColor(int r, int g, int b) {
		super(r, g, b);
		// TODO Auto-generated constructor stub
	}
	/**
	 * Changes the brightness of an color
	 * @param color
	 * @param percent
	 * @return
	 */
	public static int brightColor(int color, float percent)
	{
		int scaleR = 255 - color;
		return Math.round( percent  > 0 ? scaleR*percent : -percent*color);
	}
	/*
	 * Returns an brighter model of the color
	 */
	public static Color brighter(Color srcColor, float percent)
	{
		// Brighter difference
		
		
		
		int red = brightColor(srcColor.getRed(),percent);
		int green = brightColor(srcColor.getGreen(),percent);
		int blue = brightColor(srcColor.getBlue(),percent);
		return new Color(red,green,blue);
	}

}
