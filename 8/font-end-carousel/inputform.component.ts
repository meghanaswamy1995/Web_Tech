

import { Component, OnInit } from '@angular/core';
import {ArtistSvcService } from '../../artist-svc.service'

@Component({
  selector: 'app-inputform',
  templateUrl: './inputform.component.html',
  styleUrls: ['./inputform.component.css']
})
export class InputformComponent implements OnInit {

  inputVal:string='';
  isinput:string='';
  inputsearchbar='';
  artistObj:any;
  artistList:any; 
  spinnerText:string=''; 
  idtag:string='';
  artistId:string='';
  jsonobj:any=[];
  isLoad:boolean=false;
  loadArtistList:boolean=false;  
  loadArtistInfo:boolean=false;
  artistsNull:boolean=false;
  pickedArtistid:any;
  hideuldiv:boolean=false;
testjson:any=[];

  constructor(private artistSvc:ArtistSvcService) { }

  onKeypressEvent(val:string){ 
    this.inputsearchbar=val;
    this.inputVal=val;
    this.isinput='T';
  }

  clearInput(){
    this.isinput=''; 
    this.inputsearchbar=''; 
    this.isLoad=false; 
    this.loadArtistList=false; 
    this.loadArtistInfo=false;
    this.artistSvc.sendClearEvent(); 
    this.artistsNull=false;
  } 

  searchArtist(){  
      let artimg:any;
      let artid:any;
      let val;
      //let jsonstr:any;
      let jsonstr:string="";
     
      this.isLoad=true;
      this.artistsNull=false; 

       let htmlString:string='';

      console.log('searching for artist: '+this.inputVal);  
 
      this.artistSvc.getArtists(this.inputVal).subscribe(data=>{
        this.artistObj=data;  
       
        this.artistList=this.artistObj._embedded.results; 

        for(let result of this.artistList){ 
          if(result.og_type=='artist'){
            artimg=result._links.thumbnail.href;
            if(artimg=="/assets/shared/missing_image.png"){
              artimg="assets/artsy_logo.svg"
            }
            if(result._links.self.href!=null){ 
              val=result._links.self.href.lastIndexOf('/');
              artid = result._links.self.href.substring(val + 1);  
            }
            jsonstr+=`{
              "title": "${result.title}",
              "image": "${artimg}",
              "id": "${artid}"
            },`   
 
          }  
        } 
         
        jsonstr=jsonstr.slice(0,-1);

        jsonstr='['+jsonstr+']';  
        console.log('jsonstr:  '+jsonstr);
        this.jsonobj = JSON.parse(jsonstr); 
        console.log('after'+this.jsonobj); 

        this.artistSvc.sendClearEvent(); 
        this.loadArtistList=true; 

      if(this.jsonobj==''){ 
        this.artistsNull=true;
      } 
      this.isLoad=false; 
      });   
       
  }

  getArtist($event:any){  
    let currentid=$event.currentTarget.id;
    console.log('id to change:'+currentid);
    const val = document.getElementById(currentid);
    console.log(val);
    if(val!=null){
      console.log('color '+this.pickedArtistid); 
      if(this.pickedArtistid!=''){
        const prevArt=document.getElementById(this.pickedArtistid);  
        if(prevArt!=null){
          prevArt.style.backgroundColor= "";
        }
      }
      val.style.backgroundColor='#112b3c'; 
    }
    this.pickedArtistid=currentid;
    this.artistSvc.sendClickEvent($event);
  }
  ngOnInit(): void {  
  
  }
   
}