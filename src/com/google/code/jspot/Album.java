/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.code.jspot;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an album
 * @author plamere
 */
public class Album extends SpotifyItem {
    private String artistName;
    private String artistID;
    private String[] availableTerritories = {};
    private Map<String, String> ids = new HashMap<String, String>();
    private int released = -1;


    Album(String id, String name, float popularity) {
        super(id, name, popularity);
    }

    /**
     * gets the ID of the artist
     * @return the ID
     */
    public String getArtistID() {
        return artistID;
    }

    /**
     * gets the name of the artist
     * @return the name
     */
    public String getArtistName() {
        return artistName;
    }

    /**
     * gets the available territories for the album
     * @return the available territories
     */
    public String[] getAvailableTerritories() {
        return availableTerritories;
    }

    /**
     * Gets the map of IDS
     * @return the IDS
     */
    public Map<String, String> getIds() {
        return ids;
    }

    void setAvailableTerritories(String[] s) {
        this.availableTerritories = s;
    }

    void addID(String id, String url) {
        ids.put(id, url);
    }

    void setArtistID(String artistID) {
        this.artistID = artistID;
    }

    void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String toString() {
        return  " " + super.toString() + "\n" +
                " artistName " + artistName + "\n" +
                " artistID " + artistID + "\n" +
                " territories " + availableTerritories.length + "\n" +
                " ids " + ids.size() + "\n" ;
    }

    /**
     * Gets the year of release
     * @return the year of release
     */
    public int getReleased() {
        return released;
    }

    void setReleased(int released) {
        this.released = released;
    }

}
