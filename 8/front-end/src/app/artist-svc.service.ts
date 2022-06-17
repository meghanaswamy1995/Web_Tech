import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders, HttpParams } from '@angular/common/http';
import { BehaviorSubject, Observable,Subject } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class ArtistSvcService { 
  private subject=new Subject<void>();
  private subject2=new Subject<void>();
  private subject3=new Subject<void>();

  constructor(private htttpClient:HttpClient) { }


  sendSubmitClearEvent(){ 
    this.subject3.next(); 
  }
  getSubmitClearEvent(){
    return this.subject3.asObservable(); 
  }

  sendClearEvent(){ 
    this.subject2.next(); 
  }
  getClearEvent(){
    return this.subject2.asObservable();
  }

  sendClickEvent($event:any){
    this.subject.next($event);
  }
  getclickEvent():Observable<any>{
    return this.subject.asObservable();
  } 

  getArtists(val:string){ 
    
    //let url="http://localhost:8080/search/artists/"+val+"/"; 
    let url="http://webapp-353423.wl.r.appspot.com/search/artists/"+val+"/"; 
   
    return this.htttpClient.get(url) 
  }

  getInfo(id:string){
    ///get/artist/:id/
    //let infourl="http://localhost:8080/get/artist/"+id+"/"; 
    let infourl="http://webapp-353423.wl.r.appspot.com/get/artist/"+id+"/"; 
    return this.htttpClient.get(infourl)  
  }

  getArtworks(artistId:string){
    //let artworkurl="http://localhost:8080/get/artist/artwork/"+artistId+"/"; 
    let artworkurl="http://webapp-353423.wl.r.appspot.com/get/artist/artwork/"+artistId+"/";
    return this.htttpClient.get(artworkurl) 
  }

  getCategories(artId:string){
    //let categoryurl="http://localhost:8080/get/artwork/"+artId+"/"; 
    let categoryurl="http://webapp-353423.wl.r.appspot.com/get/artwork/"+artId+"/";
    return this.htttpClient.get(categoryurl); 
  } 
}