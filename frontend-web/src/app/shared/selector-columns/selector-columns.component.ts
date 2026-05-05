import { Component, OnInit, Inject } from '@angular/core';
import {  MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { Subscription } from 'rxjs';
import { LocalStorageService } from '../../services/local-sotorage/localstorage.service';
import { Router } from '@angular/router';

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

constructor(@Inject(MAT_DIALOG_DATA) public data: any,
  public dialogRef:MatDialogRef<SelectorColumnsComponent>,
  private _localStorageService:LocalStorageService,
  private _router:Router) {
  this.activas= data.actives;
  this.inactivas= data.inactives;

  let nameStorage = this._localStorageService.getVarLocalStorage( this._router.url.toString() );

  if (nameStorage != null) {
    localStorage.setItem(nameStorage,(`{"actives":${JSON.stringify(this.activas)},"inactives":${JSON.stringify(this.inactivas)}}`) );
  }

}
ngOnInit() {
  
}

cancelDialog(){

  let nameStorage = this._localStorageService.getVarLocalStorage( this._router.url.toString() );

  if (nameStorage != null) {
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


