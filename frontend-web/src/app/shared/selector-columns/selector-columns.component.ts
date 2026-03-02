import { Component, OnInit, Inject } from '@angular/core';
import {  MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { Subscription } from 'rxjs';
import { LocalStorageService } from '../../services/local-sotorage/localstorage.service';
import { Router } from '@angular/router';
import { PaysheetService } from '../../services/paysheet/paysheet.service';

@Component({
  selector: 'app-selector-columns',
  templateUrl: './selector-columns.component.html',
  styleUrls: ['./selector-columns.component.css']
})

export class SelectorColumnsComponent implements OnInit {
MANY_ITEMS = 'MANY_ITEMS';
public inactivas : any;
public activas : any;
private nameStorage=""

subs = new Subscription();

JsonLocalStorage:any={};

  tabFintech:number;

constructor(@Inject(MAT_DIALOG_DATA) public data: any,
  public dialogRef:MatDialogRef<SelectorColumnsComponent>,
  private _localStorageService:LocalStorageService,
  private _router:Router,
  private _fintechService: PaysheetService) {
  this.activas= data.actives;
  this.inactivas= data.inactives;
  this.tabFintech = data.tabFintech;


  let nameStorage = this._localStorageService.getVarLocalStorage( this._router.url.toString() ); 
   
  localStorage.setItem(nameStorage,(`{"actives":${JSON.stringify(this.activas)},"inactives":${JSON.stringify(this.inactivas)}}`) );

  
  if (nameStorage == null) {
    if (this._router.url.toString() == '/home/adelantos/my-advance') {
      let nameStorage = this._localStorageService.getVarStorageFintechAdvance(this.tabFintech);

      localStorage.setItem(nameStorage, (`{"actives":${JSON.stringify(this.activas)},"inactives":${JSON.stringify(this.inactivas)}}`));


    } else if (this._router.url.toString() == '/home/adelantos/velo-cash') {
      let nameStorage = this._localStorageService.getVarStorageFintechVeloCash(this.tabFintech);
      localStorage.setItem(nameStorage, (`{"actives":${JSON.stringify(this.activas)},"inactives":${JSON.stringify(this.inactivas)}}`));

    }
  }


}
ngOnInit() {
  
}

cancelDialog(){

  let nameStorage = this._localStorageService.getVarLocalStorage( this._router.url.toString() );  

  if (nameStorage == null) {
    if (this._router.url.toString() == '/home/adelantos/my-advance') {
   
      let nameStorageFintech = this._localStorageService.getVarStorageFintechAdvance(this.tabFintech);
      this.JsonLocalStorage = JSON.parse(localStorage.getItem(nameStorageFintech));
      
    } else if (this._router.url.toString() == '/home/adelantos/velo-cash') {
        let nameStorageFintech = this._localStorageService.getVarStorageFintechVeloCash(this.tabFintech);
        this.JsonLocalStorage = JSON.parse(localStorage.getItem(nameStorageFintech));
    }
  }else {
   
     this.JsonLocalStorage = JSON.parse(localStorage.getItem(nameStorage));
  }  



  console.log("LACAL STOREGE DESDE SELECTOR ",nameStorage);
  console.log("LACAL STOREGE DESDE SELECTOR JSON ",this.JsonLocalStorage);

  this.dialogRef.close(
    {
      actives: this.JsonLocalStorage.actives,
      inactives: this.JsonLocalStorage.inactives
    }
  );
}

seleccionRows() {
  try {
    this.dialogRef.close(
      {
        actives: this.activas,
        inactives: this.inactivas
      }); 
  } catch (warn) {
    console.log( warn );
  }
};


}


