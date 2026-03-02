import { Component, OnInit, OnDestroy } from '@angular/core';
import { MatTabChangeEvent } from '@angular/material';
import { GenericTableService } from '../../../services/generic-table/generic-table.service';
import { TABLE_ROUTE } from '../../../../environments/environment';

import { Router } from '@angular/router';
import { PaysheetService } from '../../../services/paysheet/paysheet.service';
import { routesWeb, HEADERS, environment } from '../../../../environments/environment.prod';
import { LocalStorageService } from '../../../services/local-sotorage/localstorage.service';
import { Subscription } from 'rxjs';
import { DialogFormFilterService } from '../../../services/filter/dialog-form-filter.service';
import { DataService } from '../../../services/data.service';
import { ToolbarFabService } from '../../../services/toolbar-fab/toolbar-fab.service';
//import { map } from 'rxjs/operators';
import { Information } from '../../../util/date';

@Component({
  selector: 'app-paysheet-velo-cash',
  templateUrl: './paysheet-velo-cash.component.html',
  styleUrls: ['./paysheet-velo-cash.component.css']
})
export class PaysheetVeloCashComponent implements OnInit,OnDestroy {

  tableData: any = {};
  tabSelectedIndex: number=0;
  page:number = 0;
  banderaFiltersNull: boolean = true;

  JsonLocalStorage: any = {};
  private eventoFiltrado: Subscription;
  filtersFintech: any;
  banderaExistFilter: boolean = false;
  totalItems:number;

  name: string;
  lastName: string;
  lastNameM: string;
  folio: string;
  dateRequest: string;  

  filterEs: any;
  filterAP: any;
  filterR: any;

  removable: boolean = true;  
  

  constructor(private _genericTableService: GenericTableService,
              private _fintechService: PaysheetService,
              private _router: Router,
              private _localStorageService: LocalStorageService,
              private _dialogFilterService: DialogFormFilterService,
              private _dataS: DataService,
              private _toolbar: ToolbarFabService,
              private router: Router) { 

      this._dataS.setIsLoadingEvent(false);          
      this._dataS.setIsLoadingEvent(true);
      
      this._toolbar.setVisible(this.router.url.toString());
      this.tabSelectedIndex = 0;

      let nameStorage = this._localStorageService.getVarStorageFintechVeloCash(this.tabSelectedIndex);
      this._fintechService.setTabFintechSelection(this.tabSelectedIndex);
      this._fintechService.setTabFintech(this.tabSelectedIndex);
      this.JsonLocalStorage = JSON.parse(localStorage.getItem(nameStorage));

      if (this.JsonLocalStorage == null) {
        this._genericTableService.getTableHeaderFintechColumns(HEADERS.FINTECH_WAIT_VELOCASH).subscribe(headers => {
          let arrayHeader: any = [];
          console.log('LOCAL STORAGE HEADER MARCO ', headers);
          headers.pop();
          this._genericTableService.setHeadersActive(headers);
          this._genericTableService.setHeadersInactive(arrayHeader);
          this.loadInfoPaysheet();
        });
      } else {
        this._genericTableService.setHeadersActive(this.JsonLocalStorage.actives);
        this._genericTableService.setHeadersInactive(this.JsonLocalStorage.inactives);
        this.loadInfoPaysheet();
      }

      this._genericTableService.getColumnsGroups().subscribe(columns => this.changeColumnsFintechAvance(columns));


      this.eventoFiltrado = this._dialogFilterService.getFiltersDialog().subscribe((filters: any) => {
        this.filtersFintech = filters;
        console.log(filters);

        this.name = filters.nombre == "" || filters.nombre == null ? null : filters.nombre;
        this.lastName = filters.apellidopaterno == "" || filters.apellidopaterno == null ? null : filters.apellidopaterno;
        this.lastNameM = filters.apellidomaterno == "" || filters.apellidomaterno == null ? null : filters.apellidomaterno;
        this.folio = filters.foliosolicitud == "" || filters.foliosolicitud == null ? null : filters.foliosolicitud;
        this.dateRequest = filters.fechasolicitud == "" || filters.fechasolicitud == null ? null : Information.getDateString(new Date(filters.fechasolicitud));

        if (this.tabSelectedIndex == 0) {
          this.filterEs = filters;
        } else if (this.tabSelectedIndex == 1) {
          this.filterAP = filters;
        } else if (this.tabSelectedIndex == 2) {
          this.filterR = filters;
        }        

        if (this.filtersFintech['fechasolicitud'] != null && this.filtersFintech['fechasolicitud'] != '') {
          let fechaStart = new Date(this.filtersFintech['fechasolicitud']).toISOString();
          this.filtersFintech['fechasolicitud'] = fechaStart;
        }

        this._genericTableService.setParamsFintech(this.filtersFintech);
        this.banderaFiltersNull = false;
        this.banderaExistFilter = true;
        this.loadInfoPaysheet(this.page, this.tabSelectedIndex);
      });      

  }                
     
