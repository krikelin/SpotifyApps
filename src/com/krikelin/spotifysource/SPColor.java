package com.krikelin.spotifysource;

import java.awt.Color;

public class SPColor extends Color {

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
