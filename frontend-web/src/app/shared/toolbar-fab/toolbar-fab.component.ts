import { Component, OnInit, Input, Output, EventEmitter, ElementRef, OnDestroy } from '@angular/core';
import { ToolbarFabService } from '../../services/toolbar-fab/toolbar-fab.service';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { SelectorColumnsComponent } from '../selector-columns/selector-columns.component';
import { GenericTableService } from '../../services/generic-table/generic-table.service';
import { SavedRoutesService } from '../../services/routing/saved-routes.service';
import { DownloadService } from '../../services/download/download.service';
import { Router } from '@angular/router';
import { PATH_EXCEL } from '../../../environments/environment';
import { NotificationService } from '../../services/notification/notification.service';
import { LocalStorageService } from '../../services/local-sotorage/localstorage.service';
import { saveAs } from 'file-saver';
import { DataService } from '../../services/data.service';
import { DialogLoadingComponent } from '../../components/dialog-loading/dialog-loading.component';
import { PaysheetService } from '../../services/paysheet/paysheet.service';
import { TABS_FINTECH, environment, TABLE_ROUTE_USERS } from '../../../environments/environment.prod';
import { Information } from '../../util/date';


const dialogConfig = new MatDialogConfig();

@Component({
    selector:'app-toolbar-fab',
    templateUrl:'./toolbar-fab.component.html',
    styleUrls:['./toolbar-fab.component.css'],
})

export class ToolbarFabComponent implements OnInit, OnDestroy{
    headersInactive:string[] = [];
    headersActive:string[] = [];

    arrayVisible:any[] = [];
    visibleFilter:boolean = true;
    ocultar:boolean=true;
    dialogRef:any;

    tabFintech:number;

    showInputUpdate:boolean=true;

    constructor(
        private _toolbarFab: ToolbarFabService,
        private _saveRouteService:SavedRoutesService,
        private dialog: MatDialog,
        private _localStorageService:LocalStorageService,
        private _notificationS:NotificationService,
        private _router:Router,
        private _tableService: GenericTableService,
        private _dowload: DownloadService,
        private _data: DataService,
        private _fintechService: PaysheetService) {

        this._toolbarFab.getVisible()
                        .subscribe( arrayToolbar => {
                            this.arrayVisible = arrayToolbar;
                        });

        this._tableService.getHeadersInactive().subscribe( inactive => this.headersInactive = inactive );
        this._tableService.getHeadersActive().subscribe( active => this.headersActive = active );
        this._fintechService.getTabFintech().subscribe(tab => {
            this.tabFintech = Number(tab);
        });

        this._toolbarFab.getRolUserRead().subscribe( (idRol:boolean) => {
            this.showInputUpdate = idRol;
        });

    }

    ngOnInit(){
        this.visibleFilter = this._toolbarFab.getVisibleFilter();
    }

    clickEvent(button:string){
        this._toolbarFab.setAddEvent(button);
    }

    clickSearch(button:string){
        this._toolbarFab.setClickSearch( button );
    }

    openSelectorColumns() {
        dialogConfig.disableClose = true;
        dialogConfig.data={
          actives: this.headersActive,
          inactives: this.headersInactive,          
          tabFintech: this.tabFintech
        }
        
        const dialogRef = this.dialog.open(SelectorColumnsComponent, dialogConfig);

        dialogRef.afterClosed().subscribe(result=>{
          let varLocalStorage = this._localStorageService.getVarLocalStorage( this._router.url.toString() );
            if (varLocalStorage == null ){
                if (this._router.url.toString() == '/home/adelantos/my-advance') {
                    let nameStorage = this._localStorageService.getVarStorageFintechAdvance(this.tabFintech);
                    localStorage.setItem(nameStorage, JSON.stringify(result));
                    this._tableService.setColumnsGroups(result);                    
                } else if (this._router.url.toString() == '/home/adelantos/velo-cash') {
                    let nameStorage = this._localStorageService.getVarStorageFintechVeloCash(this.tabFintech);
                    localStorage.setItem(nameStorage, JSON.stringify(result));
                    this._tableService.setColumnsGroups(result);
                }
            }else{
                console.log("prueba del local storage ", this._router.url.toString());
                console.log("prueba del local storage ", varLocalStorage);
                localStorage.setItem(varLocalStorage, JSON.stringify(result));
                this._tableService.setColumnsGroups(result);
            }

        })
    }