  ngOnInit() {
  }

  loadPage( page: number ){
    this.page = page;
    this.loadInfoPaysheet(this.page, this.tabSelectedIndex);    
  }

  loadInfoPaysheet(page = 0, numberTab = 0){
      if (this.banderaFiltersNull) {
        this._genericTableService.setParamsFintechNull();
      }
      this._dataS.setIsLoadingEvent(true);
      let headerFintech = numberTab == 0 ? HEADERS.FINTECH_WAIT_VELOCASH : (numberTab == 1 ? HEADERS.FINTECH_APPROVED_VELOCASH :
        (numberTab == 2 ? HEADERS.FINTECH_REJECTED_VELOCASH : ''));

        this._genericTableService.getInfoTable(TABLE_ROUTE.FINTECH_VELOCASH, headerFintech, page.toString(), numberTab)
              .subscribe((resp: any) => {
                  console.log("INFO ", resp);

                  if (resp.infoTable.infoData.length == 0 && this.banderaExistFilter) {

                    this._dataS.setGeneralNotificationMessage('No se encontró ninguna coincidencia con los parámetros ingresados');
                    console.log('NO SE ENCONTRO REGISTROS');                    
                    this.banderaExistFilter = false;
                    this._dataS.setIsLoadingEvent(false);
                    return;
                  }                  
                  
                 this.tableData = resp.infoTable;

// Work Arround Ordenar tablas con JS
                this.tableData.infoData = this.tableData.infoData.sort((a,b)=> {
                let datePartsA = a.applicationDate.split('-');
                let datePartsB = b.applicationDate.split('-');

                

                return new Date(+datePartsB[2], datePartsB[1]-1, +datePartsB[0]).getTime() - 
                       new Date(+datePartsA[2], datePartsA[1]-1, +datePartsA[0]).getTime();
                });
// Work Arround Ordenar tablas con JS  

                 console.log(this.tableData);

                 this.changeColumnsFintechAvance(this.JsonLocalStorage);
                 this._fintechService.getCountItemsVeloCash(this.tabSelectedIndex, this.filtersFintech).subscribe((count: any) => {
                     this.totalItems = count.filas;
                     this._dataS.setIsLoadingEvent(false);
                 },error => {
                   this._dataS.setIsLoadingEvent(false);      
                 });                 
          }, error => {
             this._dataS.setIsLoadingEvent(false);
          });
       
  }

  tagChange(tagChangeEvent: MatTabChangeEvent) {
    this.page = 0; // Se añade para inicializar el paginado por el cambio de tab
    this._dataS.setIsLoadingEvent(true);
    this.name = null;
    this.lastName = null;
    this.lastNameM = null;
    this.folio = null;
    this.dateRequest = null;
    this._genericTableService.setParamsFintechNull();
    this.tabSelectedIndex = tagChangeEvent.index;
    console.log("TAB SELECCIONADO ", this.tabSelectedIndex);
    this.banderaExistFilter = false;

    let nameStorage = this._localStorageService.getVarStorageFintechVeloCash(this.tabSelectedIndex);
    this._fintechService.setTabFintechSelection(this.tabSelectedIndex); 
    this._fintechService.setTabFintech(this.tabSelectedIndex);

    this.JsonLocalStorage = JSON.parse(localStorage.getItem(nameStorage));

    let header = this.tabSelectedIndex == 0 ? HEADERS.FINTECH_WAIT_VELOCASH :
      (this.tabSelectedIndex == 1 ? HEADERS.FINTECH_APPROVED_VELOCASH :
        (this.tabSelectedIndex == 2 ? HEADERS.FINTECH_REJECTED_VELOCASH : ''));

    if (this.JsonLocalStorage == null) {
        this._genericTableService.getTableHeaderFintechColumns(header).subscribe((headers:any) => {
            let arrayHeader: any = [];

          if (header == HEADERS.FINTECH_WAIT_VELOCASH){
               headers.pop();
          }

            this._genericTableService.setHeadersActive(headers);
            this._genericTableService.setHeadersInactive(arrayHeader);

        });
    } else {
          this._genericTableService.setHeadersActive(this.JsonLocalStorage.actives);
          this._genericTableService.setHeadersInactive(this.JsonLocalStorage.inactives);
    }     
    
    if (this.tabSelectedIndex === 0) {
      this.updateFilters(this.filterEs);
      this.filtersFintech = this.filterEs;
    } else if (this.tabSelectedIndex === 1) {
      this.updateFilters(this.filterAP);
      this.filtersFintech = this.filterAP;
    } else if (this.tabSelectedIndex === 2) {
      this.updateFilters(this.filterR);
      this.filtersFintech = this.filterR;
    }   

    this.loadInfoPaysheet(this.page, this.tabSelectedIndex);
  }

