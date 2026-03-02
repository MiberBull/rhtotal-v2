import { Component, OnInit } from '@angular/core';
import { BreadcrumbService } from '../../services/breadcrumbs/breadcrumbs.service';
import { ToolbarFabService } from '../../services/toolbar-fab/toolbar-fab.service';
import { DialogFormFilterService } from '../../services/filter/dialog-form-filter.service';

import { Router } from '@angular/router';
import { BREADCRUMB, TABLE_ROUTE, environment } from '../../../environments/environment.prod';
import { routesWeb, HEADERS } from '../../../environments/environment';
import { GenericTableService } from '../../services/generic-table/generic-table.service';
import { MatTabChangeEvent } from '@angular/material';
import { NotificationService } from '../../services/notification/notification.service';
import { Subscription } from 'rxjs';
import { DataService } from '../../services/data.service';
import { Information } from '../../util/date';
import { Filters } from '../../interface/util.interface';
import { LocalStorageService } from '../../services/local-sotorage/localstorage.service';
import { debounce } from 'rxjs/operators';
import { MAT_MOMENT_DATE_ADAPTER_OPTIONS_FACTORY } from '@angular/material-moment-adapter';
import { IfStmt } from '@angular/compiler';




@Component({
    selector:'app-notifications',
    templateUrl:'./notification.component.html',
    styleUrls:['./notification.component.css']
})

export class NotificationComponent implements OnInit{

endDate:string;
startDate:string;
nameAuthor:string;
title:string;
selectable = true;
removable:boolean = true;
numTab:number;
tableData:any = {};
countNotification:number;

public filtros:any;
public filtrosScheduled:any;
public filtrosSend:any;
private eventoFiltrado:Subscription;
private bandera:boolean=false;

JsonLocalStorage:any={};

showSaveUpdate: boolean = true;

constructor(
    private _breadcrumb: BreadcrumbService,
    private _toolbar: ToolbarFabService,
    private _dialogFilterService:DialogFormFilterService,
    private _localStorageService:LocalStorageService,
    private _router:Router,
    private _dataService: DataService,
    private _table: GenericTableService,
    private __notificationS: NotificationService,
    private router: Router){
    this.numTab=0;
    this.showSaveUpdate = this._localStorageService.getRolUserRead() == environment.ROL_USER_READ ? false : true;
    this._toolbar.setRolUserRead(this.showSaveUpdate); 
    
    this.__notificationS.setNameExcelNotification(environment.NAME_EXCEL_NOTIFICATION_PROGRAMADAS);

    this._breadcrumb.setRouteText({title:BREADCRUMB.NOTIFICATION,arrow:false});
    this._toolbar.setVisible( this.router.url.toString() );
    this._toolbar.getAddEvent()
                    .subscribe( option => this.options(option));

    let nameStorage = this._localStorageService.getVarLocalStorage( this._router.url.toString() );

    this.JsonLocalStorage = JSON.parse(localStorage.getItem(nameStorage));
    if(this.JsonLocalStorage == null){
        this._table.getTableHeadersJSON( HEADERS.NOTIFICATION )
            .subscribe( headers => {
                let arrayHeader:any=[];
                this._table.setHeadersActive( headers );
                this._table.setHeadersInactive( arrayHeader );
                this.loadInfoNotifications();
        });
    }else{
        this._table.setHeadersActive( this.JsonLocalStorage.actives );
        this._table.setHeadersInactive( this.JsonLocalStorage.inactives );
        this.loadInfoNotifications();
    }

    this._table.getColumnsGroups().subscribe( columns => this.changeColumnsNotification() );

    /**
     *
     */
    this.eventoFiltrado = this._dialogFilterService.getFiltersDialog().subscribe( (filtros:any) => {

        this.filtroUpdate(filtros);
        this.title = filtros.titulo == "" || filtros.titulo == null ? null : filtros.titulo;
        this.startDate = filtros.fechadeenvioinicio == null || filtros.fechadeenvioinicio == "" ? null : Information.getDateString(new Date(filtros.fechadeenvioinicio));
        this.endDate = filtros.fechadeenviofin == null || filtros.fechadeenviofin == "" ? null : Information.getDateString(new Date(filtros.fechadeenviofin));
        this.nameAuthor = filtros.autor == null || filtros.autor == "" ? null : filtros.autor;
    
        if (this.numTab==1)
        {

            this.filtrosSend = filtros;
            this.filtros=this.filtrosSend;
            this._table.setParamsNotification(this.modifyFilter(this.filtrosSend));
           
        }
        else
        {
            this.filtrosScheduled=filtros;
            this.filtros=this.filtrosScheduled;
            this._table.setParamsNotification(this.modifyFilter(this.filtrosScheduled));
        }
       
        this.bandera=true;
        this.loadInfoNotifications(0,this.numTab);

    }
    
    );

    }

