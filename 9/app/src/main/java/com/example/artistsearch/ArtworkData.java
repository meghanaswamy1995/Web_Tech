package com.example.artistsearch;

public class ArtworkData {
    private String artworkId;
    private String artworkName;
    private String artworkImage;

    public ArtworkData(String artworkId, String artworkName, String artworkImage) {
        this.artworkId = artworkId;
        this.artworkName = artworkName;
        this.artworkImage = artworkImage;
    }

    public String getArtworkId() {
        return artworkId;
    }

    public void setArtworkId(String artworkId) {
        this.artworkId = artworkId;
    }

    public String getArtworkName() {
        return artworkName;
    }

    public void setArtworkName(String artworkName) {
        this.artworkName = artworkName;
    }

    public String getArtworkImage() {
        return artworkImage;
    }

    public void setArtworkImage(String artworkImage) {
        this.artworkImage = artworkImage;
    }
}