  selectItem(itemFintech) {
    console.log("ITEM SELECCIONADO ", itemFintech);
    console.log("ITEM SELECCIONADO ", itemFintech.idFintech);
    console.log("HEADER FINTECH ", this.tableData.headers);

    let headerFintech = this.tabSelectedIndex == 0 ? HEADERS.FINTECH_WAIT_VELOCASH : (this.tabSelectedIndex == 1 ? HEADERS.FINTECH_APPROVED_VELOCASH :
      (this.tabSelectedIndex == 2 ? HEADERS.FINTECH_REJECTED_VELOCASH : '')); 
    
            
    this._fintechService.setFintechJSON(itemFintech.idFintech, TABS[this.tabSelectedIndex], environment.TYPE_FINTECH_VELOCASH, this.tableData.headers, this.tableData.titles, this.tabSelectedIndex);
    this._router.navigate([`${routesWeb.HOME}/${routesWeb.FINTECH_DETALLE}`]);
       

  }

  changeColumnsFintechAvance(columns) {
    if (columns) {
      this.tableData.titles = columns.actives.map(x => x.title);
      this.tableData.headers = columns.actives.map(x => x.id);
      this._genericTableService.setHeadersActive(columns.actives);
      this._genericTableService.setHeadersInactive(columns.inactives);
    }
  } 

  remove(filter): void {
    if (this.tabSelectedIndex === 0) {
      this.filterEs[filter] = null;
      this.filtersFintech = this.filterEs;
      this._genericTableService.setParamsFintech(this.filterEs);
    } else if (this.tabSelectedIndex === 1) {
      this.filterAP[filter] = null;
      this.filtersFintech = this.filterAP;
      this._genericTableService.setParamsFintech(this.filterAP);
    } else if (this.tabSelectedIndex === 2) {
      this.filterR[filter] = null;
      this.filtersFintech = this.filterR;
      this._genericTableService.setParamsFintech(this.filterR);
    }
    if (filter == 'nombre') this.name = null;
    if (filter == 'apellidopaterno') this.lastName = null;
    if (filter == 'apellidomaterno') this.lastNameM = null;
    if (filter == 'foliosolicitud') this.folio = null;
    if (filter == 'fechasolicitud') this.dateRequest = null;

    this.loadInfoPaysheet(this.page, this.tabSelectedIndex);
  }

  updateFilters(filtro: any) {

    if (filtro) {
      this.name = filtro.nombre == "" || filtro.nombre == null ? null : filtro.nombre;
      this.lastName = filtro.apellidopaterno == "" || filtro.apellidopaterno == null ? null : filtro.apellidopaterno;
      this.lastNameM = filtro.apellidomaterno == "" || filtro.apellidomaterno == null ? null : filtro.apellidomaterno;
      this.folio = filtro.foliosolicitud == "" || filtro.foliosolicitud == null ? null : filtro.foliosolicitud;
      this.dateRequest = filtro.fechasolicitud == "" || filtro.fechasolicitud == null ? null : Information.getDateString(new Date(filtro.fechasolicitud));
      this._genericTableService.setParamsFintech(this.modifyFilter(filtro));
    } else {
      this.name = null;
      this.lastName = null;
      this.lastNameM = null;
      this.folio = null;
      this.dateRequest = null;
    }

  }

  modifyFilter(filtros: any) {
    if (filtros['fechasolicitud'] != null && filtros['fechasolicitud'] != "") {
      let fechaStart = new Date(filtros['fechasolicitud']).toISOString();
      filtros['fechasolicitud'] = fechaStart;
    }
    return filtros;
  }


  
  ngOnDestroy() {
    this.eventoFiltrado.unsubscribe();
    this._fintechService.setRootSelected('');
  } 

}

export const TABS = {
  0: 'ES',
  1: 'AP',
  2: 'R'
}


