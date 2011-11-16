package com.krikelin.uility;

import java.awt.Toolkit;
import sun.awt.*;
import javax.swing.JWindow;

import org.jawin.COMException;
import org.jawin.FuncPtr;
import org.jawin.ReturnFlags;
public class SHWindow extends JWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final long  CS_DROPSHADOW = 0x00020000;
	public SHWindow(){
		int hwnd = ((SunToolkit)Toolkit.getDefaultToolkit()).getNativeWindowHandle(this);
		FuncPtr msgBox = new FuncPtr("USER32.DLL", "SetClsasLng");
		msgBox.invoke_I(0, "Hello From a DLL", "From Jawin", 0, ReturnFlags.CHECK_FALSE);
	}
}
