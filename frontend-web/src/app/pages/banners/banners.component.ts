import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import {MatDialog} from '@angular/material';

import { BreadcrumbService } from '../../services/breadcrumbs/breadcrumbs.service';
import { ToolbarFabService } from '../../services/toolbar-fab/toolbar-fab.service';
import { DialogFormsFilterComponent } from '../../components/dialog-forms-filter/dialog-forms-filter.component';
import { GenericTableService } from '../../services/generic-table/generic-table.service';
import { TABLE_ROUTE, BREADCRUMB } from '../../../environments/environment';
import { HEADERS, routesWeb, environment } from '../../../environments/environment.prod';
import { BannerService } from '../../services/banner/banner.service';
import { Subscription } from 'rxjs';
import { DialogFormFilterService } from '../../services/filter/dialog-form-filter.service';
import { Information } from '../../util/date';
import { DataService } from '../../services/data.service';
import { LocalStorageService } from '../../services/local-sotorage/localstorage.service';
import { error } from '@angular/compiler/src/util';

@Component({
  selector: 'app-banners',
  templateUrl: './banners.component.html',
  styleUrls: ['./banners.component.css']
})
export class BannersComponent implements OnInit,OnDestroy {

  tableData:any = {};

  filterEvent: Subscription;
  filters:any;
  endDate:string;
  startDate:string;
  nameAuthor:string;
  title:string;
  removable:boolean = true;
  activeFilter:boolean = false;
  JsonLocalStorage:any={};

  countBanner:number;


  idUserLogeado:number;
  showSaveUpdate:boolean=true;

  constructor(
    private _breadcrumb: BreadcrumbService,
    private _toolbar: ToolbarFabService,
    private _table: GenericTableService,
    private _localStorageService:LocalStorageService,                                 
    private _router:Router,
    private _bannerService: BannerService,
    private _dataService: DataService,
    private _dialogFilterService: DialogFormFilterService,
    private dialog: MatDialog,
    private route: Router) {

       //this._toolbar
      this.showSaveUpdate = this._localStorageService.getRolUserRead() == environment.ROL_USER_READ ? false : true;
      this._toolbar.setRolUserRead(this.showSaveUpdate);
      
      this._dataService.setIsLoadingEvent(true);


      this._breadcrumb.setRouteText( {title:BREADCRUMB.BANNER,arrow:false} );
      this._toolbar.setVisible( this._router.url.toString() );
      this._toolbar.getAddEvent().subscribe( click => this.goAdminBanners(click) );

      let nameStorage = this._localStorageService.getVarLocalStorage( this._router.url.toString() );
      this.JsonLocalStorage = JSON.parse(localStorage.getItem(nameStorage));
     
      if(this.JsonLocalStorage == null){
          this._table.getTableHeadersJSON( HEADERS.BANNERS )
            .subscribe( headers => {
                let arrayHeader:any=[];
                this._table.setHeadersActive( headers );
                this._table.setHeadersInactive( arrayHeader );
                this.loadInfoBanner();
          });
      }else{
          this._table.setHeadersActive( this.JsonLocalStorage.actives );
          this._table.setHeadersInactive( this.JsonLocalStorage.inactives );
          this.loadInfoBanner();
      }

      this._table.getColumnsGroups().subscribe( columns => {
      this.changeColumns();
    });

    this.filterEvent = this._dialogFilterService.getFiltersDialog().subscribe( (filters:any) => {

      this.filters = filters;
      console.log('filtro des',filters);

      this.title = filters.titulo == "" || filters.titulo == null ? null : filters.titulo;
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

      this._table.setParamsBanners(this.filters);
      this.activeFilter=true;
      this.loadInfoBanner();


    });

  }

  ngOnInit() {
  }

  goAdminBanners(event:string){
    this.route.navigate([`${routesWeb.HOME}/${routesWeb.ADMIN_BANNERS}`]);
  }

  loadInfoBanner( page = 0 ){
    if( !this.activeFilter ) {
      this._table.setParamsBannersNull();
    }
    
    this._table
        .getInfoTable( TABLE_ROUTE.BANNERS,HEADERS.BANNERS,page.toString() )
        .subscribe( (resp:any) => {
        this.tableData = resp.infoTable;        
        this.tableData.infoData =this.tableData.infoData.map(function(x){
            var fechaI = new Date(x.startDate);
            var fechaF = new Date(x.endDate);
            fechaI.setDate(fechaI.getDate());
            x.startDate = Information.getDateString(fechaI);
            x.endDate = Information.getDateString(fechaF);
            x.timePublication = Information.getTimeString(x.timePublication);
            x.notificationTime = Information.getTimeString(x.notificationTime);
            return x;
            
        },error=>    this._dataService.setIsLoadingEvent(false),
        ()=>    {this._dataService.setIsLoadingEvent(false);
          
          });

          this.changeColumns();
          if( resp.infoTable.infoData.length == 0 && this.filters ){
            this.tableData.infoData = [];
            this.countBanner = 0 ;
            if( this.filters.fechainicio && this.filters.fechafin  && !this.filters.autor && !this.filters.titulo) {

              this._dataService.setGeneralNotificationMessage('No se registró información en el periodo de fechas solicitado');
              return;
            }
            this._dataService.setGeneralNotificationMessage('No se encontró ninguna coincidencia con los parámetros ingresados');
            return;
          }
          this._bannerService.getCountItems(this.filters)
                            .subscribe( (count:any) => {
                              this.countBanner = count.filas;
                              this._dataService.setIsLoadingEvent(false); 
                            },error => {this._dataService.setIsLoadingEvent(false)},()=>this._dataService.setIsLoadingEvent(false)
                            );
        
        },error=>    this._dataService.setIsLoadingEvent(false),()=>    this._dataService.setIsLoadingEvent(false));
  }

  changeColumns(){
      let nameStorage = this._localStorageService.getVarLocalStorage( this._router.url.toString() );
     let columns = JSON.parse(localStorage.getItem(nameStorage));
    if(columns){
      this.tableData.titles  = columns.actives.map(x => x.title);
      this.tableData.headers = columns.actives.map(x => x.id);
      this._table.setHeadersActive( columns.actives );
      this._table.setHeadersInactive( columns.inactives );
    }
  }

  selectItemBanner( objectBanner ){
    if(this.tableData.infoData.length <= 0) return;
    this._bannerService.setBanner( objectBanner );
    this.route.navigate([`${routesWeb.HOME}/${routesWeb.ADMIN_BANNERS}`]);
  }


  dialogSearch(event){
	  const dialogRef = this.dialog.open(DialogFormsFilterComponent, {
		width: '500px',
		data: { filter: "AdminBanners" }
	  });

	  dialogRef.afterClosed().subscribe(result => {
	  });
  }

  remove(filter): void {
    this.filters[filter] = null;
    if ( filter == 'titulo' ) this.title = null;
    if ( filter == 'autor' ) this.nameAuthor = null;
    if ( filter == 'fechainiciodesde' ) {
      this.startDate = null;
      this.endDate = null;
      this.filters['fechainiciodesde'] = null;
      this.filters['fechainiciohasta'] = null;
    }
    this._table.setParamsBanners(this.filters);
    this.loadInfoBanner();
}

  ngOnDestroy(){
    this.filterEvent.unsubscribe();
  }

}
