import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';

import { ToolbarFabService } from '../../services/toolbar-fab/toolbar-fab.service';
import { BreadcrumbService } from '../../services/breadcrumbs/breadcrumbs.service';
import { GenericTableService } from '../../services/generic-table/generic-table.service';
import { TABLE_ROUTE, BREADCRUMB, environment } from '../../../environments/environment';
import { HEADERS, routesWeb } from '../../../environments/environment.prod';
import { DiscountService } from '../../services/discount/discount.service';

import { DialogFormFilterService } from '../../services/filter/dialog-form-filter.service';
import { Subscription } from 'rxjs';
import { Information } from '../../util/date';
import { LocalStorageService } from '../../services/local-sotorage/localstorage.service';
import { DataService } from '../../services/data.service';

@Component({
  selector: 'app-discount',
  templateUrl: './discount.component.html',
  styleUrls: ['./discount.component.css']
})
export class DiscountComponent implements OnInit,OnDestroy {

  titles:string[]=[];
  headers:any[];
  tabs:any[];
  infoData:any[];
  countDiscount:any;

  toolbar:any;

  private filterEvent:Subscription;
  filters:any;
  endDate:string;
  startDate:string;
  nameAuthor:string;
  proveedor:string;
  bandera:boolean=true;
  selectable = true;
  removable = true;

  JsonLocalStorage:any={};

  showSaveUpdate: boolean = false;

  constructor(
    private _toolbar: ToolbarFabService,
    private _breadcrumb: BreadcrumbService,
    private _dataService: DataService,
    private _discount: DiscountService,
    private _dialogFilterService:DialogFormFilterService,
    private _localStorageService:LocalStorageService,
    private route: Router,
    private _table: GenericTableService ) { 

    this._dataService.setIsLoadingEvent(true);

    this.showSaveUpdate = this._localStorageService.getRolUserRead() == environment.ROL_USER_READ ? false : true;
    this._toolbar.setRolUserRead(this.showSaveUpdate);      

   	this._breadcrumb.setRouteText( {title:BREADCRUMB.DISCOUNT,arrow:false} );
    this._toolbar.getAddEvent().subscribe( button => this.goAdminDiscount(button) );
    this._toolbar.setVisible( this.route.url.toString() );
    let nameStorage = this._localStorageService.getVarLocalStorage( this.route.url.toString() );
    
    this.JsonLocalStorage = JSON.parse(localStorage.getItem(nameStorage));
    if(this.JsonLocalStorage == null){
        this._table.getTableHeadersJSON( HEADERS.DISCOUNTS ).subscribe( headers => {
          let arrayHeader:any=[];
          this._table.setHeadersActive(headers);
          this._table.setHeadersInactive( arrayHeader );
          this.loadInfo();
        });
    }else{
      this._table.setHeadersActive(this.JsonLocalStorage.actives);
      this._table.setHeadersInactive( this.JsonLocalStorage.inactives );
      this.loadInfo();
    }



    this._table.getColumnsGroups()
                    .subscribe( columns => this.changeColumns() );
 
      this.filterEvent = this._dialogFilterService.getFiltersDialog().subscribe( (filters:any) => {

          this.filters = filters;

          this.proveedor = filters.proveedor == null || filters.proveedor == "" ? null : filters.proveedor;
          this.startDate = filters.fechainiciodesde == null || filters.fechainiciodesde == "" ? null : Information.getDateString(new Date(filters.fechainiciodesde));
          this.endDate = filters.fechainiciohasta == null || filters.fechainiciohasta == "" ? null : Information.getDateString(new Date(filters.fechainiciohasta));
          this.nameAuthor = filters.autor == null || filters.autor == "" ? null : filters.autor;
      
          if(this.filters['fechainiciodesde'] != null && this.filters['fechainiciodesde'] != ""){
              let fechaStart = new Date(this.filters['fechainiciodesde']).toISOString();
              this.filters['fechainiciodesde']=fechaStart;
          }

          if(this.filters['fechainiciohasta'] != null && this.filters['fechainiciohasta'] != ""){
              let fechaEnd = new Date(this.filters['fechainiciohasta']).toISOString();
              this.filters['fechainiciohasta']=fechaEnd;
          }
          
          this._table.setParamsDiscount(this.filters);
          this.bandera=false;
          this.loadInfo();
      });

  }

