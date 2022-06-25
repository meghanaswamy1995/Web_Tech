package com.example.artistsearch;

public class ArtistBio {
    private String artistId="";
    private String artistBday="";
    private String artistDday="";
    private String artistNation="";
    private String artistDescr="";
    private String artistName="";

    public ArtistBio(String artistId, String artistBday, String artistDday, String artistNation, String artistDescr, String artistName) {
        this.artistId = artistId;
        this.artistBday = artistBday;
        this.artistDday = artistDday;
        this.artistNation = artistNation;
        this.artistDescr = artistDescr;
        this.artistName = artistName;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getArtistBday() {
        return artistBday;
    }

    public void setArtistBday(String artistBday) {
        this.artistBday = artistBday;
    }

    public String getArtistDday() {
        return artistDday;
    }

    public void setArtistDday(String artistDday) {
        this.artistDday = artistDday;
    }

    public String getArtistNation() {
        return artistNation;
    }

    public void setArtistNation(String artistNation) {
        this.artistNation = artistNation;
    }

    public String getArtistDescr() {
        return artistDescr;
    }

    public void setArtistDescr(String artistDescr) {
        this.artistDescr = artistDescr;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
