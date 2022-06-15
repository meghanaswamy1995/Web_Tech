import { Component, OnInit } from '@angular/core';
import {ArtistSvcService } from '../artist-svc.service'
import { ConnectableObservable, Subscription } from 'rxjs';

declare var window:any;

@Component({ 
  selector: 'app-info',
  templateUrl: './info.component.html',
  styleUrls: ['./info.component.css']
})
export class InfoComponent implements OnInit {

  clickEventSubscription:Subscription;
  artistInfo:any;
  artistId:any;
  infoObj:any;
  showOutput:boolean=false;
  artistBasic:string='';
  biography:string='';
  nationality:string='';
  artistArtwork:any;
  artworksList:any=[];
  formModal:any;
  showCatLoad:boolean=false;
  artworksNull:boolean=false;
  artistsNull:boolean=false;

  showInfoSpinner:Boolean=false; 
  selectedImgdate:string='';
  selectedImgimg:string='';
  selectedImgtitle:string=''; 
  showGeneBody:boolean=false; 
  categoriesNull:boolean=false;
  genes:any;

  constructor(private artistSvc:ArtistSvcService) { 
    this.clickEventSubscription=this.artistSvc.getclickEvent().subscribe(($event)=>{
      this.artistId=$event.currentTarget.id;  
      console.log("taget: "+this.artistId); 
      this.showOutput=false; 
      this.getArtistInfo();
    });
    this.clickEventSubscription=this.artistSvc.getClearEvent().subscribe(()=>{
      this.showOutput=false;
      this.artworksNull=false; 
      this.showInfoSpinner=false; 
      
    });
    this.clickEventSubscription=this.artistSvc.getSubmitClearEvent().subscribe(()=>{
      this.showOutput=false; 
      this.artworksNull=false;    
      this.showInfoSpinner=false;  
    });
  }
 
  ngOnInit(): void {
    this.formModal= new window.bootstrap.Modal(
      document.getElementById("categoriesWindow") 
    ) 
  }

  openWindow($event:any){ 
    let id=$event.target.id;
    console.log(id);
    this.formModal.show();
    this.getCategory(id);
    this.showGeneBody=false;  
    this.showInfoSpinner=false;
    this.categoriesNull=false;
  }
  closeWindow(){
    this.formModal.hide();
  }

getCategory(artid:string){ 
  let catInfo:any;
  this.showGeneBody=false;
  this.categoriesNull=false; 
  //this.showInfoSpinner=false; 
  for(let result of this.artworksList){
    if(result[2]==artid){
      this.selectedImgimg=result[3];
      this.selectedImgdate=result[0];
      this.selectedImgtitle=result[1];
    }
  }
  this.showCatLoad=true;

  this.artistSvc.getCategories(artid).subscribe(data=>{
    this.categoriesNull=false;
    catInfo=data;
    if(catInfo._embedded.genes==''){  
      this.categoriesNull=true;
    }
    this.genes=catInfo._embedded.genes;
     this.showGeneBody=true; 
     this.showCatLoad=false; 
  }); 
}

  getArtistInfo(){
    let artwork:any;
    let artworkImg:string='';
    let artworkDate:string='';
    let artworkTitle:string='';
    let artworkId:string='';
    let artworkStr:any=[];
    this.artworksList=[]; 
    this.artworksNull=false;
    this.showInfoSpinner=true;
    this.showOutput=false;

    this.artistSvc.getInfo(this.artistId).subscribe(data=>{  
        this.artistInfo=data;
         this.biography=this.artistInfo.biography; 
        if(this.biography == null){   
            this.biography="";
          }   
        this.artistBasic=this.artistInfo.name+ " ("+this.artistInfo.birthday+" - "+this.artistInfo.deathday+")";
          this.nationality=this.artistInfo.nationality; 
    });
   

    this.artistSvc.getArtworks(this.artistId).subscribe(data=>{ 
        artwork=data;
        this.artistArtwork=artwork._embedded.artworks;
        if(this.artistArtwork==''){
        this.artworksNull=true;
        }

        for(let result of this.artistArtwork){
          artworkStr=[]; 
          artworkDate=result.date;
          artworkTitle=result.title;
          artworkId=result.id;
          artworkImg=result._links.thumbnail.href; 
          artworkStr.push(artworkDate);
          artworkStr.push(artworkTitle);
          artworkStr.push(artworkId);
          artworkStr.push(artworkImg); 
          this.artworksList.push(artworkStr); 
        }
        this.showInfoSpinner=false; 
        this.showOutput=true;
    });
   
  }

}
