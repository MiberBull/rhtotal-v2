import { Injectable } from '@angular/core';
import { Subject } from "rxjs";
import { HEADERS } from '../../../environments/environment';

import { TreeService } from '../../services/tree/tree.service';
import { NotificationService } from '../../services/notification/notification.service';

@Injectable({
  providedIn: 'root'
})
export class SavedRoutesService {

  private app:string;

  private section = new Subject<string>();
  private selected:any;
  private appi:string;
  private sectionSelected:string;
  private sectionExcel:number;

  constructor(
             private _treeService:TreeService,
             private _notificationS: NotificationService
             ) { }

  setAppi(appi:string){
      this.app = appi;
  }

  getAppi(){
    return this.app;
  }

  setSection( root:string ){
    this.selected = SECCIONS.filter( (element ) => {
        return element.RUTA === root;
    });
    this.appi = this.selected[0].CLAVE;
    this.section.next(this.appi);
  }

  getSection(){
      return this.section.asObservable();
  }

  setSectionSelected(section:string){
      this.sectionSelected = section;
  }


  getSectionSelected(){
      return this.sectionSelected;
  }

  setSectionExcel(url:string){
    let seccion = SECCIONS.filter( element => {
      return element.RUTA == url;
    });

    this.sectionExcel = seccion[0].CLAVE;
  }
  getSectionExcel(){
    return this.sectionExcel;
  }

}

const SECCIONS = [
  {SECTION:'ROLES', RUTA:'/home/roles',CLAVE:8},
  {SECTION:'BANNERS', RUTA:'/home/banners',CLAVE:3},
  {SECTION:'NOTIFICATION', RUTA:'/home/notificaciones',CLAVE:4},
  {SECTION:'DISCOUNTS', RUTA:'/home/descuentos',CLAVE:2},
  {SECTION:'INSURANCE', RUTA:'/home/seguros',CLAVE:6},
  {SECTION:'CUSTOMER', RUTA:'/home/clientes',CLAVE:7},
  //TODO: Falta establecer la clave correcta para usuaios
  {SECTION:'USERS', RUTA:'/home/usuarios',CLAVE:1}
]
