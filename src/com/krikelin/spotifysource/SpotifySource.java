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

import org.lobobrowser.main.PlatformInit;


public class SpotifySource {

	public static void main(String[] args)
	{
		// This optional step initializes logging so only warnings
		// are printed out.
		try {
			PlatformInit.getInstance().initLogging(false);
			PlatformInit.getInstance().init(false, false);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// This step is necessary for extensions to work:
		

		// This optional step initializes logging so only warnings
		// are printed out.
		try { 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		// This step is necessary for extensions to work:
		

		SpotifyWindow mainWindow = new SpotifyWindow();
		mainWindow.setVisible(true);
		
		 
		
	}
} 
