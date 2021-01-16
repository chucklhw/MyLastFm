import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ArtistsService } from '../../services/artists.service';

@Component({
  selector: 'app-tracks',
  templateUrl: './tracks.component.html',
  styleUrls: ['./tracks.component.css']
})
export class TracksComponent implements OnInit {

  artistName: string = "";
  topTrackList: any[] = [];
  pageNumber: number = 1;
  hasMore: boolean = true;

  constructor(private activatedRoute:ActivatedRoute,
              private artistsService:ArtistsService) { }

  moreTracks() {
    if(!this.hasMore) {
      return;
    }
    this.pageNumber++;
    this.artistsService.getTopTracks(this.artistName, this.pageNumber)
      .subscribe(
        data => {
          const tracks: any[] = data;
          if(tracks.length < 20) {
            this.hasMore = false;
            if(tracks.length == 0) {
              return;
            }
          }
          tracks.forEach(element=>{
            this.topTrackList.push(element);
          });
        },
        error => console.log(error)
      );
  }

  ngOnInit(): void {
    this.artistsService.sendMessage(true);
    this.artistName = this.activatedRoute.snapshot.paramMap.get('artistName') + "";
    this.pageNumber = parseInt(this.activatedRoute.snapshot.paramMap.get('pageNumber') + "");
    this.artistsService.getTopTracks(this.artistName, this.pageNumber)
      .subscribe(
        data => {
          this.topTrackList = data;
          if(this.topTrackList.length < 20) {
            this.hasMore = false;
          }
        },
        error => console.log(error)
      );
  }

}
