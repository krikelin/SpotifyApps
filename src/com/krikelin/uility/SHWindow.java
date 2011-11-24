package com.krikelin.uility;

import java.awt.Frame;
import java.awt.Toolkit;
import sun.awt.*;

import javax.swing.JFrame;
import javax.swing.JWindow;
import javax.swing.WindowConstants;

import org.jawin.COMException;
import org.jawin.FuncPtr;
import sun.awt.windows.WComponentPeer;
import org.jawin.ReturnFlags;
public class SHWindow extends JFrame {
	

	public static long getHWnd(Frame f) {
	   return f.getPeer() != null ? ((WComponentPeer) f.getPeer()).getHWnd() : 0;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final long  CS_DROPSHADOW = 0x00020000;
	public SHWindow() {
		try{
			//setDefaultLookAndFeelDecorated(false);
			long hwnd= getHWnd(this);
		//	System.load("C:\\Windows\\USER32.DLL");
	    //    FuncPtr msgBox = new FuncPtr("C:\\Windows\\USER32.DLL", "SetClsasLng");
	    //    msgBox.invoke_I(0, "Hello From a DLL", "From Jawin", 0, ReturnFlags.CHECK_FALSE);
		}catch(Exception e){
			e.printStackTrace();
		}

	}
}
