import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ArtistsService {

  private getTopArtistsApi = "/lastFm/getTopArtists";

  private getTopTracksApi = "/lastFm/getTopTracks";

  private messageSource = new BehaviorSubject<boolean>(false);

  constructor(private http: HttpClient) { }

  getTopArtists(country: string, pageNumber: number): Observable<any> {
    let url = `${this.getTopArtistsApi}?country=${country}&pageNumber=${pageNumber}`;
    return this.http.get(url);
  }

  getTopTracks(artistName: string, pageNumber: number): Observable<any> {
    let url = `${this.getTopTracksApi}?artistName=${artistName}&pageNumber=${pageNumber}`;
    return this.http.get(url);
  }

  getMessageSource(): Observable<boolean> {
    return this.messageSource.asObservable();
  }

  sendMessage(flag: boolean) {
    this.messageSource.next(flag);
    this.messageSource.complete();
  }
}
