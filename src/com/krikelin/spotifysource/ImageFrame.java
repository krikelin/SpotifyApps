package com.krikelin.spotifysource;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ImageFrame extends JFrame {

/**
	 * 
	 */
	private static final long serialVersionUID = -2993526659454308435L;
	
	/**
	* Initializes a JFrame instance that displays an graphic image centered on
	* the JFrame.
	*
	* @param title
	* the title for display on the JFrame title bar
	* @param imageFileName
	* the absolute path to the image file or the relative path from
	* the execution directory. The image file must be of a format
	* compatible with <code>javax.swing.ImageIcon</code>.
	*/
	public ImageFrame(String title, String imageFileName) {
	setTitle(title);
	ImageIcon icon = new ImageIcon(imageFileName);
	Container pane = getContentPane();
	JLabel label = new JLabel(icon);
	pane.add(label);// adds to center region of default BorderLayout of pane'
	}
	public ImageFrame(String title, Image image) {
		setTitle(title);
		ImageIcon icon = new ImageIcon(image);
		Container pane = getContentPane();
		JLabel label = new JLabel(icon);
		pane.add(label);// adds to center region of default BorderLayout of pane'
		}
}