import { Component, OnInit } from '@angular/core';
import { ArtistsService } from '../../services/artists.service';

@Component({
  selector: 'app-artists',
  templateUrl: './artists.component.html',
  styleUrls: ['./artists.component.css']
})
export class ArtistsComponent implements OnInit {

  artistList: any[] = [];
  pageNumber: number = 0;
  countryName: string = "";
  isHiding: boolean = false;
  isFirstPage: boolean = true;
  isLastPage: boolean = true;

  constructor(private artistsService: ArtistsService) { }

  getTopArtists(): any {
    if(this.countryName == undefined || this.countryName == "") {
      this.artistList = [];
      this.pageNumber = 0;
      this.isFirstPage = true;
      this.isLastPage = true;
      return;
    }
    if(this.pageNumber == 0) {
      this.pageNumber++;
    }
    
    this.artistsService.getTopArtists(this.countryName, this.pageNumber)
      .subscribe(
        data => {
          this.artistList = data;
          if(this.artistList.length == 0 && this.pageNumber == 1) {
            this.isFirstPage = true;
            this.isLastPage = true;
            this.pageNumber = 0;
          }
          if(this.artistList.length > 0 && this.pageNumber == 1) {
            this.isFirstPage = true;
            this.isLastPage = false;
            return;
          }
          if(this.artistList.length == 0 && this.pageNumber > 1) {
            this.isLastPage = false;
            this.isLastPage = true;
            return;
          }
          
          if(this.artistList.length > 0 && this.pageNumber > 1) {
            this.isFirstPage = false;
            this.isLastPage = false;
            return;
          }
          if(this.artistList.length > 0 && this.artistList.length < 20 && this.pageNumber > 1) {
            this.isFirstPage = false;
            this.isLastPage = true;
            return;
          }
        },
        error => console.log(error));
  }

  nextPage() {
    if(this.countryName == undefined || this.countryName == "") {
      return;
    }
    this.pageNumber++;
    this.getTopArtists();
  }

  previousPage() {
    if(this.countryName == undefined || this.countryName == "") {
      return;
    }
    this.pageNumber--;
    this.getTopArtists();
  }

  gotoTrackPage(artistName: string): void {
    window.open(window.location.origin + `/#/getArtistTopTracksPage/${artistName}/1`);
  }

  ngOnInit(): void {
    this.artistsService.getMessageSource().subscribe(
      data => this.isHiding = data
    )
  }

}