    ngOnInit(){}

    options(option:string){
        if(option == 'add'){
            this.router.navigate([`${routesWeb.HOME}/${routesWeb.ADMIN_NOTIFICATION}`]);
        }
    }

    modifyFilter(filtros:any)
    {
        if(filtros['fechadeenvioinicio'] != null && filtros['fechadeenvioinicio'] != ""){
            let fechaStart = new Date(filtros['fechadeenvioinicio']).toISOString();
            filtros['fechadeenvioinicio']=fechaStart;
        }

        if(filtros['fechadeenviofin'] != null && filtros['fechadeenviofin'] != ""){
            let fechaEnd = new Date(filtros['fechadeenviofin']).toISOString();
            filtros['fechadeenviofin']=fechaEnd;
        }


        return filtros;
    }

    nextNotification(page:any){        
        this.loadInfoNotifications(page, this.numTab);
    }

    loadInfoNotifications( page=0,numberTab=0){

        this._dataService.setIsLoadingEvent(true);
        
        if(!this.bandera){
            this._table.setParamsNotificationNull();
        }

        this._table.getInfoTable( TABLE_ROUTE.NOTIFICATION,HEADERS.NOTIFICATION,page.toString(),numberTab )
                    .subscribe( (resp:any) => {
                        let filtroTable= this.numTab==1?this.filtrosSend:this.filtrosScheduled;
                        this._dataService.setIsLoadingEvent(false);
                        if( resp.infoTable.infoData.length == 0 && filtroTable ){
                            this.tableData = {};
                            this.countNotification = 0 ;
                            if( filtroTable.fechadeenvioinicio && filtroTable.fechadeenviofin  && !filtroTable.autor && !filtroTable.titulo) {
                                this._dataService.setGeneralNotificationMessage('No se registró información en el periodo de fechas solicitado');
                                this.tableData=  resp.infoTable;
                                this.changeColumnsNotification();
                                return;
                            }else{
                                this._dataService.setGeneralNotificationMessage('No se encontró ninguna coincidencia con los parámetros ingresados');
                                this.tableData=  resp.infoTable;
                                this.changeColumnsNotification();
                                return;
                            }
                        }                        
                        this.tableData = resp.infoTable;
                        this.changeColumnsNotification();
                        this.tableData.infoData =this.tableData.infoData.map(function(x){
                            var fecha = new Date(x.startDate);
                            fecha.setDate(fecha.getDate()+1);
                            x.startDate = Information.getDateString(fecha);
                            x.status = (x.status=='A'?'Activo':x.status=='I'?'Inactivo':'Enviado');
                            x.notificationTime = Information.getTimeString(x.notificationTime);
                            return x;
                        });
                        
                        this.__notificationS.getCountItems(numberTab,filtroTable)                                                
                            .subscribe( (count:any) => this.countNotification = count.filas
                            );
                            console.log('numero filas',this.countNotification);
                    },
                    error => {
                        console.log("Error mandado: ",error);
                        this._dataService.setIsLoadingEvent(false);
                    },()=>    this._dataService.setIsLoadingEvent(false)
                );
    }