  ngOnInit() {
  }

  goAdminDiscount( event ){
    this.route.navigate([`${routesWeb.HOME}/${routesWeb.ADMIN_DISCOUNT}`]);
  }
 
  loadInfo( page = 0 ){
    if(this.bandera){
      this._table.setParamsDiscountNull();
    }

    this._dataService.setIsLoadingEvent(true);
    this._table.getInfoTable(TABLE_ROUTE.DISCOUNTS,HEADERS.DISCOUNTS,page.toString() )
                    .subscribe( (resp:any) => {
                      
                      this.changeColumns();
                      this.titles = resp.infoTable.titles;                    
                      this.headers = resp.infoTable.headers;
                      this.infoData = resp.infoTable.infoData;                    
                      this.infoData= this.infoData.map(function(x){               
                        x.typeDiscount = (x.typeDiscount=='D'?'Descuento':'Beneficio') ;
                         x.cost=(x.typeDiscount=='Descuento'?'N/A':!x.cost?'No':'Si');
                          return x;});                                     
                      this.infoData.forEach( (element,index) => {
                          let fecha1 = Information.convertTime(element.notificationTime);
                          let fecha2 = Information.convertTime(element.publicationTime);
                          
                          let arrayFecha1 = Information.formatTime(fecha1.split(' '));
                          let arrayFecha2 = Information.formatTime(fecha2.split(' '));

                          this.infoData[index].notificationTime = arrayFecha1;
                          this.infoData[index].publicationTime = arrayFecha2;                                                 

                      }); 
                      this.changeColumns();
                      if( resp.infoTable.infoData.length == 0 && this.filters ){
                        this.infoData = [];
                        this.countDiscount = 0 ;
                        if( this.filters.fechainicio && this.filters.fechafin  && !this.filters.autor && !this.filters.titulo) {
                          this._dataService.setGeneralNotificationMessage('No se registró información en el periodo de fechas solicitado');
                          return;
                        }
                        this._dataService.setGeneralNotificationMessage('No se encontró ninguna coincidencia con los parámetros ingresados');
                        return;
                      }
                      this._discount.getCountItems(this.filters)
                                        .subscribe( (count:any) => {
                                          this.countDiscount = count.filas;
                                          this._dataService.setIsLoadingEvent(false);
                                        }, error => {
                                            this._dataService.setIsLoadingEvent(false);
                                        });
                              

                    },error =>{this._dataService.setIsLoadingEvent(false)
                    },()=> this._dataService.setIsLoadingEvent(false)
  )};

  selectItem( item ){
    this._discount.setDiscount( item );
    this.route.navigate([`${routesWeb.HOME}/${routesWeb.ADMIN_DISCOUNT}`]);
  }

  changeColumns(){
    let nameStorage = this._localStorageService.getVarLocalStorage( this.route.url.toString() );
     let columns = JSON.parse(localStorage.getItem(nameStorage));
    
    if(columns){
      this.titles  = columns.actives.map(x => x.title);
      this.headers = columns.actives.map(x => x.id);
      this._table.setHeadersActive( columns.actives );
      this._table.setHeadersInactive( columns.inactives );
    }
  }

  remove(filter): void {
    this.filters[filter] = null;
    if ( filter == 'proveedor' ) this.proveedor = null;
    if ( filter == 'autor' ) this.nameAuthor = null;
    if ( filter == 'fechainiciodesde' ) {
      this.startDate = null;
      this.endDate = null;
      this.filters['fechainiciodesde'] = null;
      this.filters['fechainiciohasta'] = null;
    }
    this._table.setParamsDiscount(this.filters);
    this.bandera=false;
    this.loadInfo();
  }


  ngOnDestroy() {
    this.filterEvent.unsubscribe();
  }
}
