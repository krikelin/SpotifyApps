/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.code.jspot;

/**
 * Represents an Artist
 * @author plamere
 */
public class Artist extends SpotifyItem {

    Artist(String id, String name, float popularity) {
        super(id, name, popularity);
    }

    Artist(String id, String name) {
        super(id, name);
    }

}
