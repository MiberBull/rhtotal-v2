import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import {MatDialog} from '@angular/material';

import { BreadcrumbService } from '../../services/breadcrumbs/breadcrumbs.service';
import { ToolbarFabService } from '../../services/toolbar-fab/toolbar-fab.service';
import { DialogFormFilterService } from '../../services/filter/dialog-form-filter.service';
import { DialogFormsFilterComponent } from '../../components/dialog-forms-filter/dialog-forms-filter.component';

import { BREADCRUMB, routesWeb, TABLE_ROUTE } from '../../../environments/environment';
import { GenericTableService } from '../../services/generic-table/generic-table.service';
import { HEADERS, environment } from '../../../environments/environment.prod';
import { Filters } from '../../interface/util.interface';
import { LocalStorageService } from '../../services/local-sotorage/localstorage.service';
import { Subscription } from 'rxjs';
import { InsuranceService } from '../../services/insurance/insurance.service';
import { Information } from '../../util/date';
import { DataService } from '../../services/data.service';

@Component({
  selector: 'app-insurance',
  templateUrl: './insurance.component.html',
  styleUrls: ['./insurance.component.css']
})
export class InsuranceComponent implements OnInit,OnDestroy {

  tableData:any = {};
  JsonLocalStorage:any={};

  public filtros:any;
  private eventoFiltrado:Subscription;
  private bandera:boolean=true;

  showSaveUpdate: boolean = true;

  insuranceCarrier:string;
  startDate:string;
  endDate:string;
  author:string;

  removable: boolean = true;

  isFilters:boolean=false

  countInsurance:number;
 

  constructor(
    private _breadcrumb: BreadcrumbService,
    private _toolbar: ToolbarFabService,
    private _table: GenericTableService,
    private _dialogFilterService:DialogFormFilterService,
    private _localStorageService:LocalStorageService,
    private dialog: MatDialog,
    private router: Router,
    private _dataService: DataService,
    private insuranceService:InsuranceService) {

    this.showSaveUpdate = this._localStorageService.getRolUserRead() == environment.ROL_USER_READ ? false : true;
    this._toolbar.setRolUserRead(this.showSaveUpdate);    
    
    this._dataService.setIsLoadingEvent(true);

    this._breadcrumb.setRouteText({title: BREADCRUMB.INSURANCE,arrow:false});
    this._toolbar.setVisible( this.router.url.toString() );
    this._toolbar.getAddEvent()
                 .subscribe( add => {
                  this.optionsToolbar( add );
                 })

    
    this.eventoFiltrado = this._dialogFilterService.getFiltersDialog().subscribe( (filtros:any) => {
          this._dataService.setIsLoadingEvent(true);
          this.filtros = filtros;
          
          this.insuranceCarrier = this.filtros.aseguradora == '' || this.filtros.aseguradora == null ? null : this.filtros.aseguradora;
          
          this.startDate = this.filtros.fechavigenciainiciodesde == '' || this.filtros.fechavigenciainiciodesde == null ? null : Information.getDateString(new Date(this.filtros.fechavigenciainiciodesde));
          
          this.endDate = this.filtros.fechavigenciainiciohasta == '' || this.filtros.fechavigenciainiciohasta == null ? null : Information.getDateString(new Date(this.filtros.fechavigenciainiciohasta));
          
          this.author = this.filtros.numerodepoliza == '' || this.filtros.numerodepoliza == null ? null : this.filtros.numerodepoliza;
          
         
          if (this.filtros['fechavigenciainiciodesde'] != null){
              let fechaStart = new Date(this.filtros['fechavigenciainiciodesde']).toISOString();
              this.filtros['fechavigenciainiciodesde']=fechaStart;
          }

          if (this.filtros['fechavigenciainiciohasta'] != null){
              let fechaEnd = new Date(this.filtros['fechavigenciainiciohasta']).toISOString();
              this.filtros['fechavigenciainiciohasta']=fechaEnd;
          }

          this._table.setParamsInsurance(this.filtros);
          this.bandera=false;
          this.isFilters=true;
          this.loadInfoInsurance();
    });


    let nameStorage = this._localStorageService.getVarLocalStorage( this.router.url.toString() );
    console.log(nameStorage);
    this.JsonLocalStorage = JSON.parse(localStorage.getItem(nameStorage));

    if(this.JsonLocalStorage == null){
          this._table.getTableHeadersJSON( HEADERS.INSURANCE )
            .subscribe( headers => {
                let arrayHeader:any=[];
                this._table.setHeadersActive( headers );
                this._table.setHeadersInactive( arrayHeader );
                this.loadInfoInsurance();
          });
    }else {
          this._table.setHeadersActive( this.JsonLocalStorage.actives );
          this._table.setHeadersInactive( this.JsonLocalStorage.inactives );
          this.loadInfoInsurance();
    }



    this._table.getColumnsGroups().subscribe( columns => this.changeColumnsInsurance(columns) );

  }

