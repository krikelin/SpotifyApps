package com.krikelin.spotifysource;

import java.util.ArrayList;
/**
 * An SPElementGroup hosts child entries which fusionates
 *  with the host
 * @author Alexander
 *
 */
public interface SPElementGroup extends SPElement {
	public ArrayList<SPElement> getElements();
	
}
