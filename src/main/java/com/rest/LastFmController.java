package com.rest;

import com.entity.Artist;
import com.entity.Track;
import com.service.LastFmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping(value = "lastFm")
public class LastFmController {

    @Autowired
    private LastFmService lastFmService;

    @GetMapping("/getTopArtists")
    public Flux<Artist> getTopArtists(@RequestParam("country") String country,
                                     @RequestParam(value = "pageNumber", required = false) Integer pageNumber) {
        if(pageNumber == null) {
            pageNumber = 1;
        }
        List<Artist> artistList = lastFmService.getTopArtists(country, pageNumber);
        return Flux.fromIterable(artistList);
    }

    @GetMapping("/getTopTracks")
    public Flux<Track> getTopTracks(@RequestParam("artistName") String artistName,
                                    @RequestParam(value = "pageNumber", required = false) Integer pageNumber) {
        if(pageNumber == null) {
            pageNumber = 1;
        }
        List<Track> trackList = lastFmService.getTopTracks(artistName, pageNumber);
        return Flux.fromIterable(trackList);
    }

}