  ngOnInit() {
  }

  optionsToolbar(option:string){
    console.log( option );
    this.router.navigate([`${routesWeb.HOME}/${routesWeb.ADMIN_INSURANCE}`])
  }

  loadInfoInsurance( page = 0 ){

    if (this.bandera){
      this._table.setParamsInsuranceNull();
    }

    this._table.getInfoTable( TABLE_ROUTE.INSURANCE,HEADERS.INSURANCE,page.toString() )
               .subscribe( (resp:any) => {
                                 this.countInsurance = 0;
                                 this.tableData = resp.infoTable;
                                 this.changeColumnsInsurance(this.JsonLocalStorage);

                                if (resp.infoTable.infoData.length == 0 && this.isFilters === true) {
                                  if (this.filtros.fechainiciodesde && this.filtros.fechainiciohasta && !this.filtros.aseguradora && !this.filtros.autor) {
                                    this._dataService.setIsLoadingEvent(false);
                                    this._dataService.setGeneralNotificationMessage('No se registró información en el periodo de fechas solicitado');
                                    return;
                                  }   
                                  this._dataService.setIsLoadingEvent(false);                               
                                  this._dataService.setGeneralNotificationMessage('No se encontró ninguna coincidencia con los parámetros ingresados');
                                  return;
                                }                                 
                                console.log("TABLA SEGUROS ", this.tableData);
                                                             
                                let infoData = this.tableData.infoData.map( info => {
                                      
                                    let starDate = new Date(info.startDate);
                                    let endDate = new Date(info.endDate);   

                                  
                                  let fecha1 = Information.convertTime(info.timePublication);
                                  let fecha2 = Information.convertTime(info.notificationTime);

                                  let arrayFecha1 = Information.formatTime(fecha1.split(' '));
                                  let arrayFecha2 = Information.formatTime(fecha2.split(' '));

                                  info.sum="$"+info.sum;

                                  info.timePublication = arrayFecha1;                                 
                                  info.notificationTime = arrayFecha2;

                                    info.status = info.status === 'A' ? 'Activo' : 
                                                  (info.status === 'I' ? 'Inactivo' : '');

                                  info.startDate = `${starDate.getUTCDate() < 10 ? '0' + starDate.getUTCDate() : starDate.getUTCDate() }-${starDate.getUTCMonth() <= 8 ? '0' + (starDate.getUTCMonth() + 1) : starDate.getUTCMonth() + 1 }-${starDate.getUTCFullYear()}`;

                                  info.endDate = `${endDate.getUTCDate() < 10 ? '0' + endDate.getUTCDate() : endDate.getUTCDate()}-${endDate.getUTCMonth() <= 8 ? '0' + (endDate.getUTCMonth() + 1) : (endDate.getUTCMonth() + 1)}-${endDate.getUTCFullYear()}`;
                                  
                                    return info;
                             
                                });
                                
                                
                                this.changeColumnsInsurance(this.JsonLocalStorage);
                 
                                this.insuranceService.getCountRecords(this.filtros).subscribe( (countFilas:any) => {
                                    this.countInsurance = countFilas.filas;
                                    this._dataService.setIsLoadingEvent(false);
                                },
                                error => {
                                  this._dataService.setIsLoadingEvent(false);
                                });
                           },
                           error => {
                             console.log( error );
                             this._dataService.setIsLoadingEvent(false);
                           });
  }

  changeColumnsInsurance(column:any){
    let nameStorage = this._localStorageService.getVarLocalStorage(this.router.url.toString());
    
    let columns = JSON.parse(localStorage.getItem(nameStorage));    

    if(columns){
      this.tableData.titles  = columns.actives.map(x => x.title);
      this.tableData.headers = columns.actives.map(x => x.id);
      this._table.setHeadersActive( columns.actives );
      this._table.setHeadersInactive( columns.inactives );
    }
  }
  selectItemInsurance( itemInsurance ){
    console.log( itemInsurance );
    this.insuranceService.setInsuranceSelected(itemInsurance);
    this.router.navigate([`${routesWeb.HOME}/${routesWeb.INSURANCE_ADMIN}`]);
  }

  remove(filter): void{
    this.filtros[filter] = null;
    this._dataService.setIsLoadingEvent(true);

    if (filter == 'aseguradora') this.insuranceCarrier = null;
    if (filter == 'fechavigenciainiciodesde') {
      this.startDate = null;
      this.endDate = null;
      this.filtros['fechavigenciainiciodesde'] = null;
      this.filtros['fechavigenciainiciohasta'] = null;
    }
    if (filter == 'numerodepoliza') this.author = null;

    this._table.setParamsInsurance(this.filtros);
    this.loadInfoInsurance();

  }  

  ngOnDestroy(){
    this.eventoFiltrado.unsubscribe();
  }

}
