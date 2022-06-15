import { Component, OnInit } from '@angular/core';
import {ArtistSvcService } from '../artist-svc.service'
import { Subscription } from 'rxjs';
// declare var window:any; 

@Component({
  selector: 'app-categoriesmodel',
  templateUrl: './categoriesmodel.component.html',
  styleUrls: ['./categoriesmodel.component.css']
})
export class CategoriesmodelComponent implements OnInit {


  formModal:any;
   
  artworkId:any;

  constructor(private artistSvc:ArtistSvcService) {
   
   }

  ngOnInit(): void {
   
  }

  openWindow(){
    this.formModal.show();
  }
  closeWindow(){
    this.formModal.hide();
  }
}
