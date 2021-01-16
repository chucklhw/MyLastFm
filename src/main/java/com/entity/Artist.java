package com.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Artist {

    @JsonProperty("artistName")
    private String artistName;

    @JsonProperty("smallImage")
    private String smallImage;

    @JsonProperty("mediumImage")
    private String mediumImage;

    @JsonProperty("largeImage")
    private String largeImage;

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public String getMediumImage() {
        return mediumImage;
    }

    public void setMediumImage(String mediumImage) {
        this.mediumImage = mediumImage;
    }

    public String getLargeImage() {
        return largeImage;
    }

    public void setLargeImage(String largeImage) {
        this.largeImage = largeImage;
    }
}