    selectItemNotification( itemNotification ) {
        this.__notificationS.setNotifitacion( itemNotification );
        this.router.navigate([`${routesWeb.HOME}/${routesWeb.ADMIN_NOTIFICATION}`]);
    }

    tabChange( tagChangeEvent: MatTabChangeEvent ) {
               this.nameAuthor=null;
               this.startDate=null;
               this.endDate=null;
        this._table.setParamsNotificationNull();

        if (tagChangeEvent.index === 0){
            this.__notificationS.setNameExcelNotification(environment.NAME_EXCEL_NOTIFICATION_PROGRAMADAS);
        } else if (tagChangeEvent.index === 1){
            this.__notificationS.setNameExcelNotification(environment.NAME_EXCEL_NOTIFICATION_ENVIADAS);
        }

        
        this.__notificationS.setTabNotification( tagChangeEvent.index );
        this.numTab=tagChangeEvent.index;
        if(this.numTab==1)
        {
            this.filtroUpdate(this.filtrosSend);
            if(this.filtrosSend)
            {
                this._table.setParamsNotification(this.modifyFilter(this.filtrosSend));
                this.filtros=this.filtrosSend;
            }
        }
        else
        {
            this.filtroUpdate(this.filtrosScheduled);
            if(this.filtrosScheduled)
            {
                this._table.setParamsNotification(this.modifyFilter(this.filtrosScheduled));

                this.filtros=this.filtrosScheduled;
            }
        }
        this.loadInfoNotifications(0,tagChangeEvent.index);
    }

    changeColumnsNotification() {

          let nameStorage = this._localStorageService.getVarLocalStorage( this._router.url.toString() );
         let columns = JSON.parse(localStorage.getItem(nameStorage));
        
        if(columns){
            this.tableData.titles  = columns.actives.map(x => x.title);
            this.tableData.headers = columns.actives.map(x => x.id);
            this._table.setHeadersActive( columns.actives );
            this._table.setHeadersInactive( columns.inactives );
            }
    }

    remove(filter): void {
        if(this.numTab==1)
        {
            this.filtrosSend[filter] = null;
            this.title = null;
            this.nameAuthor = null;
            this.startDate = null;
            this.endDate = null;
            this.filtrosSend['fechadeenvioinicio'] = null;
            this.filtrosSend['fechadeenviofin'] = null;
            this._table.setParamsNotification(this.filtrosSend);
        }
        else
        {
            this.filtrosScheduled[filter] = null;
            this.title = null;
            this.nameAuthor = null;
            this.startDate = null;
            this.endDate = null;
            this.filtrosScheduled['fechadeenvioinicio'] = null;
            this.filtrosScheduled['fechadeenviofin'] = null;
            this._table.setParamsNotification(this.filtrosScheduled);
        }
        this.bandera=true;
        this.loadInfoNotifications(0,this.numTab);
    }

    filtroUpdate(filtro:any)
    {
        if(filtro)
        {
            this.title = (filtro['titulo']==null || filtro['titulo']=="")?null:filtro['titulo'];
            this.nameAuthor = (filtro['autor']==null || filtro['autor']=="")?null:filtro['autor'];            
            this.startDate = (filtro['fechadeenvioinicio'] == null || filtro['fechadeenvioinicio'] == "")?null: Information.getDateString(new Date( filtro['fechadeenvioinicio']));
            this.endDate =  (filtro['fechadeenviofin'] == null || filtro['fechadeenviofin'] == "")?null: Information.getDateString(new Date(filtro['fechadeenviofin']));
        }
        else
        {
            this.title = null;
            this.nameAuthor = null;
            this.startDate = null;
            this.endDate = null;

        }

    }

    ngOnDestroy() {
        this.eventoFiltrado.unsubscribe();
    }
}
