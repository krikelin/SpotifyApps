README
======
Spotify Apps is an special spotify client like application in Java, whilst not being an clone, is going to be an base platform to enginer 3rd party mashup relating to Spotify. The mashup comes in java packages and Spotify Apps platform should host an generic GUI style and guideline across all Spotify Apps and interfacing with the Spotify client for playback. This project is going to replace Spotify Ultra, Spobbler and Spotiapps since the projects were to exhausing to work with an host, and creating an clone of the official client in order to realise the dream of an spotify extension system didn’t be an reliable solution.
Requirement specification
------------------------------

* Ability to play spotify songs
* ability to make playlists **(see note n1)
* ability to view artists, album and toplists
* Ability to extend the app with new views
* Ability to change the theme of the app

* Ability to make this as MediaChrome later

** n1 : Playlist handling will be done through an HTTP playlist api, which are not ready for public usage yet. Until this, we will exclude this feature in favor for the most core, we would like to not use the libspotify hook with Java no more than for the streaming handling, as it tend to mess up with managed code. 

Sprints
===================

sprint 1: [Friday 28th - Sunday 31th]
---------------------------------------------------------------------

Target:
	Create the baseline for the app, with the view system in the app and 
	infrastructure. 
	
	
Actions:
	* Created base classes

	* Creating an class standard for all items inside Spotify Source infrastructure:
		* View system is based on Swing.
		* ISPPart - Denotates an part of the Spotify Source instracture and should have an public pointer to the main window.
		* ISPElement - Element is derived from this class, and is inherited swing/awt components.
		* ISPEntry - Playable items in Spotify views.

	* Theme system based on one (1) image file,first row of pixels is reserverd as 'palette', where each pixel in the row
	represents an color of the theme. In this way, we don't suffer from dealing	with hiliarius xml files.
		[may be an optional feature to add when allneccary features is implemented and there is resources over] 

	* Creating the Spotify UI components as following:
		Specialised Table view.

	* Created the outline of the extension infrastructure, derived classes 'Activity', not unlike Android infrastructure.
	
Obstacles:
	* Drawing remarks with flickering. Not all objects are behaving correctly. Mostly a problem by newbie experience of the framework.

	* Playlist entries wont show up, paint method never invoked 
		[fixed on 2011-07-30 midnight, problem was some overriden methods, fixed by connecting to superclass]

Difference from the .NET work:
	{ First of all, I would NEVER go back to .NET after having tested this! }
	Pros:
		* Reliability, efficiency
		Development is far better from .NET. Dev environment (eclipse) is MUCH stable than VS 2010 (express) and very few freezes
		occur inside the app and in the development environment.
		* Portability, maintaiblie
		.NET suffer from platform isolation, feeling that the software will be bound to Microsoft products, which means that heavy
		programming hours will not give any points for other platforms.
		* Spotify support
		Spotify strongely dislike .NET, more appriciation may be experienced as an result, Java knowledge is asked by Spotify.
	Changes:
		* No messed view system 
		All views are components of external java classes inherit Activity, an android like infrastructure. Much easier to maintain.
	Cons:
		* Drawing flicker there is flickering with the drawing
		