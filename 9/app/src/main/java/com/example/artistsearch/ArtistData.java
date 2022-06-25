package com.example.artistsearch;

public class ArtistData {
    private String artistId;
    private String artistName;
    private String artistImage;

    public ArtistData(String artistId, String artistName, String artistImage) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.artistImage = artistImage;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getArtistImage() {
        return artistImage;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setArtistImage(String artistImage) {
        this.artistImage = artistImage;
    }
}
