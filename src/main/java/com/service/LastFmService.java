package com.service;

import com.entity.Artist;
import com.entity.Track;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class LastFmService {

    private String WS = "http://ws.audioscrobbler.com/2.0/?";
    private String API_KEY = "7c456eef0a2e17c115203c283e286d9e";
    private int pageSize = 20;

    public List<Artist> getTopArtists(String country, int pageNumber) {
        String result = null;
        try {
            StringBuffer url = new StringBuffer(WS);
            String countryEncoded = URLEncoder.encode(country, StandardCharsets.UTF_8.toString());
            url.append("method=geo.gettopartists&country=").append(countryEncoded)
                    .append("&api_key=").append(API_KEY).append("&format=json")
                    .append("&page=").append(pageNumber).append("&limit=").append(pageSize);
            result = this.getHttpResult(url.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Artist> artistList = this.getArtistList(result);
        return artistList;
    }

    public List<Track> getTopTracks(String artistName, int pageNumber) {
        String result = null;
        try {
            StringBuffer url = new StringBuffer(WS);
            String artistNameEncoded = URLEncoder.encode(artistName, StandardCharsets.UTF_8.toString());
            url.append("method=artist.gettoptracks&artist=").append(artistNameEncoded)
                    .append("&api_key=").append(API_KEY).append("&format=json")
                    .append("&page=").append(pageNumber).append("&limit=").append(pageSize)
                    .append("&autocorrect=1");
            result = this.getHttpResult(url.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Track> trackList = this.getTrackList(result);
        return trackList;
    }

    private String getHttpResult(String fmUrl) throws IOException {
        URL url = new URL(fmUrl);
        URLConnection urlConnection = url.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
        httpURLConnection.setDoInput(true);
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setRequestProperty("Accept", "application/json");

        InputStream is = httpURLConnection.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);

        int numCharsRead;
        char[] charArray = new char[1024];
        StringBuffer sb = new StringBuffer();
        while ((numCharsRead = isr.read(charArray)) > 0) {
            sb.append(charArray, 0, numCharsRead);
        }
        isr.close();

        String result = sb.toString();
        return result;
    }

    private List<Artist> getArtistList(String artistStr) {
        List<Artist> artistList = new ArrayList<>();
        if(artistStr == null) {
            return artistList;
        }
        JSONObject jsonObject = new JSONObject(artistStr);
        if(!jsonObject.keySet().contains("topartists")) {
            return artistList;
        }
        JSONObject topartists = jsonObject.getJSONObject("topartists");
        if(topartists == null) {
            return artistList;
        }
        JSONArray artistArray = topartists.getJSONArray("artist");

        for (int i=0; i < artistArray.length(); i++) {
            JSONObject artistJson = artistArray.getJSONObject(i);
            Artist artist = new Artist();
            artist.setArtistName(artistJson.getString("name"));
            JSONArray imageArray = artistJson.getJSONArray("image");
            for (int j=0; j<imageArray.length(); j++) {
                JSONObject imageJson = imageArray.getJSONObject(j);
                if("small".equals(imageJson.getString("size"))) {
                    artist.setSmallImage(imageJson.getString("#text"));
                } else if("medium".equals(imageJson.getString("size"))) {
                    artist.setMediumImage(imageJson.getString("#text"));
                } else if("large".equals(imageJson.getString("size"))) {
                    artist.setLargeImage(imageJson.getString("#text"));
                }
            }
            artistList.add(artist);
        }

        return artistList;
    }

    private List<Track> getTrackList(String trackStr) {
        List<Track> trackList = new ArrayList<>();
        if(trackStr == null) {
            return trackList;
        }
        JSONObject jsonObject = new JSONObject(trackStr);
        if(!jsonObject.keySet().contains("toptracks")) {
            return trackList;
        }
        JSONObject topTrackJson = jsonObject.getJSONObject("toptracks");
        if(topTrackJson == null) {
            return trackList;
        }
        JSONArray trackArray = topTrackJson.getJSONArray("track");
        for (int i=0; i<trackArray.length(); i++) {
            Track track = new Track();
            JSONObject trackJson = trackArray.getJSONObject(i);
            track.setTrackName(trackJson.getString("name"));
            track.setPlayCount(trackJson.getString("playcount"));
            trackList.add(track);
        }
        return trackList;
    }

}
