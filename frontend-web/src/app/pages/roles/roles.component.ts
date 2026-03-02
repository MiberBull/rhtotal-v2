import { Router } from '@angular/router';
import { Component,OnInit } from "@angular/core";

import { ToolbarFabService } from '../../services/toolbar-fab/toolbar-fab.service';
import { RolService } from '../../services/roles/rol.service';

import { BreadcrumbService } from './../../services/breadcrumbs/breadcrumbs.service';

import { RolesUserTO } from './../../models/rol.model';
import { BREADCRUMB, FAB, routesWeb, TABLE_ROUTE, HEADERS, environment } from '../../../environments/environment';
import { GenericTableService } from '../../services/generic-table/generic-table.service';
import { LocalStorageService } from '../../services/local-sotorage/localstorage.service';
import {DataService} from '../../services/data.service';
import { error } from 'util';

@Component({
    selector:'app-roles',
    templateUrl:'./roles.component.html',
    styleUrls:['./roles.component.css']
})


export class RolesComponent implements OnInit {

    tableData:any = {};
    userRol: RolesUserTO = new RolesUserTO();
    countRoles:number;
    infoTable = [];

    JsonLocalStorage:any={};

    showSaveUpdate: boolean = true;

    constructor(
        private _toolbarFab: ToolbarFabService,
        private _breadcrumb:BreadcrumbService,
        private _table: GenericTableService,
        private _localStorageService:LocalStorageService,
        private route: Router,
        private _dataService:DataService,
        private _rolService: RolService ){

        this._dataService.setIsLoadingEvent(true);

        this.showSaveUpdate = this._localStorageService.getRolUserRead() == environment.ROL_USER_READ ? false : true;
        this._toolbarFab.setRolUserRead(this.showSaveUpdate);

        this._toolbarFab.setVisible( this.route.url.toString() );

        this._toolbarFab.getAddEvent()
                        .subscribe( click => {
                            this.options(click);
                        });
        this._breadcrumb.setRouteText({title:BREADCRUMB.ROLES,arrow:false});

        let nameStorage = this._localStorageService.getVarLocalStorage( this.route.url.toString() );

        this.JsonLocalStorage = JSON.parse(localStorage.getItem(nameStorage));
                console.log(this.JsonLocalStorage);
        if(this.JsonLocalStorage == null){

            this._table.getTableHeadersJSON( HEADERS.ROLES ).subscribe(  headers => {
                  let arrayHeader:any=[];
                  this._table.setHeadersActive( headers );
                  this._table.setHeadersInactive( arrayHeader );
                  this.loadInfoRoles();
            },error=>this._dataService.setIsLoadingEvent(false),()=>this._dataService.setIsLoadingEvent(false));
        } else {
              this._table.setHeadersActive( this.JsonLocalStorage.actives );
              this._table.setHeadersInactive( this.JsonLocalStorage.inactives );
              this.loadInfoRoles();
        }

        this._table.getColumnsGroups().subscribe( columns => this.changeColumnsRoles( columns ) );

    }

    ngOnInit(){
        this._toolbarFab.setVisibleFilter(false);
    }

    options( option:string ){
        if( option === FAB.ADD ){
            this.route.navigate([`${routesWeb.HOME}${routesWeb.ADMIN_ROLES}`])
        }
    }

    goAdminRol(){
        this.route.navigate([`${routesWeb.HOME}${routesWeb.ADMIN_ROLES}`]);
    }

    downloadExcel(){
    }

    loadInfoRoles( page = 0 ){
        this._dataService.setIsLoadingEvent(true);
        this._table.getInfoTable( TABLE_ROUTE.ROLES,HEADERS.ROLES,page.toString() )
                   .subscribe( (resp:any) => {
                    
                        this.tableData = resp.infoTable;
                         this.infoTable = this.tableData.infoData.filter(
                           x=> x.nameRol !="Administrador Master");
                         this.tableData.infoData =this.tableData.infoData.map(function(x){
                            x.status = (x.status=='A'?'Activo':x.status=='I'?'Inactivo':'Nuevo');
                            return x;
                        });

                        this._dataService.setIsLoadingEvent(false);
                        this.changeColumnsRoles( this.JsonLocalStorage );
                   },error => this._dataService.setIsLoadingEvent(false),()=>{
                       this._dataService.setIsLoadingEvent(false);
                      
                       console.log('Se lanzo arreglo columnas',this.JsonLocalStorage);
                    }
                    );
        this._rolService.getCountItems().subscribe( (count:any) => this.countRoles = count.filas );
    }
    loadRowsTable( page = 0 ){
        this._dataService.setIsLoadingEvent(true);
        this._table.getRowsTable( TABLE_ROUTE.ROLES,HEADERS.ROLES,page.toString() )
                   .subscribe( (resp:any) => {this.infoTable = resp  
                   this._dataService.setIsLoadingEvent(false);
                   }
                                
                   ,error=>this._dataService.setIsLoadingEvent(false)
                   ,()=>{this._dataService.setIsLoadingEvent(false);
                      }
                    );
    }

    changeColumnsRoles(columns:any){
        if(columns==null)
        {
          let nameStorage = this._localStorageService.getVarLocalStorage( this.route.url.toString() );
          columns = JSON.parse(localStorage.getItem(nameStorage));
        }
        if(columns){
          this.tableData.titles  = columns.actives.map(x => x.title);
          this.tableData.headers = columns.actives.map(x => x.id);
          this._table.setHeadersActive( columns.actives );
          this._table.setHeadersInactive( columns.inactives );
        }
    }

    selectItemRol( itemUserRol ){
        this._rolService.setUserRol( itemUserRol );
        this.route.navigate([`${routesWeb.HOME}${routesWeb.ADMIN_ROLES}`]);
    }

}
