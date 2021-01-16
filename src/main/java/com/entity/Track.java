package com.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Track {

    @JsonProperty("trackName")
    private String trackName;

    @JsonProperty("playCount")
    private String playCount;

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getPlayCount() {
        return playCount;
    }

    public void setPlayCount(String playCount) {
        this.playCount = playCount;
    }
}