    openLoading(){
       this.dialogRef = this.dialog.open(DialogLoadingComponent, {
            disableClose: true,                        
            width: '200px',          
            data: {message:'Cargando Archivo'}
        });          

    }

    download(){
        
        //this.openLoading();
        this._data.setIsLoadingEvent(true);
        try{
            this._saveRouteService.setSection( this._router.url.toString() );

            let section = Number(this._saveRouteService.getSectionSelected());

            let URL = section != 8 ? PATH_EXCEL.GENERIC : PATH_EXCEL.ROLES;

            if ( section === 4 ) {
                section = this._notificationS.getTabNotification() == 0 ? 4 : 5;
            } else if(section === 1){

                URL = TABLE_ROUTE_USERS.EXCEL_USER

            }

            this._saveRouteService.setSectionExcel(this._router.url.toString());

            if (this._saveRouteService.getSectionExcel() == 20){
                let tabSelect = this._fintechService.getTabFintechSelection();
                section = tabSelect == 0 ? TABS_FINTECH.FINTECH_ADVANCE_ES : 
                    (tabSelect == 1 ? TABS_FINTECH.FINTECH_ADVANCE_AP : 
                    (tabSelect == 2 ? TABS_FINTECH.FINTECH_ADVANCE_R : 11));

                URL = PATH_EXCEL.GENERIC;
                
            } else if (this._saveRouteService.getSectionExcel() == 21){
                let tabSelect = this._fintechService.getTabFintechSelection();
                section = tabSelect == 0 ? TABS_FINTECH.FINTECH_VELOCASH_ES :
                    (tabSelect == 1 ? TABS_FINTECH.FINTECH_VELOCASH_AP :
                        (tabSelect == 2 ? TABS_FINTECH.FINTECH_VELOCASH_R : 12));

                URL = PATH_EXCEL.GENERIC;
            }

            this._dowload.downloadExcel( URL,section).subscribe((data:any) => {
                if(data.base64){
                    this.downloadFile( data.base64 )
                }
            },error => { 
                this._data.setIsLoadingEvent(false);
                //this.dialogRef.close();              
            });        
        } catch(e){
            this._data.setIsLoadingEvent(false);
            //this.dialogRef.close();
        }
    } 

    downloadFile(base64) {
        //
        let nameFile = '';
        if (this._router.url === environment.ROOT_NOTIFICATION){
            let date = new Date();
            let fecha: string = Information.getDateString(date);

            nameFile = `${this._notificationS.getNameExcelNotification()}_${fecha}.xlsx`;
        }else{
            nameFile = this._dowload.getFileExcel(this._router.url);
        }
        
        let blob = new Blob([this.base64toBlob(base64, 'data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet')],
           { type: 'data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'});
         if(blob){
            saveAs(blob, nameFile); 
             this._data.setIsLoadingEvent(false);           
            //this.dialogRef.close();
         }else{
            this._data.setIsLoadingEvent(false);
            //this.dialogRef.close();
         }

      }    

    base64toBlob(base64Data, contentType) {
        contentType = contentType || '';
        let sliceSize = 1024;
        let byteCharacters = atob(base64Data);
        let bytesLength = byteCharacters.length;
        let slicesCount = Math.ceil(bytesLength / sliceSize);
        let byteArrays = new Array(slicesCount);
        for (let sliceIndex = 0; sliceIndex < slicesCount; ++sliceIndex) {
            let begin = sliceIndex * sliceSize;
            let end = Math.min(begin + sliceSize, bytesLength);

            let bytes = new Array(end - begin);
            for (var offset = begin, i = 0; offset < end; ++i, ++offset) {
                bytes[i] = byteCharacters[offset].charCodeAt(0);
            }
            byteArrays[sliceIndex] = new Uint8Array(bytes);
        }
        return new Blob(byteArrays, { type: contentType });
    }
    ngOnDestroy(): void {
     
    }
}