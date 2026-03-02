import { Component, OnInit } from '@angular/core';
import { PaysheetService } from '../../../services/paysheet/paysheet.service';
import { MatDialog } from '@angular/material';
import { of } from 'rxjs';
import { take, skip } from 'rxjs/operators';
import { DialogFintechComponent } from '../dialog-fintech/dialog-fintech.component';
import { environment, BREADCRUMB, TABLE_ROUTE, HEADERS } from '../../../../environments/environment.prod';
import { ToolbarFabService } from '../../../services/toolbar-fab/toolbar-fab.service';
import { Router } from '@angular/router';
import { BreadcrumbService } from '../../../services/breadcrumbs/breadcrumbs.service';
import { GenericTableService } from '../../../services/generic-table/generic-table.service';
import { DataService } from '../../../services/data.service';

@Component({
  selector: 'app-paysheet-forms',
  templateUrl: './paysheet-forms.component.html',
  styleUrls: ['./paysheet-forms.component.css']
})
export class PaysheetFormsComponent implements OnInit {

    arrayInput:any[]=[];
    formulario:any = {
        arrayOne: [],
        arrayTwo: []
    };
    infoFintech:any={};
    headers:any[]=[];
    titles:any[]=[];
    typeFintech:string;

  selectState:boolean=null;
  selectEnabled:boolean=false;
  tableData:any={};

  private formType:string;

  controlFintech:boolean = true;

  constructor(private _paysheetService: PaysheetService,
             public dialog: MatDialog,
             private _toolbar: ToolbarFabService,
             private router: Router,
             private _breadcrumb: BreadcrumbService,
             private _dataS: DataService,
             private _genericTableService: GenericTableService) {

          
    this._dataS.setIsLoadingEvent(true);

    this._toolbar.setVisible(this.router.url.toString());
    let infoOneFintech = this._paysheetService.getFintechJSON();              

    let urlBreadcrumbs = infoOneFintech.typeFintech == environment.TYPE_FINTECH_ADVANCE ? '/home/adelantos/my-advance' : 
                         (infoOneFintech.typeFintech == environment.TYPE_FINTECH_VELOCASH ? '/home/adelantos/velo-cash' : '' );

    this._paysheetService.setRootSelected(urlBreadcrumbs);

    let titleBreadcrumbs = infoOneFintech.typeFintech == 'MyAdvance' ? 'Mi adelanto':
                           (infoOneFintech.typeFintech == 'VeloCash' ? 'VeloCash' : '');                        
    if (infoOneFintech.status == "ES") {      
      this._breadcrumb.setRouteText({ title: titleBreadcrumbs, subMenu: BREADCRUMB.FINTECH_ADVANCE_ES, visible: true, urlSub: urlBreadcrumbs, arrow: false });
    }else if(infoOneFintech.status == "AP") {
      this._breadcrumb.setRouteText({ title: titleBreadcrumbs, subMenu: BREADCRUMB.FINTECH_ADVANCE_AP, visible: true, urlSub: urlBreadcrumbs, arrow: false });
    }else if(infoOneFintech.status == "R") {
      this._breadcrumb.setRouteText({ title: titleBreadcrumbs, subMenu: BREADCRUMB.FINTECH_ADVANCE_R, visible: true, urlSub: urlBreadcrumbs, arrow: false });
    }
    
    let idFintech = infoOneFintech.id;
    let status = infoOneFintech.status;
    let tab = infoOneFintech.tab;
    this.typeFintech = infoOneFintech.typeFintech;
    if (status == TABS[1]){
       this.selectState = null;
    }



    let urlServer = infoOneFintech.typeFintech == environment.TYPE_FINTECH_ADVANCE ? TABLE_ROUTE.FINTECH_ADVANCE :
      (infoOneFintech.typeFintech == environment.TYPE_FINTECH_VELOCASH ? TABLE_ROUTE.FINTECH_VELOCASH : '');

    let headerFintech = infoOneFintech.tab == 0 ? HEADERS.FINTECH_WAIT_VELOCASH : (infoOneFintech.tab == 1 ? HEADERS.FINTECH_APPROVED_VELOCASH :
      (infoOneFintech.tab == 2 ? HEADERS.FINTECH_REJECTED_VELOCASH : ''));
    
    if (headerFintech == HEADERS.FINTECH_WAIT_VELOCASH && urlServer == TABLE_ROUTE.FINTECH_ADVANCE){
       headerFintech = HEADERS.FINTECH_WAIT_ADVANCE;
    }
    if (headerFintech == HEADERS.FINTECH_APPROVED_VELOCASH && urlServer == TABLE_ROUTE.FINTECH_ADVANCE){
       headerFintech = HEADERS.FINTECH_APPROVED_ADVANCE;
    }

    if (headerFintech == HEADERS.FINTECH_REJECTED_VELOCASH && urlServer == TABLE_ROUTE.FINTECH_ADVANCE){
       headerFintech = HEADERS.FINTECH_REJECTED_ADVANCE;
    }
    

    this._genericTableService.getInfoTable(urlServer, headerFintech, "0", tab)
      .subscribe((resp: any) => {
        console.log("INFO ", resp);

        this.tableData = resp.infoTable;

        this.headers = this.tableData.headers;
        this.titles = this.tableData.titles;
        this.formulario.arrayOne = this.headers.slice(0, 14);
        this.formulario.arrayTwo = this.headers.slice(14, 27);

        console.log("TITULOS FORMULARIO ", this.titles);
        console.log("HEADERS FORMULARIO", this.headers);
        console.log("estado ", status);

        

    });     
    
    /*this.headers = infoOneFintech.headers;
    this.titles = infoOneFintech.titles;
    console.log("TITULOS ", this.titles);
    console.log("estado ", status);*/
        
    if (this.typeFintech == environment.TYPE_FINTECH_ADVANCE){
      this._paysheetService.getFintechAdvance(idFintech, status).subscribe(
          infoFintech => {
            console.log("CONSULTA UNICA ", infoFintech);
            this.infoFintech = infoFintech;
            this._dataS.setIsLoadingEvent(false);
          }, error => {
            this._dataS.setIsLoadingEvent(false);
          }
       ); 
    } else if (this.typeFintech == environment.TYPE_FINTECH_VELOCASH){
      this._paysheetService.getFintechVeloCash(idFintech, status).subscribe(
          infoVeloCash => {
            console.log("CONSULTA UNICA ", infoVeloCash);
            this.infoFintech = infoVeloCash;
            this._dataS.setIsLoadingEvent(false);
          }, error => {
            this._dataS.setIsLoadingEvent(false);
          }
      );
    }
         
     

    this._paysheetService.getStatusNull().subscribe(
          status => {
            this.selectState = null;
    });
           
  }

  ngOnInit() {
         
        this.formType = 'onHold';
        //this.arrayInput = this._paysheetService.getInputsFintech(this.formType);

        
        
        
  }

  changeState(event) {
    
    let valueControl:string = '';
    console.log('EVENTO ', event);
    console.log("BANDERA CONTROL", this.selectState);
    if(event == true){
      valueControl = 'Aprobar';
    }else if(event == false){
      valueControl = 'Rechazar';
    }
    if (this.selectState == true || this.selectState == false){
      const dialogRef = this.dialog.open(DialogFintechComponent, {
        disableClose: true,
        width: '350px',
        data: { notification: valueControl, idFintech: this.infoFintech.idFintech, typeFintech: this.typeFintech, status: event }
      });

      dialogRef.afterClosed().subscribe(resp => {
        this.selectEnabled = true;
      });
    }
  }  
  
}

export const TABS = {
  0: 'AP',
  1: 'ES',
  2: 'R'
}

export const TITLES = {
  'MyAdvance': 'Mi adelanto',
  'VeloCash':'VeloCash'
}